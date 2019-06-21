package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Cliente;

public interface ClienteRepository extends Repository<Cliente> {

	/**
	 * Encontrar cliente habiendo pasado por parámetro su DNI. No está
	 * implementado
	 * 
	 * @param dni
	 *            dni del cliente
	 * @return cliente buscado
	 */
	Cliente findByDni(String dni);

	/**
	 * Encontrar los lciente que tienen recomendaciones hechas. No está
	 * implementado
	 * 
	 * @return lista con los clientes
	 */
	List<Cliente> findWithRecomendations();

	/**
	 * Encontrar los clientes que tienen tres averias no usadas para bono
	 * 
	 * @return lista de los clientes
	 */
	List<Cliente> findWithThreeUnusedBreakdowns();

	/**
	 * Encontrar el recomendador de un cliente pasando el id de este por
	 * parámetro. No está implementado
	 * 
	 * @param id
	 *            identificador del recomendao
	 * @return recomendador
	 */
	List<Cliente> findRecomendedBy(Long id);

}
