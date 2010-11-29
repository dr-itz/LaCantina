<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<html>
	<head>
		<title>Hello World</title>
	</head>
<body>
	<h1><bean:write name="helloWorldForm" property="message" /></h1>
</body>
</html>

