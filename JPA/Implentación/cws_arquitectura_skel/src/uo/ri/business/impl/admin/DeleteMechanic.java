package uo.ri.business.impl.admin;

import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;
import uo.ri.util.exception.BusinessException;

/**
 * Eliminar mecánico
 * 
 * @author Adán
 *
 */
public class DeleteMechanic implements Command<Void> {

	private Long idMecanico;
	MecanicoRepository mecanicoRepository = Factory.repository.forMechanic();

	/**
	 * Eliminar mecánico pasándole el id. Constructor
	 * 
	 * @param idMecanico
	 *            identificador del mecánico (Long)
	 */
	public DeleteMechanic(Long idMecanico) {
		this.idMecanico = idMecanico;
	}

	/**
	 * Método execute. Elimina el mecánico
	 * 
	 * @return Void
	 * @throws BusinessException
	 */
	public Void execute() throws BusinessException {
		Mecanico mecanico = mecanicoRepository.findById( idMecanico );
		mecanicoRepository.remove( mecanico );
		return null;
	}

}
