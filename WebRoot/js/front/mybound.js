// 我的出入库
;(function($,window){
	var Bound = {
		S: {
			'url-outbound':'user/userWarehouse!editOutOrderUI?id=',
			'url-inbound' :'user/userWarehouse!editInOrderUI?id='
		},
		$outbound: $('#addOutbound'),
		$inbound: $('#addInbound'),
		$out_iframe: $('#editOutboundIframe'),
		$in_iframe: $('#editInboundIframe')
	};
	
	Bound.showBound = function(type,id){
		var url = this.S['url-'+type+'bound']+id,
			flag = /out/.test(type),
			$iframe = this['$'+type+'_iframe'];
		$iframe.attr('src',url);
		$.mydialog.show(this['$'+type+'bound'][0]);
		return false;
	}
	
	window.Bound = Bound;
})(jQuery,window);