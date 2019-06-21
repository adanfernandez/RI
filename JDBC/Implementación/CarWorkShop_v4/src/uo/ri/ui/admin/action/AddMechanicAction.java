package uo.ri.ui.admin.action;

import uo.ri.business.AdminService;
import uo.ri.business.impl.admin.AddMechanic;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import alb.util.console.Console;
import alb.util.menu.Action;

public class AddMechanicAction implements Action {

	AddMechanic addMechanic;

	public AddMechanicAction() {
	}

	@Override
	public void execute() throws BusinessException {

		String nombre = Console.readString("Nombre");
		String apellidos = Console.readString("Apellidos");
		
		AdminService adminService = ServicesFactory.getAdminService();
		adminService.newMechanic(nombre, apellidos);

		Console.println("Nuevo mecánico añadido");
	}

}
