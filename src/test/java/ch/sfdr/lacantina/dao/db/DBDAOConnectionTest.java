package ch.sfdr.lacantina.dao.db;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import ch.sfdr.lacantina.dao.IUserDAO;

/**
 * Tests for DBDAOConnection
 * @author D. Ritz
 */
public class DBDAOConnectionTest
	extends AbstractJDBCTest
{
	private DBDAOConnection me;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		me = new DBDAOConnection();
	}

	@Test
	public void testGetUserDAO()
	{
		IUserDAO dao = me.getUserDAO();
		assertTrue(dao instanceof DbUserDAO);
	}

	@Test
	public void testClose() throws SQLException
	{
		me.close();
		assertTrue(getConnection().isClosed());
	}
}
