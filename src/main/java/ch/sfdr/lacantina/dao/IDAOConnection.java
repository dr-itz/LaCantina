package ch.sfdr.lacantina.dao;

/**
 * DAO interface. Defines all methods the DAO system offers
 * @author D.Ritz
 */
public interface IDAOConnection
{
	/**
	 * get the DAO for user queries
	 * @return IUserDAO
	 */
	IUserDAO getUserDAO();

	/**
	 * closes the connection
	 */
	void close();
}
