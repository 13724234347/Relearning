<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="security" uri="/security" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>  
<html>
<head>
<title>订单列表</title>
</head>

<link   rel="icon" href="${basePath}/favicon.ico" type="image/x-icon" />
		<link   rel="shortcut icon" href="${basePath}/favicon.ico" />
		<link href="<%=request.getContextPath()%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css?${_v}" rel="stylesheet"/>
		<link href="<%=request.getContextPath()%>/css/common/base.css?${_v}" rel="stylesheet"/>
		<script  src="<%=request.getContextPath()%>/js/common/jquery/jquery1.8.3.min.js"></script>
		<script  src="<%=request.getContextPath()%>/js/common/layer/layer.js"></script>
		<script  src="<%=request.getContextPath()%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<script  src="<%=request.getContextPath()%>/js/shiro.demo.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/paging.js"></script>

 <script type="text/javascript">
$(function(){
	var page = "${page}";//后台用Map返回的值（page返回的值有List<T>）
	if(page != ""){//不等于空满足条件
		$("#pageNum").val("${page.pageNum}");
		$("#pageSize").val("${page.pageSize}");
		tableData();
	}
		tableData();
		$("#searchs").click(function(){//当点击搜索按钮就进行搜索
			$("#pageNum").val(1);//默认第一页
			tableData();//查询出搜索的数据
		});
});
<!--添加订单-->


function setInput(pageNum, pageSize){
	
	if(pageSize == -1){//如果等于-1就表示
		//如果一共100页
		// 1 ... 79 80 81...100
		var pageBar = $("#pageBar").val();
		$("#pageNum").val(pageNum);
		$("#pageSize").val(pageBar);
		
		tableData();
	}
	else if(pageNum != ""){//如果等于空则不执行
		$("#pageNum").val(pageNum);
		$("#pageSize").val(pageSize);
		tableData();
	}
} 


function tableData(){
	var form = $('form').serializeArray();//获取表单所有值的json格式
	$.ajax({//用ajax静态访问servlet
		type : "GET",
		url : "${pageContext.request.contextPath }/order/orderCRUD.shtml",
		dataType:"json",
		async : false,//设置为同步请求
		data : form,
		success : function(data) {//成功则调用函数tableData添加数据到页面
			$("#tables,#paging").empty();//清空表的值和页码数
			if(data.dataList != "" ){
				if($("#wu").text() == "无数据"){//如果无数据则满足条件(也就是只报一次无数据)
					$("#wu").remove();
				} 
			for ( var i in data.dataList) {
				$("#tables").append("<tr>"
						+"<td><input type='checkbox' id='checkAll'/></td>"
						+"<td>"+data.dataList[i].id+"</td>"
						+"<td>"+data.dataList[i].orderbh+"</td>"
						+"<td>"+data.dataList[i].orderType+"</td>"
						+"<td>"+data.dataList[i].orderNumber+"</td>"
						+"<td>"+data.dataList[i].orderPrice+"</td>"
						+"<td>"+data.dataList[i].orderAddr+"</td>"
						+"<td>"+data.dataList[i].orderEmpnoName+"</td>"
						+"<td>"+data.dataList[i].showTime+"</td>"
						+"<td>"+data.dataList[i].recipient+"</td>"
						+"<td>"
						+"<security:hasAnyPermissionTag name='/order/orderCRUD.shtmlput'>"
						+"<input type='button'  value='修改' onclick='update("+data.dataList[i].id+");'/>"
						+"</security:hasAnyPermissionTag>"
						+"<security:hasAnyPermissionTag name='/order/orderCRUD.shtmldelete'>"
						+"<input type='button' onclick='deletes(this)' value='删除' />"
						+"</security:hasAnyPermissionTag>"
						+"</td>"
						+"</tr>");
			}
			//参数依次为	总页数，当前页数，总条数，当前大小，页码数，分页所放的ul的ID的值 ，搜索框的值
			myFunction(data.pageCount, data.pageNum, data.totalCount, data.pageSize, 3, "paging", data.search);
			
			var pageNum = $("#pageNum").val();//当点击按钮页数，id为pageNumber就会获得点击的页数，用变量接住页数的值
			$("ul button").each(function(){//匹配ul下的button的值
				if(pageNum == $(this).text()){//当页数和该对象的文本值相等时
					$(this).attr("style","color: #2eccfa;background: #dddddd;border: 1px solid #dddddd;");//就改变该按钮的样式
				}//（需要当前页不能点击）
			});
		}else{
			if($("#wu").text() == ""){//如果无数据则满足条件(也就是只报一次无数据)
				$("#myTable").after("<h2 id='wu'>无数据</h2>");
			} 
		}
	},
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		 alert("错误: "+XMLHttpRequest.status);
	}
});
}
//跳转修改
function update(id){
	$("#formId").attr("action","${pageContext.request.contextPath }/order/orderCRUD/"+id+".shtml").submit();//修改action的值并提交form表单
}
//删除用户
function deletes(data){
	var oid = $(data).parent("td").siblings("td:eq(1)").text();
	$.ajax({//用ajax静态访问servlet
		type : "POST",
		url : "${pageContext.request.contextPath }/order/orderCRUD.shtml",
		async : false,//设置为同步请求
		data : {id:oid,
			"_method":"DELETE"
		},
		success : function(data) {//成功则调用函数tableData添加数据到页面
			if($("#pageNum").val() == $("#pageCount").text() && Math.ceil(($("#totalCount").text()-1)/$("#pageSize").val()) != $("#pageNum").val()){//	向上取整（如果总记录数-1除以当前条数）   != 需要跳转的页面
				$("#pageNum").val($("#pageNum").val()-1);
			}
			tableData();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			 alert("错误: "+XMLHttpRequest.status);
		}
	}); 
}
</script>
<body data-target="#one" data-spy="scroll">
	<%--引入头部<@_top.top 3/>--%>
		<jsp:include page="../common/config/top.jsp"></jsp:include>
		<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
			<div class="row">
				<%--引入左侧菜单--%>
				<shiro:hasAnyRoles name='888888,100004'>  
					<div id="one" class="col-md-2">
						<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
						 <security:hasAnyPermissionTag name="/order/orderCRUD.shtmlget">
						  <li class="active dropdown">
						       <a href="<%=basePath%>/order/orderCRUD/list.shtml">
						    	 <i class="glyphicon glyphicon-chevron-right"></i>订单列表
						      </a>
						  </li>
						  </security:hasAnyPermissionTag>
						 <shiro:hasPermission name="/role/index.shtmlno">
						  <li class="dropdown">
						      <a href="<%=basePath%>/role/index.shtml">
						    	 <i class="glyphicon glyphicon-chevron-right"></i>角色列表
						      </a>
						  </li>
						  </shiro:hasPermission>
						 <shiro:hasPermission name="/role/allocation.shtmlno">
						  <li class="dropdown">
						      <a href="<%=basePath%>/role/allocation.shtml" title="角色分配（这是个JSP页面）">
						    	 <i class="glyphicon glyphicon-chevron-right"></i>角色分配（这是个JSP页面）
						      </a>
						  </li>
						  </shiro:hasPermission>
						  <shiro:hasPermission name="/permission/index.shtmlno">
						  <li class=" dropdown">
						      <a href="<%=basePath%>/permission/index.shtml">
						    	 <i class="glyphicon glyphicon-chevron-right"></i>权限列表
						      </a>
						  </li>
						  </shiro:hasPermission>
						  <shiro:hasPermission name="/permission/allocation.shtmlno">
						  <li class="  dropdown">
						      <a href="<%=basePath%>/permission/allocation.shtml">
						    	 <i class="glyphicon glyphicon-chevron-right"></i>权限分配
						      </a>
						  </li>
						  </shiro:hasPermission>
						</ul>
					</div>
				</shiro:hasAnyRoles>  
				
			
				
				
				
				
				
				<div class="col-md-10">
					<h2>订单列表</h2>
					<hr>
					<form method="Get" action="" id="formId" class="form-inline">
						<input id="pageSize" name="pageSize" type="hidden" value="3"/><!-- 默认每页3条 -->
						<input id="pageNum" name="pageNum" type="hidden" value="1"/><!-- 默认第一页 -->
						<input id="order" name="order" type="hidden" value="1"/><!-- 默认排序倒序 -->
						<div>
					      <div class="form-group">
					        <input type="text" class="form-control" style="width: 300px;"  
					        		id="orderbhId"	name="orderbh"  placeholder="输入订单类型 / 订单号">
					      </div>
					     <span class=""> <%--pull-right --%>
				         	<button type="button"  id="searchs" class="btn btn-primary" >查询</button>
				         </span>    
				        </div>
				        </form>
				        	<security:hasAnyPermissionTag name="/order/orderCRUD.shtmlpost">
				       			<a href="${pageContext.request.contextPath}/order/orderCRUD/add.shtml">
					         		<button type="button" style="background:#449d44;border-color:#449d44;" class="btn btn-primary">添加订单</button>
					         	</a>
					        </security:hasAnyPermissionTag>
					<hr>		
					<table class="table table-bordered" border="1" width="100%">
						<tr>
							<th ><input type="checkbox" id="checkAll"/></th>
							<th >ID</th>
							<th>编码</th>
							<th>类型</th>
							<th>数量</th>
							<th>价格</th>
							<th>地址</th>
							<th>处理人员</th>
							<th>订单时间</th>
							<th>收件人</th>
<security:hasAnyPermissionTag name="/order/orderCRUD.shtmlput`@~@`/order/orderCRUD.shtmldelete"><th>操作</th></security:hasAnyPermissionTag>
						</tr>
						<tr>
							<tbody id="tables">
						</tr>
					</table>
				</div>
			</div>
			</div>
			<div class="pagination">
			<ul id="paging">
			</ul>
		</div>
	<hr/>
</body>
</html>
