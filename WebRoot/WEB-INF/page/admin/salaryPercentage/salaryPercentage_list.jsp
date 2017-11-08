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
 <form  name="form1" method="post" action="admin/salaryPercentage!edit.action">
<table class="title mt10">
  <tr>
    <td class="EasySiteTitle">&nbsp;机台工价(倍率)设置</td>
     <td width="10%">
		<input class="mulButton" value="保存" onclick="resubmit()" type="button"/>
	</td>
  </tr>
</table>
<br/>
  <table class="old">
    <tr>
      <th>序号</th>
      <th>机台号(,分隔)</th>
      <th>工价(即倍率)</th>
    </tr>
   <s:iterator value="pl.pts" status="s" var="o">
   	<tr >	
   		<input type="hidden" name="ids" value="<s:property value="#o.id" />"/>
   		<td><s:property value="#s.index+1" /></td>
   		<td><input name="machineNos" value="<s:property value="#o.machineNos"/>" style="font-size:25px;width:1350px;text-align: left"/></td>
   		<td><input name="percentages" value="<s:property value="#o.percentage"/>" readonly="readonly"/></td>
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