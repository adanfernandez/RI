package uo.ri.business.dto;

/**
 * Un resultado resumido de todos los bonos de un cliente.
 */
public class VoucherSummary {
	public String dni;
	public String fullName;
	public int emitted;
	public double totalAmount = 0.0;
	public double available = 0.0;
	public double consumed = 0.0;
	public String name;
	public String surname;
}
