<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>各机台月数据统计</title>
<%@include file="/commonPage/frontend_meta.jsp" %>
<script type="text/javascript" src="js/machineDailyRecord/machineMonthlyReport.js"></script>
</head>
 <body>
 <form  name="form1" method="post" action="admin/machineDailyRecord!calculateMachineMonthlyReport.action">
<table class="title mt10">
  <tr>
    <td class="EasySiteTitle">&nbsp;各机台月数据统计</td>
  </tr>
</table>
<table class="title">
		<tr align="left">
			<td>日期：<input class="common" name="createTime"
				onfocus="WdatePicker({dateFmt:'yyyy-MM'})"
				value="<s:date name='queryObj.createTime' format="yyyy-MM"/>" />
				显示区间：
				<select name="startNo">
					<s:iterator value="counter" begin="1" step="5" end="220" status="st">
						<!-- 无155 -159  -->
						<s:if test="(#st.index+1-1) * 5+1 == 156">
							<option value="160"  <s:if test="160 == startNo">selected=selected</s:if> >
								160
							</option>
						</s:if>
						<s:else>
							<option value="<s:property value="(#st.index+1-1) * 5+1"/>"  <s:if test="(#st.index+1-1) * 5+1 == startNo">selected=selected</s:if> >
								<s:property value="(#st.index+1-1) * 5+1"/>~<s:property value="(#st.index+1-1) * 5 +5"/>
							</option>
						</s:else>
					</s:iterator>
				</select>
			<input class="mulButton" value="查询" onclick="resubmit('')"
					type="button" />
			</td>
		</tr>
</table>
<br/>
</form>
    <table id="dataTable">
    	<tr>
	    <s:iterator value="everyMachineOneMonthRecord.keySet()" id="key">
    	<td>
    		
			  <table class="list singleTable" style="margin-top:0px;" >
			  	<tr>
				  	<td colspan="11">设品种：
						日期号(连续:1-12,非连续：10/12/15)：<input type="text" name="" class="noText"/>
						品种号: <input type="text" name="" class="varietiesText" style="width:70px;"/>
						<input class="mulButton" value="设置" onclick="batchSetVariety(this)" type="button" />
						<input class="common" name="createTime" type="hidden" value="<s:date name='queryObj.createTime' format="yyyy-MM"/>" />
					</td>
			  	</tr>
			  	<tr>
			  		<td colspan="11">
			  			统计：
						日期号(同上)：<input type="text" name="" class="partNo"/>
						列名（A/B/C/D/SUM）: <input type="text" name="" class="rowName" style="width:70px;"/>
						<input class="mulButton" value="统计" onclick="partCal(this)" type="button" />
			  			<input class="mulButton updateBtn" value="保存修改" onclick="batchUpdate(this)" type="button"/>
			  		</td>
			  	</tr>
			  	<tr>
			  		<th colspan="9">
			  			<s:property value="#key"/>号机
			  		</th>
			  		<th colspan="2">
			  			班组人员
			  		</th>
			  	</tr>
			    <tr>
						<th>序号</th>
						<th>日期</th>
						<th>品种</th>
						<th>A班</th>
						<th>B班</th>
						<th>C班</th>
						<th>D班</th>
						<th>总米数</th>
						<th>备注</th>
						<th>C班人员</th>
						<th>D班人员</th>
				</tr>
				<s:iterator value="everyMachineOneMonthRecord.get(#key)" var="o" status="s">
				   	<tr class="dataTr">
				   		<td><s:property value="#s.index + 1"/></td>
				   		<td class="dateTd">
				   			<input type="hidden" name="dateStrs" value="<s:date name='#o.createTime' format="dd"/>" />
				   			<s:date name='#o.createTime' format="dd"/>
				   		</td>
				   		<td>
				   			<input type="hidden" name="ids"  value="<s:property value='#o.id'/>"/>
				   			<input type="hidden" name="nos"  value="<s:property value='#o.no'/>"/>
	   						<input type="text" class="varietyNames myInput"  name="varietyNames" value="<s:property value='#o.varietyName'/>"/>
	   					</td>
	   					<td>
	   						<input type="text" name="AMs" class="AMs myInput" value="<s:property value="getIntFromDouble(#o.AM)" />" onblur="calSum(this)" />
	   					</td>
	   					<td>
	   						<input type="text" name="BMs" class="BMs myInput" value="<s:property value="getIntFromDouble(#o.BM)"/>" onblur="calSum(this)" />
	   					</td>
	   					<td>
	   						<input type="text" name="CMs" class="CMs myInput" value="<s:property value="getIntFromDouble(#o.CM)"/>" onblur="calSum(this)" />
	   					</td>
	   					<td>
	   						<input type="text" name="DMs" class="DMs myInput" value="<s:property value="getIntFromDouble(#o.DM)"/>" onblur="calSum(this)" />
	   					</td>
	   					<td>
	   						<input type="text" name="sumMs" class="sumMs myInput" value="<s:property value="getIntFromDouble(#o.sumM)"/>" />
	   					</td>
	   					<td>
	   						<input type="text" name="remarks" value="<s:property value="#o.remark" />"/>
	   					</td>
				   		<td>
				   			<select name="staffAIDs">
				   				<s:if test="#o.staffC !=null">
				   					<option  value="<s:property value='#o.staffC.id'/>" selected="selected"><s:property value='#o.staffC.name'/></option>
				   				</s:if>
				   		 		<s:iterator value="staffList" var = "o1">
				   		 			<option value="<s:property value='#o1.id'/>" ><s:property value='#o1.name'/></option>
				   		 		</s:iterator>
				   			</select>
				   		</td>
				   		<td>
				   			<select name="staffBIDs">
				   				<s:if test="#o.staffD !=null">
				   					<option  value="<s:property value='#o.staffD.id'/>" selected="selected"><s:property value='#o.staffD.name'/></option>
				   				</s:if>
				   		 		<s:iterator value="staffList" var = "o2">
				   		 			<option value="<s:property value='#o2.id'/>" ><s:property value='#o2.name'/></option>
				   		 		</s:iterator>
				   			</select>
				   		</td>
				   	</tr>
			   	</s:iterator>
				
				<!-- 补全 31行 -->
				<s:iterator var="counter" begin="1" end="90-everyMachineOneMonthRecord.get(#key).size" status="a">
					<tr class="dataTr">
					<td></td>
					<td> <input type="text" name="dateStrs" class="myInput" value=""/></td>
					<td> 
						<input type="hidden" name="ids"  value="-1"/>
						<input type="hidden" name="nos"  value="<s:property value='#key'/>"/>
	   					<input type="text" class="varietyNames myInput"  name="varietyNames" value=""/>
					</td>
					<td> <input type="text" name="AMs"  value="" class="AMs myInput" onblur="calSum(this)"/></td>
					<td> <input type="text" name="BMs" value="" class="BMs myInput" onblur="calSum(this)"/></td>
					<td> <input type="text" name="CMs" value="" class="CMs myInput" onblur="calSum(this)"/></td>
					<td> <input type="text" name="DMs"  value="" class="DMs myInput" onblur="calSum(this)"/></td>
					<td> <input type="text" name="sumMs" value="" class="sumMs myInput" /></td>
					<td> <input type="text"  name="remarks" value="" /></td>
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
				</s:iterator>
				
			</table>
		</td>
		</s:iterator>
		</tr>
	</table>

</body>
</html>
