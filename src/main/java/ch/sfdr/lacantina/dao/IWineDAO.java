package ch.sfdr.lacantina.dao;

import java.util.List;

import ch.sfdr.lacantina.dao.objects.Wine;

/**
 * DAO interface for wine related DB queries
 * @author D.Ritz
 */
public interface IWineDAO
{
	/**
	 * read a wine by ID
	 * @param id The wine ID
	 * @return Wine if found, null otherwise
	 * @throws DAOException
	 */
	Wine getWine(int id)
		throws DAOException;

	/**
	 * reads a list of wines
	 * @param user the user ID
	 * @param pc the paging cookie
	 * @return List of Wine
	 * @throws DAOException
	 */
	List<Wine> getWineList(int user, PagingCookie pc)
		throws DAOException;

	/**
	 * stores a wine (either insert or update)
	 * @param w the wine
	 * @throws DAOException
	 */
	void storeWine(Wine w)
		throws DAOException;

	/**
	 * deletes a wine
	 * @param id the wine id
	 * @throws DAOException
	 */
	void deleteWine(int id)
		throws DAOException;
}
