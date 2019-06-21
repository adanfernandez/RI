package uo.ri.business.impl;

import java.util.List;
import java.util.Map;

import uo.ri.business.AdminService;
import uo.ri.business.impl.admin.AddMechanic;
import uo.ri.business.impl.admin.DeleteMechanic;
import uo.ri.business.impl.admin.FindAllMechanics;
import uo.ri.business.impl.admin.GenerarBonos;
import uo.ri.business.impl.admin.ListarBonos;
import uo.ri.business.impl.admin.ListarTodosBonos;
import uo.ri.business.impl.admin.UpdateMechanic;
import uo.ri.common.BusinessException;

public class AdminServiceImpl implements AdminService {

	/**
	 * Añadir un mecánico al taller.
	 */
	@Override
	public void newMechanic(String nombre, String apellidos) {
		new AddMechanic(nombre, apellidos).execute();
	}

	/**
	 * Eliminar un mecánico del taller.
	 */
	@Override
	public void deleteMechanic(Long id) {

		new DeleteMechanic(id).execute();

	}

	/**
	 * Actualizar datos acerca de un mecánico dado.
	 */
	@Override
	public void updateMechanic(Long id, String nombre, String apellidos) {
		new UpdateMechanic(id, nombre, apellidos).execute();
	}

	/**
	 * Listar todos los mecánicos del taller.
	 */
	@Override
	public List<Map<String, Object>> findAllMechanics() {
		return new FindAllMechanics().execute();
	}

	/**
	 * Listar los bonos de un cliente dado junto a un pequeño resumen de estos.
	 */
	@Override
	public List<Map<String, Object>> listarBonos(Long id)
			throws BusinessException {
		return new ListarBonos(id).execute();
	}

	/**
	 * Listar todos los bonos de todos los clientes junto a sus características.
	 */
	@Override
	public List<Map<String, Object>> listarTodosLosBonos() {
		return new ListarTodosBonos().execute();
	}

	/**
	 * Generación de los bonos por 3 averías o por facturas con un importe
	 * superior a 500€.
	 */
	@Override
	public void generarBonos() {
		new GenerarBonos().execute();

	}

}
