<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %><!-- 导入springmvc的form标签库 -->

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改订单</title>
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
<body >
<%--引入头部<@_top.top 3/>--%>
		<jsp:include page="../common/config/top.jsp"></jsp:include>
		<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
			<div class="row">
				<%--引入左侧菜单--%>
				<shiro:hasAnyRoles name='888888,100004'>  
					<div id="one" class="col-md-2">
						<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
						 <shiro:hasPermission name="/order/orderCRUD/list.shtml">
						  <li class="active dropdown">
						       <a href="<%=basePath%>/order/orderCRUD/list.shtml">
						    	 <i class="glyphicon glyphicon-chevron-right"></i>订单列表
						      </a>
						  </li>
						  </shiro:hasPermission>
						 <shiro:hasPermission name="/role/index.shtml">
						  <li class="dropdown">
						      <a href="<%=basePath%>/role/index.shtml">
						    	 <i class="glyphicon glyphicon-chevron-right"></i>角色列表
						      </a>
						  </li>
						  </shiro:hasPermission>
						 <shiro:hasPermission name="/role/allocation.shtml">
						  <li class="dropdown">
						      <a href="<%=basePath%>/role/allocation.shtml" title="角色分配（这是个JSP页面）">
						    	 <i class="glyphicon glyphicon-chevron-right"></i>角色分配（这是个JSP页面）
						      </a>
						  </li>
						  </shiro:hasPermission>
						  <shiro:hasPermission name="/permission/index.shtml">
						  <li class=" dropdown">
						      <a href="<%=basePath%>/permission/index.shtml">
						    	 <i class="glyphicon glyphicon-chevron-right"></i>权限列表
						      </a>
						  </li>
						  </shiro:hasPermission>
						  <shiro:hasPermission name="/permission/allocation.shtml">
						  <li class="  dropdown">
						      <a href="<%=basePath%>/permission/allocation.shtml">
						    	 <i class="glyphicon glyphicon-chevron-right"></i>权限分配
						      </a>
						  </li>
						  </shiro:hasPermission>
						</ul>
					</div>
					
				</shiro:hasAnyRoles>  
<div align="center" >
<br>
<br>
<form:form id="form" action="${pageContext.request.contextPath}/order/orderCRUD.shtml" method="POST" modelAttribute="order">
	<input type="hidden" name="_method" value="PUT">
	<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="orderbh" name="orderbh" type="hidden" value="${page.t.orderbh}">
	<form:input path="id" type="hidden"/>
	<p>订单编号：<form:input  path="orderbh" readonly="true" /></p><br><br>
	<p>订单数量：<form:input  path="orderNumber" placeholder="请输入订单编号"/></p><br><br>
	<p>订单价格：<form:input path="orderPrice" placeholder="请输入订单价格"/></p><br><br>
	<p>订单地址：<form:input  path="orderAddr" placeholder="请输入订单发送地址"/></p><br><br>
	<p>处理人员：<form:input  path="orderEmpnoName" placeholder="请输入订单处理人员"/></p><br><br>
	<p>收件人员：<form:input  path="recipient"  placeholder="请输入订单收件人员"/></p><br><br>
	<button id="submits"  type="submit">立即提交</button>
	<button type="reset" id="resets">重置</button>
	<button type="button"onClick="javascript :history.back(-1);">返回</button>
	</form:form>
</div>
</body>
</html>