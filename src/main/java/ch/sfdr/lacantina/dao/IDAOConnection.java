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
	 * get the DAO for wine cellar queries
	 * @return IWineCellarDAO
	 */
	IWineCellarDAO getWineCellarDAO();

	/**
	 * get the DAO for wine queries
	 * @return IWineDAO
	 */
	IWineDAO getWineDAO();

	/**
	 * get the DAO for cellar entry queries
	 * @return ICellarEntryDAO
	 */
	ICellarEntryDAO getCellarEntryDAO();

	/**
	 * closes the connection
	 */
	void close();
}
