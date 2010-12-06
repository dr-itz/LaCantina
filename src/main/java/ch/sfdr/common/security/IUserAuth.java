package ch.sfdr.common.security;

/**
 * User Authentication
 * @author D.Ritz
 */
public interface IUserAuth
{
	SessionToken authenticateUser(String login, String pass);
}
