package ch.sfdr.lacantina.gui;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

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

		return errors;
	}

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
