package ch.sfdr.common.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SecurityFilter, a javax.servlet.Filter for WebApps not using container based
 * security. This is more portable and flexible.
 * @author D.Ritz
 */
public class SecFilter
	implements Filter
{
	private SecManager manager;
	private List<FilterEntry> filterEntries;


	/*
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig conf)
		throws ServletException
	{
		manager = SecManager.getInstance(conf.getServletContext());
		SecConfig sconf = manager.getSecurityConfig();

		// setup filter entries
		boolean defLogin = sconf.isDefaultRequireLogin();
		filterEntries = new ArrayList<FilterEntry>();
		for (SecConfig.SecurityConstraint c : sconf.getConstraints())
		{
			boolean login = c.isRequireLoginSet() ? c.isRequireLogin() : defLogin;
			boolean admin = false;
			for (String p : c.getPrivileges())
				admin = "admin".equals(p);

			for (String up : c.getUrlPatterns()) {
				FilterEntry e = new FilterEntry();
				e.pattern = Pattern.compile(up);
				e.login = login;
				e.admin = admin;
				filterEntries.add(e);
			}
		}
	}

	/*
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy()
	{

	}

	/*
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain)
		throws IOException, ServletException
	{
		boolean doChain = true;
		if (req instanceof HttpServletRequest &&
		    resp instanceof HttpServletResponse)
		{
			doChain = filterHttp((HttpServletRequest) req,
				(HttpServletResponse) resp, filterChain);
		}

		if (doChain)
			filterChain.doFilter(req, resp);
	}

	/**
	 * does the actual filtering
	 */
	private boolean filterHttp(HttpServletRequest req, HttpServletResponse resp,
			FilterChain filterChain)
		throws IOException, ServletException
	{
		String uri = SecManager.getRelativeUri(req);

		// get the constraint config
		boolean login = manager.getSecurityConfig().isDefaultRequireLogin();
		boolean needAdmin = false;
		for (FilterEntry e : filterEntries) {
			if (e.matches(uri)) {
				login = e.login;
				needAdmin = e.admin;
				break;
			}
		}

		// check authentication and authorization
		if (login) {
			SessionToken tok = SecManager.getSessionToken(req);
			if (tok == null) {
				manager.redirectLogin(req, resp);
				return false;
			} else if (needAdmin && !tok.isAdmin()) {
				manager.redirectDeny(req, resp);
				return false;
			}
		}

		return true;
	}

	/**
	 * one entity in the filter list
	 */
	private static class FilterEntry
	{
		Pattern pattern;
		boolean login;
		boolean admin;

		boolean matches(String url)
		{
			return pattern.matcher(url).matches();
		}
	}
}
