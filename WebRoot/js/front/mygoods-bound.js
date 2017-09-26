// 我的商品-出入库
;(function($,window){
	var Bound = {
		S:{
			'out-del':'user/userWarehouse!deleteItem',
			'out-edit':'user/userWarehouse!ajaxEditOrderItemInfo',
			
			'in-del':'user/userWarehouse!deleteInItem?id=&inItem.id=',
			'in-edit':'user/userWarehouse!ajaxEditInOrderItemInfo'
		},
		
		get_outDelData: function(id,wid){
			return 'item.id='+id+'&id='+wid;
		},
		get_inDelData: function(id,wid){
			return 'inItem.id'+id+'&id='+wid;
		},
		
		get_outEditData: function(id,price,count,gsf){
			return "item.id="+id+"&item.outPrice="+price+"&item.count="+count+"&item.gongshifei="+gsf;
		},
		get_inEditData: function(id,price,count){
			return "inItem.id="+id+"&inItem.inPrice="+price+"&inItem.count="+count;
		}
	};
	
	// 提交表头
	Bound.headSubmit = function(e){
		var $form = $('#headForm'),
			arr = [],
			flag;
		
		$form.find(':text').each(function(i,o){
			arr.push({sel:'#headForm [name="'+o.name+'"]'});
		});
		flag = cep.chkForm(arr,null,true);
		return flag;
	}
	
	Bound.showChoose = function(){
		$.mydialog.show('#chooseGoods');
	}
	
	Bound.del = function(type,id,wid){
		var url = this.S[type+'-del'],
			data = this['get_'+type+'DelData'](id,wid);
		$.ajax({url:url,data:data,type:'get',success:function(){location.hash = '';location.reload()},error:function(e){alert('删除出错')}});
	}
	// 编辑出库/入库数值
	Bound.edit = function(type,id){
		var isedit = false,
			$obj = $('#item-'+id),
			$price = $('#price_'+id),
			$count = $('#count_'+id),
			$gongshifei = $('#gongshifei_'+id),
			sels = ["#price_"+id,"#count_"+id],
			parr = ['price','int'];
		
		if( type==='out' ){
			sels.push("#gongshifei_"+id);
			parr.push('price');
		}
		isedit = $obj.is('.editing');// 判断是否修改状态
		
		if( isedit ){
			// 判断格式
			var arr = [],
				flag;
			$.each(sels,function(i,o){
				arr.push({
					sel: o,
					pattern: parr[i],
					tips: '请输入数字格式的'+$(o).attr('placeholder')
				});
			});
			flag = cep.chkForm(arr,null,true);
			if(!flag){
				return false;
			}
			
			// 开始异步，完成后刷新
			var url = this.S[type+'-edit'],
				data = this['get_'+type+'EditData'](id,$price.val(),$count.val(),$gongshifei.val());
			
			$('.dialog_scroll_box:eq(0)').addClass('loading');
			
			$.ajax({
				type:'post',url:url,data:data,dataType:'json',success:function(data){
					console.log(data)
					if(data.status=='0'){
						location.hash='';location.reload();
					}else{
						alert(data.errMsg);
					}
				},error:function(){alert('编辑出错')}
			});
		}else{
			
		}
		$obj.toggleClass('editing');
	}
	
	Bound.bindEvents = function(){
		$('#headForm').submit(this.headSubmit);
	}
	
	Bound.init = function(){
		Bound.bindEvents();
		if(location.hash=="#isshow"){
			$.mydialog.show('#chooseGoods');
		}
	}
	Bound.init();
	
	window.Bound = Bound;
})(jQuery,window);


/**异步增加订货单条目信息 出库*/
function ajaxSaveOutOrderItemInfo(goodsId,warehousId){
	var price = $("#price_goods_"+goodsId).val();
	var count = $("#count_goods_"+goodsId).val();
	var gongshifei = $("#gongshifei_goods_"+goodsId).val();
	
	var arr = [],
		sels = ["#price_goods_"+goodsId,"#count_goods_"+goodsId,"#gongshifei_goods_"+goodsId],
		parr = ['price','int','price'],
		flag;
	$.each(sels,function(i,o){
		arr.push({
			sel: o,
			pattern: parr[i],
			tips: '请输入数字格式的'+$(o).attr('placeholder')
		});
	});
	
	flag = cep.chkForm(arr,null,true);
	if(!flag){
		return false;
	}
	$.ajax({
		type:"post",
		url:"user/userWarehouse!ajaxSaveOrderItemInfo",
		data:"id="+warehousId+"&item.outPrice="+price+"&item.count="+count+"&item.user_Goods.id="+goodsId+"&item.gongshifei="+gongshifei,
		dataType:'json',
		success:function(data){
			if(data.status=="0"){
				location.reload()
			}else{
				alert(data.errMsg);
			}
			console.log(data)
		}
	});
}

/**异步增加订货单条目信息 入库*/
function ajaxSaveInOrderItemInfo(goodsId,warehousId){
	var price = $("#price_goods_"+goodsId).val();
	var count = $("#count_goods_"+goodsId).val();
	
	var arr = [],
		sels = ["#price_goods_"+goodsId,"#count_goods_"+goodsId],
		parr = ['price','int','price'],
		flag;
	$.each(sels,function(i,o){
		arr.push({
			sel: o,
			pattern: parr[i],
			tips: '请输入数字格式的'+$(o).attr('placeholder')
		});
	});
	
	flag = cep.chkForm(arr,null,true);
	if(!flag){
		return false;
	}
	$.ajax({
		type:"post",
		url:"user/userWarehouse!ajaxSaveInOrderItemInfo",
		data:"id="+warehousId+"&inItem.inPrice="+price+"&inItem.count="+count+"&inItem.user_Goods.id="+goodsId,
		dataType:'json',
		success:function(data){
			if(data.status=="0"){
				location.reload()
			}else{
				alert(data.errMsg);
			}
			console.log(data)
		}
	});
}

/* 出库方法 */
function ajaxOut(id){
	var flag = !!$('.dialog_scroll_box:eq(0) p').length;
	if( !flag ){
		alert('请先添加订货单条目');
		return false;
	}
	$.ajax({
		type:"post",
		url:"user/userWarehouse!ajaxOut",
		data:"id="+id,
		async:true,
		dataType:'Json',
		success:function(data){
			if(data.status=="0"){					
				//alert(data.inf);
				//window.location.reload()
				top.$.mydialog.hide('#addOutbound');
				top.location.reload();
			}else{
				alert(data.errMsg);
			}
		}
	});
}

/* 入库方法 */
function ajaxIn(id){
	var flag = !!$('.dialog_scroll_box:eq(0) p').length;
	if( !flag ){
		alert('请先添加订货单条目');
		return false;
	}
	$.ajax({
		type:"post",
		url:"user/userWarehouse!ajaxIn",
		data:"id="+id,
		async:true,
		dataType:'Json',
		success:function(data){
			if(data.status=="0"){					
				//alert(data.inf);
				//window.location.reload()
				top.$.mydialog.hide('#addOutbound');
				top.location.reload();
			}else{
				alert(data.errMsg);
			}
		}
	});
}