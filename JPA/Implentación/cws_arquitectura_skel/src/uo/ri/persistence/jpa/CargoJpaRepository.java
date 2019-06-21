package uo.ri.persistence.jpa;

import uo.ri.business.repository.CargoRepository;
import uo.ri.model.Cargo;
import uo.ri.persistence.jpa.util.BaseRepository;

/**
 * CargoJpaRepository. Extiende de BaseRepository e implementa CargoRepository.
 * De tal manera, ya tiene como el add, remove, findAll y el findById.
 * 
 * @author Adán
 *
 */
public class CargoJpaRepository extends BaseRepository<Cargo> implements
		CargoRepository {

}
