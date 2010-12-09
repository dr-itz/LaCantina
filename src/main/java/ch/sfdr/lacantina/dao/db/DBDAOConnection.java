package ch.sfdr.lacantina.dao.db;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.IDAOConnection;
import ch.sfdr.lacantina.dao.IUserDAO;

/**
 * DAO connection connected via JDBC to a database
 * @author D.Ritz
 */
public class DBDAOConnection
	extends DBConnection
	implements IDAOConnection
{
	/**
	 * creates the connection, connected to the database
	 * @throws DAOException
	 */
	public DBDAOConnection()
		throws DAOException
	{
		super();
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IDAOConnection#getUserDAO()
	 */
	public IUserDAO getUserDAO()
	{
		return new DbUserDAO(conn);
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IDAOConnection#close()
	 */
	public void close()
	{
		closeConnection();
	}
}
