package uo.ri.business.impl;

import java.util.List;
import java.util.Map;

import uo.ri.business.CashService;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.business.impl.cash.CreateInvoiceFor;
import uo.ri.business.impl.cash.EncontrarFacturaPorNumero;
import uo.ri.business.impl.cash.EncontrarMediosPagoPorFactura;
import uo.ri.business.impl.cash.LiquidarFactura;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

public class CashServiceImpl implements CashService {

	CommandExecutor executor = Factory.executor.forExecutor();

	/**
	 * Crea una factura pasándole un conjunto de averías en una lista
	 */
	@Override
	public InvoiceDto createInvoiceFor(List<Long> idsAveria)
			throws BusinessException {
		return executor.execute( new CreateInvoiceFor( idsAveria ) );
	}

	/**
	 * Marca una factura como abonada pasándole el id de la factura a pagar y un
	 * Map con todos los cargos.
	 */
	@Override
	public InvoiceDto settleInvoice(Long idInvoiceDto, Map<Long, Double> cargos)
			throws BusinessException {
		return executor.execute( new LiquidarFactura( idInvoiceDto, cargos ) );
	}

	/**
	 * Encuentra las averías de un cliente dado por su DNI. No está
	 * implementado.
	 */
	@Override
	public List<BreakdownDto> findRepairsByClient(String dni)
			throws BusinessException {
		return null;
	}

	/**
	 * Devuelve una factura (DTO) dado su numero
	 */
	@Override
	public InvoiceDto findInvoiceByNumber(Long numeroFactura)
			throws BusinessException {
		return executor
				.execute( new EncontrarFacturaPorNumero( numeroFactura ) );
	}

	/**
	 * Devuelve una lista con los medios de pago de un cliente que va a pagar
	 * una factura dada por parámetro (id)
	 */
	@Override
	public List<PaymentMeanDto> findPaymentMeansForInvoice(Long idFactura)
			throws BusinessException {
		return executor
				.execute( new EncontrarMediosPagoPorFactura( idFactura ) );
	}

	/**
	 * Añade una tarjeta como medio de pago pasándola como parámetro (DTO). No
	 * está implementado.
	 */
	@Override
	public void addCardPaymentMean(CardDto card) throws BusinessException {
	}

	/**
	 * Añade un bono como medio de pago pasándolo como parámetro (DTO). No está
	 * implementado.
	 */
	@Override
	public void addVoucherPaymentMean(VoucherDto voucher)
			throws BusinessException {
	}

	/**
	 * Elimina un medio de pago dado por su id. No está implementado.
	 */
	@Override
	public void deletePaymentMean(Long id) throws BusinessException {
	}

	/**
	 * Devuelve una lista de medios de pago (DTO) de un cliente dado por su id.
	 * No está implementado.
	 */
	@Override
	public List<PaymentMeanDto> findPaymentMeansByClientId(Long id)
			throws BusinessException {
		return null;
	}

}
