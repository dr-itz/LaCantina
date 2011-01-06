<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<html:form action="/ShoppingListAction.do">

<html:hidden property="action" value="list" styleId="pAction"/>
<html:hidden property="wine.id" styleId="pId"/>

<table class="datatable" width="100%" cellspacing="0">
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<tr>
		<th>
			<tiles:insert definition="sortHeader">
				<tiles:put name="label" value="wine.lbl.name"/>
				<tiles:put name="col" value="name"/>
			</tiles:insert>
		</th>
		<th>
			<tiles:insert definition="sortHeader">
				<tiles:put name="label" value="wine.lbl.producer"/>
				<tiles:put name="col" value="producer"/>
			</tiles:insert>
		</th>
		<th>
			<tiles:insert definition="sortHeader">
				<tiles:put name="label" value="wine.lbl.year"/>
				<tiles:put name="col" value="year"/>
			</tiles:insert>
		</th>
		<th>
			<tiles:insert definition="sortHeader">
				<tiles:put name="label" value="wine.lbl.quantity"/>
				<tiles:put name="col" value="quantity"/>
			</tiles:insert>
		</th>
		<th>
			<tiles:insert definition="sortHeader">
				<tiles:put name="label" value="shoppinglist.lbl.store"/>
				<tiles:put name="col" value="store"/>
			</tiles:insert>
		</th>
	</tr>
	<logic:present name="shoppingList">
	<logic:iterate name="shoppingList" id="item" indexId="idx">
		<tr class="<%= idx % 2 == 0 ? "even" : "odd" %>">
			<td>
				<html:link action="/ShoppingListAction.do" paramId="wine.id"
				 paramName="item" paramProperty="id">
				 	<html:param name="action" value="form"/>
					<bean:write name="item" property="name"/>
				</html:link>
			</td>
			<td><bean:write filter="true" name="item" property="producer"/></td>
			<td><bean:write filter="true" name="item" property="year"/></td>
			<td><bean:write filter="true" name="item" property="quantity"/></td>
			<td><bean:write filter="true" name="item" property="store"/></td>
		</tr>
	</logic:iterate></logic:present>
	<tr class="footer">
		<td colspan="2">
			<html:link action="/ShoppingListAction.do">
				<html:param name="action" value="new"/>
				<bean:message key="lbl.add"/>
			</html:link>
		</td>
		<td colspan="3" align="right">
			<tiles:insert definition="paging"/>
		</td>
	</tr>
</table>
</html:form>
