package uo.ri.ui.admin;

import uo.ri.ui.admin.action.GenerarBonosAction;
import uo.ri.ui.admin.action.ListarBonosAction;
import uo.ri.ui.admin.action.ListarTodosBonosAction;
import alb.util.menu.BaseMenu;

public class Gesti�nBonosMenu extends BaseMenu {

	public Gesti�nBonosMenu() {
		menuOptions = new Object[][] {
				{ "Administrador > Gesti�n de mec�nicos", null },

				{ "Listar bonos de un cliente dado", ListarBonosAction.class },
				{ "Listar todos los bonos de todos los clientes",
						ListarTodosBonosAction.class },
				{ "Generar bonos de los clientes", GenerarBonosAction.class }, };
	}

}
