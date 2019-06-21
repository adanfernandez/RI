package uo.ri.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Clase Sustitucion. Tiene una relación muchos a una con la clase Repuesto, una
 * relación muuchos a una con la clase Intervencion
 * 
 * @author Adán
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "REPUESTO_ID, INTERVENCION_ID") }, name = "TSUSTITUCIONES")
public class Sustitucion {

	@Id
	@ManyToOne()
	private Repuesto repuesto;
	@Id
	@ManyToOne()
	private Intervencion intervencion;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int cantidad;

	/**
	 * Constructor vacío de la clase Sustitución
	 */
	public Sustitucion() {
	}

	/**
	 * Devuelve el identificador de la sustitución
	 * 
	 * @return identificador de la sustitución (long)
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Constructor de la clase Sustitucion con un parámetro (Repuesto)
	 * 
	 * @param repuesto
	 *            repuesto utilizado en la sustitucion
	 */
	public Sustitucion(Repuesto repuesto) {
		super();
		this.repuesto = repuesto;
	}

	/**
	 * Constructor de la clase Sustitucion. Tendrá dos parámetros (Repuesto,
	 * Intervencion). Se le pasará el repuesto al constructor con un solo
	 * parámetro (Repuesto)
	 * 
	 * @param repuesto
	 *            repuesto de la sustitución (Repuesto)
	 * @param intervencion
	 *            intervención en la que se emplea el repuesto (Intervencion)
	 */
	public Sustitucion(Repuesto repuesto, Intervencion intervencion) {
		this( repuesto );
		this.intervencion = intervencion;
		Association.Sustituir.link( this, intervencion, repuesto );
	}

	/**
	 * Establece la cantidad de la sustitución
	 * 
	 * @param cantidad
	 *            cantidad de la sustitución (int)
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Devuelve la intervención
	 * 
	 * @return intervencion de la sustitución (Intervencion)
	 */
	public Intervencion getIntervencion() {
		return intervencion;
	}

	/**
	 * Devuelve el repuesto de la intervencion
	 * 
	 * @return repuesto de la intervencion (Repuesto)
	 */
	public Repuesto getRepuesto() {
		return repuesto;
	}

	void _setIntervencion(Intervencion intervencion) {
		this.intervencion = intervencion;
	}

	/**
	 * Establece el repuesto de la intervención
	 * 
	 * @param repuesto
	 */
	void _setRepuesto(Repuesto repuesto) {
		this.repuesto = repuesto;
	}

	/**
	 * Devuelve el importe de la sustitucion. Este será igual a la cantidad de
	 * repuestos utilizados por su precio individual.
	 * 
	 * @return
	 */
	public double getImporte() {
		return repuesto.getPrecio() * cantidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ( ( intervencion == null ) ? 0 : intervencion.hashCode() );
		result = prime * result
				+ ( ( repuesto == null ) ? 0 : repuesto.hashCode() );
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
		Sustitucion other = (Sustitucion) obj;
		if (intervencion == null) {
			if (other.intervencion != null)
				return false;
		} else if (!intervencion.equals( other.intervencion ))
			return false;
		if (repuesto == null) {
			if (other.repuesto != null)
				return false;
		} else if (!repuesto.equals( other.repuesto ))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sustitucion [cantidad=" + cantidad + "]";
	}

}
