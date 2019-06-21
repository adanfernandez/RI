package uo.ri.conf;

import uo.ri.business.ServiceFactory;
import uo.ri.business.impl.CommandExecutorFactory;
import uo.ri.business.repository.RepositoryFactory;

/**
 * Clase Factory. Da acceso a RepositoryFactory, a ServiceFactory y a
 * CommandExecutorFactory
 * 
 * @author Adán
 *
 */
public class Factory {

	public static RepositoryFactory repository;
	public static ServiceFactory service;
	public static CommandExecutorFactory executor;

}
