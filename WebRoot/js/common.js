// 兼容
if( !console ){
	var console = {log:function(){},dir:function(){}};
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
				return fToBind.apply(this instanceof fNOP && oThis ? this : oThis,
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
if (!Array.prototype.filter) {
  Array.prototype.filter = function(fun/*, thisArg*/) {
    'use strict';

    if (this === void 0 || this === null) {
      throw new TypeError();
    }

    var t = Object(this);
    var len = t.length >>> 0;
    if (typeof fun !== 'function') {
      throw new TypeError();
    }

    var res = [];
    var thisArg = arguments.length >= 2 ? arguments[1] : void 0;
    for (var i = 0; i < len; i++) {
      if (i in t) {
        var val = t[i];

        // NOTE: Technically this should Object.defineProperty at
        //       the next index, as push can be affected by
        //       properties on Object.prototype and Array.prototype.
        //       But that method's new, and collisions should be
        //       rare, so use the more-compatible alternative.
        if (fun.call(thisArg, val, i, t)) {
          res.push(val);
        }
      }
    }

    return res;
  };
}
// easyui
$.messager.defaults={ok:"确定",cancel:"取消"};

function $messageInfo(type,str,cb,err){
	$.messager[type]('操作提示',str,function(event){ 
		if(event){
			typeof cb === 'function' && cb(event);
			//alert('你点击的是'+event); 
		}else{
			typeof err === 'function' && err(event);
			//alert("你点击的是false"); 
		} 
	}); 
}

function confirmInfo(str,cb,err){ 
	$messageInfo('confirm',str,cb,err);
}

function promptInfo(str,cb,err){
	$messageInfo('prompt',str,cb,err);
}

function alertInfo(str,cb,err){
	$messageInfo('alert',str,cb,err);
}

function toFixedNum(num){
	num = typeof num === 'undefined' ? this : num;
	num+='';
	if( /\./.test(num) && num.split('.')[1].length>2 ){
		var arr = num.split('.');
		num = /^00/.test(arr[1]) ? arr[0] : arr[1][1]=='0' ? (arr[0]+'.'+arr[1][0]) : (num*1).toFixed(2);
	}
	return num;
}

Number.prototype.toFixedNum = toFixedNum;