// 个人信息
;(function($,window){
	
	function chkForm(){
	 var flag,
	     arr = [{
	         sel: '#editInfo [name="queryUser.showName"]'
	     },{
	         sel: '#editInfo [name="queryUser.mail"]'
	     }];

	 if( $('#editInfoBtn').is('.disabled') ){
	     return false
	 }else{
	 	 flag = cep.chkForm(arr);
	     //$('.warn-ok').show();
	     if( flag ){
	         $('#editInfoBtn').addClass('disabled').html('正在保存...');
	         //alert('修改成功');
	         //Toast.loading('show','正在保存...');
	     }
	     
	     return !!flag;
	 }

	}

	// 验证单项
	var sel_obj = {
		"showName": '#editInfo [name="queryUser.showName"]',
		"mail"    : '#editInfo [name="queryUser.mail"]'
	};
	function chgChkByType(type){
		var sel = sel_obj[type];
		var arr = [{
	        sel: sel
	    }],
	    	param = {};
		
		var flag = cep.chkForm(arr);
	}
	
	// change
	$('#qimg').change(fileChg);
	$('#editInfo').find('input:not(:file)').change(function(){
		chgChkByType($(this).data('type'));
	});
	// submit
	$('#editInfoBtn').click(function(){
		$('#editInfo').submit();
	});
	$('#editInfo').submit(chkForm);
})(jQuery,window);