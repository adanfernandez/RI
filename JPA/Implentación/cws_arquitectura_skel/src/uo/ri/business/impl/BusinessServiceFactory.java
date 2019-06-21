package uo.ri.business.impl;

import uo.ri.business.AdminService;
import uo.ri.business.CashService;
import uo.ri.business.ForemanService;
import uo.ri.business.MechanicService;
import uo.ri.business.ServiceFactory;

public class BusinessServiceFactory implements ServiceFactory {

	/**
	 * Devuelve una instancia de AdminService
	 */
	@Override
	public AdminService forAdmin() {
		return new AdminServiceImpl();
	}

	/**
	 * Devuelve una instancia de CashService
	 */
	@Override
	public CashService forCash() {
		return new CashServiceImpl();
	}

	/**
	 * Devuelve una instancia de ForemanService. No está implementado
	 */
	@Override
	public ForemanService forForeman() {
		throw new RuntimeException( "Not yet implemented" );
	}

	/**
	 * Devuelve una instancia de MechanicService. No está implementado
	 */
	@Override
	public MechanicService forMechanic() {
		throw new RuntimeException( "Not yet implemented" );
	}
}
