// 注册页第二步使用

var sel_obj = {},sel_arr = [];
//
function getArrByRequire(){
	$('.form-line .require,.form-line .require_allow_empty').each(function(i,o){
		var $next = $(o).next(), flag = $next.is('input'), sel;
		if( flag ){
			sel = '#regForm [name="'+$next.attr('name')+'"]';
			sel_arr.push({sel:sel});
			sel_obj[$next.data('type')] = sel;
		}
	});
	sel_arr.push({sel:'#province'});
	sel_arr.push({sel:'#city'});
	sel_arr.push({sel:'#addr'});
	sel_obj['province'] = '#province';
	sel_obj['city'] = '#city';
	sel_obj['addr'] = '#addr';
}
// 表单验证
function chkRegForm(){
 var flag,
 	 business,
 	 upload,
     arr = sel_arr;

 if( $('#regBtn').is('.disabled') ){
     return false
 }else{
 	 flag = cep.chkForm(arr);
     $('.warn-ok').show();
     
     if($('.business').length){
	     business = !!$('.business :checked').length;
		 $('#warn-business').toggleClass('on',!business);
     }else{
    	 business = true;
     }
     
	 // upload = !!$('#upload').val() || $('#upload').parent().find('img').attr('src')!='images/front/img-none.png';
     upload = true;
	 $('#warn-upload').toggleClass('on',!upload);
	 
	 flag = flag && business && upload;
     
     
     if( flag ){
         $('#reg-confirm').removeClass('hide');
     }else{
    	 $('#reg-confirm').addClass('hide');
     }
     return flag;
 }
}

// 验证单项

function chgChkByType(type){
	var sel = sel_obj[type];
	var arr = [{
        sel: sel
    }],
    	param = {};
	/*
	param[type] = $(sel).val();
	if(type==='password'){
		param['account'] = $(sel_obj['account']).val();
	}
	
	cep.ajaxSubmit(arr, param, null,{
		url: 'login!ajaxCheck',
		errorable: true
	},function(data){
		console.log(data)
		var $label = $($(sel).data('label'));
        if( data.res.status!=0 ){
            $label.html( data.res.errMsg ).addClass('on');
        }else{
            $label.html('').removeClass('on');
        }
	});*/
	var flag = cep.chkForm(arr);
	console.log(sel)
}

;(function($,window){
	getArrByRequire();
	// 主营业务
	$('#business').click(function(e){
		e.stopPropagation();
		$(this).toggleClass('active');
		$('.business').toggleClass('hide');
	});
	$('.business').click(function(e){e.stopPropagation();});
	$('.business :checkbox').click(function(){
		if( /all/.test($(this).attr('class')) ){
			var id = $(this).attr('class').split('-')[1];
			$('[ref*="'+id+'"]').prop('checked',this.checked);
		}
		var str = '';
		$('.business :checked:not([class*=all])').each(function(i,o){
			i&&(str+=','),str+=$(o).next().html();
		});
		$('#warn-business').toggleClass('on',!str);
		str = str ? '已选类目' : '请选择类目';
		$('.chosen-text').html(str);
	});
	
	// 地区
	Zone.init(function(){
		typeof now_addr !== 'undefined' && $.each(now_addr, function(i,o){
			var $select = $('#'+i);
			if(o){
				$select.val(o);
				Zone.change($select[0]);
			}
		});
	});
	
	// change
	$('#regForm').find('.form-line .require+input,.form-line .require_allow_empty+input,#province,#city,#zone,#addr').change(function(){
		chgChkByType($(this).data('type'));
	});
	$(document).click(function(e){
		$('#business').removeClass('active');
		$('.business').addClass('hide');
	});
	
	// submit
	$('#regBtn').click(chkRegForm);
	$('#regForm').submit(chkRegForm)
	$('#reg-confirm .reg-btn').click(function(){$('#regForm').submit();$('#regBtn').addClass('disabled').html('正在提交..');});
	
	// 文件上传
	/*$('#upload').change(function(e){
		fileChg(e,function(flag){
			$('#warn-upload').toggleClass('on',!flag);
			$('.lb-btn[for="upload"]').html(flag?'已选营业执照':'上传营业执照');
		});
	});*/
	$('#upload,#upload2,#upload3').change(fileChg);
})(jQuery,window);