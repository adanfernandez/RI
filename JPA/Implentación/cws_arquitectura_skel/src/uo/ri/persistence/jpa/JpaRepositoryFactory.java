package uo.ri.persistence.jpa;

import uo.ri.business.repository.AveriaRepository;
import uo.ri.business.repository.CargoRepository;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.business.repository.RecomendacionRepository;
import uo.ri.business.repository.RepositoryFactory;
import uo.ri.business.repository.RepuestoRepository;
import uo.ri.business.repository.VehiculoRepository;

/**
 * Clase JpaRepositoryFactory. Devuelve una instancia de cada repositorio
 * implementando a la interfaz RepositoryFactory.
 * 
 * @author Adán
 *
 */
public class JpaRepositoryFactory implements RepositoryFactory {

	/**
	 * Devuelve una instancia de MecanicoRepository.
	 */
	@Override
	public MecanicoRepository forMechanic() {
		return new MechanicJpaRepository();
	}

	/**
	 * Devuelve una instancia de AveriaRepository
	 */
	@Override
	public AveriaRepository forAveria() {
		return new AveriaJpaRepository();
	}

	/**
	 * Devuelve una instancia de MedioPagoRepository
	 */
	@Override
	public MedioPagoRepository forMedioPago() {
		return new MedioPagoJpaRepository();
	}

	/**
	 * Devuelve una instancia de FacturaRepository
	 */
	@Override
	public FacturaRepository forFactura() {
		return new FacturaJpaRepository();
	}

	/**
	 * Devuelve una instancia de ClienteRepository
	 */
	@Override
	public ClienteRepository forCliente() {
		return new ClienteJpaRepository();
	}

	/**
	 * Devuelve una instancia de RepuestoRepository
	 */
	@Override
	public RepuestoRepository forRepuesto() {
		return new RepuestoJpaRepository();
	}

	/**
	 * Devuelve una instancia de RecomendacionRepository. No está implementado.
	 */
	@Override
	public RecomendacionRepository forRecomendacion() {
		return null;
	}

	/**
	 * Devuelve una instancia de CargoRepository
	 */
	@Override
	public CargoRepository forCargo() {
		return new CargoJpaRepository();
	}

	/**
	 * Devuelve una instancia de VehiculoRepository. No está implementado
	 */
	@Override
	public VehiculoRepository forVehiculo() {
		return null;
	}

}
