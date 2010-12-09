package ch.sfdr.lacantina.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.IUserDAO;
import ch.sfdr.lacantina.dao.objects.User;

/**
 * DAO implementation for User
 * @author D.Ritz
 */
public class DbUserDAO
	implements IUserDAO
{
	private Connection conn;

	/**
	 * creates a user DAO connected to a database
	 * @param conn
	 */
	public DbUserDAO(Connection conn)
	{
		this.conn = conn;
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
	private static User readUser(ResultSet rs)
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(
				USER_SELECT +
				"WHERE id = ?");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();
			if (rs.next())
				return readUser(rs);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}
		return null;
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IUserDAO#getUsers()
	 */
	public List<User> getUsers()
		throws DAOException
	{
		List<User> list = new ArrayList<User>();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(
				USER_SELECT +
				"ORDER BY last_name, first_name");

			rs = stmt.executeQuery();
			while (rs.next())
				list.add(readUser(rs));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}

		return list;
	}
}
