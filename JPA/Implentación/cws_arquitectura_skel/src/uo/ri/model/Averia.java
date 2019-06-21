package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.util.exception.BusinessException;
import uo.ri.model.types.AveriaStatus;
import uo.ri.model.types.FacturaStatus;

/**
 * Clase Avería. Tendrá una relación uno a muchos con las intervenciones. Por
 * otro lado, tendrá una relación muchos a uno con Vehículo, Mecanico y Factura.
 * 
 * @author Adán
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "FECHA, VEHICULO_ID") }, name = "TAVERIAS")
public class Averia {

	private String descripcion;
	private Date fecha = new Date();
	private double importe = 0.0;
	@Enumerated(EnumType.STRING)
	private AveriaStatus status = AveriaStatus.ABIERTA;
	private boolean usada = false;
	@OneToMany(mappedBy = "averia")
	private Set<Intervencion> intervenciones = new HashSet<Intervencion>();
	@ManyToOne()
	private Vehiculo vehiculo;
	@ManyToOne()
	private Mecanico mecanico;
	@ManyToOne()
	private Factura factura;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Constructor de Averia vacío
	 */
	Averia() {
	}

	/**
	 * Devuelve el id de la avería
	 * 
	 * @return id identificador de la avería (Long)
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Constructor de Avería con un sólo parámetro (Vehiculo)
	 * 
	 * @param vehiculo
	 *            vehiculo que posee la averia (Vehiculo)
	 */
	public Averia(Vehiculo vehiculo) {
		super();
		this.vehiculo = vehiculo;
		Association.Averiar.link( vehiculo, this );
	}

	/**
	 * Constructor de Avería con dos parámetro (Vehiculo, String). Llamará al
	 * constructor con un sólo parámetro pasándole el vehículo (Vehiculo)
	 * 
	 * @param vehiculo
	 *            vehiculo que posee la averia (Vehiculo)
	 * @param descripción
	 *            descripción de la avería (String)
	 */
	public Averia(Vehiculo vehiculo, String descripcion) {
		this( vehiculo );
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve las intervenciones que posee la avería
	 * 
	 * @return intervenciones de la avería (Set<Intervencion>)
	 */
	Set<Intervencion> _getIntervenciones() {
		return intervenciones;
	}

	void _setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	/**
	 * Asigna la averia a un mecánico Sólo se podrá asignar cuando la avería
	 * esté en estado ABIERTA.
	 * 
	 * @param mecanico
	 *            mecanico a quien asignar la avería (Mecanico)
	 */
	public void assignTo(Mecanico mecanico) {
		if (status.equals( AveriaStatus.ABIERTA )) {
			status = AveriaStatus.ASIGNADA;
			Association.Asignar.link( mecanico, this );
		}
	}

	/**
	 * El mecánico da por finalizada esta avería. Una vez cerrada se calculará
	 * el importe y se la cambiará el estado a TERMINADA. Para ello la avería
	 * tendrá que estar en estado ASIGNADA.
	 */
	public void markAsFinished() {

		if (status.equals( AveriaStatus.ASIGNADA )) {
			Association.Asignar.unlink( mecanico, this );
			calcularImporte();
			status = AveriaStatus.TERMINADA;
		}
	}

	/**
	 * Calcular el importe de las intervenciones. El importe será igual a la
	 * suma del importe de las intervenciones que la componen.
	 */
	public void calcularImporte() {

		double importe = 0.0;

		for (Intervencion intervencion : intervenciones) {
			importe += intervencion.getImporte();
		}
		this.importe = importe;
	}

	/**
	 * Una averia en estado TERMINADA se puede asignar a otro mecánico (el
	 * primero no ha podido terminar la reparación), pero debe ser pasada a
	 * ABIERTA primero
	 */
	public void reopen() {
		if (status.equals( AveriaStatus.TERMINADA )) {
			status = AveriaStatus.ABIERTA;
		}
	}

	/**
	 * Una avería ya facturada se elimina de la factura.
	 */
	public void markBackToFinished() {
		if (status.equals( AveriaStatus.FACTURADA )) {
			status = AveriaStatus.TERMINADA;
		}
	}

	/**
	 * Devuelve el mecánico encargado de la avería
	 * 
	 * @return mecánico encargado de la avería (Mecanico)
	 */
	public Mecanico getMecanico() {
		return mecanico;
	}

	/**
	 * Devuelve el vehículo poseedor de la avería
	 * 
	 * @return vehículo poseedor de la avería (Vehiculo)
	 */
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	/**
	 * Le asocia un vehículo a la avería
	 * 
	 * @param vehiculo
	 *            vehículo de la avería (Vehiculo)
	 */
	void _setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	/**
	 * Devuelve el estado de la avería. El estado podrá ser: ABIERTA, ASIGNADA,
	 * TERMINADA o FACTURADA
	 * 
	 * @return estado de la avería (AveriaStatus)
	 */
	public AveriaStatus getStatus() {
		return status;
	}

	/**
	 * Establece un estado a la avería. El estado podrá ser: ABIERTA, ASIGNADA,
	 * TERMINADA o FACTURADA.
	 * 
	 * @param status
	 *            estado de la avería (AveriaStatus)
	 */
	public void setStatus(AveriaStatus status) {
		this.status = status;
	}

	/**
	 * Devuelve la factura a la que pertenece la avería
	 * 
	 * @return factura en la que está la avería (Factura)
	 */
	public Factura getFactura() {
		return factura;
	}

	/**
	 * Establece una factura a una avería
	 * 
	 * @param factura
	 *            factura que poseerá la avería (Factura)
	 */
	public void setFactura(Factura factura) {
		this.factura = factura;

	}

	/**
	 * Devuelve la descripción de la avería
	 * 
	 * @return descripción de la avería (String)
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece una descripción a la avería
	 * 
	 * @param descripcion
	 *            descripción de la avería (String)
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve una copia de la fecha de la avería
	 * 
	 * @return fecha de la avería (Date)
	 */
	public Date getFecha() {
		return (Date) fecha.clone();
	}

	/**
	 * Establece una fecha a la avería
	 * 
	 * @param fecha
	 *            fecha de la avería (Date)
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve el importe de la avería
	 * 
	 * @return importe de la avería (double)
	 */
	public double getImporte() {
		calcularImporte();
		return importe;
	}

	/**
	 * Devuelve una copia de las intervenciones que componen una avería
	 * 
	 * @return intervenciones de una avería (Set<Intervencion>)
	 */
	public Set<Intervencion> getIntervenciones() {
		return new HashSet<Intervencion>( intervenciones );
	}

	/**
	 * Desasocia la avería y el mecánico que está a su cargo
	 */
	public void desassign() {
		Association.Asignar.unlink( mecanico, this );
	}

	/**
	 * Marca una factura como facturada. Si la factura no existe, se lanzará una
	 * BusinessException
	 * 
	 * @throws BusinessException
	 *             "No factura asignada" si la factura es null
	 */
	public void markAsInvoiced() throws BusinessException {
		if (factura == null)
			throw new BusinessException( "No factura asignada" );
		status = AveriaStatus.FACTURADA;
	}

	/**
	 * Devuelve si una avería es elegible para bono o no. Será elegida para bono
	 * si está facturada, si no ha sido usada previamente y si está abonada
	 * 
	 * @return true: es elegible para bono. false: no es elegible para bono
	 *         (boolean)
	 */
	public boolean esElegibleParaBono3() {
		if (status.equals( AveriaStatus.FACTURADA ) && !usada
				&& factura.getStatus().equals( FacturaStatus.ABONADA ))
			return true;
		return false;
	}

	/**
	 * Se marcará la avería como usada para bono si no ha sido usada previamente
	 */
	public void markAsBono3Used() {
		if (!usada)
			usada = true;
	}

	/**
	 * Devuelve si la avería ha sido usada o no para generar bonos.
	 * 
	 * @return usada true: ha sido usada. false: no ha sido usada (boolean)
	 */
	public boolean getUsada() {
		return usada;
	}

	@Override
	public String toString() {
		return "Averia [descripcion=" + descripcion + ", fecha=" + fecha
				+ ", importe=" + importe + ", status=" + status + ", usada="
				+ usada + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( fecha == null ) ? 0 : fecha.hashCode() );
		result = prime * result
				+ ( ( vehiculo == null ) ? 0 : vehiculo.hashCode() );
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
		Averia other = (Averia) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals( other.fecha ))
			return false;
		if (vehiculo == null) {
			if (other.vehiculo != null)
				return false;
		} else if (!vehiculo.equals( other.vehiculo ))
			return false;
		return true;
	}

	public boolean isUsadaBono3() {
		return usada;
	}

	/**
	 * Devuelve si la avería está facturada o no.
	 * 
	 * @return true: está facturada. false: no está facturada (boolean)
	 */
	public boolean isInvoiced() {
		if (status.equals( AveriaStatus.FACTURADA ))
			return true;
		return false;
	}
}
