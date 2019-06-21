package uo.ri.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.model.types.FacturaStatus;
import uo.ri.util.exception.BusinessException;

/**
 * Clase Cargo. Tendrá una relación muchos a uno con factura y muchos a uno con
 * medio de pago.
 * 
 * @author Adán
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "FACTURA_ID, MEDIOPAGO_ID") }, name = "TCARGOS")
public class Cargo {

	@ManyToOne()
	private Factura factura;
	@ManyToOne
	private MedioPago medioPago;
	private double importe = 0.0;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Devuelve el identificador del cargo
	 * 
	 * @return identificador del cargo (Long)
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Constructor vacío de Cargo
	 */
	Cargo() {
	}

	/**
	 * Constructor de cargo con tres parámetros (Factura, MedioPago, double). Se
	 * hará efectiva la relación entre la factura y el medio de pago pasados por
	 * parámetro con un cargo
	 * 
	 * @param factura
	 *            factura a la que se le asociará un cargo (Factura)
	 * @param medioPago
	 *            medio de pago al que se le asociará un cargo (MedioPago)
	 * @param importe
	 *            cantidad que se ingresará (double)
	 * @throws BusinessException
	 *             Se lanzará una BusinessException en caso de que el medio de
	 *             pago no pueda efectuar el pago. Esto pasará si una tarjeta de
	 *             crédito está caducada o si un bono no tiene cantidad
	 *             suficiente de dinero disponible para efectuar un pago.
	 */
	public Cargo(Factura factura, MedioPago medioPago, double importe)
			throws BusinessException {
		medioPago.pagar( importe );
		this.factura = factura;
		this.medioPago = medioPago;
		this.importe += importe;
		Association.Cargar.link( factura, medioPago, this );
	}

	/**
	 * Anula (retrocede) este cargo de la factura y el medio de pago Solo se
	 * puede hacer si la factura no está abonada Decrementar el acumulado del
	 * medio de pago Desenlazar el cargo de la factura y el medio de pago
	 * 
	 * @throws BusinessException
	 */
	public void rewind() throws BusinessException {
		if (factura.getStatus().equals( FacturaStatus.SIN_ABONAR )) {
			medioPago.setAcumulado( medioPago.getAcumulado() - importe );
			Association.Cargar.unlink( factura, medioPago, this );
		}
	}

	/**
	 * Devuelve la factura a la que hace referencia el cargo
	 * 
	 * @return factura que posee el cargo (Factura)
	 */
	public Factura getFactura() {
		return factura;
	}

	/**
	 * Devuelve el medio de pago al que hace referencia el cargo
	 * 
	 * @return medio de pago que posee el cargo (MedioPago)
	 */
	public MedioPago getMedioPago() {
		return medioPago;
	}

	/**
	 * Establece una factura al cargo
	 * 
	 * @param factura
	 *            factura que posee el cargo (Factura)
	 */
	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	/**
	 * Establece un medio de pago al cargo
	 * 
	 * @param medioPago
	 *            medio de pago que posee el cargo (MedioPago)
	 */
	public void setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}

	/**
	 * Devuelve el importe de todos los cargos
	 * 
	 * @return importe de los cargos (double)
	 */
	public double getImporte() {
		return importe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ( ( factura == null ) ? 0 : factura.hashCode() );
		result = prime * result
				+ ( ( medioPago == null ) ? 0 : medioPago.hashCode() );
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
		Cargo other = (Cargo) obj;
		if (factura == null) {
			if (other.factura != null)
				return false;
		} else if (!factura.equals( other.factura ))
			return false;
		if (medioPago == null) {
			if (other.medioPago != null)
				return false;
		} else if (!medioPago.equals( other.medioPago ))
			return false;
		return true;
	}

	/**
	 * Método toString() con el atributo importe.
	 */
	@Override
	public String toString() {
		return "Cargo [importe=" + importe + "]";
	}

}
