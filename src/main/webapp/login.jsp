<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html lang="false">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>LaCantina - Login</title>

	<link rel="stylesheet" type="text/css" href="/LaCantina/css/main.css">
	<link rel="stylesheet" type="text/css" href="/LaCantina/css/menu.css">
</head>
<body>
	<div style="position: absolute; visibility: hidden; z-index: 1000;" id="overDiv"></div>
	<table id="HeaderTable" align="center" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				<td colspan="2" class="TitleBar">
					<table cellpadding="0" cellspacing="0" width="100%">
						<col width="10">
						<col>
						<col>
						<tbody>
							<tr>
								<td><a href="layout.html"><img src="/LaCantina/img/logoLeft.gif" alt="" border="0"/></a></td>
								<td class="Label">LaCantina</td>
								<td class="HeaderLinks">
									<span id="CurrentUser">
										Please log in
									</span>&nbsp;&nbsp;
								</td>
							</tr>
						</tbody>
					</table>
				</td>
				<td rowspan="2" class="Logo"><img src="/LaCantina/img/logoRight.jpg" alt="" hspace="4" vspace="0"/></td>
			</tr>
			<tr class="m0l0oout">
				<td colspan="2">
					<div class="m0l0iout">&nbsp;</div>
				</td>
			</tr>
		</tbody>
	</table>
	<div id="Body">
		<div class="ContextBar">
			<table width="100%">
				<tr>
					<td align="left" valign="bottom">
						<h3>Login</h3>
					</td>
				</tr>
			</table>
		</div>

		<html:errors property="org.apache.struts.action.GLOBAL_MESSAGE"
			header="errors.list.header"
			footer="errors.list.footer"
			prefix="errors.list.prefix"
			suffix="errors.list.suffix"/>

		<html:form action="/LoginAction.do">
			<html:hidden property="action" value="login"/>
			<table class="m0l0oout"
				cellpadding="5" width="100%">
				<tbody>
					<tr>
						<td style="text-align: center;" nowrap="nowrap" valign="top">
						<table id="t" align="center" border="0" cellpadding="1"
							cellspacing="0">
							<tbody>
								<tr>
									<td align="right"><font face="Arial, sans-serif"
										size="-1"> User name: </font>
									</td>
									<td align="left">
										<html:text property="user" size="18"/>
										<html:errors property="user"/>&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="2"><br>
									</td>
								</tr>
								<tr>
									<td align="right"><font face="Arial, sans-serif"
										size="-1"> Password: </font>
									</td>
									<td align="left">
										<html:password property="pass" size="18"/>
										<html:errors property="pass"/>&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="2"><br>
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center"><input name="null"
										value="Log in" type="submit"></td>
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
				</tbody>
			</table>
		</html:form>
	</div>
</body>
</html:html>
