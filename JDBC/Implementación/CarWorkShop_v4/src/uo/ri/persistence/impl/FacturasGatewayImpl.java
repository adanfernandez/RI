package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uo.ri.conf.Conf;
import uo.ri.persistence.FacturasGateway;
import alb.util.jdbc.Jdbc;

public class FacturasGatewayImpl implements FacturasGateway {


	private static final String SQL_CLIENTE_DE_UNA_FACTURA = Conf
			.get("SQL_CLIENTE_DE_UNA_FACTURA");

	private static final String SQL_VALIDEZ_TARJETA = Conf
			.get("SQL_VALIDEZ_TARJETA");

	private static final String SQL_CAMBIAR_ESTADO_FACTURA = Conf
			.get("SQL_CAMBIAR_ESTADO_FACTURA");

	private static final String SQL_PRECIO_FACTURA = Conf
			.get("SQL_PRECIO_FACTURA");

	private static final String SQL_TARJETAS_CLIENTE = Conf
			.get("SQL_TARJETAS_CLIENTE");

	private static final String SQL_ACTUALIZAR_ACUMULADO_TARJETA = Conf
			.get("SQL_ACTUALIZAR_ACUMULADO_TARJETA");

	private static final String SQL_TOTAL_ACUMULADO_TARJETA = Conf
			.get("SQL_TOTAL_ACUMULADO_TARJETA");

	private static final String SQL_ACTUALIZAR_ACUMULADO_METALICO = Conf
			.get("SQL_ACTUALIZAR_ACUMULADO_METALICO");

	private static final String SQL_TOTAL_ACUMULADO_METALICO = Conf
			.get("SQL_TOTAL_ACUMULADO_METALICO");

	private static final String SQL_ACTUALIZAR_ACUMULADO_DISPONIBLE_BONO = Conf
			.get("SQL_ACTUALIZAR_ACUMULADO_DISPONIBLE_BONO");

	private static final String SQL_TOTAL_ACUMULADO_DISPONIBLE_BONO = Conf
			.get("SQL_TOTAL_ACUMULADO_DISPONIBLE_BONO");

	private Connection c;



	/**
	 * Obtener el precio de una factura pasada por parámetro
	 * 
	 * @param id
	 *            identificador de la factura de la que se quiere obtener la
	 *            información
	 * @return factura Map con datos sobre el precio y el status de la factura.
	 */
	@Override
	public Map<String, Object> getPrecioFactura(Long id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map<String, Object> factura = new HashMap<String, Object>();
		try {
			pst = c.prepareStatement(SQL_PRECIO_FACTURA);
			pst.setLong(1, id);
			rs = pst.executeQuery();

			while (rs.next()) {

				factura.put("precio", rs.getDouble(1));
				factura.put("status", rs.getString(2));
				factura.put("fecha", new Date(rs.getTimestamp(3).getTime()));

			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return factura;
	}

	/**
	 * Obtener la caducidad de una tarjeta pasada por parámetro
	 * 
	 * @param numero
	 *            número de la tarjeta
	 * @return Date fecha de caducidad de la tarjeta
	 */
	public Date caducidad(String numeroTarjeta) {

		PreparedStatement pst = null;
		ResultSet rs = null;
		Date fecha = null;
		try {
			pst = c.prepareStatement(SQL_VALIDEZ_TARJETA);
			pst.setString(1, numeroTarjeta);
			rs = pst.executeQuery();

			while (rs.next()) {
				fecha = rs.getDate(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

		return fecha;
	}

	/**
	 * Obtener el cliente de una factura pasada por parámetro
	 * 
	 * @param id
	 *            identificador de la factura de la que se quiere obtener la
	 *            información
	 * @return id_cliente Long del cliente
	 */
	@Override
	public Long obtenerClienteDeUnaFactura(Long id) {

		PreparedStatement pst = null;
		ResultSet rs = null;
		Long id_cliente = null;
		try {
			pst = c.prepareStatement(SQL_CLIENTE_DE_UNA_FACTURA);
			pst.setLong(1, id);
			rs = pst.executeQuery();

			while (rs.next()) {
				id_cliente = rs.getLong(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return id_cliente;
	}

	/**
	 * Liquidar una factura pasada por parámetro
	 * 
	 * @param id
	 *            identificador de la factura que se quiere actualizar
	 */
	public void liquidarFactura(Long id_factura) {

		PreparedStatement pst = null;
		ResultSet rs = null;
		try {

			pst = c.prepareStatement(SQL_CAMBIAR_ESTADO_FACTURA);
			pst.setLong(1, id_factura);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/**
	 * Tarjetas de un cliente pasado por parámetro
	 * 
	 * @param cliente_id
	 *            Identificador del cliente
	 * @return Lista de Map que contienen información sobre la tarjeta
	 */
	@Override
	public List<Map<String, Object>> tarjetasCliente(Long cliente_id) {
		List<Map<String, Object>> facturas = new ArrayList<>();

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_TARJETAS_CLIENTE);
			pst.setLong(1, cliente_id);
			rs = pst.executeQuery();
			while (rs.next()) {

				Map<String, Object> factura = new HashMap<>();

				factura.put("numeroTarjeta", rs.getLong(1));
				factura.put("tipo", rs.getString(2));
				factura.put("validez", new Date(rs.getTimestamp(3).getTime()));

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
	 * Efectuar un pago con tarjeta
	 * 
	 * @param dineroIngresado
	 *            dinero a ingresar
	 * @param numeroTarjeta
	 *            número de tarjeta a la que introducir el dinero
	 */
	@Override
	public void pagarConTarjeta(double dineroIngresado, String numeroTarjeta) {

		PreparedStatement pst = null;
		ResultSet rs = null;

		double acumulado = acumuladoHastaElMomentoTarjeta(numeroTarjeta);
		acumulado += dineroIngresado;
		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(SQL_ACTUALIZAR_ACUMULADO_TARJETA);
			pst.setDouble(1, acumulado);
			pst.setString(2, numeroTarjeta);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/**
	 * Acumulado en la tarjeta pasada por parámetro
	 * 
	 * @param numero
	 *            numero de la tarjeta
	 * @return acumulado total
	 */
	protected double acumuladoHastaElMomentoTarjeta(String numero) {

		PreparedStatement pst = null;
		ResultSet rs = null;
		double totalAcumulado = 0;
		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_TOTAL_ACUMULADO_TARJETA);
			pst.setString(1, numero);
			rs = pst.executeQuery();
			while (rs.next()) {

				totalAcumulado = rs.getDouble(1);

			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return totalAcumulado;
	}

	/**
	 * Efectuar un pago en metálico
	 * 
	 * @param dineroIngresado
	 *            dinero a ingresar
	 * @param id_cliente
	 *            Identificador del cliente que introduce el dinero
	 */
	@Override
	public void actualizarMetalico(double dineroIngresado, Long id_cliente) {

		PreparedStatement pst = null;
		ResultSet rs = null;

		double acumulado = acumuladoHastaElMomentoMetalico(id_cliente);
		acumulado += dineroIngresado;
		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(SQL_ACTUALIZAR_ACUMULADO_METALICO);
			pst.setDouble(1, acumulado);
			pst.setLong(2, id_cliente);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/**
	 * Acumulado hasta el momento en forma de metálico
	 * 
	 * @param id
	 *            ientificador del cliente
	 * @return totalAcumulado total del acumulado
	 */
	protected double acumuladoHastaElMomentoMetalico(Long id) {

		PreparedStatement pst = null;
		ResultSet rs = null;
		double totalAcumulado = 0;
		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(SQL_TOTAL_ACUMULADO_METALICO);
			pst.setLong(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				totalAcumulado = rs.getDouble(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return totalAcumulado;
	}

	/**
	 * Establecer conexión con la Base de Datos
	 * 
	 * @param c
	 *            Conexión
	 */
	public void setConnection(Connection c) {
		this.c = c;
	}

	/**
	 * Efectuar un pago con bono
	 * 
	 * @param dineroIngresado
	 *            dinero a ingresar
	 * @param id_bono
	 *            Identificador del bono con el que pagar
	 */
	@Override
	public void actualizarAcumuladoYDisponibleBonos(double dineroIngresado,
			Long id_bono) {

		PreparedStatement pst = null;
		ResultSet rs = null;

		Map<String, Object> dinero = acumuladoHastaElMomentoAcumuladoYDisponibleBono(id_bono);

		Double acumulado = (Double) dinero.get("acumulado");
		Double disponible = (Double) dinero.get("disponible");

		acumulado += dineroIngresado;
		disponible -= dineroIngresado;
		try {

			pst = c.prepareStatement(SQL_ACTUALIZAR_ACUMULADO_DISPONIBLE_BONO);
			pst.setDouble(1, acumulado);
			pst.setDouble(2, disponible);
			pst.setLong(3, id_bono);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	/**
	 * Acumulado y disponible hasta el momento en los bonos
	 * 
	 * @param id_bono
	 *            Identificador del bono con el que pagar
	 * @return Map con los datos referentes al bono
	 */
	@Override
	public Map<String, Object> acumuladoHastaElMomentoAcumuladoYDisponibleBono(
			Long id_bono) {

		PreparedStatement pst = null;
		ResultSet rs = null;

		Map<String, Object> datosBono = new HashMap<String, Object>();

		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(SQL_TOTAL_ACUMULADO_DISPONIBLE_BONO);
			pst.setLong(1, id_bono);
			rs = pst.executeQuery();
			while (rs.next()) {
				datosBono.put("acumulado", rs.getDouble(1));
				datosBono.put("disponible", rs.getDouble(2));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return datosBono;
	}
}
