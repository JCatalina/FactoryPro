<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>员工月数据统计</title>
<%@include file="/commonPage/frontend_meta.jsp" %>
<script>
//表单提交
function resubmit(action){
	var form = document.form1;
	if(action){
		form.action=action;
	}
	form.submit();
	//恢复默认action
	form.action="admin/machineDailyRecord!calculateStaffMonthlyReport.action";
	
}

function deleteUrl(url){
	if(confirm("确定删除所选的吗?")){
		resubmit(url);
	}
	return false;
}

</script>
</head>
  
 <body>
 <form  name="form1" method="post" action="admin/machineDailyRecord!calculateStaffMonthlyReport.action">
<table class="title mt10">
  <tr>
    <td class="EasySiteTitle">&nbsp;员工月数据统计</td>
  </tr>
</table>
<table class="title">
		<tr align="left">
			<td>日期：<input class="common" name="createTime"
				onfocus="WdatePicker({dateFmt:'yyyy-MM'})"
				value="<s:date name='queryObj.createTime' format="yyyy-MM"/>" />
			</td>
			<td>员工:
				<select name="queryObj.id">
	   		 		<s:iterator value="staffList" var = "o">
	   		 			<option value="<s:property value='#o.id'/>" <s:if test="#o.id == queryObj.id">selected='selected'</s:if>><s:property value='#o.name'/></option>
	   		 		</s:iterator>
	   			</select>
			</td>
			<td>
			<input class="mulButton" value="查询" onclick="resubmit('')"	 type="button" />
			</td>
			<td>
			<input class="mulButton" value="导出单个员工报表" onclick="resubmit('admin/machineDailyRecord!downloadStaffMonthlyReport.action')"	 type="button" />
			</td>
			<td>
			<input class="mulButton" value="导出所有员工报表" onclick="resubmit('admin/machineDailyRecord!downloadAllStaffMonthlyReport.action')"	 type="button" />
			</td>
		</tr>
</table>
<br/>
  <table class="old">
  	<tr>
  		<th colspan="11"><s:property value="queryObj.remark"/>  <s:date name='queryObj.createTime' format="yyyy-MM"/>产量</th>
  	</tr>
    <tr>
			<th>机台号</th>
			<th>品种</th>
			<th>米数</th>
			<th>品种</th>
			<th>米数</th>
			<th>品种</th>
			<th>米数</th>
			<th>品种</th>
			<th>米数</th>
			<th>品种</th>
			<th>米数</th>
			
	</tr>
	
   <s:iterator value="staffMonthlyReport.keySet()" id="key1">
   	<tr class="dataTr">	
   		<td width="17">
   			<s:property value="#key1" />
   		</td>
   		<s:iterator value="staffMonthlyReport.get(#key1)">
	   		<td>
	   			<s:property value="key==null?'未填':key" />
	   		</td>
	   		<td>
	   			<s:property value="value" />
	   		</td>
   		</s:iterator>
		
		<!-- 补全空白 -->
		<s:iterator var="counter" begin="1" end="5-staffMonthlyReport.get(#key1).size" status="a">
			<td> </td>
			<td> </td>
		</s:iterator>


	</tr>
   </s:iterator>

<%-- 
    <s:iterator value="staffMonthlyReport" id="key">
   	<tr class="dataTr">	
   		<td width="17">
   			<s:property value="key" />
   		</td>
   		<td>
   			<s:property value="value" />
   		</td>
   	</tr>
   </s:iterator>
    --%>
  </table>

</form>
</body>
</html>
