package uo.ri.ui.admin.action;

import java.util.List;
import java.util.Map;

import uo.ri.business.AdminService;
import uo.ri.business.impl.admin.FindAllMechanics;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import alb.util.console.Console;
import alb.util.menu.Action;

public class ListMechanicsAction implements Action {

	FindAllMechanics fm;

	public ListMechanicsAction() {
	}

	@Override
	public void execute() throws BusinessException {

		Console.println("\nListado de mecánicos\n");

		AdminService a = ServicesFactory.getAdminService();

		List<Map<String, Object>> mechanics = a.findAllMechanics();

		for (Map<String, Object> mechanic : mechanics) {

			Console.printf("\t%d %s %s\n", mechanic.get("idMecanico"),
					mechanic.get("nombre"), mechanic.get("apellidos"));
		}
	}
}
