package ch.sfdr.lacantina.gui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.sfdr.common.BaseAction;
import ch.sfdr.common.BaseForm;
import ch.sfdr.common.security.SecManager;
import ch.sfdr.lacantina.dao.DAOConnectionFactory;
import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.IDAOConnection;
import ch.sfdr.lacantina.dao.IWineCellarDAO;
import ch.sfdr.lacantina.dao.objects.WineCellar;

/**
 * Action for Wine Cellar handling
 * @author S.Freihofer
 */
public class WineCellarAction
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
		WineCellarForm form = (WineCellarForm) af;
		String action = form.getAction();

		if (BaseForm.ACTION_NEW.equals(action)) {
			form.reset(mapping, request);
			return returnInputForward(form, mapping, request);
		}

		IDAOConnection conn = DAOConnectionFactory.getConnection();
		try {
			IWineCellarDAO dao = conn.getWineCellarDAO();

			if (BaseForm.ACTION_FORM.equals(action)) {
				if (form.getWc().getId() != 0) {
					WineCellar wc = dao.getWineCellar(form.getWc().getId());
					form.setWc(wc);
				}
				return returnInputForward(form, mapping, request);
			}

			if (BaseForm.ACTION_MODIFY.equals(action)) {
				try {
					WineCellar wc = form.getWc();
					wc.setUserId(SecManager.getUserId(request));
					dao.storeWineCellar(wc);
				} catch (DAOException e) {
					attachSingleErrorMessage(mapping, request,
						"winecellar.update.failed");
				}

			} else if (BaseForm.ACTION_DELETE.equals(action)) {
				if (form.getWc().getId() != 0) {
					try {
						dao.deleteWineCellar(form.getWc().getId());
					} catch (DAOException e) {
						attachSingleErrorMessage(mapping, request,
							"winecellar.delete.failed");
					}
				}
			}

			// defaults to LIST
			List<WineCellar> winecellarList =
				dao.getWineCellars(SecManager.getUserId(request));
			request.setAttribute("winecellarList", winecellarList);

		} finally {
			conn.close();
		}

		return mapping.findForward("winecellarList");
	}
}
