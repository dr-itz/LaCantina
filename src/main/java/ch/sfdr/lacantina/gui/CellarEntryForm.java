package ch.sfdr.lacantina.gui;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import ch.sfdr.common.PagedForm;

/**
 * @author S.Freihofer
 */
public class CellarEntryForm
	extends PagedForm
{
	private static final long serialVersionUID = 1L;

	private int winecellarId;

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

	/*
	 * @see
	 * ch.sfdr.common.BaseForm#reset(org.apache.struts.action.ActionMapping,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		super.reset(mapping, request);
		winecellarId = 0;
	}

	/**
	 * @return the winecellarId
	 */
	public int getWinecellarId()
	{
		return winecellarId;
	}

	/**
	 * @param winecellarId the winecellarId to set
	 */
	public void setWinecellarId(int winecellarId)
	{
		this.winecellarId = winecellarId;
	}
}
