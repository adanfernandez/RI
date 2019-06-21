package uo.ri.ui.admin.action;

import uo.ri.business.AdminService;
import uo.ri.conf.ServicesFactory;
import alb.util.console.Console;
import alb.util.menu.Action;

public class GenerarBonosAction implements Action {

	/**
	 * Generación de los bonos (Interfaz de usuario).
	 */
	@Override
	public void execute()  {

		Console.println("Generando bonos...");
		AdminService a = ServicesFactory.getAdminService();
		a.generarBonos();
		Console.println("Bonos generados y actualiados.");
	}

}
