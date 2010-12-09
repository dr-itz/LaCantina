package ch.sfdr.lacantina.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.sfdr.lacantina.dao.db.AbstractJDBCTest;

/**
 * Tests for DAOConnectionFactory
 * @author D.Ritz
 */
public class DAOConnectionFactoryTest
	extends AbstractJDBCTest
{
	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
	}

	@Test
	public void testGetConnection()
		throws DAOException
	{
		IDAOConnection conn = DAOConnectionFactory.getConnection();
		assertNotNull(conn);
		conn.close();
	}

	@Test
	public void testSetFactory()
		throws DAOException
	{
		DummyDAOConnectionFactory dummy = new DummyDAOConnectionFactory();
		DAOConnectionFactory.setFactory(dummy);
		assertEquals(dummy.getConn(),
			DAOConnectionFactory.getConnection());
	}
}
