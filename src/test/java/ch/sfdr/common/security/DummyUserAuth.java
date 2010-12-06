package ch.sfdr.common.security;

/**
 * Dummy class for testing. This cannot be mocked since it's configured as a
 * string in security-config.xml
 * @author D.Ritz
 */
public class DummyUserAuth
	implements IUserAuth
{
	public SessionToken authenticateUser(String login, String pass)
	{
		if ("admin".equals(login) && "admin".equals(pass))
			return new SessionToken(login, 0, true);
		return null;
	}
}
