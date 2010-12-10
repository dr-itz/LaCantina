package ch.sfdr.lacantina.gui.admin;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.sfdr.common.security.Password;
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
		super.setUp(UserForm.class, UserAction.class);

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

		param("action", "list");
		action();

		verifyNoErrors();
        verifyForward("userList");
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

		param("action", "form");
		param("user.id", "123");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();
		assertEquals(user, form.getUser());
	}

	@Test
	public void testFormNew()
		throws DAOException
	{
		jMockery.checking(new Expectations() {{
			never(userDAO).getUser(123);
		}});

		param("action", "new");
		param("user.id", "123");
		setInputForward();
		action();

		verifyNoErrors();
		verifyInputForward();
		assertNotNull(form.getUser());
		assertEquals(0, form.getUser().getId());
	}

	@Test
	public void testFormValidation()
	{
		param("action", "mod");
		module.populateRequestToForm();

		verifyError("user.login.required");
		verifyError("user.lastname.required");
		verifyError("user.firstname.required");
		verifyError("user.pw.new.required");


		param("pw1", "test2");
		param("pw2", "test1");
		module.populateRequestToForm();

		verifyError("user.pw.mustmatch");

		param("user.id", "123");
		param("pw1", "");
		module.populateRequestToForm();

		UserForm uf = (UserForm) module.getActionForm();
		assertNull(uf.getPw1());
		assertNull(uf.getPw2());
	}


	private void setupUserForm()
	{
		param("action", "mod");
		param("user.id", "0");
		param("user.firstName", "first");
		param("user.lastName", "last");
		param("user.login", "login");
		param("user.email", "email");
		param("pw1", "pass");
		param("pw2", "pass");
	}

	@Test
	public void testCreateUser()
		throws DAOException
	{
		final List<User> list = new ArrayList<User>();
		jMockery.checking(new Expectations() {{
			one(userDAO).storeUser(with(any(User.class)));

			one(userDAO).getUsers();
			will(returnValue(list));
		}});

		setupUserForm();

		action();

		verifyNoErrors();
		verifyForward("userList");

		User u = ((UserForm) module.getActionForm()).getUser();
		assertEquals("first", u.getFirstName());
		assertEquals("last", u.getLastName());
		assertEquals("login", u.getLogin());
		assertEquals("email", u.getEmail());
		assertEquals(Password.getPasswordHash("pass"), u.getPasswordHash());
	}

	@Test
	public void testCreateUserFailStore()
		throws DAOException
	{
		final List<User> list = new ArrayList<User>();
		jMockery.checking(new Expectations() {{
			one(userDAO).storeUser(with(any(User.class)));
			will(throwException(new DAOException("mock")));

			one(userDAO).getUsers();
			will(returnValue(list));
		}});

		setupUserForm();

		action();

		verifyError("user.update.failed");
		verifyForward("userList");
	}

	@Test
	public void testDeleteUser()
		throws DAOException
	{
		final List<User> list = new ArrayList<User>();
		jMockery.checking(new Expectations() {{
			one(userDAO).deleteUser(123);

			one(userDAO).getUsers();
			will(returnValue(list));
		}});

		param("action", "del");
		param("user.id", "123");

		action();

		verifyNoErrors();
		verifyForward("userList");
	}

	@Test
	public void testDeleteUserFail()
		throws DAOException
	{
		final List<User> list = new ArrayList<User>();
		jMockery.checking(new Expectations() {{
			one(userDAO).deleteUser(123);
			will(throwException(new DAOException("mock")));

			one(userDAO).getUsers();
			will(returnValue(list));
		}});

		param("action", "del");
		param("user.id", "123");

		action();

		verifyError("user.delete.failed");
		verifyForward("userList");
	}
}
