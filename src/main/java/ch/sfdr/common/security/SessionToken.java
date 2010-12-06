package ch.sfdr.common.security;

import java.io.Serializable;

/**
 * Token for a user's session
 * @author D.Ritz
 */
public class SessionToken
	implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String login;
	private int userId;
	private boolean isAdmin;

	/**
	 * Creates a new SessionToken with the given values
	 */
	public SessionToken(String login, int userId, boolean isAdmin)
	{
		this.login = login;
		this.userId = userId;
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the login
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * @return the userId
	 */
	public int getUserId()
	{
		return userId;
	}

	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin()
	{
		return isAdmin;
	}
}
