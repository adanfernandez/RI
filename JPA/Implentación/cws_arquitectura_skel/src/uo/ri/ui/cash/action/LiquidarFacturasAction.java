package uo.ri.ui.cash.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uo.ri.business.CashService;
import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.CashDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.conf.Factory;
import alb.util.console.Console;
import alb.util.menu.Action;

public class LiquidarFacturasAction implements Action {

	/**
	 * Liquidación de factura. Se introducirá el número de esta y se le
	 * mostrarán al cliente los medios de pago que dispone para efectuar su
	 * pago. Una vez la cantidad introducida tenga una diferencia con el importe
	 * de la factura de 0.01 o el importe de ingresado sea mayor que el de la
	 * factura, saldrá del bucle y se harán las comprobaciones pertinentes.
	 * 
	 */
	@Override
	public void execute() throws Exception {

		InvoiceDto factura = new InvoiceDto();

		Long num = Console.readLong( "numero de la factura " );

		CashService cs = Factory.service.forCash();

		factura = cs.findInvoiceByNumber( num );

		if (factura == null) {
			System.out.println( "La factura no existe" );
			return;
		}

		List<PaymentMeanDto> mediosPago = cs
				.findPaymentMeansForInvoice( factura.id );

		double dineroIngresado = 0.0;
		Map<Long, Double> cargosParametro = new HashMap<Long, Double>();

		while (dineroIngresado < factura.total) {
			Console.println( "Total a pagar " + factura.total );
			Console.println( "dinero ingresado " + dineroIngresado );
			Console.println( "Medios de pago " );
			for (int i = 0; i < mediosPago.size(); i++) {
				if (mediosPago.get( i ) instanceof VoucherDto) {
					Console.println( i + 1 + ") Bono: Dinero disponible "
							+ ( (VoucherDto) mediosPago.get( i ) ).available );
				}
				if (mediosPago.get( i ) instanceof CardDto) {
					Console.println( i + 1 + ") Tarjeta de crédito: número "
							+ ( (CardDto) mediosPago.get( i ) ).cardNumber );
				}
				if (mediosPago.get( i ) instanceof CashDto) {
					Console.println( i + 1 + ") Metálico" );
				}
			}

			int eleccion = Console.readInt( "Elija uno (introduzca numero) " );
			double cantidad = Console.readDouble( "Cantidad a ingresar" );
			dineroIngresado += cantidad;
			mediosPago.get( eleccion - 1 ).accumulated += dineroIngresado;
			if (mediosPago.get( eleccion - 1 ) instanceof VoucherDto)
				( (VoucherDto) mediosPago.get( eleccion - 1 ) ).available -= dineroIngresado;

			cargosParametro.put( mediosPago.get( eleccion - 1 ).id, cantidad );
		}
		cs.settleInvoice( factura.id, cargosParametro );
		Console.print( "La factura ha sido abonada." );
	}
}
