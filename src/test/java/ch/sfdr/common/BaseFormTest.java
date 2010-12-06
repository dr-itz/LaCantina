package ch.sfdr.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockActionMapping;
import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * Tests for BaseForm
 * @author D.Ritz
 */
public class BaseFormTest
	extends BaseForm
{
	private static final long serialVersionUID = 1L;

	private MockActionMapping mapping;
	private MockHttpServletRequest req;
	private ActionErrors errors;

	@Before
	public void setUp()
		throws Exception
	{
		mapping = new MockActionMapping();
		req = new MockHttpServletRequest();
		errors = new ActionErrors();
	}

	@Test
	public void testIsEmptyString()
	{
		assertTrue(isEmptyString(""));
		assertTrue(isEmptyString(null));
		assertFalse(isEmptyString("blub"));
	}

	@Test
	public void testTrimStr()
	{
		assertEquals("test", trimStr(" test "));
		assertNull(trimStr(null));
	}

	@Test
	public void testReset()
	{
		setAction("blub");
		reset(mapping, req);
		assertEquals("list", getAction());
	}

	@Test
	public void testDoValidate()
	{
		validate(mapping, req);
		errors.add("test", new ActionMessage("bla"));
		validate(mapping, req);
	}

	@Test
	public void testGetSetAction()
	{
		assertNull(getAction());
		setAction("blub");
		assertEquals("blub", getAction());
	}

	@Override
	protected ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request)
	{
		assertEquals(this.mapping, mapping);
		assertEquals(this.req, request);
		return errors;
	}

	@Override
	protected void preValidate(ActionMapping mapping, HttpServletRequest request)
	{
		assertEquals(this.mapping, mapping);
		assertEquals(this.req, request);
		super.preValidate(mapping, request);
	}
}
