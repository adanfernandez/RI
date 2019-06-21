package uo.ri.business.impl.admin;

import java.util.ArrayList;
import java.util.List;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;

/**
 * Encontrar todos los mecánicos
 * 
 * @author Adán
 *
 */
public class FindAllMechanics implements Command<List<MechanicDto>> {

	MecanicoRepository mecanicoRepository = Factory.repository.forMechanic();

	/**
	 * Encontrar todos los mecánicos.
	 * 
	 * @return
	 */
	public List<MechanicDto> execute() {
		List<Mecanico> mecanicos = mecanicoRepository.findAll();
		List<MechanicDto> mecanicosDto = new ArrayList<MechanicDto>();
		for (Mecanico m : mecanicos) {
			mecanicosDto.add( DtoAssembler.toDto( m ) );
		}
		return mecanicosDto;
	}
}
