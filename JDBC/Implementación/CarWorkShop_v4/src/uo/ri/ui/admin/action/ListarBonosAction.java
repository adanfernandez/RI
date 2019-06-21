package uo.ri.ui.admin.action;

import java.util.List;
import java.util.Map;

import uo.ri.business.AdminService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import alb.util.console.Console;
import alb.util.menu.Action;

public class ListarBonosAction implements Action {

	/**
	 * Listar todos los bonos de todos los clientes junto a sus características
	 * (Interfaz de Usuario).
	 */
	@Override
	public void execute() throws BusinessException {

		Long idCliente = Console.readLong("Id de cliente");

		Console.println("\nLISTADO DE BONOS: ");

		AdminService a = ServicesFactory.getAdminService();

		List<Map<String, Object>> bonos = a.listarBonos(idCliente);

		for (Map<String, Object> bono : bonos) {

			Console.printf("\n%s\n \t%s\n \t%s\n \t%s\n \t%s\n",
					"ID " + bono.get("id_bono"),
					"Acumulado " + bono.get("acumulado"),
					"Disponible " + bono.get("disponible"),
					"Código " + bono.get("codigo"),
					"Descripción " + bono.get("descripcion"));
		}

		int numeroDeBonos = 0;

		double total = 0;
		double canjeado = 0;
		double disponible = 0;

		numeroDeBonos = bonos.size();

		for (Map<String, Object> bono : bonos) {
			canjeado = canjeado + (double) bono.get("acumulado");
			disponible = disponible + (double) bono.get("disponible");
		}
		total = disponible + canjeado;

		Console.println("\nRESUMEN DE LOS BONOS DEL CLIENTE:\n\n"
				+ "[  Número de bonos: " + numeroDeBonos
				+ ", Total de los bonos: " + total
				+ ", Canjeado de los bonos: " + canjeado
				+ ", Disponible de los bonos: " + disponible + "  ]");

	}

}
