package ch.sfdr.common;

import static org.junit.Assert.*;

import org.apache.struts.action.ActionForward;
import org.junit.Before;
import org.junit.Test;

import ch.sfdr.common.security.SecManagerTest;

import com.mockrunner.mock.web.MockActionMapping;
import com.mockrunner.mock.web.MockActionServlet;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;

/**
 * Tests for LoginAction
 * @author D.Ritz
 */
public class LoginActionTest
{
	private LoginAction me;

	private MockActionMapping mapping;
	private MockHttpServletRequest req;
	private MockHttpServletResponse resp;
	private MockServletContext ctx;
	private MockActionServlet servlet;

	private LoginForm form;

	@Before
	public void setUp()
		throws Exception
	{
		mapping = new MockActionMapping();
		req = new MockHttpServletRequest();
		// getSession() returns null otherwise - violation of the spec
		req.setSession(new MockHttpSession());
		resp = new MockHttpServletResponse();
		ctx = new MockServletContext();

		servlet = new MockActionServlet();
		servlet.setServletContext(ctx);

		SecManagerTest.getSecurityManager(ctx);

		form = new LoginForm();

		me = new LoginAction();
		me.setServlet(servlet);
	}

	@Test
	public void testDeny()
		throws Exception
	{
		ActionForward fwd = me.execute(mapping, form, req, resp);
		assertFalse(fwd.getRedirect());
	}

	@Test
	public void testLogin()
		throws Exception
	{
		form.setUser("admin");
		form.setPass("admin");
		ActionForward fwd = me.execute(mapping, form, req, resp);
		assertTrue(fwd.getRedirect());
		assertEquals("index.do", fwd.getPath());
	}

	@Test
	public void testLogout()
		throws Exception
	{
		form.setAction(LoginForm.ACTION_LOGOUT);
		ActionForward fwd = me.execute(mapping, form, req, resp);
		assertFalse(fwd.getRedirect());
	}
}
