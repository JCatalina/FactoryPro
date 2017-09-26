<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>每日数据统计列表</title>
<%@include file="/commonPage/frontend_meta.jsp" %>
<script type="text/javascript" src="js/machineDailyRecord/machineDailyRecord_list.js"></script>
</head>
  
 <body>
 <form  name="form1" method="post" action="admin/machineDailyRecord!list.action">
<table class="title mt10">
  <tr>
    <td class="EasySiteTitle">&nbsp;每日数据统计列表</td>
  </tr>
</table>
<table class="title">
		<tr align="left">
			<td>日期：<input class="common" name="queryObj.createTime"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
				value="<s:date name='queryObj.createTime' format="yyyy-MM-dd"/>" />
				机台号:<input name="queryObj.no" value="<s:property value='queryObj.no'/>"/>
				<input class="mulButton" value="查询数据" onclick="resubmit('')"
					type="button" />
			</td>
			<td>
				部分统计总米数:<span id="partSum">0</span>米  
				序号:<input id="inputNumber" value=""/>
				<input class="mulButton" value="计算" onclick="calCheckSumAmount()" type="button"/>
			</td>
			<td>
				<input class="mulButton" value="新增每日数据表" onclick="resubmit('admin/machineDailyRecord!addUI.action')" type="button"/>
			</td>
			<td>
				<input class="mulButton" value="修改追加数据行" onclick="addTr()" type="button"/>
			</td>
			<td>
				<input class="mulButton" value="保存修改" onclick="resubmit('admin/machineDailyRecord!edit.action')" type="button"/>
			</td>
		</tr>
</table>
<br/>
  <table class="list myTable" id="dataTable">
    <tr>
    		<th><input onclick="checkAll(this,'checkBox');" type="checkbox"/> 全选</th>
    		<th>序号</th>
			<th>机台号(必填)</th>
			<th>A班</th>
			<th>B班</th>
			<th>C班</th>
			<th>D班</th>
			<th>总米数</th>
			<th>备注</th>
			<th>C班人员</th>
			<th>D班人员</th>
	</tr>
   <s:iterator value="pl.pts" status="s" var="o">
   	<tr class="dataTr">	
   			<td width="17">
   				<input type="checkbox" name="checkBox" value="<s:property value="#o.id"/>"/>
   				<input type="hidden" name="ids" value="<s:property value="#o.id"/>"/>
   			</td>
   			<td class="indexNo">
   				<s:property value="#s.index+1" />
   			</td>
   			<td>
	   			<%-- <input type="hidden" name="ids" value="<s:property value="#o.id" />"/> --%>
	   			<input type="text" name="nos" class="myInput" tabindex="1" value="<s:property value='#o.no' />"/>
	   		</td>
	   		<td>
	   			<input type="text" name="AMs" class="AMs myInput" tabindex="2" value="<s:property value="getIntFromDouble(#o.AM)" />" onblur="calSum(this)" />
	   		</td>
	   		<td>
	   			<input type="text" name="BMs" class="BMs myInput" tabindex="3" value="<s:property value="getIntFromDouble(#o.BM)"/>" onblur="calSum(this)" />
	   		</td>
	   		<td>
	   			<input type="text" name="CMs" class="CMs myInput" tabindex="4" value="<s:property value="getIntFromDouble(#o.CM)"/>" onblur="calSum(this)"/>
	   		</td>
	   		<td>
	   			<input type="text" name="DMs" class="DMs myInput" tabindex="5" value="<s:property value="getIntFromDouble(#o.DM)"/>" onblur="calSum(this)"/>
	   		</td>
	   		<td>
	   			<input type="text" name="sumMs" class="sumMs myInput" tabindex="6" value="<s:property value="getIntFromDouble(#o.sumM)"/>"/>
	   		</td>
	   		<td>
	   			<input type="text" name="remarks" class="myRemark" tabindex="7" value="<s:property value="#o.remark" />" />
	   		</td>
	   		<td>
	   			<select name="staffAIDs">
	   		 		<s:iterator value="staffList" var = "o1">
	   		 			<option value="<s:property value='#o1.id'/>"  <s:if test="#o1.id == #o.staffC.id">selected='selected'</s:if> ><s:property value='#o1.name'/></option>
	   		 		</s:iterator>
	   			</select>
	   			
	   		</td>
	   		<td>
	   			<select name="staffBIDs">
	   		 		<s:iterator value="staffList" var = "o2">
	   		 			<option value="<s:property value='#o2.id'/>" <s:if test="#o2.id == #o.staffD.id">selected='selected'</s:if> ><s:property value='#o2.name'/></option>
	   		 		</s:iterator>
	   			</select>
	   		</td>
   	</tr>
   </s:iterator>
  </table>


<div class="listhandle">
<s:set name="pageList" value="pl"/>
 <%@include file="/commonPage/fenye.jsp"%>
</div>
</form>
	<div style="display: none" >
	<table id="dataTableModel">
		<tr class="dataTr">	
				<td class="indexNo">0</td>
	   			<td>
		   			<input type="hidden" name="ids" value="-1"/>
		   			<input type="text" name="nos" value="" class="myInput"/>
		   		</td>
		   		<td>
		   			<input type="text" name="AMs" class="AMs myInput"  onblur="calSum(this)" style="border:0px;"/>
		   		</td>
		   		<td>
		   			<input type="text" name="BMs" class="BMs myInput"  onblur="calSum(this)" style="border:0px;"/>
		   		</td>
		   		<td>
	   				<input type="text" name="CMs" class="CMs myInput" value="0" onblur="calSum(this)" style="border:0px;"/>
	   			</td>
	   			<td>
	   				<input type="text" name="DMs" class="DMs myInput" value="0" onblur="calSum(this)" style="border:0px;"/>
	   			</td>
		   		<td>
		   			<input type="text" name="sumMs" class="sumMs myInput"  style="border:0px;"/>
		   		</td>
		   		<td>
		   			<input type="text" name="remarks" class="myRemark" style="border:0px;"/>
		   		</td>
		   		<td>
		   			<select name="staffAIDs">
		   		 		<s:iterator value="staffList" var = "o1">
		   		 			<option value="<s:property value='#o1.id'/>"><s:property value='#o1.name'/></option>
		   		 		</s:iterator>
		   			</select>
		   			
		   		</td>
		   		<td>
		   			<select name="staffBIDs">
		   		 		<s:iterator value="staffList" var = "o2">
		   		 			<option value="<s:property value='#o2.id'/>"><s:property value='#o2.name'/></option>
		   		 		</s:iterator>
		   			</select>
		   		</td>
   		</tr>
   	</table>
	</div>

</body>
</html>