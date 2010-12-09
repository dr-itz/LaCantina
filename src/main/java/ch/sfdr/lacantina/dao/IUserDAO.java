package ch.sfdr.lacantina.dao;

import java.util.List;

import ch.sfdr.lacantina.dao.objects.User;

/**
 * DAO interface for user related DB queries
 * @author D.Ritz
 */
public interface IUserDAO
{
	/**
	 * read a user by ID
	 * @param id The user ID
	 * @return User if found, null otherwise
	 * @throws DAOException
	 */
	User getUser(int id)
		throws DAOException;

	/**
	 * reads a list of users
	 * @return List of User
	 * @throws DAOException
	 */
	List<User> getUsers()
		throws DAOException;
}
