package ch.sfdr.lacantina.dao.db;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.jdbc.MockPreparedStatement;
import com.mockrunner.mock.jdbc.MockResultSet;

/**
 * Test for DBConnectino
 * @author D.Ritz
 */
public class DBConnectionTest
	extends AbstractJDBCTest
{
	private DBConnection me;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		me = new DBConnection();
	}

	@Test
	public void testDBConnection()
	{
		assertNotNull(me.getConnection());
	}

	@Test
	public void testCloseConnection()
	{
		// this must not throw when called twice
		me.closeConnection();
		me.closeConnection();
	}

	@Test
	public void testCloseStatement()
	{
		// must not throw
		DBConnection.closeStatement(null);

		MockPreparedStatement stmt =
			new MockPreparedStatement(me.getConnection(), "TEST");
		DBConnection.closeStatement(stmt);
		assertTrue(stmt.isClosed());
	}

	@Test
	public void testCloseResultSet()
	{
		// must not throw
		DBConnection.closeResultSet(null);

		MockResultSet rs = new MockResultSet("TEST");
		DBConnection.closeResultSet(rs);
		assertTrue(rs.isClosed());
	}
}
