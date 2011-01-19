package ch.sfdr.lacantina.dao.db;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.PagingCookie;
import ch.sfdr.lacantina.dao.objects.CellarEntry;
import ch.sfdr.lacantina.dao.objects.Wine;


/**
 * Tests for DbCellarEntryDAO
 * @author S.Freihofer
 */
public class DbCellarEntryDAOTest
	extends AbstractJDBCTest
{
	private DbCellarEntryDAO me;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		me = new DbCellarEntryDAO(getConnection());
	}

	@Test
	public void testGetCellarEntry()
		throws DAOException
	{
		prepareQueryWithResult(
			"SELECT y.id, y.winecellar_id, y.year, y.quantity, y.rating_points, y.rating_text," +
			"  w.id, w.user_id, w.name, w.producer, w.country, w.region, w.description, w.bottle_size " +
			"FROM wine_years y INNER JOIN wines w" +
			"  ON y.wine_id = w.id " +
			"WHERE y.id = ? AND w.user_id = ?",
			"CellarEntryQuery",
			new Object [][] {
				{ 456, 789, 2005, 6, 10, "text", 123, 56, "name", "producer", "country", "region", "description", 75 },
			},
			456, 56
		);

		CellarEntry ce = me.getCellarEntry(456, 56);
		assertEquals(456, ce.getId());
		assertEquals(2005, ce.getYear());
		assertEquals(6, ce.getQuantity());
		assertEquals(10, ce.getRatingPoints());
		assertEquals("text", ce.getRatingText());

		Wine w = ce.getWine();
		assertEquals(123, w.getId());
		assertEquals(56, w.getUserId());
		assertEquals("name", w.getName());
		assertEquals("producer", w.getProducer());
		assertEquals("country", w.getCountry());
		assertEquals("region", w.getRegion());
		assertEquals("description", w.getDescription());
		assertEquals(75, w.getBottleSize());
	}

	@Test
	public void testGetCellarEntryList()
		throws DAOException
	{
		prepareQueryWithResult(
			"SELECT COUNT(*) " +
			"FROM wine_years y INNER JOIN wines w" +
			"  ON y.wine_id = w.id " +
			"WHERE y.winecellar_id = ? AND w.user_id = ?",
			"CountQuery",
			new Object[][] {
				{ 2 },
			},
			789, 56);

		prepareQueryWithResult(
			"SELECT y.id, y.winecellar_id, y.year, y.quantity, y.rating_points, y.rating_text," +
			"  w.id, w.user_id, w.name, w.producer, w.country, w.region, w.description, w.bottle_size " +
			"FROM wine_years y INNER JOIN wines w" +
			"  ON y.wine_id = w.id " +
			"WHERE y.winecellar_id = ? AND w.user_id = ?" +
			" ORDER BY w.country, w.region, w.name" +
			" LIMIT ? OFFSET ?",
			"CellarEntryListQuery",
			new Object [][] {
				{ 456, 789, 2005, 6, 10, "text", 123, 56, "name", "producer", "country", "region", "description", 75 },
				{ 457, 789, 2004, 12, 5, "text2", 124, 56, "name2", "producer2", "country2", "region2", "description2", 75 },
			},
			789, 56, 20, 0
		);

		PagingCookie pc = new PagingCookie();
		List<CellarEntry> list = me.getCellarEntries(789, 56, pc);
		assertEquals(2, list.size());

		CellarEntry ce = list.get(0);
		assertEquals(456, ce.getId());
		assertEquals(2005, ce.getYear());
		assertEquals(6, ce.getQuantity());
		assertEquals(10, ce.getRatingPoints());
		assertEquals("text", ce.getRatingText());

		Wine w = ce.getWine();
		assertEquals(123, w.getId());
		assertEquals(56, w.getUserId());
		assertEquals("name", w.getName());
		assertEquals("producer", w.getProducer());
		assertEquals("country", w.getCountry());
		assertEquals("region", w.getRegion());
		assertEquals("description", w.getDescription());
		assertEquals(75, w.getBottleSize());
	}

	@Test
	public void testStoreCellarEntryInsert()
		throws DAOException
	{
		CellarEntry ce = new CellarEntry();
		ce.setWinecellarId(789);
		ce.getWine().setId(123);
		ce.setYear(2005);
		ce.setQuantity(6);
		ce.setRatingPoints(10);
		ce.setRatingText("text");

		prepareUpdate(
			"INSERT INTO wine_years" +
			"  (winecellar_id, wine_id, year, quantity, rating_points, rating_text) " +
			"VALUES (?, ?, ?, ?, ?, ?)",
			"CellarEntryInsert",
			789, 123, 2005, 6, 10, "text");

		me.storeCellarEntry(ce);
	}

	@Test
	public void testStoreCellarEntryUpdate()
		throws DAOException
	{
		CellarEntry ce = new CellarEntry();
		ce.setId(1);
		ce.setWinecellarId(789);
		ce.getWine().setId(123);
		ce.setYear(2005);
		ce.setQuantity(6);
		ce.setRatingPoints(10);
		ce.setRatingText("text");

		prepareUpdate(
			"UPDATE wine_years SET" +
			"  winecellar_id = ?, wine_id = ?, year = ?, quantity = ?, rating_points = ?, rating_text = ? " +
			"WHERE id = ?",
			"CellarEntryUpdate",
			789, 123, 2005, 6, 10, "text", 1);

		me.storeCellarEntry(ce);
	}

	@Test
	public void testDeleteCellarEntry()
		throws DAOException
	{
		prepareUpdate(
			"DELETE FROM wine_years WHERE id = ?",
			"CellarEntryDelete",
			1);
		me.deleteCellarEntry(1);
	}

	@Test
	public void testGetWineRatingList()
		throws DAOException
	{
		prepareQueryWithResult(
			"SELECT COUNT(*) " +
			"FROM wine_years y INNER JOIN wines w" +
			"  ON y.wine_id = w.id " +
			"WHERE y.rating_points >= ?",
			"CountQuery",
			new Object[][] {
				{ 2 },
			},
			0);

		prepareQueryWithResult(
			"SELECT y.id, y.winecellar_id, y.year, y.quantity, y.rating_points, y.rating_text," +
			"  w.id, w.user_id, w.name, w.producer, w.country, w.region, w.description, w.bottle_size " +
			"FROM wine_years y INNER JOIN wines w" +
			"  ON y.wine_id = w.id " +
			"WHERE y.rating_points >= ?" +
			" ORDER BY w.country, w.region, w.name" +
			" LIMIT ? OFFSET ?",
			"WineRatingListQuery",
			new Object [][] {
				{ 456, 789, 2005, 6, 10, "text", 123, 56, "name", "producer", "country", "region", "description", 75 },
				{ 457, 789, 2004, 12, 5, "text2", 124, 56, "name2", "producer2", "country2", "region2", "description2", 75 },
			},
			0, 20, 0
		);

		PagingCookie pc = new PagingCookie();
		List<CellarEntry> ratingList = me.getWineRatings(0, pc);
		assertEquals(2, ratingList.size());

		CellarEntry ce = ratingList.get(0);
		assertEquals(456, ce.getId());
		assertEquals(2005, ce.getYear());
		assertEquals(6, ce.getQuantity());
		assertEquals(10, ce.getRatingPoints());
		assertEquals("text", ce.getRatingText());

		Wine w = ce.getWine();
		assertEquals(123, w.getId());
		assertEquals(56, w.getUserId());
		assertEquals("name", w.getName());
		assertEquals("producer", w.getProducer());
		assertEquals("country", w.getCountry());
		assertEquals("region", w.getRegion());
		assertEquals("description", w.getDescription());
		assertEquals(75, w.getBottleSize());
	}
}
