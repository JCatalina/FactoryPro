// 确认订单页
;(function($,window){
	$.fn.toggleRadio = function(){
		this.click(function(){
			var cln = 'active';
			$(this).parent().addClass(cln).siblings().removeClass(cln);
			$(this).prop('checked',true);
		});
	}
	Addr.$list = $('.address-box');
	Addr.setContent = function(){
		var addr = this.getAddrByIndex($('.address-box label.cur').index()+1);
		if( !addr ) return false;
		var str_addr = addr.province+addr.city+addr.zone+addr.addr,
			str_reci = addr.name+' '+addr.phone;
		
		$('#msb-addr').html(str_addr);
		$('#msb-recieve').html(str_reci);
	}
	Addr.init(function(){
		// 选择地址
		$('.address-box :radio').click(function(){
			$(this).parents('label').addClass('cur').siblings().removeClass('cur');
			Addr.setContent();
		});
		
		if($('.address-box :checked').length){
			$('.address-box :checked').click();
		}else{
			$('.address-box :radio').eq(0).click();
		}
	});
	
	
	$('#payTypeBox :radio,#sendTypeBox :radio').toggleRadio();
	$('#conformForm').submit(function(){
		var len = $('.address-box :checked').length,
			r_len = $('.address-box :radio').length;
		if( !r_len ){
			$('html,body').scrollTop(0);
			Addr.show('add');
			alert('请先新增收货地址');
		}else if( !len ){
			$('html,body').scrollTop(0);
			alert('请先选择收货地址');
		}
		return !!len;
	});
})(jQuery,window);