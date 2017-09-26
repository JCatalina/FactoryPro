<!DOCTYPE >
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/commonPage/frontend_meta.jsp" %>
<meta name="viewport" content="width=device-width"/>
<title>没有权限 <s:property value="exception.message" escape="false"/></title>
<link rel="stylesheet" href="css/error.css?v=${version}">
</head>

<body>
<%--@include file="/page/head.jsp" --%>

  <div class="wrap">
    <div class="error_wrap">
    <font color=red><b>对不起，你没有操作权限 </b></font><br>  
		<s:property value="exception.message" escape="false"/>  
		<s:property value="errMsg" escape="false"/>  
	<br>
	
    </div>
  </div>

<%--@include file="/page/foot.jsp" --%>
</body>
</html>
