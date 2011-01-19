package ch.sfdr.lacantina.dao.objects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for ShoppingItem
 * @author D.Ritz
 */
public class ShoppingItemTest
{
	private ShoppingItem me;

	@Before
	public void setUp()
		throws Exception
	{
		me = new ShoppingItem();
	}

	@Test
	public void testGetSetId()
	{
		assertEquals(0, me.getId());
		me.setId(123);
		assertEquals(123, me.getId());
	}

	@Test
	public void testGetSetUserId()
	{
		assertEquals(0, me.getUserId());
		me.setUserId(123);
		assertEquals(123, me.getUserId());
	}

	@Test
	public void testGetSetWine()
	{
		assertNull(me.getWineId());
		me.setWineId(123);
		assertEquals(123, me.getWineId().intValue());
	}

	@Test
	public void testGetSetName()
	{
		assertNull(me.getName());
		me.setName("name");
		assertEquals("name", me.getName());
	}

	@Test
	public void testGetSetProducer()
	{
		assertNull(me.getProducer());
		me.setProducer("producer");
		assertEquals("producer", me.getProducer());
	}

	@Test
	public void testGetSetYear()
	{
		assertNull(me.getYear());
		me.setYear(2005);
		assertEquals(2005, me.getYear().intValue());
	}

	@Test
	public void testGetSetStore()
	{
		assertNull(me.getStore());
		me.setStore("immer besser, immer billiger");
		assertEquals("immer besser, immer billiger", me.getStore());
	}

	@Test
	public void testGetSetQuantity()
	{
		assertEquals(0, me.getQuantity());
		me.setQuantity(6);
		assertEquals(6, me.getQuantity());
	}

	@Test
	public void testGetSetBottleSize()
	{
		assertEquals(0, me.getBottleSize());
		me.setBottleSize(75);
		assertEquals(75, me.getBottleSize());
	}
}
