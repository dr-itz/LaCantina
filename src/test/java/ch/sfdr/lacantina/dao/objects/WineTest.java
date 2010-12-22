package ch.sfdr.lacantina.dao.objects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Wine
 * @author D.Ritz
 */
public class WineTest
{
	private Wine me;

	@Before
	public void setUp()
		throws Exception
	{
		me = new Wine();
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
	public void testGetSetCountry()
	{
		assertNull(me.getCountry());
		me.setCountry("country");
		assertEquals("country", me.getCountry());
	}

	@Test
	public void testGetSetRegion()
	{
		assertNull(me.getRegion());
		me.setRegion("region");
		assertEquals("region", me.getRegion());
	}

	@Test
	public void testGetSetDescription()
	{
		assertNull(me.getDescription());
		me.setDescription("description");
		assertEquals("description", me.getDescription());
	}

	@Test
	public void testGetSetBottleSize()
	{
		assertEquals(0, me.getBottleSize());
		me.setBottleSize(75);
		assertEquals(75, me.getBottleSize());
	}

	@Test
	public void testGetFriendlyName()
	{
		me.setName("Campofiorin");
		me.setProducer("Masi");
		me.setCountry("Italy");
		me.setRegion("Veneto");
		me.setBottleSize(75);
		assertEquals("Italy/Veneto: Masi: Campofiorin, 75cl", me.getFriendlyName());
	}
}
