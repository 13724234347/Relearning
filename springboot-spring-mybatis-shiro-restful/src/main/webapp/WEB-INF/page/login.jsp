<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/4
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>login</title>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/login.css">
    <script type="text/javascript">
        $(function(){
            var mark = getQueryString("mark");
            if(mark == 1){
                alert("账号或密码错误");
            }
        });
    </script>
</head>

<body>
<div class="form">
    <div id="landing">登陆</div>
    <div id="registered">注册</div>
    <div class="fix"></div>
    <div id="landing-content">
        <form action="/login/login" method="post">
            <div id="photo"><img src="${pageContext.request.contextPath }/images/photo.jpg"></div>
            <div class="inp"><input type="text" name="username" placeholder="用户名"></div>
            <div class="inp"><input type="password" name="password" placeholder="密码"></div>
            <input type="submit" value="登录" class="login" />
        </form>
    </div>
    <div id="registered-content">
        <div class="inp"><input type="username" name="username" placeholder="请输入用户名"></div>
        <div class="inp"><input type="password" name="password" placeholder="请输入密码"></div>
        <div class="inp"><input type="password" name="password" placeholder="请再次输入密码"></div>
        <div class="inp"><input type="text" name="email" placeholder="电子邮箱"></div>
        <input type="submit" value="注册" class="login" />
    </div>
</div>
<input type="hidden" name="status" value="${status}">
<input type="hidden" name="message" value="${message}">
</body>
<script type="text/javascript">
    $(function(){
        $(".form").slideDown(500);
        $("#landing").addClass("border-btn");
        $("#registered").click(function() {
            $("#landing").removeClass("border-btn");
            $("#landing-content").hide(500);
            $(this).addClass("border-btn");
            $("#registered-content").show(500);

        })

        $("#landing").click(function() {
            $("#registered").removeClass("border-btn");
            $(this).addClass("border-btn");

            $("#landing-content").show(500);
            $("#registered-content").hide(500);
        })
        var message = $("#message").val();
        var status = $("#status").val();
        if (status == "account_prohibit"){
            alert(message);
        } else if (status == "login_error"){
            alert(message);
        } else if(status == "logout_success"){
            alert(message);
        }

    });
</script>
</html>
