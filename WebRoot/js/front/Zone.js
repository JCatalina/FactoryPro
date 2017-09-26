var Zone = {data:null,index:0,value:[0,0,0],text:['','',''],level:3};
// 获取地址
Zone.getData = function (cb){
	var _this = this;
	$.ajax({
		url:'js/area/zone.json',
		dataType:'json',
		success:function(data){
			_this.data = data.data;
			typeof cb === 'function' && cb(_this.data);
		},error:function(){
			// demo
			_this.data = [{
	            "id": "2",
	            "name": "\u5317\u4eac\u5e02",
	            "child": [{
	                "id": "2288",
	                "name": "\u4e1c\u57ce\u533a"
	            }, {
	                "id": "2301",
	                "name": "\u5927\u5174\u533a"
	            }, {
	                "id": "2300",
	                "name": "\u660c\u5e73\u533a"
	            }]
	        }];
			typeof cb === 'function' && cb(_this.data);
		}
	});
};
Zone.change = function(obj){
	var _this = this, $obj = $(obj);
	
	var i = $obj.index(), $selected = $obj.find(':selected');
	_this.value[i] = $selected.attr('ref');
	_this.text[i] = _this.value[i] == 0 ?  "" : $selected.html();
	for (var j = _this.level - 1; j > i; j--) {
		_this.value[j] = 0;
		_this.text[j] = "";
	}
	_this.format();
	//$('#addr').val(_this.text.join(''));
};
// 绑定事件
Zone.bindEvent = function(){
	var _this = this;
	$('#scroller').on('change','select',function(){
		_this.change(this);
	});
};
// render
Zone.f = function(data){
	var _this = this;
	var item = data;
	if (!item) {
		item = [];
	};
	var str = _this.value[_this.index] ? '' : '<option value="">--请选择--</option>';
	var focus = 0, childData, nowData;
	
	if (_this.index !== 0 && _this.value[_this.index - 1] == "0") {
		str = '<option value="" selected>--请选择--</option>';
		_this.value[_this.index] = 0;
		_this.text[_this.index] = "";
		focus = 0;
	}else{
		if (_this.value[_this.index] == "0") {
			str = '<option value="" selected>--请选择--</option>';
			focus = 0;
		}
		/// 区自动选择第一项
		if( _this.index==2 && _this.value[2]==0 && item[0] ){
			str = '';
			_this.value[_this.index] =  item[0].id;
		}
		console.log(_this.value)
		for (var j = 0, len = item.length; j < len; j++) {
			var pid = item[j].pid || 0;
			var id = item[j].id || 0;
			var cls = '';
			if (_this.value[_this.index] == id) {
				cls = "selected";
				focus = id;
				childData = item[j].child;
			};
			str += '<option pid="' + pid + '" ' + cls + ' ref="' + id + '" value="'+ item[j].name +'">' + item[j].name + '</option>';
		}
	}
	var $child = $('#scroller select').eq(_this.index);
	var prevIndex = _this.index-1;
	prevIndex = prevIndex<0?0:prevIndex;
	$child.html(str);
	_this.index++;
	if (_this.index > _this.level - 1) {
		_this.index = 0;
		// 当区没有数据，直辖市
		if( !item.length && _this.text[prevIndex] ){
			str = '<option value="" selected>'+_this.text[prevIndex]+'</option>';
			$child.html(str);
		}
		return;
	}
	_this.f(childData, nowData);
};
Zone.format = function(){
	this.f(this.data);
};
Zone.init_cb = function(){};
// 初始化
Zone.init = function(cb){
	var _this = this;
	this.getData(function(){
		_this.format();
		typeof cb === 'function' && cb();
	});
	this.bindEvent();
}