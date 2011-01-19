package ch.sfdr.lacantina.gui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.sfdr.common.BaseForm.DataListAttacher;
import ch.sfdr.common.PagedForm;
import ch.sfdr.common.security.SecManager;
import ch.sfdr.lacantina.dao.DAOException;
import ch.sfdr.lacantina.dao.IDAOConnection;
import ch.sfdr.lacantina.dao.IWineCellarDAO;
import ch.sfdr.lacantina.dao.objects.ShoppingItem;
import ch.sfdr.lacantina.dao.objects.Wine;
import ch.sfdr.lacantina.dao.objects.WineCellar;

/**
 * Shopping list Form
 * @author D.Ritz
 */
public class ShoppingListForm
	extends PagedForm
	implements DataListAttacher
{
	private static final long serialVersionUID = 1L;

	public static final String ACTION_FROMWINE = "fromwine";
	public static final String ACTION_FROMWINEYEAR = "fromwineyear";

	public static final String ACTION_CHEKIN_FORM = "checkinform";
	public static final String ACTION_CHEKIN = "checkin";

	private ShoppingItem item;
	private Wine wine;
	private int refId;
	private int wineCellarId;

	/*
	 * @see ch.sfdr.common.BaseForm#doValidate(
	 * 		org.apache.struts.action.ActionMapping,
	 * 		javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();

		if (item.getWineId() != null && item.getWineId() == 0)
			item.setWineId(null);

		if (ACTION_MODIFY.equals(action)) {
			if (item.getWineId() == null && isEmptyString(item.getName()))
				errors.add("name", new ActionMessage("shoppinglist.name.required"));

			if (item.getQuantity() <= 0)
				errors.add("quantity", new ActionMessage("shoppinglist.quantity.required"));

		} else if (ACTION_CHEKIN.equals(action)) {
			if (item.getWineId() == null) {
				if (isEmptyString(wine.getName()))
					errors.add("name", new ActionMessage("wine.name.required"));
				if (isEmptyString(wine.getProducer()))
					errors.add("producer", new ActionMessage("wine.producer.required"));
				if (isEmptyString(wine.getCountry()))
					errors.add("country", new ActionMessage("wine.country.required"));
				if (isEmptyString(wine.getRegion()))
					errors.add("region", new ActionMessage("wine.region.required"));
			}
			// hack to make the JSP select the right form
			request.setAttribute("checkinForm", "prettyPlease");
		}

		return errors;
	}

	/*
	 * @see ch.sfdr.common.BaseForm#reset(
	 * 		org.apache.struts.action.ActionMapping,
	 * 		javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		super.reset(mapping, request);
		item = new ShoppingItem();
		item.setBottleSize(75);
		refId = 0;
		wineCellarId = 0;
		wine = new Wine();
	}

	/**
	 * @return the item
	 */
	public ShoppingItem getItem()
	{
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(ShoppingItem item)
	{
		this.item = item;
	}

	/**
	 * @return the refId
	 */
	public int getRefId()
	{
		return refId;
	}

	/**
	 * @param refId the refId to set
	 */
	public void setRefId(int refId)
	{
		this.refId = refId;
	}

	/**
	 * @return the wineCellarId
	 */
	public int getWineCellarId()
	{
		return wineCellarId;
	}

	/**
	 * @param wineCellarId the wineCellarId to set
	 */
	public void setWineCellarId(int wineCellarId)
	{
		this.wineCellarId = wineCellarId;
	}


	/**
	 * @return the wine
	 */
	public Wine getWine()
	{
		return wine;
	}

	/**
	 * @param wine the wine to set
	 */
	public void setWine(Wine wine)
	{
		this.wine = wine;
	}

	/*
	 * @see ch.sfdr.common.BaseForm.DataListAttacher#attachDataLists(
	 * 		javax.servlet.http.HttpServletRequest,
	 * 		ch.sfdr.lacantina.dao.IDAOConnection)
	 */
	public void attachDataLists(HttpServletRequest request, IDAOConnection conn)
		throws DAOException
	{
		IWineCellarDAO dao = conn.getWineCellarDAO();
		List<WineCellar> list = dao.getWineCellars(SecManager.getUserId(request));
		request.setAttribute("wineCellarList", list);
	}
}
