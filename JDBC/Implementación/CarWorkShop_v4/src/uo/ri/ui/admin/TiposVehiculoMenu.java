package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;

public class TiposVehiculoMenu extends BaseMenu {

	public TiposVehiculoMenu() {
		menuOptions = new Object[][] {
				{ "Administrador > Gesti�n de tipos de vehiculo", null },

				{ "A�adir tipo de vehiculo", NotYetImplementedAction.class },
				{ "Modificar datos de tipo de vehiculo",
						NotYetImplementedAction.class },
				{ "Eliminar tipo de vehiculo", NotYetImplementedAction.class },
				{ "Listar tipos de vehiculo", NotYetImplementedAction.class }, };
	}

}
