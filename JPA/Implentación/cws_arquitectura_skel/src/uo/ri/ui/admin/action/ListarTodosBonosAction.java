package uo.ri.ui.admin.action;

import java.util.ArrayList;
import java.util.List;

import uo.ri.business.AdminService;
import uo.ri.business.dto.VoucherSummary;
import uo.ri.conf.Factory;
import alb.util.console.Console;
import alb.util.menu.Action;

public class ListarTodosBonosAction implements Action {

	/**
	 * Listado de un resumen los bonos que se encuentran en la base de datos. Se
	 * enseñarán todos ellos agrupados por clientes. Se mostrará el DNI del
	 * cliente y su nombre, para a continuación mostrar el número de bonos que
	 * disponen, el total de éstos, el dinero disponible y el consumido.
	 */
	@Override
	public void execute() throws Exception {

		List<VoucherSummary> bonos = new ArrayList<VoucherSummary>();

		AdminService as = Factory.service.forAdmin();

		bonos = as.getVoucherSummary();
		Console.println( "Listado de bonos: " );
		for (VoucherSummary bono : bonos) {
			Console.println( "\n\nDNI del cliente " + bono.dni + "\nNombre "
					+ bono.name + "\nNúmero de bonos " + bono.emitted
					+ "\nTotal de los bonos " + bono.totalAmount
					+ "\nConsumido " + bono.consumed + "\nDisponible "
					+ bono.available );
		}

	}

}
