// 我的商品
;(function($,window){
	var Goods = {
		S: {
			'icon-add'   :'i-add',
			'icon-edit'  :'i-edit',
			'title-add'  :'增加商品',
			'title-edit' :'查看商品',
			'url-add': 'user/userGoods!add',
			'url-edit': 'user/userGoods!edit',
			'url-del': 'user/userGoods!delete',
				
			'url-outbound':'user/userWarehouse!addOutOrderUI',
			'url-inbound':'user/userWarehouse!addInOrderUI'
		},
		$icon: $('#goods-icon'),
		$form: $('#addForm'),
		$title: $('#goods-title'),
		$dialog: $('#addGood'),
		$list: $('.p-s-item'),
		
		$outbound: $('#addOutbound'),
		$inbound: $('#addInbound'),
		$out_iframe: $('#addOutboundIframe'),
		$in_iframe: $('#addInboundIframe'),
		
		$record: $('#recordView')
		
	};
	
	
	Goods.getGoodById = function(id){
		if(!id) return false;
		var good = {
			id:'', goodsName:'', img:'', type:'', price:'', count:'', typeName:'', brandName:'', brandType:'', no:'', oriNo:'', brandNo:'',
			more1Name:'', more2Name:'', more3Name:'', 
			more1Value:'', more2Value:'', more3Value:'', 
			cost:'', lastInTime:'', lastInSuppliers:'', 'goods.id':'', 'user.id':''
		},$good = $('#item-'+id);
		$.each(good,function(i,o){
			var $obj = $good.find('.js-'+i);
			if( /\./.test(i) ){
				$obj = $good.find('.js-'+i.replace('.',''));
			}
			good[i] = $obj.html()||$obj.val()||$obj.attr('src')||'';
		});
		good.id = id;
		
		return good;
	}
	
	Goods.show = function(type, id){
		var flag = /add/.test(type),
		title = this.S['title-'+type],
		iconCln = this.S['icon-'+type],
		_this = this;
		
		this.$icon.attr('class',iconCln);
		this.$title.html(title);
		$.mydialog.show(this.$dialog[0]);
		this.$form.attr('action',this.S['url-'+type]);
		
		if( id ){
			var good = this.getGoodById(id);
			//this.$dialog.find('.js-id').attr('name','id');
			console.log(good)
			$.each(good,function(i,o){
				var $input = _this.$dialog.find('[name="userGoods.'+i+'"]'),
					flag = $input.length && $input.is('input,select,textarea');
				flag && $input.val(o);
				if( i==='img' ){
					$input.next().find('img').attr('src',o);
				}
			});
		}else{
			this.$dialog.find('input,select,textarea').val('');
			this.$dialog.find('img').attr('src','images/blank.gif')
			//this.$dialog.find('.js-id').removeAttr('name');
		}
	}
	
	
	Goods.hide = function(){$.mydialog.hide(this.$dialog[0]);}
	
	Goods.hideRecord = function(){$.mydialog.hide(this.$record[0]);}
	
	
	Goods.showBound = function(type){
		var url = this.S['url-'+type+'bound'],
			flag = /out/.test(type),
			$iframe = this['$'+type+'_iframe'];
		!$iframe.attr('src') && $iframe.attr('src',url);
		$.mydialog.show(this['$'+type+'bound'][0]);
		return false;
	}
	
	Goods.del = function(id){
		var flag = confirm('确认删除该商品？');
		if( flag ){
			var url = this.S['url-del'],
				data = 'id='+id,
				success = function(){location.reload();},
				err = function(){alert('商品删除出错')}
			$.ajax({type:'post',url:url,data:data,success:success,error:err});
		}
	}
	
	Goods.bindEvents = function(){
		$('.upload').change(fileChg);
		this.$form.submit(function(){
			var arr = [], flag, imgflag,
				$form = $(this),
				$btn = $form.find('.c-btn-red');
			$form.find('[name]').each(function(i,o){
				arr.push({sel:'#addForm [name="'+o.name+'"]'});
			});
			
			imgflag = $form.find('[name="userGoods.img"]').val() || $form.find('[name="imgFile"]').val();
			
			if( !imgflag ){
				alert('请选择商品图片');
				return false;
			}
			
			flag = cep.chkForm(arr,null,true);
			
			if( !$btn.is('disabled') && flag ){
				$btn.addClass('disabled').html('正在保存');
				$form.ajaxSubmit({
		            type: 'post',
		            iframe: true,
		            dataType: 'json',
		            success: function (data) {
						if(data.res.status!=0){
							alert(data.res.errMsg);
							$btn.removeClass('disabled').html('保存信息');
						}else{
							location.reload();
						}
		            },
		            error: function (obj) {
		                alert('保存失败');
		                $btn.removeClass('disabled').html('保存信息');
		            }
		        });
			}
			return false;
		});
	}
	
	Goods.init = function(){
		this.bindEvents();
	}
	Goods.init();
	
	window.Goods = Goods;
})(jQuery,window);