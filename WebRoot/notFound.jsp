<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title>Error 404 (Not Found)!!!</title>
<style>
* {
	margin: 0;
	padding: 0
}

html,code {
	font: 15px/22px arial, sans-serif
}

html {
	background: #fff;
	color: #222;
	padding: 15px
}

body {
	margin: 7% auto 0;
	max-width: 390px;
	min-height: 180px;
	padding: 30px 0 15px
}

*>body {
	p {margin: 11px 0 22px;
	overflow: hidden
}

ins {
	color: #777;
	text-decoration: none
}

a img {
	border: 0
}

@media screen and (max-width:772px) {
	body {
		background: none;
		margin-top: 0;
		max-width: none;
		padding-right: 0
	}
}
</style>
<a href= />返回首页
</a>
<p>
	<b>404 error.</b>
</p>
<p>
	<ins>页面找不到.........</ins>
</p>

<!-- <p>The requested URL <code>/404.html</code> was not found on this server.  <ins>contact us………….</ins> -->
</html>
