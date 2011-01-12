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
import ch.sfdr.lacantina.dao.IShoppingListDAO;
import ch.sfdr.lacantina.dao.IWineDAO;
import ch.sfdr.lacantina.dao.objects.CellarEntry;
import ch.sfdr.lacantina.dao.objects.ShoppingItem;
import ch.sfdr.lacantina.dao.objects.Wine;

/**
 * Action for shopping list handling
 * @author D.Ritz
 */
public class ShoppingListAction
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
		ShoppingListForm form = (ShoppingListForm) pf;
		String action = form.getAction();

		if (BaseForm.ACTION_NEW.equals(action)) {
			form.reset(mapping, request);
			return returnInputForward(form, mapping, request);
		}

		IDAOConnection conn = DAOConnectionFactory.getConnection();
		try {
			// pre-fill form from existing wine
			if (ShoppingListForm.ACTION_FROMWINE.equals(action) ||
				ShoppingListForm.ACTION_FROMWINEYEAR.equals(action))
			{
				int refId = form.getRefId();

				form.reset(mapping, request);
				ShoppingItem si = form.getWine();

				Wine w = null;

				if (ShoppingListForm.ACTION_FROMWINE.equals(action)) {
					IWineDAO wdao = conn.getWineDAO();
					w = wdao.getWine(refId);
				} else {
					ICellarEntryDAO cedao = conn.getCellarEntryDAO();
					CellarEntry ce = cedao.getCellarEntry(refId,
						SecManager.getUserId(request));

					if (ce != null) {
						w = ce.getWine();
						si.setYear(ce.getYear());
					}
				}
				if (w != null) {
					si.setName(w.getName());
					si.setProducer(w.getProducer());
					si.setBottleSize(w.getBottleSize());
				}

				return returnInputForward(form, mapping, request);
			}

			IShoppingListDAO dao = conn.getShoppingListDAO();

			if (BaseForm.ACTION_FORM.equals(action)) {
				if (form.getWine().getId() != 0) {
					ShoppingItem item = dao.getShoppingItem(form.getWine().getId());
					form.setWine(item);
				}
				return returnInputForward(form, mapping, request);
			}

			if (BaseForm.ACTION_MODIFY.equals(action)) {
				try {
					ShoppingItem si = form.getWine();
					si.setUserId(SecManager.getUserId(request));
					dao.storeShoppingItem(si);
				} catch (DAOException e) {
					attachSingleErrorMessage(mapping, request,
						"shoppinglist.update.failed");
				}

			} else if (BaseForm.ACTION_DELETE.equals(action)) {
				if (form.getWine().getId() != 0) {
					try {
						dao.deleteShoppingItem(form.getWine().getId());
					} catch (DAOException e) {
						attachSingleErrorMessage(mapping, request,
							"shoppinglist.delete.failed");
					}
				}
			}

			// defaults to LIST
			List<ShoppingItem> shoppingList = dao.getShoppingList(
				SecManager.getUserId(request), form.getPagingCookie());
			setList(request, form.getPagingCookie(), shoppingList, "shoppingList");
		} finally {
			conn.close();
		}

		return mapping.findForward("shoppingList");
	}
}
