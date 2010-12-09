package ch.sfdr.lacantina.dao;

/**
 * Interface defining a factory on how IDAOConnections are created
 * @author D.Ritz
 */
public interface IDAOConnectionFactory
{
	IDAOConnection createConnection()
		throws DAOException;
}
