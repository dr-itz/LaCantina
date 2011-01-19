package ch.sfdr.lacantina.dao.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.PagingCookie;
import ch.sfdr.lacantina.dao.objects.Wine;

/**
 * Tests for DbWineDAO
 * @author D. Ritz
 */
public class DbWineDAOTest
	extends AbstractJDBCTest
{
	private DbWineDAO me;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		me = new DbWineDAO(getConnection());
	}

	@Test
	public void testGetWine()
		throws DAOException
	{
		prepareQueryWithResult(
			"SELECT id, user_id, name, producer, country, region, description," +
			"  bottle_size " +
			"FROM wines " +
			"WHERE id = ?",
			"WineQuery",
			new Object[][] {
				{ 123, 56, "name", "producer", "country", "region", "description", 75 },
			},
			123
		);

		Wine w = me.getWine(123);
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
	public void testGetWineNonExist()
		throws DAOException
	{
		Wine w = me.getWine(1234);
		assertNull(w);
	}

	@Test
	public void testGetWineList()
		throws DAOException
	{
		prepareQueryWithResult(
			"SELECT COUNT(*) FROM wines WHERE user_id = ?",
			"CountQuery",
			new Object[][] {
				{ 2 },
			},
			123);
		prepareQueryWithResult(
			"SELECT id, user_id, name, producer, country, region, description," +
			"  bottle_size " +
			"FROM wines " +
			"WHERE user_id = ? " +
			"ORDER BY producer DESC, country, region, producer, name, bottle_size " +
			"LIMIT ? OFFSET ?",
			"WineQuery",
			new Object[][] {
				{ 123, 56, "name", "producer", "country", "region", "description", 75 },
				{ 124, 56, "name2", "producer2", "country2", "region2", "description2", 75 },
			},
			123, 20, 0
		);

		PagingCookie pc = new PagingCookie();
		pc.setSortKey("producer");
		pc.setSortDesc(true);
		List<Wine> list = me.getWineList(123, pc);
		assertEquals(2, list.size());

		Wine w = list.get(0);
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
	public void testGetWineListNonPaging()
		throws DAOException
	{
		prepareQueryWithResult(
			"SELECT id, user_id, name, producer, country, region, description," +
			"  bottle_size " +
			"FROM wines " +
			"WHERE user_id = ? " +
			"ORDER BY country, region, producer, name, bottle_size",
			"WineQuery",
			new Object[][] {
				{ 123, 56, "name", "producer", "country", "region", "description", 75 },
				{ 124, 56, "name2", "producer2", "country2", "region2", "description2", 75 },
			},
			123
		);

		List<Wine> list = me.getWineList(123, null);
		assertEquals(2, list.size());

		Wine w = list.get(0);
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
	public void testStoreWineInsert()
		throws DAOException
	{
		Wine w = new Wine();
		w.setUserId(123);
		w.setName("name");
		w.setProducer("producer");
		w.setCountry("country");
		w.setRegion("region");
		w.setDescription("description");
		w.setBottleSize(75);

		prepareUpdate(
			"INSERT INTO wines" +
			"  (user_id, name, producer, country, region, description," +
			"   bottle_size) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?)",
			"WineInsert",
			123, "name", "producer", "country", "region", "description",
			75);

		me.storeWine(w);
	}

	@Test
	public void testStoreWineUpdate()
		throws DAOException
	{
		Wine w = new Wine();
		w.setId(546);
		w.setUserId(123);
		w.setName("name");
		w.setProducer("producer");
		w.setCountry("country");
		w.setRegion("region");
		w.setDescription("description");
		w.setBottleSize(75);

		prepareUpdate(
			"UPDATE wines SET" +
			"  name = ?, producer = ?, country = ?, region = ?," +
			"  description = ?, bottle_size = ? " +
			"WHERE id = ?",
			"WineUpdate",
			"name", "producer", "country", "region", "description",
			75, 546);

		me.storeWine(w);
	}

	@Test
	public void testDeleteWine()
		throws DAOException
	{
		prepareUpdate(
			"DELETE FROM wines WHERE id = ?",
			"WineDelete",
			123);
		me.deleteWine(123);
	}
}
