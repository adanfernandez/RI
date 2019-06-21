package uo.ri.business.impl.admin;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.conf.PersistentFactory;
import uo.ri.persistence.MecanicosGateway;

public class AddMechanic {

	private String nombre;
	private String apellidos;

	/**
	 * A�adir un mec�nico.
	 * 
	 * @param nombre
	 *            nombre del mec�nico
	 * @param apellidos
	 *            apellidos del mec�nico
	 */
	public AddMechanic(String nombre, String apellidos) {
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	/**
	 * A�adir un mec�nico
	 */
	public void execute() {

		MecanicosGateway m = PersistentFactory.getMecanicosGatewayImpl();
		Connection c = null;
		try {
			c = Jdbc.getConnection();
			m.setConnection(c);
			c.setAutoCommit(false);
			m.addMechanic(nombre, apellidos);
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
