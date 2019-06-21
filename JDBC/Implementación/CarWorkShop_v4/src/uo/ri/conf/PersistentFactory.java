package uo.ri.conf;

import uo.ri.persistence.impl.BonosGatewayImpl;
import uo.ri.persistence.impl.FacturasGatewayImpl;
import uo.ri.persistence.impl.MecanicosGatewayImpl;

public class PersistentFactory {

	/**
	 * Devuelve una instancia de FacturasGatewayImpl
	 * 
	 * @return new FacturasGatewayImpl
	 */
	public static FacturasGatewayImpl getFacturasGateway() {
		return new FacturasGatewayImpl();
	}

	/**
	 * Devuelve una instancia de MecanicosGatewayImpl
	 * 
	 * @return new MecanicosGatewayImpl
	 */
	public static MecanicosGatewayImpl getMecanicosGatewayImpl() {
		return new MecanicosGatewayImpl();
	}

	/**
	 * Devuelve una instancia de BonosGatewayImpl
	 * 
	 * @return new BonosGatewayImpl
	 */
	public static BonosGatewayImpl getBonosImpl() {
		return new BonosGatewayImpl();
	}

}
