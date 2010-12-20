package ch.sfdr.lacantina.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.ICellarEntryDAO;
import ch.sfdr.lacantina.dao.objects.CellarEntry;
import ch.sfdr.lacantina.dao.objects.Wine;

/**
 * DAO implementation for CellarEntry
 * @author S.Freihofer
 */
public class DbCellarEntryDAO
	extends AbstractDAO<CellarEntry>
	implements ICellarEntryDAO
{
	/**
	 * creates a cellar entry DAO connected to a database
	 * @param conn the connection
	 */
	public DbCellarEntryDAO(Connection conn)
	{
		super(conn);
	}

	/**
	 * Query used to read cellar entries
	 */
	private static final String CELLARENTRY_SELECT =
		"SELECT y.id, y.winecellar_id, y.year, y.quantity, w.id, w.user_id, w.name," +
		"  w.producer, w.country, w.region, w.description, w.bottle_size " +
		"FROM wine_years y INNER JOIN wines w" +
		"  ON y.wine_id = w.id ";

	/*
	 * @see ch.sfdr.lacantina.dao.db.AbstractDAO#readRow(java.sql.ResultSet)
	 */
	@Override
	public CellarEntry readRow(ResultSet rs)
		throws SQLException
	{
		CellarEntry ce = new CellarEntry();

		int n = 1;

		ce.setId(rs.getInt(n++));
		ce.setWinecellarId(rs.getInt(n++));
		ce.setYear(rs.getInt(n++));
		ce.setQuantity(rs.getInt(n++));

		Wine w = new Wine();

		w.setId(rs.getInt(n++));
		w.setUserId(rs.getInt(n++));
		w.setName(rs.getString(n++));
		w.setProducer(rs.getString(n++));
		w.setCountry(rs.getString(n++));
		w.setRegion(rs.getString(n++));
		w.setDescription(rs.getString(n++));
		w.setBottleSize(rs.getShort(n++));

		ce.setWine(w);

		return ce;
	}

	/*
	 * @see ch.sfdr.lacantina.dao.ICellarEntryDAO#getCellarEntries(int, int)
	 */
	public List<CellarEntry> getCellarEntries(int winecellarId, int userId)
		throws DAOException
	{
		return getRowList(
			CELLARENTRY_SELECT +
			"WHERE y.winecellar_id = ? AND w.user_id = ?",
			winecellarId, userId);
	}
}
