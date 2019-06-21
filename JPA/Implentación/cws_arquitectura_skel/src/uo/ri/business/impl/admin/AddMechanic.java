package uo.ri.business.impl.admin;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;

/**
 * Añadir mecánico
 * 
 * @author Adán
 *
 */
public class AddMechanic implements Command<Void> {

	private MechanicDto dto;

	private MecanicoRepository mecanicoRepositorio = Factory.repository
			.forMechanic();

	/**
	 * Añadir mecanico a la base de datos
	 * 
	 * @param mecanico
	 *            , MechanicDto. Mecanico a añadir
	 */
	public AddMechanic(MechanicDto mecanico) {
		this.dto = mecanico;
	}

	/**
	 * Método execute donde se llamara los repositorios para añadir al mecanico
	 * (dto) pasado previamente en el constructor
	 */
	@Override
	public Void execute() {
		mecanicoRepositorio.add( DtoAssembler.toEntity( dto ) );
		return null;
	}

}
