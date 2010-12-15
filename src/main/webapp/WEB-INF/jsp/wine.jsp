<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/WineAction.do" styleId="wineForm">
	<html:hidden property="action" value="mod" styleId="pAction"/>
	<html:hidden property="wine.id" styleId="pId"/>

	<table class="formtable" width="100%">
		<tr>
			<th colspan="3">
				<bean:message key="wine.lbl.name"/>
				 <logic:notEmpty name="wineForm" property="wine.name">
				 	'<bean:write name="wineForm" property="wine.name"/>'
				 </logic:notEmpty>
			</th>
		</tr>
		<tr class="even">
			<td width="120"><bean:message key="wine.lbl.name"/></td>
			<td width="450">
				<html:text property="wine.name" styleId="fname"/>
			</td>
			<td><html:errors property="name"/>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="wine.lbl.producer"/></td>
			<td>
				<html:text property="wine.producer" styleId="fproducer"/>
			</td>
			<td><html:errors property="producer"/>&nbsp;</td>
		</tr>
		<tr class="even">
			<td><bean:message key="wine.lbl.country"/></td>
			<td>
				<html:text property="wine.country" styleId="fcountry"/>
			</td>
			<td><html:errors property="country"/>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="wine.lbl.region"/></td>
			<td>
				<html:text property="wine.region" styleId="fregion"/>
			</td>
			<td><html:errors property="region"/>&nbsp;</td>
		</tr>
		<tr class="even">
			<td><bean:message key="wine.lbl.description"/></td>
			<td>
				<html:textarea property="wine.description" styleId="fdescr"/>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="wine.lbl.bottlesize"/></td>
			<td>
				<html:text property="wine.bottleSize" styleId="fbottlesize"/>
			</td>
			<td><html:errors property="bottlesize"/>&nbsp;</td>
		</tr>
		<tr class="footer">
			<td>
				<html:link action="/WineAction.do">
					<bean:message key="lbl.cancel"/>
				</html:link>
			</td>
			<td align="right">
				<logic:notEqual name="wineForm" property="wine.id" value="0">
					<input type="button" value="<bean:message key="lbl.delete"/>"
					 id="fdelete" onClick="javascript:pfDelete('wineForm')">
					&nbsp;<html:submit styleId="fSubmit">
						<bean:message key="lbl.modify"/>
					</html:submit>
				</logic:notEqual>
				<logic:equal name="wineForm" property="wine.id" value="0">
					<html:submit styleId="fSubmit">
						<bean:message key="lbl.add"/>
					</html:submit>
				</logic:equal>
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
</html:form>
