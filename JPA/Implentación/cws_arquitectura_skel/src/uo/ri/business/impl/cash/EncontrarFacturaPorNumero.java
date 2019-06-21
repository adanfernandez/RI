package uo.ri.business.impl.cash;

import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Factura;
import uo.ri.util.exception.BusinessException;

/**
 * Clase EncontrarFacturaPorNumero. Implementa a la interfaz Command. Posee el
 * metodo execute() correspondiente a la interfaz. La principal finaliad de esta
 * clase es buscar una factura y devolverla (DTO) habiendo pasado el ID de la
 * factura deseada previamente.
 * 
 * @author Adán
 *
 */
public class EncontrarFacturaPorNumero implements Command<InvoiceDto> {

	Long numero;
	FacturaRepository facturaRepository = Factory.repository.forFactura();

	/**
	 * Constructor de la clase EncontrarFacturaPorNumero. Tiene un parámetro que
	 * se corresponde al número de la factura que deseamos buscar (Long)
	 * 
	 * @param numeroFactura
	 *            número de la factura que deseamos buscar (Long)
	 */
	public EncontrarFacturaPorNumero(Long numeroFactura) {
		this.numero = numeroFactura;
	}

	/**
	 * Método execute(). Buscará la factura pasándole el número previamente por
	 * parámetro y la devolverá (DTO) si no está abonada. Si lo está se lanzará
	 * una BusinessException("Ya está abonada").
	 */
	@Override
	public InvoiceDto execute() throws BusinessException {
		InvoiceDto facturaDto = new InvoiceDto();
		Factura facturaLogica = facturaRepository.findByNumber( numero );
		if (facturaLogica == null) {
			return null;
		}
		if (facturaLogica.isSettled())
			throw new BusinessException( "Ya está abonada" );
		facturaDto = DtoAssembler.toDto( facturaLogica );
		return facturaDto;
	}

}
