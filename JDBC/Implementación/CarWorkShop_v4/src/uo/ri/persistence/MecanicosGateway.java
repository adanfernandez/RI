package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface MecanicosGateway {
	
	/**
	 * Añadir un mecánico
	 * @param nombre nombre del mecánico
	 * @param apellido apellido del mecánico
	 */
	public void addMechanic(String nombre, String apellido);

	/**
	 * Eliminar mecánico
	 * @param id identificador del mecánico a eliminar
	 */
	public void deleteMechanic(Long id);

	/**
	 * Encontrar todos los mecánicos
	 * @return Lista de Map con los datos de todos los mecánicos
	 */
	public List<Map<String, Object>> findAllMechanics();

	/**
	 * Actualizar un mecánico
	 * @param nombre nombre
	 * @param apellidos apellidos
	 * @param id identificador
	 */
	public void updateMechanic(String nombre, String apellidos, Long id);
	
	/**
	 * Establecer conexión con la base de datos
	 * @param c Connection
	 */
	public void setConnection(Connection c);
}
