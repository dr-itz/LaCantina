package ch.sfdr.lacantina.dao;

/**
 * 'Cookie' for paging. Holds the current position and the number of elements
 * in a list.
 * @author D.Ritz
 */
public class PagingCookie
{
	private static final int DEFAULT_PAGE_SIZE = 20;

	private int offset;
	private int limit = DEFAULT_PAGE_SIZE;
	private int totalRows;
	private int pageRows;
	private String sortKey = "";
	private boolean sortDesc;

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(offset).append(",");
		sb.append(limit).append(",");
		sb.append(totalRows).append(",");
		sb.append(pageRows).append(",");
		sb.append(sortKey).append(",");
		sb.append(sortDesc ? "1" : "0");
		return sb.toString();
	}

	private static int string2int(String str)
	{
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * creates a paging cookie from String
	 * @param str input string
	 * @return PagingCookie
	 */
	public static PagingCookie fromString(String str)
	{
		PagingCookie pc = new PagingCookie();
		String[] parts = str.split(",");
		if (parts.length > 0)
			pc.offset = string2int(parts[0]);
		if (parts.length > 1)
			pc.limit = string2int(parts[1]);
		if (parts.length > 2)
			pc.totalRows = string2int(parts[2]);
		if (parts.length > 3)
			pc.pageRows = string2int(parts[3]);
		if (parts.length > 4)
			pc.sortKey = parts[4];
		if (parts.length > 5)
			pc.sortDesc = "1".equals(parts[5]);
		return pc;
	}

	/**
	 * sets the offset to the next page
	 */
	public void next()
	{
		offset += limit;
	}

	/**
	 * sets the offset to the previous page
	 * @return true if offset was updated
	 */
	public boolean prev()
	{
		offset -= limit;
		if (offset < 0) {
			offset = 0;
			return false;
		}
		return true;
	}

	/**
	 * @return the offset
	 */
	public int getOffset()
	{
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset)
	{
		this.offset = offset;
	}

	/**
	 * @return the limit
	 */
	public int getLimit()
	{
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	/**
	 * @return the totalRows
	 */
	public int getTotalRows()
	{
		return totalRows;
	}

	/**
	 * @param totalRows the totalRows to set
	 */
	public void setTotalRows(int totalRows)
	{
		this.totalRows = totalRows;
	}

	/**
	 * @return the pageRows
	 */
	public int getPageRows()
	{
		return pageRows;
	}

	/**
	 * @param pageRows the pageRows to set
	 */
	public void setPageRows(int pageRows)
	{
		this.pageRows = pageRows;
	}

	/**
	 * @return the sortKey
	 */
	public String getSortKey()
	{
		return sortKey;
	}

	/**
	 * @param sortKey the sortKey to set
	 */
	public void setSortKey(String sortKey)
	{
		this.sortKey = sortKey;
	}

	/**
	 * @return the sortDesc
	 */
	public boolean isSortDesc()
	{
		return sortDesc;
	}

	/**
	 * @param sortDesc the sortDesc to set
	 */
	public void setSortDesc(boolean sortDesc)
	{
		this.sortDesc = sortDesc;
	}

	/**
	 * convenience helper for JSP: get the start of the range
	 * @return offset + 1
	 */
	public int getRangeStart()
	{
		return offset + 1;
	}

	/**
	 * convenience helper for JSP: get the end of the range
	 * @return offset + pageRows
	 */
	public int getRangeEnd()
	{
		return offset + pageRows;
	}

	/**
	 * convenience helper for JSP: returns a flag indicating if there are more pages
	 * @return true if there are more pages
	 */
	public boolean getHasMoreRows()
	{
		return offset + pageRows < totalRows;
	}
}
