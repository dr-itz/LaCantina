<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<html:form action="/admin/UserAction.do">

<html:hidden property="action" value="list" styleId="pAction"/>
<html:hidden property="id" styleId="pId"/>

<table class="datatable" width="100%" cellspacing="0">
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<tr>
		<th>
			<bean:message key="user.lbl.login"/>
		</th>
		<th>
			<bean:message key="user.lbl.lastname"/>
		</th>
		<th>
			<bean:message key="user.lbl.firstname"/>
		</th>
		<th>
			<bean:message key="user.lbl.email"/>
		</th>
		<th>
			<bean:message key="user.lbl.admin"/>
		</th>
	</tr>
	<logic:present name="userList">
	<logic:iterate name="userList" id="item" indexId="idx">
		<tr class="<%= idx % 2 == 0 ? "even" : "odd" %>">
			<td>
				<html:link action="/admin/UserAction" paramId="id"
				 paramName="item" paramProperty="id">
				 	<html:param name="action" value="form"/>
					<bean:write name="item" property="login"/>
				</html:link>
			</td>
			<td><bean:write filter="true" name="item" property="lastName"/></td>
			<td><bean:write filter="true" name="item" property="firstName"/></td>
			<td><bean:write filter="true" name="item" property="email"/></td>
			<td>
				<bean:write name="item" property="admin"/>
			</td>
		</tr>
	</logic:iterate></logic:present>
	<tr class="footer">
		<td colspan="5">
			<html:link action="/admin/UserAction">
				<html:param name="action" value="new"/>
				<bean:message key="lbl.add"/>
			</html:link>
		</td>
	</tr>
</table>
</html:form>
