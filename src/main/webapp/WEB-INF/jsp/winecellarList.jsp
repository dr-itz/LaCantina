<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<html:form action="/WineCellarAction.do">

<html:hidden property="action" value="list" styleId="pAction"/>
<html:hidden property="wc.id" styleId="pId"/>

<table class="datatable" width="100%" cellspacing="0">
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<col class="fixedtextcolumn"/>
	<tr>
		<th>
			<bean:message key="wc.lbl.name"/>
		</th>
		<th>
			<bean:message key="wc.lbl.capacity"/>
		</th>
		<th>
			<bean:message key="lbl.action"/>
		</th>
	</tr>
	<logic:present name="winecellarList">
	<logic:iterate name="winecellarList" id="item" indexId="idx">
		<tr class="<%= idx % 2 == 0 ? "even" : "odd" %>">
			<td>
				<html:link action="/CellarEntryAction" paramId="ce.winecellarId"
				 paramName="item" paramProperty="id">
				 	<html:param name="action" value="list"/>
					<bean:write name="item" property="name"/>
				</html:link>
			</td>
			<td><bean:write filter="true" name="item" property="capacity"/></td>
			<td>
				<html:link action="/WineCellarAction" paramId="wc.id"
				 paramName="item" paramProperty="id">
				 	<html:param name="action" value="form"/>
				 	<bean:message key="lbl.modify"/>
				</html:link>
			</td>
		</tr>
	</logic:iterate></logic:present>
	<tr class="footer">
		<td colspan="3">
			<html:link action="/WineCellarAction">
				<html:param name="action" value="new"/>
				<bean:message key="lbl.add"/>
			</html:link>
		</td>
	</tr>
</table>
</html:form>
