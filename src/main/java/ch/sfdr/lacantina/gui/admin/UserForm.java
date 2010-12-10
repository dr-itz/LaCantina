package ch.sfdr.lacantina.gui.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.sfdr.common.BaseForm;
import ch.sfdr.lacantina.dao.objects.User;

/**
 * User Form
 * @author D.Ritz
 */
public class UserForm
	extends BaseForm
{
	private static final long serialVersionUID = 1L;

	private User user;
	private String pw1;
	private String pw2;

	/*
	 * @see org.apache.struts.action.ActionForm#reset(
	 * 		org.apache.struts.action.ActionMapping,
	 * 		javax.servlet.HttpServletRequest)
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest req)
	{
		super.reset(mapping, req);
		user = new User();
	}

	/*
	 * @see ch.sfdr.common.BaseForm#doValidate(
	 * 		org.apache.struts.action.ActionMapping,
	 *		javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();

		if (ACTION_MODIFY.equals(action)) {
			if (isEmptyString(user.getLogin()))
				errors.add("login", new ActionMessage("user.login.required"));
			else
				user.setLogin(user.getLogin().toLowerCase());

			if (isEmptyString(user.getLastName()))
				errors.add("lastName", new ActionMessage("user.lastname.required"));

			if (isEmptyString(user.getFirstName()))
				errors.add("firstName", new ActionMessage("user.firstname.required"));

			if (user.getId() == 0 && isEmptyString(pw1)) {
				errors.add("pw", new ActionMessage("user.pw.new.required"));
			} else if (!isEmptyString(pw1)) {
				if (!pw1.equals(pw2)) {
					errors.add("pw", new ActionMessage("user.pw.mustmatch"));
					pw1 = pw2 = null;
				}
			} else if (isEmptyString(pw1)) {
				pw1 = pw2 = null;
			}
		}

		return errors;
	}

	/**
	 * @return the user
	 */
	public User getUser()
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user)
	{
		this.user = user;
	}

	/**
	 * @return the pw1
	 */
	public String getPw1()
	{
		return pw1;
	}

	/**
	 * @param pw1 the pw1 to set
	 */
	public void setPw1(String pw1)
	{
		this.pw1 = pw1;
	}

	/**
	 * @return the pw2
	 */
	public String getPw2()
	{
		return pw2;
	}

	/**
	 * @param pw2 the pw2 to set
	 */
	public void setPw2(String pw2)
	{
		this.pw2 = pw2;
	}
}
