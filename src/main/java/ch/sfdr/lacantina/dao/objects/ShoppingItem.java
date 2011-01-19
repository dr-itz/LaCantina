package ch.sfdr.lacantina.dao.objects;

/**
 * A single item in a shopping list.
 * @author D.Ritz
 */
public class ShoppingItem
{
	private int id;
	private int userId;
	private Integer wineId;
	private String name;
	private String producer;
	private Integer year;
	private short bottleSize;
	private String store;
	private int quantity;

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
	 * @return the userId
	 */
	public int getUserId()
	{
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	/**
	 * @return the wineId
	 */
	public Integer getWineId()
	{
		return wineId;
	}

	/**
	 * @param wineId the wineId to set
	 */
	public void setWineId(Integer wineId)
	{
		this.wineId = wineId;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the producer
	 */
	public String getProducer()
	{
		return producer;
	}

	/**
	 * @param producer the producer to set
	 */
	public void setProducer(String producer)
	{
		this.producer = producer;
	}

	/**
	 * @return the year
	 */
	public Integer getYear()
	{
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year)
	{
		if (year != null && year == 0)
			this.year = null;
		else
			this.year = year;
	}

	/**
	 * @return the bottleSize
	 */
	public short getBottleSize()
	{
		return bottleSize;
	}

	/**
	 * @param bottleSize the bottleSize to set
	 */
	public void setBottleSize(int bottleSize)
	{
		this.bottleSize = (short) bottleSize;
	}

	/**
	 * @return the store
	 */
	public String getStore()
	{
		return store;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(String store)
	{
		this.store = store;
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
}
