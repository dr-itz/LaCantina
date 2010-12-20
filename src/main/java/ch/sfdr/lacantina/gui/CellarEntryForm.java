package ch.sfdr.lacantina.gui;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.sfdr.common.PagedForm;
import ch.sfdr.lacantina.dao.objects.CellarEntry;

/**
 * @author S.Freihofer
 */
public class CellarEntryForm
	extends PagedForm
{
	private static final long serialVersionUID = 1L;

	private CellarEntry ce;

	/*
	 * @see
	 * ch.sfdr.common.BaseForm#doValidate(org.apache.struts.action.ActionMapping
	 * , javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();

		if (ACTION_MODIFY.equals(action)) {
			if (ce.getYear() == 0)
				errors.add("year", new ActionMessage("ce.year.required"));
			if (ce.getQuantity() == 0)
				errors.add("quantity", new ActionMessage("ce.quantity.required"));
		}

		return errors;
	}

	/*
	 * @see
	 * ch.sfdr.common.BaseForm#reset(org.apache.struts.action.ActionMapping,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		super.reset(mapping, request);
		ce = new CellarEntry();
	}

	/**
	 * @return the ce
	 */
	public CellarEntry getCe()
	{
		return ce;
	}

	/**
	 * @param ce the ce to set
	 */
	public void setCe(CellarEntry ce)
	{
		this.ce = ce;
	}
}
