package ch.sfdr.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * base class for all forms. adds some helpers
 * @author D.Ritz
 */
public abstract class BaseForm
	extends ActionForm
{
	private static final long serialVersionUID = 1L;

	// common param used in most forms
	protected String action;
	protected int id;

	// the most common actions
	public static final String ACTION_FORM   = "form";
	public static final String ACTION_MODIFY = "mod";
	public static final String ACTION_LIST   = "list";
	public static final String ACTION_NEW    = "new";
	public static final String ACTION_DELETE = "del";

	/**
	 * returns true if a String is null or empty
	 * @param str
	 * @return true if string is empty
	 */
	public static boolean isEmptyString(String str)
	{
		return str == null || str.trim().length() == 0;
	}

	/**
	 * trims a string if it's not null
	 * @param a the string
	 * @return the trimmed string of null if input was null
	 */
	public static String trimStr(String a)
	{
		if (a == null)
			return null;
		return a.trim();
	}

	/**
	 * resets the basic parameters
	 * @see org.apache.struts.action.ActionForm#reset(
	 *   org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		action = ACTION_LIST;
		id = 0;
	}

	@Override
	public final ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request)
	{
		preValidate(mapping, request);
		ActionErrors errors = doValidate(mapping, request);
		if (!errors.isEmpty())
			attachDataLists(request);
		return errors;
	}

	/**
	 * implementation of validate
	 * @param mapping
	 * @param request
	 * @return
	 */
	protected abstract ActionErrors doValidate(ActionMapping mapping,
		HttpServletRequest request);

	/**
	 * used to attach data lists
	 * @param request
	 */
	public void attachDataLists(HttpServletRequest request)
	{

	}

	/**
	 * allows changing of parameters before the validation
	 * @param mapping
	 * @param request
	 */
	protected void preValidate(ActionMapping mapping,
			HttpServletRequest request)
	{

	}

	/**
	 * sets the action
	 * @return the action
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * sets the action
	 * @param action
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id)
	{
		this.id = id;
	}
}
