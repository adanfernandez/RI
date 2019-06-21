package uo.ri.persistence.jpa;

import uo.ri.business.repository.MecanicoRepository;
import uo.ri.model.Mecanico;
import uo.ri.persistence.jpa.util.BaseRepository;

/**
 * MechanicJpaRepository. Implementa de MecanicoRepository y extiende de
 * BaseRepository. De tal manera, tendrá que implementar unos métodos así como
 * algunos que ya tiene como el add, remove, findAll y el findById.
 * 
 * @author Adán
 *
 */
public class MechanicJpaRepository extends BaseRepository<Mecanico> implements
		MecanicoRepository {

}
