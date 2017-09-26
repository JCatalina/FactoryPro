// ko
(function(window, $) {
	//getGoodsJson
	var Good = function(o){
		var type_arr = ['配件','用品'];
		var type = o.type || 0;// window.g_front_type || '0';// 0 配件 1 车件
		o = o||{};
		o.place = type == '0' ? o.place: o.spec;
		o.type_tx = type_arr[type];
		// o.name_tx = o.spec ? '('+o.spec+')' + o.name : o.name;
		
		o.underpan = o.underpan || '';
		o.engine = o.engine || '';
		o.more1 = o.more1 || '';
		o.more2 = o.more2 || '';
		o.more3 = o.more3 || '';
		o.seSonPlatformLevels = o.seSonPlatformLevels || '';
		
		// o.lowPrice = o.lowPrice;
		o.isHide = window.g_islogined!=='true';// 是否已登录
		o.isAsk  = !o.isHide && o.lowPrice === 0;// 是否需要询价
		o.isShow = !o.isHide && !o.isAsk;// 正常显示
		
		o.unit_tx = o.unit && ('/'+o.unit) || '';// 单位
		
		//o.saleCount = o.saleCount*1 + o.initSaleCount*1;
		o.saleCount = o.saleCount || 0;
		o.fakeThingCount = o.thingCount;//> 0 ? o.thingCount : 999

		// textInput 购买数量
		o.buyCount = 1;

		// 店铺
		o.shopId = o.shopId || "";
		o.shopName = o.shopName || "";
		o.shopLinkTel = o.shopLinkTel || "";
		o.shopLinkUrl = o.shopLinkUrl || "";
		o.isCollect = o.isCollect || 0;
		// 详情
		o.goodsDetail = '正在加载...';
		o.goods_carsJson = [];
		o.detail_inited = false;
		o.detail_loading = false;
		// 电话
		o.shopLinkTelArr = /\s/.test(o.shopLinkTel) ? o.shopLinkTel.split(' ') : [o.shopLinkTel];
		
		// 促销
		o.promotion = o.promotion || null;
		o.isPro = o.promotion && o.promotion.isWork==0 && o.promotion.pType==0;
		o.isSet = o.promotion && o.promotion.isWork==0 && o.promotion.pType==1;
		// 单价
		o.price = o.isPro ? o.promotion.pDisPrice : o.lowPrice;
		
		ko.mapping.fromJS(o,[],this);
	}
	
	//IE 8以下兼容map方法
	if (!Array.prototype.map) {
		Array.prototype.map = function(callback, thisArg) {
			var T, A, k;
			if (this == null) {
				throw new TypeError(" this is null or not defined");
			}
			var O = Object(this);
			var len = O.length >>> 0;
			if (typeof callback !== "function") {
				throw new TypeError(callback + " is not a function");
			}
			if (thisArg) {
				T = thisArg;
			}
			A = new Array(len);
			k = 0;
			while(k < len) {
				var kValue, mappedValue;
			if (k in O) {
				kValue = O[k];
				mappedValue = callback.call(T, kValue, k, O);
				A[k] = mappedValue;
			}
			k++;
			}
			return A;
		};
	}
	/* 购买套装 */
	Good.prototype.buyset = function(){
		window.addManyCart( 'set-'+this.id() );
	}
	/* 显示电话号码 */
	Good.prototype.showTel = function(){
		var arr = this.shopLinkTelArr(),
			param = [];
			if( arr.length<2 ){
				location.href = 'tel:'+arr[0];
			}else{
				$.each(arr, function(i,o){
					param[i] = {text: o, href: 'tel:'+o}
				});
				Toast.show_list({title:'电话联系',options:param,cancel:'取消',clickhide:true});
			}
	}
	/* 收藏店铺 */
	Good.prototype.collect = function(){
		var self = this;
		if( !this.shopId() ){return false;}
		Toast.loading('show');
		cep.commAjax({url:'m/shopIndex!ajaxCollectShop',data:{shopId:this.shopId()}},function(data){
			if( data.res.status!=0 ){
				Toast.alert('接口有误')
				return ;
			}
			Toast.show('收藏成功');
			self.isCollect(1);
			// 对应全部店铺显示收藏
			if(glvm && glvm.list()){
				glvm.list().map(function(o){
					o.shopId() && o.shopId() == self.shopId() && o.isCollect(1);
				});
			}
		});
	}
	/* 取消收藏 */
	Good.prototype.cancelCollect = function(){
		var self = this;
		if( !this.shopId() ){return false;}
		Toast.loading('show');
		cep.commAjax({url:'m/shopIndex!ajaxCancleCollectShop',data:{shopId:this.shopId()}},function(data){
			if( data.res.status!=0 ){
				Toast.alert('接口有误')
				return ;
			}
			Toast.show('已取消收藏');
			self.isCollect(0);
			// 对应全部店铺显示取消收藏
			if(glvm && glvm.list()){
				glvm.list().map(function(o){
					o.shopId() && o.shopId() == self.shopId() && o.isCollect(0);
				});
			}
		});
	}
	
	
	// 分页组件
	ko.components.register('page-buttons',{
		viewModel: function(params){
			var self = this;
			this.totalRecord = params.totalRecord;// 总条目数
			this.totalPage   = params.totalPage||1;// 总页数
			this.pageSize    = params.pageSize;// 总页数
			this.pageNo      = params.pageNo;// 当前页码
			this.totalTime   = params.totalTime;// 耗时

			this.loading = ko.observable(false);

			var Btn = function(i){
				this.index = i;
				this.isShow = ko.computed(function(){
					var flag = self.totalPage()<5;
					var isShow =  i==self.pageNo() || i-1==self.pageNo() || i+1==self.pageNo() || i==self.totalPage() || i==1;
					return flag || isShow;
				},this);
				this.active = ko.computed(function(){
					return i==self.pageNo();
				},this);
				this.isEll = ko.computed(function(){
					return !this.isShow() && (self.pageNo()-2==i && self.pageNo()-2!=1) || (self.pageNo()+2==i && self.pageNo()+2!=self.totalPage());
				},this);
			}
			this.buttons = ko.computed(function(){
				var buttons = [];
				for(var i=0;i<this.totalPage();i++){
					buttons.push(new Btn(i+1));
				}
				return buttons;
			},this);

			this.goPage = function(btn){
				var n = typeof btn === 'object' ? btn.index*1 : btn*1;
				if( n<1 || n>this.totalPage() || this.loading() ){return false;}
				this.pageNo(n);
				this.loading(true);
				params.callback(function(){
					self.loading(false);
				});
			}.bind(this);
			this.nextPage = function(){
				this.goPage(this.pageNo()+1);
			}.bind(this);
			this.prevPage = function(){
				this.goPage(this.pageNo()-1);
			}.bind(this);
		},
		template: 
		'<div class="list_pages fix">\
			<div class="fl total">共<span data-bind="text: totalRecord"></span>条记录</div>\
			<div class="sn_pages">\
			    <a class="pageprev" href="javascript:void(0)" data-bind="visible: pageNo()>1,click: prevPage">&lt;</a>\
				<!-- ko foreach: buttons -->\
					<!--ko if:active()&&isShow()--><i data-bind="text: index"></i><!--/ko-->\
					<!--ko if:!active()&&isShow()--><a href="javascript:void(0)" data-bind="text: index, click: $parent.goPage"></a><!--/ko-->\
					<span data-bind="if:isEll()">…</span>\
				<!-- /ko -->\
				<a class="pagenext" href="javascript:void(0)" data-bind="visible: pageNo()<totalPage(), click: nextPage">&gt;</a>\
				<span class="ml10">共<span id="maxPage" data-bind="text: totalPage"></span>页，转到</span>\
				<input tyle="text" class="text_pages ml10" id="pageNum" data-bind="attr:{value:pageNo, onchange: \'check_number(this,\'+totalPage()+\')\'}, keydown: function(e){if(e.keycode==13) goPage($(\'#pageNum\').val());}">\
				<input type="button" class="submit_pages" value="Go" data-bind="click: function(){goPage($(\'#pageNum\').val())}">\
			</div>\
		</div>'
	});

	var GoodsListViewModel = function(){
		var type_arr = ['配件','用品'];
		var self = this;
		var ismobile = UA.type != 'pc';
		var type = window.g_front_type || '0';// 0 配件 1 车件
		this.col6_tx = type == '0' ? '位置' : '规格';
		var urls = {
			goodslist: {url: 'search!search', tips: '暂无相关商品', mockkey: 'goodslist'}
			,goodsdetail: {url:'search!getGoodsDetail',mockkey:'goodsdetail'}
		}

		// 商品列表
		this.list = ko.observableArray([]);
		
		// 滚动
		this.scroll = null;
		this.buttons = null;
		// 列表固有参数
		this.firstIndex = ko.observable(1);
		/* 向上加载用 */
		this.startIndex = ko.observable(this.firstIndex());
		this.loading = ko.observable(false);
		// 分页类型：scroll 滚动加载，button 分页按钮
		this.pageType = ko.observable('scroll');

		// 商品列表属性
		this.totalHits = ko.observable();// 总条目数
		this.totalTime = ko.observable();// 耗时
		this.pageSize  = ko.observable();// 每页数
		this.maxSize   = ko.observable();// 总页数
		this.goodsBrands = ko.observableArray([]);// 品牌数组
		
		// 品牌类型
		this.brandTypes = ko.observableArray(['原厂','下线件','品牌','拆车件']);

		// 全车件, 配件
		this.type_tx = type_arr[type];

		// 参数
		this.shopId          = ko.observable(getQueryString('id')); // 店铺idok
		this.showType        = ko.observable(getQueryString('shopShowType')||getQueryString('index_showType'));// 店铺车件、用品
		this.searchWord      = ko.observable(decodeURIComponent(getQueryString('queryGoodsName')||'')); // 关键字ok
		this.searchTypeName  = ko.observable(decodeURIComponent(getQueryString('typeName')||'')); // 分类名称ok
		this.searchBrandName = ko.observable(decodeURIComponent(getQueryString('brandName')||'')); // 品牌名称ok
		
		this.selectBrandName = ko.observable(this.searchBrandName());// 选择用的品牌名称
		
		this.searchBrandType = ko.observable(decodeURIComponent(getQueryString('brandType')||'')); // 品牌分类，暂定
		this.searchTypeLevel = ko.observable(getQueryString('typeLevel')); // 分类级别ok
		this.orderByW        = ko.observable(getQueryString('orderByW')); // 排序字段 ok
		this.orderByT        = ko.observable(getQueryString('orderByT')); // 排序类型 desc asc ok
		
		this.search_params   = ko.observableArray();// 参数展示
		
		// 第一个商品
		this.shop = ko.computed(function(){
			var list = this.list();
			return this.shopId()&&list[0]||false;
		},this);
		
		this.postFilterWord = ko.computed(function(){
			var params = this.search_params();
			if( !params ) return false;
			
			var result = [];
			$.each(params,function(i,o){
				o.checked() && result.push(o.checked());// radio用，checked是字符串
			});
			return result.join();
		},this);
		
		this.params_cache = ko.observable(this.postFilterWord());
		
		this.back = function(flg){var sel = (!flg ? '#filter,' : '') + '#filter-brand,#filter-params';$(sel).removeClass('active');}
		
		this.radioClick = function(parent,o,e){
			var $radio = $('#'+e.target.getAttribute('for'));
			if( $radio[0].checked ){
				$radio[0].checked = false;
				parent.checked('');
				return false;
			}
			return true;
		}
		
		// 返回查询参数对象
		this.list_param = function(reverse){
			var param = {};
			
			this.shopId()          && (param.shopId = this.shopId());
			this.showType()        && (param.showType = this.showType());
			this.searchWord()      && (param.searchWord = this.searchWord());
			this.searchTypeName()  && (param.searchTypeName = this.searchTypeName());
			this.searchBrandName() && (param.searchBrandName = this.searchBrandName());
			this.searchBrandType() && (param.searchBrandType = this.searchBrandType());
			this.searchTypeLevel() && (param.searchTypeLevel = this.searchTypeLevel());
			this.orderByW()        && (param.orderByW = this.orderByW());
			this.orderByT()        && (param.orderByT = this.orderByT());

			this.firstIndex()      && (param.firstIndex = this.firstIndex()*1);
			/*if(this.ccvm){
				this.ccvm.searchName() && (param.searchName = this.ccvm.searchName()); // 搜索字段，配合ccvm
			}*/
			
			this.postFilterWord() && (param.postFilterWord = this.postFilterWord());
			
			if( reverse ){
				param.firstIndex = this.startIndex()*1
			}
			return param;
		}.bind(this);
		
		var isFirst = true;
		// 获取数据
		this.getList = function(cb,reverse){
			if( this.pageType()=='buttons' ){
				$(window).scrollTop(0);
			}
			this.loading(true);
			var opt = urls.goodslist;
			console.log(this.list_param(),'param')
			cep.commAjax({url: opt.url,mockkey:opt.mockkey,data:this.list_param()},function(_data){
				var inf = _data.inf;
				this.loading(false);
				if( !inf ){ console.log('inf 不存在'); return false }
				
				var goodsBrands = inf.goodsBrands,// 品牌数组
					list = inf.hitGoodses,// 商品数组
					params = inf.params;
				
				goodsBrands && goodsBrands.length>this.goodsBrands().length && this.goodsBrands( goodsBrands );
				
				if( this.pageType()==='buttons' ){
					this.list([]);
				}
				list && list.map(function(good){
					self.list.push(new Good(good));
				});
				// params = params || [{arr: ["前","后"],name: "测试demo：位置"},{arr: ["大","小"],name: "测试demo：规格"}];
				params && $.each(params,function(i,o){
					o.checked = ko.observable();
					$.each(o.arr,function(j,b){
						var obj = {};
						obj.name = 'param-'+i
						obj.id = obj.name+'-'+j;
						obj.text = b;
						o.arr[j] = obj;
					});
				});
				params && !this.search_params().length && this.search_params(params);

				this.totalHits(inf.totalHits);
				this.totalTime(inf.totalTime);
				this.pageSize(inf.pageSize);
				this.maxSize(inf.maxSize||1);
				typeof cb === 'function' && cb(inf);
				
				$.fn.scrollLoading && $('#goodsList img[data-url]').scrollLoading();
				
				// 在列表页第一次加载时重新自定义微信分享
				if(isFirst){
					isFirst = false;
					window.sortNavFixed && window.sortNavFixed.updateOuter();// 更新fixed导航高度
					if( wxkit ){
						wxkit.share_update({
							url: location.href,
							title: window.Js_global.platname + (self.searchWord() ? '-' + self.searchWord() : ''),
							imgUrl: $('#goodsList img').length && $('#goodsList img')[0].src,
							desc: self.searchWord() || document.title
						});
					}
				}
			}.bind(this));

		}.bind(this);

		// 获取详情
		this.getDetail = function(o){
			var opt = urls.goodsdetail;
			if( o.detail_inited() ){return false;}
			o.detail_loading(true);
			cep.commAjax({url: opt.url, mockkey: opt.mockkey, data:'id='+o.id()},function(_data){
				var inf = _data.inf;
				o.detail_loading(false);
				if( !inf ){ console.log('inf 不存在'); return false }
				o.goods_carsJson(inf.goods_carsJson);
				o.goodsDetail(inf.goodsDetail);
				o.detail_inited(true);
			});
		}.bind(this);

		// 更换参数
		this.reGetList = function(key, value){
			console.log(arguments,'arguments')
			if(typeof key === 'string'){
				if( value==='lowPrice' && this.orderByW()===value && !this.orderByT() ){
					this.orderByT('asc');
				}else{
					this.orderByT('');
				}
				
				value = typeof value === 'object' ? '' : value;
				this[key](value);
			}
			
			this.list([]);
			if(self.pageType()=='scroll'){
				this.scroll.isEnd(false);
			}
			if( key != 'firstIndex' ){
				this.firstIndex(1);
			}
			// 左上角
			var str = '选车型',flg;
			if( this.ccvm ){
				if(this.ccvm.carSeries()||(this.ccvm.searchName&&this.ccvm.searchName())){
	            	if(/通用/.test(this.ccvm.searchName())){
	            		str = '通用车型';
	            	}else{
	            		str = this.ccvm.carSeries().substring(0,4);
	            	}
	            	$('.head-search .bn-choose').html(str);
	            }
				flg = !!this.ccvm.pro_series;
				this.ccvm.pro_maker = this.ccvm.pro_series = '';
			}
			
			this.getList(function(inf){
				if(self.pageType()=='scroll'){
					self.scroll.isEnd(inf.isEnd);
	                self.scroll.onOff(true);
	                self.firstIndex(self.firstIndex()+1);
				}
			});
			
			this.back(flg);
			/*if(window.UA.type!='pc' ){//&& window.myhash && window.myhash.history.length>1
				//var $page = $('#page-index'), dataFunc = $page.attr('data-func');
				//$page.attr('data-func','');
				//history.go(-1);
				// if(!flg) location.hash = '';
				if(!flg) this.back();
				//setTimeout(function(){$page.attr('data-func',dataFunc);},300);
			}*/
		}.bind(this);

		// 选择品牌
		this.setBrandName = function(o){
			o = typeof o === 'string' ? o : '';
			this.selectBrandName(o);
		}.bind(this);
		
		this.setBrandNameBack = function(o){
			o = typeof o === 'string' ? o : '';
			this.setBrandName.apply(this,arguments);
			
			var brand = this.selectBrandName();
			if( brand != this.searchBrandName() ){
				this.searchBrandName(brand);
				this.reGetList();
			}else{
				this.back();
			}
		}.bind(this);
		
		this.resetBrandName = function(){
			this.selectBrandName(this.searchBrandName());
			return true;
		}.bind(this);
		// 清除选项再重新获取
		this.clearOptions = function(){
			this.searchBrandName('');
			this.reGetList();
		}.bind(this);
		
		
		// 品牌全部
		this.clearBrand = function(){
			console.log(this.selectBrandName(),this.searchBrandName())
			this.selectBrandName('');
			if( this.selectBrandName()!=this.searchBrandName() ){
				this.searchBrandName('');
				this.reGetList();
			}else{
				this.back();
			}
		}.bind(this);
		
		/** 161008参数 **/
		this.clearParam = function(){
			var params = this.search_params();
			params && $.each(params,function(i,o){
				o.checked('');
			});
		}.bind(this);
		this.finishParam = function(){
			if( this.search_params().length && this.params_cache()!=this.postFilterWord() ){
				this.params_cache(this.postFilterWord());
				this.reGetList();
			}
			$('#filter-params').removeClass('active');
		}.bind(this);
		
		// 移动端跳转详情方法
		this.mobileJump = function(o){
			location.href = 'm/index!goodsDetail?id='+o.id();
		}
		// 微信查看图片（单张）
		this.previewImage = function(o,e){
			var src = e.target.src;
			if(!src) return false;
			wxkit.previewImage(src,[src]);
		}

		// 事件绑定
		this.reg = function(){
			var type = this.pageType(),
				self = this;
			var callback = function(reverse){
	        	self.getList(function(inf){
	        		self.scroll.isEnd(inf.isEnd);
	                self.scroll.onOff(true);
	                if( reverse ){
	                	var startIndex = self.startIndex();
	                	if( startIndex>1 ){
	                		self.startIndex( startIndex-1 );
	                	}else{
	                		self.scroll.isReverseEnd(1);
	                	}
	                }else{
	                	self.firstIndex(self.firstIndex()+1);
	                }
	            },reverse);
	        };
			switch(type){
				// 滚动加载
				case 'scroll':
					this.scroll = new ScrollView({
			            list: '.good-list'
			            , item: 'li:not(.list-tips)'
			            , scroller: window
			        });
			        this.scroll.init(callback,function(){callback(true)});
			        break;
			    // 分页按钮
			    case 'button':break;
			}
		}.bind(this);
		
		this.cacheSetting = function(){}.bind(this);

		// main
		this.init = function(type,noFirstPage){
			if(window.ccvm){
				this.ccvm = window.ccvm;
			}
			
			type = type||'scroll';
			this.pageType(type);// 设置分页类型
			this.reg();
			ko.applyBindings(this, document.getElementById("searchList"));
			if( !noFirstPage ){
				this.getList(function(inf){
					if(self.pageType()=='scroll'){
						self.scroll.isEnd(inf.isEnd);
						self.scroll.onOff(true);
						self.firstIndex(self.firstIndex()+1);
					}
				});
			}
			// 补充
			$('[name=queryGoodsName]').val(this.searchWord()||'');
			$('[name=index_showType]').val(this.showType()||0);
		}.bind(this);
		
	}

	
	// 初始化
	var glvm = new GoodsListViewModel();
	
	window.glvm = glvm;

}(this, jQuery));