package ch.sfdr.lacantina.dao;

import java.util.List;

import ch.sfdr.lacantina.dao.objects.ShoppingItem;

/**
 * DAO interface for wine related DB queries
 * @author D.Ritz
 */
public interface IShoppingListDAO
{
	/**
	 * read a Shopping Item by ID
	 * @param id The ShoppingItem ID
	 * @return ShoppingItem if found, null otherwise
	 * @throws DAOException
	 */
	ShoppingItem getShoppingItem(int id)
		throws DAOException;

	/**
	 * reads a list of ShoppingItems
	 * @param user the user ID
	 * @param pc the paging cookie
	 * @return List of ShoppingItem
	 * @throws DAOException
	 */
	List<ShoppingItem> getShoppingList(int user, PagingCookie pc)
		throws DAOException;

	/**
	 * stores a ShoppingItem (either insert or update)
	 * @param i the ShoppingItem
	 * @throws DAOException
	 */
	void storeShoppingItem(ShoppingItem i)
		throws DAOException;

	/**
	 * deletes a ShoppingItem
	 * @param id the ShoppingItem id
	 * @throws DAOException
	 */
	void deleteShoppingItem(int id)
		throws DAOException;
}
