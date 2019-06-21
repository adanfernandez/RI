package uo.ri.ui.admin.action;

import uo.ri.business.AdminService;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;
import alb.util.console.Console;
import alb.util.menu.Action;

public class GenerarBonosAction implements Action {

	/**
	 * Se generarán los bonos. Se le mostrará un mensaje al usuario cuando haya
	 * finalizado el proceso
	 */
	@Override
	public void execute() throws BusinessException {
		AdminService as = Factory.service.forAdmin();
		Console.println( "Actualizando bonos..." );
		as.generateVouchers();
		Console.println( "Bonos actualizados" );
	}

}
