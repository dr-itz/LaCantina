package ch.sfdr.lacantina.dao.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for WineCellar
 * @author S.Freihofer
 */
public class WineCellarTest
{
	private WineCellar me;

	@Before
	public void setUp()
		throws Exception
	{
		me = new WineCellar();
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
	public void testGetSetCapacity()
	{
		assertEquals(0, me.getCapacity());
		me.setCapacity(50);
		assertEquals(50, me.getCapacity());
	}
}
