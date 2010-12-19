package ch.sfdr.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import ch.sfdr.lacantina.dao.PagingCookie;

/**
 * Base form for 'paged' lists
 * @author D.Ritz
 */
public abstract class PagedForm
	extends BaseForm
{
	private static final long serialVersionUID = 1L;

	public static final String ACTION_PREV   = "prev";
	public static final String ACTION_NEXT   = "next";

	protected PagingCookie pc;

	/*
	 * @see ch.sfdr.common.BaseForm#reset(
	 * 		org.apache.struts.action.ActionMapping,
	 * 		javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		super.reset(mapping, request);
		pc = null;
	}


	/**
	 * gets the PagingCookie as String (for the JSP part)
	 * @return String representation of the paging cookie
	 */
	public String getPc()
	{
		if (pc != null)
			return pc.toString();
		return "";
	}

	/**
	 * creates the paging cookie from string
	 * @param s the input string
	 */
	public void setPc(String s)
	{
		if (!isEmptyString(s))
			pc = PagingCookie.fromString(s);
	}

	/**
	 * gets the PagingCookie
	 * @return the PagingCookie
	 */
	public PagingCookie getPagingCookie()
	{
		if (pc == null)
			pc = new PagingCookie();
		return pc;
	}
}
