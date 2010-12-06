package ch.sfdr.lacantina.security;

import ch.sfdr.common.security.IUserAuth;
import ch.sfdr.common.security.SessionToken;

/**
 * User Authentication
 * FIXME: this is just a dummy
 * @author D.Ritz
 */
public class UserAuth
	implements IUserAuth
{
	public SessionToken authenticateUser(String login, String pass)
	{
		if ("admin".equals(login) && "admin".equals(pass))
			return new SessionToken(login, 0, true);
		return null;
	}
}
