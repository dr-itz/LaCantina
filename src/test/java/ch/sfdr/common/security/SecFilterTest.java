package ch.sfdr.common.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockFilterChain;
import com.mockrunner.mock.web.MockFilterConfig;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockRequestDispatcher;
import com.mockrunner.mock.web.MockServletContext;

/**
 * Tests for SecFilter using Mockrunner to mock the javax.servlet stuff
 * @author D.Ritz
 */
public class SecFilterTest
{
	private SecFilter me;
	private MockServletContext ctx;
	private MockHttpServletRequest req;
	private MockHttpServletResponse resp;
	private MockFilterChain chain;

	@Before
	public void setUp()
		throws Exception
	{
		ctx = new MockServletContext();
		ctx.setInitParameter("security-config", "/WEB-INF/security-config.xml");
		ctx.setResourceAsStream("/WEB-INF/security-config.xml",
			SecConfigTest.getSecurityConfigInputStream());

		MockFilterConfig conf = new MockFilterConfig();
		conf.setupServletContext(ctx);

		req = new MockHttpServletRequest();
		req.setContextPath("contextPath/");

		resp = new MockHttpServletResponse();
		chain = new MockFilterChain();

		me = new SecFilter();
		me.init(conf);
	}

	@Test
	public void testDoFilterOk()
		throws IOException, ServletException
	{
		req.setRequestURI("/img/blub.jpg");
		me.doFilter(req, resp, chain);

		assertEquals(req, chain.getLastRequest());
		assertEquals(resp, chain.getLastResponse());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDoFilterRedirectLogin()
		throws IOException, ServletException
	{
		req.setRequestURI("/admin/bla.jsp");
		me.doFilter(req, resp, chain);

		assertNull(chain.getLastRequest());
		assertNull(chain.getLastResponse());

		assertFalse(resp.wasRedirectSent());
		Map<String, MockRequestDispatcher> m = req.getRequestDispatcherMap();
		assertEquals("/login.jsp", m.get(m.keySet().iterator().next()).getPath());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDoFilterRedirectDeny()
		throws IOException, ServletException
	{
		req.setRequestURI("/admin/bla.jsp");

		// getSession() returns null otherwise - violation of the spec
		req.setSession(new MockHttpSession());

		SessionToken tok = new SessionToken("login", 1234, false);
		SecManager.getInstance(ctx).setSessionToken(req, tok);

		me.doFilter(req, resp, chain);

		assertNull(chain.getLastRequest());
		assertNull(chain.getLastResponse());

		assertFalse(resp.wasRedirectSent());
		Map<String, MockRequestDispatcher> m = req.getRequestDispatcherMap();
		assertEquals("/deny.do", m.get(m.keySet().iterator().next()).getPath());
	}

	@Test
	public void testDestroy()
	{
		me.destroy();
	}

}
