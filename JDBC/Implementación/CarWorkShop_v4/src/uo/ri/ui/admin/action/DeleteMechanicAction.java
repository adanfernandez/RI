package uo.ri.ui.admin.action;

import uo.ri.business.AdminService;
import uo.ri.business.impl.admin.DeleteMechanic;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import alb.util.console.Console;
import alb.util.menu.Action;

public class DeleteMechanicAction implements Action {

	DeleteMechanic dm;

	public DeleteMechanicAction() {
	}

	@Override
	public void execute() throws BusinessException {
		Long idMecanico = Console.readLong("Id de mec·nico");

		AdminService adminService = ServicesFactory.getAdminService();
		adminService.deleteMechanic(idMecanico);

		Console.println("Se ha eliminado el mec√°nico");
	}

}
