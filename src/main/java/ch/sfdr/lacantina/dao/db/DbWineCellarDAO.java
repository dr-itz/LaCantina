package ch.sfdr.lacantina.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.IWineCellarDAO;
import ch.sfdr.lacantina.dao.objects.WineCellar;

/**
 * DAO implementation for WineCellar
 * @author S.Freihofer
 */
public class DbWineCellarDAO
	extends AbstractDAO<WineCellar>
	implements IWineCellarDAO
{
	/**
	 * creates a wine cellar DAO connected to a database
	 * @param conn
	 */
	public DbWineCellarDAO(Connection conn)
	{
		super(conn);
	}

	/**
	 * Query used to read wine cellars
	 */
	private static final String WINECELLAR_SELECT =
		"SELECT id, user_id, name, capacity " +
		"FROM winecellars ";

	/*
	 * @see ch.sfdr.lacantina.dao.IWineCellarDAO#getWineCellar(int)
	 */
	public WineCellar getWineCellar(int id)
		throws DAOException
	{
		return getSingleRow(
			WINECELLAR_SELECT +
			"WHERE id = ?",
			id);
	}

	/*
	 * @see ch.sfdr.lacantina.dao.db.AbstractDAO#readRow(java.sql.ResultSet)
	 */
	@Override
	public WineCellar readRow(ResultSet rs)
		throws SQLException
	{
		WineCellar wc = new WineCellar();

		int n = 1;
		wc.setId(rs.getInt(n++));
		wc.setUserId(rs.getInt(n++));
		wc.setName(rs.getString(n++));
		wc.setCapacity(rs.getInt(n++));

		return wc;
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IWineCellarDAO#getWineCellars(int)
	 */
	public List<WineCellar> getWineCellars(int userId)
		throws DAOException
	{
		return getRowList(
			WINECELLAR_SELECT +
			"WHERE user_id = ? " +
			"ORDER BY name",
			userId);
	}

	/*
	 * @see
	 * ch.sfdr.lacantina.dao.IWineCellarDAO#storeWineCellar(ch.sfdr.lacantina
	 * .dao.objects.WineCellar)
	 */
	public void storeWineCellar(WineCellar wc)
		throws DAOException
	{
		if (wc.getId() == 0) {
			executeUpdateStatement(
				"INSERT into winecellars" +
				" (user_id, name, capacity) " +
				"VALUES (?, ?, ?)",
				wc.getUserId(), wc.getName(), wc.getCapacity());
		} else {
			executeUpdateStatement(
				"UPDATE winecellars SET" +
				" name = ?, capacity = ? " +
				"WHERE id = ?",
				wc.getName(), wc.getCapacity(), wc.getId());
		}
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IWineCellarDAO#deleteWineCellar(int)
	 */
	public void deleteWineCellar(int id)
		throws DAOException
	{
		executeUpdateStatement("DELETE FROM winecellars WHERE id = ?", id);
	}
}
