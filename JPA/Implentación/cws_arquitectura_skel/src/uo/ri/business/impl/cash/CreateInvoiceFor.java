package uo.ri.business.impl.cash;

import java.util.List;

import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.AveriaRepository;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Averia;
import uo.ri.model.Factura;
import uo.ri.persistence.jpa.util.Jpa;
import uo.ri.util.exception.BusinessException;

/**
 * Clase CreateInvoiceFor. Su principal funcionalidad es crear una factura
 * 
 * @author Adán
 *
 */
public class CreateInvoiceFor implements Command<InvoiceDto> {

	private List<Long> idsAveria;
	FacturaRepository facturaRepository = Factory.repository.forFactura();
	AveriaRepository averiaRepository = Factory.repository.forAveria();

	/**
	 * Constructor de CreateInvoiceFor con un parámetro(List<Long>)
	 * 
	 * @param idsAveria
	 *            lista de las averías (List<Long>)
	 */
	public CreateInvoiceFor(List<Long> idsAveria) {
		this.idsAveria = idsAveria;
	}

	/**
	 * Método execute(). Crea la factura en caso de que haya averías.
	 */
	@Override
	public InvoiceDto execute() throws BusinessException {
		Long id = facturaRepository.getNextInvoiceNumber();
		List<Averia> averias = averiaRepository.findByIds( idsAveria );
		assertNotEmpyt( averias );
		Factura f = new Factura( id, averias );
		Jpa.getManager().persist( f );
		return DtoAssembler.toDto( f );
	}

	/**
	 * Se comprueba que haya averías. Si no las hay, se lanzará una
	 * BusinessException
	 * 
	 * @param averias
	 *            lista de averias (List<Averia>)
	 * @throws BusinessException
	 *             si no hay averías: "No hay averias en la lista"
	 */
	private void assertNotEmpyt(List<Averia> averias) throws BusinessException {
		if (averias.isEmpty())
			throw new BusinessException( "No hay averias en la lista" );
	}
}
