package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Bono;
import uo.ri.model.MedioPago;
import uo.ri.model.TarjetaCredito;

public interface MedioPagoRepository extends Repository<MedioPago> {

	/**
	 * Encontrar los medios de pago de un cliente pasándole el id de este
	 * 
	 * @param id
	 *            identificador del cliente
	 * @return medios de pago del cliente
	 */
	List<MedioPago> findPaymentMeansByClientId(Long id);

	/**
	 * Medios de pago disponibles para pagar una factura
	 * 
	 * @param idFactura
	 *            identificador de la factura
	 * @return lista con los medios de pago
	 */
	List<MedioPago> findPaymentMeansByInvoiceId(Long idFactura);

	/**
	 * Medios de pago que posee un cliente
	 * 
	 * @param id
	 *            identificador del cliente
	 * @return lista con los medios de pago
	 */
	List<MedioPago> findByClientId(Long id);

	/**
	 * Returns an Object[] with three values - Object[0] an integer with the
	 * number of vouchers the client has - Object[1] a double with the total
	 * amount available in all the client's vouchers - Object[2] a double with
	 * the amount already consumed
	 * 
	 * @param id
	 * @return
	 */
	Object[] findAggregateVoucherDataByClientId(Long id);

	/**
	 * Encontrar una tarjeta de credito por su numero
	 * 
	 * @param pan
	 *            numero de la tarjeta de credito
	 * @return tarjeta de credito buscada
	 */
	TarjetaCredito findCreditCardByNumber(String pan);

	/**
	 * Encontrar bonos de un cliente en concreto
	 * 
	 * @param id
	 *            identificador del cliente
	 * @return lista con los bonos
	 */
	List<Bono> findVouchersByClientId(Long id);

	/**
	 * Encontrar los bonos que tengan un código específico
	 * 
	 * @param code
	 *            código del bono
	 * @return bono buscado
	 */
	Bono findVoucherByCode(String code);
}
