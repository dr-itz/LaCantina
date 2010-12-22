<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<tiles:importAttribute name="label" scope="page"/>
<tiles:importAttribute name="col" scope="page"/>

<a class="sortlink" href="javascript:pfSort('${col}')">
	<bean:message key="${label}"/>
</a>
<logic:present name="pcookie">
	<logic:equal name="pcookie" property="sortKey" value="${col}">
		<logic:equal name="pcookie" property="sortDesc" value="true">
			<img src="/LaCantina/img/sort_descending.gif" alt="">
		</logic:equal>
		<logic:notEqual name="pcookie" property="sortDesc" value="true">
			<img src="/LaCantina/img/sort_ascending.gif" alt="">
		</logic:notEqual>
	</logic:equal>
</logic:present>
