package ch.sfdr.lacantina.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.IWineDAO;
import ch.sfdr.lacantina.dao.objects.Wine;

/**
 * Tests for WineAction
 * @author D. Ritz
 */
@RunWith(JMock.class)
public class WineActionTest
	extends AbstractActionTest<WineForm>
{
	private IWineDAO wineDAO;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp(WineForm.class, WineAction.class);

		wineDAO = jMockery.mock(IWineDAO.class);
		daoConn.setWineDAO(wineDAO);
	}

	@Test
	public void testList()
		throws DAOException
	{
		final List<Wine> list = new ArrayList<Wine>();
		jMockery.checking(new Expectations() {{
			one(wineDAO).getWines(123);
			will(returnValue(list));
		}});

		param("action", "list");
		action();

		verifyNoErrors();
        verifyForward("wineList");
        assertEquals(list, module.getRequestAttribute("wineList"));
	}

	@Test
	public void testForm()
		throws DAOException
	{
		final Wine wine = new Wine();
		jMockery.checking(new Expectations() {{
			one(wineDAO).getWine(123);
			will(returnValue(wine));
		}});

		param("action", "form");
		param("wine.id", "123");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();
		assertEquals(wine, form.getWine());
	}

	@Test
	public void testFormNew()
		throws DAOException
	{
		jMockery.checking(new Expectations() {{
			never(wineDAO).getWine(123);
		}});

		param("action", "new");
		param("wine.id", "123");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();
		assertNotNull(form.getWine());
		assertEquals(0, form.getWine().getId());
	}

	@Test
	public void testFormValidation()
	{
		param("action", "mod");
		module.populateRequestToForm();

		verifyError("wine.name.required");
		verifyError("wine.producer.required");
		verifyError("wine.country.required");
		verifyError("wine.region.required");
	}


	private void setupWineForm()
	{
		param("action", "mod");
		param("wine.id", "0");
		param("wine.name", "name");
		param("wine.producer", "producer");
		param("wine.country", "country");
		param("wine.region", "region");
		param("wine.description", "description");
		param("wine.bottleSize", "75");
	}

	@Test
	public void testCreateWine()
		throws DAOException
	{
		final List<Wine> list = new ArrayList<Wine>();
		jMockery.checking(new Expectations() {{
			one(wineDAO).storeWine(with(any(Wine.class)));

			one(wineDAO).getWines(123);
			will(returnValue(list));
		}});

		setupWineForm();

		action();

		verifyNoErrors();
		verifyForward("wineList");

		Wine w = ((WineForm) module.getActionForm()).getWine();
		assertEquals(123, w.getUserId());
		assertEquals("name", w.getName());
		assertEquals("producer", w.getProducer());
		assertEquals("country", w.getCountry());
		assertEquals("region", w.getRegion());
		assertEquals("description", w.getDescription());
		assertEquals(75, w.getBottleSize());
	}

	@Test
	public void testCreateWineFailStore()
		throws DAOException
	{
		final List<Wine> list = new ArrayList<Wine>();
		jMockery.checking(new Expectations() {{
			one(wineDAO).storeWine(with(any(Wine.class)));
			will(throwException(new DAOException("mock")));

			one(wineDAO).getWines(123);
			will(returnValue(list));
		}});

		setupWineForm();

		action();

		verifyError("wine.update.failed");
		verifyForward("wineList");
	}

	@Test
	public void testDeleteWine()
		throws DAOException
	{
		final List<Wine> list = new ArrayList<Wine>();
		jMockery.checking(new Expectations() {{
			one(wineDAO).deleteWine(123);

			one(wineDAO).getWines(123);
			will(returnValue(list));
		}});

		param("action", "del");
		param("wine.id", "123");

		action();

		verifyNoErrors();
		verifyForward("wineList");
	}

	@Test
	public void testDeleteWineFail()
		throws DAOException
	{
		final List<Wine> list = new ArrayList<Wine>();
		jMockery.checking(new Expectations() {{
			one(wineDAO).deleteWine(123);
			will(throwException(new DAOException("mock")));

			one(wineDAO).getWines(123);
			will(returnValue(list));
		}});

		param("action", "del");
		param("wine.id", "123");

		action();

		verifyError("wine.delete.failed");
		verifyForward("wineList");
	}
}
