package uo.ri.ui.admin.action;

import java.util.List;

import uo.ri.business.AdminService;
import uo.ri.business.dto.VoucherDto;
import uo.ri.conf.Factory;
import alb.util.console.Console;
import alb.util.menu.Action;

public class ListarBonosClienteAction implements Action {

	/**
	 * Método de interacción conel usuario. Se mostrarán los bonos que posee
	 * este mismo. El usuario es el que se introduzca (ID). Se mostrará el
	 * identificador de cada bono, el identificador del usuario y el dinero
	 * acumulado, disponible y total de cada bono, además de un pequeño resumen
	 * acerca del conjunto de bonos que el usuario posee. Este resumen contedrá
	 * el número total de los bonos emitidos y el dinero disponible, acumulado y
	 * total.
	 */
	@Override
	public void execute() throws Exception {

		Long id_cliente = Console.readLong( "Id del cliente" );

		AdminService as = Factory.service.forAdmin();
		List<VoucherDto> bonos = as.findVouchersByClientId( id_cliente );

		double disponible = 0.0;
		double acumulado = 0.0;
		for (VoucherDto bono : bonos) {
			Console.printf( "\n%s\n \t%s\n \t%s\n \t%s\n \t%s\n \t%s\n",
					"ID del bono " + bono.id,
					"ID del cliente " + bono.clientId, "Acumulado "
							+ bono.accumulated, "Disponible " + bono.available,
					"Código " + bono.code, "Descripción " + bono.description );
			disponible += bono.available;
			acumulado += bono.accumulated;
		}

		int numero = bonos.size();

		Console.println( " \n[RESUMEN DE LOS BONOS]"
				+ "\nNúmero de bonos emitidos " + numero
				+ "\nDinero de los bonos disponible " + disponible
				+ "\nDinero de los bonos acumulado " + acumulado
				+ "\nTotal de los bonos " + ( disponible + acumulado ) );

	}

}
