package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Clase intervención. Tendrá una relación muchos a uno con la clase Averia, una
 * relación muchos a uno con la clase Mecanico y una relación uno a muchos con
 * la clase Intervencion
 * 
 * @author Adán
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "MECANICO_ID, AVERIA_ID") }, name = "TINTERVENCIONES")
public class Intervencion {

	@ManyToOne()
	private Averia averia;
	@ManyToOne()
	private Mecanico mecanico;
	private int minutos;
	@OneToMany(mappedBy = "intervencion")
	private Set<Sustitucion> sustituciones = new HashSet<Sustitucion>();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Devuelve el identificador del cliente
	 * 
	 * @return identificador del cliente (Long)
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Constructor vacío de la clase Intervencion
	 */
	Intervencion() {
	}

	/**
	 * Constructor de la clase Intervencion con dos parámetros (Mecanico,
	 * Averia). Se hará efectiva la relación entre la intervención con el
	 * mecánico y la avería pasados como parámetro
	 * 
	 * @param mecanico
	 *            mecánico que realiza la intervención (Mecanico)
	 * @param averia
	 *            avería de la que se hace cargo el mecánico (Averia)
	 */
	public Intervencion(Mecanico mecanico, Averia averia) {
		Association.Intervenir.link( mecanico, this, averia );
	}

	/**
	 * Constructor de la clase Intervencion con tres parámetros (Mecanico,
	 * Averia, int). Llamará al constructor con dos parámetros (Mecanico,
	 * Averia) pasándole el mecánico y la avería pasados previamente como
	 * parámetros.
	 * 
	 * @param mecanico
	 *            mecánico que realiza la intervención (Mecanico)
	 * @param averia
	 *            avería de la que se hace cargo el mecánico (Averia)
	 * @param minutos
	 *            duración de la intervención en minutos (int)
	 */
	public Intervencion(Mecanico mecanico, Averia averia, int minutos) {
		this( mecanico, averia );
		this.minutos = minutos;
	}

	/**
	 * Establece la duración de la intervención en minutos.
	 * 
	 * @param minutos
	 *            minutos que dura la intervención (int)
	 */
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	/**
	 * Devuelve la duración de la intervención en minutos (int)
	 * 
	 * @return minutos que dura la itnervención (int)
	 */
	public int getMinutos() {
		return minutos;
	}

	/**
	 * Devuelve el mecánico con el que está asociada la intervención
	 * 
	 * @return mecánico de la intervención (Mecanico)
	 */
	public Mecanico getMecanico() {
		return mecanico;
	}

	/**
	 * Establece el mecánico que se hace cargo de la intervención
	 * 
	 * @param mecanico
	 *            mecánico que realiza la intervención (Mecanico)
	 */
	void _setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	/**
	 * Establece la avería de la intervención
	 * 
	 * @param averia
	 *            avería que se repara en la intervención (Averia)
	 */
	void _setAveria(Averia averia) {
		this.averia = averia;
	}

	/**
	 * Devuelve la avería que se repara en la intervención
	 * 
	 * @param averia
	 *            avería que se repara en la intervención (Averia)
	 */
	public Averia getAveria() {
		return averia;
	}

	/**
	 * Devuelve una copia de las sustituciones realizada en la intervención
	 * 
	 * @return sustituciones realizadas en la intervención (Set<Sustitucion>)
	 */
	public Set<Sustitucion> getSustituciones() {
		return new HashSet<Sustitucion>( sustituciones );
	}

	/**
	 * Devuelve las sustituciones realizadas en la intervención
	 * 
	 * @return sustituciones realizadas en la intervención (Set<Sustitucion>)
	 */
	Set<Sustitucion> _getSustituciones() {
		return sustituciones;
	}

	/**
	 * Devuelve el importe total de la intervención. Este erá igual a la suma
	 * del importe de todas las sustituciones con la mano de obra. Esta última
	 * se calculará en función de los minutos trabajados por el precio del tipo
	 * de vehículo que se esté reparando (Horas * Precio/Hora)
	 * 
	 * @return importe total de la intervención
	 */
	public double getImporte() {
		double importe = 0.0;
		double horas = getMinutos() / 60.0;
		importe += horas * averia.getVehiculo().getTipo().getPrecioHora();

		for (Sustitucion sustitucion : sustituciones) {
			importe += sustitucion.getImporte();
		}

		return importe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( averia == null ) ? 0 : averia.hashCode() );
		result = prime * result
				+ ( ( mecanico == null ) ? 0 : mecanico.hashCode() );
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
		Intervencion other = (Intervencion) obj;
		if (averia == null) {
			if (other.averia != null)
				return false;
		} else if (!averia.equals( other.averia ))
			return false;
		if (mecanico == null) {
			if (other.mecanico != null)
				return false;
		} else if (!mecanico.equals( other.mecanico ))
			return false;
		return true;
	}

	/**
	 * Método toString() con los minutos de la intervención
	 */
	@Override
	public String toString() {
		return "Intervencion [minutos=" + minutos + "]";
	}

}
