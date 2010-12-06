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

	@Test
	public void testAuthenticateUser()
	{
		prepareQueryWithResult(
			"SELECT id, password_hash, is_admin " +
			"FROM users " +
			"WHERE login = ?",
			"UserQuery",
			new Object[][] {
				{ 123, "d033e22ae348aeb5660fc2140aec35850c4da997", true },
			}
		);

		SessionToken tok = me.authenticateUser("admin", "admin");
		assertNotNull(tok);
		assertEquals(123, tok.getUserId());
		assertTrue(tok.isAdmin());
	}
}
