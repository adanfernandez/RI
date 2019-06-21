package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.AveriaRepository;
import uo.ri.model.Averia;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

/**
 * AveriaJpaRepository. Implementa de AveriaRepository y extiende de
 * BaseRepository. De tal manera, tendrá que implementar unos métodos así como
 * algunos que ya tiene como el add, remove, findAll y el findById.
 * 
 * @author Adán
 *
 */
public class AveriaJpaRepository extends BaseRepository<Averia> implements
		AveriaRepository {

	/**
	 * Encontrar una lista de averías equivalente a la lista que se le pasa, la
	 * cual contiene sus identificadores. No está implementado
	 */
	@Override
	public List<Averia> findByIds(List<Long> idsAveria) {
		return null;
	}

	/**
	 * Encontrar las averías no facturadas de un cliente pasado por parámetro
	 * (DNI). No está implementado
	 */
	@Override
	public List<Averia> findNoFacturadasByDni(String dni) {
		return null;
	}

	/**
	 * Devuelve una lista de averías no usadas para bono de un cliente pasado
	 * por parámetro (id)
	 */
	@Override
	public List<Averia> findWithUnusedBono3ByClienteId(Long id) {
		return Jpa
				.getManager()
				.createNamedQuery( "Averia.findWithUnusedBono3ByClienteId",
						Averia.class ).setParameter( 1, id ).getResultList();
	}

}
