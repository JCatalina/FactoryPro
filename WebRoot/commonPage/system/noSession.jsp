<!DOCTYPE>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/commonPage/frontend_meta.jsp" %>
<meta name="viewport" content="width=device-width"/>
<title>没有登录<s:property value="exception.message" escape="false"/></title>
<link rel="stylesheet" href="css/error.css?v=${version}">
</head>

<body>
<%--@include file="/page/head.jsp" --%>

  <div class="wrap">
    <div class="error_wrap">
    <font color=red><b>对不起，请确认以下信息：</b></font><br>  
		<s:property value="exception.message" escape="false"/>  
	<br>
	
    </div>
  </div>

<%--@include file="/page/foot.jsp" --%>
</body>
</html>
