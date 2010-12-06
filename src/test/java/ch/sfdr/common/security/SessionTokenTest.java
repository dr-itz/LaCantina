package ch.sfdr.common.security;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test cases for SessionToken
 * @author D.Ritz
 */
public class SessionTokenTest
{
	@Test
	public final void testSessionTokenAdmin()
	{
		SessionToken tok = new SessionToken("test", 1234, true);
		assertEquals("test", tok.getLogin());
		assertEquals(1234, tok.getUserId());
		assertTrue(tok.isAdmin());
	}

	@Test
	public final void testSessionTokenUser()
	{
		SessionToken tok = new SessionToken("test2", 12345, false);
		assertEquals("test2", tok.getLogin());
		assertEquals(12345, tok.getUserId());
		assertFalse(tok.isAdmin());
	}
}
