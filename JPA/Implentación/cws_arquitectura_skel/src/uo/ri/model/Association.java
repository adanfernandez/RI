package uo.ri.model;

import uo.ri.model.types.AveriaStatus;

/**
 * Clase Association.
 * 
 * @author Adán
 *
 */
public class Association {

	/**
	 * Modifica las relaciones entre un cliente y un vehículo dados. La relación
	 * entre un cliente y un vehículo es uno a muchos. La relación entre un
	 * vehículo y un cliente es muchos a uno.
	 * 
	 * @author Adán
	 *
	 */
	public static class Poseer {

		/**
		 * Establece las relaciones entre un cliente y un vehículo.
		 * 
		 * @param cliente
		 *            cliente propietario del vehículo (Cliente)
		 * @param vehiculo
		 *            vehículo del cliente (Vehiculo)
		 */
		public static void link(Cliente cliente, Vehiculo vehiculo) {
			vehiculo.setCliente( cliente );
			cliente._getVehiculos().add( vehiculo );
		}

		/**
		 * Elimina una relación ya existente entre un cliente y vehículo.
		 * 
		 * @param cliente
		 *            cliente propietario del vehículo (Cliente)
		 * @param vehiculo
		 *            vehículo del cliente (Vehiculo)
		 */
		public static void unlink(Cliente cliente, Vehiculo vehiculo) {
			cliente._getVehiculos().remove( vehiculo );
			vehiculo.setCliente( null );
		}
	}

	/**
	 * Modifica las relaciones que hay entre un vehículo y el tipo al que
	 * pertenece.La relación entre un tipo de vehículo y un vehículo es uno a
	 * muchos. La relación entre un vehículo y un tipo de vehículo es muchos a
	 * uno.
	 * 
	 * @author Adán
	 *
	 */
	public static class Clasificar {

		/**
		 * Establece la relación entre un vehículo y el tipo al que pertenece.
		 * 
		 * @param tipoVehiculo
		 *            tipo del vehílo (TipoVehiculo)
		 * @param vehiculo
		 *            vehículo al que se le especificará el tipo (Vehiculo)
		 */
		public static void link(TipoVehiculo tipoVehiculo, Vehiculo vehiculo) {
			vehiculo.setTipoVehiculo( tipoVehiculo );
			tipoVehiculo._getVehiculos().add( vehiculo );
		}

		/**
		 * Elimina la relación ya existente entre un vehículo y su tipo.
		 * 
		 * @param tipoVehiculo
		 *            tipo del vehílo (TipoVehiculo)
		 * @param vehiculo
		 *            vehículo al que se le especificará el tipo (Vehiculo)
		 */
		public static void unlink(TipoVehiculo tipoVehiculo, Vehiculo vehiculo) {
			tipoVehiculo._getVehiculos().remove( vehiculo );
			vehiculo.setTipoVehiculo( null );
		}
	}

	/**
	 * Modifica las relaciones entre un cliente y un medio de pago dados. La
	 * relación entre un cliente y un medio de pago es uno a muchos. Por el
	 * contrario, la relación entre un medio de pago y un cliente es muchos a
	 * uno.
	 * 
	 * @author Adán
	 *
	 */
	public static class Pagar {

		/**
		 * Elimina una relación ya existente entre un cliente y un medio de
		 * pago.
		 * 
		 * @param cliente
		 *            cliente poseedor del medio de pago (Cliente)
		 * @param mp
		 *            medio de pago del cliente (MedioPago)
		 */
		public static void unlink(Cliente cliente, MedioPago mp) {

			cliente._getMediosPago().remove( mp );
			mp._setCliente( null );
		}

		/**
		 * Establece una relación entre un cliente y un medio de pago dados.
		 * 
		 * @param cliente
		 *            cliente poseedor del medio de pago (Cliente)
		 * @param mp
		 *            medio de pago del cliente (MedioPago)
		 */
		public static void link(Cliente cliente, MedioPago mp) {

			mp._setCliente( cliente );
			cliente._getMediosPago().add( mp );
		}
	}

	/**
	 * Modifica las relaciones que hay entre un vehículo y sus averías. La
	 * relación entre un vehículo y una avería es uno a muchos. La relación
	 * entre una avería y un vehículo es muchos a uno.
	 * 
	 * @author Adán
	 *
	 */
	public static class Averiar {

		/**
		 * Elimina una relación ya existente entre un vehículo y una avería
		 * dados.
		 * 
		 * @param vehiculo
		 *            vehiculo que tendrá la avería (Vehiculo)
		 * @param averia
		 *            averia del vehículo (Averia)
		 */
		public static void unlink(Vehiculo vehiculo, Averia averia) {
			vehiculo._getAverias().remove( averia );
			vehiculo.setNumAverias( vehiculo.getNumAverias() - 1 );
			averia._setVehiculo( null );
		}

		/**
		 * Establece una relación entre un vehículo y una averia dados.
		 * 
		 * @param vehiculo
		 *            vehiculo que tendrá la avería (Vehiculo)
		 * @param averia
		 *            averia del vehículo (Averia)
		 */
		public static void link(Vehiculo vehiculo, Averia averia) {
			averia._setVehiculo( vehiculo );
			vehiculo._getAverias().add( averia );
			vehiculo.setNumAverias( vehiculo.getNumAverias() + 1 );

		}
	}

	/**
	 * Modifica las relaciones entre una factura y las averías que la componen.
	 * La relación entre una avería y una factura es muchos a uno. La relación
	 * entre una factura y una avería es una a muchos.
	 * 
	 * @author Adán
	 *
	 */
	public static class Facturar {

		/**
		 * Elimina una relación existente entre una factura y una avería dados.
		 * 
		 * @param factura
		 *            factura poseedor de la avería (Factura)
		 * @param averia
		 *            avería que compondrá la factura (Averia)
		 */
		public static void unlink(Factura factura, Averia averia) {

			factura._getAverias().remove( averia );
			averia.setFactura( null );
			averia.markBackToFinished();
		}

		/**
		 * Establece una relación entre una factura y avería dados.
		 * 
		 * @param factura
		 *            factura poseedor de la avería (Factura)
		 * @param averia
		 *            avería que compondrá la factura (Averia)
		 */
		public static void link(Factura factura, Averia averia) {

			averia.setStatus( AveriaStatus.FACTURADA );
			averia.setFactura( factura );
			factura._getAverias().add( averia );
		}
	}

	/**
	 * Modifica la relación que tendrá un cargo con las facturas y con los
	 * medios de pago. La relación entre un cargo con un medio de pago y una
	 * factura es muchos a uno. Por el contrario, la relación entre una factura
	 * y un medio de pago con un cargo es uno a muchos.
	 * 
	 * @author Adán
	 *
	 */
	public static class Cargar {

		/**
		 * Elimina las relaciones que un cargo pueda tener con todas las
		 * facturas y medios de pago.
		 * 
		 * @param cargo
		 *            cargo del que se eliminarán todas las relacion (Cargo).
		 */
		public static void unlink(Cargo cargo) {
			cargo.getFactura()._getCargos().remove( cargo );
			cargo.getMedioPago()._getCargos().remove( cargo );
			cargo.setFactura( null );
			cargo.setMedioPago( null );
		}

		/**
		 * Establece una relación entre una factura y un medio de pago
		 * utilizando la clase Cargo.
		 * 
		 * @param factura
		 *            factura la cual tendrá varios cargos (Factura).
		 * @param medioPago
		 *            medios de pago con los cuales se pagará la factura
		 *            (MedioPago).
		 * @param cargo
		 *            instancia que sirve como relación entre la factura y los
		 *            medios de pago (Cargo)
		 */
		public static void link(Factura factura, MedioPago medioPago,
				Cargo cargo) {
			cargo.setFactura( factura );
			cargo.setMedioPago( medioPago );
			medioPago._getCargos().add( cargo );
			factura._getCargos().add( cargo );
		}

		/**
		 * Deshace una relación entre una factura y un medio de pago utilizando
		 * la clase Cargo.
		 * 
		 * @param factura
		 *            factura la cual tendrá varios cargos (Factura).
		 * @param medioPago
		 *            medios de pago con los cuales se pagará la factura
		 *            (MedioPago).
		 * @param cargo
		 *            instancia que sirve como relación entre la factura y los
		 *            medios de pago (Cargo)
		 */
		public static void unlink(Factura factura, MedioPago medioPago,
				Cargo cargo) {
			medioPago._getCargos().remove( cargo );
			factura._getCargos().remove( cargo );
			cargo.setFactura( null );
			cargo.setMedioPago( null );
		}
	}

	/**
	 * Modifica las relaciones entre un mecánico y una avería de la cual se
	 * tendrá que hacer cargo. La relación entre un mecánico y una avería es
	 * muchos a uno, mientras que una avería con un mecánico tiene una relación
	 * uno a muchos.
	 * 
	 * @author Adán
	 *
	 */
	public static class Asignar {

		/**
		 * Establece una relación entre un mecánico y una avería. El mecánico se
		 * hará cargo de ella.
		 * 
		 * @param mecanico
		 *            mecánico el cual se hará cargo de la avería (Mecanico)
		 * @param averia
		 *            averia que se asignará al mecánico (Averia)
		 */
		public static void link(Mecanico mecanico, Averia averia) {
			mecanico._getAsignadas().add( averia );
			averia._setMecanico( mecanico );
		}

		/**
		 * Elimina una relación ya existente entre un mecanico y una avería
		 * dados.
		 * 
		 * @param mecanico
		 *            mecanico que tiene asignada la avería (Mecanico)
		 * @param averia
		 *            averia que posee el mecánico (Averia)
		 */
		public static void unlink(Mecanico mecanico, Averia averia) {

			mecanico._getAsignadas().remove( averia );
			averia._setMecanico( null );

		}
	}

	/**
	 * Modifica una relación entre un mecánico, una avería y las intervenciones
	 * que éste ha necesitado para solucionarla. La relación de una intervención
	 * con una avería y con un mecánico es muchos a uno. Por el contrario, la
	 * relación de un mecánico y de una avería con una intervención es uno a
	 * muchos.
	 * 
	 * @author Adán
	 *
	 */
	public static class Intervenir {

		/**
		 * Establece una intervención que el mecánico ha necesitado para
		 * solucionar la avería.
		 * 
		 * @param mecanico
		 *            mecánico que se hace cargo de la avería (Mecanico)
		 * @param intervencion
		 *            intervención del mecánico (Intervencion)
		 * @param averia
		 *            averia que está asignada al mecánico (Averia)
		 */
		public static void link(Mecanico mecanico, Intervencion intervencion,
				Averia averia) {
			intervencion._setMecanico( mecanico );
			intervencion._setAveria( averia );
			mecanico._getIntervenciones().add( intervencion );
			averia._getIntervenciones().add( intervencion );
		}

		/**
		 * Deshace una intervención que el mecánico ha necesitado para
		 * solucionar la avería.
		 * 
		 * @param mecanico
		 *            mecánico que se hace cargo de la avería (Mecanico)
		 * @param intervencion
		 *            intervención del mecánico (Intervencion)
		 * @param averia
		 *            averia que está asignada al mecánico (Averia)
		 */
		public static void unlink(Intervencion intervencion) {
			intervencion.getAveria()._getIntervenciones().remove( intervencion );
			intervencion.getMecanico()._getIntervenciones()
					.remove( intervencion );
			intervencion._setAveria( null );
			intervencion._setMecanico( null );
		}
	}

	/**
	 * Modifica la relación que hay entre una sustitución, un repuesto y una
	 * intervención. La relación entre una sustitución con un repuesto y con una
	 * intervención es muchos a uno. Sin embargo, la relación de un repuesto y
	 * una intervención con una susticuón es uno a muchos.
	 * 
	 * @author Adán
	 *
	 */
	public static class Sustituir {

		/**
		 * Elimina todas las relaciones que posee una sustitución, tanto como
		 * con las intervenciones como con los repuestos.
		 * 
		 * @param sustitucion
		 *            sustitución a la cual se le eliminarán las relaciones
		 *            (Sustitucion).
		 */
		public static void unlink(Sustitucion sustitucion) {
			sustitucion.getRepuesto()._getSustituciones().remove( sustitucion );
			sustitucion.getIntervencion()._getSustituciones()
					.remove( sustitucion );
			sustitucion._setIntervencion( null );
			sustitucion._setRepuesto( null );
		}

		/**
		 * Establece una relación entre una intervención y un repuesto através
		 * de una sustitución.
		 * 
		 * @param sustitucion
		 *            relación de sustitución de una intervención con un
		 *            repuesto (Sustitucion)
		 * @param intervencion
		 *            intervención que tendrá los repuestos (Intervencion)
		 * @param repuesto
		 *            repuesto que tendrá la intervención (Repuesto)
		 */
		public static void link(Sustitucion sustitucion,
				Intervencion intervencion, Repuesto repuesto) {
			sustitucion._setIntervencion( intervencion );
			sustitucion._setRepuesto( repuesto );
			sustitucion.getRepuesto()._getSustituciones().add( sustitucion );
			sustitucion.getIntervencion()._getSustituciones().add( sustitucion );
		}
	}
}
