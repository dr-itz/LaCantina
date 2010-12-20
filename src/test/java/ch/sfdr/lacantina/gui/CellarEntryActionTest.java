package ch.sfdr.lacantina.gui;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.ICellarEntryDAO;
import ch.sfdr.lacantina.dao.PagingCookie;
import ch.sfdr.lacantina.dao.objects.CellarEntry;

/**
 * Tests for CellarEntryAction
 * @author S.Freihofer
 */
public class CellarEntryActionTest
	extends AbstractActionTest<CellarEntryForm>
{
	private ICellarEntryDAO cellarentryDAO;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp(CellarEntryForm.class, CellarEntryAction.class);

		cellarentryDAO = jMockery.mock(ICellarEntryDAO.class);
		daoConn.setCellarEntryDAO(cellarentryDAO);
	}

	@Test
	public void testList()
		throws DAOException
	{
		final List<CellarEntry> list = new ArrayList<CellarEntry>();
		jMockery.checking(new Expectations() {{
			one(cellarentryDAO).getCellarEntries(with(789), with(123),
				with(any(PagingCookie.class)));
			will(returnValue(list));
		}});

		param("winecellarId", "789");
		param("action", "list");
		action();

		verifyNoErrors();
        verifyForward("cellarentryList");
        assertEquals(list, module.getRequestAttribute("cellarentryList"));
	}
}
