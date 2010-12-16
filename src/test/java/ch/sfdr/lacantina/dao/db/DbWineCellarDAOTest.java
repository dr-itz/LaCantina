package ch.sfdr.lacantina.dao.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.objects.WineCellar;

/**
 * Tests for DbWineCellarDAO
 * @author S.Freihofer
 */
public class DbWineCellarDAOTest
	extends AbstractJDBCTest
{
	private DbWineCellarDAO me;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		me = new DbWineCellarDAO(getConnection());
	}

	@Test
	public void testGetWineCellar()
		throws DAOException
	{
		prepareQueryWithResult(
			"SELECT id, user_id, name, capacity " +
			"FROM winecellars " +
			"WHERE id = ?",
			"WineCellarQuery",
			new Object[][] {
				{ 123, 56, "name", 50 },
			},
			123
		);

		WineCellar wc = me.getWineCellar(123);
		assertEquals(123, wc.getId());
		assertEquals(56, wc.getUserId());
		assertEquals("name", wc.getName());
		assertEquals(50, wc.getCapacity());
	}

	@Test
	public void testGetWineCellarNonExist()
		throws DAOException
	{
		WineCellar wc = me.getWineCellar(1234);
		assertNull(wc);
	}

	@Test
	public void testGetWineCellarList()
		throws DAOException
	{
		prepareQueryWithResult(
			"SELECT id, user_id, name, capacity " +
			"FROM winecellars " +
			"WHERE user_id = ? " +
			"ORDER BY name",
			"WineCellarQuery",
			new Object[][] {
				{ 123, 56, "name", 50 },
				{ 124, 56, "name2", 50 },
			},
			123
		);

		List<WineCellar> list = me.getWineCellars(123);
		assertEquals(2, list.size());

		WineCellar wc = list.get(0);
		assertEquals(123, wc.getId());
		assertEquals(56, wc.getUserId());
		assertEquals("name", wc.getName());
		assertEquals(50, wc.getCapacity());
	}

	@Test
	public void testStoreWineCellarInsert()
		throws DAOException
	{
		WineCellar wc = new WineCellar();
		wc.setUserId(123);
		wc.setName("name");
		wc.setCapacity(50);

		prepareUpdate(
			"INSERT INTO winecellars" +
			" (user_id, name, capacity) " +
			"VALUES (?, ?, ?)",
			"WineCellarInsert",
			123, "name", 50);

		me.storeWineCellar(wc);
	}

	@Test
	public void testStoreWineCellarUpdate()
		throws DAOException
	{
		WineCellar wc = new WineCellar();
		wc.setId(546);
		wc.setUserId(123);
		wc.setName("name");
		wc.setCapacity(50);

		prepareUpdate(
			"UPDATE winecellars SET" +
			" name = ?, capacity = ? " +
			"WHERE id = ?",
			"WineCellarUpdate",
			"name", 50, 546);

		me.storeWineCellar(wc);
	}

	@Test
	public void testDeleteWineCellar()
		throws DAOException
	{
		prepareUpdate(
			"DELETE FROM winecellars WHERE id = ?",
			"WineCellarDelete",
			123);
		me.deleteWineCellar(123);
	}
}
