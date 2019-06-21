package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Clase Mecanico. Tendrá una relación uno a muchos con la clase Intervencion,
 * una relación uno a muchos con la clase Averia.
 * 
 * @author Adán
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "DNI") }, name = "TMECANICOS")
public class Mecanico {

	private String dni;
	private String apellidos;
	private String nombre;
	@OneToMany(mappedBy = "mecanico")
	private Set<Intervencion> intervenciones = new HashSet<Intervencion>();
	@OneToMany(mappedBy = "mecanico")
	private Set<Averia> averias = new HashSet<Averia>();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Constructor vacío de la clase Mecanico
	 */
	Mecanico() {
	}

	/**
	 * Devuelve el identificador del mecánico
	 * 
	 * @return identificador del mecánico (Long)
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Constructor de la clase mecánico con un sólo parámetro (String)
	 * 
	 * @param dni
	 *            DNI del mecánico (String)
	 */
	public Mecanico(String dni) {
		super();
		this.dni = dni;
	}

	/**
	 * Constructor de la clase Mecánico con tres parámetros (String, String,
	 * String) Llamará al constructor con un sólo parámetro (String), pasándole
	 * el DNI.
	 * 
	 * @param dni
	 *            DNI del mecánico (String)
	 * @param nombre
	 *            nombre del mecánico (String)
	 * @param apellidos
	 *            apellidos del mecánico (String)
	 */
	public Mecanico(String dni, String nombre, String apellidos) {
		this( dni );
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	/**
	 * Devuelve las intervenciones del mecánico
	 * 
	 * @return intervenciones del mecánico (Set<Intervencion>)
	 */
	Set<Intervencion> _getIntervenciones() {
		return intervenciones;
	}

	/**
	 * Devuelve una copia de las averías asignadas que posee el mecánico.
	 * 
	 * @return averías asignadas al mecánico (Set<Averia>)
	 */
	public Set<Averia> getAsignadas() {
		return new HashSet<Averia>( averias );
	}

	/**
	 * Devuelve las averías asignadas que posee el mecánico.
	 * 
	 * @return averías asignadas al mecánico (Set<Averia>)
	 */
	Set<Averia> _getAsignadas() {
		return averias;
	}

	/**
	 * Devuelve una copia de las intervenciones del mecánico.
	 * 
	 * @return intervenciones del mecánico (Set<Intervencion>)
	 */
	public Set<Intervencion> getIntervenciones() {
		return new HashSet<Intervencion>( intervenciones );
	}

	/**
	 * Devuelve el DNI del mecánico
	 * 
	 * @return DNI del mecánico (String)
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Devuelve los apellidos del mecánico
	 * 
	 * @return apellidos del mecánico (String)
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Devuelve el nombre del mecánico
	 * 
	 * @return nombre del mecánico (String)
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del mecánico
	 * 
	 * @param nombre
	 *            nombre del mecánico (String)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Establece los apellidos del mecánico
	 * 
	 * @param apellidos
	 *            apellidos del mecánico (String)
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( dni == null ) ? 0 : dni.hashCode() );
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
		Mecanico other = (Mecanico) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals( other.dni ))
			return false;
		return true;
	}

	/**
	 * Método toString() con el dni, el nombre, los apellidos y el identificador
	 * del mecánico.
	 */
	@Override
	public String toString() {
		return "Mecanico [dni=" + dni + ", apellidos=" + apellidos
				+ ", nombre=" + nombre + ", id=" + id + "]";
	}
}
