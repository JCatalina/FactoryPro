<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>品种列表</title>
<script>
function resubmit(action){
	var form = document.form1;
	if(action){
		form.action=action;
	}
	form.submit();
}

function deleteUrl(url){
	if(confirm("确定删除所选的吗?")){
		resubmit(url);
	}
	return false;
}
</script>
<%@include file="/commonPage/frontend_meta.jsp" %>
</head>
  
 <body>
 <form  name="form1" method="post" action="">
<table class="mt10">
  <tr>
    <td class="EasySiteTitle">&nbsp;品种列表</td>
    <td width="1%">
		<input class="mulButton" value="新增" onclick="resubmit('admin/variety!addUI.action')" type="button"/>
	</td>
  </tr>
</table>
<table>
  <tr align="right">
    <td>
	品种名：<input name="queryObj.name" value="<s:property value="queryObj.name"/>"/>
  		<input class="mulButton" value="查询" onclick="resubmit('')" type="button"/>
    </td>
  </tr>
</table>
<br/>
  <table class="list">
    <tr>
      <th>选择</th>
      <th>序号</th>
      <th>名字</th>
      <th>操作</th>
    </tr>
   <s:iterator value="pl.pts" status="s" var="o">
   	<tr >	
   		<td width="17">
   		<input type="checkbox" name="ids" value="<s:property value="id" />"/>
   		</td>
   		<td><s:property value="#s.index+1" /></td>
   		<td><s:property value="#o.name"/></td>
   		<td>
   			<a class="return_url" href="admin/variety!editUI.action?id=<s:property value='id'/>" href="javascript:;">编辑</a> | 
   			<a class="return_url" href="admin/variety!delete.action?id=<s:property value='id'/>" href="javascript:;">删除</a>
		</td>
   	</tr>
   </s:iterator>
    
  </table>

<div class="listhandle">
<s:set name="pageList" value="pl"/>
 <%@include file="/commonPage/fenye.jsp"%>
</div>
</form>
</body>
</html>