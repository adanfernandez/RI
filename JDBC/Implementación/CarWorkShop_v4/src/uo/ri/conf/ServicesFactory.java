package uo.ri.conf;

import uo.ri.business.AdminService;
import uo.ri.business.CashService;
import uo.ri.business.impl.AdminServiceImpl;
import uo.ri.business.impl.CashServiceImpl;

public class ServicesFactory {

	public ServicesFactory() {
	}

	/**
	 * Devuelve una instancia de AdminServiceImpl
	 * @return new AdminServiceImpl
	 */
	public static AdminService getAdminService() {
		return new AdminServiceImpl();
	}

	/**
	 * Devuelve una instancia de CashServiceImpl
	 * @return new CashServiceImpl
	 */
	public static CashService getCashService() {
		return new CashServiceImpl();
	}

}
