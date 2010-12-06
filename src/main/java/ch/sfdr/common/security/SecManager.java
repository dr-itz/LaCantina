package ch.sfdr.common.security;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Central Security Manager
 * @author D.Ritz
 */
public class SecManager
{
	private static final Log log = LogFactory.getLog(SecManager.class);

	// attribute names
	private static final String ATTR_SECURITY_MANAGER = SecManager.class.getName();
	private static final String ATTR_SESSION_TOKEN    = SessionToken.class.getName();

	// session inactivity timeout
	private int sessionTimeout = 120;

	// the security config from the XML
	private SecConfig config;
	private IUserAuth userAuth;

	/**
	 * SecurityManager. The security config is loaded from the context's XML
	 * named by the parameter security-config
	 * @param context the ServletContext
	 */
	private SecManager(ServletContext ctx)
	{
		log.debug("creating SecurityManager instance");

		String url = ctx.getInitParameter("security-config");
		InputStream is = ctx.getResourceAsStream(url);
		try {
			config = SecConfig.parse(is);
			userAuth = (IUserAuth) Class.forName(config.getUserAuth()).newInstance();
		} catch (Exception e) {
			log.error("error trying to load security-config: " + e);
		}
	}

	/**
	 * gets the security manager for the specified servlet context
	 * @param ctx the ServletContext
	 * @return the security manager for this context
	 */
	public static SecManager getInstance(ServletContext ctx)
	{
		synchronized (ctx) {
			SecManager mgr = (SecManager) ctx.getAttribute(
				ATTR_SECURITY_MANAGER);
			if (mgr == null) {
				mgr = new SecManager(ctx);
				ctx.setAttribute(ATTR_SECURITY_MANAGER, mgr);
			}
			return mgr;
		}
	}

	/**
	 * returns the SecurityConfig for this security manager
	 * @return SecurityConfig
	 */
	public SecConfig getSecurityConfig()
	{
		return config;
	}


	/**
	 * returns the session timeout in minutes
	 * @return session timeout
	 */
	public int getSessionTimeout()
	{
		return sessionTimeout;
	}

	/**
	 * sets the session timeout in minutes
	 * @param minutes the session timeout in minutes
	 */
	public void setSessionTimeout(int minutes)
	{
		sessionTimeout = minutes;
	}

	/**
	 * returns a relative URL from the current request. cuts off the ContextPath
	 * @param req
	 * @return
	 */
	public static String getRelativeUri(HttpServletRequest req)
	{
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		if (uri.startsWith(cp))
			return uri.substring(cp.length());
		return uri;
	}

	/**
	 * sets the SessionToken to the requests current session
	 * @param req the HttpServletRequest
	 * @param tok the SessionToken
	 */
	public void setSessionToken(HttpServletRequest req, SessionToken tok)
	{
		HttpSession sess = req.getSession();
		sess.setMaxInactiveInterval(sessionTimeout * 60);
		sess.setAttribute(ATTR_SESSION_TOKEN, tok);
	}


	/**
	 * returns the SessionToken of the current session or null if no token exists
	 * @param req the HttpServletRequest
	 * @return SessionToken
	 */
	public static SessionToken getSessionToken(HttpServletRequest req)
	{
		HttpSession sess = req.getSession(false);
		if (sess == null)
			return null;
		SessionToken tok = (SessionToken) sess.getAttribute(ATTR_SESSION_TOKEN);
		return tok;
	}

	/**
	 * invalidates the SessionToken. i.e. logout
	 * @param req
	 */
	public static void invalidateSessionToken(HttpServletRequest req)
	{
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			sess.removeAttribute(ATTR_SESSION_TOKEN);
		}
	}

	/**
	 * authenticates a user, set the session token to the HttpSession
	 * @param req the HttpServletRequest
	 * @param user User's login
	 * @param pass password
	 * @return SessionToken if successful, null otherwise
	 */
	public SessionToken authenticateUser(HttpServletRequest req, String user, String pass)
	{
		SessionToken tok = userAuth.authenticateUser(user, pass);
		if (tok != null)
			setSessionToken(req, tok);
		return tok;
	}

	/**
	 * sends a redirection header
	 * @param resp
	 * @param url
	 */
	public static void redirect(HttpServletRequest req,
		HttpServletResponse resp, String url)
		throws IOException
	{
		log.debug("from=" + req.getRequestURI() + " to=" + url);
		if (!url.startsWith("http:") && !url.startsWith("https://")) {
			String prefix = req.getContextPath();
			if (!url.startsWith(prefix)) {
				if (url.startsWith("/"))
					url = prefix + url;
				else
					url = prefix + "/" + url;
			}
		}
		resp.sendRedirect(url);
	}

	/**
	 * either dispatch the request internally or send a HTTP redirect
	 * @param req
	 * @param resp
	 * @param url
	 * @throws IOException
	 */
	private static void dispatch(HttpServletRequest req,
		HttpServletResponse resp, String url)
		throws IOException
	{
		try {
			req.getRequestDispatcher(url).forward(req, resp);
		} catch (ServletException e) {
			redirect(req, resp, url);
		}
	}

	/**
	 * redirects to the login page
	 * @param resp
	 * @throws IOException
	 */
	public void redirectLogin(HttpServletRequest req, HttpServletResponse resp)
		throws IOException
	{
		dispatch(req, resp, config.getLoginUrl());
	}

	/**
	 * redirects to the deny page
	 * @param resp
	 * @throws IOException
	 */
	public void redirectDeny(HttpServletRequest req, HttpServletResponse resp)
		throws IOException
	{
		dispatch(req, resp, config.getDenyUrl());
	}

	/**
	 * redirects to the home page
	 * @param resp
	 * @throws IOException
	 */
	public void redirectHome(HttpServletRequest req, HttpServletResponse resp)
		throws IOException
	{
		redirect(req, resp, config.getHomeUrl());
	}
}
