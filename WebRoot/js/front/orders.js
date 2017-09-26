// 订单列表页使用
;(function($,window){
	
	var Order = {};
	Order.hrefToAjax = function(href){
		var url = href;
		if( !url ) return false;
		$.ajax({type:'post',url:url,success:function(){location.reload();},error:function(e){console.log(e)}});
	}
	// 确认收货
	Order.receive = function(a){
		var $a = $(a), onclick = $a.attr('onclick'),
			url = $a.attr('href'),
			flag = confirm('是否确认收货？');
		if( !flag ) return false;
		
		$a.attr('onclick','return false');
		cep.ajaxSubmit([],{},null, {
			url: url, errable: true
		},function(data){
			if(data.res.status==0){
				location.reload();
			}else{
				$a.attr('onclick',onclick);
			}
		});
		
		return false;
	}
	// 订单取消
	Order.cancel = function(a){
		var $a = $(a), onclick = $a.attr('onclick'),
			url = $a.attr('href'),
			isPay = $a.attr('isPay');
		var str = (isPay == 2 ? "提示：商家同意取消订单后，已支付的款项会退回到您的钱包" : "提示：已提交取消申请到商家处");
		var	flag = confirm(str);
		if( !flag ) return false;
		$a.attr('onclick','return false');
		cep.ajaxSubmit([],{},null, {
			url: url, errable: true
		},function(data){
			if(data.res.status==0){
				location.reload();
			}else{
				$a.attr('onclick',onclick);
			}
		});
		
		return false;
	}
	
	// 确认收货
	Order.click = function(a){
		this.hrefToAjax(a.href);
		return false;
	}
	
	window.Order = Order;
	
})(jQuery,window);