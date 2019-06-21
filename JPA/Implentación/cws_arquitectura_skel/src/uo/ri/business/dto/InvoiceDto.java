package uo.ri.business.dto;

import java.util.Date;

/**
 * InvoiceDto. Posee los datos de una factura
 * 
 * @author Adán
 *
 */
public class InvoiceDto {

	public Long id;
	public double total;
	public double vat;
	public long number;
	public Date date;
	public String status;

}
