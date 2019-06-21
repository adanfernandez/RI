package uo.ri.business.impl.cash;

import java.util.List;
import java.util.Map;

import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.CargoRepository;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cargo;
import uo.ri.model.Factura;
import uo.ri.model.MedioPago;
import uo.ri.util.exception.BusinessException;

/**
 * Clase LiquidarFactura. Implementa a la interfaz Command. Posee el metodo
 * execute() correspondiente a la interfaz. Posee 4 metodos privados que
 * complementan al execute(). La principal finalidad es el efectuar el pago de
 * la factura. Para ello se tendrá que seguir una serie de restricciones (tiene
 * que existir, tiene que estar sin abonar y tiene que tener al menos una avería
 * facturada. En caso contrario, saltará una BusinessException.
 * 
 * @author Adán
 *
 */
public class LiquidarFactura implements Command<InvoiceDto> {

	Long idInvoiceDto;
	Map<Long, Double> cargos;
	FacturaRepository facturaRepository = Factory.repository.forFactura();
	MedioPagoRepository medioPagoRepository = Factory.repository.forMedioPago();
	CargoRepository cargoRepository = Factory.repository.forCargo();

	/**
	 * Constructor de la clase LiquidarFactura. Tiene dos parámetros (Long,
	 * Map<Long, Double>)
	 * 
	 * @param idInvoiceDto
	 *            id de la factura a la que se hace referencia (Long)
	 * @param cargos
	 *            cargos de la factura indicada anteriormente (Map<Long,
	 *            Double>)
	 */
	public LiquidarFactura(Long idInvoiceDto, Map<Long, Double> cargos) {
		this.idInvoiceDto = idInvoiceDto;
		this.cargos = cargos;
	}

	/**
	 * Metodo execute(). Se efectuará el pago de una factura y se devolverá un
	 * InvoiceDto de dicha factura. En caso de que la factura no exista se
	 * lanzará una BusinessException con el siguiente mensaje:
	 * "La factura no existe". Si la factura que se está intentando pagar está
	 * abonada también se lanzará una BusinessException. En este caso el mensaje
	 * será el siguiente: "Ya está abonada". Por último, si se intenta pagar una
	 * factura que no posee averías saltará una BusinessException con el
	 * siguiente mensaje: "La factura no tiene averias". Una vez efectuado el
	 * pago se devolverá un DTO de la factura.
	 */
	@Override
	public InvoiceDto execute() throws BusinessException {
		Factura factura = facturaRepository.findById( idInvoiceDto );
		if (factura == null)
			throw new BusinessException( "La factura no existe" );
		if (factura.isSettled())
			throw new BusinessException( "Ya está abonada" );
		List<MedioPago> mediosPago = medioPagoRepository.findAll();
		for (MedioPago mp : mediosPago) {
			if (cargos.get( mp.getId() ) != null)
				cargoRepository.add( new Cargo( factura, mp, cargos.get( mp
						.getId() ) ) );
		}
		factura.settle();
		return DtoAssembler.toDto( factura );
	}
}
