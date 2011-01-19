package ch.sfdr.lacantina.dao.objects;

/**
 * A wine
 * @author D.Ritz
 */
public class Wine
{
	private int id;
	private int userId;
	private String name;
	private String producer;
	private String country;
	private String region;
	private String description;
	private int bottleSize;

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
	 * @return the country
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	/**
	 * @return the region
	 */
	public String getRegion()
	{
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region)
	{
		this.region = region;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the bottleSize
	 */
	public int getBottleSize()
	{
		return bottleSize;
	}

	/**
	 * @param bottleSize the bottleSize to set
	 */
	public void setBottleSize(int bottleSize)
	{
		this.bottleSize = bottleSize;
	}

	/**
	 * returns a friendly name
	 * @return friendly String
	 */
	public String getFriendlyName()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(country);
		if (region != null && region.length() > 0)
			sb.append("/").append(region);
		sb.append(": ");
		sb.append(producer).append(": ").append(name).append(", ");
		sb.append(bottleSize).append("cl");
		return sb.toString();
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return getFriendlyName();
	}
}
