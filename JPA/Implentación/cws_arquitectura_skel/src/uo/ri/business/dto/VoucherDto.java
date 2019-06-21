package uo.ri.business.dto;

/**
 * VoucherDto. Posee los datos de un bono
 * 
 * @author Adán
 *
 */
public class VoucherDto extends PaymentMeanDto {
	public String code;
	public String description;
	public Double available;
}
