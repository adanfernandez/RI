package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uo.ri.conf.Conf;
import uo.ri.persistence.MecanicosGateway;
import alb.util.jdbc.Jdbc;

public class MecanicosGatewayImpl implements MecanicosGateway {

	private static String SQL_INSERT_MECHANIC = Conf.get("SQL_INSERT_MECHANIC");
	private static String SQL_DELETE_MECHANIC = Conf.get("SQL_DELETE_MECHANIC");
	private static String SQL_FIND_ALL_MECHANICS = Conf
			.get("SQL_FIND_ALL_MECHANICS");
	private static String SQL_UPDATE_MECHANIC = Conf.get("SQL_UPDATE_MECHANIC");

	
	private Connection c;
	
	/**
	 * Establecer conexión con la base de datos
	 * @param c Connection
	 */
	public void setConnection(Connection c)
	{
		this.c = c;
	}
	
	/**
	 * Añadir un mecánico
	 * @param nombre nombre del mecánico
	 * @param apellido apellido del mecánico
	 */
	@Override
	public void addMechanic(String nombre, String apellidos) {
		
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_INSERT_MECHANIC);
			pst.setString(1, nombre);
			pst.setString(2, apellidos);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/**
	 * Eliminar mecánico
	 * @param id identificador del mecánico a eliminar
	 */
	@Override
	public void deleteMechanic(Long idMecanico) {
		
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_DELETE_MECHANIC);
			pst.setLong(1, idMecanico);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	/**
	 * Encontrar todos los mecánicos
	 * @return Lista de Map con los datos de todos los mecánicos
	 */
	@Override
	public List<Map<String, Object>> findAllMechanics() {
		List<Map<String, Object>> mechanics = new ArrayList<>();

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_FIND_ALL_MECHANICS);

			rs = pst.executeQuery();
			while (rs.next()) {

				Map<String, Object> mechanic = new HashMap<>();

				mechanic.put("idMecanico", rs.getLong(1));
				mechanic.put("nombre", rs.getString(2));
				mechanic.put("apellidos", rs.getString(3));

				mechanics.add(mechanic);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

		return mechanics;
	}

	/**
	 * Actualizar un mecánico
	 * @param nombre nombre
	 * @param apellidos apellidos
	 * @param id identificador
	 */
	@Override
	public void updateMechanic(String nombre, String apellidos, Long id) {
		
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			

			pst = c.prepareStatement(SQL_UPDATE_MECHANIC);
			pst.setString(1, nombre);
			pst.setString(2, apellidos);
			pst.setLong(3, id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}
}
