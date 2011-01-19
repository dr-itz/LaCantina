package ch.sfdr.lacantina.gui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.sfdr.common.BaseForm;
import ch.sfdr.common.PagedAction;
import ch.sfdr.common.PagedForm;
import ch.sfdr.common.security.SecManager;
import ch.sfdr.lacantina.dao.DAOConnectionFactory;
import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.ICellarEntryDAO;
import ch.sfdr.lacantina.dao.IDAOConnection;
import ch.sfdr.lacantina.dao.objects.CellarEntry;

/**
 * Action for wine cellar entries
 * @author S.Freihofer
 */
public class CellarEntryAction
	extends PagedAction
{
	/*
	 * @see org.apache.struts.action.Action#execute(
	 * 		org.apache.struts.action.ActionMapping,
	 * 		org.apache.struts.action.ActionForm,
	 * 		javax.servlet.http.HttpServletRequest,
	 * 		javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward doExecute(ActionMapping mapping, PagedForm pf,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		CellarEntryForm form = (CellarEntryForm) pf;
		String action = form.getAction();

		IDAOConnection conn = DAOConnectionFactory.getConnection();
		try {
			ICellarEntryDAO dao = conn.getCellarEntryDAO();

			if (BaseForm.ACTION_NEW.equals(action)) {
				int winecellarId = form.getCe().getWinecellarId();
				form.reset(mapping, request);
				form.getCe().setWinecellarId(winecellarId);
				return returnInputForward(form, conn, mapping, request);
			}

			if (BaseForm.ACTION_FORM.equals(action)) {
				if (form.getCe().getId() != 0) {
					CellarEntry ce = dao.getCellarEntry(
						form.getCe().getId(), SecManager.getUserId(request));
					form.setCe(ce);
				}
				return returnInputForward(form, conn, mapping, request);
			}

			if (BaseForm.ACTION_MODIFY.equals(action)) {
				try {
					CellarEntry ce = form.getCe();
					dao.storeCellarEntry(ce);
				} catch (DAOException e) {
					attachSingleErrorMessage(mapping, request,
						"ce.update.failed");
				}

			} else if (BaseForm.ACTION_DELETE.equals(action)) {
				if (form.getCe().getId() != 0) {
					try {
						dao.deleteCellarEntry(form.getCe().getId());
					} catch (DAOException e) {
						attachSingleErrorMessage(mapping, request,
							"ce.delete.failed");
					}
				}
			} else if (BaseForm.ACTION_LIST.equals(action) &&
					CellarEntryForm.LIST_TYPE_RATINGS.equals(form.getListType()))
			{

				List<CellarEntry> wineratingList = dao.getWineRatings(0,
					form.getPagingCookie());
				setList(request, form.getPagingCookie(), wineratingList,
					"wineratingList");

				return mapping.findForward("wineratingList");
			}

			// defaults to LIST
			List<CellarEntry> cellarentryList = dao.getCellarEntries(
				form.getCe().getWinecellarId(), SecManager.getUserId(request),
				form.getPagingCookie());
			setList(request, form.getPagingCookie(), cellarentryList,
				"cellarentryList");
		} finally {
			conn.close();
		}

		return mapping.findForward("cellarentryList");
	}
}
