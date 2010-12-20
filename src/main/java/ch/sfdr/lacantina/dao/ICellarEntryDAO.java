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
}
