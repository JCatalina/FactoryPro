// 登录页使用
// 表单验证
function chkLoginForm(){
 var flag,
     arr = [{
         sel: '#loginForm [name="user.username"]'
     },{
         sel: '#loginForm [name="user.password"]'
     }/*,{
         sel: '#loginForm [name="checkCode"]'
     }*/];

 if( $('#loginBtn').is('.disabled') ){
     return false
 }else{
 	 flag = cep.chkForm(arr);
     $('.warn-ok').show();

     if( flag ){
         $('#loginBtn').addClass('disabled').html('正在登录...');
         remember($(arr[0].sel).val());
     }

     return flag;
 }

}
// 记住我
function remember(account){
	var flag = $('#remember')[0].checked?1:0;
	$.ajax({
			type:"post",
			url:"index!remember.action",
			data:"flag="+flag+"&account="+account,
			async:true,
			dataType:'Json',
			success:function(value){
				if(value.res.status!=0){
					alert("通讯失败");
				}
			}
		});
}

// 验证单项
var sel_obj = {
	"username":  '#loginForm [name="user.username"]',
	"password":  '#loginForm [name="user.password"]'
	//"checkCode": '#loginForm [name="checkCode"]'
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
	var flag = cep.chkForm(arr);
}

;(function($,window){
	// 选项卡
	/*$('#login-tag a').click(function () {
	    var ind = $(this).index(), action = $(this).attr('href');
	    $('#login-tag a').removeClass('cur').eq(ind).addClass('cur');
	    $('#loginForm').attr('action',action);
	    return !1;
	});*/
	
	// change
	$('#loginForm').find('[name="user.username"],[name="user.password"]').change(function(){
		chgChkByType($(this).data('type'));
	});
	
	// submit
	$('#loginForm').submit(chkLoginForm);
})(jQuery,window);