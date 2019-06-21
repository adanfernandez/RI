package uo.ri.business.impl;

import java.util.List;
import java.util.Map;

import uo.ri.business.CashService;
import uo.ri.business.impl.cash.LiquidarFactura;
import uo.ri.common.BusinessException;

public class CashServiceImpl implements CashService {

	public CashServiceImpl() {
	}


	/**
	 * Devolver el precio de una factura dada. Se comprobará que esta factura no
	 * haya sido abonada previamente.
	 * 
	 * @param id
	 *            id de la factura
	 * @return factura Posee el precio de la factura y el
	 *         status para poder controlar que no se pague dos veces una factura
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> precioFactura(Long id) throws BusinessException {
		return new LiquidarFactura().precioFactura(id);
	}

	/**
	 * Obtener el cliente poseedor de una factura dada
	 * 
	 * @param id
	 *            Identificador de la factura
	 * @return cliente id del cliente al que pertenece la factura
	 * @throws BusinessException
	 */
	@Override
	public Long obtenerClienteDeUnaFactura(Long id) throws BusinessException {
		return new LiquidarFactura().obtenerClienteDeUnaFactura(id);
	}

	/**
	 * Se marcará una factura dada como abonada. Se comprobará que los
	 * parámetros introducidos son correctos. Se actualizarán los datos que
	 * hacen referencia a los medios de pago.
	 * 
	 * @throws BusinessException
	 */
	@Override
	public void liquidarFactura(Long id_factura, List<Map<String, Object>> dineroParaLiquidar) throws BusinessException {
		new LiquidarFactura(id_factura, dineroParaLiquidar).execute();
	}

	/**
	 * Devuelve una lista de Map con las tarjetas de un cliente
	 * 
	 * @param id_cliente
	 *            identificador del cliente
	 * @return tarjetas tarjetas que posee el cliente
	 * @throws BusinessException
	 */
	@Override
	public List<Map<String, Object>> tarjetasCliente(Long id_cliente) throws BusinessException {
		return new LiquidarFactura().tarjetasCliente(id_cliente);
	}

	/**
	 * Listar todos los bonos disponibles por un cliente en una lista de Map
	 * 
	 * @param id_cliente
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<Map<String, Object>> bonosCliente(Long id_cliente) throws BusinessException {
		return new LiquidarFactura().bonosCliente(id_cliente);
	}
}
