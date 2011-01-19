package ch.sfdr.lacantina.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.IShoppingListDAO;
import ch.sfdr.lacantina.dao.PagingCookie;
import ch.sfdr.lacantina.dao.objects.ShoppingItem;

/**
 * DAO implementation for ShoppingList
 * @author D.Ritz
 */
public class DbShoppingListDAO
	extends AbstractDAO<ShoppingItem>
	implements IShoppingListDAO
{
	/**
	 * creates a user DAO connected to a database
	 * @param conn
	 */
	public DbShoppingListDAO(Connection conn)
	{
		super(conn);
	}

	/**
	 * Query used to read shopping items
	 */
	private static final String USER_SELECT =
		"SELECT id, user_id, wine_id, name, producer, year, store, bottle_size, quantity " +
		"FROM shoppinglist ";

	/**
	 * sort mapping
	 */
	private static final SortPair[] SORT_MAP = {
		sortPair(DEFAULT_SORT, "name, producer, year, store, bottle_size, quantity"),
		sortPair("name", "name"),
		sortPair("producer", "producer"),
		sortPair("store", "store"),
		sortPair("year", "year"),
	};

	@Override
	public ShoppingItem readRow(ResultSet rs)
		throws SQLException
	{
		ShoppingItem i = new ShoppingItem();

		int n = 1;
		i.setId(rs.getInt(n++));
		i.setUserId(rs.getInt(n++));
		i.setWineId(rs.getInt(n++));
		if (rs.wasNull())
			i.setWineId(null);
		i.setName(rs.getString(n++));
		i.setProducer(rs.getString(n++));
		i.setYear(rs.getInt(n++));
		if (rs.wasNull())
			i.setYear(null);
		i.setStore(rs.getString(n++));
		i.setBottleSize(rs.getShort(n++));
		i.setQuantity(rs.getInt(n++));

		return i;
	}

	/*
	 * @see ch.sfdr.lacantina.dao.db.AbstractDAO#getSortMapping()
	 */
	@Override
	protected SortPair[] getSortMapping()
	{
		return SORT_MAP;
	}

	public ShoppingItem getShoppingItem(int id)
		throws DAOException
	{
		return getSingleRow(
			USER_SELECT +
			"WHERE id = ?",
			id);
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IWineDAO#getWineList(int, PagingCookie)
	 */
	public List<ShoppingItem> getShoppingList(int user, PagingCookie pc)
		throws DAOException
	{
		return getPagedRowList(
			"SELECT COUNT(*) FROM shoppinglist WHERE user_id = ?",
			USER_SELECT +
			"WHERE user_id = ?",
			pc, user);
	}

	public void storeShoppingItem(ShoppingItem w)
		throws DAOException
	{
		if (w.getId() == 0) {
			executeUpdateStatement(
				"INSERT INTO shoppinglist" +
				"  (user_id, wine_id, name, producer, year, quantity, store," +
				"   bottle_size) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
				w.getUserId(), w.getWineId(), w.getName(), w.getProducer(), w.getYear(),
				w.getQuantity(), w.getStore(), w.getBottleSize());
		} else {
			executeUpdateStatement(
				"UPDATE shoppinglist SET" +
				"  name = ?, producer = ?, year = ?, quantity = ?, store = ?," +
				"  bottle_size = ? " +
				"WHERE id = ?",
				w.getName(), w.getProducer(), w.getYear(), w.getQuantity(),
				w.getStore(), w.getBottleSize(), w.getId());
		}
	}

	/*
	 * @see ch.sfdr.lacantina.dao.IShoppingListDAO#deleteShoppingItem(int)
	 */
	public void deleteShoppingItem(int id)
		throws DAOException
	{
		executeUpdateStatement("DELETE FROM shoppinglist WHERE id = ?", id);
	}
}
