package uo.ri.business;

import java.util.List;
import java.util.Map;

import uo.ri.common.BusinessException;

public interface AdminService {

	/**
	 * Añadir un mecánico al taller.
	 */
	public void newMechanic(String nombre, String apellidos);

	/**
	 * Eliminar un mecánico del taller.
	 */
	public void deleteMechanic(Long id);

	/**
	 * Actualizar datos acerca de un mecánico dado.
	 */
	public void updateMechanic(Long id, String nombre, String apellidos);

	/**
	 * Listar todos los mecánicos del taller.
	 */
	public List<Map<String, Object>> findAllMechanics();

	/**
	 * Listar los bonos de un cliente dado junto a un pequeño resumen de estos.
	 */
	public List<Map<String, Object>> listarBonos(Long id)
			throws BusinessException;

	/**
	 * Listar todos los bonos de todos los clientes junto a sus características.
	 */
	public List<Map<String, Object>> listarTodosLosBonos();

	/**
	 * Generación de los bonos por 3 averías o por facturas con un importe
	 * superior a 500€.
	 */
	public void generarBonos();
}
