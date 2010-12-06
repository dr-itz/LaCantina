package ch.sfdr.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * LoginForm
 * @author D.Ritz
 */
public class LoginForm
	extends BaseForm
{
	private static final long serialVersionUID = 1L;

	public static final String ACTION_LOGIN  = "login";
	public static final String ACTION_LOGOUT = "logout";

	private String user = null;
	private String pass = null;

	@Override
	protected ActionErrors doValidate(ActionMapping mapping,
		HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		if (!ACTION_LOGOUT.equals(action)) {
			if (isEmptyString(user))
				errors.add("user", new ActionMessage("login.user.required"));
			if (isEmptyString(pass))
				errors.add("pass", new ActionMessage("login.pass.required"));
			if (!errors.isEmpty())
				pass = null;
		}
		return errors;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		super.reset(mapping, request);
		user = pass = null;
		action = ACTION_LOGIN;
	}

	public void   setUser(String user) { this.user = user; }
	public String getUser()            { return user; }

	public void   setPass(String pass) { this.pass = pass; }
	public String getPass()            { return pass; }
}
