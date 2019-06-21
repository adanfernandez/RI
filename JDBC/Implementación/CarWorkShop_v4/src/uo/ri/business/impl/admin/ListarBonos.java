package uo.ri.business.impl.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistentFactory;
import uo.ri.persistence.impl.BonosGatewayImpl;

public class ListarBonos {

	private Long id_cliente;

	/**
	 * Listar bonos de un cliente dado.
	 * 
	 * @param id_cliente
	 *            id del cliente sobre el que buscar los bonos.
	 */
	public ListarBonos(Long id_cliente) {
		this.id_cliente = id_cliente;
	}

	/**
	 * Listar los bonos de un cliente dado.
	 * 
	 * @return bonosDeUnCliente bonos de un cliente dado en el constructor.
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> execute() throws BusinessException {
		BonosGatewayImpl bonos = PersistentFactory.getBonosImpl();
		Connection c = null;
		List<Map<String, Object>> bonosDeUnCliente = null;
		try {
			c = Jdbc.getConnection();
			bonos.setConnection(c);
			bonosDeUnCliente = bonos.verBonosDeUnCliente(id_cliente);
			if (bonosDeUnCliente == null) {
				throw new BusinessException("Cliente incorrecto.");
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			Jdbc.close(c);
		}
		return bonosDeUnCliente;
	}

}
