package uo.ri.persistence.jpa;

import uo.ri.business.repository.RepuestoRepository;
import uo.ri.model.Repuesto;
import uo.ri.persistence.jpa.util.BaseRepository;

/**
 * RepuestoJpaRepository. Implementa de AveriaRepository y extiende de
 * BaseRepository. De tal manera, tiene métodos como el add, remove, findAll y
 * el findById.
 * 
 * @author Adán
 *
 */
public class RepuestoJpaRepository extends BaseRepository<Repuesto> implements
		RepuestoRepository {

}
