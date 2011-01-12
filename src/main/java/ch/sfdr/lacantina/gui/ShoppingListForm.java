package ch.sfdr.lacantina.gui;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.sfdr.common.PagedForm;
import ch.sfdr.lacantina.dao.objects.ShoppingItem;

/**
 * Shopping list Form
 * @author D.Ritz
 */
public class ShoppingListForm
	extends PagedForm
{
	private static final long serialVersionUID = 1L;

	public static final String ACTION_FROMWINE = "fromwine";
	public static final String ACTION_FROMWINEYEAR = "fromwineyear";

	private ShoppingItem wine;
	private int refId;

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

		if (ACTION_MODIFY.equals(action)) {
			if (isEmptyString(wine.getName()))
				errors.add("name", new ActionMessage("shoppinglist.name.required"));
			if (wine.getQuantity() <= 0)
				errors.add("quantity", new ActionMessage("shoppinglist.quantity.required"));
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
		wine = new ShoppingItem();
		wine.setBottleSize(75);
		refId = 0;
	}

	/**
	 * @return the wine
	 */
	public ShoppingItem getWine()
	{
		return wine;
	}

	/**
	 * @param wine the wine to set
	 */
	public void setWine(ShoppingItem wine)
	{
		this.wine = wine;
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
}
