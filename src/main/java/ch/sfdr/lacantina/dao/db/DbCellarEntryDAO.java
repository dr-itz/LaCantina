package ch.sfdr.lacantina.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.ICellarEntryDAO;
import ch.sfdr.lacantina.dao.PagingCookie;
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
	private static final String CELLARENTRY_FROM =
		"FROM wine_years y INNER JOIN wines w" +
		"  ON y.wine_id = w.id ";

	private static final String CELLARENTRY_SELECT =
		"SELECT y.id, y.winecellar_id, y.year, y.quantity, y.rating_points, y.rating_text," +
		"  w.id, w.user_id, w.name, w.producer, w.country, w.region, w.description, w.bottle_size " +
		CELLARENTRY_FROM;

	private static final String CELLARENTRY_LIST_WHERE =
		"WHERE y.winecellar_id = ? AND w.user_id = ?";

	/**
	 * sort mapping
	 */
	private static final SortPair[] SORT_MAP = {
		sortPair(DEFAULT_SORT, "w.country, w.region, w.name"),
		sortPair("name", "w.name"),
		sortPair("producer", "w.producer"),
		sortPair("country", "w.country"),
		sortPair("region", "w.region"),
		sortPair("quantity", "y.quantity"),
		sortPair("year", "y.year"),
		sortPair("ratingPoints", "y.rating_points"),
		sortPair("ratingText", "y.rating_text"),
	};

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
		ce.setRatingPoints(rs.getInt(n++));
		ce.setRatingText(rs.getString(n++));

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
	 * @see ch.sfdr.lacantina.dao.db.AbstractDAO#getSortMapping()
	 */
	@Override
	protected SortPair[] getSortMapping()
	{
		return SORT_MAP;
	}

	/*
	 * @see ch.sfdr.lacantina.dao.ICellarEntryDAO#getCellarEntry(int)
	 */
	public CellarEntry getCellarEntry(int id, int userId)
		throws DAOException
	{
		return getSingleRow(
			CELLARENTRY_SELECT +
			"WHERE y.id = ? AND w.user_id = ?",
			id, userId);
	}

	/*
	 * @see ch.sfdr.lacantina.dao.ICellarEntryDAO#getCellarEntry(int)
	 */
	public CellarEntry getCellarEntry(int cellarId, int wineId, int year)
		throws DAOException
	{
		return getSingleRow(
			CELLARENTRY_SELECT +
			"WHERE y.winecellar_id = ? AND y.wine_id = ? AND y.year = ?",
			cellarId, wineId, year);
	}

	/*
	 * @see ch.sfdr.lacantina.dao.ICellarEntryDAO#getCellarEntries(int, int)
	 */
	public List<CellarEntry> getCellarEntries(int winecellarId, int userId,
			PagingCookie pc)
		throws DAOException
	{
		return getPagedRowList(
			"SELECT COUNT(*) " + CELLARENTRY_FROM + CELLARENTRY_LIST_WHERE,
			CELLARENTRY_SELECT + CELLARENTRY_LIST_WHERE,
			pc, winecellarId, userId);
	}

	/*
	 * @see ch.sfdr.lacantina.dao.ICellarEntryDAO#storeCellarEntry(ch.sfdr.
	 * lacantina.dao.objects.CellarEntry)
	 */
	public void storeCellarEntry(CellarEntry ce)
		throws DAOException
	{
		if (ce.getId() == 0) {
			executeUpdateStatement(
				"INSERT INTO wine_years" +
				"  (winecellar_id, wine_id, year, quantity, rating_points, rating_text) " +
				"VALUES (?, ?, ?, ?, ?, ?)",
				ce.getWinecellarId(), ce.getWine().getId(), ce.getYear(),
				ce.getQuantity(), ce.getRatingPoints(), ce.getRatingText());
		} else {
			executeUpdateStatement(
				"UPDATE wine_years SET" +
				"  winecellar_id = ?, wine_id = ?, year = ?, quantity = ?, rating_points = ?, rating_text = ? " +
				"WHERE id = ?",
				ce.getWinecellarId(), ce.getWine().getId(), ce.getYear(),
				ce.getQuantity(), ce.getRatingPoints(), ce.getRatingText(), ce.getId());
		}
	}

	/*
	 * @see ch.sfdr.lacantina.dao.ICellarEntryDAO#deleteCellarEntry(int)
	 */
	public void deleteCellarEntry(int id)
		throws DAOException
	{
		executeUpdateStatement("DELETE FROM wine_years WHERE id = ?", id);
	}
}
