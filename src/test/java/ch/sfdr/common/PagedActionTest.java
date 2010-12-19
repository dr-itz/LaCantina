package ch.sfdr.common;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author D.Ritz
 */
public class PagedActionTest
	extends PagedAction
{
	private PagedForm form;

	@Before
	public void setUp()
		throws Exception
	{
		form = new PagedForm()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected ActionErrors doValidate(ActionMapping mapping,
					HttpServletRequest request)
			{
				return null;
			}
		};
	}

	@Test
	public void testPrev()
		throws Exception
	{
		form.getPagingCookie().setOffset(100);
		form.setAction("prev");

		execute(null, form, null, null);

		assertEquals(80, form.getPagingCookie().getOffset());
	}

	@Test
	public void testNext()
		throws Exception
	{
		form.getPagingCookie().setOffset(100);
		form.setAction("next");

		execute(null, form, null, null);

		assertEquals(120, form.getPagingCookie().getOffset());
	}

	/*
	 * @see ch.sfdr.common.PagedAction#doExecute(org.apache.struts.action.ActionMapping, ch.sfdr.common.PagedForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward doExecute(ActionMapping mapping, PagedForm form,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		assertEquals(this.form, form);
		assertEquals("list", form.getAction());
		return null;
	}
}
