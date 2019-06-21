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
	 * Actualizar los datos de un mec�nico espec�fico
	 * 
	 * @param id
	 *            id del mec�nico
	 * @param nombre
	 *            nombre del mec�nico
	 * @param apellidos
	 *            apellidos del mec�nico
	 */
	public UpdateMechanic(Long id, String nombre, String apellidos) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	/**
	 * Actualizar los datos de un mec�nico dado
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
