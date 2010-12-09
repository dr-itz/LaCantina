package ch.sfdr.lacantina.dao.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

import com.mockrunner.jdbc.JDBCTestModule;
import com.mockrunner.jdbc.PreparedStatementResultSetHandler;
import com.mockrunner.mock.jdbc.JDBCMockObjectFactory;
import com.mockrunner.mock.jdbc.MockResultSet;


/**
 * Base class for all JDBC test using Mockrunner. Does all the setup work in
 * order to use Mockrunner with JUnit4 annotations. Also provides some
 * useful helpers to ease writing the tests.
 * @author D.Ritz
 */
@Ignore
public abstract class AbstractJDBCTest
{
	protected JDBCMockObjectFactory factory = new JDBCMockObjectFactory();
	protected JDBCTestModule module = new JDBCTestModule(factory);

	private List<String> queries = new ArrayList<String>();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp()
		throws Exception
	{
		DBConnection.setDataSource(factory.getMockDataSource());

		/* register an empty ResultSet so that no NullPointerExceptions will be
		 * thrown in case the wrong statement is executed
		 */
		PreparedStatementResultSetHandler handler = module.getPreparedStatementResultSetHandler();
		MockResultSet result = handler.createResultSet();
		handler.prepareGlobalResultSet(result);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown()
		throws Exception
	{
		verifyQueries();
		module.verifyAllResultSetsClosed();
		module.verifyAllStatementsClosed();

		factory.restoreDrivers();
	}

	/**
	 * verifies if all previously registered statements are executed
	 */
	protected void verifyQueries()
	{
		for (String query : queries)
			module.verifySQLStatementExecuted(query);
	}

	/**
	 * prepares a query with attached results
	 * @param query the SQL query string
	 * @param name a name for the query
	 * @param cols the columns
	 * @param data the data returned
	 */
	protected void prepareQueryWithResult(String query, String name,
			String[] cols, Object[][] data)
	{
		queries.add(query);

		PreparedStatementResultSetHandler handler = module.getPreparedStatementResultSetHandler();

		// ArrayResultSetFactory and StringValueTable are just useless
		MockResultSet resultSet = new MockResultSet(name);
		if (cols != null) {
			for (int i = 0; i < cols.length; i++)
				resultSet.addColumn(cols[i]);
		}
		for (int i = 0; i < data.length; i++)
			resultSet.addRow(data[i]);

		handler.prepareResultSet(query, resultSet);
	}

	/**
	 * prepares a query with attached results
	 * @param query the SQL query string
	 * @param name a name for the query
	 * @param data the data returned
	 */
	protected void prepareQueryWithResult(String query, String name,
			Object[][] data)
	{
		prepareQueryWithResult(query, name, null, data);
	}

	/**
	 * gets the MockConnection
	 * @return
	 */
	protected Connection getConnection()
	{
		return factory.getMockConnection();
	}
}
