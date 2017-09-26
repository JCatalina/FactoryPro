// 我的流通记录
;(function($,window){
	window.view = function(index){
		var $dialog = $('#recordView'),
			$item = $('#item-'+index),
			$hidden = $item.find(':hidden'),
			arr = $hidden.val().split(',');
		$.each(arr,function(i,o){
			$dialog.find('input').eq(i).val(o);
		});
		
		$.mydialog.show($dialog[0]);
	}
})(jQuery,window);