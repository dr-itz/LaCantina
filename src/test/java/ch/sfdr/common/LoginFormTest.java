package ch.sfdr.common;

import static org.junit.Assert.*;

import org.apache.struts.action.ActionErrors;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockActionMapping;
import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * Tests for LoginForm
 * @author D.Ritz
 */
public class LoginFormTest
{
	private LoginForm me;
	private MockActionMapping mapping;
	private MockHttpServletRequest req;


	@Before
	public void setUp()
		throws Exception
	{
		me = new LoginForm();
		mapping = new MockActionMapping();
		req = new MockHttpServletRequest();
	}

	@Test
	public void testGetSetUser()
	{
		assertNull(me.getUser());
		me.setUser("test");
		assertEquals("test", me.getUser());
	}

	@Test
	public void testGetSetPass()
	{
		assertNull(me.getPass());
		me.setPass("test");
		assertEquals("test", me.getPass());
	}

	@Test
	public void testReset()
	{
		me.setUser("test1");
		me.setPass("test2");

		me.reset(mapping, req);

		assertNull(me.getUser());
		assertNull(me.getPass());
		assertEquals("login", me.getAction());
	}

	@Test
	public void testValidateEmpty()
	{
		ActionErrors e = me.validate(mapping, req);
		assertEquals(2, e.size());
	}

	@Test
	public void testValidateLogout()
	{
		me.setAction(LoginForm.ACTION_LOGOUT);
		ActionErrors e = me.validate(mapping, req);
		assertTrue(e.isEmpty());
	}
}
