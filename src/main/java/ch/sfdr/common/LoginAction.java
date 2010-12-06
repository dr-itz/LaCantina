package ch.sfdr.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.sfdr.common.security.SecManager;
import ch.sfdr.common.security.SessionToken;

/**
 * LoginAction. handles the login and creates the session
 * @author D.Ritz
 */
public class LoginAction
	extends BaseAction
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		LoginForm frm = (LoginForm) form;
		String action = frm.getAction();

		if (LoginForm.ACTION_LOGOUT.equals(action))
			return handleLogout(mapping, frm, request, response);
		return handleLogin(mapping, frm, request, response);
	}

	/**
	 * handles the login
	 * @param mapping
	 * @param frm
	 * @param req
	 * @param resp
	 * @return
	 */
	private ActionForward handleLogin(ActionMapping mapping, LoginForm frm,
		HttpServletRequest req, HttpServletResponse resp)
	{
		SecManager sm = SecManager.getInstance(getServlet().getServletContext());
		SessionToken tok = sm.authenticateUser(req, frm.getUser(), frm.getPass());
		if (tok == null) {
			attachSingleErrorMessage(mapping, req, "login.deny");
			frm.setPass(null);
			return mapping.getInputForward();
		}

		return new ActionForward("index.do", true);
	}

	/**
	 * handles logout
	 * @param mapping
	 * @param frm
	 * @param req
	 * @param resp
	 * @return
	 */
	private ActionForward handleLogout(ActionMapping mapping, LoginForm frm,
			HttpServletRequest req, HttpServletResponse resp)
	{
		SecManager.invalidateSessionToken(req);
		return mapping.getInputForward();
	}
}
