package ch.sfdr.common.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import ch.sfdr.common.security.SecConfig.SecurityConstraint;

/**
 * Tests for SecConfig
 * @author D.Ritz
 */
public final class SecConfigTest
{
	private SecConfig me;

	@Before
	public void setUp()
		throws Exception
	{
		me = new SecConfig();
	}

	@Test
	public void testGetSetLoginUrl()
	{
		me.setLoginUrl("testLoginURL");
		assertEquals("testLoginURL", me.getLoginUrl());
	}

	@Test
	public void testGetSetDenyUrl()
	{
		me.setDenyUrl("testDenyURL");
		assertEquals("testDenyURL", me.getDenyUrl());
	}

	@Test
	public void testGetSetHomeUrl()
	{
		me.setHomeUrl("testHomeURL");
		assertEquals("testHomeURL", me.getHomeUrl());
	}

	@Test
	public void testIsDefaultRequireLogin()
	{
		me.setLogin("bla");
		assertFalse(me.isDefaultRequireLogin());
		me.setLogin("required");
		assertTrue(me.isDefaultRequireLogin());
	}

	@Test
	public void testParser()
		throws SAXException, IOException
	{
		InputStream is = getSecurityConfigInputStream();
		me = SecConfig.parse(is);
		testSecurityConfig(me);
	}

	/**
	 * gets the security config input stream for testing later with
	 * testSecurityConfig
	 * @return
	 */
	public static InputStream getSecurityConfigInputStream()
	{
		return SecConfigTest.class.getResourceAsStream("security-config.xml");
	}

	/**
	 * tests the security config loaded from security-config.xml
	 */
	public static void testSecurityConfig(SecConfig config)
	{
		assertEquals("/login.jsp", config.getLoginUrl());
		assertEquals("/home.do", config.getHomeUrl());
		assertEquals("/deny.do", config.getDenyUrl());
		assertTrue(config.isDefaultRequireLogin());

		List<SecurityConstraint> c = config.getConstraints();
		assertEquals(2, c.size());

		SecurityConstraint sc = c.get(0);
		assertFalse(sc.isRequireLogin());
		assertTrue(sc.isRequireLoginSet());
		assertEquals(0, sc.getPrivileges().size());
		assertEquals(3, sc.getUrlPatterns().size());
		assertEquals("/img/.*", sc.getUrlPatterns().get(0));
		assertEquals("/css/.*", sc.getUrlPatterns().get(1));
		assertEquals("/js/.*", sc.getUrlPatterns().get(2));

		sc = c.get(1);
		assertFalse(sc.isRequireLogin());
		assertFalse(sc.isRequireLoginSet());
		assertEquals(1, sc.getPrivileges().size());
		assertEquals("admin", sc.getPrivileges().get(0));
		assertEquals(1, sc.getUrlPatterns().size());
		assertEquals("/admin/.*", sc.getUrlPatterns().get(0));

		assertEquals("ch.sfdr.common.security.DummyUserAuth", config.getUserAuth());
	}
}
