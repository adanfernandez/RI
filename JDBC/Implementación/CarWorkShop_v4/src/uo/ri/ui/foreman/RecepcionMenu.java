package uo.ri.ui.foreman;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.ui.foreman.action.ModificarAveriaAction;
import uo.ri.ui.foreman.action.RegistrarAveriaAction;

public class RecepcionMenu extends BaseMenu {

	public RecepcionMenu() {
		menuOptions = new Object[][] {
				{ "Jefe de Taller > Recepci�n en taller", null },

				{ "Registrar aver�a", RegistrarAveriaAction.class },
				{ "Modificar averia", ModificarAveriaAction.class },
				{ "Eliminar una averia", NotYetImplementedAction.class },
				{ "", null },
				{ "Listar aver�as", NotYetImplementedAction.class },
				{ "Ver una aver�a", NotYetImplementedAction.class },
				{ "", null },
				{ "Listar mec�nicos", NotYetImplementedAction.class },
				{ "Asignar una aver�a", NotYetImplementedAction.class }, };
	}

}
