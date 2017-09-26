<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>机台员工列表</title>
<script>
function resubmit(action){
	var form = document.form1;
	if(action){
		form.action=action;
	}
	form.submit();
}

</script>
<%@include file="/commonPage/frontend_meta.jsp" %>
</head>
  
 <body>
 <form  name="form1" method="post" action="admin/machineStaff!list.action">
<table class="title mt10">
  <tr>
    <td class="EasySiteTitle">&nbsp;机台员工列表</td>
    <td width="1%">
		<input class="mulButton" value="新增" onclick="resubmit('admin/machineStaff!addUI.action')" type="button"/>
	</td>
  </tr>
</table>
<table class="title">
  <tr align="right">
    <td>
	机台：<input name="queryObj.no" value="<s:property value="queryObj.no"/>"/>
  		<input class="mulButton" value="查询" onclick="resubmit('')" type="button"/>
    </td>
  </tr>
</table>
<br/>
  <table class="old">
	<tr>
			<th>机台号</th>
			<th>A班人员</th>
			<th>B班人员</th>
	</tr>
   <s:iterator value="pl.pts" status="s" var="o">
   	<tr >	
   		<td>
   			<input type="hidden" name="ids" value="<s:property value="id" />"/>
   			<input type="text" name="nos" value="<s:property value="#o.no"/>"/>
   		</td>
   		<td>
	   		<select name="staffAIds">
	   		 	<s:iterator value="staffList" var = "o1">
	   		 		<option value="<s:property value='#o1.id'/>"  <s:if test="#o1.id == #o.staffA.id">selected='selected'</s:if> ><s:property value='#o1.name'/></option>
	   		 	</s:iterator>
	   		</select>
   		</td>
   		<td>
	   		<select name="staffBIds">
	   		 	<s:iterator value="staffList" var = "o2">
	   		 		<option value="<s:property value='#o2.id'/>" <s:if test="#o2.id == #o.staffB.id">selected='selected'</s:if> ><s:property value='#o2.name'/></option>
	   		 	</s:iterator>
	   		</select>   		
   		</td>
   		
   	</tr>
   </s:iterator>
    
  </table>
   		<div class="btn-handle">
			<input class="mulButton" value="保存修改" onclick="resubmit('admin/machineStaff!edit.action')" type="button"/>
		</div>

<div class="listhandle">
<s:set name="pageList" value="pl"/>
 <%@include file="/commonPage/fenye.jsp"%>
</div>
</form>
</body>
</html>