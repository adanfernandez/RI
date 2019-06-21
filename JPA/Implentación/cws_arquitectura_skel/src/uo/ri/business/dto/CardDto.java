package uo.ri.business.dto;

import java.util.Date;

/**
 * CardDto. Posee los datos de una tarjeta de crédito.
 * 
 * @author Adán
 *
 */
public class CardDto extends PaymentMeanDto {
	public String cardNumber;
	public Date cardExpiration;
	public String cardType;

}
