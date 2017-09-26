<!DOCTYPE>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/commonPage/frontend_meta.jsp"%>
<title>错误页面 <s:property value="exception.message" escape="false" />
</title>
<link rel="stylesheet" href="css/error.css?v=${version}">
</head>

<body>
	<%--@include file="/page/head.jsp" --%>

	<div class="wrap">
		<div class="error_wrap">
			<font color=red><b>对不起，请确认以下信息：</b></br>
			</font>
			<s:property value="errMsg" escape="false"/>

			<s:if test="exception.message=='请先退出登录。'">
				<p>你已登录系统，请先点击退出登录，再行注册</p>
				<a href="front/login!logout">退出登录</a>
				<br />
			</s:if>
			<s:elseif test="exception.message=='请先退出登录哈。'">
				<p>你已登录系统，请先点击退出登录，再行注册</p>
				<a href="m/index!logout">退出登录</a>
				<br />
			</s:elseif>
			<s:else>
				<p>
					<s:property value="exception.message" escape="false" />
				</p>
			</s:else>

			<a href="javascript :;" onClick="javascript :history.back(-1);">返回上一页</a>
		</div>
	</div>

	<%--@include file="/page/foot.jsp" --%>
</body>
</html>