package ch.sfdr.lacantina.dao;

import java.util.List;

import ch.sfdr.lacantina.dao.objects.CellarEntry;

/**
 * DAO interface for cellar entry related DB queries
 * @author S.Freihofer
 */
public interface ICellarEntryDAO
{
	/**
	 * reads a list of cellar entries
	 * @param winecellarId the wine cellar ID
	 * @param userId the user ID
	 * @return List of cellar entries
	 * @throws DAOException
	 */
	List<CellarEntry> getCellarEntries(int winecellarId, int userId,
			PagingCookie pc)
		throws DAOException;

	/**
	 * reads a cellar entry by ID
	 * @param id the cellar entry ID
	 * @return cellar entry if found, otherwise null
	 * @throws DAOException
	 */
	CellarEntry getCellarEntry(int id, int userId)
		throws DAOException;

	/**
	 * reads a cellar entry by wine cellar ID, wine id and year
	 * @param cellarId ID of the wine cellar
	 * @param wineId ID of the wine
	 * @param year year of the wine
	 * @return cellar entry if found, otherwise null
	 * @throws DAOException
	 */
	CellarEntry getCellarEntry(int cellarId, int wineId, int year)
		throws DAOException;

	/**
	 * stores a cellar entry (either insert or update)
	 * @param ce the cellar entry
	 * @throws DAOException
	 */
	void storeCellarEntry(CellarEntry ce)
		throws DAOException;

	/**
	 * deletes a cellar entry
	 * @param id the cellar entry id
	 * @throws DAOException
	 */
	void deleteCellarEntry(int id)
		throws DAOException;

	/**
	 * reads a list of wine ratings
	 * @param minPoints the minimum rating points
	 * @return list of wine ratings
	 * @throws DAOException
	 */
	List<CellarEntry> getWineRatings(int minPoints, PagingCookie pc)
		throws DAOException;
}
