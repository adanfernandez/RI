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
 * Clase TipoVehiculo. Tendrá una relación uno a muchos con la clase Vehiculo
 * 
 * @author Adán
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "NOMBRE") }, name = "TTIPOSVEHICULO")
public class TipoVehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	private String nombre;
	private double precioHora;

	@OneToMany(mappedBy = "tipo")
	private Set<Vehiculo> vehiculos = new HashSet<Vehiculo>();

	/**
	 * Constructor vacío de la clase TipoVehiculo
	 */
	public TipoVehiculo() {
	}

	/**
	 * Constructor de la clase TipoVehiculo con dos parámetros (String, double)
	 * 
	 * @param nombre
	 *            nombre del tipo de vehículo (String)
	 * @param precioHora
	 *            precio por hora cuando se repare (double)
	 */
	public TipoVehiculo(String nombre, double precioHora) {
		this.nombre = nombre;
		this.precioHora = precioHora;
	}

	/**
	 * Devuelve los vehículos que tienen el tipo de vehículo en concreto
	 * 
	 * @return vehiculos con este tipo de vehículo (Set<Vehiculo>)
	 */
	Set<Vehiculo> _getVehiculos() {
		return vehiculos;
	}

	/**
	 * Devuelve una copia de los los vehículos que tienen el tipo de vehículo en
	 * concreto
	 * 
	 * @return vehiculos con este tipo de vehículo (Set<Vehiculo>)
	 */
	public Set<Vehiculo> getVehiculos() {
		return new HashSet<Vehiculo>( vehiculos );
	}

	/**
	 * Devuelve el precio por hora que se apliacará al vehículo que sea de este
	 * tipo cuando se repare
	 * 
	 * @return precio por hora en las intervenciones en un vehículo de este tipo
	 *         (double)
	 */
	public double getPrecioHora() {
		return precioHora;
	}

	/**
	 * Devuelve el nombre del tipo de vehículo
	 * 
	 * @return nombre del tipo de vehíuclo (String)
	 */
	public String getNombre() {
		return nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( nombre == null ) ? 0 : nombre.hashCode() );
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
		TipoVehiculo other = (TipoVehiculo) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals( other.nombre ))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoVehiculo [nombre=" + nombre + ", precioHora=" + precioHora
				+ "]";
	}

}
