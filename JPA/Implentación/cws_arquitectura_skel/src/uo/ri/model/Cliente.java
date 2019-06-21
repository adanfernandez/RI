package uo.ri.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.model.types.Address;

/**
 * Clase Cliente. Tendrá una relación uno a muchos con la clase Vehículo, una
 * relación uno a muchos con la clase MedioPago y tendrá dos relaciones con la
 * clase Recomendacion. Una de ellas será uno a uno (recomendado) y, la otra,
 * uno a muchos (recomendaciones)
 * 
 * @author Adán
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "DNI") }, name = "TCLIENTES")
public class Cliente {

	private String dni;
	private String nombre;
	private String apellidos;
	private Address address;
	@OneToMany(mappedBy = "cliente")
	private Set<Vehiculo> vehiculos = new HashSet<Vehiculo>();
	@OneToMany(mappedBy = "cliente")
	private Set<MedioPago> mediosPago = new HashSet<MedioPago>();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private Recomendacion recomendado;
	@OneToMany(mappedBy = "recomendado")
	private Set<Recomendacion> recomendaciones = new HashSet<Recomendacion>();

	private String telefono;
	private String email;

	/**
	 * Constructor vacío de la clase Cliente
	 */
	Cliente() {
	}

	/**
	 * Constructor con un parámetro de la clase Cliente
	 * 
	 * @param dni
	 *            DNI del cliente en cuestión (String)
	 */
	public Cliente(String dni) {
		super();
		this.dni = dni;
	}

	/**
	 * Constructor con tres parámetros de la clase Cliente (String, String,
	 * String), Llamará al constructor con un sólo parámetro (String) pasándole
	 * el DNI del cliente.
	 * 
	 * @param dni
	 *            DNI del cliente (String)
	 * @param nombre
	 *            nombre del cliente (String)
	 * @param apellidos
	 *            apellidos del cliente (String)
	 */
	public Cliente(String dni, String nombre, String apellidos) {
		this( dni );
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	/**
	 * Devuelve el DNI del cliente
	 * 
	 * @return DNI del cliente (String)
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Devuelve el nombre del cliente en cuestión
	 * 
	 * @return nombre del cliente (String)
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre al cliente
	 * 
	 * @param nombre
	 *            nombre del cliente (String)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve los apellidos del cliente
	 * 
	 * @return apellidos del cliente (String)
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Establece los apellidos del cliente
	 * 
	 * @param apellidos
	 *            apellidos del cliente (String)
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Devuelve la dirección del cliente
	 * 
	 * @return la dirección del cliente (Address)
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Establece la dirección del cliente
	 * 
	 * @param address
	 *            la dirección del cliente (Address)
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Devuelve los vehículos del cliente
	 * 
	 * @return vehículos del cliente (Set<Vehiculo>)
	 */
	Set<Vehiculo> _getVehiculos() {
		return vehiculos;
	}

	/**
	 * Devuelve una copia de los vehículos del cliente
	 * 
	 * @return new HashSet<Vehiculo>(vehiculos)
	 */
	public Set<Vehiculo> getVehiculos() {
		return new HashSet<Vehiculo>( vehiculos );
	}

	/**
	 * Devuelve una copia de los medios de pago de un cliente
	 * 
	 * @return new HashSet<Vehiculo>(mediosPago)
	 */
	public Set<MedioPago> getMediosPago() {
		return new HashSet<MedioPago>( mediosPago );
	}

	/**
	 * Devuelve los medios de pago de un cliente
	 * 
	 * @return medios de pago de un cliente (Set<MedioPago>)
	 */
	Set<MedioPago> _getMediosPago() {
		return mediosPago;
	}

	/**
	 * Devuelve el identificador de un cliente
	 * 
	 * @return identificador de un cliente (Long)
	 */
	public Long getId() {
		return id;
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
		Cliente other = (Cliente) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals( other.dni ))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [dni=" + dni + ", nombre=" + nombre + ", apellidos="
				+ apellidos + ", address=" + address + ", id=" + id + "]";
	}

	/**
	 * Devuelve las averias que han sido pagadas y no usuadas para bono
	 * 
	 * @return averias no usadas para bono y que han sido pagadas (List<Averia>)
	 */
	public List<Averia> getAveriasBono3NoUsadas() {
		List<Averia> averias = new ArrayList<Averia>();

		for (Vehiculo vehiculo : vehiculos) {
			for (Averia averia : vehiculo.getAverias()) {
				if (averia.esElegibleParaBono3()) {
					averias.add( averia );
				}
			}
		}
		return averias;
	}

	/**
	 * Devuelve una copia de las recomendaciones hechas por un cliente
	 * 
	 * @return recomendaciones hechas por el cliente (Set<Recomendacion>)
	 */
	public Set<Recomendacion> getRecomendacionesHechas() {
		return new HashSet<Recomendacion>( recomendaciones );
	}

	/**
	 * Devuelve las recomendaciones hechas por el cliente
	 * 
	 * @return recomendaciones hechas por el cliente (Set<Recomendacion>)
	 */
	Set<Recomendacion> _getRecomendacionesHechas() {
		return recomendaciones;
	}

	/**
	 * Devuelve la recomendación haciendo referenciaque recomendó al cliente
	 * 
	 * @return
	 */
	public Recomendacion getRecomendacionRecibida() {
		return recomendado;
	}

	/**
	 * Establece la recomendación que recibiío un cliente
	 * 
	 * @param recomendacion
	 *            recomendación que recibió un cliente
	 */
	void _setRecomendacionRecibida(Recomendacion recomendacion) {
		this.recomendado = recomendacion;
	}

	/**
	 * Devuelve si un cliente es elegible para bonos por tres recomendaciones.
	 * No lo será si está recién registrado (no tiene averías), si no tiene
	 * recomendaciones, si no tiene averías o si alguno de los recomendados no
	 * tiene recomendaciones.
	 * 
	 * @return true: es elegible para bonos. false: no es elegible para bonos
	 *         (boolean)
	 */
	public boolean elegibleBonoPorRecomendaciones() {
		if (vehiculos.size() == 0)
			return false;
		if (recomendaciones.size() == 0)
			return false;
		for (Vehiculo vehiculo : vehiculos) {
			if (vehiculo._getAverias().size() == 0)
				return false;
		}
		int contador = 0;
		for (Recomendacion recomendacion : recomendaciones) {
			if (!recomendacion.isUsada()) {
				for (Vehiculo v : recomendacion.getRecomendado().getVehiculos()) {
					int total = 0;
					if (v.getAverias().size() > 0) {
						total++;
					}
					if (total > 0)
						contador++;
				}
			}
		}
		if (contador >= 3)
			return true;

		return false;

	}

	/**
	 * Devuelve el correo electrónico del cliente
	 * 
	 * @return correo electrónico del cliente (String)
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Devuelve el teléfono del cliente
	 * 
	 * @return teléfono del cliente (String)
	 */
	public String getPhone() {
		return telefono;
	}

	/**
	 * Establece el teléfono del cliente
	 * 
	 * @param telefono
	 *            teléfono del cliente (String)
	 */
	public void setPhone(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Establece el email del cliente
	 * 
	 * @param email
	 *            correo eléctronico del cliente (String)
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
