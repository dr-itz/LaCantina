package ch.sfdr.lacantina.security;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ch.sfdr.common.security.IUserAuth;
import ch.sfdr.common.security.SessionToken;
import ch.sfdr.lacantina.dao.db.DBConnection;

/**
 * User Authentication
 * @author D.Ritz
 * @author S.Freihofer
 */
public class UserAuth
	implements IUserAuth
{
	public SessionToken authenticateUser(String login, String pass)
	{
		DBConnection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = new DBConnection();
			stmt = conn.getConnection().prepareStatement("" +
				"SELECT id, password_hash, is_admin " +
				"FROM users " +
				"WHERE login = ?");
			stmt.setString(1, login);
			rs = stmt.executeQuery();

			if (rs.next()) {
				int uid = rs.getInt(1);
				String pw = rs.getString(2);
				boolean isAdmin = rs.getBoolean(3);
				// FIXME: check password
				return new SessionToken(login, uid, isAdmin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
			if (conn != null)
				conn.closeConnection();
		}
		return null;
	}
}
