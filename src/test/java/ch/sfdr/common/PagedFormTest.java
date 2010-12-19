package ch.sfdr.common;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockActionMapping;
import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * Tests for PagedForm
 * @author D.Ritz
 */
public class PagedFormTest
	extends PagedForm
{
	private static final long serialVersionUID = 1L;

	private MockActionMapping mapping;
	private MockHttpServletRequest req;

	@Before
	public void setUp()
		throws Exception
	{
		mapping = new MockActionMapping();
		req = new MockHttpServletRequest();
	}

	@Test
	public void testReset()
	{
		getPagingCookie().setOffset(100);
		reset(mapping, req);
		assertEquals(0, getPagingCookie().getOffset());
	}

	@Test
	public void testGetPc()
	{
		assertEquals("", getPc());
		getPagingCookie().setOffset(100);
		assertEquals("100,20,0,0", getPc());
	}

	@Test
	public void testSetPc()
	{
		assertEquals(0, getPagingCookie().getOffset());
		setPc("100,25,200,25");
		assertEquals(100, getPagingCookie().getOffset());
		assertEquals(25, getPagingCookie().getLimit());
		assertEquals(200, getPagingCookie().getTotalRows());
		assertEquals(25, getPagingCookie().getPageRows());
	}

	@Override
	protected ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request)
	{
		return null;
	}
}
