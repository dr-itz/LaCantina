package ch.sfdr.common.security;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockRequestDispatcher;
import com.mockrunner.mock.web.MockServletContext;

/**
 * Tests for SecManager using Mockrunner to mock the javax.servlet stuff
 * @author D.Ritz
 */
public final class SecManagerTest
{
	private SecManager me;
	private MockServletContext ctx;
	private MockHttpServletRequest req;
	private MockHttpServletResponse resp;

	@Before
	public void setUp()
		throws Exception
	{
		ctx = new MockServletContext();
		ctx.setInitParameter("security-config", "/WEB-INF/security-config.xml");
		ctx.setResourceAsStream("/WEB-INF/security-config.xml",
			SecConfigTest.getSecurityConfigInputStream());

		req = new MockHttpServletRequest();
		resp = new MockHttpServletResponse();

		me = SecManager.getInstance(ctx);
	}

	@Test
	public void testGetInstance()
	{
		assertNotNull(me);
	}

	@Test
	public void testGetSecurityConfig()
	{
		SecConfigTest.testSecurityConfig(me.getSecurityConfig());
	}

	@Test
	public void testGetSetSessionTimeout()
	{
		assertEquals(120, me.getSessionTimeout());
		me.setSessionTimeout(1234);
		assertEquals(1234, me.getSessionTimeout());
	}

	@Test
	public void testGetRelativeUri()
	{
		req.setContextPath("contextPath/");
		req.setRequestURI("contextPath/blub");
		assertEquals("blub", SecManager.getRelativeUri(req));

		req.setRequestURI("foo/bar");

		assertEquals("foo/bar", SecManager.getRelativeUri(req));
	}

	@Test
	public void testGetSetSessionToken()
	{
		assertNull(SecManager.getSessionToken(req));

		// getSession() returns null otherwise - violation of the spec
		req.setSession(new MockHttpSession());

		SessionToken tok = new SessionToken("login", 1234, true);
		me.setSessionToken(req, tok);

		assertEquals(tok, SecManager.getSessionToken(req));

		SecManager.invalidateSessionToken(req);
		assertNull(SecManager.getSessionToken(req));
	}

	@Test
	public void testRedirectLogin()
		throws IOException
	{
		req.setContextPath("contextPath/");
		req.setRequestURI("contextPath/blub");

		me.redirectLogin(req, resp);

		assertFalse(resp.wasRedirectSent());
		Map<String, MockRequestDispatcher> m = req.getRequestDispatcherMap();
		assertEquals("/login.jsp", m.get(m.keySet().iterator().next()).getPath());
	}

	@Test
	public void testRedirectDeny()
		throws IOException
	{
		req.setContextPath("contextPath/");
		req.setRequestURI("contextPath/blub");

		me.redirectDeny(req, resp);

		assertFalse(resp.wasRedirectSent());
		Map<String, MockRequestDispatcher> m = req.getRequestDispatcherMap();
		assertEquals("/deny.do", m.get(m.keySet().iterator().next()).getPath());
	}

	@Test
	public void testRedirectHome()
		throws IOException
	{
		req.setContextPath("contextPath");
		req.setRequestURI("contextPath/blub");

		me.redirectHome(req, resp);

		assertTrue(resp.wasRedirectSent());
		assertEquals("contextPath/home.do", resp.getHeader("Location"));
	}

	@Test
	public void testRedirect()
		throws IOException
	{
		req.setContextPath("contextPath");
		req.setRequestURI("contextPath/blub");

		SecManager.redirect(req, resp, "blub.jsp");

		assertTrue(resp.wasRedirectSent());
		assertEquals("contextPath/blub.jsp", resp.getHeader("Location"));
	}

	@Test
	public void testUserAuth()
	{
		// getSession() returns null otherwise - violation of the spec
		req.setSession(new MockHttpSession());

		SessionToken tok = me.authenticateUser(req, "admin", "admin");

		assertNotNull(tok);
		assertEquals("admin", tok.getLogin());
	}
}
