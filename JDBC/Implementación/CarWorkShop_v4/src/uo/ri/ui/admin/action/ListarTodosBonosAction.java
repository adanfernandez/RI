package uo.ri.ui.admin.action;

import java.util.List;
import java.util.Map;

import uo.ri.business.AdminService;
import uo.ri.conf.ServicesFactory;
import alb.util.console.Console;
import alb.util.menu.Action;

public class ListarTodosBonosAction implements Action {

	/**
	 * Listar los bonos de todos los clientes (Interfaz de Usuario).
	 */
	@Override
	public void execute() {
		Console.println("\nListado de bonos de todos los clientes \n");

		AdminService a = ServicesFactory.getAdminService();

		List<Map<String, Object>> bonos = a.listarTodosLosBonos();

		for (Map<String, Object> bono : bonos) {

			Console.printf("\n%s\n \t%s\n \t%s\n \t%s\n \t%s\n \t%s\n", "DNI "
					+ bono.get("dni"), "Nombre " + bono.get("nombre"),
					"Número de bonos " + bono.get("bonos"),
					"Total " + bono.get("total"),
					"Acumulado " + bono.get("acumulado"),
					"Disponible " + bono.get("disponible")

			);
		}

	}
}