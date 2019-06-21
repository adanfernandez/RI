package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.model.Bono;
import uo.ri.model.MedioPago;
import uo.ri.model.TarjetaCredito;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

/**
 * MedioPagoJpaRepository. Implementa de MedioPagoRepository y extiende de
 * BaseRepository. De tal manera, tendrá que implementar unos métodos así como
 * algunos que ya tiene como el add, remove, findAll y el findById.
 * 
 * @author Adán
 *
 */
public class MedioPagoJpaRepository extends BaseRepository<MedioPago> implements
		MedioPagoRepository {

	/**
	 * Encontrar los medios de pago de un cliente pasándole el id de este
	 */
	@Override
	public List<MedioPago> findPaymentMeansByClientId(Long id) {
		return Jpa
				.getManager()
				.createNamedQuery( "MedioPago.findPaymentMeansByClientId",
						MedioPago.class ).setParameter( 1, id ).getResultList();
	}

	/**
	 * Encontrar los medios de pago de un cliente pasándole una factura que
	 * pertenece a este
	 */
	@Override
	public List<MedioPago> findPaymentMeansByInvoiceId(Long idFactura) {
		return Jpa
				.getManager()
				.createNamedQuery( "MedioPago.findByInvoiceId", MedioPago.class )
				.setParameter( 1, idFactura ).getResultList();
	}

	/**
	 * Encontrar los medios de pago de un cliente pasándole el id de este
	 */
	@Override
	public List<MedioPago> findByClientId(Long id) {
		return Jpa
				.getManager()
				.createNamedQuery( "MedioPago.findPaymentMeansByClientId",
						MedioPago.class ).setParameter( 1, id ).getResultList();
	}

	/**
	 * Devuelve un Object[] con tres valores - Object[0] entero con el número de
	 * bonos que posee el cliente - Object[1] double con la cantidad disponible
	 * de un cliente en bonos - Object[2] double con la cantidad consumidad de
	 * un cliente en bonos
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Object[] findAggregateVoucherDataByClientId(Long id) {
		return (Object[]) Jpa
				.getManager()
				.createNamedQuery(
						"MedioPago.findAggregateVoucherDataByClientId",
						Object.class ).setParameter( 1, id ).getSingleResult();
	}

	/**
	 * Encontrar tarjeta de crédito pasándole su número. No implementado
	 */
	@Override
	public TarjetaCredito findCreditCardByNumber(String pan) {
		return null;
	}

	/**
	 * Encontrar bonos pasándole su id
	 */
	@Override
	public List<Bono> findVouchersByClientId(Long id) {
		return Jpa
				.getManager()
				.createNamedQuery( "MedioPago.findVouchersByClientId",
						Bono.class ).setParameter( 1, id ).getResultList();
	}

	/**
	 * Encontrar un bono pasándole su id. No implementado
	 */
	@Override
	public Bono findVoucherByCode(String code) {
		return null;
	}
}
