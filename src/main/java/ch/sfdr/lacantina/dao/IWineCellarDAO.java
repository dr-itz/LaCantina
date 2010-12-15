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
	 * read a wine cellar by ID
	 * @param id The wine cellar id
	 * @return Wine cellar if found, otherwise null
	 * @throws DAOException
	 */
	WineCellar getWineCellar(int id)
		throws DAOException;

	/**
	 *
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	List<WineCellar> getWineCellars(int userId)
		throws DAOException;
}
