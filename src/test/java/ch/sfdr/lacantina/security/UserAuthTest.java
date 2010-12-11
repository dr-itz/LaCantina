package ch.sfdr.lacantina.security;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.sfdr.common.security.SessionToken;
import ch.sfdr.lacantina.dao.db.AbstractJDBCTest;

/**
 * Tests for UserAuth
 * @author D.Ritz
 * @author S.Freihofer
 */
public class UserAuthTest
	extends AbstractJDBCTest
{
	private UserAuth me;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		me = new UserAuth();
	}

	private void prepareResultSet(String login)
	{
		prepareQueryWithResult(
			"SELECT id, password_hash, is_admin " +
			"FROM users " +
			"WHERE login = ?",
			"UserQuery",
			new Object[][] {
				{ 123, "d033e22ae348aeb5660fc2140aec35850c4da997", true },
			},
			login
		);
	}

	@Test
	public void testAuthenticateUser()
	{
		prepareResultSet("admin");
		SessionToken tok = me.authenticateUser("admin", "admin");
		assertNotNull(tok);
		assertEquals(123, tok.getUserId());
		assertTrue(tok.isAdmin());
	}

	@Test
	public void testAuthenticateUserDeny()
	{
		prepareResultSet("admin");
		SessionToken tok = me.authenticateUser("admin", "blub");
		assertNull(tok);
	}

	@Test
	public void testAuthenticateUserNoUser()
	{
		SessionToken tok = me.authenticateUser("blub", "blub");
		assertNull(tok);
	}

	@Test
	public void testAuthenticateException()
	{
		prepareException(
			"SELECT id, password_hash, is_admin " +
			"FROM users " +
			"WHERE login = ?", "blub");
		SessionToken tok = me.authenticateUser("blub", "blub");
		assertNull(tok);
	}
}
