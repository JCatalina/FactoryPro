// json2.js
if(typeof JSON!=="object"){JSON={}}(function(){function f(n){return n<10?"0"+n:n}if(typeof Date.prototype.toJSON!=="function"){Date.prototype.toJSON=function(){return isFinite(this.valueOf())?this.getUTCFullYear()+"-"+f(this.getUTCMonth()+1)+"-"+f(this.getUTCDate())+"T"+f(this.getUTCHours())+":"+f(this.getUTCMinutes())+":"+f(this.getUTCSeconds())+"Z":null};String.prototype.toJSON=Number.prototype.toJSON=Boolean.prototype.toJSON=function(){return this.valueOf()}}var cx=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,escapable=/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,gap,indent,meta={"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"},rep;function quote(string){escapable.lastIndex=0;return escapable.test(string)?'"'+string.replace(escapable,function(a){var c=meta[a];return typeof c==="string"?c:"\\u"+("0000"+a.charCodeAt(0).toString(16)).slice(-4)})+'"':'"'+string+'"'}function str(key,holder){var i,k,v,length,mind=gap,partial,value=holder[key];if(value&&typeof value==="object"&&typeof value.toJSON==="function"){value=value.toJSON(key)}if(typeof rep==="function"){value=rep.call(holder,key,value)}switch(typeof value){case"string":return quote(value);case"number":return isFinite(value)?String(value):"null";case"boolean":case"null":return String(value);case"object":if(!value){return"null"}gap+=indent;partial=[];if(Object.prototype.toString.apply(value)==="[object Array]"){length=value.length;for(i=0;i<length;i+=1){partial[i]=str(i,value)||"null"}v=partial.length===0?"[]":gap?"[\n"+gap+partial.join(",\n"+gap)+"\n"+mind+"]":"["+partial.join(",")+"]";gap=mind;return v}if(rep&&typeof rep==="object"){length=rep.length;for(i=0;i<length;i+=1){if(typeof rep[i]==="string"){k=rep[i];v=str(k,value);if(v){partial.push(quote(k)+(gap?": ":":")+v)}}}}else{for(k in value){if(Object.prototype.hasOwnProperty.call(value,k)){v=str(k,value);if(v){partial.push(quote(k)+(gap?": ":":")+v)}}}}v=partial.length===0?"{}":gap?"{\n"+gap+partial.join(",\n"+gap)+"\n"+mind+"}":"{"+partial.join(",")+"}";gap=mind;return v}}if(typeof JSON.stringify!=="function"){JSON.stringify=function(value,replacer,space){var i;gap="";indent="";if(typeof space==="number"){for(i=0;i<space;i+=1){indent+=" "}}else{if(typeof space==="string"){indent=space}}rep=replacer;if(replacer&&typeof replacer!=="function"&&(typeof replacer!=="object"||typeof replacer.length!=="number")){throw new Error("JSON.stringify")}return str("",{"":value})}}if(typeof JSON.parse!=="function"){JSON.parse=function(text,reviver){var j;function walk(holder,key){var k,v,value=holder[key];if(value&&typeof value==="object"){for(k in value){if(Object.prototype.hasOwnProperty.call(value,k)){v=walk(value,k);if(v!==undefined){value[k]=v}else{delete value[k]}}}}return reviver.call(holder,key,value)}text=String(text);cx.lastIndex=0;if(cx.test(text)){text=text.replace(cx,function(a){return"\\u"+("0000"+a.charCodeAt(0).toString(16)).slice(-4)})}if(/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,"@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,"]").replace(/(?:^|:|,)(?:\s*\[)+/g,""))){j=eval("("+text+")");return typeof reviver==="function"?walk({"":j},""):j}throw new SyntaxError("JSON.parse")}}}());

//arttemplate
!function(){function a(a){return a.replace(t,"").replace(u,",").replace(v,"").replace(w,"").replace(x,"").split(y)}function b(a){return"'"+a.replace(/('|\\)/g,"\\$1").replace(/\r/g,"\\r").replace(/\n/g,"\\n")+"'"}function c(c,d){function e(a){return m+=a.split(/\n/).length-1,k&&(a=a.replace(/\s+/g," ").replace(/<!--[\w\W]*?-->/g,"")),a&&(a=s[1]+b(a)+s[2]+"\n"),a}function f(b){var c=m;if(j?b=j(b,d):g&&(b=b.replace(/\n/g,function(){return m++,"$line="+m+";"})),0===b.indexOf("=")){var e=l&&!/^=[=#]/.test(b);if(b=b.replace(/^=[=#]?|[\s;]*$/g,""),e){var f=b.replace(/\s*\([^\)]+\)/,"");n[f]||/^(include|print)$/.test(f)||(b="$escape("+b+")")}else b="$string("+b+")";b=s[1]+b+s[2]}return g&&(b="$line="+c+";"+b),r(a(b),function(a){if(a&&!p[a]){var b;b="print"===a?u:"include"===a?v:n[a]?"$utils."+a:o[a]?"$helpers."+a:"$data."+a,w+=a+"="+b+",",p[a]=!0}}),b+"\n"}var g=d.debug,h=d.openTag,i=d.closeTag,j=d.parser,k=d.compress,l=d.escape,m=1,p={$data:1,$filename:1,$utils:1,$helpers:1,$out:1,$line:1},q="".trim,s=q?["$out='';","$out+=",";","$out"]:["$out=[];","$out.push(",");","$out.join('')"],t=q?"$out+=text;return $out;":"$out.push(text);",u="function(){var text=''.concat.apply('',arguments);"+t+"}",v="function(filename,data){data=data||$data;var text=$utils.$include(filename,data,$filename);"+t+"}",w="'use strict';var $utils=this,$helpers=$utils.$helpers,"+(g?"$line=0,":""),x=s[0],y="return new String("+s[3]+");";r(c.split(h),function(a){a=a.split(i);var b=a[0],c=a[1];1===a.length?x+=e(b):(x+=f(b),c&&(x+=e(c)))});var z=w+x+y;g&&(z="try{"+z+"}catch(e){throw {filename:$filename,name:'Render Error',message:e.message,line:$line,source:"+b(c)+".split(/\\n/)[$line-1].replace(/^\\s+/,'')};}");try{var A=new Function("$data","$filename",z);return A.prototype=n,A}catch(B){throw B.temp="function anonymous($data,$filename) {"+z+"}",B}}var d=function(a,b){return"string"==typeof b?q(b,{filename:a}):g(a,b)};d.version="3.0.0",d.config=function(a,b){e[a]=b};var e=d.defaults={openTag:"<%",closeTag:"%>",escape:!0,cache:!0,compress:!1,parser:null},f=d.cache={};d.render=function(a,b){return q(a,b)};var g=d.renderFile=function(a,b){var c=d.get(a)||p({filename:a,name:"Render Error",message:"Template not found"});return b?c(b):c};d.get=function(a){var b;if(f[a])b=f[a];else if("object"==typeof document){var c=document.getElementById(a);if(c){var d=(c.value||c.innerHTML).replace(/^\s*|\s*$/g,"");b=q(d,{filename:a})}}return b};var h=function(a,b){return"string"!=typeof a&&(b=typeof a,"number"===b?a+="":a="function"===b?h(a.call(a)):""),a},i={"<":"&#60;",">":"&#62;",'"':"&#34;","'":"&#39;","&":"&#38;"},j=function(a){return i[a]},k=function(a){return h(a).replace(/&(?![\w#]+;)|[<>"']/g,j)},l=Array.isArray||function(a){return"[object Array]"==={}.toString.call(a)},m=function(a,b){var c,d;if(l(a))for(c=0,d=a.length;d>c;c++)b.call(a,a[c],c,a);else for(c in a)b.call(a,a[c],c)},n=d.utils={$helpers:{},$include:g,$string:h,$escape:k,$each:m};d.helper=function(a,b){o[a]=b};var o=d.helpers=n.$helpers;d.onerror=function(a){var b="Template Error\n\n";for(var c in a)b+="<"+c+">\n"+a[c]+"\n\n";"object"==typeof console&&console.error(b)};var p=function(a){return d.onerror(a),function(){return"{Template Error}"}},q=d.compile=function(a,b){function d(c){try{return new i(c,h)+""}catch(d){return b.debug?p(d)():(b.debug=!0,q(a,b)(c))}}b=b||{};for(var g in e)void 0===b[g]&&(b[g]=e[g]);var h=b.filename;try{var i=c(a,b)}catch(j){return j.filename=h||"anonymous",j.name="Syntax Error",p(j)}return d.prototype=i.prototype,d.toString=function(){return i.toString()},h&&b.cache&&(f[h]=d),d},r=n.$each,s="break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined",t=/\/\*[\w\W]*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|"(?:[^"\\]|\\[\w\W])*"|'(?:[^'\\]|\\[\w\W])*'|\s*\.\s*[$\w\.]+/g,u=/[^\w$]+/g,v=new RegExp(["\\b"+s.replace(/,/g,"\\b|\\b")+"\\b"].join("|"),"g"),w=/^\d[^,]*|,\d[^,]*/g,x=/^,+|,+$/g,y=/^$|,+/;"function"==typeof define?define(function(){return d}):"undefined"!=typeof exports?module.exports=d:this.template=d}();

// console
if( typeof console === 'undefined' ){var console = {log: function(){},dir: function(){}}};

// 获取querystring
function getQueryString(key,query) {
	query = decodeURIComponent(query || location.search.replace(/\+/g,'%20'));
    return (query.match(new RegExp("(?:^\\?|&)" + key + "=(.*?)(?=&|$)")) || ['', null])[1];
}

// 获取 string 字节长度
function getByteLen( str ){
	return str.replace(/[^\x00-\xff]/g, "**").length;
}
// 读取验证码
function codeChg(cid){
	var svalue = "/proscenium/registeredandlogon/MyHtdfml.html" + Math.random()+".html";
	document.getElementById(cid).setAttribute("src",svalue);
}

if (!Function.prototype.bind) {
  Function.prototype.bind = function (oThis) {
    if (typeof this !== "function") {
      // closest thing possible to the ECMAScript 5 internal IsCallable function
      throw new TypeError("Function.prototype.bind - what is trying to be bound is not callable");
    }
 
    var aArgs = Array.prototype.slice.call(arguments, 1), 
        fToBind = this, 
        fNOP = function () {},
        fBound = function () {
          return fToBind.apply(
        	this instanceof fNOP && oThis
                                 ? this
                                 : oThis,
            aArgs.concat(Array.prototype.slice.call(arguments)));
        };
 
    fNOP.prototype = this.prototype;
    fBound.prototype = new fNOP();
 
    return fBound;
  };
}

if (!Array.prototype.find) {
  Array.prototype.find = function(predicate) {
    if (this === null) {
      throw new TypeError('Array.prototype.find called on null or undefined');
    }
    if (typeof predicate !== 'function') {
      throw new TypeError('predicate must be a function');
    }
    var list = Object(this);
    var length = list.length >>> 0;
    var thisArg = arguments[1];
    var value;

    for (var i = 0; i < length; i++) {
      value = list[i];
      if (predicate.call(thisArg, value, i, list)) {
        return value;
      }
    }
    return undefined;
  };
}

function check_number(obj,max){
	var value = obj.value;
	if( !/^\d+$/.test(value) ){
		obj.value = '';
		alert('请输入整数数字');
	}else{
		if( max && value>max ){
			obj.value = max;
			alert('请输入不大于'+max+'的数字');
		}
	}
}

// 加入收藏
function addfavorite(name) { 
	/*if(document.all){ 
		window.external.addFavorite(url, name); 
	}else if (window.sidebar) { 
		window.sidebar.addPanel(name, url, ""); 
	}else{*/
		alert('请使用浏览器的收藏夹功能收藏'+name+'！');
	//}
}

function topage(firstIndex){
	var form = document.form1;
	form.firstIndex.value = firstIndex;
	form.submit();
}

// 随机数
function GetRndCode(a,b){a=a||6;var d="";b=b||new Array(0,1,2,3,4,5,6,7,8,9);for(var c=0;c<a;c++){var e=Math.floor(Math.random()*10);d+=b[e]}return d};

function GetRndNum(Min,Max){
	var Range = Max - Min,
		Rand = Math.random();
	return(Min + Math.round(Rand * Range));
}

// dialog
function showDialog(o){
	$(o).fadeIn();
	$('.fixed-block').fadeIn();
}

function hideDialog(o){
	o = typeof o === 'string' ? $(o) : $(o).parents('.fixed-dialog');
	$(o).fadeOut();
	$('.fixed-block').fadeOut();
}

//日期格式化
Date.prototype.format = function(format){ 
	var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
	} 

	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 

	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	return format; 
}

//file
function fileChg(e,cb){
    var $file = $(e.target),
        $box  = $file.parent(),
        $img  = $box.find('img'),
        fr_flag = !!window.FileReader,
        reader,file;
    if( !$file.val() ){
        return false;
    }else{
        if( fr_flag ){
            file = e.target.files[0];
            type = file.type;
            type = /image\/.*/i.test(type);
        }else{
            type = $file.val().split('.');
            type = type[type.length-1].toLowerCase();
            type = /jpe?g|png|gif|bmp/.test(type);
        }
        if( typeof cb === 'function' ){
        	cb(type);
        }
        if( !type ){
            alert('请选择图片格式文件！');
            $file.val('').siblings('img').remove();
            return false;
        }
    }

    $box.addClass('chosen');

    
    if( fr_flag ){
        reader = new FileReader();
        reader.onload = function(e){
            $img = $img.length ? $img : $('<img>').prependTo($box);
            $img.attr('src',this.result).attr('alt',file.name);
        }
        reader.readAsDataURL(file);
    }
    
    return true;
}

/*
 * formChk
 */
var formChk = {
	pwdSelectors : [],
	STRING : {
	    "notnull" : "这是必填字段，不能为空",
	    "username": "用户名必填，请勿包含中文和空格，长度大于4小于30",
	    "same_user": "用户名已存在",
	    "int"     : "请输入整数",
	    "num"     : "请输入数值",
	    "price"   : "请输入正数价格",
	    "yyyy-MM-dd" : "请按照 yyyy-MM-dd 格式输入日期",
	    "phone"   : "请输入正确格式的电话号码",
	    "mobile"  : "请输入11位数字手机号码",
	    "e-mail"  : "请输入正确格式的电子邮箱",
	    "password": "限6-16个英文字母、数字符号，区分大小写",
	    "same_pwd": "密码不相同，请检查密码",
	    "carnumber" : "请输入正确格式车牌号",

        "score"   : "分数必须在200分~670分之间",

	    "errCln"  : "text-err",
	    "okCln"   : "text-ok",

	    "iconHtml":'<i class="fa fa-times-circle"></i>'
    },
	PATTERN : {
	    "int"        : /^-?\d+$/,
      	"age"		 : /^\d+$/,
	    "minusint"   : /^-\d+$/,
	    "plusint"    : /^\d+$/,
	    "num"        : /^(-?\d+)(\.\d+)?$/,
	    "price"      : /^\d+(\.\d+)?$/,
	    "minusnum"   : /^-(\d+)(\.\d+)$/,
	    "phone"      : /^\s*\+?\s*(\(\s*\d+\s*\)|\d+)(\s*-?\s*(\(\s*\d+\s*\)|\s*\d+\s*))*\s*$/,
	    // /^1[3|4|5|8][0-9]\d{4,8}$/
	    "mobile"     : /^((1[0-9][0-9]))\d{8}$/,
	    "e-mail"     : /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/,
	    "yyyy-MM-dd" : /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/,
	    "password"   : /^[\w\W]{6,16}$/,
	    "idcard"     : /^(\d{15}$)|(^\d{17}([0-9]|X)$)/,
	    "carnumber"  : /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/
    },
    chkStrByType : function( str, type ){
	    var result = $.trim(str).length>0 , p = null;
	    switch( type ){
	      case "notnull"    : 
	      case "area"       : 
	      case "seeCode"    : break;
	      case "int"        : 
	      case "minusint"   :
	      case "plusint"    :
	      case "age"        :
	      case "num"        :
	      case "price"      : 
	      case "phone"      :
	      case "mobile"     : 
	      case "e-mail"     : 
	      case "minusnum"   : 
	      case "yyyy-MM-dd" : 
	      case "password"   :
	      case "idcard"     :
	      case "carnumber"  :
	                          p = formChk.PATTERN[type];break;
          //case "score"      : result = result && parseInt(str)>=220 && parseInt(str)<=670;break;
          //case "examnum"    : result = formChk.PATTERN[type] && str.length==10;break;
	      case "username"   : result = result && str.indexOf(' ')<0 && !/[\u4E00-\u9FA5]/.test( str ) && str.length<31 && str.length>3;break;
	      case "same_pwd"   : result = $(formChk.pwdSelectors[0]).val()==$(formChk.pwdSelectors[1]).val();break;
	      case "checked"    : result = str;break; 
	    }
	    if(p) result = result && p.test( str );
	    //alert(result)
	    return result;
    },
    chkValByType : function(s,p,l,t,e){
    	// pattern, label, 
		var o = $(s),
			v = o.is(':radio,:checkbox')?o[0].checked:$.trim(o.val()),
      		b = p
      		  ? (e&&!v ? true : this.chkStrByType(v,p)) // 是否允许空
      		  : true;
      	
		if( !b&&l ){
			$(l).addClass('on').html( t||$(l).html() );
		}else{
			$(l).removeClass('on');
		}
		return b;
	},
	strTips : function(arr,br){
		var t = '',
			i = 0,
			len = arr.length;
		if( len<2 ){
			t+=arr[0];
		}else{
			for(i;i<len;i++){
				t+= (i+1)+'. '+arr[i]+br;
			}
		}
		return t;
	},
    changeElems: {},
	changeEdit : function(obj){
		var s = obj.sel,
			$elem = $(s),
			p = obj.pattern || $elem.data('pattern'),
			l = obj.label || $elem.data('label'),
			t = obj.tips || $elem.data('tips') || null;
		if( p&&l&& typeof this.changeElems[s]==='undefined' ){
        	this.changeElems[s] = !0;
  			$elem.change(function(){
  				var b = formChk.chkValByType(s,p,l,t);
  			});
		}
	}
}

function set_content(elem, value, attr){
	var $o = typeof elem === 'string' ? $(elem) : elem;
	attr = attr || ($o.is('img,video') ? 'src' : $o.is('input,select,textarea') ? 'value' : null);
	if( attr==='value' ){
		$o.val(value);
	}else if(attr){
		$o.attr(attr, value);
	}else{
		$o.html( value );
	}
}

(function($){
	$.fn.extend({
		// 查看全部，收起
		ohText: function(h){
			h = h || 180;
			var cln = 'oh-text'
			this.each(function(i,o){
				var $o = $(o);

				if( $o.height()>h ){
					$o.addClass(cln).next().find('a').off('click').click(function(){
						var flag = $(this).parent().prev().toggleClass(cln).hasClass(cln);
						$(this).html(flag?'[查看全部]':'[收起]');
					});
				}else if(!$o.hasClass(cln)){
					$o.next().addClass('hide');
				}
			});
		},
		// textarea自适应高度
		autoTextAreaHeight: function( re_height, re_border_width ){
			function auto(){
				$(this).css('height', re_height);
	    		var height = re_height,
	    		    _offseth = height - (re_border_width||0);
	    	        _scrollh = Math.max(this.scrollHeight,re_height), // 可滚动内容高度
	    	        _padding = _offseth - re_height;
	    	    height = _scrollh-_padding;
	    		$(this).css('height', height);
			}
	    	this.off('keyup.auto').on('keyup.auto',auto).each(function(i,o){
	    		auto.call(o);
	    	});
	    },
	    animateClass: function(cln, time, callback){
		    var timer = null, _ = this;
		    _.addClass(cln);
		    timer = setTimeout(function(){
		        _.removeClass(cln);
		        clearTimeout(timer);
		        if(callback && typeof callback === 'function')
		            callback(_);
		    }, time);
		    return _;
		}
	});
})(jQuery);

var Cep = function(){}
Cep.prototype.String = {
	'code-alert':'点击获取验证码后，验证码会以短信形式发到您的手机，15分钟内有效'
}
Cep.prototype._getRequestPara = function(valsels, othParam) {
	var para = {};
	if( valsels && valsels.length ){
		var fields = $(valsels.join()).serializeArray();

		$.each(fields, function(i, field){
			var para_value = para[field.name],
				_value     = $.trim(field.value);
			if( !para_value ){
				para[field.name] = _value;
			}else{
				console.log("已经存在"+field.name);
			}
		});
	}
	para = $.extend(para, othParam||{});
	//console.log('_getRequestPara',para);
	return para;
};
/* 表单检测 */
Cep.prototype.chkForm = function(arr_vals, othTips, isAlert) {
	var textArr = [],
		i = 0,
		len = arr_vals ? arr_vals.length : 0,
		bool, tips,
		sel, label, pattern,
		$o;
    if( len ){
    	for(i;i<len;i++){
    		sel     = arr_vals[i].sel;
    		$o      = $(sel);
    		label   = arr_vals[i].label || $o.data('label') || null;
    		pattern = arr_vals[i].pattern || $o.data('pattern') || null;
    		allow_empty = arr_vals[i].empty || $o.data('empty') || false; // 是否允许空
    		bool    = formChk.chkValByType(sel, pattern, label, null, allow_empty);
    		if( !bool && !allow_empty ){
    			tips = arr_vals[i].tips || $o.data('tips') || formChk.STRING[pattern] || "";
    			textArr.push( tips );
    		}
    	}
    }
	if( othTips && othTips.length ){
		var oth_len = othTips.length;
		for(i=0;i<oth_len;i++){
			textArr.push( othTips[i] );
		}
	}
	if(!!textArr.length){
		if(isAlert){
			alert( '表单尚未填写完整，请按照以下提示完成\n\n'+formChk.strTips(textArr,'\n') );	
		}
		return false;
	}

	return true;
};
Cep.prototype.commAjax = function(ajaxparam, callback, errCallback){
	var s,// ajax 参数
        _self = this,
        o_para,
        errorable = ajaxparam.errorable;
    s = {
      type: "post",
      data: null,

      url:"",
      dataType: "json"
    };
    

    if( ajaxparam ){
    	// 加密
    	o_para = ajaxparam.data;
    	//ajaxparam.data = 'q='+o_para.string+'&v='+md5(o_para.string+GCW.config.key);
    	$.extend( s, ajaxparam );
        console.log(s.url,ajaxparam.data);
        delete s.errorable;
        
        s.error = function(e){
        	if( typeof errCallback === 'function' ){
        		errCallback(e);
        	}else{
        		/**暂时注释掉，影响用户体验*/
        		//alert('连接失败，请检查网络..正在为你加载');
        		//window.location.reload();
        	}
        	console.log('error '+s.url);
        }
        s.success = function(data){
        	console.log(data)
            if( data.res.status!="0" && !errorable ){
                alert(data.res.errMsg);
                if( typeof errCallback === 'function' ){
            		errCallback(data);
            	}
                return ;
            }
            
            if( typeof callback === 'function' ){
                callback( data, o_para );
            }
        }
        if( s.url ) $.ajax(s);
	}
}

Cep.prototype.ajaxSubmit = function(arr_vals,othParam,othTips, ajaxparam,callback,errCallback,isAlert){
	var _self = this, i=0, valsels = [];

	//changeEdit
    if(arr_vals){
    	for(i;i<arr_vals.length;i++){
    		formChk.changeEdit(arr_vals[i]);
    		valsels.push( arr_vals[i].sel );
    	}
    }
	
	var flag = _self.chkForm(arr_vals, othTips, isAlert), _data;
	if( !flag ){
		if( typeof errCallback === 'function' ){
			errCallback();
		}
		return false;
	}
	_data = _self._getRequestPara(valsels, othParam);
    ajaxparam = ajaxparam || {};
    console.log('ssssss',_data,ajaxparam.data)
    if( typeof ajaxparam.data === 'string' ){
    	$.each(_data,function(i,o){
    		ajaxparam.data += '&'+i+'='+o; 
    	});
    }else{
    	ajaxparam.data = _data;// 将obj和string两个版本赋值
    }
    _self.commAjax(ajaxparam, callback, errCallback);
    //return true;
}
Cep.prototype.init = function(settings){};

var cep = new Cep();
cep.init();


;(function($){
    $.fn.hoverView = function(para,selector){
        var opts = {
            timer: null,                        // 计时器
            over:{delay:0,func:show},           // 鼠标移入
            out:{delay:500,func:hide},          // 鼠标移出
            target:{obj: this, active: 'cur'},  // 鼠标移入目标
            view:  {obj: null, active: ''}      // 显示目标
        },
            self = this;

        function show(){
            opts.target.obj.addClass(opts.target.active);
            opts.view.obj&&opts.view.obj.removeClass('hide');
        }
        function hide(){
            opts.target.obj.removeClass(opts.target.active);
            opts.view.obj&&opts.view.obj.addClass('hide');
        }
        
        function setTO(f,d,s){
            return d && setTimeout($.proxy(f,s), d) || $.proxy(f,s)() || null;
        }
        if(typeof para === 'string'){
            opts.view.obj = $(para);
        }else{
            $.extend(true,opts,para||{});
        }
        if( !selector ){
        	opts.target.obj.hover(function(){
        		opts.timer&&clearTimeout(opts.timer);
        		opts.timer = setTO(opts.over.func,  opts.over.delay, this);
        	},function(){
        		opts.timer&&clearTimeout(opts.timer);
        		opts.timer = setTO(opts.out.func,   opts.out.delay, this);
        	});
        }else{
        	opts.target.obj.on('mouseover',selector,function(){
        		opts.timer&&clearTimeout(opts.timer);
        		opts.timer = setTO(opts.over.func,  opts.over.delay, this);
        	}).on('mouseout',selector,function(){
        		opts.timer&&clearTimeout(opts.timer);
        		opts.timer = setTO(opts.out.func,   opts.out.delay, this);
        	});
        }
    }
    
    var MyDialog = {
        show: function(sel){$(sel).removeClass('hide');$('html,body').css('overflow','hidden')},
        hide: function(sel){$(sel||'.dialog').addClass('hide');$('html,body').css('overflow','auto')},
        init: function(){
            var _self = this;
            $(document).on('click','.dialog_close',function(){_self.hide($(this).parents('.dialog')[0])});
        }
    }
    $.mydialog = MyDialog;
    MyDialog.init();
})(jQuery);

var tpl_index = '';
// 带有reurl的登录
function loginWithUrl(url,reUrl){
	reUrl = reUrl || location.href;
	reUrl = encodeURIComponent(reUrl);
	location.href = url + (/\?/.test(url) ? '&' : '?') + 'reUrl=' + reUrl;
	return false;
}
//vin
var swiperVin = null;
function swipeVin(imgArr){
    var html = '';
    swiperVin && swiperVin.clear();
    imgArr.each(function(i,o){
    	html += '<li><img src="'+o.src+'"></li>';
    });
    $('.vin-slide ul').html(html);
    swiperVin = new Slide({
        width:360,
        speed:800,
        container:'.vin-slide',
        moveElem:'.vin-slide li',

        dotElem: '.vin-slide p',

        autoSlide:true,
        autoTime:4000,
        hoverStopAuto:true,
        hoverStopElem:'.vin-slide'
    });
}