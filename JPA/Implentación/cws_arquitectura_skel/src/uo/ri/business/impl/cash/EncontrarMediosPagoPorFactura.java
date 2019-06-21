package uo.ri.business.impl.cash;

import java.util.ArrayList;
import java.util.List;

import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.CashDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Bono;
import uo.ri.model.MedioPago;
import uo.ri.model.Metalico;
import uo.ri.model.TarjetaCredito;
import uo.ri.util.exception.BusinessException;

/**
 * Clase EncontrarMediosPagoPorFactura. Implementa a la interfaz Command. Posee
 * el metodo execute() correspondiente a la interfaz. Se añadirán todos los
 * medios de pago que posee un cliente que se dispone a efectuar el pago de una
 * factura.
 * 
 * @author Adán
 *
 */
public class EncontrarMediosPagoPorFactura implements
		Command<List<PaymentMeanDto>> {

	Long id;
	MedioPagoRepository medioPagoRepository = Factory.repository.forMedioPago();

	/**
	 * Constructor de EncontrarMediosPagoPorFactura con un parámetro que
	 * representa el identificador de la factura (Long)
	 * 
	 * @param id
	 *            identificador de la factura (Long)
	 */
	public EncontrarMediosPagoPorFactura(Long id) {
		this.id = id;
	}

	/**
	 * Método execute(). Devolverá una lista de medios de pago (DTO). Estos se
	 * corresponderán a los medios de pago que posee el cliente que se dispone a
	 * efectuar el pago de la factura.
	 */
	@Override
	public List<PaymentMeanDto> execute() throws BusinessException {
		List<PaymentMeanDto> mediosPagoDto = new ArrayList<PaymentMeanDto>();
		List<MedioPago> mediosPagoLogica = medioPagoRepository
				.findPaymentMeansByInvoiceId( id );
		for (MedioPago mp : mediosPagoLogica) {
			if (mp instanceof Bono) {
				VoucherDto mepa = new VoucherDto();
				mepa.accumulated = mp.getAcumulado();
				mepa.clientId = mp.getCliente().getId();
				mepa.tipo = mp.getDtype();
				mepa.available = ( (Bono) mp ).getDisponible();
				mepa.code = ( (Bono) mp ).getCodigo();
				mepa.description = ( (Bono) mp ).getCodigo();
				mepa.id = mp.getId();
				mediosPagoDto.add( mepa );
			}

			else if (mp instanceof TarjetaCredito) {
				CardDto mepa = new CardDto();
				mepa.accumulated = mp.getAcumulado();
				mepa.clientId = mp.getCliente().getId();
				mepa.tipo = mp.getDtype();
				mepa.id = mp.getId();
				mepa.cardExpiration = ( (TarjetaCredito) mp ).getValidez();
				mepa.cardNumber = ( (TarjetaCredito) mp ).getNumero();
				mepa.cardType = ( (TarjetaCredito) mp ).getTipo();
				mediosPagoDto.add( mepa );
			}

			else if (mp instanceof Metalico) {
				CashDto mepa = new CashDto();
				mepa.accumulated = mp.getAcumulado();
				mepa.clientId = mp.getCliente().getId();
				mepa.tipo = mp.getDtype();
				mepa.id = mp.getId();
				mediosPagoDto.add( mepa );
			}
		}

		return mediosPagoDto;
	}

}
