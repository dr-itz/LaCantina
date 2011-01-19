package ch.sfdr.lacantina.dao.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.PagingCookie;
import ch.sfdr.lacantina.dao.objects.ShoppingItem;

/**
 * Tests for DbWineDAO
 * @author D. Ritz
 */
public class DbShoppingListDAOTest
	extends AbstractJDBCTest
{
	private DbShoppingListDAO me;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		me = new DbShoppingListDAO(getConnection());
	}

	@Test
	public void testGetShoppingItem()
		throws DAOException
	{
		prepareQueryWithResult(
			"SELECT id, user_id, wine_id, name, producer, year, store, bottle_size, quantity " +
			"FROM shoppinglist " +
			"WHERE id = ?",
			"ShoppingItemQuery",
			new Object[][] {
				{ 123, 56, null, "name", "producer", 2005, "store", 75, 5 },
			},
			123
		);

		ShoppingItem si = me.getShoppingItem(123);
		assertEquals(123, si.getId());
		assertEquals(56, si.getUserId());
		assertNull(si.getWineId());
		assertEquals("name", si.getName());
		assertEquals("producer", si.getProducer());
		assertEquals(2005, si.getYear().intValue());
		assertEquals(75, si.getBottleSize());
		assertEquals(5, si.getQuantity());
	}

	@Test
	public void testGetShoppingItemNonExist()
		throws DAOException
	{
		ShoppingItem w = me.getShoppingItem(1234);
		assertNull(w);
	}

	@Test
	public void testGetShoppingList()
		throws DAOException
	{
		prepareQueryWithResult(
			"SELECT COUNT(*) FROM shoppinglist WHERE user_id = ?",
			"CountQuery",
			new Object[][] {
				{ 2 },
			},
			123);
		prepareQueryWithResult(
			"SELECT id, user_id, wine_id, name, producer, year, store, bottle_size, quantity " +
			"FROM shoppinglist " +
			"WHERE user_id = ? " +
			"ORDER BY producer DESC, name, producer, year, store, bottle_size, quantity " +
			"LIMIT ? OFFSET ?",
			"ShoppingItemQuery",
			new Object[][] {
				{ 123, 56, 456, "name", "producer", 2005, "store", 75, 5 },
				{ 124, 56, null, "name2", "producer2", null, "store2", 75, 5 },
			},
			123, 20, 0
		);

		PagingCookie pc = new PagingCookie();
		pc.setSortKey("producer");
		pc.setSortDesc(true);
		List<ShoppingItem> list = me.getShoppingList(123, pc);
		assertEquals(2, list.size());

		ShoppingItem si = list.get(0);
		assertEquals(123, si.getId());
		assertEquals(56, si.getUserId());
		assertEquals(456, si.getWineId().intValue());
		assertEquals("name", si.getName());
		assertEquals("producer", si.getProducer());
		assertEquals(2005, si.getYear().intValue());
		assertEquals(75, si.getBottleSize());
		assertEquals(5, si.getQuantity());
		si = list.get(1);
		assertNull(si.getYear());
	}

	@Test
	public void testStoreShoppingItemInsert()
		throws DAOException
	{
		ShoppingItem si = new ShoppingItem();
		si.setUserId(123);
		si.setName("name");
		si.setProducer("producer");
		si.setStore("store");
		si.setYear(2005);
		si.setBottleSize(75);
		si.setQuantity(5);

		prepareUpdate(
			"INSERT INTO shoppinglist" +
			"  (user_id, wine_id, name, producer, year, quantity, store," +
			"   bottle_size) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
			"ShoppingItemInsert",
			123, null, "name", "producer", 2005, 5, "store", (short) 75);

		me.storeShoppingItem(si);
	}

	@Test
	public void testStoreShoppingItemUpdate()
		throws DAOException
	{
		ShoppingItem si = new ShoppingItem();
		si.setId(546);
		si.setUserId(123);
		si.setName("name");
		si.setProducer("producer");
		si.setStore("store");
		si.setYear(2005);
		si.setBottleSize(75);
		si.setQuantity(5);

		prepareUpdate(
			"UPDATE shoppinglist SET" +
			"  name = ?, producer = ?, year = ?, quantity = ?, store = ?," +
			"  bottle_size = ? " +
			"WHERE id = ?",
			"ShoppingItemUpdate",
			"name", "producer", 2005, 5, "store", (short) 75, 546);

		me.storeShoppingItem(si);
	}

	@Test
	public void testDeleteShoppingItem()
		throws DAOException
	{
		prepareUpdate(
			"DELETE FROM shoppinglist WHERE id = ?",
			"ShoppingItemDelete",
			123);
		me.deleteShoppingItem(123);
	}
}
