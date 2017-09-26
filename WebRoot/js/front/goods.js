// 商品列表页
;(function($,window){
    // 按数量规则计算总价
    // 20160407 由三价格区间，更改为单价格
    function countRule($obj,increase,cb){
    	var $count   = $obj.find('input'),
			id       = $count.attr('id').split('-')[1],
			count    = parseInt($count.val()) + increase,
			max      = $count.attr('data-max'),//单价格不需要用max
			oneprice = $count.attr('data-lowprice'),
			total,
			arrprice = $count.attr('data-arr')
					 ? $count.attr('data-arr').split('|') : [],
			len      = arrprice.length,
			promotion = $count.attr('data-promotion');
		if( isNaN(count) ){
			count = 1;
		}
		max = max ? max*1 : 0;
		//if( !max ){
			//$count.val(0).prop('disabled',true);
			//count = 0;
			//oneprice = arrprice.length ? arrprice[0].split(',')[0]*1 : $count.attr('data-oneprice');
		//}else{
			if( max ) count = count ? (count > max ? max : count) : 1;
			count = count || 1;
			/*$.each(arrprice,function(i,o){
				var attr  = o.split(','),
				price = attr[0]*1,
				min   = attr[1]*1,
				max   = attr[2]*1;
				if( (len-1==i && count>=min) || (count>=min && count<=max) ){
					oneprice = price;
					return false;
				}
			});
			if( typeof oneprice === 'undefined' ){
				oneprice = arrprice.length ? arrprice[0].split(',')[0]*1 : $count.attr('data-oneprice');
				console.log('arrprice为空');
			}*/
		//}
		total = count * oneprice;
		
		$count.val(count);
		if( typeof cb === 'function' ){cb(id, oneprice, count, total, max, promotion);}
    }
    
    
    
    
    
    
    // 购买通用 id:表单id,type单个或多个,cb回调
    function addCart(formId,type,cb){
    	
    	var formsel = '#'+formId,
    		$form = $(formsel),
    		url  = $form.attr('action'),
    		arr  = type
    		     ? [{sel:formsel+' [name=carGoodsId]',pattern:'notnull'},{sel:formsel+' [name=carCount]',pattern:'notnull'}]
    			 : [],
    		data = $form.serialize(),
    		carCount = getQueryString('carCount',data);
    	
    	if(type && carCount < 1) return false;
    	data = type ? '' : data;
    	cep.ajaxSubmit(arr, null, null,{
    		url: url, data: data
    		//errorable: true
    	},function(data){
    		console.log(data);
    		typeof cb === 'function' && cb(data);
    	},function(e){
    		//location.href='/front/login!loginUI?loginType=1';
    		loginWithUrl('front/login!loginUI?loginType=1');
    	});
    }
    // 加入购物车，单个商品
    function addCartWithAnim(formId){
    	addCart(formId,1,function(data){
    		var timer = null,
				id = new Date().getTime(),
				$form = $('#'+formId),
				src = $form.attr('data-src'),
				border = 8*2,
				padding = 30*2,
				width = 936,
				height = 482,
				top = parseInt($('.sidebar').css('top'))-($('#buy-'+formId.split('-')[1]).height()-height+border)/2 - 75,
				right = (width+border+padding-$('body').width())/2,
				animHtml = '<div class="animGood" id="anim-'+id+'"><img src="'+src+'"></div>',
				$animHtml = $(animHtml);
			
			$animHtml.appendTo($form).animate({top:top,right:right,width:10,height:10,padding:0,opacity:0.5},1000);
			timer = setTimeout(function(){
				$animHtml.remove();
				$('.cartSize').html(data.inf.carSize);
				clearTimeout(timer);
			},1000);
    	});
    	
    }
    // 加入购物车，多个商品
    function addManyCart(formId){
    	addCart(formId,0,function(data){location.href = ' user/userIndex!carList';});
    }
    // 立即购买
    function addCartAtOnce(formId){
    	addCart(formId,1,function(data){location.href = ' user/userIndex!carList';});
    }
    
    
    window.countRule = countRule;
    //window.addCart = addCart;
    window.addCartAtOnce = addCartAtOnce;
    window.addCartWithAnim = addCartWithAnim;
    window.addManyCart = addManyCart;
    
    function main(){
    	/*显示dialog*/
        if(window.g_islogined=='true'){
        	$('.sort-l').on('click','.buy',function(){
        		var id = $(this).parents('.sort-l-item').attr('data-id');$.mydialog.show('#buy-'+id);
        	}).on('click','.dialog-more .c-btn-red',function(){
        		var ind = $(this).parents('.dialog-more').attr('id').split('-')[1];
        		$.mydialog.hide('#more-'+ind);
        		$.mydialog.show('#buy-'+ind);
        	});
        }else{
        	$('.sort-l').on('click','.buy,.dialog-more .c-btn-red',function(){
        		//location.href="front/login!loginUI?loginType=1";
        		loginWithUrl('front/login!loginUI?loginType=1');
        	});
        }
        $('.sort-l').on('click','.more',function(){
        	// 显示更多详情
        	var id = $(this).parents('.sort-l-item').attr('data-id'),
        		$more = $('#more-'+id);
        	$.mydialog.show($more[0]);
        	/*if( $more.data('init') ){
        		// 已加载
        		return ;
        	}
        	$more.data('init',1);
        	$more.find('.dialog_container').addClass('loading');
        	$.get('index!goodsDetail?id='+id,function(html){
        		$more.find('.dialog_container>div').html(html);
        		$more.data('init',1);
        		$more.find('.dialog_container').removeClass('loading');
        	},'html');*/
        }).on('click','.dialog-more .title a',function(){
        	// 更多详情，选项卡切换
        	var ind = $(this).index();
        	$(this).addClass('cur').siblings().removeClass('cur');
        	$(this).parents('.dialog-more').find('.scroll_container').addClass('hide').eq(ind).removeClass('hide');
        	return false;
        });
        /*/显示dialog */
        
        
    	
    	/*购买相关*/
    	$('.sort-l').on('click','.dialog-buy .buy-handle i',function(e){
    		var increase = $(this).index()? 1:-1;
    		countRule($(this).parent(), increase, function(id, oneprice, count, total, max, promotion){
    			$('#buyTotal-'+id).html(total.toFixed(2));
    			if( !isNaN(promotion) ){
    				$('#buyTotal-pro-'+id).html((total*promotion).toFixed(2));
    			}
    		});
    	})
    	.on('keyup','.dialog-buy .buy-handle input',function(e){
    		var kc = e.which||window.event.keyCode;
    		if( kc==8 ){return false;}
    		countRule($(this).parent(), 0, function(id, oneprice, count, total, max, promotion){
    			$('#buyTotal-'+id).html(total.toFixed(2));
    			if( !isNaN(promotion) ){
    				$('#buyTotal-pro-'+id).html((total*promotion).toFixed(2));
    			}
    		});
    	})
    	.on('focus','.dialog-buy .buy-handle input',function(){
    		$(this).select();
    	})
    	.on('submit','form.handle,form.pro-box-2',function(){return false})
    	.on('click','.dialog-buy .btns .c-btn',function(e){
    		// 立即购买
    		var id = $(e.target).parents('.dialog-buy').attr('id').replace('buy','buyform'),
    			flag = /buynow/.test($(e.target).attr('class'));
    		if(flag){
    			addCartAtOnce(id);
    		}else{
    			addCartWithAnim(id);
    		}
    	}).hoverView({
            over: {
                func: function(){$(this).find('[class*="pro-box"]').removeClass('hide');}
            },out: {
                func: function(){$(this).find('[class*="pro-box"]').addClass('hide');},
                delay: 0
            }
        },'.pro,.set');
    	
    	
    }
    
    function getListCallback(){
    	/*promotion相关*/
    	$('.dialog-buy .buy-handle input').each(function(){
    		countRule($(this).parent(), 0, function(id, oneprice, count, total, max, promotion){
    			$('#buyTotal-'+id).html(total.toFixed(2));
    			if( !isNaN(promotion) ){
    				$('#buyTotal-pro-'+id).html((total*promotion).toFixed(2));
    			}
    			/*if( !max ){
	    			$('#good-'+id+',#buy-'+id+',#more-'+id).find('.c-btn-red,.c-btn-buynow').addClass('disabled');
	    			$('#good-'+id).find('.c-btn-red').html('暂无库存');
	    		}*/
    		});
    	});
    }
    
    main();
})(jQuery,this);