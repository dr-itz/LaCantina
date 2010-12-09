package ch.sfdr.lacantina.dao.db;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.IDAOConnection;
import ch.sfdr.lacantina.dao.IDAOConnectionFactory;

/**
 * A DAO connection factory returning a JDBC database connected IDAOConnection
 * @author D.Ritz
 */
public class DBDAOConnectionFactory
	implements IDAOConnectionFactory
{
	/*
	 * @see ch.sfdr.lacantina.dao.IDAOConnectionFactory#createConnection()
	 */
	public IDAOConnection createConnection()
		throws DAOException
	{
		return new DBDAOConnection();
	}
}
