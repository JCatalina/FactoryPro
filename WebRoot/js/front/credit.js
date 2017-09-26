;(function($,window){
	// 积分商品对象
	var Item = function(id, name, detail, needCredit, img, sellCount, thingCount){
		var self        = this;
		self.id         = id;
		self.name       = name;
		self.detail     = detail;
		self.needCredit = needCredit;
		self.img        = img;
		self.sellCount  = sellCount;
		self.thingCount = thingCount;
	}
	
	var Page = function(obj){
		var self = this;
		self.pageCount   = obj.pageCount||0;
		self.firstIndex  = obj.firstIndex||0;
		self.maxresult   = obj.maxresult||0;
		self.totalResult = obj.totalResult||0;
		
		self.htmls = ko.computed(function(){
			var len     = self.pageCount,   // 总页数
				current = self.firstIndex,  // 当前页码
				i       = self.firstIndex-3,// 起始页数
				end     = self.firstIndex+3,// 结束页数
				html    = '',
				temp    = '';
			i = i<0?0:i;
			end = end>len?len:end;
			
			if( i>3 ){
				html+='<a href="javascript:void(0)">1</a> <span>...</span> ';
			}
			
			for(;i<end;i++){
				var cur = i+1;
				temp = cur == current
					 ? '<i>'+cur+'</i> '
					 : '<a href="javascript:void(0)">'+cur+'</a> ';
				html+=temp;
			}
			
			if( end<len-3 ){
				html+='<span>...</span> <a href="javascript:void(0)">'+len+'</a> ';
			}
			return html;
		}.bind(self));
	}
	
	// 绑定事件
	$('.list_pages').on('click','a',function(){
		var index = $(this).html()*1;
		if( !isNaN(index) ){
			vm.getInfos.bind(vm,index)();
		}
	});
	
	// 获取积分商品页面信息
	function getInfos(firstIndex,sort){
		var self = this;
		var _index = typeof firstIndex === 'number'?firstIndex:self.firstIndex();
		var _sort  = typeof sort==='string'?sort:self.sort();
		$.ajax({type:'get',url:'creditGoods!creditGoodsUI'
			,data:{isAjax:1,sort:_sort,firstIndex:_index}
			,dataType:'json'
			,error:function(){alert('获取失败')}
			,success:function(data){
				console.log(data)
				if(data.res.status==0){
					var credit = data.inf.userCredit*1;
					credit = isNaN(credit) ? 0 : credit;
					self.userCredit(credit);
					self.list(data.inf.pts);
					self.page(new Page(data.inf));
					self.firstIndex(_index);
					self.sort(_sort);
				}else{
					alert(data.res.errMsg||'获取出错');
				}
			}
		});
	}
	
	function ViewModel(){
		var self = this;
		
		this.isLogin = window.g_islogined=='true';
		
		this.userCredit = ko.observable(0);
		// 列表
		this.list = ko.observableArray([]);

		// 当前商品
		this.current = ko.observable(new Item());
		
		this.firstIndex = ko.observable(0);
		
		// 当前排序方式：normal,max,min
		this.sort = ko.observable('normal');
		
		// 
		this.getOthSort = function(){
			var sort = this.sort();
			if( sort == 'max' ){
				sort = 'min';
			}else{
				sort = 'max';
			}
			return sort;
		}.bind(this);
		
		// 页面
		this.page = ko.observable(new Page({}));

		// 获取信息
		this.getInfos = getInfos.bind(this);
		
		// 显示商品详细
		this.showItem = function(item){
			console.log(item)
			this.current(item);
			$.mydialog.show('#exchange');
		}.bind(this);
		
		// 兑换积分商品
		this.exchange = function(){
			var item = this.current(),
				isLogin = this.isLogin,
				need = this.current().needCredit,
				user = this.userCredit();
			if(!isLogin){
				//location.href = 'front/login!loginUI?loginType=1';
				loginWithUrl('front/login!loginUI?loginType=1');
				return false;
			}
			
			if(user<need){
				alert('您的积分不足'+need+'分，前往购物收集更多积分吧');
				location.href='index!GoodsList?index_showType=0';
				return false;
			}
			
			var arr_vals = [
			    {sel:'#exchange [name=t_province]'},
			    {sel:'#exchange [name=t_city]'},
			    {sel:'#exchange [name=t_area]'},
			    {sel:'#exchange [name=t_addr]'},
			    {sel:'#exchange [name=consignee]'},
			    {sel:'#exchange [name=tel]'}
			],
			othParam = {id:item.id,count:1};
			
			cep.ajaxSubmit(arr_vals, othParam, null, {url: 'creditGoods!createCreditOrder',type:'post',dataType:'json'},function(data){
				console.log(data)
				if(data.res.status==0){
					alert('兑换成功，请耐心等待审核');
					location.reload();
		    	}else{
		    		alert(data.res.errMsg);
		    	}
			},null,true);
		}.bind(this);
	}

	var vm = new ViewModel();
	ko.applyBindings(vm,document.getElementById('creditList'));
	vm.getInfos();
	
	// 地区
	Zone.init(function(){
		typeof now_addr !== 'undefined' && $.each(now_addr, function(i,o){
			var $select = $('#exchange .'+i);
			if(o){
				$select.val(o);
				Zone.change($select[0]);
			}
		});
	});
})(jQuery,window);