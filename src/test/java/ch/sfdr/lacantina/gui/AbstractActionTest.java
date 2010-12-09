package ch.sfdr.lacantina.gui;

import org.apache.struts.action.ActionForm;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Ignore;

import ch.sfdr.lacantina.dao.DAOConnectionFactory;
import ch.sfdr.lacantina.dao.DummyDAOConnectionFactory;
import ch.sfdr.lacantina.dao.DummyDAOConnectionFactory.DummyDAOConnection;

import com.mockrunner.mock.web.ActionMockObjectFactory;
import com.mockrunner.struts.ActionTestModule;

/**
 * Abstract base class for Struts Action tests
 * @author D. Ritz
 */
@Ignore
public class AbstractActionTest<T extends ActionForm>
{
	protected DummyDAOConnection daoConn;
	protected Mockery jMockery;
	protected ActionMockObjectFactory actionMockFactory;
	protected ActionTestModule module;
	protected T form;

    public void setUp(Class<T> clazz)
    {
    	DummyDAOConnectionFactory daoFact = new DummyDAOConnectionFactory();
    	DAOConnectionFactory.setFactory(daoFact);
    	daoConn = daoFact.getConn();

    	jMockery = new JUnit4Mockery();
    	actionMockFactory = new ActionMockObjectFactory();
        module = new ActionTestModule(actionMockFactory);
        form = (T) module.createActionForm(clazz);
        module.setValidate(true);
    }
}
