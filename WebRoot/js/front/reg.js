// 注册页使用
var getCodeTimer = null;
// 倒计时
function countDown(s){
    s--;
    var flag = s<1,
        str = flag ? '获取验证码' : '重新获取（'+s+'）',
        act = (flag ? 'remove' : 'add') + 'Class';
    $('#getCodeBtn')[act]('disabled').html(str);
    if(flag){
        clearTimeout(getCodeTimer);
    }else{
        getCodeTimer = setTimeout(function(){countDown(s)},1000);
    }
}
// 获取验证码
function getCodeBtn(){
    var clickFlag = $('#getCodeBtn').is('.disabled');
    if(clickFlag) return false;
    
    var arr = [{
        sel: '#regForm [name="username"]'
    }];

    $('#getCodeBtn').addClass('disabled').html('正在获取');
    // IValue:0
    cep.ajaxSubmit(arr,{mobile:$(arr[0].sel).val(),IValue:0},null, {
        url: 'front/login!ajaxGetPhoneCode',errorable: true
    },function(data,request){
    	if(data.res.status==0){
    		countDown(60);
    		alert(cep.String['code-alert']);
    	}else{
    		$('#getCodeBtn').removeClass('disabled').html('获取验证码');
    		alert(data.res.errMsg);
    	}
    },function(){
        $('#getCodeBtn').removeClass('disabled').html('点击获取');
    });
    /*
    // demo
    var flag = cep.chkForm(arr);
    if( flag ){
    	countDown(60);
    }*/
}

// 表单验证
function chkRegForm(){
 var flag,
 	 //read = $('#read')[0].checked,
     arr = [{
         sel: '#regForm [name="username"]'
     },{
         sel: '#regForm [name="password"]'
     },{
    	 sel: '#regForm [name="password2"]'
     },{
    	 sel: '#regForm [name="phoneCode"]'
     },{
    	 sel: '#read'
     }];

 if( $('#regBtn').is('.disabled') || $('.reged').length ){
     return false
 }else{
 	 flag = cep.chkForm(arr);
     $('.warn-ok').show();
     
     /*if( !read ){
    	 alert('请确认同意注册协议');
     }
     flag = flag && read;*/
     if( flag ){
         $('#regBtn').addClass('disabled').html('正在注册...');
         $('#reg-suc').removeClass('hide');
     }

     return flag;
 }

}

// 验证单项
var sel_obj = {
	"username":  '#regForm [name="username"]',
	"password":  '#regForm [name="password"]',
	"password2": '#regForm [name="password2"]',
	"code":      '#regForm [name="phoneCode"]',
	"read":      '#read'
};
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
	if( type == 'code' ){
		cep.ajaxSubmit(arr, {mobile:$(sel).val()}, null,{
			url: 'front/login!ajaxCheckPhoneCode',
			errorable: true
		},function(data){
			var $label = $($(sel).data('label'));
	        if( data.res.status!=0 ){
	            $label.html( data.res.errMsg ).addClass('on');
	        }else{
	            $label.html('请输入验证码').removeClass('on');
	        }
	        console.log(data);
		});
	}else if( type == 'username' ){
		cep.ajaxSubmit(arr, {username:$(sel).val()}, null,{
			url: 'front/login!ajaxCheckUsername',
			errorable: true
		},function(data){
			var $label = $($(sel).data('label'));
	        if( data.res.status!=0 ){
	            $label.html( data.res.errMsg ).addClass('on reged');
	        }else{
	            $label.html('请输入11位手机号码').removeClass('on reged');
	        }
	        console.log(data);
		});
	}else{
		var flag = cep.chkForm(arr);
	}
}

;(function($,window){
	// 验证码
	$('#getCodeBtn').click(getCodeBtn);
	
	// change
	formChk.pwdSelectors = ['#regForm [name="password"]','#regForm [name="password2"]'];
	$('#regForm').find('[name="username"],[name="phoneCode"],[name="password"],[name="password2"]').change(function(){
		chgChkByType($(this).data('type'));
	});
	$('#read').click(function(){
		chgChkByType($(this).data('type'));
	});
	
	// submit
	$('#regForm').submit(chkRegForm);
})(jQuery,window);