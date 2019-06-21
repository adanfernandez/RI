package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Averia;

public interface AveriaRepository extends Repository<Averia> {

	/**
	 * Encontrar averías pasando sus id en una lista
	 * 
	 * @param idsAveria
	 *            id's de las averías
	 * @return
	 */
	List<Averia> findByIds(List<Long> idsAveria);

	/**
	 * Encontrar las averías no facturadas de un cliente pasado por parámetro
	 * (DNI). No está implementado
	 */
	List<Averia> findNoFacturadasByDni(String dni);

	/**
	 * Devuelve una lista de averías no usadas para bono de un cliente pasado
	 * por parámetro (id)
	 */
	List<Averia> findWithUnusedBono3ByClienteId(Long id);
}