//获取query string值
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

var initCss = function(){
	$('head').append('<link href="css/form_validate.css" type="text/css" rel="stylesheet">')//form_valid.css
}
initCss();

var formChk = {
  HOST :　"",
  pwdId : [],
  STRING : {
    "notnull"    : "必须填写",
    "username"   : "用户名必填",
    "same_user"  : "用户名已存在",
    "int"        : "请输入整数",
    "minusint"   : "请输入负整数",
    "num"        : "请输入数值",
    "minusnum"   : "请输入负数",
    "yyyy-MM-dd" : "请按照 yyyy-MM-dd 格式输入日期",
    "phone"      : "请输入正确格式的电话号码",
    "e-mail"     : "请输入正确格式的电子邮箱",
    "password"   : "密码必须在6~16之间",
    "same_pwd"   : "密码不相同，请检查密码",
    "max"        : "不能大于$s",
    "min"        : "不能少于$s",
    "max-min"    : "不能大于$s且不能少于$s",
    //"area"       : "请选择一个社区",
    //"seeCode"    : "请填写社区验证码",

    "errCln"     : "text-err",
    "okCln"      : "text-ok",

    "iconHtml"   :'<i class="fa fa-times-circle"></i>'
  },

  PATTERN : {
    "int"        : /^-?\d+$/,
    "minusint"   : /^-\d+$/,
    "num"        : /^(-?\d+)(\.\d+)?$/,
    "minusnum"   : /^-(\d+)(\.\d+)?$/,
    "phone"      : /^\s*\+?\s*(\(\s*\d+\s*\)|\d+)(\s*-?\s*(\(\s*\d+\s*\)|\s*\d+\s*))*\s*$/,
    "e-mail"     : /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/,
    "yyyy-MM-dd" : /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/
  },

  chkStrByType : function( str, type, options ){
    var result = $.trim(str).length>0 , p = null;
    switch( type ){
      case "notnull"    : 
      case "username"   :
      case "area"       : 
      case "seeCode"    : break;
      case "int"        : 
      case "minusint"   : 
      case "num"        : 
      case "minusnum"   :
      case "phone"      : 
      case "e-mail"     : 
      case "yyyy-MM-dd" : 
                          p = formChk.PATTERN[type];break;
      case "password"   : result = str.length >=6&&str.length<=16;break;
      case "same_pwd"   : result = $(formChk.pwdId[0]).val()==$(formChk.pwdId[1]).val();break;
      
      case "max"        : p = formChk.PATTERN["num"];result = str*1<=options.max;break;
      case "min"        : p = formChk.PATTERN["num"];result = str*1>=options.min;break;
      case "max-min"    : p = formChk.PATTERN["num"];result = str*1<=options.max && str*1>=options.min;break;
    }
    if(p) {result = result && p.test( str );}
    return result;
  },

  changeSubmit : function( elem, isDisabled ){
    var $submit = $(elem)
    if(isDisabled)
      $submit.addClass('disabled').attr('disabled','disabled');
    else
      $submit.removeClass('disabled').removeAttr('disabled','disabled');
  },

  Error : function( type, elem, text, isChgSubmit ){

    var $elem  = $(elem);
    var isShow = type!="hide"&&!!type;

    var add    = isShow ? "errCln" : "okCln";
    var remove = isShow ? "okCln" : "errCln";
    var func   = isShow ? "show" : "hide";
    var t      = isShow ? text : null;

    $elem.removeClass(formChk.STRING[remove]).addClass(formChk.STRING[add]);
    
    var replaceArr = [];
    if(type.indexOf('max')!=0||type.indexOf('min')!=0){
    	if(elem.attr('data-max'))
    		replaceArr.push(elem.attr('data-max'));
    	if(elem.attr('data-min'))
    		replaceArr.push(elem.attr('data-min'));
    }
    
    formChk.Toast[func]( t, $elem.offset(), replaceArr );
    
    //if(isShow) $elem.focus().select();
    
    if(isChgSubmit){
    	var submit = $elem.parents('form').find(':submit')[0];
    	formChk.changeSubmit( submit, isShow );
    }
  },
  
  Toast : {
	show : function( text, offset, strArr ){
	  var _top = offset.top+20, _left = offset.left;
	  
	  if(strArr && strArr.length){
		  $.each(strArr, function(i,s){
			  text = text.replace("$s",s);
		  });
	  }
	  if(!$('.toast').length){
		  var toast = '<div class="toast" style="top:'+_top+'px;left:'+_left+'px">'+text+'</div>'
		  $('body').append(toast);
	  }else{
		  $('.toast').css({top:_top,left:_left}).html( text );
	  }
  	},
	hide : function( text, offset ){
  	  $('.toast').remove();
  	}
  },

  clearError : function( elem ){
    $(elem).removeClass(formChk.STRING["errCln"]).removeClass(formChk.STRING["okCln"])
  },

  checkText : function( elem, type ){

    var $elem = $(elem);

    var chk;
    
    var options = {};
    if(type.indexOf('max')!=-1||type.indexOf('min')!=-1){
    	if($elem.attr('data-max'))
    		options.max = $elem.attr('data-max');
    	if($elem.attr('data-min'))
    		options.min = $elem.attr('data-min');
    }
    	

    chk = 
      formChk.chkStrByType( $elem.val(), type, options );
    
    if(chk){
      formChk.Error( "hide", $elem );
    }else{
      formChk.Error( "show", $elem, formChk.STRING[type] );
    }

    return chk;
  },

  checkSelect: function( $elem, type ){

    var chk;
    chk = 
      formChk.chkStrByType( $elem.val(), type );

  },

  init: function( pwdId ){

    //formChk.HOST = window.location.host || "http://www.o2ojysq.com/";
    //formChk.pwdId = pwdId;

    $.fn.extend({

      checkBySubmit : function( type, callback ){
        var chk = true;
        var _type = type || $(this).attr('data-valid');
        chk = !formChk.checkText(this, _type) ? false : chk;
        return {"checked":chk, "text":chk?"":formChk.STRING[type]};
      },

      checkBind : function( type, ev, callback ){
        var _ev = ev || "blur";
        //this.after(formChk.STRING.iconHtml);
        return this.each(function(i,o){
        	var _type = type || $(o).attr('data-valid');
        	var replaceArr = [];
            if(_type.indexOf('max')!=0||_type.indexOf('min')!=0){
            	if($(this).attr('data-max'))
            		replaceArr.push($(this).attr('data-max'));
            	if($(this).attr('data-min'))
            		replaceArr.push($(this).attr('data-min'));
            }
        	$(o).on( _ev, function(){
                $(this).checkBySubmit( _type )
            }).focus(function(){
            	formChk.Toast.show(formChk.STRING[_type],$(this).offset(),replaceArr);
            })
        })
      },

      checkUnbind : function(ev){
        var _ev = ev || "blur";
        return this.each(function(i,o){
        	formChk.Toast.hide(null, $(o).offset());
        	$(o).off(_ev).off('focus').off('blur');
        })
      }

    });
  }

}

// 通用
$(function(){
	formChk.init();
	//$(':text[data-valid]').checkBind();
});

var consoleLog = function(msg,isAlert){try{console.log(msg)}catch(e){if(isAlert)alert(msg)}}

// AbsLoading
var AbsLoading = function( elemParent, type ){
	if(type=="hide"){
		$(elemParent).find('.AbsLoading').remove();
		return;
	}
	$(elemParent).append('<div class="AbsLoading"></div>');
}

//加法
function accAdd(arg1,arg2){ 
	var r1,r2,m; 
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
	m=Math.pow(10,Math.max(r1,r2)); 
	return (arg1*m+arg2*m)/m; 
} 