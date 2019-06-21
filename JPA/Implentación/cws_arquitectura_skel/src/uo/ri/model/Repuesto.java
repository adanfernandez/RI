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
 * Clase repuesto. Tendrá una relación uno a muchos con la clase Sustitucion.
 * 
 * @author Adán
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "CODIGO") }, name = "TREPUESTOS")
public class Repuesto {

	private String codigo;
	private String descripcion;
	private double precio;
	@OneToMany(mappedBy = "repuesto")
	private Set<Sustitucion> sustituciones = new HashSet<Sustitucion>();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Constructor vacío de la clase Repuesto
	 */
	Repuesto() {
	}

	/**
	 * Devuelve el identificador del repuesto
	 * 
	 * @return id del repuesto (Long)
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Constructor de la clase Repuesto con un parámetro (String)
	 * 
	 * @param codigo
	 *            codigo del repuesto (String)
	 */
	public Repuesto(String codigo) {
		super();
		this.codigo = codigo;
	}

	/**
	 * Constructor de la clase Repuesto con tres parámetro
	 * (String)(String)(double). Utilizará el constructor de un solo parametro
	 * (String) pasándole el código del repuesto en cuestión.
	 * 
	 * @param codigo
	 *            codigo del repuesto (String)
	 * @param descripcion
	 *            descripción del repuesto (String)
	 * @param precio
	 *            precio del repuesto (double)1
	 */
	public Repuesto(String codigo, String descripcion, double precio) {
		this( codigo );
		this.descripcion = descripcion;
		this.precio = precio;
	}

	/**
	 * Devuelve una copia de las sustituciones del repuesto
	 * 
	 * @return sustituciones del repuesto (Set<Sustitucion>)
	 */
	public Set<Sustitucion> getSustituciones() {
		return new HashSet<Sustitucion>( sustituciones );
	}

	/**
	 * Devuelve las sustituciones del repuesto
	 * 
	 * @return sustituciones del repuesto (Set<Sustitucion>)
	 */
	Set<Sustitucion> _getSustituciones() {
		return sustituciones;
	}

	/**
	 * Devuelve el precio del repuesto
	 * 
	 * @return precio del repuesto (double)
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * Devuelve el código del repuesto
	 * 
	 * @return el código del repuesto (String)
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Devuelve la descripción del repuesto
	 * 
	 * @return la descripción del repuesto (String)
	 */
	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( codigo == null ) ? 0 : codigo.hashCode() );
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
		Repuesto other = (Repuesto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals( other.codigo ))
			return false;
		return true;
	}

	/**
	 * Método toString() con el código, la descripción y el precio del repuesto.
	 */
	@Override
	public String toString() {
		return "Repuesto [codigo=" + codigo + ", descripcion=" + descripcion
				+ ", precio=" + precio + "]";
	}

}
