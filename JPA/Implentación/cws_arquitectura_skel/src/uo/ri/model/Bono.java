package uo.ri.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.util.exception.BusinessException;

/**
 * Clase Bono. Hereda de la clase abstracta 'MedioPago'. Implementa el método
 * 'pagar'.
 * 
 * @author Adán
 *
 */
@Entity
@DiscriminatorValue("TBonos")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "CODIGO") }, name = "TBonos")
public class Bono extends MedioPago {

	private double disponible = 0.0;
	private String descripcion = "";
	private String codigo;

	/**
	 * Constructor vacíio de la clase Bono
	 */
	Bono() {
	}

	/**
	 * Constructor de la clase Bono con un parámetro (Cliente). Se hará efectiva
	 * la relación entre el cliente y el bono
	 * 
	 * @param cliente
	 *            cliente poseedor del bono (Cliente)
	 */
	public Bono(Cliente cliente) {
		super();
		Association.Pagar.link( cliente, this );
	}

	/**
	 * Constructor de la clase Bono con dos parámetros (String, disponible)
	 * 
	 * @param codigo
	 *            codigo del bono (String)
	 * @param disponible
	 *            dinero que poseerá el bono (double)
	 */
	public Bono(String codigo, double disponible) {
		this.codigo = codigo;
		this.disponible = disponible;
	}

	/**
	 * Constructor de la clase Bono con tres parámetros (String, String, double)
	 * 
	 * @param codigo
	 *            código del bono (String)
	 * @param descripcion
	 *            descripción del bono (String)
	 * @param disponible
	 *            dinero que poseerá el bono (double)
	 */
	public Bono(String codigo, String descripcion, double disponible) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.disponible = disponible;
	}

	/**
	 * Constructor de la clase bono con cuatro parámetros (Cliente, String,
	 * String, double). Llamará al constructor con un solo parámetro pasándole
	 * el cliente poseedor del bono (Cliente)
	 * 
	 * @param cliente
	 *            cliente poseedor del bono (Cliente)
	 * @param codigo
	 *            código del bono (String)
	 * @param descripcion
	 *            descripción del bono (String)
	 * @param disponible
	 *            dinero que poseerá el bono (double)
	 */
	public Bono(Cliente cliente, String codigo, String descripcion,
			double disponible) {
		this( cliente );
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.disponible = disponible;
	}

	/**
	 * Constructor de bono pasándole su código (String)
	 * 
	 * @param codigo
	 *            código del bono (String)
	 */
	public Bono(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Devuelve el dinero que tiene disponible el bono
	 * 
	 * @return dinero disponible del bono (double)
	 */
	public Double getDisponible() {
		return disponible;
	}

	/**
	 * Establece la cantidad que el bono tendrá como disponible
	 * 
	 * @param disponible
	 *            cantidad que el bono tendrá como disponible (double)
	 */
	public void setDisponible(double disponible) {
		this.disponible = disponible;
	}

	/**
	 * Devuelve la descripción que posee el bono
	 * 
	 * @return descripción del bono (String)
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción del bono
	 * 
	 * @param descripcion
	 *            descripción del bono (String)
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve el código del bono
	 * 
	 * @return código del bono (String)
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Establece el código del bono
	 * 
	 * @param codigo
	 *            código del bono (String)
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método 'pagar' definifido en la clase abstracta 'MedioPago'. Disminuye el
	 * disponible del bono en la cantidad pasada como parámetro. Se lanzará una
	 * BusinessException ("No hay saldo suficiente en el bono") si la cantidad
	 * disponibleque posee el bono es inferior a la cantidad introducida
	 */
	@Override
	public void pagar(double cantidad) throws BusinessException {
		if (cantidad > getDisponible()) {
			throw new BusinessException( "No hay saldo suficiente en el bono" );
		}
		setDisponible( getDisponible() - cantidad );
		acumulado += cantidad;
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
		Bono other = (Bono) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals( other.codigo ))
			return false;
		return true;
	}

	/**
	 * Método toString. Información acerca del saldo disponible, la descripción
	 * y código
	 */
	@Override
	public String toString() {
		return "Bono [disponible=" + disponible + ", descripcion="
				+ descripcion + ", codigo=" + codigo + "]";
	}
}
