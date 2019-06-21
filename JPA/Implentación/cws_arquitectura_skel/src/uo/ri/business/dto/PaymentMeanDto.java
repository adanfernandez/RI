package uo.ri.business.dto;

/**
 * PaymentMeanDto. Posee los datos de un medio de pago.
 * 
 * @author Adán
 *
 */
public abstract class PaymentMeanDto {
	public Long id;
	public Long clientId;
	public Double accumulated;
	public String tipo;
}
