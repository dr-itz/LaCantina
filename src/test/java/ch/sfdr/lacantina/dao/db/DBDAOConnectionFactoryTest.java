package ch.sfdr.lacantina.dao.db;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.IDAOConnection;

/**
 * Test for DBDAOConnectionFactory
 * @author D. Ritz
 */
public class DBDAOConnectionFactoryTest
	extends AbstractJDBCTest
{
	private DBDAOConnectionFactory me;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		me = new DBDAOConnectionFactory();
	}

	@Test
	public void testCreateConnection()
		throws DAOException
	{
		IDAOConnection conn = me.createConnection();
		assertTrue(conn instanceof DBDAOConnection);
	}
}
