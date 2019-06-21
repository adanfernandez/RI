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
	 * Registrar que una avería ya ha sido usada para generar un bono
	 * 
	 * @param id_averia
	 *            avería usada para generar un bono que será desactivada
	 */
	public void desactivarAverias(Long id_averia);
	
	/**
	 * Obtener averías de un cliente dado
	 * @param id_cliente identificador del cliente
	 * @return Lista de mapas que representar a las averías
	 */
	public List<Map<String, Object>> obtenerAverias(Long id_cliente);
	
	/**
	 * Clientes que tienen facturas con más de 500€ no usadas para la obtención
	 * de bonos.
	 * 
	 * @return Lista de Map con los datos relativos a los clientes.
	 */
	public List<Map<String, Object>> verClientesFacturas500();

	/**
	 * Clientes que tienen más de 3 averías no usadas para la obtención de
	 * bonos.
	 * 
	 * @return Lista de Map con los datos relativos a los clientes.
	 */
	public List<Map<String, Object>> verClientesParaBonos();

	/**
	 * ID donde insertar los bonos.
	 * 
	 * @return el identificador del próximo bono que se añada
	 */
	public Long obtenerIdBono();

	/**
	 * Código donde insertar los bonos
	 * @return el código del próximo bono que se añada
	 */
	public String obtenerCodigoBono();

	/**
	 * añadir bonos a un cliente dado especificando su código
	 * 
	 * @param id_bono
	 *            identificador del bono
	 * @param id_cliente
	 *            identificador del cliente
	 * @param codigo
	 *            código del bono
	 */
	public void añadirBonosAverias(Long id_bono, Long cliente_id, String codigo);

	/**
	 * Establecer conexión con la base de datos
	 * 
	 * @param c conexión
	 */
	public void setConnection(Connection c);

	/**
	 * añadir bonos a un cliente dado especificando su código por las facturas
	 * 
	 * @param id_bono
	 *            identificador del bono
	 * @param id_cliente
	 *            identificador del cliente
	 * @param codigo
	 *            código del bono
	 */
	public void añadirBonosFacturas(Long id_bono, Long long1, String codigo);

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
	 * generación de bonos
	 * 
	 * @param factura_id
	 *            identificador de la factura
	 */
	public void desactivarFacturas(Long long1);
}
