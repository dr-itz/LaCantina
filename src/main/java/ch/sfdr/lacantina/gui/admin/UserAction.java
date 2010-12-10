package ch.sfdr.lacantina.gui.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.sfdr.common.BaseAction;
import ch.sfdr.common.security.Password;
import ch.sfdr.lacantina.dao.DAOConnectionFactory;
import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.IDAOConnection;
import ch.sfdr.lacantina.dao.IUserDAO;
import ch.sfdr.lacantina.dao.objects.User;

/**
 * User Action
 * @author D.Ritz
 */
public class UserAction
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
		UserForm form = (UserForm) af;
		String action = form.getAction();

		if (UserForm.ACTION_NEW.equals(action)) {
			form.reset(mapping, request);
			return returnInputForward(form, mapping, request);
		}

		IDAOConnection conn = DAOConnectionFactory.getConnection();
		try {
			IUserDAO dao = conn.getUserDAO();

			if (UserForm.ACTION_FORM.equals(action)) {
				if (form.getUser().getId() != 0) {
					User user = dao.getUser(form.getUser().getId());
					form.setUser(user);
				}
				return returnInputForward(form, mapping, request);
			}

			if (UserForm.ACTION_MODIFY.equals(action)) {
				String pw = form.getPw1();
				if (pw != null) {
					String pwHash = Password.getPasswordHash(pw);
					form.getUser().setPasswordHash(pwHash);
				}
				try {
					dao.storeUser(form.getUser());
				} catch (DAOException e) {
					attachSingleErrorMessage(mapping, request,
						"user.update.failed");
				}

			} else if (UserForm.ACTION_DELETE.equals(action)) {
				if (form.getUser().getId() != 0) {
					try {
						dao.deleteUser(form.getUser().getId());
					} catch (DAOException e) {
						attachSingleErrorMessage(mapping, request,
							"user.delete.failed");
					}
				}
			}

			// defaults to LIST
			List<User> userList = dao.getUsers();
			request.setAttribute("userList", userList);
		} finally {
			conn.close();
		}

		return mapping.findForward("userList");
	}
}
