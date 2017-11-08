<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>系统后台</title>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath %>"/>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<link rel="stylesheet" href="js/jquery-easyui-1.3.2/themes/default/easyui.css"/>
<link rel="stylesheet" href="js/jquery-easyui-1.3.2/themes/icon.css"/>
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
<%@taglib uri="/struts-tags" prefix="s" %>
<script>
var GL_tree;
	GL_tree = [
			   {text : "系统菜单", attributes : {url : ""} ,children: 
					 	[
					 		{text : "员工管理", attributes : {url : "admin/staff!list.action"}},
					 		{text : "机台员工管理", attributes : {url : "admin/machineStaff!list.action"}},
					 		{text : "每日数据统计", attributes : {url : "admin/machineDailyRecord!list.action"} },
					 		{text : "(员工每月)数据统计", attributes : {url : "admin/machineDailyRecord!calculateStaffMonthlyReport.action"} },
					 		{text : "(月总量)数据统计", attributes : {url : "admin/machineDailyRecord!calculateOneMonthReport.action"} },
					 		{text : "(机台每月)数据统计", attributes : {url : "admin/machineDailyRecord!calculateMachineMonthlyReport.action"} },
					 		{text : "机台工价(倍率)设置", attributes : {url : "admin/salaryPercentage!list.action"} }
					 	],iconCls:'icon-user'
				 	},
           	   {text : "退出", attributes : {url : "login!loginOut.action"} ,children:[],iconCls:'icon-exit'}
			  ];
function changeClock()
{
	function addZero(num){
		return num<10 ? '0'+num : num;
	}
    var d = new Date(),
    	day = d.getDay(),
    	t = d.getFullYear() + "年" + (d.getMonth() + 1) + "月" + d.getDate() + "日 " + addZero(d.getHours()) + ":" + addZero(d.getMinutes()) + ":" + addZero(d.getSeconds());
    switch(day){
		case 0:day='星期天';break;
		case 1:day='星期一';break;
		case 2:day='星期二';break;
		case 3:day='星期三';break;
		case 4:day='星期四';break;
		case 5:day='星期五';break;
		case 6:day='星期六';break;
	}
    document.getElementById("clock").innerHTML = t+' '+day;
    document.getElementById("clock2").innerHTML = t+' '+day;
}
window.setInterval(changeClock, 1000);

	function changeReFresh(){
		if($('#reFresh').is(':checked')==true){
		$('#reFresh').val("1");
		}else{
			$('#reFresh').val("0");
		}
		}

</script>

<link rel="stylesheet" href="css/ad_index.css"/>
</head>
<body class="easyui-layout" onload="changeReFresh();">
	<div region="north" class="north" style="height: 36px">
		<!-- 详细 -->
		<div id="northDeatil" data-height="80" style="display:none">
			<div style="float:left;background:#fff;height:100%;width:80px;">
				<img src="images/sys/banner-title-ctrl.png" height="80" alt=""/>
			</div>
			<div class="clock tr" >
				<span>
					欢迎您:<s:property value="#session.login_admin.username"/>(<s:property value="#session.login_admin.username"/>)
				</span>
				<p>
					当前时间：<span id="clock"></span>
					<a class="wh aNorthChange" href="javascript:void(0)">收起<span class="spinner-arrow-up spinner-inline"></span></a>
				</p>
			</div>
		</div>
		<!-- 省略 -->
		<div id="northEll" data-height="36" style="color:#fff;padding:10px">
			<span>系统后台</span>
			<p style="position:absolute;right:10px;top:10px;margin:0;line-height:1">
				<span class="mr20">欢迎您:<s:property value="#session.login_admin.username"/></span>
				<span class="mr20">当前时间：<span id="clock2"></span></span>
			</p>
		</div>
	</div>
  <div region="center">
    <div class="easyui-tabs" fit="true" border="false" id="tabs">
      
    </div>
  </div>
  <div region="west" class="west" title="主菜单">
  	<div id="Menu" class="easyui-accordion">
   		
    </div>
    <%--<ul id="Menu"></ul>--%>
  </div> 
  
  <div id="tabsMenu" class="easyui-menu" style="width:120px;">  
    <div name="close" iconCls="icon-cancel">关闭</div> 
    <div name="Other" iconCls="icon-cancel">关闭其他</div>  
    <div name="All" iconCls="icon-cancel">关闭所有</div>
  </div>
  <!-- 控制台全局参数 -->
  <input id="isOpen" type="hidden" value="true"/>
  <script type="text/javascript" src="js/admin_index.js"></script>
  <object id="jatoolsPrinter" classid="CLSID:B43D3361-D075-4BE2-87FE-057188254255" codebase="jatoolsPrinter.cab#version=8,6,0,0"></object>
<script>

$(function(){
	var flagNorth = true;// 1 省略，2 详细
	$('.aNorthChange').click(function(){
		var height = 0;
		if(flagNorth){
			height = $('#northEll').prev().attr('data-height')*1;
			$('#northEll').hide().prev().show();
		}else{
			height = $('#northEll').attr('data-height')*1;
			$('#northEll').show().prev().hide();
		}
		layoutResize('north',{height:height});
		flagNorth = !flagNorth;
	});
});
</script>
</body>
</html>