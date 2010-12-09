package ch.sfdr.lacantina.gui.admin;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.IUserDAO;
import ch.sfdr.lacantina.dao.objects.User;
import ch.sfdr.lacantina.gui.AbstractActionTest;

/**
 * Tests for UserAction
 * @author D. Ritz
 */
@RunWith(JMock.class)
public class UserActionTest
	extends AbstractActionTest<UserForm>
{
	private IUserDAO userDAO;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp(UserForm.class);

		userDAO = jMockery.mock(IUserDAO.class);
		daoConn.setUserDAO(userDAO);
	}

	@Test
	public void testList()
		throws DAOException
	{
		final List<User> list = new ArrayList<User>();
		jMockery.checking(new Expectations() {{
			one(userDAO).getUsers();
			will(returnValue(list));
		}});

		module.addRequestParameter("action", "list");
		module.actionPerform(UserAction.class, form);

		module.verifyNoActionErrors();
        module.verifyForward("userList");
        assertEquals(list, module.getRequestAttribute("userList"));
	}

	@Test
	public void testForm()
		throws DAOException
	{
		final User user = new User();
		jMockery.checking(new Expectations() {{
			one(userDAO).getUser(123);
			will(returnValue(user));
		}});

		module.addRequestParameter("action", "form");
		module.addRequestParameter("id", "123");
		module.setInput("inputForward");
		module.actionPerform(UserAction.class, form);

		module.verifyNoActionErrors();
		module.verifyForwardName("inputForward");
		assertEquals(user, form.getUser());
	}

	@Test
	public void testFormNew()
		throws DAOException
	{
		jMockery.checking(new Expectations() {{
			never(userDAO).getUser(123);
		}});

		module.addRequestParameter("action", "new");
		module.addRequestParameter("id", "123");
		module.setInput("inputForward");
		module.actionPerform(UserAction.class, form);

		module.verifyNoActionErrors();
		module.verifyForwardName("inputForward");
		assertNotNull(form.getUser());
		assertEquals(0, form.getId());
	}
}
