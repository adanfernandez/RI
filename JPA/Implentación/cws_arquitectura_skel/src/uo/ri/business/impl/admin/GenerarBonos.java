package uo.ri.business.impl.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uo.ri.business.impl.Command;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Averia;
import uo.ri.model.Bono;
import uo.ri.model.Cliente;
import uo.ri.model.Factura;
import uo.ri.model.MedioPago;
import uo.ri.util.exception.BusinessException;

/**
 * Clase GenerarBonos. Implementa a la interfaz Command. Posee el metodo
 * execute() correspondiente a la interfaz. Posee 4 metodos privados que
 * complementan al execute(). Dos de ellos se encargarán de la generación de
 * bonos por 3 averías y por facturas superiores a 500€. Los otros dos se
 * encargarán de generar el código del nuevo bono apartir del más alto de losque
 * ya hay (se ordenan en función del código)
 * 
 * @author Adán
 *
 */
public class GenerarBonos implements Command<Integer> {

	private ClienteRepository clienteRepositorio = Factory.repository
			.forCliente();
	private MedioPagoRepository medioPagoRepository = Factory.repository
			.forMedioPago();
	private FacturaRepository facturaRepository = Factory.repository
			.forFactura();

	/**
	 * Metodo execute(). Se generaran bonos por 3 averias (por 3 averías
	 * cobradas y no usadas previamente habrá un bono de 20€ para el cliente
	 * poseedor del vehículo que las tuvo) y por facturas superiores a 500€ (por
	 * cada factura superior a 500€ se le dará al cliente en cuestión un bono de
	 * 30€). Se devolverá el numero de bonos totales emitidos en ambos casos.
	 */
	@Override
	public Integer execute() throws BusinessException {
		int bonosGenerados = 0;
		List<Cliente> clientes = clienteRepositorio.findAll();
		List<MedioPago> bonos = medioPagoRepository.findAll();
		List<Factura> facturas = facturaRepository.findAll();
		bonosGenerados += generarBonos3Averias( clientes, bonos );
		bonos = medioPagoRepository.findAll();
		bonosGenerados += generarBonosFacturas500( clientes, facturas, bonos );
		return bonosGenerados;
	}

	/**
	 * Generar bonos por tres averías.
	 * 
	 * @param clientes
	 *            List<Cliente> lista de todos los clientes registrados en la
	 *            base de datos
	 * @param bonos
	 *            List<MedioPago> lista de todos los medios de pago registrados
	 *            en la base de datos
	 * @return numero de bonos creados, integer
	 */
	private int generarBonos3Averias(List<Cliente> clientes,
			List<MedioPago> bonos) {
		int bonosGenerados = 0;
		for (Cliente cliente : clientes) {
			List<Averia> averias = cliente.getAveriasBono3NoUsadas();
			int numeroBonos = averias.size() / 3;
			for (int i = 0; i < numeroBonos; i++) {
				String codigo = tratarCodigo( bonos );
				Bono bono = new Bono( cliente, codigo, "Por tres averías", 20 );
				bonos.add( bono );
				medioPagoRepository.add( bono );
				bonosGenerados++;
			}
			int nAverias = averias.size();
			while (nAverias % 3 != 0)
				nAverias--;
			for (int i = 0; i < nAverias; i++)
				averias.get( i ).markAsBono3Used();
		}
		return bonosGenerados;
	}

	/**
	 * Método para generar bonos por facturas superiores a 500 €
	 * 
	 * @param clientes
	 *            clientes candidatos a obtener los bonos
	 * @param facturas
	 *            las facturas registradas en la aplicación
	 * @param bonos
	 *            los bonos que hay registrados en la aplicación
	 * @return numero de bonos creados, integer
	 * @throws BusinessException
	 */
	private int generarBonosFacturas500(List<Cliente> clientes,
			List<Factura> facturas, List<MedioPago> bonos)
			throws BusinessException {
		int bonosGenerados = 0;
		for (Cliente cliente : clientes) {

			List<Factura> facturasCliente = new ArrayList<Factura>();

			for (Factura factura : facturas) {
				for (Averia averia : factura.getAverias()) {
					if (averia.getVehiculo().getCliente().equals( cliente )) {
						if (!facturasCliente.contains( factura )
								&& factura.puedeGenerarBono500()) {
							facturasCliente.add( factura );
						}
					}
				}
			}
			for (int i = 0; i < facturasCliente.size(); i++) {
				facturasCliente.get( i ).markAsBono500Used();
				String codigo = tratarCodigo( bonos );
				Bono bono = new Bono( cliente, codigo,
						"Por factura superior a 500€", 30 );
				bonos.add( bono );
				medioPagoRepository.add( bono );
				bonosGenerados++;
			}
		}
		return bonosGenerados;
	}

	/**
	 * Generación del código de los nuevos bonos. El código siempre empezará por
	 * la letra 'B'. Si no hay bonos registrados en la aplicación la letra irá
	 * seguida del número 1010. Apartir de este número, cada bono nuevo tendrá
	 * como código la letra 'B' más el número del predecesor +10.
	 * 
	 * @param mediosPago
	 *            lista con todos los medios de pago registrados en la
	 *            aplicación
	 * @return codigo codigo que le corresponderá al siguiente bono que se
	 *         introduzca en la aplicación
	 */
	private String tratarCodigo(List<MedioPago> mediosPago) {
		List<Bono> bonos = new ArrayList<Bono>();
		for (MedioPago medioPago : mediosPago) {
			if (medioPago instanceof Bono) {
				bonos.add( (Bono) medioPago );
			}
		}
		if (bonos.size() > 0) {
			comparar( bonos );
			String codigo = bonos.get( bonos.size() - 1 ).getCodigo();
			int numero = Integer
					.parseInt( codigo.substring( 1, codigo.length() ) );
			return "B" + ( numero + 10 );
		}

		return "B" + 1010;
	}

	/**
	 * Ordenar una lista de bonos en función de su código
	 * 
	 * @param bonos
	 *            lista de bonos (List<Bono>)
	 */
	private void comparar(List<Bono> bonos) {
		Collections.sort( bonos, new Comparator<Bono>() {
			@Override
			public int compare(Bono b1, Bono b2) {
				return b1.getCodigo().compareTo( b2.getCodigo() );
			}
		} );
	}

}
