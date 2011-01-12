<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<html:form action="/WineAction.do">

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
				<tiles:put name="label" value="wine.lbl.country"/>
				<tiles:put name="col" value="country"/>
			</tiles:insert>
		</th>
		<th>
			<tiles:insert definition="sortHeader">
				<tiles:put name="label" value="wine.lbl.region"/>
				<tiles:put name="col" value="region"/>
			</tiles:insert>
		</th>
		<th>
			<bean:message key="lbl.action"/>
		</th>
	</tr>
	<logic:present name="wineList">
	<logic:iterate name="wineList" id="item" indexId="idx">
		<tr class="<%= idx % 2 == 0 ? "even" : "odd" %>">
			<td>
				<html:link action="/WineAction" paramId="wine.id"
				 paramName="item" paramProperty="id">
				 	<html:param name="action" value="form"/>
					<bean:write name="item" property="name"/>
				</html:link>
			</td>
			<td><bean:write filter="true" name="item" property="producer"/></td>
			<td><bean:write filter="true" name="item" property="country"/></td>
			<td><bean:write filter="true" name="item" property="region"/></td>
			<td>
				<html:link action="/ShoppingListAction" paramId="refId"
				 paramName="item" paramProperty="id">
				 	<html:param name="action" value="fromwine"/>
				 	<bean:message key="shoppinglist.lbl.addshop"/>
				</html:link>
			</td>
		</tr>
	</logic:iterate></logic:present>
	<tr class="footer">
		<td colspan="2">
			<html:link action="/WineAction">
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
