package ch.sfdr.lacantina.gui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.sfdr.common.BaseAction;
import ch.sfdr.common.security.SecManager;
import ch.sfdr.lacantina.dao.DAOConnectionFactory;
import ch.sfdr.lacantina.dao.ICellarEntryDAO;
import ch.sfdr.lacantina.dao.IDAOConnection;
import ch.sfdr.lacantina.dao.objects.CellarEntry;

/**
 * @author S.Freihofer
 */
public class CellarEntryAction
	extends BaseAction
{
	/*
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.
	 * ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm af,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		CellarEntryForm form = (CellarEntryForm) af;
		String action = form.getAction();

		IDAOConnection conn = DAOConnectionFactory.getConnection();

		try {
			ICellarEntryDAO dao = conn.getCellarEntryDAO();

			// defaults to LIST
			List<CellarEntry> cellarentryList =
				dao.getCellarEntries(form.getWinecellarId(), SecManager.getUserId(request));
			request.setAttribute("cellarentryList", cellarentryList);

		} finally {
			conn.close();
		}

		return mapping.findForward("cellarentryList");
	}
}
