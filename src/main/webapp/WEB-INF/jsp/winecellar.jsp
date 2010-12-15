<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/WineCellarAction.do" styleId="winecellarForm">
	<html:hidden property="action" value="mod" styleId="pAction"/>
	<html:hidden property="wc.id" styleId="pId"/>

	<table class="formtable" width="100%">
		<tr>
			<th colspan="3">
				<bean:message key="wc.lbl.name"/>
				 <logic:notEmpty name="winecellarForm" property="wc.name">
				 	'<bean:write name="winecellarForm" property="wc.name"/>'
				 </logic:notEmpty>
			</th>
		</tr>
		<tr class="even">
			<td width="120"><bean:message key="wc.lbl.name"/></td>
			<td width="450">
				<html:text property="wc.name" styleId="fname"/>
			</td>
			<td><html:errors property="name"/>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="wc.lbl.capacity"/></td>
			<td>
				<html:text property="wc.capacity" styleId="fcapacity"/>
			</td>
			<td><html:errors property="capacity"/>&nbsp;</td>
		</tr>
		<tr class="footer">
			<td>
				<html:link action="/WineCellarAction.do">
					<bean:message key="lbl.cancel"/>
				</html:link>
			</td>
			<td align="right">
				<logic:notEqual name="winecellarForm" property="wc.id" value="0">
					<input type="button" value="<bean:message key="lbl.delete"/>"
					 id="fdelete" onClick="javascript:pfDelete('winecellarForm')">
					&nbsp;<html:submit styleId="fSubmit">
						<bean:message key="lbl.modify"/>
					</html:submit>
				</logic:notEqual>
				<logic:equal name="winecellarForm" property="wc.id" value="0">
					<html:submit styleId="fSubmit">
						<bean:message key="lbl.add"/>
					</html:submit>
				</logic:equal>
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
</html:form>
