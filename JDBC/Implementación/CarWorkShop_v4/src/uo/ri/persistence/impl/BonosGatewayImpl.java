package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uo.ri.conf.Conf;
import uo.ri.persistence.BonosGateway;
import alb.util.jdbc.Jdbc;

public class BonosGatewayImpl implements BonosGateway {

	public String SQL_OBTENER_BONOS_CLIENTE = Conf
			.get("SQL_OBTENER_BONOS_CLIENTE");
	public String SQL_OBTENER_TODOS_BONOS = Conf.get("SQL_OBTENER_TODOS_BONOS");
	public String SQL_OBTENER_CLIENTES_TRES_AVERIAS = Conf
			.get("SQL_OBTENER_CLIENTES_TRES_AVERIAS");
	public String SQL_CLIENTES_FACTURAS_500 = Conf
			.get("SQL_CLIENTES_FACTURAS_500");
	public String SQL_MAX_CODIGO = Conf.get("SQL_MAX_CODIGO");

	public String SQL_OBTENER_BONO_POR_ID = Conf.get("SQL_OBTENER_BONO_POR_ID");

	public String SQL_MAX_BONOS = Conf.get("SQL_MAX_BONOS");
	public String SQL_AÑADIR_BONOS_AVERIAS = Conf
			.get("SQL_AÑADIR_BONOS_AVERIAS");
	public String SQL_AÑADIR_BONOS_FACTURA = Conf
			.get("SQL_AÑADIR_BONOS_FACTURA");
	public String SQL_OBTENER_AVERIAS = Conf.get("SQL_OBTENER_AVERIAS");
	public String SQL_DESACTIVAR_AVERIAS = Conf.get("SQL_DESACTIVAR_AVERIAS");
	public String SQL_OBTENER_FACTURAS = Conf.get("SQL_OBTENER_FACTURAS");
	private String SQL_DESACTIVAR_FACTURAS = Conf
			.get("SQL_DESACTIVAR_FACTURAS");

	Connection c = null;

	/**
	 * Ver bonos de un cliente dado por su id
	 */
	@Override
	public List<Map<String, Object>> verBonosDeUnCliente(Long id_cliente) {

		List<Map<String, Object>> bonos = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_OBTENER_BONOS_CLIENTE);
			pst.setLong(1, id_cliente);
			rs = pst.executeQuery();
			while (rs.next()) {

				Map<String, Object> bono = new HashMap<>();

				bono.put("id_bono", rs.getLong(1));
				bono.put("acumulado", rs.getDouble(2));
				bono.put("disponible", rs.getDouble(3));
				bono.put("codigo", rs.getString(4));
				bono.put("descripcion", rs.getString(5));

				bonos.add(bono);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

		return bonos;

	}

	/**
	 * Listar todos los bonos registrados en el taller.
	 */
	@Override
	public List<Map<String, Object>> listarTodosBonos()
	{

		List<Map<String, Object>> bonos = new ArrayList<>();

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(SQL_OBTENER_TODOS_BONOS);
			rs = pst.executeQuery();
			while (rs.next()) {

				Map<String, Object> bono = new HashMap<>();

				bono.put("dni", rs.getString(1));
				bono.put("nombre", rs.getString(2));
				bono.put("bonos", rs.getString(3));
				bono.put("total", rs.getDouble(4));
				bono.put("acumulado", rs.getDouble(5));
				bono.put("disponible", rs.getDouble(6));

				bonos.add(bono);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

		return bonos;
	}

	/**
	 * Registrar que una avería ya ha sido usada para generar un bono
	 * 
	 * @param id_averia
	 *            avería usada para generar un bono que será desactivada
	 */
	@Override
	public void desactivarAverias(Long id_averia) {

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_DESACTIVAR_AVERIAS);
			pst.setLong(1, id_averia);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/**
	 * Obtener averías de un cliente dado
	 * 
	 * @param id_cliente
	 *            identificador del cliente
	 * @return Lista de mapas que representar a las averías
	 */
	@Override
	public List<Map<String, Object>> obtenerAverias(Long id_cliente) {

		List<Map<String, Object>> averias = new ArrayList<>();

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_OBTENER_AVERIAS);
			pst.setLong(1, id_cliente);
			rs = pst.executeQuery();
			while (rs.next()) {

				Map<String, Object> averia = new HashMap<>();

				averia.put("id_averia", rs.getLong(1));
				averias.add(averia);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

		return averias;

	}

	/**
	 * añadir bonos a un cliente dado especificando su código por sus averías
	 * 
	 * @param id_bono
	 *            identificador del bono
	 * @param id_cliente
	 *            identificador del cliente
	 * @param codigo
	 *            código del bono
	 */
	@Override
	public void añadirBonosAverias(Long id_bono, Long cliente_id, String codigo) {

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_AÑADIR_BONOS_AVERIAS);
			pst.setLong(1, id_bono);
			pst.setLong(2, cliente_id);
			pst.setString(3, codigo);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	/**
	 * ID donde insertar los bonos.
	 * 
	 * @return el identificador del próximo bono que se añada
	 */
	@Override
	public Long obtenerIdBono(){
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(SQL_MAX_BONOS);
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getLong(1) + 1;
			} else {
				return 1L;
			}
		}catch(SQLException e)
		{
			throw new RuntimeException();
		}
		finally {
			Jdbc.close(rs, pst);
		}
	}

	/**
	 * Código donde insertar los bonos
	 * 
	 * @return el código del próximo bono que se añada
	 */
	@Override
	public String obtenerCodigoBono(){
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(SQL_MAX_CODIGO);
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getString(1);
			} else {
				return null;
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException();
		}
		finally {
			Jdbc.close(rs, pst);
		}
	}

	/**
	 * Clientes que tienen más de 3 averías no usadas para la obtención de
	 * bonos.
	 * 
	 * @return Lista de Map con los datos relativos a los clientes.
	 */
	@Override
	public List<Map<String, Object>> verClientesParaBonos() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Map<String, Object>> clientes = new ArrayList<>();
		try {

			pst = c.prepareStatement(SQL_OBTENER_CLIENTES_TRES_AVERIAS);
			rs = pst.executeQuery();
			while (rs.next()) {

				Map<String, Object> cliente = new HashMap<>();

				cliente.put("id_cliente", rs.getLong(1));
				cliente.put("averias", rs.getInt(2));
				clientes.add(cliente);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

		return clientes;
	}

	/**
	 * Clientes que tienen facturas con más de 500€ no usadas para la obtención
	 * de bonos.
	 * 
	 * @return Lista de Map con los datos relativos a los clientes.
	 */
	@Override
	public List<Map<String, Object>> verClientesFacturas500() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Map<String, Object>> clientes = new ArrayList<>();
		try {

			pst = c.prepareStatement(SQL_CLIENTES_FACTURAS_500);
			rs = pst.executeQuery();
			while (rs.next()) {

				Map<String, Object> cliente = new HashMap<>();

				cliente.put("id_cliente", rs.getLong(1));
				cliente.put("NumeroFacturas", rs.getInt(2));
				clientes.add(cliente);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

		return clientes;
	}

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
	@Override
	public void añadirBonosFacturas(Long id_bono, Long cliente_id, String codigo) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_AÑADIR_BONOS_FACTURA);
			pst.setLong(1, id_bono);
			pst.setLong(2, cliente_id);
			pst.setString(3, codigo);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/**
	 * Obtener el acumulado y el disponible de un bono especificado.
	 * 
	 * @param id_bono
	 *            identificador del bono
	 * @param bonos
	 *            lista de Map con los diferentes datos.
	 */
	@Override
	public List<Map<String, Object>> verBono(Long id_bono) {

		List<Map<String, Object>> bonos = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_OBTENER_BONO_POR_ID);
			pst.setLong(1, id_bono);
			rs = pst.executeQuery();
			while (rs.next()) {

				Map<String, Object> bono = new HashMap<>();

				bono.put("acumulado", rs.getDouble(1));
				bono.put("disponible", rs.getDouble(2));

				bonos.add(bono);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

		return bonos;

	}

	/**
	 * Establecer conexión con la base de datos
	 * 
	 * @param c
	 *            conexión
	 */
	@Override
	public void setConnection(Connection c) {
		this.c = c;
	}

	/**
	 * Obtener las facturas de un cliente especificado por ID
	 * 
	 * @param id_cliente
	 *            identificador del cliente
	 * @return facturas lista de Map con los ids de las facturas
	 */
	@Override
	public List<Map<String, Object>> obtenerFacturas(Long id_cliente) {

		List<Map<String, Object>> facturas = new ArrayList<>();

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_OBTENER_FACTURAS);
			pst.setLong(1, id_cliente);
			rs = pst.executeQuery();
			while (rs.next()) {

				Map<String, Object> factura = new HashMap<>();

				factura.put("factura_id", rs.getLong(1));
				facturas.add(factura);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

		return facturas;

	}

	/**
	 * Desactivar las facturas para que no puedan volver a ser usadas para la
	 * generación de bonos
	 * 
	 * @param factura_id
	 *            identificador de la factura
	 */
	@Override
	public void desactivarFacturas(Long factura_id) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_DESACTIVAR_FACTURAS);
			pst.setLong(1, factura_id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

}