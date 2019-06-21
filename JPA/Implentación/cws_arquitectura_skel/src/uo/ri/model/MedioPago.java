package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.util.exception.BusinessException;

/**
 * Clase MedioPago. Tiene una relación muchos a uno con un cliente y uno a
 * muchos con la clase Cargo.
 * 
 * @author Adán
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TMEDIOSPAGO")
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING, length = 5)
public abstract class MedioPago {

	protected double acumulado = 0.0;
	@ManyToOne()
	protected Cliente cliente;
	@OneToMany(mappedBy = "medioPago")
	private Set<Cargo> cargos = new HashSet<Cargo>();
	private String dtype;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Devuelve el identificador del medio de pago
	 * 
	 * @return identificador del medio de pago (Long)
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Devuelve el cliente poseedor del medio de pago
	 * 
	 * @return cliente poseedor del medio de pago (Cliente)
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Establece un cliente a un medio de pago
	 * 
	 * @param cliente
	 *            cliente poseedor del medio de pago (Cliente)
	 */
	void _setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Devuelve una copia de los cargos del medio de pago
	 * 
	 * @return new HashSet<Cargo>(cargos)
	 */
	public Set<Cargo> getCargos() {
		return new HashSet<Cargo>( cargos );
	}

	/**
	 * Devuelve los cargos del medio de pago
	 * 
	 * @return cargos del medio de pago (Set<Cargo>)
	 */
	Set<Cargo> _getCargos() {
		return cargos;
	}

	/**
	 * Devuelve el acumulado que posee un medio de pago. Cada vez que se
	 * introduzca una cantidad con un medio de pago, el acumulado de este
	 * aumentará en la cantidad introducida.
	 * 
	 * @return acumulado acumulado total del medio de pago (double)
	 */
	public double getAcumulado() {
		return acumulado;
	}

	/**
	 * Establece un cliente al medio de pago
	 * 
	 * @param cliente
	 *            cliente poseedor del medio de pago (Cliente)
	 */
	void _setClientes(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Establece un acumulado al medio de pago. Este será el dinero total
	 * gastado por un medio de pago.
	 * 
	 * @param acumulado
	 *            acumulado del medio de pago (double)
	 */
	void setAcumulado(double acumulado) {
		this.acumulado = acumulado;
	}

	/**
	 * Método implementado por las subclases (Bono, Metalico, TarjetaCredito)
	 * 
	 * @param importe
	 *            dinero a pagar (double)
	 * @throws BusinessException
	 *             si no cumple los requisitos para pagar. En el caso de una
	 *             tarjeta de crédito, está no podrá estar caducada. En el caso
	 *             del bono, tendrá que tener suficiente dinero disponible para
	 *             hacer frente al pago.
	 */
	public abstract void pagar(double importe) throws BusinessException;

	/**
	 * Método toString() con el acumulado del medio de pago.
	 */
	@Override
	public String toString() {
		return "MedioPago [acumulado=" + acumulado + "]";
	}

	/**
	 * Devuelve el tipo del medio de pago. Este podrá ser 'TBonos',
	 * 'TTarjetasCredito' o 'TMetalico'
	 * 
	 * @return
	 */
	public String getDtype() {
		return dtype;
	}
}
