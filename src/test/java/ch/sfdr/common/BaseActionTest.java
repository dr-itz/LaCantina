package ch.sfdr.common;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.junit.Before;
import org.junit.Test;

import ch.sfdr.lacantina.dao.DummyDAOConnectionFactory;
import ch.sfdr.lacantina.dao.IDAOConnection;

import com.mockrunner.mock.web.MockActionMapping;
import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * Tests for BaseAction
 * @author D.Ritz
 */
public class BaseActionTest
	extends BaseAction
{
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
	public void testAttachSingleErrorMessage()
	{
		attachSingleErrorMessage(mapping, req, "bla");
		ActionMessages err = getErrors(req);
		assertEquals("bla[]",
			err.get(ActionErrors.GLOBAL_MESSAGE).next().toString());
	}

	@Test
	public void testEncodeUrl()
	{
		assertEquals("test%26123", encodeUrl("test&123"));
	}

	@Test
	public void testReturnInputForward()
	{
		BaseForm frm = new DummyForm();
		returnInputForward(frm,
			new DummyDAOConnectionFactory.DummyDAOConnection(), mapping, req);
	}

	private class DummyForm
		extends BaseForm
		implements BaseForm.DataListAttacher
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected ActionErrors doValidate(ActionMapping mapping,
				HttpServletRequest request)
		{
			return null;
		}

		public void attachDataLists(HttpServletRequest request, IDAOConnection conn)
		{
			assertEquals(req, request);
		}
	}
}
