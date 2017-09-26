(function(window, $) {
	// 基础类
	var Base = function(o){
		o = o || {};
		this.id = ko.observable(o.id);
		this.name = ko.observable(o.name);
		this.active = ko.observable(false);
	};
	// 品牌
	var Brand = function(o){
		o = o || {};
		this.img = ko.observable(o.img);
		this.indexString = ko.observable(o.indexString);
		Base.call(this,o);
	};
	Brand.prototype = new Base();

	// 车系
	var Series = function(o){
		o = o || {};
		this.maker = ko.observable(o.maker);
		this.carSeries = ko.observableArray([]);
		o.carSeries.map(function(o){
			this.carSeries.push(new Base({name:o.carSerie}));
		}.bind(this));
		Base.call(this,o);
	};
	Series.prototype = new Base();

	// 排量
	var Centimetres = function(o){
		o = o || {};
		o.name = o.carCentimetres;
		Base.call(this,o);
	};
	Centimetres.prototype = new Base();

	// 车型
	var Size = function(o){Base.call(this,o);};
	Size.prototype = new Base();

	var ChooseCarViewModel = function() {
		var self = this,
			urls = [
				// queryIndexString
				{url: 'chooseCarTool!chooseCar',tips:'暂无车型品牌',mockkey:'chooseCar'}
				// carBrandName
				, {url: 'chooseCarTool!chooseCarSeries',tips:'暂无相关车系',mockkey:'chooseCarSeries'}
				// carBrandName=日产&carSeries=风度A33&carMaker=进口日产
				, {url: 'chooseCarTool!chooseCarCentimetres',tips:'暂无相关排量',mockkey:'chooseCarCentimetres'}
				// carBrandName=日产&carSeries=风度A33&carMaker=进口日产&carCentimetres=2.0L
				, {url: 'chooseCarTool!chooseCarSize',tips:'暂无相关车型',mockkey:'chooseCarSize'}
				// carId=8ab7ad9d51e79f860151e7a7f9c30e72
				, {url: 'chooseCarTool!chooseDetailCar',mockkey:'commSuc'}
				, {url: 'chooseCarTool!ajaxClearChooseCar',mockkey:'commSuc'}
				, {url: 'chooseCarTool!queryVin',tips:'暂无相关车型',mockkey:'queryVin'}
			];
		
		this.isInit = ko.observable(0);
		/* 页面内容 */
		// 五个列表
		this.brandList       = ko.observableArray([]);
		this.seriesList      = ko.observableArray([]);
		this.centimetresList = ko.observableArray([]);
		this.sizeList        = ko.observableArray([]);
		this.carList         = ko.observableArray([]);

		// 字母索引数组
		this.indexStrings = ko.observableArray([]);
		// vin码匹配数据
		this.match = ko.observable();
		this.imgset = ko.computed(function(){
			return this.match() && this.match().PIC;
		},this);

		/* 异步用的当前参数 */
		this.queryIndexString = ko.observable();// 查询字母
		// 查询品牌名称
		this.currentBrand = ko.computed(function(){
			return this.brandList().find(function(brand){return brand.active()});
		},this);
		this.carBrandName = ko.computed(function(){
			var brand = this.currentBrand();
			return brand && brand.name() || '';
		},this);
		
		this.carMaker = ko.observable();// 查询厂商

		// 查询车系
		this.currentMaker = ko.computed(function(){
			var self = this;
			var maker = this.seriesList().find(function(m){
				return m.maker() == self.carMaker();
			});
			return maker;
		},this);
		this.currentSeries = ko.computed(function(){
			var self = this;
			var maker = this.currentMaker();
			if( maker ){
				var serie = maker.carSeries().find(function(item){
					return item.active();
				});
				return serie;
			}
		},this);
		this.carSeries = ko.computed(function(){
			var self = this;
			var serie = this.currentSeries();
			if( serie ){
				return serie.name() || '';
			}
			return '';
		},this);

		// 查询排量
		this.carCentimetres = ko.computed(function(){
			var c = this.centimetresList().find(function(c){
				return c.active();
			});
			return c && c.name() || '';
		},this);
		
		// 查询详细车型
		this.carSize = ko.computed(function(){
			var s = this.sizeList().find(function(s){
				return s.active();
			});
			return s && s.name() || '';
		},this);
		
		this.carId = ko.observable();// 车型id
		this.vin = ko.observable();// vin码
		/* /参数 */

		/* 异步相关辅助 */
		var loading_tips = '正在加载...';
		// 当前步骤
		this.steps = ko.observable(1);
		// 是否正在loading
		this.loading = ko.observable(false);
		// 对应提示
		this.tips = ko.observable(loading_tips);
		// 获取异步用data
		var getData = function(mode){
			var data = {};
			switch(mode){
				case 0: 
					data.queryIndexString = '';//this.queryIndexString();
					break;
				case 1: 
					data.carBrandName = this.carBrandName();
					break;
				case 2: 
					data.carBrandName = this.carBrandName();
					data.carSeries = this.carSeries();
					data.carMaker = this.carMaker();
					break;
				case 3: 
					data.carBrandName = this.carBrandName();
					data.carSeries = this.carSeries();
					data.carMaker = this.carMaker();
					data.carCentimetres = this.carCentimetres();
					break;
				case 4:
					data.carId = this.carId();break;
				case 6:
					data.vin = this.vin();break;
			}
			return data;
		}.bind(this);
		
		// 初始化用key数组
		var arrKey = ['brand','series','centimetres','size'];
		// 初始化用key数组下标
		var iKey = 0;
		/* 获取数据 */
		// 通用异步获取列表数据，并set
		var getList = function(mode,list_key,ItemClass,data_list_key){
			
			var isPc = UA.type != "mobile" && !/\/m\//.test(location.href);
			if( mode==5 && !this.carBrandName() ){
				this.queryIndexString('');
				if( !isPc ){
					// history.go(-1);
					glvm&&glvm.reGetList();// reGetList带有后退
					return false;
				}
			}
			
			var opt = urls[mode],
				url = opt.url,
				mockkey = opt.mockkey,
				data = getData(mode);
			console.log('data',data)
			data_list_key = data_list_key || 'data';
			this.loading(true);// 标记loading
			this.tips(loading_tips);// 使用loading时的提示
			cep.commAjax({url:url,data:data,mockkey:mockkey},function(_data){

				this.loading(false);// 取消标记loading

				var inf = _data.inf, self = this, list = inf && inf[data_list_key];
				if( mode == 5 ){
					if( !isPc ){
						this.queryIndexString('');
						this.pro_brand = '';
						this.pro_series = '';
						this.brandList([]);
						this.seriesList([]);
						history.go(-1);
						this.isHasPro(2);// 当2时，下次执行get_brand将调用glvm的reGetList
						alert('isHasPro 2')
						this.get_brand();
					}else{
						location.reload();
					}
				}else if(mode==0 && this.isHasPro()==2){
					glvm&&glvm.reGetList();
					this.isHasPro(false);
				}
				if( !inf ){
					return false;
				}
				// 使用列表为空的提示
				if((!list || !list.length) && opt.tips){
					this.tips(opt.tips);
				}
				
				// session有值
				var key = list_key.replace('List',''),
					provalue = self['pro_'+key];
				// set列表
				if( mode==6 || self[list_key]().length != list.length || (self[list_key]()[0] && self[list_key]()[0].name() != list[0].name) ){
					self[list_key]([]);// 清空列表
					
					var flag = true;// 是否使用session标志
					
					list && list.map(function(item){
						var oItem = new ItemClass(item);
						if( flag && key!='series' && provalue && oItem.name()==provalue ){
							oItem.active(true);
							key=='brand' && (
								self.queryIndexString(oItem.indexString()),
								self.carMaker(self['pro_maker']) // carMaker
							);
							flag = !flag;
						}else if(flag && key=='series' && provalue ){
							var maker = oItem.maker(), serie;
							if( maker==self['pro_maker'] || maker==(self['pro_maker']+self['pro_brand']) ){
								self.carMaker(maker==self['pro_maker'] ? self['pro_maker'] : self['pro_maker']+self['pro_brand']);
								serie = oItem.carSeries().find(function(o){
									return o.name() == provalue;
								});
								if( serie ){
									serie.active(true);
									flag = !flag;
								}
							}
						}
						
						self[list_key].push(oItem);
					});
				}
				
				// session有值时，自动执行下一步
				if( provalue ){
					self['get_'+arrKey[++iKey]]();
				}
				
				// 额外
				if( inf.indexStrings ){
					this.indexStrings(inf.indexStrings);
				}
				if( inf.match ){
					this.match(inf.match);
					swipeVin($('#choose2 .imgset img'));
				}else if(data.vin){
					this.match(undefined);
				}

				//console.log(this[list_key]())

			}.bind(this),function(_data){
				this.loading(false);// 取消标记loading
				console.log(_data)
				if(/登录/.test(_data.res.errMsg)){
					loginWithUrl(UA.type=='pc' ? 'front/login!loginUI?loginType=1':'m/login!loginUI');
				}
			}.bind(this));

		}.bind(this);

		/* 被调用的方法 */
		this.get_brand           = getList.bind(this,0,'brandList',Brand,'carBrands');// 品牌
		this.get_series          = getList.bind(this,1,'seriesList',Series);// 车系
		this.get_centimetres     = getList.bind(this,2,'centimetresList',Centimetres);// 排量
		this.get_size            = getList.bind(this,3,'sizeList',Size);// 车型
		this.clearChoose        = getList.bind(this,5);// 清除车型
		this.queryVin           = getList.bind(this,6,'carList',Size,'carList');// 获取vin码车型

		/* 设置 */
		this.setStep = function(index){
			this.steps(index);
		}.bind(this);
		// 设置当前字母索引，并获取品牌列表
		this.setIndex = function(index){
			this.queryIndexString(index.name);
		}.bind(this);
		this.setIndexBack = function(index){
			if( index.name == this.queryIndexString() ){
				this.queryIndexString('');// 清除已选字母/车型
				// 清空并重新加载
				var brand = this.currentBrand();
				if( brand ){// 有品牌才需要重新加载
					this.get_brand();
					brand.active(false);
				}
				this.pro_brand = '';
				this.pro_series = '';
				this.carMaker(undefined);
				this.currentSeries() && this.currentSeries().active(false);
				this.seriesList([]);
			}else{
				this.setIndex.apply(this,arguments);
			}
		}.bind(this);
		// 设置选择的品牌，并获取车系列表
		this.setCarBrandName = function(brand){
			this.brandList().map(function(brand){
				brand.active(false);
			});
			brand.active(true);
			this.setStep(2);
			this.get_series();
		}.bind(this);
		this.setCarBrandNameBack = function(brand){
			if( brand.active() ){
				brand.active(false);
				// 清空并重新加载
				this.pro_brand = '';
				this.pro_series = '';
				this.carMaker(undefined);
				this.currentSeries() && this.currentSeries().active(false);
				this.seriesList([]);
				this.get_brand();
			}else{
				this.setCarBrandName.apply(this,arguments);
			}
		}.bind(this);
		// 设置选择的车系，并获取排量列表
		this.setCarSerie = function($parent,serie){
			var maker = $parent.maker(),
				name = serie.name();
			$parent.carSeries().map(function(serie){
				serie.active(false);
			});
			serie.active(true);
			this.carMaker(maker);
			this.setStep(3);
			this.get_centimetres();
		}.bind(this);
		this.setCarSerieBack = function($parent,serie){
			if( serie.active() ){
				serie.active(false);
				this.carMaker(undefined);
				// 清空并重新加载
				this.pro_series = '';
				this.get_series();
			}else{
				this.setCarSerie.apply(this,arguments);
				glvm && glvm.reGetList();
			}
		}.bind(this);
		// 设置选择的排量，并获取车型列表
		this.setCentimetre = function(centimetre){
			this.centimetresList().map(function(centimetre){
				centimetre.active(false);
			});
			centimetre.active(true);
			this.setStep(4);
			// this.sizeList([]);
			this.get_size();
		}.bind(this);
		this.setCentimetreBack = function(centimetre){
			if( centimetre.active() ){
				centimetre.active(false);
			}else{
				this.setCentimetre.apply(this,arguments);
			}
		}.bind(this);
		// 设置选择的车型，并设置session，跳转
		this.setSize = function(size){
			this.carId(size.id());
			var opt = urls[4],
				url = opt.url,
				mockkey = opt.mockkey,
				data = getData(4);
			size.active(true);
			cep.commAjax({url:url,data:data,mockkey:mockkey},function(_data){
				self.endChoose();
			});
		}.bind(this);
		this.setSizeBack = function(size){
			if( this.carId() == size.id() ){
				this.carId(undefined);
				size.active(false);
			}else{
				this.setSize.apply(this,arguments);
			}
		}.bind(this);
		// 设置当前vin，并获取车型列表
		this.setVin = function(){
			var vin = $('#vin').val(),
				tips = '请输入车架号';
			if( !vin ){
				window.Toast ? Toast.alert(tips) : alert(tips);
				return false;
			}
			this.vin(vin);
			this.setStep(5);
			// this.carList([]);
			this.queryVin();
		}.bind(this);
		
		// 跳转页面
		this.endChoose = function(){
			var listUrl = {
				pc: {
					shop:'front/shopIndex!GoodsList',
					good:'index!GoodsList'
				},
				mobile: {
					shop:'m/shopIndex!goodsList',
					good:'m/index!goodsList'
				}
			};
			var type = UA.type == 'tablet'?'mobile':UA.type,
				type2 = /shopIndex/.test(location.href) ? 'shop':'good';
			if( type=='pc' ){
				location.href = listUrl[type][type2];
			}
		}.bind(this);

		/* main */
		this.main = function(noApply){
			if(!this.isInit()){
				var dialog = document.getElementById("chooseDialog");
				if( !noApply && dialog ){
					ko.applyBindings(this, dialog);
				}
				// ko.applyBindings(this, document.getElementById("f-brand"));
				this.get_brand();
				this.isInit(1);
			}
			this.setStep(1);
		}.bind(this);
		
		/* 初始化用 */
		this.isHasPro = ko.observable(false);
		window.wx_carBrand && (this.pro_brand = wx_carBrand,this.isHasPro(1));
		window.wx_carMaker && (this.pro_maker = wx_carMaker);
		window.wx_carSeries && (this.pro_series = wx_carSeries);
		window.wx_carCentimetres && (this.pro_centimetres = wx_carCentimetres);
		window.wx_carSize && (this.pro_size = wx_carSize);
	};

	// 初始化
	var ccvm = new ChooseCarViewModel();
	window.ccvm = ccvm;
}(this, jQuery));