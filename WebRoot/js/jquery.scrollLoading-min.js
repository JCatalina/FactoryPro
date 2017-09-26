/*!
 * jquery.scrollLoading.js
 * by zhangxinxu  http://www.zhangxinxu.com
 * 2010-11-19 v1.0
 * 2012-01-13 v1.1 偏移值计算修改 position → offset
 * 2012-09-25 v1.2 增加滚动容器参数, 回调参数
*/
// change in jQuery or Zepto
var Lib;
if(window.jQuery)
	Lib = jQuery;
else
	Lib = Zepto;
(function(a){a.fn.scrollLoading=function(b){b=b||{};var c={attr:"data-url",container:a(b.wrapper||window),callback:a.noop};var d=a.extend({},c,b);d.cache=[];a(this).each(function(){var h=this.nodeName.toLowerCase(),g=a(this).attr(d.attr);var i={obj:a(this),tag:h,url:g};d.cache.push(i)});var f=function(g){if(a.isFunction(d.callback)){d.callback.call(g.get(0))}};var e=function(){var g=d.container.height();
var flg=d.container.get(0)===window;if(flg){contop=a(window).scrollTop()}else{contop=d.container.offset().top+(d.offsetTop||0)}
a.each(d.cache,function(m,n){var p=n.obj,j=n.tag,k=n.url,l,h;if(p){l=p.offset().top-contop,l+p.height();if((l>=0&&l<g)||(h>0&&h<=g)){if(k){if(j==="img"){f(p.attr("src",k))}else{p.load(k,{},function(){f(p)})}}else{f(p)}n.obj=null}}})};e();d.container.unbind("scroll.scrollLoading").bind("scroll.scrollLoading",e)}})(Lib);