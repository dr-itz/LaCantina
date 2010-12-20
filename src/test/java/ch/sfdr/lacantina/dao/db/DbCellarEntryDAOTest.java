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
			"SELECT y.id, y.winecellar_id, y.year, y.quantity, w.id, w.user_id, w.name," +
			"  w.producer, w.country, w.region, w.description, w.bottle_size " +
			"FROM wine_years y INNER JOIN wines w" +
			"  ON y.wine_id = w.id " +
			"WHERE y.winecellar_id = ? AND w.user_id = ?" +
			" LIMIT ? OFFSET ?",
			"CellarEntryQuery",
			new Object [][] {
				{ 456, 789, 2005, 6, 123, 56, "name", "producer", "country", "region", "description", 75 },
				{ 457, 789, 2004, 12, 124, 56, "name2", "producer2", "country2", "region2", "description2", 75 },
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
