package ch.sfdr.lacantina.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.PagingCookie;

/**
 * Tests for AbstractDAO, tests only the exception handling, the rest gets
 * coverage by other tests.
 * @author D.Ritz
 */
public class AbstractDAOTest
	extends AbstractJDBCTest
{
	private DummyDAO me;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		me = new DummyDAO(getConnection());
	}

	@Test(expected = DAOException.class)
	public void testGetSingleRowException()
		throws DAOException
	{
		prepareException("SELECT exception FROM mockrunner");
		me.getSingleRowException();
	}

	@Test(expected = DAOException.class)
	public void testGetRowListException()
		throws DAOException
	{
		prepareException("SELECT exception FROM mockrunner");
		me.getRowListException();
	}

	@Test(expected = DAOException.class)
	public void testGetPagedRowListException()
		throws DAOException
	{
		prepareQueryWithResult(
			"SELECT COUNT(*) FROM mockrunner",
			"CountQuery",
			new Object[][] {
				{ 1 }
			});
		prepareException("SELECT exception FROM mockrunner LIMIT ? OFFSET ?",
			20, 0);
		me.getPagedRowListException();
	}

	@Test(expected = DAOException.class)
	public void testExecuteUpdateStatementException()
		throws DAOException
	{
		prepareException("UPDATE exception SET exception=now");
		me.getExecuteUpdateException();
	}

	private static class DummyDAO
		extends AbstractDAO<String>
	{
		public DummyDAO(Connection conn)
		{
			super(conn);
		}

		@Override
		public String readRow(ResultSet rs)
			throws SQLException
		{
			return "test";
		}

		public void getSingleRowException()
			throws DAOException
		{
			getSingleRow("SELECT exception FROM mockrunner");
		}

		public void getRowListException()
			throws DAOException
		{
			getRowList("SELECT exception FROM mockrunner");
		}

		public void getExecuteUpdateException()
			throws DAOException
		{
			executeUpdateStatement("UPDATE exception SET exception=now");
		}

		public void getPagedRowListException()
			throws DAOException
		{
			PagingCookie pc = new PagingCookie();
			getPagedRowList(
				"SELECT COUNT(*) FROM mockrunner",
				"SELECT exception FROM mockrunner", pc) ;
		}
	}
}
