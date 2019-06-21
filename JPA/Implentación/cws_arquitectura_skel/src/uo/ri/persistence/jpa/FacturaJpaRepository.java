package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.FacturaRepository;
import uo.ri.model.Factura;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

/**
 * Clase FacturaJpaRepository. Implementa de FacturaRepository y extiende de
 * BaseRepository. De tal manera, tendrá que implementar unos métodos así como
 * algunos que ya tiene como el add, remove, findAll y el findById.
 * 
 * @author Adán
 *
 */
public class FacturaJpaRepository extends BaseRepository<Factura> implements
		FacturaRepository {

	/**
	 * Encontrar una factura pasándole su número por parámetro
	 */
	@Override
	public Factura findByNumber(Long numero) {
		return Jpa.getManager()
				.createNamedQuery( "Factura.findByNumber", Factura.class )
				.setParameter( 1, numero ).getResultList().stream().findFirst()
				.orElse( null );
	}

	/**
	 * Encontrar el siguiente número de una factura.
	 */
	@Override
	public Long getNextInvoiceNumber() {
		return Jpa.getManager()
				.createNamedQuery( "Factura.getNextInvoiceNumber", Long.class )
				.getSingleResult();
	}

	/**
	 * Encontrar facturas en estado ABONADA no usadas para bono.
	 */
	@Override
	public List<Factura> findUnusedWithBono500() {
		return Jpa
				.getManager()
				.createNamedQuery( "Factura.findUnusedWithBono500",
						Factura.class ).getResultList();
	}

}
