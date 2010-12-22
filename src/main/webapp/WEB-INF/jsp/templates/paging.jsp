<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>

<logic:present name="pcookie">
	<html:hidden property="sortKey" styleId="pSortKey"/>
	<html:hidden property="pc"/>
	<logic:notEqual name="pcookie" property="rangeStart" value="1">
		<a href="javascript:pfPrev()"><bean:message key="lbl.prev"/></a>
		&nbsp;&nbsp;
	</logic:notEqual>
	<bean:write name="pcookie" property="rangeStart"/>
	&nbsp;-&nbsp; <bean:write name="pcookie" property="rangeEnd"/>
	&nbsp;/&nbsp;<bean:write name="pcookie" property="totalRows"/>&nbsp;&nbsp;
	<logic:equal name="pcookie" property="hasMoreRows" value="true">
		<a href="javascript:pfNext()"><bean:message key="lbl.next"/></a>
	</logic:equal>
</logic:present>
&nbsp;