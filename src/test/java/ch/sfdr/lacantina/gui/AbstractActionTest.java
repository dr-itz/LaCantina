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
	protected Class<?> actionClazz;

    @SuppressWarnings("unchecked")
	public void setUp(Class<T> clazz, Class<?> actionClazz)
    {
    	this.actionClazz = actionClazz;

    	DummyDAOConnectionFactory daoFact = new DummyDAOConnectionFactory();
    	DAOConnectionFactory.setFactory(daoFact);
    	daoConn = daoFact.getConn();

    	jMockery = new JUnit4Mockery();
    	actionMockFactory = new ActionMockObjectFactory();
        module = new ActionTestModule(actionMockFactory);
        form = (T) module.createActionForm(clazz);
        module.setValidate(true);
    }

    /**
     * sets a parameter on the request
     * @param name name
     * @param val value
     */
    public void param(String name, String val)
    {
    	module.addRequestParameter(name, val);
    }

    /**
     * lets the action perform
     */
    public void action()
    {
    	module.actionPerform(actionClazz, form);
    }

    /**
     * sets the forward to inputForward
     */
    public void setInputForward()
    {
    	module.setInput("inputForward");
    }

    /**
     * verifies that the input forward was returned
     */
    public void verifyInputForward()
    {
    	module.verifyForwardName("inputForward");
    }

    /**
     * verifies a specific forward
     * @param name name
     */
    public void verifyForward(String name)
    {
    	module.verifyForward(name);
    }

    /**
     * verifies that no errors where returned
     */
    public void verifyNoErrors()
    {
    	module.verifyNoActionErrors();
    }

    /**
     * verifies if an error is present
     * @param name name
     */
    public void verifyError(String name)
    {
    	module.verifyActionErrorPresent(name);
    }
}
