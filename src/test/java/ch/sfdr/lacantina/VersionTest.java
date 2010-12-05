package ch.sfdr.lacantina;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test for generated Version
 * @author D.Ritz
 */
public class VersionTest
{
	@Test
	public final void testGetVersion()
	{
		assertTrue(Version.getVersion().contains("LaCantina"));
		assertFalse(Version.getVersion().contains("webapp.version"));
	}
}
