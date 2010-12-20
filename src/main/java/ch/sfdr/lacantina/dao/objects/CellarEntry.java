package ch.sfdr.lacantina.dao.objects;

/**
 * A wine cellar entry
 * @author S.Freihofer
 */
public class CellarEntry
{
	private int id;
	private int winecellarId;
	private int year;
	private int quantity;
	private Wine wine;

	/**
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * @return the winecellarId
	 */
	public int getWinecellarId()
	{
		return winecellarId;
	}

	/**
	 * @param winecellarId the winecellarId to set
	 */
	public void setWinecellarId(int winecellarId)
	{
		this.winecellarId = winecellarId;
	}

	/**
	 * @return the year
	 */
	public int getYear()
	{
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year)
	{
		this.year = year;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity()
	{
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	/**
	 * @return the wine
	 */
	public Wine getWine()
	{
		return wine;
	}

	/**
	 * @param wine the wine to set
	 */
	public void setWine(Wine wine)
	{
		this.wine = wine;
	}
}
