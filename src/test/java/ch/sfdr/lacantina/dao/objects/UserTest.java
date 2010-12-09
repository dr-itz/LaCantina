package ch.sfdr.lacantina.dao.objects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for User
 * @author D.Ritz
 */
public class UserTest
{
	private User me;

	@Before
	public void setUp()
		throws Exception
	{
		me = new User();
	}

	@Test
	public void testGetSetId()
	{
		assertEquals(0, me.getId());
		me.setId(123);
		assertEquals(123, me.getId());
	}

	@Test
	public void testGetLogin()
	{
		assertNull(me.getLogin());
		me.setLogin("test");
		assertEquals("test", me.getLogin());
	}

	@Test
	public void testGetFirstName()
	{
		assertNull(me.getFirstName());
		me.setFirstName("test2");
		assertEquals("test2", me.getFirstName());
	}

	@Test
	public void testGetLastName()
	{
		assertNull(me.getLastName());
		me.setLastName("test3");
		assertEquals("test3", me.getLastName());
	}

	@Test
	public void testGetPasswordHash()
	{
		assertNull(me.getPasswordHash());
		me.setPasswordHash("test4");
		assertEquals("test4", me.getPasswordHash());
	}

	@Test
	public void testGetEmail()
	{
		assertNull(me.getEmail());
		me.setEmail("test5");
		assertEquals("test5", me.getEmail());
	}

	@Test
	public void testSetAdmin()
	{
		assertFalse(me.isAdmin());
		me.setAdmin(true);
		assertTrue(me.isAdmin());
	}
}
