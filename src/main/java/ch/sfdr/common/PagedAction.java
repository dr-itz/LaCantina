package ch.sfdr.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.sfdr.lacantina.dao.PagingCookie;


/**
 * Base Action for 'paged' lists
 * @author D.Ritz
 */
public abstract class PagedAction
	extends BaseAction
{
	/*
	 * @see org.apache.struts.action.Action#execute(
	 * 		org.apache.struts.action.ActionMapping,
	 * 		org.apache.struts.action.ActionForm,
	 * 		javax.servlet.http.HttpServletRequest,
	 * 		javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm af,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		PagedForm form = (PagedForm) af;
		String action = form.getAction();

		if (PagedForm.ACTION_NEXT.equals(action)) {
			form.getPagingCookie().next();
			form.setAction(PagedForm.ACTION_LIST);
		} else if (PagedForm.ACTION_PREV.equals(action)) {
			form.getPagingCookie().prev();
			form.setAction(PagedForm.ACTION_LIST);
		}

		return doExecute(mapping, form, request, response);
	}

	/**
	 * Does the actual work.
	 * @see org.apache.struts.action.Action#execute(
	 * 		org.apache.struts.action.ActionMapping,
	 * 		org.apache.struts.action.ActionForm,
	 * 		javax.servlet.http.HttpServletRequest,
	 * 		javax.servlet.http.HttpServletResponse)
	 * @param mapping the ActionMapping
	 * @param form The PagedForm
	 * @param request the request
	 * @param response the response
	 * @return ActionForward
	 * @throws Exception
	 */
	public abstract ActionForward doExecute(ActionMapping mapping, PagedForm form,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception;

	/**
	 * sets the list value and the paging cookie
	 * @param req the request
	 * @param pc the paging cookie
	 * @param list the list object
	 * @param listName the name of the list
	 */
	protected void setList(HttpServletRequest req, PagingCookie pc,
		List<?> list, String listName)
	{
		req.setAttribute(listName, list);
		req.setAttribute("pcookie", pc);
	}
}
