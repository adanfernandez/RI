package uo.ri.business.impl;

import java.util.List;

import uo.ri.business.AdminService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.business.dto.VoucherSummary;
import uo.ri.business.impl.admin.AddMechanic;
import uo.ri.business.impl.admin.DeleteMechanic;
import uo.ri.business.impl.admin.FindAllMechanics;
import uo.ri.business.impl.admin.FindMechanicById;
import uo.ri.business.impl.admin.GenerarBonos;
import uo.ri.business.impl.admin.ListarBonosCliente;
import uo.ri.business.impl.admin.ListarTodosBonos;
import uo.ri.business.impl.admin.UpdateMechanic;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

/**
 * Clase AdminServiceImpl. Implementa a AdminService.
 * 
 * @author Adán
 *
 */
public class AdminServiceImpl implements AdminService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	/**
	 * Añadir mecánico a la base de datos
	 */
	@Override
	public void addMechanic(MechanicDto mecanico) throws BusinessException {
		executor.execute( new AddMechanic( mecanico ) );
	}

	/**
	 * Eliminar mecánico de la base de datos
	 */
	@Override
	public void deleteMechanic(Long idMechanicDto) throws BusinessException {
		executor.execute( new DeleteMechanic( idMechanicDto ) );
	}

	/**
	 * Actualizar mecánico de la base de datos
	 */
	@Override
	public void updateMechanic(MechanicDto mecanico) throws BusinessException {
		executor.execute( new UpdateMechanic( mecanico ) );
	}

	/**
	 * Buscar mecánico pasando el identificador
	 */
	@Override
	public MechanicDto findMechanicById(Long id) throws BusinessException {
		return executor.execute( new FindMechanicById( id ) );
	}

	/**
	 * Busca todos los mecánicos
	 */
	@Override
	public List<MechanicDto> findAllMechanics() throws BusinessException {
		return executor.execute( new FindAllMechanics() );
	}

	/**
	 * Generar bonos por tres averías y por facturas con un importe superior de
	 * 500€
	 */
	@Override
	public int generateVouchers() throws BusinessException {
		return executor.execute( new GenerarBonos() );
	}

	/**
	 * Obtener una lista de Bonos (DTO) de un cliente dado por su identificador.
	 */
	@Override
	public List<VoucherDto> findVouchersByClientId(Long id)
			throws BusinessException {
		return executor.execute( new ListarBonosCliente( id ) );
	}

	/**
	 * Obtener una lista de VoucherSummary de todos los clientes de la base de
	 * datos
	 */
	@Override
	public List<VoucherSummary> getVoucherSummary() throws BusinessException {
		return executor.execute( new ListarTodosBonos() );
	}
}
