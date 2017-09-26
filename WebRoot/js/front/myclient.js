// 我的客户
;(function($,window){
	var Client = {
		S: {
			//'icon-add'   :'i-add',
			//'icon-edit'  :'i-edit',
			'title-add'  :'新增客户',
			'title-edit' :'修改客户',
			'url-add': 'user/userClient!add',
			'url-edit': 'user/userClient!edit',
			'url-del': 'user/userClient!delete'
		},
		//$icon: $('#client-icon'),
		$form: $('#addForm'),
		$title: $('#client-title'),
		$dialog: $('#addClient'),
		$list: $('.p-s-item')
	};
	
	Client.getClientById = function(id){
		if(!id) return false;
		var client = {
			id:'', name:'', tel:'', addr:'', mail:'', carNo:''
		},$client = $('#item-'+id);
		$.each(client,function(i,o){
			client[i] = $client.find('.js-'+i).html()||'';
		});
		client.id = id;
		
		return client;
	}
	
	Client.show = function(type,id){
		var flag = /add/.test(type),
		title = this.S['title-'+type],
		//iconCln = this.S['icon-'+type],
		_this = this;
		
		//this.$icon.attr('class',iconCln);
		this.$title.html(title);
		$.mydialog.show(this.$dialog[0]);
		this.$form.attr('action',this.S['url-'+type]);
		
		if( id ){
			var client = this.getClientById(id);
			this.$dialog.find('.js-id').attr('name','id');
			console.log(client)
			$.each(client,function(i,o){
				var $input = _this.$dialog.find('.js-'+i),
					flag = $input.length && $input.is('input,select,textarea');
				flag && $input.val(o);
			});
		}else{
			this.$dialog.find('[type=text]').val('');
			this.$dialog.find('.js-id').removeAttr('name');
		}
	}
	
	Client.hide = function(){$.mydialog.hide(this.$dialog[0]);}
	
	// 删除
	Client.del = function(id){
		var flag = confirm('确认删除该客户？');
		if( flag ){
			var url = this.S['url-del'],
				data = 'id='+id,
				success = function(){location.reload();},
				err = function(){alert('客户删除出错')}
			$.ajax({type:'post',url:url,data:data,success:success,error:err});
		}
	}
	
	Client.submit = function(){
		var $form = $('#addForm'),
			$btn = $form.find('.c-btn-red'),
			url = $form.attr('action'),
			success = function(data){location.reload()},
			err = function(){$btn.removeClass('disabled').html('保存信息');},
			flag = true;
		
		var arr = [];
		$form.find('[name]').each(function(i,o){
			arr.push({sel:'#addForm .'+o.className});
		});
		
		if( !$btn.is('disabled') ){
			$btn.addClass('disabled').html('正在保存');
			cep.ajaxSubmit(arr, null, null,{
	    		url: url//, data: data
	    		//errorable: true
	    	},success,err,true);
		}
	}
	
	Client.bindEvents = function(){
		var _this = this;
		$('#addForm').submit(function(){
			_this.submit();
			return false;
		});
	}
	Client.init = function(){
		Client.bindEvents();
	}
	
	Client.init();
	
	window.Client = Client;
})(jQuery,window);