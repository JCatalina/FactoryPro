<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
   <title>员工编辑</title>
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
<form  name="form2" method="post" action="admin/staff!edit.action">
<input name="id" type="hidden" value="<s:property value='staff.id'/>"/>
<table class="title mt10">
  <tr>
    <td class="EasySiteTitle">&nbsp;员工编辑</td>
    <td width="1%">
		<input value="返回" type="button" class="mulButton" onclick="window.history.go(-1)"/>
	</td>
  </tr>
</table>
<!--divJCGroupClient start-->
<div id="divJCGroupClient">
<table class="old">
  <tr>
    <td class="title">姓名</td>
    <td class="input">
  	  <input type="hidden" name="staff.id" value="<s:property value='id'/>"/>
      <input maxlength="255" name="staff.name" class="long_common"  value="<s:property value='staff.name'/>"  />
    </td>
  </tr>
  </table>
  <div class="btn-handle">
   <input value="保存" type="submit" class="mulButton"/>
  </div>
</div>
</form>

</body>
</html>