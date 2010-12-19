package ch.sfdr.lacantina.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for PagingCookie
 * @author D.Ritz
 */
public class PagingCookieTest
{
	private PagingCookie me;

	@Before
	public void setUp()
		throws Exception
	{
		me = new PagingCookie();
	}

	@Test
	public void testToString()
	{
		assertEquals("0,20,0,0", me.toString());
	}

	@Test
	public void testFromString()
	{
		me = PagingCookie.fromString("10,5,50,5");
		assertEquals(5, me.getLimit());
		assertEquals(10, me.getOffset());
		assertEquals(50, me.getTotalRows());
		assertEquals(5, me.getPageRows());

		me = PagingCookie.fromString("blub,5,50,5");
		assertEquals(5, me.getLimit());
		assertEquals(0, me.getOffset());
		assertEquals(50, me.getTotalRows());
		assertEquals(5, me.getPageRows());
	}

	@Test
	public void testNext()
	{
		assertEquals(0, me.getOffset());
		me.next();
		assertEquals(20, me.getOffset());
	}

	@Test
	public void testPrev()
	{
		me.setOffset(30);
		me.prev();
		assertEquals(10, me.getOffset());
		me.prev();
		assertEquals(0, me.getOffset());
	}

	@Test
	public void testGetSetOffset()
	{
		assertEquals(0, me.getOffset());
		me.setOffset(20);
		assertEquals(20, me.getOffset());
	}

	@Test
	public void testGetSetLimit()
	{
		assertEquals(20, me.getLimit());
		me.setLimit(30);
		assertEquals(30, me.getLimit());
	}

	@Test
	public void testGetSetTotalRows()
	{
		assertEquals(0, me.getTotalRows());
		me.setTotalRows(40);
		assertEquals(40, me.getTotalRows());
	}

	@Test
	public void testGetSetPageRows()
	{
		assertEquals(0, me.getPageRows());
		me.setPageRows(10);
		assertEquals(10, me.getPageRows());
	}

	@Test
	public void testGetSetRangeStart()
	{
		assertEquals(1, me.getRangeStart());
	}

	@Test
	public void testGetSetRangeEnd()
	{
		me.setPageRows(5);
		assertEquals(5, me.getRangeEnd());
	}

	@Test
	public void testGetHasMoreRows()
	{
		me.setTotalRows(50);
		me.setOffset(20);
		me.setPageRows(20);
		assertTrue(me.getHasMoreRows());

		me.setTotalRows(40);
		me.setOffset(20);
		me.setPageRows(20);
		assertFalse(me.getHasMoreRows());
	}
}
