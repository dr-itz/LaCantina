<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<html:form action="/CellarEntryAction.do">

<html:hidden property="action" value="list" styleId="pAction"/>
<html:hidden property="winecellarId"/>

<table class="datatable" width="100%" cellspacing="0">
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<tr>
		<th>
			<bean:message key="wine.lbl.name"/>
		</th>
		<th>
			<bean:message key="wine.lbl.producer"/>
		</th>
		<th>
			<bean:message key="wine.lbl.country"/>
		</th>
		<th>
			<bean:message key="wine.lbl.region"/>
		</th>
		<th>
			<bean:message key="wine.lbl.year"/>
		</th>
		<th>
			<bean:message key="wine.lbl.quantity"/>
		</th>
		<th>
			<bean:message key="lbl.action"/>
		</th>
	</tr>
	<logic:present name="cellarentryList">
	<logic:iterate name="cellarentryList" id="item" indexId="idx">
		<tr class="<%= idx % 2 == 0 ? "even" : "odd" %>">
			<td>
				<bean:write name="item" property="wine.name"/>
			</td>
			<td><bean:write filter="true" name="item" property="wine.producer"/></td>
			<td><bean:write filter="true" name="item" property="wine.country"/></td>
			<td><bean:write filter="true" name="item" property="wine.region"/></td>
			<td><bean:write filter="true" name="item" property="year"/></td>
			<td><bean:write filter="true" name="item" property="quantity"/></td>
			<td>&nbsp;</td>
		</tr>
	</logic:iterate></logic:present>
	<tr class="footer">
		<td colspan="4">
			<html:link action="/CellarEntryAction">
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
