package uo.ri.business.impl.admin;

import java.util.ArrayList;
import java.util.List;

import uo.ri.business.dto.VoucherSummary;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cliente;
import uo.ri.util.exception.BusinessException;

/**
 * Clase ListarTodosBonos. Implementa a la interfaz Command. Posee el metodo
 * execute() correspondiente a la interfaz. Dará un resumen de los bonos que
 * poseen todos los clientes que están registrados en la Base de Datos,
 * especificando datos tanto del cliente como del bono
 * 
 * @author Adán
 *
 */
public class ListarTodosBonos implements Command<List<VoucherSummary>> {

	private MedioPagoRepository medioPagoRepository = Factory.repository
			.forMedioPago();

	private ClienteRepository clienteRepositorio = Factory.repository
			.forCliente();

	/**
	 * Metodo execute(). Devolverá una lista de VoucherSummary de todos los
	 * clientes registrados en la base de datos. Cada VoucherSummary tendrá el
	 * nombre, los apellidos y el DNI del cliente, el número de bonos emitidos
	 * por dicho cliente, el dinero disponible y el dinero totalen el conjunto
	 * de los bonos además del dinero total que representan como conjunto
	 */
	@Override
	public List<VoucherSummary> execute() throws BusinessException {
		List<VoucherSummary> bonos = new ArrayList<VoucherSummary>();
		List<Cliente> clientes = clienteRepositorio.findAll();
		Object[] datos;
		for (Cliente cliente : clientes) {
			datos = medioPagoRepository
					.findAggregateVoucherDataByClientId( cliente.getId() );
			VoucherSummary voucher = new VoucherSummary();
			voucher.name = cliente.getNombre();
			voucher.surname = cliente.getApellidos();
			voucher.fullName = cliente.getNombre() + " "
					+ cliente.getApellidos();
			voucher.dni = cliente.getDni();
			int tamaño = Integer.parseInt( datos[0].toString() );
			voucher.emitted = tamaño;
			if (tamaño > 0) {
				voucher.available = (double) datos[1];
				voucher.consumed = (double) datos[2];
			} else {
				voucher.available = 0.0;
				voucher.consumed = 0.0;
			}
			voucher.totalAmount = voucher.available + voucher.consumed;
			voucher.name = cliente.getNombre();
			bonos.add( voucher );
		}
		return bonos;
	}
}
