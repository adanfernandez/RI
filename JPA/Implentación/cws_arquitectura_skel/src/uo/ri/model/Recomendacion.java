package uo.ri.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Clase Recomendacion. Tendrá una relación uno a muchos con el Cliente
 * (recomendador) y una relación muchos a uno con el Cliente (recomendado)
 * 
 * @author Adán
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "RECOMENDADOR_ID, RECOMENDADO_ID") }, name = "TRECOMENDACIONES")
public class Recomendacion {

	@OneToOne
	Cliente recomendador;
	@ManyToOne
	Cliente recomendado;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private boolean usada = false;

	/**
	 * Constructor vacío de la clase recomendación
	 */
	public Recomendacion() {
	}

	/**
	 * Constructor de la clase Recomendacion con dos parámetros (Cliente,
	 * Cliente). El primer parámetro se corresponde con el recomendador, el
	 * segundo será el recomendado.
	 * 
	 * @param recomendador
	 *            cliente que recomienda a una persona el taller (Cliente)
	 * @param recomendado
	 *            persona recomendada por un cliente del taller (Cliente)
	 */
	public Recomendacion(Cliente recomendador, Cliente recomendado) {
		this.recomendador = recomendador;
		this.recomendado = recomendado;
		recomendador._getRecomendacionesHechas().add( this );
		recomendado._setRecomendacionRecibida( this );
	}

	/**
	 * Devuelve el recomendador
	 * 
	 * @return cliente que ha recomendado a otro cliente (Cliente)
	 */
	public Cliente getRecomendador() {
		return recomendador;
	}

	/**
	 * Devuelve el cliente recomendado
	 * 
	 * @return cliente que ha sido recomendado por otro cliente (Cliente)
	 */
	public Cliente getRecomendado() {
		return recomendado;
	}

	/**
	 * Devuelve el identificador de la recomendación
	 * 
	 * @return identificador de la recomendación (Long)
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Deshace una relación entre un recomendador y sus recomendaciones
	 */
	public void unlink() {
		recomendador._getRecomendacionesHechas().remove( this );
		recomendado._setRecomendacionRecibida( null );
		recomendador = null;
		recomendado = null;

	}

	/**
	 * Marca una recomendación como usada para bono
	 */
	public void markAsUsadaBono() {
		usada = true;
	}

	/**
	 * Devuelve si la recomendación ha sido usada para bono o no
	 * 
	 * @return true: ha sido usada. false: no ha sido usada (boolean)
	 */
	public boolean isUsada() {
		return usada;
	}

	/**
	 * Método toString() con el atributo usada.
	 */
	@Override
	public String toString() {
		return "Recomendacion [usada=" + usada + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ( ( recomendado == null ) ? 0 : recomendado.hashCode() );
		result = prime * result
				+ ( ( recomendador == null ) ? 0 : recomendador.hashCode() );
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
		Recomendacion other = (Recomendacion) obj;
		if (recomendado == null) {
			if (other.recomendado != null)
				return false;
		} else if (!recomendado.equals( other.recomendado ))
			return false;
		if (recomendador == null) {
			if (other.recomendador != null)
				return false;
		} else if (!recomendador.equals( other.recomendador ))
			return false;
		return true;
	}
}