<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/CellarEntryAction.do" styleId="cellarentryForm">
	<html:hidden property="action" value="mod" styleId="pAction"/>
	<html:hidden property="ce.id" styleId="pId"/>
	<html:hidden property="ce.winecellarId" styleId="pWinecellarId"/>

	<table class="formtable" width="100%">
		<tr>
			<th colspan="3">
				<bean:message key="ce.lbl.wine"/>
				 <logic:notEmpty name="cellarentryForm" property="ce.wine.name">
				 	'<bean:write name="cellarentryForm" property="ce.wine.name"/>'
				 </logic:notEmpty>
			</th>
		</tr>
		<tr class="even">
			<td width="120"><bean:message key="ce.lbl.wine"/></td>
			<td width="450">
				<html:select property="ce.wine.id" styleId="fwineId">
					<html:optionsCollection name="wineList" label="friendlyName" value="id"/>
				</html:select>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="ce.lbl.year"/></td>
			<td>
				<html:text property="ce.year" styleId="fyear"/>
			</td>
			<td><html:errors property="year"/>&nbsp;</td>
		</tr>
		<tr class="even">
			<td><bean:message key="ce.lbl.quantity"/></td>
			<td>
				<html:text property="ce.quantity" styleId="fquantity"/>
			</td>
			<td><html:errors property="quantity"/>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="ce.lbl.ratingPoints"/></td>
			<td>
				<html:select property="ce.ratingPoints" styleId="fratingPoints">
					<html:option value="0">-- None --</html:option>
					<html:option value="1">1</html:option>
					<html:option value="2">2</html:option>
					<html:option value="3">3</html:option>
					<html:option value="4">4</html:option>
					<html:option value="5">5</html:option>
					<html:option value="6">6</html:option>
					<html:option value="7">7</html:option>
					<html:option value="8">8</html:option>
					<html:option value="9">9</html:option>
					<html:option value="10">10</html:option>
				</html:select>
			</td>
			<td><html:errors property="ratingPoints"/>&nbsp;</td>
		</tr>
		<tr class="even">
			<td><bean:message key="ce.lbl.ratingText"/></td>
			<td>
				<html:textarea cols="50" rows="8" property="ce.ratingText" styleId="fratingText"/>
			</td>
			<td><html:errors property="ratingText"/>&nbsp;</td>
		</tr>
		<tr class="footer">
			<td>
				<html:link action="/CellarEntryAction" paramId="ce.winecellarId"
				 paramName="cellarentryForm" paramProperty="ce.winecellarId">
				 	<html:param name="action" value="list"/>
					<bean:message key="lbl.cancel"/>
				</html:link>
			</td>
			<td align="right">
				<logic:notEqual name="cellarentryForm" property="ce.id" value="0">
					<input type="button" value="<bean:message key="lbl.delete"/>"
					 id="fdelete" onClick="javascript:pfDelete()">
					&nbsp;<html:submit styleId="fSubmit">
						<bean:message key="lbl.modify"/>
					</html:submit>
				</logic:notEqual>
				<logic:equal name="cellarentryForm" property="ce.id" value="0">
					<html:submit styleId="fSubmit">
						<bean:message key="lbl.add"/>
					</html:submit>
				</logic:equal>
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
</html:form>
