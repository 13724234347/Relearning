<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/2/26
  Time: 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shiro Login Page</title>
</head>
<body>
<h1>Shiro Login Page</h1>
<div class="form">
    <form action="<%=request.getContextPath() %>/login" method="post">
        Username: <input type="text" name="username"/><br/>
        Password: <input type="password" name="password"/><br/>
        <input type="submit" value="Submit"/>
    </form>
</div>
</body>
</html>
