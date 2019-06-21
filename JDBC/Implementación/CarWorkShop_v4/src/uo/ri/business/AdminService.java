package uo.ri.business;

import java.util.List;
import java.util.Map;

import uo.ri.common.BusinessException;

public interface AdminService {

	/**
	 * A�adir un mec�nico al taller.
	 */
	public void newMechanic(String nombre, String apellidos);

	/**
	 * Eliminar un mec�nico del taller.
	 */
	public void deleteMechanic(Long id);

	/**
	 * Actualizar datos acerca de un mec�nico dado.
	 */
	public void updateMechanic(Long id, String nombre, String apellidos);

	/**
	 * Listar todos los mec�nicos del taller.
	 */
	public List<Map<String, Object>> findAllMechanics();

	/**
	 * Listar los bonos de un cliente dado junto a un peque�o resumen de estos.
	 */
	public List<Map<String, Object>> listarBonos(Long id)
			throws BusinessException;

	/**
	 * Listar todos los bonos de todos los clientes junto a sus caracter�sticas.
	 */
	public List<Map<String, Object>> listarTodosLosBonos();

	/**
	 * Generaci�n de los bonos por 3 aver�as o por facturas con un importe
	 * superior a 500�.
	 */
	public void generarBonos();
}
