<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>
用户名：<%= session.getAttribute("autoLoginName")%>
<a href="customer/logout">退出</a>
</body>
</html>
