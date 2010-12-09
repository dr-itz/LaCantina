package ch.sfdr.lacantina.dao;

import ch.sfdr.lacantina.dao.db.DBDAOConnectionFactory;

/**
 * DAO Connection factory. Defaults to return a JDBC connected IDAOConnection
 * @author D.Ritz
 */
public class DAOConnectionFactory
{
	private static IDAOConnectionFactory factory;
	static {
		// default to database
		factory = new DBDAOConnectionFactory();
	}

	/**
	 * returns the connection
	 * @return IDAOConnection
	 * @throws DAOException
	 */
	public static IDAOConnection getConnection()
		throws DAOException
	{
		return factory.createConnection();
	}

	/**
	 * Set the factory used to create IDAOConnections
	 * @param f the factory to use
	 */
	public static void setFactory(IDAOConnectionFactory f)
	{
		factory = f;
	}

	/*
	 * Usage example
	 * IDAOConnection conn = DAOConnectionFactory.getConnection();
	 * IUserDAO userDAO = conn.getUserDAO();
	 *
	 * User u = userDAO.getUser(123);
	 * ...
	 *
	 * conn.close();
	 */
}
