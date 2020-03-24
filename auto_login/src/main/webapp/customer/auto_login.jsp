<%--
  Created by IntelliJ IDEA.
  User: 陈志峰
  Date: 2020/2/15
  Time: 8:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <span style="color: red">
        <%= request.getAttribute("loginMsg")%>
    </span>
<form action="../customer/auto_login" method="post">
    用户名：<input type="text" name="username"><br>
    密码：<input type="password" name="password"><br>
    <input type="checkbox" name="autologin" value="ok">记住密码<br>
    <input type="submit" value="登录">


</form>
</body>
</html>
