<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://www.shiro.org/jsp" prefix="shiro" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>

<!-- 这里引用shiro的标签库 -->
<!DOCTYPE html>
<html>
<head>
    <title>角色表</title>
    <style>
        .window {
            width: 500px;
            background-color: gray;
            position: absolute;
            padding: 5px;
            margin: 10px;
            display: none;
            left: 40%;
            top: 0px
        }

        #content {
            height: 400px;
            background-color: white;
            font-size: 16px;
            overflow: auto;
        }

        .title {
            padding: 2px;
            color: white;
            font-size: 15px;
        }

        .title img {
            float: right;
        }
    </style>
</head>
<link rel="stylesheet" href="<%=basePath%>/css/style.css" media="screen" type="text/css"/>
<link rel="stylesheet" href="<%=basePath%>/css/paging.css"/>
<script type="text/javascript" src="<%=basePath%>/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/paging.js"></script>
<script type="text/javascript">
</script>
<body style="background-color:#FFEBCD;">
<div align="center">
    <form action="" id="form">
        <input id="pageSize" name="pageSize" type="hidden" value="5"/><!-- 默认每页3条 -->
        <input id="pageNum" name="pageNum" type="hidden" value="1"/><!-- 默认第一页 -->
        <input id="order" name="order" type="hidden" value="0"/><!-- 默认排序倒序 -->
        <p>
            <shiro:authorize buttonUrl="/login/jumpUser" buttonMethod="GET">
                <a href="<%=basePath%>/login/jumpUser">返回用户页面</a>
            </shiro:authorize>

            <shiro:authorize buttonUrl="/role/add" buttonMethod="GET">
                <a href="<%=basePath%>/role/add">添加</a>
            </shiro:authorize>

            <shiro:authorize buttonUrl="/role/role" buttonMethod="GET">
            <button type="button" id="asc">id顺序查询</button>
            <button type="button" id="desc">id倒序查询</button>
            </shiro:authorize>

            <a href="<%=basePath%>/logout">登出</a>

            <shiro:authorize buttonUrl="/login/jumpPermission" buttonMethod="GET">
                <a href="<%=basePath%>/login/jumpPermission">进入权限页面</a>
            </shiro:authorize>
        </p>
    </form>
    <shiro:authorize buttonUrl="/role/role" buttonMethod="GET">
        <table id="myTable" border="1" cellpadding="10" cellspacing="0" style="border-collapse: collapse;">
            <!-- 表头 -->
            <thead>
            <tr align="center">
                <td>id</td>
                <td>角色名字</td>
                <td>该角色拥有权限</td>
                <td>操作</td>
            </tr>
            </thead>
            <!-- 主体 -->
            <tbody id="tables">
            </tbody>
            <!-- 页脚 -->
            <tfoot>
            </tfoot>
        </table>
        <%--赋权限弹出层--%>
        <div class="window" id="center">
            <div id="title" class="title"><img src="<%=basePath%>/images/off.jpg" alt="关闭"/>赋权
            </div>
            <div id="content">
            </div>
            <div align="center">
                <shiro:authorize buttonUrl="/role/addPermission" buttonMethod="POST">
                <button id="addPermission" onclick="addPermission()">提交</button>
                </shiro:authorize>
            </div>
        </div>

        <div class="pagination">
            <ul id="paging">
            </ul>
        </div>
    </shiro:authorize>
</div>
</body>
<script type="text/javascript">
    $(function () {
        tableData();//调用方法查询数据
        $("#searchs").click(function () {//当点击搜索按钮就进行搜索
            $("#pageNum").val(1);//默认第一页
            tableData();//查询出搜索的数据
        });

        //id顺序或id倒叙
        $("#asc,#desc").click(function () {
            var id = $(this).attr("id");
            /* var pageSize = $("#pageSize").val();
            var search = $("#search").val(); */
            if (id == "asc") {
                var order = $("#order").val();
                if (order != "0") {
                    $("#order").val(0);//0为顺序
                    tableData();
                }
            } else {
                var order = $("#order").val();
                if (order != "1") {
                    $("#order").val(1);//1为倒序
                    tableData();
                }
            }
        });

    });

    //跳转修改页面
    function update(data) {
        $("#form").attr("action", "<%=basePath%>/role/role/" + data).submit();//修改action的值并提交form表单
    }


    //删除用户
    function deletes(data) {
        var id = $(data).parent("td").siblings("td:first").text();
        $.ajax({//用ajax静态访问servlet
            type: "POST",
            url: "<%=basePath%>/role/role/" + id,
            async: false,//设置为同步请求
            data: {
                "_method": "DELETE"
            },
            success: function (data) {//成功则调用函数tableData添加数据到页面
                if ($("#pageNum").val() == $("#pageCount").text() && Math.ceil(($("#totalCount").text() - 1) / $("#pageSize").val()) != $("#pageNum").val()) {//	向上取整（如果总记录数-1除以当前条数）   != 需要跳转的页面
                    $("#pageNum").val($("#pageNum").val() - 1);
                }
                tableData();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("错误: " + XMLHttpRequest.status);
            }
        });
    }

    function tableData() {
        $("#tables,#paging").empty();//清空表的值和页码数
        var form = $('form').serializeArray();//获取表单所有值的json格式
        $.ajax({//用ajax静态访问servlet
            type: "GET",
            url: "<%=basePath%>/role/role",
            dataType: "json",
            async: false,//设置为同步请求
            data: form,
            success: function (data) {//成功则调用函数tableData添加数据到页面
                if (data.dataList == "") {
                    if ($("#wu").text() == "") {//如果无数据则满足条件(也就是只报一次无数据)
                        $("#myTable").after("<h2 id='wu'>无数据</h2>");
                    }
                } else {
                    if ($("#wu").text() == "无数据") {//如果无数据则满足条件(也就是只报一次无数据)
                        $("#wu").remove();
                    }
                    for (var i in data.dataList) {//循环出数据的数据添加到table表中
                        var permission = "";
                        if (data.dataList[i].role.permissionSet == null || data.dataList[i].role.permissionSet == "") {
                            permission = "该角色没有任何权限!";
                        } else {
                            for (var s = 0; s < data.dataList[i].role.permissionSet.length; s++) {//循环出数据的数据添加到table表中
                                permission += data.dataList[i].role.permissionSet[s].urlDescription;
                                if ((s + 1) != data.dataList[i].role.permissionSet.length) {
                                    permission += ",";
                                }
                            }
                            ;
                        }
                        $("#tables").append("<tr>"
                            + "<td>" + data.dataList[i].role.id + "</td>"
                            + "<td>" + data.dataList[i].role.roleName + "</td>"
                            + "<td>" + permission + "</td>"
                            + "<td name='myButton'>"
                            + "<shiro:authorize buttonUrl='/role/role/*'  buttonMethod='GET'>"
                            + "<input type='button' name='update' class='updatebutton' value='修改' onclick='update(" + data.dataList[i].role.id + ");'/>"
                            + "</shiro:authorize>"
                            + "<shiro:authorize buttonUrl='/role/role/*'  buttonMethod='DELETE'>"
                            + "<input type='button' onclick='deletes(this)' style='background-color:#FA5858;border: 1px solid #FA5858' class='updatebutton' value='删除' />"
                            + "</shiro:authorize>"
                            + "<shiro:authorize buttonUrl='/role/permission_echo'  buttonMethod='GET'>"
                            + "<input type='button' onclick='empowerment(" + data.dataList[i].role.id + ")' style='background-color:#FA5858;border: 1px solid #FA5858' class='updatebutton' value='赋权' />"
                            + "</shiro:authorize>"
                            + "</td>"
                            + "</tr>"
                        )
                        ;
                    }
                    //参数依次为	总页数，当前页数，总条数，当前大小，页码数，分页所放的ul的ID的值 ，搜索框的值
                    myFunction(data.pageCount, data.pageNum, data.totalCount, data.pageSize, 3, "paging", data.search);

                    var pageNum = $("#pageNum").val();//当点击按钮页数，id为pageNumber就会获得点击的页数，用变量接住页数的值
                    $("ul button").each(function () {//匹配ul下的button的值
                        if (pageNum == $(this).text()) {//当页数和该对象的文本值相等时
                            $(this).attr("style", "color: #2eccfa;background: #dddddd;border: 1px solid #dddddd;");//就改变该按钮的样式
                        }
                    });
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("错误: " + XMLHttpRequest.status);
            }
        })
        ;
    }

    function empowerment(id) {
        popCenterWindow();
        permission_echo(id);
    };

    function permission_echo(id) {
        $.ajax({
            type: "get",  // 请求方式(post或get)
            async: false,  //默认true(异步请求),设置为false(同步请求)
            url: "<%=basePath%>/role/permission_echo",
            scriptCharset: 'utf-8',
            data: {
                "id": id,
            },
            dataType: 'json',
            success: function (permissionList) {
                $("#content").empty();
                var permission = "";
                for (var i in permissionList) {
                    if (permissionList[i].sentence == "1") {
                        permission += "<input type='checkbox' value=" + permissionList[i].id + " name='permission' checked='checked'>" + permissionList[i].urlDescription + ",<br/>";
                    } else if (permissionList[i].sentence == "0") {
                        permission += "<input type='checkbox' value=" + permissionList[i].id + " name='permission'>" + permissionList[i].urlDescription + ",<br/>";
                    }
                }
                $("#addPermission").val(id);
                $("#content").append(permission);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpReqest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    }


    function addPermission() {
        var obj = document.getElementsByName("permission");
        var check_val = [];
        var id = document.getElementById("addPermission").value;
        for (k in obj) {
            if (obj[k].checked)
                check_val.push(obj[k].value);
        }
        $.ajax({
            type: "post",  // 请求方式(post或get)
            async: false,  //默认true(异步请求),设置为false(同步请求)
            url: "<%=basePath%>/role/addPermission",
            scriptCharset: 'utf-8',
            data: {
                "roleId": id,
                "permissionId": check_val,
            },
            traditional: true,//这里设置为true
            success: function (permission) {
                if (permission == "success") {
                    close()
                    tableData();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    }

    //关闭弹出层
    function closeWindow() {
        $(".title img").click(function () {
            $(this).parent().parent().hide("slow");
        });
    }

    //自动点击关闭弹出层的图片
    function close() {
        $(".title img").each(function(){
            this.click();
        });
    }


    //定义弹出居中窗口的方法
    function popCenterWindow() {
        specifications();
        //计算弹出窗口的左上角Y的偏移量
        var popY = (windowHeight - popHeight) / 2;
        var popX = (windowWidth - popWidth) / 2;
        //alert('jihua.cnblogs.com');
        //设定窗口的位置
        $("#center").css("top", popY).css("left", popX).slideToggle("slow");
        closeWindow();
    }

    //获取窗口的高度
    var windowHeight;
    //获取窗口的宽度
    var windowWidth;
    //获取弹窗的宽度
    var popWidth;
    //获取弹窗高度
    var popHeight;

    function specifications() {
        windowHeight = $(window).height();
        windowWidth = $(window).width();
        popHeight = $(".window").height();
        popWidth = $(".window").width();
    }

</script>
</html>