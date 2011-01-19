<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:empty name="checkinForm">
	<jsp:include page="/WEB-INF/jsp/shoppingItem.jsp"/>
</logic:empty>
<logic:notEmpty name="checkinForm">
	<jsp:include page="/WEB-INF/jsp/shoppingCheckin.jsp"/>
</logic:notEmpty>