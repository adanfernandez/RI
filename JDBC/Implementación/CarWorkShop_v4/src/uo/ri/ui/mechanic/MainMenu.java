package uo.ri.ui.mechanic;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] {
				{ "Mec�nico", null },
				{ "Listar reparaciones asignadas",
						NotYetImplementedAction.class },
				{ "A�adir repuestos a reparaci�n",
						NotYetImplementedAction.class },
				{ "Eliminar repuestos a reparaci�n",
						NotYetImplementedAction.class },
				{ "Cerrar una reparaci�n", NotYetImplementedAction.class }, };
	}

	public static void main(String[] args) {
		new MainMenu().execute();
	}

}
