// 修改密码
;(function($,window){
	
	function chkForm(){
	 var flag,
	     arr = [{
	         sel: '#editPwd [name="oldPassword"]'
	     },{
	         sel: '#editPwd [name="password"]'
	     },{
	         sel: '#editPwd [name="password2"]'
	     }];

	 if( $('#editPwdBtn').is('.disabled') ){
	     return false
	 }else{
	 	 flag = cep.chkForm(arr);
	     //$('.warn-ok').show();
	     if( flag ){
	    	 if( $(arr[0].sel).val() == $(arr[1].sel).val() ){
	    		 alert('新密码不能与旧密码相同');
	    		 return false;
	    	 }
	         $('#editPwdBtn').addClass('disabled').html('正在保存...');
	         //alert('修改成功');
	         //Toast.loading('show','正在保存...');
	     }
	     
	     return !!flag;
	 }

	}

	// 验证单项
	var sel_obj = {
		"p1": '#editPwd [name="oldPassword"]',
		"p2": '#editPwd [name="password"]',
		"p3": '#editPwd [name="password2"]'
	};
	function chgChkByType(type){
		var sel = sel_obj[type];
		var arr = [{
	        sel: sel
	    }],
	    	param = {};
		
		var flag = cep.chkForm(arr);
	}
	
	
	formChk.pwdSelectors = ['#editPwd [name="password"]','#editPwd [name="password2"]'];
	// change
	$('#editPwd').find('input').change(function(){
		chgChkByType($(this).data('type'));
	});
	// submit
	$('#editPwdBtn').click(function(){
		$('#editPwd').submit();
	});
	$('#editPwd').submit(chkForm);
})(jQuery,window);