package uo.ri.business.impl.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistentFactory;
import uo.ri.persistence.impl.BonosGatewayImpl;

public class ListarTodosBonos {

	/**
	 * Listar todos los bonos de todos los clientes. Se especificará: 1) dni del
	 * cliente con el bono 2)nombre del cliente poseedor del bono
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> execute() 
	{

		BonosGatewayImpl bonos = PersistentFactory.getBonosImpl();
		Connection c = null;
		List<Map<String, Object>> todosLosBonos = null;
		try {
			c = Jdbc.getConnection();
			bonos.setConnection(c);
			todosLosBonos = bonos.listarTodosBonos();
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			Jdbc.close(c);
		}
		return todosLosBonos;
	}

}
