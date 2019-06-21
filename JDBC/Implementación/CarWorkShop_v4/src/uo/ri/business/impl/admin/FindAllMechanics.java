package uo.ri.business.impl.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.conf.PersistentFactory;
import uo.ri.persistence.MecanicosGateway;

public class FindAllMechanics {

	/**
	 * Listar todos los mecánicos.
	 * 
	 * @return lista de mapas con los datos de los mecánicos.
	 */
	public List<Map<String, Object>> execute() {
		
		
		MecanicosGateway m = PersistentFactory.getMecanicosGatewayImpl();
		Connection c = null;
		try {
			c = Jdbc.getConnection();
			m.setConnection(c);
			c.setAutoCommit(false);
			return m.findAllMechanics();
		} catch (SQLException e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
			}
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(c);
		}
	}

}
