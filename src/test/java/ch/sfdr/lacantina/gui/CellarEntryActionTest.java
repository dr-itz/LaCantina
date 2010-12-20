package ch.sfdr.lacantina.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

		param("ce.winecellarId", "789");
		param("action", "list");
		action();

		verifyNoErrors();
        verifyForward("cellarentryList");
        assertEquals(list, module.getRequestAttribute("cellarentryList"));
	}

	@Test
	public void testForm()
		throws DAOException
	{
		final CellarEntry ce = new CellarEntry();
		jMockery.checking(new Expectations() {{
			one(cellarentryDAO).getCellarEntry(456, 123);
			will(returnValue(ce));
		}});

		param("action", "form");
		param("ce.id", "456");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();
		assertEquals(ce, form.getCe());
	}

	@Test
	public void testFormNew()
		throws DAOException
	{
		jMockery.checking(new Expectations() {{
			never(cellarentryDAO).getCellarEntry(456, 123);
		}});

		param("action", "new");
		param("ce.id", "456");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();
		assertNotNull(form.getCe());
		assertEquals(0, form.getCe().getId());
	}

	@Test
	public void testFormValidation()
	{
		param("action", "mod");
		module.populateRequestToForm();

		verifyError("ce.year.required");
		verifyError("ce.quantity.required");
	}
}
