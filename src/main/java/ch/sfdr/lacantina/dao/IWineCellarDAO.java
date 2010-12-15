package ch.sfdr.lacantina.dao;

import java.util.List;

import ch.sfdr.lacantina.dao.objects.WineCellar;

/**
 * DAO interface for wine cellar related DB queries
 * @author S.Freihofer
 */
public interface IWineCellarDAO
{
	/**
	 * reads a wine cellar by ID
	 * @param id The wine cellar id
	 * @return Wine cellar if found, otherwise null
	 * @throws DAOException
	 */
	WineCellar getWineCellar(int id)
		throws DAOException;

	/**
	 * reads a list of wines
	 * @param userId the user ID
	 * @return List of wine cellar
	 * @throws DAOException
	 */
	List<WineCellar> getWineCellars(int userId)
		throws DAOException;

	/**
	 * stores a wine cellar (either insert or update)
	 * @param wc the wine cellar
	 * @throws DAOException
	 */
	void storeWineCellar(WineCellar wc)
		throws DAOException;

	/**
	 * deletes a wine cellar
	 * @param id wine cellar ID
	 * @throws DAOException
	 */
	void deleteWineCellar(int id)
		throws DAOException;
}
