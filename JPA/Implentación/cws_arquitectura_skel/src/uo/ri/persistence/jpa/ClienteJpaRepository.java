package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.ClienteRepository;
import uo.ri.model.Cliente;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

/**
 * ClienteJpaRepository. Implementa de ClienteRepository y extiende de
 * BaseRepository. De tal manera, tendrá que implementar unos métodos así como
 * algunos que ya tiene como el add, remove, findAll y el findById.
 * 
 * @author Adán
 *
 */
public class ClienteJpaRepository extends BaseRepository<Cliente> implements
		ClienteRepository {

	/**
	 * Encontrar cliente habiendo pasado por parámetro su DNI. No está
	 * implementado
	 */
	@Override
	public Cliente findByDni(String dni) {
		return null;
	}

	/**
	 * Encontrar los lciente que tienen recomendaciones hechas. No está
	 * implementado
	 */
	@Override
	public List<Cliente> findWithRecomendations() {
		return null;
	}

	/**
	 * Encontrar los clientes que tienen tres averias no usadas para bono
	 */
	@Override
	public List<Cliente> findWithThreeUnusedBreakdowns() {
		return Jpa
				.getManager()
				.createNamedQuery( "Cliente.findWithThreeUnusedBreakdowns",
						Cliente.class ).getResultList();
	}

	/**
	 * Encontrar el recomendador de un cliente pasando el id de este por
	 * parámetro. No está implementado
	 */
	@Override
	public List<Cliente> findRecomendedBy(Long id) {
		return null;
	}

}
