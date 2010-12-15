package ch.sfdr.lacantina.gui;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.sfdr.common.BaseForm;
import ch.sfdr.lacantina.dao.objects.WineCellar;

/**
 * Wine Cellar Form
 * @author S.Freihofer
 */
public class WineCellarForm
	extends BaseForm
{
	private static final long serialVersionUID = 1L;

	private WineCellar wc;

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
			if (isEmptyString(wc.getName()))
				errors.add("name",
					new ActionMessage("winecellar.name.required"));
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
		wc = new WineCellar();
	}

	/**
	 * @return the wc
	 */
	public WineCellar getWc()
	{
		return wc;
	}

	/**
	 * @param wc the wc to set
	 */
	public void setWc(WineCellar wc)
	{
		this.wc = wc;
	}
}
