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
import ch.sfdr.lacantina.dao.IWineCellarDAO;
import ch.sfdr.lacantina.dao.objects.WineCellar;

/**
 * Test for WineCellarAction
 * @author S.Freihofer
 */
@RunWith(JMock.class)
public class WineCellarActionTest
	extends AbstractActionTest<WineCellarForm>
{
	private IWineCellarDAO winecellarDAO;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp(WineCellarForm.class, WineCellarAction.class);

		winecellarDAO = jMockery.mock(IWineCellarDAO.class);
		daoConn.setWinecellarDAO(winecellarDAO);
	}

	@Test
	public void testList()
		throws DAOException
	{
		final List<WineCellar> list = new ArrayList<WineCellar>();
		jMockery.checking(new Expectations()
		{
			{
				one(winecellarDAO).getWineCellars(123);
				will(returnValue(list));
			}
		});

		param("action", "list");
		action();

		verifyNoErrors();
		verifyForward("winecellarList");
		assertEquals(list, module.getRequestAttribute("winecellarList"));
	}

	@Test
	public void testForm()
		throws DAOException
	{
		final WineCellar wc = new WineCellar();
		jMockery.checking(new Expectations()
		{
			{
				one(winecellarDAO).getWineCellar(123);
				will(returnValue(wc));
			}
		});

		param("action", "form");
		param("wc.id", "123");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();
		assertEquals(wc, form.getWc());
	}

	@Test
	public void testFormNew()
		throws DAOException
	{
		jMockery.checking(new Expectations()
		{
			{
				never(winecellarDAO).getWineCellar(123);
			}
		});

		param("action", "new");
		param("wc.id", "123");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();
		assertNotNull(form.getWc());
		assertEquals(0, form.getWc().getId());
	}

	@Test
	public void testFormValidation()
	{
		param("action", "mod");
		module.populateRequestToForm();

		verifyError("winecellar.name.required");
	}

	private void setupWineCellarForm()
	{
		param("action", "mod");
		param("wc.id", "0");
		param("wc.name", "name");
		param("wc.capacity", "50");
	}

	@Test
	public void testCreateWineCellar()
		throws DAOException
	{
		final List<WineCellar> list = new ArrayList<WineCellar>();
		jMockery.checking(new Expectations()
		{
			{
				one(winecellarDAO).storeWineCellar(with(any(WineCellar.class)));

				one(winecellarDAO).getWineCellars(123);
				will(returnValue(list));
			}
		});

		setupWineCellarForm();

		action();

		verifyNoErrors();
		verifyForward("winecellarList");

		WineCellar wc = ((WineCellarForm) module.getActionForm()).getWc();
		assertEquals(123, wc.getUserId());
		assertEquals("name", wc.getName());
		assertEquals(50, wc.getCapacity());
	}

	@Test
	public void testCreateWineCellarFailStore()
		throws DAOException
	{
		final List<WineCellar> list = new ArrayList<WineCellar>();
		jMockery.checking(new Expectations()
		{
			{
				one(winecellarDAO).storeWineCellar(with(any(WineCellar.class)));
				will(throwException(new DAOException("mock")));

				one(winecellarDAO).getWineCellars(123);
				will(returnValue(list));
			}
		});

		setupWineCellarForm();

		action();

		verifyError("winecellar.update.failed");
		verifyForward("winecellarList");
	}

	@Test
	public void testDeleteWineCellar()
		throws DAOException
	{
		final List<WineCellar> list = new ArrayList<WineCellar>();
		jMockery.checking(new Expectations()
		{
			{
				one(winecellarDAO).deleteWineCellar(123);

				one(winecellarDAO).getWineCellars(123);
				will(returnValue(list));
			}
		});

		param("action", "del");
		param("wc.id", "123");

		action();

		verifyNoErrors();
		verifyForward("winecellarList");
	}

	@Test
	public void testDeleteWineCellarFail()
		throws DAOException
	{
		final List<WineCellar> list = new ArrayList<WineCellar>();
		jMockery.checking(new Expectations()
		{
			{
				one(winecellarDAO).deleteWineCellar(123);
				will(throwException(new DAOException("mock")));

				one(winecellarDAO).getWineCellars(123);
				will(returnValue(list));
			}
		});

		param("action", "del");
		param("wc.id", "123");

		action();

		verifyError("winecellar.delete.failed");
		verifyForward("winecellarList");
	}
}
