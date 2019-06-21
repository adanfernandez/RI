package uo.ri.business.impl.cash;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uo.ri.common.BusinessException;
import uo.ri.conf.PersistentFactory;
import uo.ri.persistence.FacturasGateway;
import uo.ri.persistence.impl.BonosGatewayImpl;
import uo.ri.persistence.impl.FacturasGatewayImpl;
import alb.util.date.DateUtil;
import alb.util.jdbc.Jdbc;
import alb.util.math.Round;

public class LiquidarFactura {

	private List<Map<String, Object>> dineroParaLiquidar;
	private Long id_factura;

	public LiquidarFactura() {
	}

	/**
	 * Constructor de LiquidarFactura
	 * @param idFactura identificador de la factura
	 * @param dineroParaLiquidar  Lista del dinero
	 */
	public LiquidarFactura(Long idFactura,
			List<Map<String, Object>> dineroParaLiquidar) {
		this.dineroParaLiquidar = dineroParaLiquidar;
		this.id_factura = idFactura;
	}

	/**
	 * Devolver el precio de una factura dada. Se comprobará que esta factura no
	 * haya sido abonada previamente.
	 * 
	 * @param id
	 *            id de la factura
	 * @return factura Posee el precio de la factura y el status para poder
	 *         controlar que no se pague dos veces una factura
	 * @throws BusinessException
	 */
	public Map<String, Object> precioFactura(Long id) throws BusinessException  {
		FacturasGatewayImpl gateway = PersistentFactory.getFacturasGateway();
		Map<String, Object> factura = null;
		Connection c = null;
		try {
			c = Jdbc.getConnection();
			gateway.setConnection(c);
			factura = gateway.getPrecioFactura(id);
			if (factura.get("status").equals("ABONADA"))
				throw new BusinessException(
						"La factura ya ha sido abonada con anterioridad");
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			Jdbc.close(c);
		}

		return factura;

	}

	/**
	 * Comprobar si una tarjeta dada está caducada o no.
	 * 
	 * @param numeroTarjeta
	 *            número de la tarjeta sobre la que comprobar la caducidad
	 * @param gateway
	 * @return operacion indica si se puede realizar la operación en función de
	 *         su validez
	 */
	private boolean comprobarCaducidadTarjeta(String numeroTarjeta,
			FacturasGateway gateway) {

		boolean operacion = false;
		operacion = DateUtil.isBefore(DateUtil.now(),
				gateway.caducidad(numeroTarjeta));

		return operacion;
	}

	/**
	 * Obtener el cliente poseedor de una factura dada
	 * 
	 * @param id
	 *            Identificador de la factura
	 * @return cliente id del cliente al que pertenece la factura
	 * @throws BusinessException
	 */
	public Long obtenerClienteDeUnaFactura(Long id) throws BusinessException {
		FacturasGatewayImpl gateway = PersistentFactory.getFacturasGateway();
		Connection c = null;
		Long cliente = null;
		try {
			c = Jdbc.getConnection();
			gateway.setConnection(c);
			cliente = gateway.obtenerClienteDeUnaFactura(id);
			if (cliente == null) {
				throw new BusinessException(
						"No existe cliente asociado a la factura");
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			Jdbc.close(c);
		}
		return cliente;
	}

	/**
	 * Se marcará una factura dada como abonada. Se comprobará que los
	 * parámetros introducidos son correctos. Se actualizarán los datos que
	 * hacen referencia a los medios de pago.
	 * 
	 * @throws BusinessException
	 */
	public void execute() throws BusinessException {
		FacturasGateway gateway = PersistentFactory.getFacturasGateway();
		Connection c = null;

		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			gateway.setConnection(c);
			comprobarPrecio(gateway);
			introducirDinero(gateway);
			gateway.liquidarFactura(id_factura);

			c.commit();
		} catch (SQLException e) {
			if (c != null) {
				try {
					c.rollback();
				} catch (SQLException e1) {
				}
			}
			throw new RuntimeException();
		} finally {
			Jdbc.close(c);
		}
	}

	/**
	 * Comprobar que el precio introducido no es superior a la cantidad total de
	 * la factura
	 */
	private void comprobarPrecio(FacturasGateway gateway)
			throws BusinessException {
		double dinero = 0;
		for (Map<String, Object> cantidad : dineroParaLiquidar) {
			dinero = dinero + (double) cantidad.get("ingresado");
		}
		Map<String, Object> datosFactura = new HashMap<String, Object>();
		datosFactura = gateway.getPrecioFactura(id_factura);

		double acumulado = (double) datosFactura.get("precio");

		if (DateUtil.isAfter(DateUtil.fromDdMmYyyy(1, 7, 2012),
				(Date) datosFactura.get("fecha"))) {
			acumulado = acumulado + acumulado * 0.18;
		} else {
			acumulado = acumulado + acumulado * 0.21;
		}

		if (Round.twoCents(dinero) != Round.twoCents(acumulado)) {
			throw new BusinessException(
					"El importe introducido no se corresponde"
							+ " al total de la factura total");
		}
	}

	/**
	 * Devuelve una lista de Map con las tarjetas de un cliente
	 * 
	 * @param id_cliente
	 *            identificador del cliente
	 * @return tarjetas tarjetas que posee el cliente
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> tarjetasCliente(Long id_cliente)
			throws BusinessException {

		Connection c = null;
		FacturasGatewayImpl gateway = PersistentFactory.getFacturasGateway();
		List<Map<String, Object>> tarjetas = null;
		try {
			c = Jdbc.getConnection();
			gateway.setConnection(c);
			tarjetas = gateway.tarjetasCliente(id_cliente);
			if (tarjetas == null) {
				throw new BusinessException("El cliente no tiene tarjetas");
			}
		} catch (SQLException e) {
		} finally {
			Jdbc.close(c);
		}

		return tarjetas;
	}

	/**
	 * Actualizar el acumulado de un cliente dado, a una tarjeta dada
	 * introduciendo la cantidad a insertar. Se sumará el acumulado a dicha
	 * cantidad.
	 * 
	 * @param dineroIngresado
	 *            dinero a intruducir en la tarjeta
	 * @param numeroTarjeta
	 *            número de la tarjeta en la que se quiere introducir el dinero
	 * @throws BusinessException
	 */
	private void actualizarAcumuladoTarjeta(FacturasGateway gateway,
			double dineroIngresado, String numeroTarjeta)
			throws BusinessException {

		if (!comprobarCaducidadTarjeta(numeroTarjeta, gateway))
			throw new BusinessException(
					"No es posible el pago con esta tarjeta puesto que está "
							+ "caducada.");
		gateway.pagarConTarjeta(dineroIngresado, numeroTarjeta);
	}

	/**
	 * Actualizar el acumulado del metálico un cliente dado introduciendo la
	 * cantidad a insertar. Se sumará el acumulado a dicha cantidad.
	 * 
	 * @param dineroIngresado
	 *            dinero a intruducir en el apartado de metálico
	 * @param id_cliente
	 *            identificador del cliente al que habrá que actualizar.
	 * 
	 */
	private void actualizarMetalico(FacturasGateway gateway,
			double dineroIngresado, Long id_cliente) {
		Connection c = null;

		try {
			c = Jdbc.getConnection();
			gateway.setConnection(c);
			gateway.actualizarMetalico(dineroIngresado, id_cliente);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Listar todos los bonos disponibles por un cliente en una lista de Map
	 * 
	 * @param id_cliente
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> bonosCliente(Long id_cliente)
			throws BusinessException {

		BonosGatewayImpl bonos = PersistentFactory.getBonosImpl();
		Connection c = null;
		List<Map<String, Object>> bonosDeUnCliente = null;
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);
			bonos.setConnection(c);
			bonosDeUnCliente = bonos.verBonosDeUnCliente(id_cliente);
			if (bonosDeUnCliente == null) {
				throw new BusinessException("El cliente no posee bonos");
			}
		} catch (SQLException e) {
			if (c != null) {
				try {
					c.rollback();
				} catch (SQLException e1) {
				}
			}
			throw new RuntimeException();
		} finally {
			Jdbc.close(c);
		}
		return bonosDeUnCliente;
	}

	/**
	 * Actualizar el dinero correspondiente a los bonos
	 * 
	 * @param gateway
	 * @param dineroIngresado
	 *            dinero a ingresar
	 * @param id_bono
	 *            identificador del bono que se debe de actualizar
	 * @throws BusinessException
	 */
	private void actualizarAcumuladoYDisponibleBonos(FacturasGateway gateway,
			double dineroIngresado, Long id_bono) throws BusinessException {

		Map<String, Object> bono = gateway
				.acumuladoHastaElMomentoAcumuladoYDisponibleBono(id_bono);
		if (dineroIngresado > (double) bono.get("disponible"))
			throw new BusinessException(
					"El total introducido es superior al dinero "
							+ "disponible en bonos");

		gateway.actualizarAcumuladoYDisponibleBonos(dineroIngresado, id_bono);
	}

	/**
	 * Introcir el dinero de la factura en su medio de pago correspondiente
	 * 
	 * @param gateway
	 *            Consultas a la base de datos
	 * @throws BusinessException
	 */
	private void introducirDinero(FacturasGateway gateway)
			throws BusinessException {

		for (Map<String, Object> dinero : dineroParaLiquidar) {
			if (dinero.get("dtype").equals("tarjeta")) {
				double acumulado = (double) dinero.get("ingresado");
				String numeroTarjeta = (String) dinero.get("id");
				actualizarAcumuladoTarjeta(gateway, acumulado, numeroTarjeta);
			} else if (dinero.get("dtype").equals("metalico")) {
				double acumulado = (double) dinero.get("ingresado");
				long id_cliente = (long) dinero.get("id");
				actualizarMetalico(gateway, acumulado, id_cliente);
			} else if (dinero.get("dtype").equals("bono")) {
				double acumulado = (double) dinero.get("ingresado");
				long id = (long) dinero.get("id");
				actualizarAcumuladoYDisponibleBonos(gateway, acumulado, id);
			}
		}
	}

}
