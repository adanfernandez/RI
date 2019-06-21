package uo.ri.business.impl.admin;

import uo.ri.conf.Factory;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.model.Mecanico;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto dto;

	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public Void execute() throws BusinessException {
		MecanicoRepository mecanicoRepository = Factory.repository
				.forMechanic();
		Mecanico mecanico = mecanicoRepository.findById( dto.id );
		mecanico.setNombre( dto.name );
		mecanico.setApellidos( dto.surname );
		return null;
	}

}
