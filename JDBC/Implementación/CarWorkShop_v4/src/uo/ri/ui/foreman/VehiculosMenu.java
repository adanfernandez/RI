package uo.ri.ui.foreman;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;

public class VehiculosMenu extends BaseMenu {

	public VehiculosMenu() {
		menuOptions = new Object[][] {
				{ "Jefe de Taller > Gesti�n de Veh�culos", null },

				{ "A�adir veh�culo", NotYetImplementedAction.class },
				{ "Modificar datos de veh�culo", NotYetImplementedAction.class },
				{ "Eliminar veh�culo", NotYetImplementedAction.class },
				{ "Listar veh�culo", NotYetImplementedAction.class }, };
	}

}
