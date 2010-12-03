<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html lang="false">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>LaCantina - Getting Drunk With Style</title>

	<script type="text/javascript" src="/LaCantina/js/menu.js"></script>
	<script type="text/javascript" src="/LaCantina/js/menu_tpl.js"></script>
	<script type="text/javascript">
	<!--
		var MENU_ITEMS = [
			['Wine Cellar', '/LaCantina/app/WineCellar.do'],
			['Configuration', null, null,
				['Configuration',		'/LaCantina/admin/ConfigAction.do'],
				['User management',		'/LaCantina/admin/UserAction.do'],
			],
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
								<td><a href="layout.html"><img src="/LaCantina/img/logoLeft.gif" alt="" border="0"/></a></td>
								<td class="Label">LaCantina</td>
								<td class="HeaderLinks">
									<span id="CurrentUser">
										Current user: Blub
									</span>&nbsp;&nbsp;
									<a href="logout.html">Sign out</a>
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
							<h3><tiles:get name="title"/></h3>
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
