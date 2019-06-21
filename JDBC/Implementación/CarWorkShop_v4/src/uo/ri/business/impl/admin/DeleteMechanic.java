package uo.ri.business.impl.admin;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.conf.PersistentFactory;
import uo.ri.persistence.MecanicosGateway;

public class DeleteMechanic {

	private Long idMecanico;

	/**
	 * Eliminar un mecánico mediante su ID
	 * 
	 * @param id
	 *            id del mecánico a eliminar
	 */
	public DeleteMechanic(Long id) {
		this.idMecanico = id;
	}

	/**
	 * Eliminar un mecánico
	 */
	public void execute() {

		MecanicosGateway m = PersistentFactory.getMecanicosGatewayImpl();
		Connection c = null;
		try {
			c = Jdbc.getConnection();
			m.setConnection(c);
			c.setAutoCommit(false);
			m.deleteMechanic(idMecanico);
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
