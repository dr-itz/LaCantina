package ch.sfdr.lacantina.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.IWineDAO;
import ch.sfdr.lacantina.dao.objects.Wine;

/**
 * DAO implementation for Wine
 * @author D.Ritz
 */
public class DbWineDAO
	extends AbstractDAO<Wine>
	implements IWineDAO
{
	/**
	 * creates a user DAO connected to a database
	 * @param conn
	 */
	public DbWineDAO(Connection conn)
	{
		super(conn);
	}

	/**
	 * Query used to read users
	 */
	private static final String USER_SELECT =
		"SELECT id, user_id, name, producer, country, region, description," +
		"  bottle_size " +
		"FROM wines ";

	/**
	 * reads a wine from the ResultSet
	 * @param rs the result set
	 * @return Wine
	 * @throws SQLException
	 */
	public Wine readRow(ResultSet rs)
		throws SQLException
	{
		Wine w = new Wine();

		int n = 1;
		w.setId(rs.getInt(n++));
		w.setUserId(rs.getInt(n++));
		w.setName(rs.getString(n++));
		w.setProducer(rs.getString(n++));
		w.setCountry(rs.getString(n++));
		w.setRegion(rs.getString(n++));
		w.setDescription(rs.getString(n++));
		w.setBottleSize(rs.getShort(n++));

		return w;
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IWineDAO#getWine(int)
	 */
	public Wine getWine(int id)
		throws DAOException
	{
		return getSingleRow(
			USER_SELECT +
			"WHERE id = ?",
			id);
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IWineDAO#getWines(int)
	 */
	public List<Wine> getWines(int user)
		throws DAOException
	{
		return getRowList(
			USER_SELECT +
			"WHERE user_id = ? " +
			"ORDER BY country, region, name",
			user);
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IWineDAO#storeWine(ch.sfdr.lacantina.dao.objects.Wine)
	 */
	public void storeWine(Wine w)
		throws DAOException
	{
		if (w.getId() == 0) {
			executeUpdateStatement(
				"INSERT INTO wines" +
				"  (user_id, name, producer, country, region, description," +
				"   bottle_size) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?)",
				w.getUserId(), w.getName(), w.getProducer(), w.getCountry(),
				w.getRegion(), w.getDescription(), w.getBottleSize());
		} else {
			executeUpdateStatement(
				"UPDATE wines SET" +
				"  name = ?, producer = ?, country = ?, region = ?," +
				"  description = ?, bottle_size = ? " +
				"WHERE id = ?",
				w.getName(),w.getProducer(), w.getCountry(),
				w.getRegion(), w.getDescription(), w.getBottleSize(),
				w.getId());
		}
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IWineDAO#deleteWine(int)
	 */
	public void deleteWine(int id)
		throws DAOException
	{
		executeUpdateStatement("DELETE FROM wines WHERE id = ?", id);
	}
}
