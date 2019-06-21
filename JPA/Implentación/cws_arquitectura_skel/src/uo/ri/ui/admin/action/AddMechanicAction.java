package uo.ri.ui.admin.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.AdminService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

public class AddMechanicAction implements Action {

	/**
	 * Método de interacción con el usuario para añadir nuevos mecánicos. Deberá
	 * introducir su DNI, nombre y apellidos
	 */
	@Override
	public void execute() throws BusinessException {

		MechanicDto m = new MechanicDto();
		m.dni = Console.readString( "Dni" );
		m.name = Console.readString( "Nombre" );
		m.surname = Console.readString( "Apellidos" );

		AdminService as = Factory.service.forAdmin();
		as.addMechanic( m );

		Console.println( "Nuevo mecánico añadido" );
	}

}
