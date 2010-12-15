package ch.sfdr.lacantina.gui;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.sfdr.common.BaseForm;
import ch.sfdr.lacantina.dao.objects.Wine;

/**
 * Wine Form
 * @author D.Ritz
 */
public class WineForm
	extends BaseForm
{
	private static final long serialVersionUID = 1L;

	private Wine wine;

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
				errors.add("name", new ActionMessage("wine.name.required"));
			if (isEmptyString(wine.getProducer()))
				errors.add("producer", new ActionMessage("wine.producer.required"));
			if (isEmptyString(wine.getCountry()))
				errors.add("country", new ActionMessage("wine.country.required"));
			if (isEmptyString(wine.getRegion()))
				errors.add("region", new ActionMessage("wine.region.required"));
		}

		return errors;
	}

	/*
	 * @see ch.sfdr.common.BaseForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		super.reset(mapping, request);
		wine = new Wine();
		wine.setBottleSize(75);
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
}
