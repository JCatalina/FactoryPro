// 购物车页使用
;(function($,window){
    
    var $all = $('.cart-body :checkbox:not([disabled])'),
    	$goods = $('.cart-t-list');
    var Cart = {};
    // 通过html获取单个商品情况
    Cart.getGoods = function(o){
    	var obj = {},
    		$o = $(o);
    	
    	obj.id = $o.attr('data-id');
    	obj.oneprice = $o.find('.price span').html()*1;
    	obj.count = $o.find('.count input').val()*1;
    	obj.count = isNaN(obj.count)?$o.find('.count').html()*1:obj.count;
    	obj.total = $o.find('.total span').html()*1;
    	obj.checked = $o.find(':checkbox')[0].checked;
    	
    	return obj;
    }
    
    // 设置购物车页面内容
    function pageSetting(){
    	var flag = false, // 是否有已选
    		flagR = true, // 是否全选
    		dis  = 'disabled',
    		count = 0,
    		total = 0;
    	
    	$goods.each(function(i,o){
    		var goods = Cart.getGoods(o);
    		//sconsole.log(goods)
    		goods.checked && (flag=true,count+=goods.count,total+=goods.total);
    		goods.checked || (flagR = false);
    	});
    	
    	$('.allCbx').prop('checked',flagR);
    	$('.wrap .c-btn').toggleClass(dis,!flag);
    	$('.cartprice').html('￥'+total.toFixed(2));
    	$('.cartcount').html(count);
    }
    
    // 购物车事件
    Cart.reg = function(){
    	var _this = this;
    	// 数量点击事件，调用 demo.js 的 countRule
    	$('.buy-handle i').click(function(){
        	var increase = $(this).index()? 1:-1;
        	countRule($(this).parent(), increase, function(id, oneprice, count, total){
        		$('#buyTotal-'+id).html(total.toFixed(2));
        		$('#oneprice-'+id).html(oneprice.toFixed(2));
        	});
        });
        $('.buy-handle input').keyup(function(){
        	var kc = e.which||window.event.keyCode;
        	if( kc==8 ){return false;}
        	countRule($(this).parent(), 0, function(id, oneprice, count, total){
        		$('#buyTotal-'+id).html(total.toFixed(2));
        		$('#oneprice-'+id).html(oneprice.toFixed(2));
        	});
        });
    	
	    // 全选cbx
	    $('.allCbx').click(function(){
	    	var flag = this.checked;
	    	$all.prop('checked',flag);
	    	pageSetting();
	    });
	    // 店铺cbx
	    $('.shopCbx').click(function(){
	    	var flag = this.checked;
	    	$(this).parents('.cart-table').find(':checkbox').prop('checked',flag);
	    	pageSetting();
	    });
	    // 商品cbx,:not(.gift)
	    $goods.find(':checkbox').click(function(){
	    	var $shop = $(this).parents('.cart-table'),
	    		len = $shop.find('.cart-t-list').length,
	    		checkedLen = $shop.find('.cart-t-list :checked').length,
	    		flag = len === checkedLen;
	    	$shop.find(':checkbox:not([disabled])')[0].checked = flag;
	    	pageSetting();
	    });
	    /*$goods.find('.gift').click(function(){
	    	return false;
	    });*/
	    
	    // 删除点击
	    $goods.find('.opera a').click(function(){
	    	return _this.remove(this);
	    });
	    
	    // 购物车提交
	    $('#cartForm').submit(function(){
	    	return !!$goods.find(':checked').length;
	    });
    }
    Cart.allCheck = function(){
    	$('.allCbx').eq(0).prop('checked',false).click();
    }
    Cart.remove = function(_this){
    	var url = $(_this).attr('href'),
    		name = $(_this).parents('.cart-t-list').find('.gname').html();
    	if( !confirm('是否删除'+name+'？') ) return false;
    	$.ajax({
    		url:url,
    		success:function(){location.reload();},
    		error:function(){alert('删除出错')}
    	});
    	return false;
    };
    Cart.removeMany = function(){
    	var data = $('#cartForm').serialize();
    	if( !data ){
    		alert('请先选择要删除的商品');
    		return false;
    	}
    	var flag = confirm('确认要批量删除吗？'),
    		url = 'user/userIndex!deleteManyCar'
    	if( !flag ){
    		return false;
    	}
    	$.ajax({url:url,type:'post',data:data,success:function(){location.reload()}});
    	
    }
    // 初始化
    Cart.init = function(){
    	window.Cart = this;
    	this.reg();
    	if( $goods.length ){
    		this.allCheck();
    	}else{
    		$('.allCbx').prop('disabled',true);
    	}
    }
    Cart.init();
    
    pageSetting();
})(jQuery,window);