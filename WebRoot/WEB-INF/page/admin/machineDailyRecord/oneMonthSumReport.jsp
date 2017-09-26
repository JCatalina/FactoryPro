<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>月总量数据统计</title>
<%@include file="/commonPage/frontend_meta.jsp" %>
<script>
//表单提交
function resubmit(action){
	var form = document.form1;
	if(action){
		form.action=action;
	}
	form.submit();
}

</script>
</head>
  
 <body>
 <form  name="form1" method="post" action="admin/machineDailyRecord!calculateOneMonthReport.action">
<table class="title mt10">
  <tr>
    <td class="EasySiteTitle">&nbsp;月总量数据统计</td>
  </tr>
</table>
<table class="old">
		<tr align="left">
			<td>日期：<input class="common" name="createTime"
				onfocus="WdatePicker({dateFmt:'yyyy-MM'})"
				value="<s:date name='queryObj.createTime' format="yyyy-MM"/>" />
			</td>
			<td><input class="mulButton" value="查询" onclick="resubmit('')"
					type="button" /></td>
		</tr>
</table>
<br/>
  <table class="old">
    <tr>
			<th>日期</th>
			<th>总米数</th>
			<th>匹数</th>
	</tr>
   
    <s:iterator value="everyDaySumRecord" id="key">
   	<tr class="dataTr">	
   		<td>
   			<s:date name="key" format="yyyy-MM-dd"/>
   		</td>
   		<td>
   			<s:property value="value" />
   		</td>
   		<td>
   			<s:property value="everyDaySumRows.get(key)"/>
   		</td>
   	</tr>
   </s:iterator>
   <tr>
   		<td>总</td>
   		<td>
   			<s:property value="sum" />
   		</td>
   		<td>
   			<s:property value="sumRows" />
   		</td>
   </tr>
  </table>

</form>
</body>
</html>
