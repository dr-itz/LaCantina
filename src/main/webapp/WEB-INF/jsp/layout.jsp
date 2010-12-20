<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<%@page import="ch.sfdr.common.security.SecManager"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html lang="false">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title><bean:message key="title.head"/></title>

	<script type="text/javascript" src="/LaCantina/js/utils.js"></script>
	<script type="text/javascript" src="/LaCantina/js/menu.js"></script>
	<script type="text/javascript" src="/LaCantina/js/menu_tpl.js"></script>
	<script type="text/javascript">
	<!--
		var MENU_ITEMS = [
			['<bean:message key="menu.winecellar"/>', '/LaCantina/WineCellarAction.do'],
			['<bean:message key="menu.wines"/>', '/LaCantina/WineAction.do'],
			<% if (SecManager.getSessionToken(request).isAdmin()) { %>
				['<bean:message key="menu.config"/>', null, null,
				//	['<bean:message key="menu.config.config"/>',		'/LaCantina/admin/ConfigAction.do'],
					['<bean:message key="menu.config.usermgmt"/>',		'/LaCantina/admin/UserAction.do'],
				],
			<% } %>
		];
	-->
	</script>
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
								<td class="Label"><a class="Label" href="/LaCantina/">LaCantina</a></td>
								<td class="HeaderLinks">
									<span id="CurrentUser">
										<bean:message key="lbl.currentuser"/>:
										&nbsp;<%= SecManager.getLogin(request)%>
									</span>&nbsp;&nbsp;
									<html:link href="/LaCantina/LoginAction.do?action=logout">
										<bean:message key="lbl.signout"/>
									</html:link>
									&nbsp;
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
					<script type="text/javascript">new menu (MENU_ITEMS, MENU_POS);</script>
				</td>
			</tr>
		</tbody>
	</table>
	<div id="Body">
		<tiles:importAttribute name="hideTitle"/>
		<logic:notEqual name="hideTitle" value="yes">
			<div class="ContextBar">
				<table width="100%">
					<tr>
						<td align="left" valign="bottom">
							<tiles:importAttribute name="title" scope="page"/>
							<h3><bean:message key="${title}"/></h3>
						</td>
					</tr>
				</table>
			</div>
		</logic:notEqual>

		<html:errors property="org.apache.struts.action.GLOBAL_MESSAGE"
			header="errors.list.header"
			footer="errors.list.footer"
			prefix="errors.list.prefix"
			suffix="errors.list.suffix"/>
		<tiles:insert attribute="body"/>
	</div>
</body>
</html:html>
