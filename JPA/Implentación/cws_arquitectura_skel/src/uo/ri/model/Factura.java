package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.util.exception.BusinessException;
import uo.ri.model.types.AveriaStatus;
import uo.ri.model.types.FacturaStatus;
import alb.util.date.DateUtil;
import alb.util.math.Round;

/**
 * Clase Factura. Tendrá una relación uno a muchos con la clase Averia y una
 * relación uno a muchos con la clase Cargo.
 * 
 * @author Adán
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "NUMERO") }, name = "TFACTURAS")
public class Factura {

	private Long numero;
	private Date fecha;
	private double importe;
	private double iva;
	private boolean usada = false;
	@Enumerated(EnumType.STRING)
	private FacturaStatus status = FacturaStatus.SIN_ABONAR;
	@OneToMany(mappedBy = "factura")
	private Set<Averia> averias = new HashSet<Averia>();
	@OneToMany(mappedBy = "factura")
	private Set<Cargo> cargos = new HashSet<Cargo>();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Constructor vacío de la clase Factura.
	 */
	Factura() {
	}

	/**
	 * Devolver el Id de la factura.
	 * 
	 * @return id identificador de la factura (Long).
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Constructor de la clase Factura con un sólo parámetro (long). La fecha de
	 * la factura será el día en que se cree.
	 * 
	 * @param numero
	 *            número de la factura (integer)
	 */
	public Factura(long numero) {
		super();
		this.numero = numero;
		this.fecha = DateUtil.today();
	}

	/**
	 * Constructor de la clase Factura con dos parámetros (long, Date). Llamará
	 * al constructor con un sólo parámetro (long) pasándole el número de la
	 * factura
	 * 
	 * @param numero
	 *            numero de la factura (long).
	 * @param fecha
	 *            fecha de la factura (fecha).
	 */
	public Factura(long numero, Date fecha) {
		this( numero );
		this.fecha = fecha;
	}

	/**
	 * Constructo de la clase Factura con dos parámetros (long, List<Averia>)
	 * 
	 * @param numero
	 *            numero de la factura (long)
	 * @param averias
	 *            lista con las averías que componen la factura (List<Averia>)
	 * @throws BusinessException
	 *             si alguna de las averías pasadas no está terminada
	 */
	public Factura(long numero, List<Averia> averias) throws BusinessException {
		this( numero );
		for (Averia averia : averias) {
			addAveria( averia );
		}
	}

	/**
	 * Constructor de la clase Factura con tres parámetros
	 * 
	 * @param numero
	 *            número de la factura (long)
	 * @param date
	 *            fecha de la factura (Date)
	 * @param averias
	 *            averías que componen la factura (List<Averia>)
	 * @throws BusinessException
	 *             se lanzará si alguna de las averías no está terminada
	 */
	public Factura(long numero, Date date, List<Averia> averias)
			throws BusinessException {
		this( numero );
		this.fecha = date;
		for (Averia averia : averias) {
			addAveria( averia );
		}
	}

	/**
	 * Añade la averia a la factura
	 * 
	 * @param averia
	 * @throws BusinessException
	 *             si la avería no está terminada
	 *             ("La avería no está terminada")
	 */
	public void addAveria(Averia averia) throws BusinessException {

		if (!averia.getStatus().equals( AveriaStatus.TERMINADA )) {
			throw new BusinessException( "La avería no está terminada" );
		}

		Association.Facturar.link( this, averia );
		calcularImporte();
	}

	/**
	 * Calcula el importe de la avería (IVA incluido) teniendo en cuenta la
	 * fecha de factura. Si la fecha es posterior al 1 de julio de 2012, el IVA
	 * será del 21%. De otro modo será del 18%
	 */
	void calcularImporte() {

		if (DateUtil.isAfter( DateUtil.fromDdMmYyyy( 1, 7, 2012 ), fecha )) {
			iva = 0.18;
		} else {
			iva = 0.21;
		}

		double importe = 0.0;
		for (Averia averia : averias) {
			averia.calcularImporte();
			importe += averia.getImporte();
		}

		importe += importe * iva;
		this.importe = importe;
	}

	/**
	 * Elimina una averia de la factura. Para ello la factura SIN_ABONAR.
	 * Además, recalcula el importe
	 * 
	 * @param averia
	 *            avería a eliminar (Averia)
	 */
	public void removeAveria(Averia averia) {
		if (status.equals( FacturaStatus.SIN_ABONAR )) {
			Association.Facturar.unlink( this, averia );
			calcularImporte();
		}
	}

	/**
	 * Obbtener una copia de los cargos de una factura.
	 * 
	 * @return los cargos de la avería (Set<Cargo>)
	 */
	public Set<Cargo> getCargos() {
		return new HashSet<Cargo>( cargos );
	}

	/**
	 * Obtener los cargos de una factura.
	 * 
	 * @return cargos los cargos de una factura (Set<Cargo>).
	 */
	Set<Cargo> _getCargos() {
		return cargos;
	}

	/**
	 * Obtener una copa de las averias de una factura.
	 * 
	 * @return averias averias de la factura (Set<Averia)
	 */
	public Set<Averia> getAverias() {
		return new HashSet<Averia>( averias );
	}

	/**
	 * Devuelve el importe de la factura, con un redondeo de 2 decimales (2
	 * céntimos)
	 * 
	 * @return importe de la factura (double)
	 */
	public double getImporte() {
		return Round.twoCents( importe );
	}

	/**
	 * Obtener las averias de la factura.
	 * 
	 * @return averias averias de la factura (Set<Averia)
	 */
	Set<Averia> _getAverias() {
		return averias;
	}

	/**
	 * Obtener el estado de la factura. Podrá ser ABONADA o SIN_ABONAR.
	 * 
	 * @return status estado de la factura (FacturaStatus).
	 */
	public FacturaStatus getStatus() {
		return status;
	}

	/**
	 * Obtener una copia de la fecha de la factura.
	 * 
	 * @return fecha (Date).
	 */
	public Date getFecha() {
		return (Date) fecha.clone();
	}

	/**
	 * Establecer la fecha de la factura
	 * 
	 * @param fecha
	 *            fecha de la factura (Date)
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtener el hashCode de la factura utilizando el numero de esta.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( numero == null ) ? 0 : numero.hashCode() );
		return result;
	}

	/**
	 * Método equals de la factura. Utilizará el numero de la factura.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Factura other = (Factura) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals( other.numero ))
			return false;
		return true;
	}

	/**
	 * Método toString(). Devuelve el numero, la fecha, el iva y el status de la
	 * factura (String).
	 */
	@Override
	public String toString() {
		return "Factura [numero=" + numero + ", fecha=" + fecha + ", importe="
				+ importe + ", iva=" + iva + ", status=" + status + "]";
	}

	/**
	 * Devuelve el IVA aplicado sobre la factura.
	 * 
	 * @return iva iva aplicado sobre la factura (double)
	 */
	public double getIva() {
		return iva;
	}

	/**
	 * Devuelve el número de una factura.
	 * 
	 * @return numero numero de la factura (Long).
	 */
	public Long getNumero() {
		return numero;
	}

	/**
	 * Marcar una factura como abonada. Se puede marcar como liquidada una
	 * factura completamente pagada con margen de +-0,01 €.
	 * 
	 * @throws BusinessException
	 *             Si la factura ya ha sido abonada previamente:
	 *             ("Ya está abonada."). Si la factura no posee averías:
	 *             ("La factura no no tiene averias").
	 */
	public void settle() throws BusinessException {

		if (averias.size() == 0)
			throw new BusinessException( "La factura no tiene averias" );

		if (status.equals( FacturaStatus.ABONADA ))
			throw new BusinessException( "Ya está abonada" );

		double diferencia = redondear3Decimales( Math.abs( totalCargos()
				- getImporte() ) );

		if (diferencia <= 0.01)
			status = FacturaStatus.ABONADA;
		else {
			throw new BusinessException( "Los cargos no igualan el importe" );
		}

	}

	/**
	 * Devuelve si una factura está abonada o no.
	 * 
	 * @return true: está abonada. false: no está abonada (boolean).
	 */
	public boolean isSettled() {
		if (status.equals( FacturaStatus.ABONADA ))
			return true;
		return false;
	}

	/**
	 * Devuelve el total de los cargos de la factura.
	 * 
	 * @return total de los cargos (double).
	 */
	private double totalCargos() {
		double pagado = 0.0;
		for (Cargo cargo : cargos) {
			pagado += cargo.getImporte();
		}
		return pagado;
	}

	/**
	 * Una factura podrá ser usada para bono si está abonada, si su importe es
	 * mayor o igual que 500€ y si no ha sido usada previamente.
	 * 
	 * @return true: puede ser usada. false: no puede ser usada (boolean)
	 */
	public boolean puedeGenerarBono500() {
		if (!status.equals( FacturaStatus.ABONADA ))
			return false;

		if (importe >= 500.0 && !usada)
			return true;

		return false;
	}

	/**
	 * Marcar una factura usada para generar bonos.
	 * 
	 * @throws BusinessException
	 *             ("La factura no puede ser usada para bono.")
	 */
	public void markAsBono500Used() throws BusinessException {
		if (!puedeGenerarBono500())
			throw new BusinessException(
					"La factura no puede ser usada para bono." );
		usada = true;
	}

	/**
	 * Devuelve si una factura de 500€ fue usada para generar un bono o no.
	 * 
	 * @return usada (boolean)
	 */
	public boolean isBono500Used() {
		return usada;
	}

	/**
	 * Redondea un valor pasado por parámetro a tres decimales
	 * 
	 * @param valorInicial
	 *            valor que se quiere redondear (double)
	 * @return resultado del redondeo (double).
	 */
	private double redondear3Decimales(double valorInicial) {
		double parteEntera, resultado;
		resultado = valorInicial;
		parteEntera = Math.floor( resultado );
		resultado = ( resultado - parteEntera ) * Math.pow( 10, 3 );
		resultado = Math.round( resultado );
		resultado = ( resultado / Math.pow( 10, 3 ) ) + parteEntera;
		return resultado;
	}
}
