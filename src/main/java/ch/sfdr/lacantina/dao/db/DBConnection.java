package ch.sfdr.lacantina.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * base class for db connections
 * @author S.Freihofer
 */
public class DBConnection
{
	protected Connection conn = null;
	private static DataSource dataSource = null;

	/**
	 * sets the data source for testing purposes
	 * @param ds the data source
	 */
	public static void setDataSource(DataSource ds)
	{
		dataSource = ds;
	}

	private static DataSource getDataSource()
	{
		if (dataSource != null)
			return dataSource;

		DataSource ds = null;
		InitialContext ic = null;
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:/comp/env/jdbc/LaCantinaDB");
		} catch (NamingException e) {
			return null;
		} finally {
			try {
				if (ic != null)
					ic.close();
			} catch (Exception e) {
				// ignored
			}
		}
		return ds;
	}

	/**
	 * creates a database connection from either the JNDI or a previously set data source
	 * @throws Exception
	 */
	public DBConnection()
		throws Exception
	{
		DataSource ds = getDataSource();
		if (ds == null)
			throw new Exception("no data source");
		
		conn = ds.getConnection();
	}

	/**
	 * returns the JDBC connection
	 * @return the connection
	 */
	public Connection getConnection()
	{
		return conn;
	}

	/**
	 * closes the JDBC connection
	 */
	public void closeConnection()
	{
		if (conn == null)
			return;
		try {
			conn.close();
		} catch (SQLException e) {
			// ignored
		}
	}

	/**
	 * closes a statement
	 * @param stmt the statement
	 */
	public static void closeStatement(PreparedStatement stmt)
	{
		if (stmt == null)
			return;
		try {
			stmt.close();
		} catch (SQLException e) {

		}
	}

	/**
	 * closes a result set, ignoring any exception
	 * @param rs the ResultSet to close
	 */
	public static void closeResultSet(ResultSet rs)
	{
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			; // ignored
		}
	}
}
