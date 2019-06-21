package uo.ri.business.dto;

import java.util.Date;

/**
 * BreakdownDto. Posee los datos de una avería.
 * 
 * @author Adán
 *
 */
public class BreakdownDto {

	public long id;
	public long vehicleId;
	public String description;
	public Date date;
	public Long invoiceId;
	public boolean usedForVoucher;
	public double total;
	public String status;

}
