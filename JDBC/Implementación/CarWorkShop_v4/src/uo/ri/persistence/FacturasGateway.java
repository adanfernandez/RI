package uo.ri.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FacturasGateway {

	/**
	 * Obtener la caducidad de una tarjeta pasada por parámetro
	 * 
	 * @param numero
	 *            número de la tarjeta
	 * @return Date fecha de caducidad de la tarjeta
	 * @throws SQLException
	 */
	public Date caducidad(String numeroTarjeta);

	/**
	 * Obtener el cliente de una factura pasada por parámetro
	 * @param id identificador de la factura de la que se quiere obtener la información
	 * @return id_cliente Long del cliente
	 * @throws SQLException
	 */
	public Long obtenerClienteDeUnaFactura(Long id_factura);

	/**
	 * Liquidar una factura pasada por parámetro
	 * @param id identificador de la factura que se quiere actualizar
	 */
	public void liquidarFactura(Long id_factura);

	/**
	 * Efectuar un pago con bono
	 * @param dineroIngresado dinero a ingresar 
	 * @param id_bono Identificador del bono con el que pagar
	 */
	public abstract void actualizarAcumuladoYDisponibleBonos(double dineroIngresado,
			Long id_bono);

	/**
	 * Acumulado y disponible hasta el momento en los bonos 
	 * @param id_bono Identificador del bono con el que pagar
	 * @return Map con los datos referentes al bono
	 */
	public Map<String, Object> acumuladoHastaElMomentoAcumuladoYDisponibleBono(Long id_bono);

	/**
	 * Establecer conexión con la Base de Datos
	 * @param c Conexión
	 */
	public void setConnection(Connection c);

	/**
	 * Efectuar un pago en metálico
	 * @param dineroIngresado dinero a ingresar 
	 * @param id_cliente Identificador del cliente que introduce el dinero
	 */
	public void actualizarMetalico(double dineroIngresado, Long id_cliente);

	/**
	 * Efectuar un pago con tarjeta
	 * @param dineroIngresado dinero a ingresar 
	 * @param numeroTarjeta número de tarjeta a la que introducir el dinero
	 */
	public void pagarConTarjeta(double dineroIngresado, String numeroTarjeta);

	/**
	 * Obtener el precio de una factura pasada por parámetro
	 * @param id identificador de la factura de la que se quiere obtener la información
	 * @return factura Map con datos sobre el precio y el status de la factura.
	 * @throws SQLException
	 */
	public Map<String, Object> getPrecioFactura(Long id_factura);
	
	/**
	 * Tarjetas de un cliente pasado por parámetro
	 * @param cliente_id Identificador del cliente 
	 * @return Lista de Map que contienen información sobre la tarjeta
	 */
	public List<Map<String, Object>> tarjetasCliente(Long cliente_id);
}
