<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/5
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 导入springmvc的form标签库 -->
<%@ taglib uri="http://www.shiro.org/jsp" prefix="shiro" %>
<html>
<head>
    <title>用户添加 Or 修改页面</title>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/paging.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/login.css">
</head>
<body style="background-color:#FFEBCD;">
<form:form method="POST" id="forms" action="${pageContext.request.contextPath }/user/user" modelAttribute="user">
    <br/><br/>
    <br/><br/>
    <div align="center">
        <form:hidden path="id"/>
        <!-- 显示给用户的数据 -->
        <div class="inp"><form:input path="username" placeholder="请输入用户名"/></div>
        <div class="inp"><form:input path="email" placeholder="请输入邮箱"/></div>
        <div class="inp"><form:input path="password" placeholder="请输入密码"/></div>
        <div class="inp">
            <form:select path="status" items="${status}">
            </form:select>
        </div>
        <c:choose>
            <c:when test="${user.id != null && user.id != '' }">
                <input type="hidden" name="_method" value="PUT">
                <!-- 保存后需要回显到查询的数据 -->
                <input id="pageNum " name="pageNum" type="hidden" value="${page.pageNum}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <input id="username" name="user.username" type="hidden" value="${page.t.user.username}">
                <input id="email" name="user.email" type="hidden" value="${page.t.user.email}">
            </c:when>
        </c:choose>
            <shiro:authorize buttonUrl="/user/user" buttonMethod="PUT">
        <button id="submits" type="submit" class="login">立即提交</button>
            </shiro:authorize>
        <button type="reset" id="resets" class="login">重置</button>
        <button type="button" onClick="javascript :history.back(-1);" class="login">返回</button>
    </div>
</form:form>
</body>
</html>
