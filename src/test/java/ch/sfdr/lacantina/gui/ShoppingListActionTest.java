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
import ch.sfdr.lacantina.dao.IShoppingListDAO;
import ch.sfdr.lacantina.dao.IWineCellarDAO;
import ch.sfdr.lacantina.dao.IWineDAO;
import ch.sfdr.lacantina.dao.PagingCookie;
import ch.sfdr.lacantina.dao.objects.CellarEntry;
import ch.sfdr.lacantina.dao.objects.ShoppingItem;
import ch.sfdr.lacantina.dao.objects.Wine;
import ch.sfdr.lacantina.dao.objects.WineCellar;

/**
 * Tests for CellarEntryAction
 * @author S.Freihofer
 * @author D.Ritz
 */
public class ShoppingListActionTest
	extends AbstractActionTest<ShoppingListForm>
{
	private IShoppingListDAO shoppingDAO;
	private IWineDAO wineDAO;
	private ICellarEntryDAO ceDAO;
	private IWineCellarDAO wcDAO;
	private List<ShoppingItem> list;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp(ShoppingListForm.class, ShoppingListAction.class);

		shoppingDAO = jMockery.mock(IShoppingListDAO.class);
		wineDAO = jMockery.mock(IWineDAO.class);
		ceDAO = jMockery.mock(ICellarEntryDAO.class);
		wcDAO = jMockery.mock(IWineCellarDAO.class);
		daoConn.setShoppingListDAO(shoppingDAO);
		daoConn.setWineDAO(wineDAO);
		daoConn.setCellarEntryDAO(ceDAO);
		daoConn.setWinecellarDAO(wcDAO);
	}

	private void setupCellarEntriesExpectations()
		throws DAOException
	{
		list = new ArrayList<ShoppingItem>();
		jMockery.checking(new Expectations() {{
			one(shoppingDAO).getShoppingList(with(123), with(any(PagingCookie.class)));
			will(returnValue(list));
		}});
	}

	private void setupAttachDataListExpectations()
		throws DAOException
	{
		final List<WineCellar> list = new ArrayList<WineCellar>();
		jMockery.checking(new Expectations() {{
			one(wcDAO).getWineCellars(123);
			will(returnValue(list));
		}});
	}

	@Test
	public void testList()
		throws DAOException
	{
		setupCellarEntriesExpectations();

		param("action", "list");
		action();

		verifyNoErrors();
        verifyForward("shoppingList");
        assertEquals(list, module.getRequestAttribute("shoppingList"));
	}

	@Test
	public void testForm()
		throws DAOException
	{
		final ShoppingItem si = new ShoppingItem();
		jMockery.checking(new Expectations() {{
			one(shoppingDAO).getShoppingItem(456);
			will(returnValue(si));
		}});

		param("action", "form");
		param("item.id", "456");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();
		assertEquals(si, form.getItem());
	}

	@Test
	public void testFormNew()
		throws DAOException
	{
		jMockery.checking(new Expectations() {{
			never(shoppingDAO).getShoppingItem(456);
		}});

		param("action", "new");
		param("wine.id", "456");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();
		assertNotNull(form.getItem());
		assertEquals(0, form.getItem().getId());
	}

	@Test
	public void testFromWineAndWineYear()
		throws DAOException
	{
		final Wine w = new Wine();
		w.setName("Campofiorin");
		w.setProducer("Masi");
		w.setBottleSize(75);

		final CellarEntry ce = new CellarEntry();
		ce.setWine(w);
		ce.setYear(2005);

		jMockery.checking(new Expectations() {{
			one(wineDAO).getWine(456);
			will(returnValue(w));

			one(ceDAO).getCellarEntry(456, 123);
			will(returnValue(ce));
		}});

		// first: fromwine
		param("action", "fromwine");
		param("refId", "456");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();

		ShoppingItem si = form.getItem();
		assertEquals("Campofiorin", si.getName());
		assertEquals("Masi", si.getProducer());
		assertEquals(75, si.getBottleSize());

		// second: fromwineyear
		param("action", "fromwineyear");
		param("refId", "456");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();

		si = form.getItem();
		assertEquals("Campofiorin", si.getName());
		assertEquals("Masi", si.getProducer());
		assertEquals(75, si.getBottleSize());
		assertEquals(2005, si.getYear().intValue());
	}

	@Test
	public void testFormValidation()
		throws DAOException
	{
		setupAttachDataListExpectations();

		param("action", "mod");
		module.populateRequestToForm();

		verifyError("shoppinglist.name.required");
		verifyError("shoppinglist.quantity.required");
	}

	@Test
	public void testStoreCellarEntry()
		throws DAOException
	{
		setupCellarEntriesExpectations();
		setupAttachDataListExpectations();

		jMockery.checking(new Expectations() {{
			one(shoppingDAO).storeShoppingItem(with(any(ShoppingItem.class)));

			one(shoppingDAO).storeShoppingItem(with(any(ShoppingItem.class)));
			will(throwException(new DAOException("mock")));
		}});

		param("action", "mod");
		param("item.id", "0");
		param("item.name", "Campofiorin");
		param("item.year", "2005");
		param("item.quantity", "10");

		action();

		verifyNoErrors();
		verifyForward("shoppingList");

		ShoppingItem ce = ((ShoppingListForm) module.getActionForm()).getItem();
		assertEquals(0, ce.getId());
		assertEquals("Campofiorin", ce.getName());
		assertEquals(2005, ce.getYear().intValue());
		assertEquals(10, ce.getQuantity());

		// again, this time an exception will be thrown
		setupCellarEntriesExpectations();
		action();
		verifyError("shoppinglist.update.failed");
		verifyForward("shoppingList");
	}

	@Test
	public void testDeleteCellarEntry()
		throws DAOException
	{
		setupCellarEntriesExpectations();
		jMockery.checking(new Expectations() {{
			one(shoppingDAO).deleteShoppingItem(789);

			one(shoppingDAO).deleteShoppingItem(789);
			will(throwException(new DAOException("mock")));
		}});

		param("action", "del");
		param("item.id", "789");

		action();

		verifyNoErrors();
		verifyForward("shoppingList");

		// again, this time an exception will be thrown
		setupCellarEntriesExpectations();
		action();
		verifyError("shoppinglist.delete.failed");
		verifyForward("shoppingList");
	}
}
