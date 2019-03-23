<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
    <title>验证码</title>
</head>
<body onload="err();" bgcolor="pink">

<form action="<%=request.getContextPath()%>/CheckServlet" method="post">
    <div align="center">
        <table border="1">
            <tbody>
            <tr>
                <td>用户名</td>
                <td><input type="text" name="user" /></td>
            </tr>
            <tr>
                <td>密码</td>
                <td><input type="text" name="pd" /></td>
            </tr>
            <tr>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="登录" />
                    <input type="reset" value="重置" />
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <div align="center" style="display: none;" id="code">
        <input type="hidden" name="hiddenCode" id="hiddenCodeId" value="${hiddenCode}">
        <input type="text" name="randomCode" />
        <img id="vimg" title="点击更换" onclick="changeCode();" src="servlet/AuthImageServlet"><br />
        <input type="hidden" name="err" id="errId" value="${err}" />
        <span id="span"></span>
    </div>
</form>

</body>
<!-- 引入jquery -->
<script src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript">

    function changeCode() {
        var imgNode = document.getElementById("vimg");
        //Math.random(); 随机数，只是为了每次都请求sevlet，如果每次请求的都是同一个值，那么只会请求一次servlet
        imgNode.src = "servlet/AuthImageServlet?t=" + Math.random();
    }

    function err() {
        var hiddenCodeId = document.getElementById("hiddenCodeId").value;/* 得到隐藏验证码输入框的值 */
        var err = document.getElementById("errId").value;//得到错误的值
        if ("" == hiddenCodeId) {//如果值等于空，就说明是第一次登录
            document.getElementById("code").style.display = "none";//将验证码输入框隐藏起来
        } else if ("1" == hiddenCodeId) {//如果等于1 说明不是第一次

            document.getElementById("code").style.display = "";//将验证码输入框
        }

        if ("0" == err) {//如果err等于0或2，表示用户名或密码错误
            alert("请输入验证码");/*弹出框效果  */
            // $("#span").html("请输入验证码!").css("color", "green");/* 直接在页面打出来 */
        } else if ("1" == err) {//如果err等于1，表示验证码输入错误
            alert("请输入用户名或密码");
            // $("#span").html("请输入用户名或密码").css("color", "green");
        } else if ("2" == err) {//如果err等于1，表示验证码输入错误
            alert("用户名或密码错误");
            // $("#span").html("用户名或密码错误").css("color", "green");
        } else if ("3" == err) {//如果err等于1，表示验证码输入错误
            alert("验证码输入错误");
            // $("#span").html("验证码输入错误").css("color", "green");
        }
    }
</script>
</html>
