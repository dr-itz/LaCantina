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
	public static final String ACTION_SORT   = "sort";

	protected PagingCookie pc;
	private String sortKey;

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

	/*
	 * @see ch.sfdr.common.BaseForm#preValidate(
	 * org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preValidate(ActionMapping mapping, HttpServletRequest request)
	{
		if (ACTION_SORT.equals(getAction())) {
			PagingCookie pc = getPagingCookie();
			if (pc.getSortKey().equals(sortKey))
				pc.setSortDesc(!pc.isSortDesc());
			else
				pc.setSortDesc(false);
			pc.setSortKey(sortKey);
			setAction(ACTION_LIST);
		}
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

	/**
	 * @return the sortKey
	 */
	public String getSortKey()
	{
		return sortKey;
	}

	/**
	 * @param sortKey the sortKey to set
	 */
	public void setSortKey(String sortKey)
	{
		this.sortKey = sortKey;
	}
}
