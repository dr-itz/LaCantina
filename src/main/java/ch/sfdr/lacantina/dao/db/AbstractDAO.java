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
	public static final String DEFAULT_SORT = "default";

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
	 * retrieves the sort mapping. this uses a linear search which is not that
	 * fast, a hash would be faster, but with so small column counts it's not
	 * worth the code complexity.
	 * @param pc the paging cookie
	 * @return the order by columns
	 */
	private String getOrderBy(PagingCookie pc)
	{
		SortPair[] map = getSortMapping();
		if (map == null || map.length == 0)
			return null;

		String key = pc.getSortKey();
		String def = null;
		StringBuilder sb = new StringBuilder();
		for (SortPair sp : map) {
			if (DEFAULT_SORT.equals(sp.key)) {
				def = sp.value;
			} else {
				if (sp.key.equals(key)) {
					sb.append(sp.value);
					if (pc.isSortDesc())
						sb.append(" DESC");
					break;
				}
			}
		}

		// always add default order for more natural sorting
		if (def != null) {
			if (sb.length() > 0)
				sb.append(", ");
			sb.append(def);
		}

		return sb.toString();
	}

	/**
	 * returns the sort mapping used to construct the ORDER BY statement
	 * @return SortPair[]
	 */
	protected SortPair[] getSortMapping()
	{
		return null;
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
		if (pc == null)
			return getRowList(query, bind);

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

				// prepare the query
				StringBuilder sb = new StringBuilder();
				sb.append(query);
				String orderby = getOrderBy(pc);
				if (orderby != null && orderby.length() != 0)
					sb.append(" ORDER BY ").append(orderby);
				sb.append(" LIMIT ? OFFSET ?");

				// get the rows
				stmt = conn.prepareStatement(sb.toString());
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
	 * returns a new SortPair
	 * @param key
	 * @param value
	 * @return SortPair
	 */
	public static SortPair sortPair(String key, String value)
	{
		return new SortPair(key, value);
	}

	/**
	 * defines how a row is read
	 * @param rs the ResultSet to read from
	 * @return the row read
	 */
	public abstract T readRow(ResultSet rs)
		throws SQLException;

	/**
	 * a key value pair for sort column mapping
	 */
	public static class SortPair
	{
		public String key;
		public String value;

		public SortPair(String key, String value)
		{
			this.key = key;
			this.value = value;
		}
	}
}
