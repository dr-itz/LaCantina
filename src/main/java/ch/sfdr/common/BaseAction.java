package ch.sfdr.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * base action with some added helpers
 * @author d_ritz
 */
public abstract class BaseAction
	extends Action
{
	/**
	 * attaches a single global error message
	 * @param mapping
	 * @param request
	 * @param key message key
	 */
	public void attachSingleErrorMessage(ActionMapping mapping,
		HttpServletRequest request, String property, String key)
	{
		ActionErrors errors = new ActionErrors();
		errors.add(property, new ActionMessage(key));
		saveErrors(request, errors);
	}

	/**
	 * attaches a single global error message
	 * @param mapping
	 * @param request
	 * @param key message key
	 */
	public void attachSingleErrorMessage(ActionMapping mapping,
		HttpServletRequest request, String key)
	{
		attachSingleErrorMessage(mapping, request,
			ActionErrors.GLOBAL_MESSAGE, key);
	}

	/**
	 * returns the input forward with all data set
	 * @param mapping
	 * @param request
	 * @return
	 */
	protected ActionForward returnInputForward(BaseForm frm,
		ActionMapping mapping, HttpServletRequest request)
	{
		frm.attachDataLists(request);
		return mapping.getInputForward();
	}

	/**
	 * encode an URL for HTTP
	 * @param url
	 * @return
	 */
	public static String encodeUrl(String url)
	{
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) { ; }
		return url;
	}
}
