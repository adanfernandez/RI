package uo.ri.model;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.date.DateUtil;
import uo.ri.util.exception.BusinessException;

/**
 * Clase TarjetaCredito. Extiende de la clase MedioPago, implementando pues el
 * método pagar. Posee un id (long), un acumulado (double), unos
 * cargos(Set<Cargo>), un cliente (Cliente) y un DType (String)(será el
 * discriminante)
 * 
 * @author Adán
 *
 */
@Entity
@DiscriminatorValue("TTarjetasCredito")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "NUMERO") }, name = "TTARJETASCREDITO")
public class TarjetaCredito extends MedioPago {

	/**
	 * Constructor de la clase TarjetaCredito con un parametro (Cliente).
	 * Efectuará la relación entre el cliente y la tarjeta de crédito.
	 * 
	 * @param cliente
	 *            cliente poseedor de la tarjeta de crédito (Cliente)
	 */
	public TarjetaCredito(Cliente cliente) {
		super();
		Association.Pagar.link( cliente, this );
	}

	/**
	 * Constructor vacío de la clase TarjetaCredito
	 */
	TarjetaCredito() {
	}

	private String numero;
	private String tipo = "UNKNOWN";
	private Date validez = DateUtil.tomorrow();

	/**
	 * Constructor de la clase TarjetaCredito al que se le pasa el número de
	 * esta (String)
	 * 
	 * @param numero
	 *            número de la tarjeta de crédito (String)
	 */
	public TarjetaCredito(String numero) {
		this.numero = numero;
	}

	/**
	 * Constructor de la clase TarjetaCredito al que se le pasa tres parámetros
	 * (String, String, Date)
	 * 
	 * @param numero
	 *            número de la tarjeta de crédito (String)
	 * @param tipo
	 *            tipo de la tarjeta de crédito (String)
	 * @param fecha
	 *            fecha de caducidad de la tarjeta de crédito (Date)
	 */
	public TarjetaCredito(String numero, String tipo, Date fecha) {
		this.numero = numero;
		this.tipo = tipo;
		this.validez = fecha;
	}

	/**
	 * Devuelve el número de la tarjeta de crédito
	 * 
	 * @return número que tiene la tarjeta de crédito (String)
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * Devuelve el tipo de la tarjeta de crédito
	 * 
	 * @return tipo de la tarjeta de crédito (String)
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Establece el tipo de la tarjeta de crédito
	 * 
	 * @param tipo
	 *            nuevo tipo que tendrá la tarjeta de crédito (String)
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Devuelve la fecha de caducidad de la tarjeta de crédito
	 * 
	 * @return fecha de caducidad de la tarjeta de crédito (Date)
	 */
	public Date getValidez() {
		return validez;
	}

	/**
	 * Establece la fecha de caducidad de una tarjeta de crédito
	 * 
	 * @param validez
	 *            fecha de caducidad nueva de la tarjeta de crédito (Date)
	 */
	public void setValidez(Date validez) {
		this.validez = validez;
	}

	/**
	 * Método pagar. Se le pasará el importe a pagar como parámetro y se le
	 * sumará al acumulado total de la tarjeta de crédito. Para efectuar el
	 * pago, la tarjeta no puede estar caducada. En caso de que lo esté se
	 * lanzará una BusinessException ("La tarjeta está caducada").
	 */
	public void pagar(double cantidad) throws BusinessException {
		if (!isValidNow())
			throw new BusinessException( "La tarjeta está caducada" );
		this.acumulado += cantidad;
	}

	/**
	 * Devulve si la tarjeta está caducada o no. La tarjeta estará caducada si
	 * su fecha de validación es superior al día de hoy.
	 * 
	 * @return true: es valida para efectuar cualquier pago. false: está
	 *         caducada (boolean)
	 */
	public boolean isValidNow() {
		if (validez.after( DateUtil.today() ))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( numero == null ) ? 0 : numero.hashCode() );
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TarjetaCredito other = (TarjetaCredito) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals( other.numero ))
			return false;
		return true;
	}

	/**
	 * Método toString() con el número de la tarjeta de crédito, su tipo y su
	 * fecha de caducidad
	 */
	@Override
	public String toString() {
		return "TarjetaCredito [numero=" + numero + ", tipo=" + tipo
				+ ", validez=" + validez + "]";
	}

}
