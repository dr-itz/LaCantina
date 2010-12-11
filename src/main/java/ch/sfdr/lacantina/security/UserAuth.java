package ch.sfdr.lacantina.security;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.sfdr.common.security.IUserAuth;
import ch.sfdr.common.security.Password;
import ch.sfdr.common.security.SessionToken;
import ch.sfdr.lacantina.dao.db.DBConnection;

/**
 * User Authentication
 * @author S.Freihofer
 */
public class UserAuth
	implements IUserAuth
{
	private static final Log log = LogFactory.getLog(UserAuth.class);

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

				if (pw.equals(Password.getPasswordHash(pass)))
					return new SessionToken(login, uid, isAdmin);
			}
		} catch (Exception e) {
			log.warn(e);
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
			if (conn != null)
				conn.closeConnection();
		}
		return null;
	}
}
