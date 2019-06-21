package uo.ri.business.impl.admin;

import java.util.ArrayList;
import java.util.List;

import uo.ri.business.dto.VoucherDto;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Bono;
import uo.ri.util.exception.BusinessException;

/**
 * Clase ListarBonosCliente. Implementa a la interfaz Command. Posee el metodo
 * execute() correspondiente a la interfaz. Posee un constructor con un solo
 * parámetro (Long).
 * 
 * @author Adán
 *
 */
public class ListarBonosCliente implements Command<List<VoucherDto>> {

	private Long id;

	private MedioPagoRepository medioPagoRepository = Factory.repository
			.forMedioPago();

	/**
	 * Constructor de ListarBonosCliente.
	 * 
	 * @param id
	 *            identificador de la persona de la que se quiere listar los
	 *            bonos (Long)
	 */
	public ListarBonosCliente(Long id) {
		this.id = id;
	}

	/**
	 * Metodo execute(). Devolverá una lista de Bonos (DTO) de un cliente pasado
	 * previamente por parámetro en el constructor. Cada Bono contendrá su ID,
	 * su descripción, su códgo, su dinero disponible, su dinero acumulado y el
	 * ID del cliente.
	 */
	@Override
	public List<VoucherDto> execute() throws BusinessException {
		List<Bono> bonos = medioPagoRepository.findVouchersByClientId( id );
		List<VoucherDto> bonosCliente = new ArrayList<VoucherDto>();

		for (Bono bono : bonos) {
			if (bono.getCliente().getId().equals( id )) {
				VoucherDto voucher = new VoucherDto();
				voucher.id = bono.getId();
				voucher.description = bono.getDescripcion();
				voucher.code = bono.getCodigo();
				voucher.clientId = bono.getCliente().getId();
				voucher.available = bono.getDisponible();
				voucher.accumulated = bono.getAcumulado();
				bonosCliente.add( voucher );
			}
		}
		return bonosCliente;
	}

}
