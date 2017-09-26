<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html >
<html>
  <head>
   <title>每日记录添加</title>
<%@include file="/commonPage/frontend_meta.jsp" %>

<script type="text/javascript">
	function href(url){
		var form = document.form2;
		if(url){
			form.action=url;
		}
		form.submit();
	}
	
	//计算总量
	function calSum(obj){
		var _tr=$(obj).closest("tr");
		var ams=parseFloat(_tr.find(".AMs").val());
		var bms=parseFloat(_tr.find(".BMs").val());
		var cms=parseFloat(_tr.find(".CMs").val());
		var dms=parseFloat(_tr.find(".DMs").val());
		if(!ams) ams=0;
		if(!bms) bms=0;
		_tr.find(".sumMs").val((ams+bms+cms+dms));
		
	}
	
	//清空
	function cleanUp(obj){
		var _tr=$(obj).closest("tr");
		_tr.find(".varietyNames").val('');
		_tr.find(".AMs").val('');
		_tr.find(".BMs").val('');
		_tr.find(".CMs").val('');
		_tr.find(".DMs").val('');
		_tr.find(".sumMs").val('');
		_tr.find(".remarks").val('');
		_tr.find("[name='staffAIDs']").val("-1");
		_tr.find("[name='staffBIDs']").val("-1");
	} 
	
	//增加行数据
	function addTr(){
		for(var i = 0 ; i< 5 ;i ++ )
			$("#dataTable").append($("#dataTableModel").html());
	}
	
</script>
<style>
/* table { width: 70%;}  */
</style>
</head>
  
  <body>
<form  name="form2" method="post" action="admin/machineDailyRecord!add.action"  enctype="multipart/form-data">
<table class="title mt10">
  <tr>
    <td class="EasySiteTitle">&nbsp;每日记录添加</td>
    <td>数据日期:(默认当天)
    	<input class="common" name="queryObj.createTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
    			value="<s:date name='queryObj.createTime' format="yyyy-MM-dd"/>" />
    </td>
	<td width="1%">
		<input type="file" name="file" id=""/>
	</td>
	<td width="1%">
		<input class="mulButton" value="批量导入" onclick="href('admin/machineDailyRecord!importDataByFile.action')" type="button" />
	</td>
	<td width="1%">
		<input class="mulButton" value="下载导入模版" onclick="href('admin/machineDailyRecord!downLoadExcel.action')" type="button" />
	</td>
	<td width="1%">
		<input class="mulButton" value="追加数据行" onclick="addTr()" type="button"/>
	</td>
	<td width="1%">
		 <input value="保存数据" type="submit" class="mulButton"/>
	</td>
	<td>
		<input value="返回" type="button" class="mulButton" onclick="window.history.go(-1)"/>
	</td>
  </tr>
</table>
<!--divJCGroupClient start-->
<div id="divJCGroupClient">

<table class="list" id="dataTable">
  <tr>
 	<th>序号</th>
 	<th>机台号</th>
 	<th>A班</th>
 	<th>B班</th>
 	<th>C班</th>
 	<th>D班</th>
 	<th>总米数</th>
 	<th>备注</th>
 	<th>C班人员</th>
 	<th>D班人员</th>
 	<th>操作</th>
  </tr>
  <s:bean name="org.apache.struts2.util.Counter" id="counter">
    <s:param name="first" value="1" />
    <s:param name="last" value="200" />
    <s:iterator>
       <tr class="dataTr">	
	   		<td>
	   			<s:property value='current - 1'/>
	   		</td>
	   		<td>
	   			<input type="text" name="nos" value="" class="myInput"/>
	   		</td>
	   		<td>
	   			<input type="text" name="AMs" class="AMs myInput" onblur="calSum(this)" placeholder="0"/>
	   		</td>
	   		<td>
	   			<input type="text" name="BMs" class="BMs myInput" onblur="calSum(this)"  placeholder="0" />
	   		</td>
	   		<td>
	   			<input type="text" name="CMs" class="CMs myInput" onblur="calSum(this)" value="0"  placeholder="0"/>
	   		</td>
	   		<td>
	   			<input type="text" name="DMs" class="DMs myInput" onblur="calSum(this)" value="0" placeholder="0" />
	   		</td>
	   		<td>
	   			<input type="text" name="sumMs" class="sumMs myInput" placeholder="0"/>
	   		</td>
	   		<td>
	   			<input type="text" name="remarks" class="remarks myRemark" value="<s:property value="" />" />
	   		</td>
	   		<td>
	   			<!-- C班人员选择 -->
	   			<select name="staffAIDs">
	   		 		<s:iterator value="staffList" var = "o">
	   		 			<option value="<s:property value='#o.id'/>"><s:property value='#o.name'/></option>
	   		 		</s:iterator>
	   			</select>
	   		</td>
	   		<td>
	   			<!-- D班人员选择 -->
	   			<select name="staffBIDs">
	   		 		<s:iterator value="staffList" var = "o">
	   		 			<option value="<s:property value='#o.id'/>"><s:property value='#o.name'/></option>
	   		 		</s:iterator>
	   			</select>
	   		
	   		</td>
	   		<td>
	   			<a class="return_url"  href="javascript:;" onclick="cleanUp(this)">清空</a> | 
			</td>
   	</tr>
    </s:iterator>
</s:bean>
  
  </table>
</div>
</form>

<div style="display: none" >
	<table id="dataTableModel">
		<tr class="dataTr">	
				<td>
	   				<input type="text" name="inputNos" value=""/>
	   			</td>
	   			<td>
		   			<input type="text" name="nos" class="myInput" value=""/>
		   		</td>
	   		
		   		<td>
		   			<input type="text" name="AMs" class="AMs myInput"  onblur="calSum(this)" style="border:0px;"/>
		   		</td>
		   		<td>
		   			<input type="text" name="BMs" class="BMs myInput"  onblur="calSum(this)" style="border:0px;"/>
		   		</td>
		   		<td>
		   			<input type="text" name="sumMs" class="sumMs myInput"  style="border:0px;"/>
		   		</td>
		   		<td>
		   			<input type="text" name="remarks myRemark"  style="border:0px;"/>
		   		</td>
		   		<td>
		   			<select name="staffAIDs">
		   		 		<s:iterator value="staffList" var = "o1">
		   		 			<option value="<s:property value='#o1.id'/>"  <s:if test="#o1.id == #o.staffA.id">selected='selected'</s:if> ><s:property value='#o1.name'/></option>
		   		 		</s:iterator>
		   			</select>
		   			
		   		</td>
		   		<td>
		   			<select name="staffBIDs">
		   		 		<s:iterator value="staffList" var = "o2">
		   		 			<option value="<s:property value='#o2.id'/>" <s:if test="#o2.id == #o.staffB.id">selected='selected'</s:if> ><s:property value='#o2.name'/></option>
		   		 		</s:iterator>
		   			</select>
		   		</td>
   		</tr>
   	</table>
	</div>


</body>
</html>