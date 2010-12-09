package ch.sfdr.lacantina.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test for DAOException
 * @author D.Ritz
 */
public class DAOExceptionTest
{
	@Test
	public void testDAOExceptionString()
	{
		DAOException ex = new DAOException("test");
		assertEquals("test", ex.getMessage());
	}

	@Test
	public void testDAOExceptionException()
	{
		Exception cause = new Exception("test123");
		DAOException ex = new DAOException(cause);

		assertEquals(cause, ex.getCause());
	}
}
