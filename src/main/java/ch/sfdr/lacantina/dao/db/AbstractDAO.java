package ch.sfdr.lacantina.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.PagingCookie;

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
	 * @return the next bind param index
	 * @throws SQLException
	 */
	private int fillStatement(PreparedStatement stmt, Object... bind)
		throws SQLException
	{
		for (int i = 0; i < bind.length; i++)
			stmt.setObject(i + 1, bind[i]);
		return bind.length + 1;
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
	 * reads a list of rows
	 * @param query the query
	 * @param pc the paging cookie
	 * @param bind bind params
	 * @return the list of rows read
	 * @throws DAOException
	 */
	protected List<T> getPagedRowList(String countQuery, String query,
			PagingCookie pc, Object... bind)
		throws DAOException
	{
		List<T> list = new ArrayList<T>(pc.getLimit());

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			// first get the row count
			stmt = conn.prepareStatement(countQuery);
			fillStatement(stmt, bind);
			rs = stmt.executeQuery();
			if (rs.next()) {
				pc.setTotalRows(rs.getInt(1));

				// cleanup
				rs.close();
				stmt.close();

				// get the rows
				stmt = conn.prepareStatement(query + " LIMIT ? OFFSET ?");
				int next = fillStatement(stmt, bind);
				stmt.setInt(next, pc.getLimit());
				stmt.setInt(next + 1, pc.getOffset());

				rs = stmt.executeQuery();
				while (rs.next())
					list.add(readRow(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}
		pc.setPageRows(list.size());

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
