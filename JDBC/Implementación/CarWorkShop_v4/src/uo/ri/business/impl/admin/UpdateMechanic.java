package uo.ri.business.impl.admin;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.conf.PersistentFactory;
import uo.ri.persistence.MecanicosGateway;

public class UpdateMechanic {

	private Long id;
	private String nombre;
	private String apellidos;

	/**
	 * Actualizar los datos de un mecánico específico
	 * 
	 * @param id
	 *            id del mecánico
	 * @param nombre
	 *            nombre del mecánico
	 * @param apellidos
	 *            apellidos del mecánico
	 */
	public UpdateMechanic(Long id, String nombre, String apellidos) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	/**
	 * Actualizar los datos de un mecánico dado
	 */
	public void execute() {

		MecanicosGateway m = PersistentFactory.getMecanicosGatewayImpl();
		Connection c = null;
		try {
			c = Jdbc.getConnection();
			m.setConnection(c);
			c.setAutoCommit(false);
			m.updateMechanic(nombre, apellidos, id);
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
