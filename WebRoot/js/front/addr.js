// 地址，地址管理用 & 确认订单用
;(function($,window){
	// 地区选择
	Zone.init();
	// 地址默认checkbox使用
	$.fn.checked = function(flag){
		if( this.is(':radio,:checkbox') ) this.prop('checked',flag).val(flag?1:0);
	}
	
	// 地址组件
	var Addr = {
		S: {
			'icon-add'   :'i-add',
			'icon-edit'  :'i-edit',
			'title-add'  :'新增地址',
			'title-edit' :'修改地址'
		},
		$icon: $('#addr-icon'),
		$title: $('#addr-title'),
		$dialog: $('#addrEdit'),
		$manage: $('#addrList'),
		$list: $('.p-a-list')
	};
	
	
	// 绑定事件
	Addr.bindEvent = function(){
		var _this = this;
		// 默认
		$('#set_default').click(function(){
			$(this).checked(this.checked);
		});
		
	}
	
	
	// 获取地址
	Addr.getAddrByIndex = function(id){
		if(!id) return false;
		var addr = {
			id:'', company:'', name:'', province:'', city:'', zone:'', addr:'', phone:'', isdefault:''
		},$addr = this.$list.find('[for="addr'+id+'"]');
		$.each(addr,function(i,o){
			addr[i] = $addr.find('.'+i).html()||'';
		});
		addr.isdefault = !!$addr.find('.def').length;
		addr.id = $('#addr'+id).val();
		
		return addr;
	}
	
	
	// 显示编辑对话框
	Addr.show = function(type, id){
		var flag = /add/.test(type),
		title = this.S['title-'+type],
		iconCln = this.S['icon-'+type],
		_this = this;
		
		this.$icon.attr('class',iconCln);
		this.$title.html(title);
		$.mydialog.show(this.$dialog[0]);
		
		var addr = this.getAddrByIndex(id);
		if( id && addr.id ){
			console.log(addr)
			$.each(addr,function(i,o){
				var $input = _this.$dialog.find('.'+i),
					flag = $input.length && $input.is('input,select'),
					sel_flag = $input.is('select');
				flag && $input.val(o);
				sel_flag && Zone.change($input[0]);
			});
			$('#set_default').checked(addr.isdefault);
		}else{
			this.$dialog.find('[type=text],select').val('');
			Zone.change(this.$dialog.find('select')[0]);
			$('#set_default').checked($('.address-box>label[for]').length<1);
		}
	}
	// 隐藏
	Addr.hide = function(){$.mydialog.hide(this.$dialog[0]);}
	
	Addr.hideManage = function(){$.mydialog.hide(this.$manage[0]);}
	
	Addr.showManage = function(){$.mydialog.show(this.$manage[0]);}
	
	Addr.submit = function($btn){
		$btn = $btn || $('#addrEditBtn');
		var arr = [
			    {sel:'#addrEditForm .company'},
			    {sel:'#addrEditForm .province'},
			    {sel:'#addrEditForm .city'},
			    {sel:'#addrEditForm .addr'},
			    {sel:'#addrEditForm .name'},
			    {sel:'#addrEditForm .phone'}
			],
			$form = $('#addrEditForm'),
			url = 'user/userAddr!ajaxEditAddr',
			data = $form.serialize()+'&isEdit='+this.$dialog.find('.i-edit').length,
			success = function(){location.reload()},
			err = function(){alert('提交编辑地址错误');$btn.removeClass('disabled').html('保存');},
			flag;
		/*
		$form.find(':text,select:not(.zone)').each(function(){
			if(!this.value) flag = false;
		});*/
		flag = cep.chkForm(arr,null,true);
		//alert(data);
		if( flag && !$btn.is('disabled') ){
			$btn.addClass('disabled').html('正在保存');
			$.ajax({type:'post',url:url,data:data,success:success,error:err});
		}
	}
	// 删除
	Addr.del = function(id){
		var flag = confirm('确认删除该地址？');
		if( flag ){
			var url = 'user/userAddr!ajaxDeleteAddr',
				data = 'id='+id,
				success = function(){location.reload();},
				err = function(){alert('地址删除出错')}
			$.ajax({type:'post',url:url,data:data,success:success,error:err});
		}
	}
	
	Addr.init = function(cb){
		this.bindEvent();
		typeof cb === 'function' && cb();
	}
	window.Addr = Addr;
})(jQuery,window);