<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<html:form action="/CellarEntryAction.do">

<html:hidden property="action" value="list" styleId="pAction"/>
<html:hidden property="listType" value="rating" styleId="pListType"/>
<html:hidden property="ce.winecellarId"/>

<table class="datatable" width="100%" cellspacing="0">
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
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
			<tiles:insert definition="sortHeader">
				<tiles:put name="label" value="wine.lbl.year"/>
				<tiles:put name="col" value="year"/>
			</tiles:insert>
		</th>
		<th>
			<tiles:insert definition="sortHeader">
				<tiles:put name="label" value="wine.lbl.ratingPoints"/>
				<tiles:put name="col" value="ratingPoints"/>
			</tiles:insert>
		</th>
		<th>
			<bean:message key="wine.lbl.ratingText"/>
		</th>
	</tr>
	<logic:present name="wineratingList">
	<logic:iterate name="wineratingList" id="item" indexId="idx">
		<tr class="<%= idx % 2 == 0 ? "even" : "odd" %>">
			<td>
				<bean:write name="item" property="wine.name"/>
			</td>
			<td><bean:write filter="true" name="item" property="wine.producer"/></td>
			<td><bean:write filter="true" name="item" property="wine.country"/></td>
			<td><bean:write filter="true" name="item" property="wine.region"/></td>
			<td><bean:write filter="true" name="item" property="year"/></td>
			<td><bean:write filter="true" name="item" property="ratingPoints"/></td>
			<td><bean:write filter="true" name="item" property="ratingText"/></td>
		</tr>
	</logic:iterate></logic:present>
	<tr class="footer">
		<td colspan="7" align="right">
			<tiles:insert definition="paging"/>
		</td>
	</tr>
</table>
</html:form>
