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
import ch.sfdr.lacantina.dao.IDAOConnection;
import ch.sfdr.lacantina.dao.IWineDAO;
import ch.sfdr.lacantina.dao.objects.Wine;

/**
 * Action for Wine handling
 * @author D.Ritz
 */
public class WineAction
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
		WineForm form = (WineForm) pf;
		String action = form.getAction();

		if (BaseForm.ACTION_NEW.equals(action)) {
			form.reset(mapping, request);
			return returnInputForward(form, mapping, request);
		}

		IDAOConnection conn = DAOConnectionFactory.getConnection();
		try {
			IWineDAO dao = conn.getWineDAO();

			if (BaseForm.ACTION_FORM.equals(action)) {
				if (form.getWine().getId() != 0) {
					Wine wine = dao.getWine(form.getWine().getId());
					form.setWine(wine);
				}
				return returnInputForward(form, mapping, request);
			}

			if (BaseForm.ACTION_MODIFY.equals(action)) {
				try {
					Wine w = form.getWine();
					w.setUserId(SecManager.getUserId(request));
					dao.storeWine(w);
				} catch (DAOException e) {
					attachSingleErrorMessage(mapping, request,
						"wine.update.failed");
				}

			} else if (BaseForm.ACTION_DELETE.equals(action)) {
				if (form.getWine().getId() != 0) {
					try {
						dao.deleteWine(form.getWine().getId());
					} catch (DAOException e) {
						attachSingleErrorMessage(mapping, request,
							"wine.delete.failed");
					}
				}
			}

			// defaults to LIST
			List<Wine> wineList = dao.getWineList(SecManager.getUserId(request),
				form.getPagingCookie());
			setList(request, form.getPagingCookie(), wineList, "wineList");
		} finally {
			conn.close();
		}

		return mapping.findForward("wineList");
	}
}
