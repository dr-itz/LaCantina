package ch.sfdr.lacantina.dao.objects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for CellarEntry
 * @author S.Freihofer
 */
public class CellarEntryTest
{
	private CellarEntry me;

	@Before
	public void setUp()
		throws Exception
	{
		me = new CellarEntry();
	}

	@Test
	public void testGetSetId()
	{
		assertEquals(0, me.getId());
		me.setId(123);
		assertEquals(123, me.getId());
	}

	@Test
	public void testGetSetWinecellarId()
	{
		assertEquals(0, me.getWinecellarId());
		me.setWinecellarId(123);
		assertEquals(123, me.getWinecellarId());
	}

	@Test
	public void testGetSetYear()
	{
		assertEquals(0, me.getYear());
		me.setYear(2005);
		assertEquals(2005, me.getYear());
	}

	@Test
	public void testGetSetQuantity()
	{
		assertEquals(0, me.getQuantity());
		me.setQuantity(6);
		assertEquals(6, me.getQuantity());
	}

	@Test
	public void testGetSetWine()
	{
		Wine w = new Wine();
		me.setWine(w);
		assertEquals(w, me.getWine());
	}

	@Test
	public void testGetSetRatingPoints()
	{
		assertEquals(0, me.getRatingPoints());
		me.setRatingPoints(10);
		assertEquals(10, me.getRatingPoints());
	}

	@Test
	public void testGetSetRatingText()
	{
		assertNull(me.getRatingText());
		me.setRatingText("text");
		assertEquals("text", me.getRatingText());
	}
}
