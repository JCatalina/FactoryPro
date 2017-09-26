<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
   <title>机台AB班员工添加</title>
<%@include file="/commonPage/frontend_meta.jsp" %>
    <script type="text/javascript">
	function href(url){
		var form = document.form2;
		if(url){
			form.action=url;
		}
		form.submit();
	}
  </script>
  </head>
  
  <body>
<form  name="form2" method="post" action="admin/machineStaff!add.action">
		<table class="title mt10">
			<tr>
				<td class="EasySiteTitle">&nbsp;机台AB班员工添加</td>
				<td width="1%"><input value="返回" type="button"
					class="mulButton" onclick="window.history.go(-1)" /></td>
			</tr>
		</table>

		<div id="divJCGroupClient">
			<table class="old">
				<tr>
					<th>机台号(单个设置：10  批量设置: 1-10 )</th>
					<th>A班人员</th>
					<th>B班人员</th>
				</tr>

				<s:bean name="org.apache.struts2.util.Counter" id="counter">
					<s:param name="first" value="1" />
					<s:param name="last" value="20" />
					<s:iterator>
						<tr>
							<td><input type="text" name="noStr" value=""/></td>
							<td>
								<select name="staffAIds">
									<s:iterator value="staffList" var="o1">
										<option value="<s:property value='#o1.id'/>"><s:property
												value='#o1.name' /></option>
									</s:iterator>
								</select>
							</td>
							<td>
								<select name="staffBIds">
									<s:iterator value="staffList" var="o2">
										<option value="<s:property value='#o2.id'/>"><s:property
												value='#o2.name' /></option>
									</s:iterator>
								</select>
							</td>
						</tr>
					</s:iterator>
				</s:bean>
						
			</table>

			<div class="btn-handle">
				<input value="保存" type="submit" class="mulButton" />
			</div>
</div>
</form>

</body>
</html>