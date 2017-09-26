<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%-- 分页按钮 --%>
<s:bean name="org.apache.struts2.util.Counter" id="counter">
<s:param name="first" value="1" />
<s:param name="last" value="#pageList.pageCount>100?100:#pageList.pageCount" />

<div class="list_pages fix">
	<input type="hidden" name="firstIndex" value="<s:property value="firstIndex"/>"/>
    <!-- <div class="fl total">共<s:property value="#pageList.totalResult"/>件条记录</div> -->
    <div class="sn_pages">
    	<s:if test="firstIndex>1">
    		<a class="pageprev" href="javascript:void(0)" onclick="topage(<s:property value="firstIndex-1"/>)">&lt;</a>
    	</s:if>
    	
    	<s:if test="#pageList.pageCount<5">
			<s:iterator status="st">
				<s:if test="firstIndex==#st.count"><i><s:property/></i></s:if>
				<s:else><a href="javascript:void(0)" onclick="topage(<s:property/>)"><s:property/></a></s:else>
			</s:iterator>
		</s:if>
		<s:else>
			<s:iterator status="st" id="dangqian">
				<s:if test="firstIndex==#st.count or firstIndex+1==#st.count or firstIndex-1==#st.count or #st.count==#pageList.pageCount or #st.count==1">
				<s:if test="firstIndex==#st.count">
				    <i><s:property/></i>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="topage(<s:property/>)"><s:property/></a>
				</s:else>
				</s:if>
				<s:if test="(firstIndex-2==#st.count and firstIndex-2!=1) || (firstIndex+2==#st.count and firstIndex+2!=#pageList.pageCount)">
					<span>…</span>
				</s:if>
			</s:iterator>
		</s:else>
        
        <s:if test="firstIndex<#pageList.pageCount">
        	<a class="pagenext" href="javascript:void(0)" onclick="topage(<s:property value="firstIndex+1"/>)">&gt;</a>
        </s:if>
        
        <span class="ml10">共<span id="maxPage"><s:property value="#pageList.pageCount"/></span>页，转到</span>
        <input tyle="text" class="text_pages ml10" id="pageNum" value="<s:property value="firstIndex"/>" onchange="check_number(this,<s:property value="#pageList.pageCount"/>)" onkeydown="if(event.keycode==13)topage(this.value)">
        <input type="button" class="submit_pages" value="Go" onclick="topage(document.getElementById('pageNum').value)">
    </div>
</div>

</s:bean>