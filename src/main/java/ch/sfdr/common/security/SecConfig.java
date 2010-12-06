package ch.sfdr.common.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

/**
 * Configuration for SecurityFilter
 * @author D.Ritz
 */
public class SecConfig
{
	/**
	 * a single constraint
	 * - a set of URL patterns
	 * - a flag indicating if the user must be logged in
	 * - a set of privileges that are allowed to access the resource
	 */
	public static class SecurityConstraint
	{
		private List<String> urlPatterns = new ArrayList<String>();
		private List<String> privileges  = new ArrayList<String>();
		private boolean requireLogin    = false;
		private boolean requireLoginSet = false;

		public void addUrl(String url)     { urlPatterns.add(url); }
		public void addPrivilege(String p) { privileges.add(p); }

		public boolean isRequireLoginSet() { return requireLoginSet; }
		public boolean isRequireLogin()    { return requireLogin; }
		public void    setLogin(String s)
		{
			requireLogin = "required".equals(s);
			requireLoginSet = true;
		}

		public List<String> getUrlPatterns() { return urlPatterns; }
		public List<String> getPrivileges()  {return privileges; }
	}

	private String userAuth;
	private String loginUrl;
	private String denyUrl;
	private String homeUrl;
	private boolean requireLogin;
	private List<SecurityConstraint> constraints = new ArrayList<SecurityConstraint>();


	/**
	 * parses the XML from an InputStream
	 * @param input
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public static SecConfig parse(InputStream input)
		throws SAXException, IOException
	{
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.addObjectCreate("security-config",	SecConfig.class);

		// redirectors
		digester.addCallMethod("security-config/redirectors/login-url", "setLoginUrl", 1);
		digester.addCallParam("security-config/redirectors/login-url", 0);
		digester.addCallMethod("security-config/redirectors/deny-url", "setDenyUrl", 1);
		digester.addCallParam("security-config/redirectors/deny-url", 0);
		digester.addCallMethod("security-config/redirectors/home-url", "setHomeUrl", 1);
		digester.addCallParam("security-config/redirectors/home-url", 0);

		// user auth class
		digester.addCallMethod("security-config/user-auth/class", "setUserAuth", 1);
		digester.addCallParam("security-config/user-auth/class", 0);

		// default constraint
		digester.addCallMethod("security-config/default-constraint/login", "setLogin", 1);
		digester.addCallParam("security-config/default-constraint/login", 0);

		// constraints
		digester.addObjectCreate("security-config/constraint", SecurityConstraint.class);
		digester.addCallMethod("security-config/constraint/login", "setLogin", 1);
		digester.addCallParam("security-config/constraint/login", 0);
		digester.addCallMethod("security-config/constraint/url-pattern", "addUrl", 1);
		digester.addCallParam("security-config/constraint/url-pattern", 0);
		digester.addCallMethod("security-config/constraint/privilege", "addPrivilege", 1);
		digester.addCallParam("security-config/constraint/privilege", 0);
		digester.addSetNext("security-config/constraint", "addConstraints");

		return (SecConfig) digester.parse(input);
	}

	public String getUserAuth()          { return userAuth; }
	public void   setUserAuth(String ua) { userAuth = ua; }

	public String getLoginUrl()           { return loginUrl; }
	public void   setLoginUrl(String url) { loginUrl = url; }

	public String getDenyUrl()           { return denyUrl; }
	public void   setDenyUrl(String url) { denyUrl = url; }

	public String getHomeUrl()           { return homeUrl; }
	public void   setHomeUrl(String url) { homeUrl = url; }

	public boolean isDefaultRequireLogin() { return requireLogin; }
	public void    setLogin(String s)      { requireLogin = "required".equals(s); }

	public List<SecurityConstraint> getConstraints() { return constraints; }
	public void addConstraints(SecurityConstraint c) { constraints.add(c); }
}
