<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 分页开始 ------------------------------------------------------------------------------------->
<script>
function topage(firstIndex){
	var form = document.form1;
	if(firstIndex > <s:property value="#pageList.pageCount"/>){
		alert("不能超过最大页面数");
		return;
	}else{
		form.firstIndex.value = firstIndex;
	}
	form.submit();
}
function checkAll(obj, name){
	var chk = obj.checked;
	$('[name='+name+']').each(function(){
		this.checked = chk;
	});
}
</script>
<p class="align_right" id="fenye">
<input type="hidden" name="firstIndex" value="<s:property value="firstIndex"/>"/>
<span>共 <s:property value="#pageList.totalResult"/> 条</span>
<span>共 <s:property value="#pageList.pageCount"/> 页</span>
<span>当前第 <s:property value="firstIndex"/> 页</span>
 <s:bean
name="org.apache.struts2.util.Counter" id="counter">
<s:param name="first" value="1" />
<s:param name="last" value="#pageList.pageCount" />
<s:if test="#pageList.pageCount<5">
<s:iterator>
<input class="mulButton" onclick="javascript:topage('<s:property/>')" type="button" value="<s:property />"/> 
</s:iterator>
</s:if>
<s:else>
<s:iterator status="curren" id="dangqian">
<s:if
test="
firstIndex==#curren.count or
firstIndex+1==#curren.count or
firstIndex-1==#curren.count or
#curren.count==#pageList.pageCount or
#curren.count==1
">
<s:if test="firstIndex==#curren.count">

<input class="mulButton" onclick="javascript:topage('<s:property/>')" type="button" value="<s:property value="#curren.count" />"/>
</s:if>
<s:if test="firstIndex!=#curren.count">
<input class="mulButton" onclick="javascript:topage('<s:property/>')" type="button" value="<s:property value="#curren.count" />"/>
</s:if>
</s:if>
<s:if test="firstIndex-2==#curren.count and firstIndex-2!=1">
<span>…</span>
</s:if>
<s:if
test="firstIndex+2==#curren.count and firstIndex+2!=#pageList.pageCount">
<span>…</span>
</s:if>


</s:iterator>
</s:else>
</s:bean>
<input type="text" id="topageValue"/> <input class="mulButton" type="button" onclick="javascript:topage($('#topageValue').val())" value="Go">
</p><!-- 分页结束 ----------------------------------------------------------------------------------------------------------->