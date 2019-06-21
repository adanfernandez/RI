package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import uo.ri.common.BusinessException;

public interface BonosGateway {
	
	/**
	 * Ver bonos de un cliente dado por su id
	 */
	public List<Map<String, Object>> verBonosDeUnCliente(Long id_cliente);

	/**
	 * Listar todos los bonos registrados en el taller.
	 */
	public List<Map<String, Object>> listarTodosBonos()
			throws BusinessException;

	/**
	 * Registrar que una aver�a ya ha sido usada para generar un bono
	 * 
	 * @param id_averia
	 *            aver�a usada para generar un bono que ser� desactivada
	 */
	public void desactivarAverias(Long id_averia);
	
	/**
	 * Obtener aver�as de un cliente dado
	 * @param id_cliente identificador del cliente
	 * @return Lista de mapas que representar a las aver�as
	 */
	public List<Map<String, Object>> obtenerAverias(Long id_cliente);
	
	/**
	 * Clientes que tienen facturas con m�s de 500� no usadas para la obtenci�n
	 * de bonos.
	 * 
	 * @return Lista de Map con los datos relativos a los clientes.
	 */
	public List<Map<String, Object>> verClientesFacturas500();

	/**
	 * Clientes que tienen m�s de 3 aver�as no usadas para la obtenci�n de
	 * bonos.
	 * 
	 * @return Lista de Map con los datos relativos a los clientes.
	 */
	public List<Map<String, Object>> verClientesParaBonos();

	/**
	 * ID donde insertar los bonos.
	 * 
	 * @return el identificador del pr�ximo bono que se a�ada
	 */
	public Long obtenerIdBono();

	/**
	 * C�digo donde insertar los bonos
	 * @return el c�digo del pr�ximo bono que se a�ada
	 */
	public String obtenerCodigoBono();

	/**
	 * a�adir bonos a un cliente dado especificando su c�digo
	 * 
	 * @param id_bono
	 *            identificador del bono
	 * @param id_cliente
	 *            identificador del cliente
	 * @param codigo
	 *            c�digo del bono
	 */
	public void a�adirBonosAverias(Long id_bono, Long cliente_id, String codigo);

	/**
	 * Establecer conexi�n con la base de datos
	 * 
	 * @param c conexi�n
	 */
	public void setConnection(Connection c);

	/**
	 * a�adir bonos a un cliente dado especificando su c�digo por las facturas
	 * 
	 * @param id_bono
	 *            identificador del bono
	 * @param id_cliente
	 *            identificador del cliente
	 * @param codigo
	 *            c�digo del bono
	 */
	public void a�adirBonosFacturas(Long id_bono, Long long1, String codigo);

	/**
	 * Obtener el acumulado y el disponible de un bono especificado.
	 * 
	 * @param id_bono
	 *            identificador del bono
	 * @param bonos lista de Map con los diferentes datos.
	 */
	public List<Map<String, Object>> verBono(Long id_bono);
	
	
	public List<Map<String, Object>> obtenerFacturas(Long id_cliente);

	/**
	 * Desactivar las facturas para que no puedan volver a ser usadas para la
	 * generaci�n de bonos
	 * 
	 * @param factura_id
	 *            identificador de la factura
	 */
	public void desactivarFacturas(Long long1);
}
