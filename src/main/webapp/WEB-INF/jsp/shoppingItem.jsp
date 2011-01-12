<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/ShoppingListAction.do" styleId="shoppinglistForm">
	<html:hidden property="action" value="mod" styleId="pAction"/>
	<html:hidden property="wine.id" styleId="pId"/>

	<table class="formtable" width="100%">
		<tr>
			<th colspan="3">
				<bean:message key="wine.lbl.name"/>
				 <logic:notEmpty name="shoppinglistForm" property="wine.name">
				 	'<bean:write name="shoppinglistForm" property="wine.name"/>'
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
			<td><bean:message key="ce.lbl.year"/></td>
			<td>
				<html:text property="wine.year" styleId="fyear"/>
			</td>
			<td><html:errors property="year"/>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="wine.lbl.bottlesize"/></td>
			<td>
				<html:text property="wine.bottleSize" styleId="fbottlesize"/>
			</td>
			<td><html:errors property="bottlesize"/>&nbsp;</td>
		</tr>
		<tr class="even">
			<td><bean:message key="ce.lbl.quantity"/></td>
			<td>
				<html:text property="wine.quantity" styleId="fquantity"/>
			</td>
			<td><html:errors property="quantity"/>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="shoppinglist.lbl.store"/></td>
			<td>
				<html:text property="wine.store" styleId="fstore"/>
			</td>
			<td><html:errors property="store"/>&nbsp;</td>
		</tr>
		<tr class="footer">
			<td>
				<html:link action="/ShoppingListAction.do">
					<bean:message key="lbl.cancel"/>
				</html:link>
			</td>
			<td align="right">
				<logic:notEqual name="shoppinglistForm" property="wine.id" value="0">
					<input type="button" value="<bean:message key="lbl.delete"/>"
					 id="fdelete" onClick="javascript:pfDelete()">
					&nbsp;<html:submit styleId="fSubmit">
						<bean:message key="lbl.modify"/>
					</html:submit>
				</logic:notEqual>
				<logic:equal name="shoppinglistForm" property="wine.id" value="0">
					<html:submit styleId="fSubmit">
						<bean:message key="lbl.add"/>
					</html:submit>
				</logic:equal>
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
</html:form>
