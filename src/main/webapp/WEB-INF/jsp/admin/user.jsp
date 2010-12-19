<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/admin/UserAction.do" styleId="userForm">
	<html:hidden property="action" value="mod" styleId="pAction"/>
	<html:hidden property="user.id" styleId="pId"/>

	<table class="formtable" width="100%">
		<tr>
			<th colspan="3">
				<bean:message key="user.lbl.user"/>
				 <logic:notEmpty name="userForm" property="user.login">
				 	'<bean:write name="userForm" property="user.login"/>'
				 </logic:notEmpty>
			</th>
		</tr>
		<tr class="even">
			<td width="120"><bean:message key="user.lbl.login"/></td>
			<td width="450">
				<html:text property="user.login" styleId="flogin"/>
			</td>
			<td><html:errors property="login"/>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="user.lbl.lastname"/></td>
			<td>
				<html:text property="user.lastName" styleId="flastName"/>
			</td>
			<td><html:errors property="lastName"/>&nbsp;</td>
		</tr>
		<tr class="even">
			<td><bean:message key="user.lbl.firstname"/></td>
			<td>
				<html:text property="user.firstName" styleId="ffirstName"/>
			</td>
			<td><html:errors property="firstName"/>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="user.lbl.email"/></td>
			<td>
				<html:text property="user.email" styleId="femail"/>
			</td>
			<td><html:errors property="email"/>&nbsp;</td>
		</tr>
		<tr class="even">
			<td><bean:message key="user.lbl.admin"/></td>
			<td>
				<html:checkbox property="user.admin" styleId="fadmin"/>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="user.lbl.pass"/></td>
			<td>
				<html:password property="pw1" styleId="fpw1"/> <bean:message key="user.lbl.pass"/><br/>
				<html:password property="pw2" styleId="fpw2"/> <bean:message key="user.lbl.pass.repeat"/>
			</td>
			<td>
				<html:errors property="pw"/>&nbsp;
			</td>
		</tr>
		<tr class="footer">
			<td>
				<html:link action="/admin/UserAction.do">
					<bean:message key="lbl.cancel"/>
				</html:link>
			</td>
			<td align="right">
				<logic:notEqual name="userForm" property="user.id" value="0">
					<input type="button" value="<bean:message key="lbl.delete"/>"
					 id="fdelete" onClick="javascript:pfDelete()">
					&nbsp;<html:submit styleId="fSubmit">
						<bean:message key="lbl.modify"/>
					</html:submit>
				</logic:notEqual>
				<logic:equal name="userForm" property="user.id" value="0">
					<html:submit styleId="fSubmit">
						<bean:message key="lbl.add"/>
					</html:submit>
				</logic:equal>
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
</html:form>
