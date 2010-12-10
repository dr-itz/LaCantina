package ch.sfdr.lacantina.dao.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.objects.User;

/**
 * Tests for DbUserDAO
 * @author D. Ritz
 */
public class DbUserDAOTest
	extends AbstractJDBCTest
{
	private DbUserDAO me;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		me = new DbUserDAO(getConnection());
	}

	@Test
	public void testGetUser()
		throws DAOException
	{
		prepareQueryWithResult(
			"SELECT id, login, first_name, last_name, email, is_admin " +
			"FROM users " +
			"WHERE id = ?",
			"UserQuery",
			new Object[][] {
				{ 123, "admin", "first", "last", "some@one.com", true },
			},
			123
		);

		User u = me.getUser(123);
		assertEquals(123, u.getId());
		assertEquals("admin", u.getLogin());
		assertEquals("first", u.getFirstName());
		assertEquals("last", u.getLastName());
		assertEquals("some@one.com", u.getEmail());
		assertTrue(u.isAdmin());
	}

	@Test
	public void testGetUserNonExist()
		throws DAOException
	{
		User u = me.getUser(1234);
		assertNull(u);
	}

	@Test
	public void testGetUserList()
		throws DAOException
	{
		prepareQueryWithResult(
			"SELECT id, login, first_name, last_name, email, is_admin " +
			"FROM users " +
			"ORDER BY last_name, first_name",
			"UserQuery",
			new Object[][] {
				{ 123, "admin", "first1", "last1", "some1@one.com", true },
				{ 124, "user", "first2", "last2", "some2@one.com", false },
			}
		);

		List<User> list = me.getUsers();
		assertEquals(2, list.size());
		User u = list.get(0);
		assertEquals(123, u.getId());
		assertEquals("admin", u.getLogin());
		assertEquals("first1", u.getFirstName());
		assertEquals("last1", u.getLastName());
		assertEquals("some1@one.com", u.getEmail());
		assertTrue(u.isAdmin());
	}

	@Test
	public void testStoreUserInsert()
		throws DAOException
	{
		User u = new User();
		u.setId(0);
		u.setFirstName("first");
		u.setLastName("last");
		u.setLogin("login");
		u.setEmail("email");
		u.setPasswordHash("passwordHash");

		prepareUpdate(
			"INSERT INTO users" +
			"  (login, first_name, last_name, email, is_admin, password_hash) " +
			"VALUES (?, ?, ?, ?, ?, ?)",
			"UserInsert",
			"login", "first", "last", "email", false, "passwordHash");

		me.storeUser(u);
	}

	@Test
	public void testStoreUserUpdate()
		throws DAOException
	{
		User u = new User();
		u.setId(123);
		u.setFirstName("first");
		u.setLastName("last");
		u.setLogin("login");
		u.setEmail("email");
		u.setPasswordHash("passwordHash");

		prepareUpdate(
			"UPDATE users SET" +
			"  login = ?, first_name = ?, last_name = ?, email = ?," +
			"  is_admin = ? " +
			"WHERE id = ?",
			"UserUpdate",
			"login", "first", "last", "email", false, 123);
		prepareUpdate(
			"UPDATE users SET password_hash = ? " +
			"WHERE login = ?",
			"UserUpdatePW",
			"passwordHash", "login");

		me.storeUser(u);
	}

	@Test
	public void testDeleteUser()
		throws DAOException
	{
		prepareUpdate(
			"DELETE FROM users WHERE id = ?",
			"UserDelete",
			123);
		me.deleteUser(123);
	}
}
