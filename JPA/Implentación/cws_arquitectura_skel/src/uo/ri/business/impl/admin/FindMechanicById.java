package uo.ri.business.impl.admin;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

/**
 * Encontrar mecánico pasándole el identificador
 * 
 * @author Adán
 *
 */
public class FindMechanicById implements Command<MechanicDto> {

	private Long id;
	MecanicoRepository mecanicoRepository = Factory.repository.forMechanic();

	/**
	 * Encontrar un mecánico pasándole un id. Constructor (Long)
	 * 
	 * @param id
	 */
	public FindMechanicById(Long id) {
		this.id = id;
	}

	/**
	 * Método execute. Devuelve el mecánico (DTO) buscado
	 * 
	 * @return MechanicDto buscado
	 * @throws BusinessException
	 */
	public MechanicDto execute() throws BusinessException {
		return DtoAssembler.toDto( mecanicoRepository.findById( id ) );
	}

}
