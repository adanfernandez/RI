package uo.ri.ui.admin;

import uo.ri.ui.admin.action.GenerarBonosAction;
import uo.ri.ui.admin.action.ListarBonosClienteAction;
import uo.ri.ui.admin.action.ListarTodosBonosAction;
import alb.util.menu.BaseMenu;

public class GestionBonosMenu extends BaseMenu {

	public GestionBonosMenu() {
		menuOptions = new Object[][] {
				{ "Administrador > Gestión de bonos", null },

				{ "Generar bonos", GenerarBonosAction.class },
				{ "Listar todos los bonos", ListarTodosBonosAction.class },
				{ "Listar bonos de un cliente dado",
						ListarBonosClienteAction.class } };
	}

}
