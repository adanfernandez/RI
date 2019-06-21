package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface MecanicosGateway {
	
	/**
	 * A�adir un mec�nico
	 * @param nombre nombre del mec�nico
	 * @param apellido apellido del mec�nico
	 */
	public void addMechanic(String nombre, String apellido);

	/**
	 * Eliminar mec�nico
	 * @param id identificador del mec�nico a eliminar
	 */
	public void deleteMechanic(Long id);

	/**
	 * Encontrar todos los mec�nicos
	 * @return Lista de Map con los datos de todos los mec�nicos
	 */
	public List<Map<String, Object>> findAllMechanics();

	/**
	 * Actualizar un mec�nico
	 * @param nombre nombre
	 * @param apellidos apellidos
	 * @param id identificador
	 */
	public void updateMechanic(String nombre, String apellidos, Long id);
	
	/**
	 * Establecer conexi�n con la base de datos
	 * @param c Connection
	 */
	public void setConnection(Connection c);
}
