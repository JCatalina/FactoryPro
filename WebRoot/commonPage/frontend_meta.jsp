<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String basePath3 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<base href="<%=basePath %>"/>
<meta name="viewport" content="width=device-width"/>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<link href="css/AdminStyle.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<link rel="stylesheet" href="js/jquery-easyui-1.3.2/themes/default/easyui.css">
<link rel="stylesheet" href="js/jquery-easyui-1.3.2/themes/icon.css">
<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>

<%-- <script src="js/json2.js"></script> --%>
<script src="js/My97DatePicker/WdatePicker.js"></script>

<%@taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

