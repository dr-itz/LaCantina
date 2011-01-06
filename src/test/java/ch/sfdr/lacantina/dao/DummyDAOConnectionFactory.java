package ch.sfdr.lacantina.dao;

/**
 * Dummy factory for testing
 * @author D.Ritz
 */
public class DummyDAOConnectionFactory
	implements IDAOConnectionFactory
{
	private DummyDAOConnection conn = new DummyDAOConnection();

	/*
	 * @see ch.sfdr.lacantina.dao.IDAOConnectionFactory#createConnection()
	 */
	public IDAOConnection createConnection()
		throws DAOException
	{
		return conn;
	}

	public DummyDAOConnection getConn()
	{
		return conn;
	}

	/**
	 * a dummy DAO connection, allowing to set the DAOs
	 */
	public static class DummyDAOConnection
		implements IDAOConnection
	{
		private boolean closed = false;
		private IUserDAO userDAO;
		private IWineCellarDAO winecellarDAO;
		private IWineDAO wineDAO;
		private ICellarEntryDAO cellarentryDAO;
		private IShoppingListDAO shoppingListDAO;

		public void close()
		{
			closed = true;
		}

		public boolean isClosed()
		{
			return closed;
		}

		public IUserDAO getUserDAO()
		{
			return userDAO;
		}

		public void setUserDAO(IUserDAO userDAO)
		{
			this.userDAO = userDAO;
		}

		public IWineDAO getWineDAO()
		{
			return wineDAO;
		}

		public void setWineDAO(IWineDAO wineDAO)
		{
			this.wineDAO = wineDAO;
		}

		public IWineCellarDAO getWineCellarDAO()
		{
			return winecellarDAO;
		}

		public void setWinecellarDAO(IWineCellarDAO winecellarDAO)
		{
			this.winecellarDAO = winecellarDAO;
		}

		public ICellarEntryDAO getCellarEntryDAO()
		{
			return cellarentryDAO;
		}

		public void setCellarEntryDAO(ICellarEntryDAO cellarentryDAO)
		{
			this.cellarentryDAO = cellarentryDAO;
		}

		public IShoppingListDAO getShoppingListDAO()
		{
			return shoppingListDAO;
		}

		public void setShoppingListDAO(IShoppingListDAO shoppingListDAO)
		{
			this.shoppingListDAO = shoppingListDAO;
		}
	}
}
