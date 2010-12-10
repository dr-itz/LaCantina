package ch.sfdr.lacantina.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.sfdr.lacantina.dao.DAOException;

/**
 * Abstract base class for DAOs. Ensures JDBC cleanup and exception handling
 * @author D.Ritz
 */
public abstract class AbstractDAO<T>
{
	protected Connection conn;

	/**
	 * create with connection
	 * @param conn the connection
	 */
	public AbstractDAO(Connection conn)
	{
		this.conn = conn;
	}

	/**
	 * fills in bind params for a statement
	 * @param stmt the prepared statement
	 * @param bind the bind params
	 * @throws SQLException
	 */
	private void fillStatement(PreparedStatement stmt, Object... bind)
		throws SQLException
	{
		for (int i = 0; i < bind.length; i++)
			stmt.setObject(i + 1, bind[i]);
	}

	/**
	 * reads a single row
	 * @param query the query
	 * @param bind bind params
	 * @return the row read
	 * @throws DAOException
	 */
	protected T getSingleRow(String query, Object... bind)
		throws DAOException
	{
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(query);
			fillStatement(stmt, bind);

			rs = stmt.executeQuery();
			if (rs.next())
				return readRow(rs);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}
		return null;
	}

	/**
	 * reads a list of rows
	 * @param query the query
	 * @param bind bind params
	 * @return the list of rows read
	 * @throws DAOException
	 */
	protected List<T> getRowList(String query, Object... bind)
		throws DAOException
	{
		List<T> list = new ArrayList<T>();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(query);
			fillStatement(stmt, bind);

			rs = stmt.executeQuery();
			while (rs.next())
				list.add(readRow(rs));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}

		return list;
	}

	/**
	 * executes a statement without results
	 * @param query the query string
	 * @param bind the bind params
	 * @throws DAOException
	 */
	protected void executeUpdateStatement(String query, Object... bind)
		throws DAOException
	{
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(query);
			fillStatement(stmt, bind);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBConnection.closeStatement(stmt);
		}
	}

	/**
	 * defines how a row is read
	 * @param rs the ResultSet to read from
	 * @return the row read
	 */
	public abstract T readRow(ResultSet rs)
		throws SQLException;
}
