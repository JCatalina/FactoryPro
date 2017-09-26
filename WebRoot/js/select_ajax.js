;(function(){
//全局变量
var siteTextId="";
var eventTarget=".select_ajax";
var timeout;
//之前的id
var cacheId="";
//初始值
var initValue="";
//oninput 事件或 onpropertychange 事件
var change_event=(navigator.userAgent.indexOf("MSIE") != -1)?((navigator.userAgent.indexOf("MSIE 9")!=-1)?"input":"propertychange"):"input";

$(document).ready(function(){
/**
 * 当鼠标 放上去 的效果 CSS  Class名称
 */
var divItemSelect = 'div_item_select';
/** 
 * 展示数据 的DIV 
 */
var showDataDivId = "input_div";
$("body").append("<div id='"+showDataDivId+"' style='display:none;'></div>");

$(eventTarget).attr("autocomplete","off");
$(document)
	.on('keyup',eventTarget,key_up)
	.on('keydown',eventTarget,site_keydown)
	.on('click',eventTarget,site_click)
	.on(change_event,eventTarget,site_change);
$(document).click(function(){
	$('#'+showDataDivId).hide();
});
/*$('#'+showDataDivId).scroll(function(){
	$("#client").focus();
})*/

$.extend({
  
  site_ajax:function(){
	//alert(31)
	//var roomId = $('[name=typeId]').val();
	var type = $(siteTextId).parent().attr('class').replace('myicon myicon-2 ',"").replace('myicon myicon-3 ',"");
	var url = $(siteTextId).attr('data-url');
	var othData = $(siteTextId).attr('data-oth');
	othData = othData?'&'+othData:'';
	if(!url) return false;
    $.ajax({
			type:"post",
			url:url,
			data:"querySValue="+$(siteTextId).val()+othData,//权  强行加入参数----不会弄了。
			async: false,
			dataType:"text",
			success:function(value){
				var str = "";
				if(value!=''){
					values=value.split("|");
					str="<span id='site_tab_comment' style='padding:2px;display:block;background-color:#67a1e1;color:#FFFAFA;text-align:left;'>请输入名称，用↑↓选择</span>";
					for(var i=0;i<values.length;i++){
						var arrs=values[i].split(",");
						var id=arrs[0];
						var num=arrs[1];
						var name=arrs[2];
						str += "<div><span>"+name+"</span><input type='hidden' class='d_index' value='" +id+"' /></div>";
					}
				}else{
					$("#site_tab_comment").html("对不起，找不到："+$(siteTextId).val());
				}
				//展示层，并展示数据
				if(str!=""){
					var $showDiv = $("#" + showDataDivId),
						$siteText = $(siteTextId),
						allHeight = document.documentElement.scrollHeight;
					$showDiv.html(str).hide();
					//定位位置与宽度
					$showDiv.css("left",$siteText.offset().left);
					//$showDiv.css("top",$siteText.offset().top + $siteText.outerHeight());
					if($siteText.offset().top + $siteText.outerHeight()+$showDiv.height()<= allHeight || allHeight<=500 ){
				   	 	$showDiv.css("top",$siteText.offset().top + $siteText.outerHeight());
					}else if($siteText.offset().top - $showDiv.height()<0){
						$showDiv.css("top", 0);
					}else{
						$showDiv.css("top", $siteText.offset().top - $showDiv.height());
					}
					$showDiv.css('width',$siteText[0].offsetWidth);//宽度
					$showDiv.show();
					//注册事件
					registerInputEvent(siteTextId);
				}
			}
		});
	},
	changeDown:function(){
		chageSelect(1);
	},
	changeUp:function(){
		chageSelect(-1);
	}
});
function stopEvent(e){
	if ( e && e.stopPropagation ){
		e.stopPropagation();
	}else{
    	window.event.cancelBubble = true;
    }
}
function site_click(event){
	if (siteTextId.id!=""&&event.target.id != siteTextId.id) {
		if($("#" + showDataDivId).css("display")=="block"){
			$("#" + showDataDivId).hide();
			if($(siteTextId).val()!=""){
				if($(siteTextId).val()==""){
					
				}
				$(siteTextId).val(initValue);
			}
		}
	}else{
		cacheId=siteTextId;
	}
	if(cacheId!=$(event.target).attr("id")){
		if($(cacheId).val()!=""){
			$(cacheId).val(initValue);
		}
		initValue=$(event.target).val();
	}
	site_keyup(event);
}
function site_change(o){
	site_keyup(o);
}

function key_up(event){
	siteTextId=event.target;
	//alert(41)
	//init text field
      if (event.keyCode == 13) {//回车
          var selected=$("#" + showDataDivId + " div[class='" + divItemSelect + "']");
    	  if(selected.text()!=""){
    		  item_click(selected,siteTextId);
    	  }else{
    		  ;
    	  }
      }else if(event.keyCode==32){//空格
    	  $(siteTextId).val($.trim($(siteTextId).val()));
    	  var selected=$("#" + showDataDivId + " div[class='" + divItemSelect + "']");
    	  if(selected.text()!=""){
    		  item_click(selected,siteTextId);
    	  }else{
    		  ;
    	  }
      }else if(event.keyCode==9){//tab
    	  cacheId=siteTextId;
    	  initValue=$(event.target).val();
    	  if(timeout!=null){
    		clearTimeout(timeout);
    	  }
		timeout=setTimeout("$.site_ajax()",200);
      }
}
/**
* 鼠标在文本框输入值,针对tab键
* @param {Object} event
*/
function site_keydown(event){
	if(event.keyCode==9){//Tab down
		cacheId=siteTextId;
		if($(this).val()!=""){
			if($("#"+showDataDivId).css("display")=="block"){
				$(this).val(initValue);
				$("#"+showDataDivId).hide();
			}
		}
      }else	if (event.keyCode == 40) {//down
    	  $.changeDown();
      }else if (event.keyCode == 38) {//up
    	  $.changeUp();
      }
}
/**
* 鼠标在文本框输入值
* @param {Object} event
*/
function site_keyup(event){
	//alert(51)
	siteTextId=event.target;
	var id=$(siteTextId).attr("id");
	//var sequence=Number(id.substring(id.length-1));
	//initAVdata(Math.floor((sequence+1)/2));
	if(timeout!=null){
		clearTimeout(timeout);
	}
	timeout=setTimeout("$.site_ajax()",200);
  }
/**
* 键盘操作  向上 或向上
* @param {Object} opt   向上 -1  向下 1
*/
  function chageSelect(opt){
      if ($("#"+showDataDivId).css('display') == 'block') {
          var obj = $("#"+showDataDivId+" div." + divItemSelect);
          if (obj.html() == null) {//当前还未选中。
              if (opt == 1) {
                  $("#" + showDataDivId + "> div:first").addClass(divItemSelect);
              }else {
                  $("#" + showDataDivId + "> div:last").addClass(divItemSelect);
              }
          }else {
              //var curr = parseInt($("#" + showDataDivId + " div[class='" + divItemSelect + "'] ~ input.d_index").val()) + opt;
        	  var curr = obj.index()-1 + opt;//减去tab的index
              var divCount = $("#" + showDataDivId + " > div").size();
              curr=(curr < 0) ? (divCount - 1) : ((curr == divCount) ? 0 : curr);
              $("#" + showDataDivId + " > div." + divItemSelect).removeClass(divItemSelect);
              var div=$("#" + showDataDivId + " > div").eq(curr);
              div.addClass(divItemSelect);
              //滚动计算开始
              var	show_h = $("#" + showDataDivId)[0].offsetHeight,
              		now_h=div[0].offsetHeight,
              		now_t=div[0].offsetTop;
              if((now_t + now_h) > show_h){
            	  $("#" + showDataDivId).scrollTop(now_t + now_h - show_h +10);
              }else{
            	  $("#" + showDataDivId).scrollTop(0);
              }
              //计算结束
              /*if(Math.abs(curr)>=6){
				$("#" + showDataDivId).scrollTop((curr-5)*26);
              }else{
				$("#" + showDataDivId).scrollTop(0);
              }
              */
          }
      }
  }
/**
* 注册事件
*/
  function registerInputEvent(obj){
      $("#" + showDataDivId + " div").click(function(){
          item_click($(this),obj);
      }).mouseover(function(){
          $("#" + showDataDivId + " div[class='" + divItemSelect + "']").removeClass(divItemSelect);
          $(this).addClass(divItemSelect);
      }).mouseout(function(){
          $(this).removeClass(divItemSelect);
      });
  }
  /**
   * 点击每一项的操作
   * @param {Object} obj
   */
	function item_click(obj,text_obj){
		stopEvent(window.event || arguments.callee.caller.arguments[0]);
		$(text_obj).unbind(change_event);
		if (obj.html() == null) {
			//alert("请选择学校名称。");
		}else if($(text_obj).attr('class').indexOf('equipNo')!=-1){ // 设备编号
			$(text_obj).val( $(obj).find("em").text() ).focus();
			$(text_obj).next().val( $(obj).find("span").text() );
			initValue=$(obj).find("em").text();//全局变量赋值
			if($(text_obj).next(':hidden').length>0){//hidden赋值
				var _val = obj.find('.d_index').val();
				$(text_obj).next(':hidden').val(_val);
			}
		}else { // 名称
			$(text_obj).val($(obj).find("span").text()).focus();//赋值，注意更改赋值对象
			initValue=$(obj).find("span").text();//全局变量赋值
			if($(text_obj).next(':hidden').length>0){//hidden赋值
				var _val = obj.find('.d_index').val();
				$(text_obj).next(':hidden').val(_val);
			}
		}
		$(text_obj).bind(change_event, site_keyup);
		$("#" + showDataDivId).hide();
	}
});

})(jQuery,window);