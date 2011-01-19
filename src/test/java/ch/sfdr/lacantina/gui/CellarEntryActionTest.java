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
import ch.sfdr.lacantina.dao.IWineDAO;
import ch.sfdr.lacantina.dao.PagingCookie;
import ch.sfdr.lacantina.dao.objects.CellarEntry;
import ch.sfdr.lacantina.dao.objects.Wine;

/**
 * Tests for CellarEntryAction
 * @author S.Freihofer
 */
public class CellarEntryActionTest
	extends AbstractActionTest<CellarEntryForm>
{
	private ICellarEntryDAO cellarentryDAO;
	private IWineDAO wineDAO;
	private List<CellarEntry> list;
	private List<CellarEntry> ratingList;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp(CellarEntryForm.class, CellarEntryAction.class);

		cellarentryDAO = jMockery.mock(ICellarEntryDAO.class);
		wineDAO = jMockery.mock(IWineDAO.class);
		daoConn.setCellarEntryDAO(cellarentryDAO);
		daoConn.setWineDAO(wineDAO);
	}

	private void setupCellarEntriesExpectations()
		throws DAOException
	{
		list = new ArrayList<CellarEntry>();
		jMockery.checking(new Expectations() {{
			one(cellarentryDAO).getCellarEntries(with(789), with(123),
				with(any(PagingCookie.class)));
			will(returnValue(list));
		}});
		param("ce.winecellarId", "789");
	}

	@Test
	public void testList()
		throws DAOException
	{
		setupCellarEntriesExpectations();

		param("action", "list");
		action();

		verifyNoErrors();
        verifyForward("cellarentryList");
        assertEquals(list, module.getRequestAttribute("cellarentryList"));
	}

	private void setupAttachDataListExpectations()
		throws DAOException
	{
		final List<Wine> wineList = new ArrayList<Wine>();
		jMockery.checking(new Expectations() {{
			one(wineDAO).getWineList(123, null);
			will(returnValue(wineList));
		}});
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
		setupAttachDataListExpectations();

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
		setupAttachDataListExpectations();

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
		throws DAOException
	{
		setupAttachDataListExpectations();

		param("action", "mod");
		module.populateRequestToForm();

		verifyError("ce.year.required");
		verifyError("ce.quantity.required");
	}

	@Test
	public void testStoreCellarEntry()
		throws DAOException
	{
		setupCellarEntriesExpectations();
		jMockery.checking(new Expectations() {{
			one(cellarentryDAO).storeCellarEntry(with(any(CellarEntry.class)));

			one(cellarentryDAO).storeCellarEntry(with(any(CellarEntry.class)));
			will(throwException(new DAOException("mock")));
		}});

		param("action", "mod");
		param("ce.id", "0");
		param("ce.wine.id", "1");
		param("ce.year", "2010");
		param("ce.quantity", "10");

		action();

		verifyNoErrors();
		verifyForward("cellarentryList");

		CellarEntry ce = ((CellarEntryForm) module.getActionForm()).getCe();
		assertEquals(0, ce.getId());
		assertEquals(1, ce.getWine().getId());
		assertEquals(2010, ce.getYear());
		assertEquals(10, ce.getQuantity());

		// again, this time an exception will be thrown
		setupCellarEntriesExpectations();
		action();
		verifyError("ce.update.failed");
		verifyForward("cellarentryList");
	}

	@Test
	public void testDeleteCellarEntry()
		throws DAOException
	{
		setupCellarEntriesExpectations();
		jMockery.checking(new Expectations() {{
			one(cellarentryDAO).deleteCellarEntry(789);

			one(cellarentryDAO).deleteCellarEntry(789);
			will(throwException(new DAOException("mock")));
		}});

		param("action", "del");
		param("ce.id", "789");

		action();

		verifyNoErrors();
		verifyForward("cellarentryList");

		// again, this time an exception will be thrown
		setupCellarEntriesExpectations();
		action();
		verifyError("ce.delete.failed");
		verifyForward("cellarentryList");
	}

	private void setupWineRatingsExpectations()
		throws DAOException
	{
		ratingList = new ArrayList<CellarEntry>();
		jMockery.checking(new Expectations() {{
			one(cellarentryDAO).getWineRatings(with(1),
				with(any(PagingCookie.class)));
			will(returnValue(ratingList));
		}});
		param("ce.ratingPoints", "1");
	}

	@Test
	public void testRatingList()
		throws DAOException
	{
		setupWineRatingsExpectations();

		param("listType", "rating");
		action();

		verifyNoErrors();
	    verifyForward("wineratingList");
	    assertEquals(ratingList, module.getRequestAttribute("wineratingList"));
	}
}
