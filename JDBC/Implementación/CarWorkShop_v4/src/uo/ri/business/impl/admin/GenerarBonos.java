package uo.ri.business.impl.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import uo.ri.conf.PersistentFactory;
import uo.ri.persistence.BonosGateway;
import alb.util.jdbc.Jdbc;

public class GenerarBonos {

	/**
	 * Generación de los bonos por 3 averías y por facturas con un importe
	 * superior a 500€.
	 * 
	 * @throws SQLException
	 */
	public void execute() {
		BonosGateway gateway = PersistentFactory.getBonosImpl();
		Connection connection = null;
		try {
			connection = Jdbc.getConnection();
			connection.setAutoCommit(false);
			gateway.setConnection(connection);

			generarBonos3Averias(gateway);
			generarBonosFacturas500Euros(gateway);

			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
			}
			throw new RuntimeException();
		} finally {
			Jdbc.close(connection);
		}
	}

	/**
	 * Generar bonos para cada cliente que tenga 3 o más averías.
	 */
	private void generarBonos3Averias(BonosGateway gateway) {
		List<Map<String, Object>> clientes = gateway.verClientesParaBonos();

		for (Map<String, Object> c : clientes) {
			int bonosAUtilizar = (int) c.get("averias") / 3;
			Long id_bono;
			String codigo;

			for (int i = 0; i < bonosAUtilizar; i++) {
				id_bono = gateway.obtenerIdBono();
				codigo = gateway.obtenerCodigoBono();
				if (codigo == null)
					codigo = "B1010";
				codigo = tratarCodigo(codigo);
				gateway.añadirBonosAverias(id_bono, (Long) c.get("id_cliente"),
						codigo);
			}

			int nAverias = (int) c.get("averias");
			while (nAverias % 3 != 0)
				nAverias--;

			List<Map<String, Object>> averias = gateway.obtenerAverias((Long) c
					.get("id_cliente"));
			for (int i = 0; i < nAverias; i++) {
				gateway.desactivarAverias((Long) averias.get(i)
						.get("id_averia"));
			}
		}

	}

	/**
	 * Generar bonos por facturas mayores que 500€
	 */
	private void generarBonosFacturas500Euros(BonosGateway gateway) {
		List<Map<String, Object>> clientes = gateway.verClientesFacturas500();

		for (Map<String, Object> c : clientes) {
			int bonosAUtilizar = (int) c.get("NumeroFacturas");
			Long id_bono;
			String codigo;

			for (int i = 0; i < bonosAUtilizar; i++) {
				id_bono = gateway.obtenerIdBono();
				codigo = gateway.obtenerCodigoBono();
				if (codigo == null)
					codigo = "B1010";
				codigo = tratarCodigo(codigo);
				gateway.añadirBonosFacturas(id_bono,
						(Long) c.get("id_cliente"), codigo);
			}

			List<Map<String, Object>> facturas = gateway
					.obtenerFacturas((Long) c.get("id_cliente"));
			for (int i = 0; i < bonosAUtilizar; i++) {
				gateway.desactivarFacturas((Long) facturas.get(i).get(
						"factura_id"));
			}
		}
	}

	/**
	 * Generación del código de los nuevos bonos
	 * 
	 * @param codigo
	 *            código al partir del que se generará el código nuevo
	 * @return codigo código nuevo generado apartir del código pasado como
	 *         parámetro
	 */
	private String tratarCodigo(String codigo) {
		String aux = codigo;
		aux = aux.substring(1, codigo.length());
		int numero = Integer.parseInt(aux) + 10;

		codigo = "B" + numero;

		return codigo;
	}
}
