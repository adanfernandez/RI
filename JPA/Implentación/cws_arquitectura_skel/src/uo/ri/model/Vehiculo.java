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
 * Clase Vehiculo. Tendrá una relación muchos a uno con el cliente, una relación
 * muchos a uno con el tipo y una relación uno a muchos con las averías.
 * 
 * @author Adán
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "MATRICULA") }, name = "TVEHICULOS")
public class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Devuelve el identificador del vehículo
	 * 
	 * @return identificador del vehículo (Long)
	 */
	public Long getId() {
		return id;
	}

	private String marca;

	private String matricula;

	private String modelo;
	private int num_Averias = 0;

	@ManyToOne()
	private Cliente cliente;

	@ManyToOne()
	private TipoVehiculo tipo;

	@OneToMany(mappedBy = "vehiculo")
	private Set<Averia> averias = new HashSet<Averia>();

	/**
	 * Constructor vacío de la clase Vehiculo
	 */
	Vehiculo() {
	};

	/**
	 * Constructor de la clase Vehcíulo con un parámetro (String).
	 * 
	 * @param matricula
	 *            matrícula del vehículo (String).
	 */
	public Vehiculo(String matricula) {
		super();
		this.matricula = matricula;
	}

	/**
	 * Devuelve la marca del vehículo.
	 * 
	 * @return marca marca del veículo (String)
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * Establece la marca al vehículo
	 * 
	 * @param marca
	 *            marca del vehículo (String)
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * Devuelve la matrícula del vehículo
	 * 
	 * @return matricula matricula del vehículo (String)
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * Devuelve el modelo del vehículo
	 * 
	 * @return modelo modelo del vehículo (String)
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * Establece el modelo del vehículo
	 * 
	 * @param modelo
	 *            modelo del vehículo (String)
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	/**
	 * Constructor de Vehiculo con tres parámetros (String, String, String)
	 * 
	 * @param matricula
	 *            matrícula del vehículo (String)
	 * @param marca
	 *            marca del vehículo (String)
	 * @param modelo
	 *            modelo del vehículo (String)
	 */
	public Vehiculo(String matricula, String marca, String modelo) {
		this( matricula );
		this.marca = marca;
		this.modelo = modelo;
	}

	/**
	 * Establece el cliente del vehículo.
	 * 
	 * @param cliente
	 *            cliente propietario del vehículo (Cliente)
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Devuelve al cliente propietario del vehículo
	 * 
	 * @return cliente cliente propietario del vehículo (Cliente)
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Establece el tipo de vehículo.
	 * 
	 * @param tipoVehiculo
	 *            tipo del vehículo (TipoVehiculo).
	 */
	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipo = tipoVehiculo;
	}

	/**
	 * Devuelve una copia de las averías que posee el vehículo.
	 * 
	 * @return new HashSet<Averia>(averias)
	 */
	public Set<Averia> getAverias() {
		return new HashSet<Averia>( averias );
	}

	/**
	 * Devuelve el número de averías de un vehículo
	 * 
	 * @return num_averias número de averías de un vehículo (integer)
	 */
	public int getNumAverias() {
		return num_Averias;
	}

	/**
	 * Devuelve las averías del vehículo
	 * 
	 * @return averias averías que posee un vehiculo (Set<Averia>)
	 */
	Set<Averia> _getAverias() {
		return averias;
	}

	/**
	 * Establece el número de averías que posee un vehículo
	 * 
	 * @param num_Averias
	 *            número de averías del vehículo (integer)
	 */
	public void setNumAverias(int num_Averias) {
		this.num_Averias = num_Averias;
	}

	/**
	 * Método hashCode. Utiliza el atributo matricula del vehiculo.
	 * 
	 * @return hashCode del vehiculo
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ( ( matricula == null ) ? 0 : matricula.hashCode() );
		return result;
	}

	/**
	 * Método equals. Utiliza el atributo matricula del vehículo.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehiculo other = (Vehiculo) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals( other.matricula ))
			return false;
		return true;
	}

	/**
	 * Retorna el tipo del que es el vehículo
	 * 
	 * @return tipo tipo del vehículo (TipoVehiculo)
	 */
	public TipoVehiculo getTipo() {
		return tipo;
	}

	/**
	 * Método toString(). Devuelve la marca, la matrícula y el modelo del
	 * vehículo (String)
	 */
	@Override
	public String toString() {
		return "Vehiculo [marca=" + marca + ", matricula=" + matricula
				+ ", modelo=" + modelo + "]";
	}

}
