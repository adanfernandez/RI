package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Factura;

public interface FacturaRepository extends Repository<Factura> {

	/**
	 * Encontrar una factura pasándole su número por parámetro
	 * 
	 * @param numero
	 *            numero de la factura
	 * @return factura
	 */
	Factura findByNumber(Long numero);

	/**
	 * Encontrar el siguiente número de una factura.
	 * 
	 * @return numero nuevo de la factura
	 */
	Long getNextInvoiceNumber();

	/**
	 * Encontrar facturas en estado ABONADA no usadas para bono.
	 * 
	 * @return facturas no usadas y abonadas
	 */
	List<Factura> findUnusedWithBono500();
}
