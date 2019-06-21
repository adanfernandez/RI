package uo.ri.ui.cash.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uo.ri.business.CashService;
import uo.ri.conf.ServicesFactory;
import alb.util.console.Console;
import alb.util.date.DateUtil;
import alb.util.math.Round;
import alb.util.menu.Action;

public class LiquidarFacturaAction implements Action {

	/**
	 * Ejecutar el pago de una factura dada en los distintos medios de pago
	 * disponibles (Interfaz de Usuario).
	 */
	@Override
	public void execute() throws Exception {

		Long id_factura = Console.readLong("Id de la factura ");

		CashService cash = ServicesFactory.getCashService();

		Map<String, Object> factura = cash.precioFactura(id_factura);

		double precio = (double) factura.get("precio");
		
		Date fecha = DateUtil.fromDdMmYyyy(1, 7, 2012);
		
		if(DateUtil.isAfter(fecha, (Date) factura.get("fecha")))
		{
			precio = precio + precio * 0.18;
		}
		else
		{
			precio = precio  + precio * 0.21;
		}
		

		double dineroIngresado = 0;

		Long id_cliente = cash.obtenerClienteDeUnaFactura(id_factura);

		List<Map<String, Object>> dineroParaLiquidar 
		= new ArrayList<Map<String, Object>>();

		while (Round.twoCents(dineroIngresado) < Round.twoCents(precio)) {
			Console.println("Total a pagar: " + Round.twoCents(precio));
			Console.println("Dinero ingresado hasta ahora "
					+ Round.twoCents(dineroIngresado));
			Console.printf("\t%s\n \t%s\n \t%s\n", "1) Tarjeta de crédito",
					"2) Metálico", "3) Bono");
			String medioPago = Console.readString("Elija un medio de pago ");

			Map<String, Object> ingresado = new HashMap<String, Object>();
			if (medioPago.equals("1")) {
				List<Map<String, Object>> tarjetas = cash
						.tarjetasCliente(id_cliente);

				Console.println("TARJETAS DEL CLIENTE CON ID " + id_cliente
						+ " :");
				for (int i = 0; i < tarjetas.size(); i++) {
					Console.printf(
							"\n\n%s\n \t%s\n \t%s\n\n",
							"Numero de la tarjeta: "
									+ tarjetas.get(i).get("numeroTarjeta"),
							"Tipo de la tarjeta: "
									+ tarjetas.get(i).get("tipo"), "Validez: "
									+ tarjetas.get(i).get("validez"));
				}

				if (tarjetas.size() != 0) {
					String numeroTarjeta = Console
							.readString("Número de tarjeta ");

					double ingreso = Console.readDouble("Cantidad ");
					dineroIngresado = ingreso + dineroIngresado;
					ingresado.put("ingresado", ingreso);
					ingresado.put("id", numeroTarjeta);
					ingresado.put("dtype", "tarjeta");
				} else {
					Console.println("No tiene tarjetas asociadas.");
				}
			}

			else if (medioPago.equals("2")) {

				double ingreso = Console.readDouble("Cantidad: ");
				dineroIngresado = ingreso + dineroIngresado;
				ingresado.put("ingresado", ingreso);
				ingresado.put("id", id_cliente);
				ingresado.put("dtype", "metalico");
			}

			else if (medioPago.equals("3")) {
				List<Map<String, Object>> bonos = cash.bonosCliente(id_cliente);

				Console.println("BONOS DEL CLIENTE " + id_cliente + " :");
				for (int i = 0; i < bonos.size(); i++) {
					Console.printf(
							"\n\n%s\n \t%s\n \t%s\n t%s\n t%s\n t%s\n\n",
							"ID: " + bonos.get(i).get("id_bono"),
							"Disponible: " + bonos.get(i).get("disponible"),
							"Acumulado: " + bonos.get(i).get("acumulado"),
							"Total: "
									+ ((double) bonos.get(i).get("disponible")
											+ (double) bonos
											.get(i).get("acumulado")),
							"Código: " + bonos.get(i).get("codigo"),
							"Descripción: " + bonos.get(i).get("descripcion")

					);
				}

				if (bonos.size() != 0) {

					Long id_bono = Console.readLong("ID del bono: ");

					double ingreso = Console.readDouble("Cantidad: ");
					dineroIngresado = ingreso + dineroIngresado;
					ingresado.put("ingresado", ingreso);
					ingresado.put("id", id_bono);
					ingresado.put("dtype", "bono");

				} else {
					Console.println("No tiene bonos asociados.");
				}
			}

			if(ingresado.get("ingresado")!=null)
				dineroParaLiquidar.add(ingresado);
		}
		cash.liquidarFactura(id_factura, dineroParaLiquidar);
		Console.print("Ha efectuado el pago de su factura.");
	}
}