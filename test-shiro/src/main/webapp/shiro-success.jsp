<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/2/26
  Time: 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Shiro Success Page</h1>

<shiro:guest>
    游客访问 <a href = "login.jsp"></a>
</shiro:guest>
<br><br>
user 标签：用户已经通过认证\记住我 登录后显示响应的内容
<shiro:user>
    欢迎[<shiro:principal/>]登录
    <a href = "logout">退出</a>
</shiro:user>
<br><br>
authenticated标签：用户身份验证通过，即 Subjec.login 登录成功 不是记住我登录的
<shiro:authenticated>
    用户[<shiro:principal/>] 已身份验证通过
</shiro:authenticated>
<br><br>
notAuthenticated标签：用户未进行身份验证，<即没有调用Subject class="login进行"></即没有调用Subject>登录,包括"记住我"也属于未进行身份验证
<shiro:notAuthenticated>
    未身份验证(包括"记住我")
</shiro:notAuthenticated>
<br><br>

<%--principal 标签：显示用户身份信息，默认调用--%>
<%--Subjec.getPrincipal()获取，即Primary Principal--%>
<%--<shiro_filter:principal property = "username"/>--%>

hasRole标签：如果当前Subject有角色将显示body体内的内容
<shiro:hasRole name = "admin">
    用户[<shiro:principal/>]拥有角色admin
</shiro:hasRole>
<br><br>
hasAnyRoles标签：如果Subject有任意一个角色(或的关系)将显示body体里的内容
<shiro:hasAnyRoles name = "admin,user">
    用户[<shiro:principal/>]拥有角色admin 或者 user
</shiro:hasAnyRoles>
<br><br>
lacksRole:如果当前 Subjec没有角色将显示body体内的内容
<shiro:lacksRole name = "admin">
    用户[<shiro:principal/>]没有角色admin
</shiro:lacksRole>
<br><br>
hashPermission:如果当前Subject有权限将显示body体内容
<shiro:hasPermission name = "user:create">
    用户[<shiro:principal/>] 拥有权限user:create
</shiro:hasPermission>
<br><br>
lacksPermission:如果当前Subject没有权限将显示body体内容
<shiro:lacksPermission name = "org:create">
    用户[<shiro:principal/>] 没有权限org:create
</shiro:lacksPermission>
<br><br>
</body>
</html>
