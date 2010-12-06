package ch.sfdr.lacantina.security;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for UserAuth
 * FIXME: this is dummy for now
 * @author D.Ritz
 */
public class UserAuthTest
{
	private UserAuth me;

	@Before
	public void setUp()
		throws Exception
	{
		me = new UserAuth();
	}

	@Test
	public void testAuthenticateUser()
	{
		assertNotNull(me.authenticateUser("admin", "admin"));
		assertNull(me.authenticateUser("admin", "bla"));
	}

}
