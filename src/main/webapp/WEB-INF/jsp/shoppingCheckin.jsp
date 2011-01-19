<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/ShoppingListAction.do" styleId="shoppinglistForm">
	<html:hidden property="action" value="checkin" styleId="pAction"/>
	<html:hidden property="item.id" styleId="pId"/>
	<html:hidden property="item.wineId" styleId="pWineId"/>

	<table class="formtable" width="100%">
		<tr>
			<th colspan="3">
				<bean:message key="shoppinglist.lbl.checkin"/>
				 <logic:notEmpty name="shoppinglistForm" property="item.name">
				 	'<bean:write name="shoppinglistForm" property="item.name" filter="true"/>'
				 </logic:notEmpty>
			</th>
		</tr>

		<tr class="even">
			<td width="120"><bean:message key="wine.lbl.name"/></td>
			<td width="450">
				<logic:notEmpty name="shoppinglistForm" property="item.wineId">
					<bean:write name="shoppinglistForm" property="wine.name" filter="true"/>
				</logic:notEmpty>
				<logic:empty name="shoppinglistForm" property="item.wineId">
					<html:text property="wine.name" styleId="fname"/>
				</logic:empty>
			</td>
			<td><html:errors property="name"/>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="wine.lbl.producer"/></td>
			<td>
				<logic:notEmpty name="shoppinglistForm" property="item.wineId">
					<bean:write name="shoppinglistForm" property="wine.producer" filter="true"/>
				</logic:notEmpty>
				<logic:empty name="shoppinglistForm" property="item.wineId">
					<html:text property="wine.producer" styleId="fproducer"/>
				</logic:empty>
			</td>
			<td><html:errors property="producer"/>&nbsp;</td>
		</tr>
		<tr class="even">
			<td><bean:message key="wine.lbl.country"/></td>
			<td>
				<logic:notEmpty name="shoppinglistForm" property="item.wineId">
					<bean:write name="shoppinglistForm" property="wine.country" filter="true"/>
				</logic:notEmpty>
				<logic:empty name="shoppinglistForm" property="item.wineId">
					<html:text property="wine.country" styleId="fcountry"/>
				</logic:empty>
			</td>
			<td><html:errors property="country"/>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="wine.lbl.region"/></td>
			<td>
				<logic:notEmpty name="shoppinglistForm" property="item.wineId">
					<bean:write name="shoppinglistForm" property="wine.region" filter="true"/>
				</logic:notEmpty>
				<logic:empty name="shoppinglistForm" property="item.wineId">
					<html:text property="wine.region" styleId="fregion"/>
				</logic:empty>
			</td>
			<td><html:errors property="region"/>&nbsp;</td>
		</tr>
		<tr class="even">
			<td><bean:message key="wine.lbl.bottlesize"/></td>
			<td>
				<logic:notEmpty name="shoppinglistForm" property="item.wineId">
					<bean:write name="shoppinglistForm" property="wine.bottleSize" filter="true"/>
				</logic:notEmpty>
				<logic:empty name="shoppinglistForm" property="item.wineId">
					<html:text property="wine.bottleSize" styleId="fbottlesize"/>
				</logic:empty>
			</td>
			<td><html:errors property="bottlesize"/>&nbsp;</td>
		</tr>

		<tr class="odd">
			<td><bean:message key="ce.lbl.year"/></td>
			<td>
				<html:text property="item.year" styleId="fyear"/>
			</td>
			<td><html:errors property="year"/>&nbsp;</td>
		</tr>
		<tr class="even">
			<td><bean:message key="ce.lbl.quantity"/></td>
			<td>
				<html:text property="item.quantity" styleId="fquantity"/>
			</td>
			<td><html:errors property="quantity"/>&nbsp;</td>
		</tr>
		<tr class="odd">
			<td><bean:message key="shoppinglist.lbl.winecellar"/></td>
			<td>
				<html:select property="wineCellarId" styleId="fwcid">
					<html:optionsCollection name="wineCellarList" label="name" value="id"/>
				</html:select>
			</td>
			<td>&nbsp;</td>
		</tr>

		<tr class="footer">
			<td>
				<html:link action="/ShoppingListAction.do">
					<bean:message key="lbl.cancel"/>
				</html:link>
			</td>
			<td align="right">
				<html:submit styleId="fSubmit">
					<bean:message key="shoppinglist.lbl.checkin"/>
				</html:submit>
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
</html:form>
