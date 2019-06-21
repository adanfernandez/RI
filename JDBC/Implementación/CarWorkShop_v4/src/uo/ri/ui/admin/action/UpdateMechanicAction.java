package uo.ri.ui.admin.action;

import uo.ri.business.AdminService;
import uo.ri.business.impl.admin.UpdateMechanic;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import alb.util.console.Console;
import alb.util.menu.Action;

public class UpdateMechanicAction implements Action {

	UpdateMechanic um;

	@Override
	public void execute() throws BusinessException {

		Long id = Console.readLong("Id del mec√°nico");
		String nombre = Console.readString("Nombre");
		String apellidos = Console.readString("Apellidos");
		

		AdminService adminService = ServicesFactory.getAdminService();
		adminService.updateMechanic(id, nombre, apellidos);

		Console.println("Mec·nico actualizado");
	}

}
