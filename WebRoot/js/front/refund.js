// 申请退款页使用
;(function($,window){
	var $goods = $('.return-g-list .item');
    var Refund = {};
    
    Refund.getGoods = function(o){
    	var obj = {},
			$o = $(o);
		
		obj.id = $o.attr('data-id');//*
		obj.oneprice = $o.find('.price').html()*1;
		obj.count = $o.find('.count input').val()*1;
		obj.total = obj.oneprice*obj.count;
		obj.checked = $o.find(':checkbox')[0].checked;
		
		return obj;
    }
    Refund.refund = function(){
    	var $form = $('#applyForm'),
    		data  = $form.serialize(),
    		url   = $form.attr('action'),
    		error = function(e){console.log(e)},
    		success = function(){location.reload()},
    		flag = !!getQueryString('tuiReason',data);
    	if( !flag ){alert('请填写退货退款原因');return false;}
    	if( !confirm('您确定要申请退货退款吗？') ){return false;}
    	$('.return-form').find('button').addClass('disabled').html('正在提交...');
    	$.ajax({type:'post',url:url,data:data,success:success,error:error});
    }
    
    // 设置购物车页面内容
    function pageSetting(){
    	var flag = false, // 是否有已选
    		dis  = 'disabled',
    		count = 0,
    		total = 0;
    	
    	$goods.each(function(i,o){
    		var goods = Refund.getGoods(o);
    		goods.checked && (flag=true,count+=goods.count,total+=goods.total);
    	});
    	
    	//$('.allCbx').prop('checked',flagR);
    	$('.wrap .c-btn').toggleClass(dis,!flag);
    	$('.cartprice').html(total.toFixed(2));
    	//$('.cartcount').html(count);
    }
    
    if( STATUS && STATUS>0 ){
    	// 已提交申请
    	$goods.find('.cbx').hide();
    	$goods.find('.input').attr('readonly','readonly');
    	$('#apply').find('.form-line:eq(1),.handle').addClass('hide');
    	$('textarea').attr('readonly','readonly');
    }else{
    	// 未提交申请
    	$('#flow').addClass('hide');
    	$('.return-g-list .count i').click(function(){
        	var increase = $(this).index()? 1:-1;
        	countRule($(this).parent(), increase, function(id, oneprice, count, total, max){
        		pageSetting();
        	});
        });
    	$('.return-g-list .count input').keyup(function(){
    		var kc = e.which||window.event.keyCode;
        	if( kc==8 ){return false;}
        	countRule($(this).parent(), 0, function(){pageSetting();});
        }).focus(function(){
        	$(this).select();
        }).attr('autocomplete','off');
    	// 商品cbx
        $goods.find('.cbx').click(function(){
        	pageSetting();
        });//.each(function(i,o){$(o).click();});
    }
    
    $('#applyForm').submit(function(){
    	if($('.return-form button.disabled').length||!$goods.find(':checked').length){
    		return false;
    	}else{
    		Refund.refund();
    	}
    	return false;
    });
    pageSetting();
})(jQuery,window);