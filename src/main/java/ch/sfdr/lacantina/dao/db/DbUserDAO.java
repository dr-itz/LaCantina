package ch.sfdr.lacantina.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.IUserDAO;
import ch.sfdr.lacantina.dao.objects.User;

/**
 * DAO implementation for User
 * @author D.Ritz
 */
public class DbUserDAO
	extends AbstractDAO<User>
	implements IUserDAO
{
	/**
	 * creates a user DAO connected to a database
	 * @param conn
	 */
	public DbUserDAO(Connection conn)
	{
		super(conn);
	}

	/**
	 * Query used to read users
	 */
	private static final String USER_SELECT =
		"SELECT id, login, first_name, last_name, email, is_admin " +
		"FROM users ";

	/**
	 * reads a user from the ResultSet
	 * @param rs the result set
	 * @return User
	 * @throws SQLException
	 */
	public User readRow(ResultSet rs)
		throws SQLException
	{
		User u = new User();

		int n = 1;
		u.setId(rs.getInt(n++));
		u.setLogin(rs.getString(n++));
		u.setFirstName(rs.getString(n++));
		u.setLastName(rs.getString(n++));
		u.setEmail(rs.getString(n++));
		u.setAdmin(rs.getBoolean(n++));

		return u;
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IUserDAO#getUser(int)
	 */
	public User getUser(int id)
		throws DAOException
	{
		return getSingleRow(
			USER_SELECT +
			"WHERE id = ?",
			id);
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IUserDAO#getUsers()
	 */
	public List<User> getUsers()
		throws DAOException
	{
		return getRowList(
			USER_SELECT +
			"ORDER BY last_name, first_name");
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IUserDAO#storeUser(
	 * 		ch.sfdr.lacantina.dao.objects.User)
	 */
	public void storeUser(User u)
		throws DAOException
	{
		if (u.getId() == 0) {
			executeUpdateStatement(
				"INSERT INTO users" +
				"  (login, first_name, last_name, email, is_admin, password_hash) " +
				"VALUES (?, ?, ?, ?, ?, ?)",
				u.getLogin(), u.getFirstName(), u.getLastName(), u.getEmail(),
				u.isAdmin(), u.getPasswordHash());
		} else {
			executeUpdateStatement(
				"UPDATE users SET" +
				"  login = ?, first_name = ?, last_name = ?, email = ?," +
				"  is_admin = ? " +
				"WHERE id = ?",
				u.getLogin(), u.getFirstName(), u.getLastName(), u.getEmail(),
				u.isAdmin(),
				u.getId());
			if (u.getPasswordHash() != null) {
				executeUpdateStatement(
					"UPDATE users SET password_hash = ? " +
					"WHERE login = ?",
					u.getPasswordHash(),
					u.getLogin());
			}
		}
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IUserDAO#deleteUser(int)
	 */
	public void deleteUser(int id)
		throws DAOException
	{
		executeUpdateStatement("DELETE FROM users WHERE id = ?", id);
	}
}
