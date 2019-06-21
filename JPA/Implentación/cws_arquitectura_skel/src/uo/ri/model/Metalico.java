package uo.ri.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Clase Metálico. Extiende de la clase MedioPago, implementando pues el método
 * pagar. Posee un id (long), un acumulado (double), unos cargos(Set<Cargo>), un
 * cliente (Cliente) y un DType (String)(será el discriminante)
 * 
 * @author Adán
 *
 */
@Entity
@DiscriminatorValue("TMetalicos")
@Table(name = "TMETALICOS")
public class Metalico extends MedioPago {

	/**
	 * Constructor de la clase Metalico con un parametro (Cliente). Efectuará la
	 * relación entre el cliente y el metálico.
	 * 
	 * @param cliente
	 *            cliente poseedor del metálico (Cliente)
	 */
	public Metalico(Cliente cliente) {
		super();
		Association.Pagar.link( cliente, this );
	}

	/**
	 * Constructor vacío de la clase Metalico.
	 */
	Metalico() {
	}

	/**
	 * Método pagar. Se le pasará el importe a pagar como parámetro y se le
	 * sumará al acumulado total del metálico. No hay ninguna restricción de
	 * pago.
	 */
	@Override
	public void pagar(double importe) {
		acumulado += importe;
	}
}
