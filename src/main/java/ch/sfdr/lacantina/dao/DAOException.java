package ch.sfdr.lacantina.dao;

/**
 * Exception for anything related to DAO
 * @author D.Ritz
 */
public class DAOException
	extends Exception
{
	private static final long serialVersionUID = 1L;

	/**
	 * creates the exception from a message
	 * @param msg the message
	 */
	public DAOException(String msg)
	{
		super(msg);
	}

	/**
	 * create the exception from an exception
	 * @param cause the causing exception
	 */
	public DAOException(Exception cause)
	{
		super(cause);
	}
}
