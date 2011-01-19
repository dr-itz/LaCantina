package ch.sfdr.lacantina.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
		wcDAO = jMockery.mock(IWineCellarDAO.class);
		ceDAO = jMockery.mock(ICellarEntryDAO.class);
		daoConn.setShoppingListDAO(shoppingDAO);
		daoConn.setWineDAO(wineDAO);
		daoConn.setCellarEntryDAO(ceDAO);
		daoConn.setWinecellarDAO(wcDAO);
	}

	private void setupShoppingListExpectations()
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
		setupShoppingListExpectations();

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
		param("item.wineId", "0");

		module.populateRequestToForm();

		verifyError("shoppinglist.name.required");
		verifyError("shoppinglist.quantity.required");

		assertNull(form.getItem().getWineId());
	}

	@Test
	public void testCheckinFormValidation()
		throws DAOException
	{
		setupAttachDataListExpectations();

		param("action", "checkin");
		module.populateRequestToForm();

		verifyError("wine.name.required");
		verifyError("wine.producer.required");
		verifyError("wine.country.required");
		verifyError("wine.region.required");
	}

	@Test
	public void testStoreCellarEntry()
		throws DAOException
	{
		setupShoppingListExpectations();
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
		setupShoppingListExpectations();
		action();
		verifyError("shoppinglist.update.failed");
		verifyForward("shoppingList");
	}

	@Test
	public void testStoreCellarEntryExisting()
		throws DAOException
	{
		setupShoppingListExpectations();
		setupAttachDataListExpectations();

		final Wine w = new Wine();
		w.setId(450);
		w.setName("Campofiorin");
		w.setProducer("Masi");
		w.setBottleSize(75);

		jMockery.checking(new Expectations() {{
			one(shoppingDAO).storeShoppingItem(with(any(ShoppingItem.class)));

			one(wineDAO).getWine(450);
			will(returnValue(w));
		}});

		param("action", "mod");
		param("item.id", "0");
		param("item.wineId", "450");
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
	}

	@Test
	public void testDeleteShoppingItem()
		throws DAOException
	{
		setupShoppingListExpectations();
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
		setupShoppingListExpectations();
		action();
		verifyError("shoppinglist.delete.failed");
		verifyForward("shoppingList");
	}

	@Test
	public void testCheckinForm()
		throws DAOException
	{
		setupAttachDataListExpectations();

		final ShoppingItem si = new ShoppingItem();
		si.setName("testname");
		si.setProducer("testproducer");
		si.setBottleSize(100);

		jMockery.checking(new Expectations() {{
			one(shoppingDAO).getShoppingItem(456);
			will(returnValue(si));
		}});

		param("action", "checkinform");
		param("item.id", "456");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();

		assertEquals(si, form.getItem());
		Wine w = form.getWine();
		assertEquals("testname", w.getName());
		assertEquals("testproducer", w.getProducer());
		assertEquals(100, w.getBottleSize());
	}

	@Test
	public void testCheckinFormExistingWine()
		throws DAOException
	{
		setupAttachDataListExpectations();

		final ShoppingItem si = new ShoppingItem();
		si.setWineId(789);
		final Wine w = new Wine();

		jMockery.checking(new Expectations() {{
			one(shoppingDAO).getShoppingItem(456);
			will(returnValue(si));
			one(wineDAO).getWine(789);
			will(returnValue(w));
		}});

		param("action", "checkinform");
		param("item.id", "456");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();

		assertEquals(si, form.getItem());
		assertEquals(w, form.getWine());
	}

	@Test
	public void testCheckinExistingWine()
		throws DAOException
	{
		setupShoppingListExpectations();
		setupAttachDataListExpectations();

		final ShoppingItem si = new ShoppingItem();
		si.setWineId(789);
		si.setYear(2005);

		final Wine w = new Wine();
		w.setId(789);
		w.setName("Campofiorin");
		w.setProducer("Masi");
		w.setBottleSize(75);

		final CellarEntry ce = new CellarEntry();
		ce.setWinecellarId(3);
		ce.setWine(w);
		ce.setYear(2005);
		ce.setQuantity(10);

		jMockery.checking(new Expectations() {{
			one(shoppingDAO).getShoppingItem(456);
			will(returnValue(si));

			one(ceDAO).getCellarEntry(3, 789, 2005);
			will(returnValue(ce));

			one(ceDAO).storeCellarEntry(ce);

			one(shoppingDAO).deleteShoppingItem(456);
		}});

		param("action", "checkin");
		param("item.id", "456");
		param("item.year", "2005");
		param("item.wineId", "789");
		param("item.quantity", "5");
		param("wineCellarId", "3");

		action();

		verifyNoErrors();
        verifyForward("shoppingList");
        assertEquals(list, module.getRequestAttribute("shoppingList"));
        assertEquals(15, ce.getQuantity());
	}

	@Test
	public void testCheckinExistingWineNoEntry()
		throws DAOException
	{
		setupShoppingListExpectations();
		setupAttachDataListExpectations();

		final ShoppingItem si = new ShoppingItem();
		si.setWineId(789);
		si.setYear(2005);

		final Wine w = new Wine();
		w.setId(789);
		w.setName("Campofiorin");
		w.setProducer("Masi");
		w.setBottleSize(75);

		jMockery.checking(new Expectations() {{
			one(shoppingDAO).getShoppingItem(456);
			will(returnValue(si));

			one(ceDAO).getCellarEntry(3, 789, 2005);
			will(returnValue(null));

			one(ceDAO).storeCellarEntry(with(any(CellarEntry.class)));

			one(shoppingDAO).deleteShoppingItem(456);
		}});

		param("action", "checkin");
		param("item.id", "456");
		param("item.year", "2005");
		param("item.wineId", "789");
		param("item.quantity", "5");
		param("wineCellarId", "3");

		action();

		verifyNoErrors();
        verifyForward("shoppingList");
        assertEquals(list, module.getRequestAttribute("shoppingList"));
	}

	@Test
	public void testCheckinExistingNewWine()
		throws DAOException
	{
		setupShoppingListExpectations();
		setupAttachDataListExpectations();

		final ShoppingItem si = new ShoppingItem();
		si.setWineId(789);
		si.setYear(2005);

		final Wine w = new Wine();
		w.setId(789);
		w.setName("Campofiorin");
		w.setProducer("Masi");
		w.setBottleSize(75);

		jMockery.checking(new Expectations() {{
			one(shoppingDAO).getShoppingItem(456);
			will(returnValue(si));

			one(wineDAO).storeWine(with(any(Wine.class)));

			one(ceDAO).storeCellarEntry(with(any(CellarEntry.class)));

			one(shoppingDAO).deleteShoppingItem(456);
		}});

		param("action", "checkin");
		param("item.id", "456");
		param("item.year", "2005");
		param("item.quantity", "5");
		param("wineCellarId", "3");
		param("wine.name", "name");
		param("wine.producer", "producer");
		param("wine.country", "country");
		param("wine.region", "region");
		param("wine.description", "description");
		param("wine.bottleSize", "75");

		action();

		verifyNoErrors();
        verifyForward("shoppingList");
        assertEquals(list, module.getRequestAttribute("shoppingList"));
	}
}
