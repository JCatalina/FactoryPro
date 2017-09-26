;(function($,window){
	// 帮助文章对象
	var Help = function(id, title, content){
		var self     = this;
		self.id      = id||'';
		self.title   = title||'正在读取...';
		self.content = content||'正在读取...';
	}

	function ViewModel(list){
		list = list||[];
		var self = this;
		// 列表
		this.list = ko.observableArray(list.map(function(help){
			return new Help(help.id, help.title, help.content);
		}));

		// 当前文章
		this.current = ko.observable(new Help());

		// 获取信息
		this.getInfos = function(){
			$.ajax({type:'get',url:'index!helpCenter?isAjax=1&isNeedTypes=1',dataType:'json',error:function(){alert('获取失败')},
				success:function(data){
					if(data.res.status==0){
						self.list(data.inf.helpTypes);
						if(!self.list().length){
							var temp = {id:'',title:'暂无帮助信息',content:'暂无帮助信息'}
							self.current(temp);
							self.list([{helps:[temp],name:'暂无帮助'}]);
							return;
						}
						if(!self.current().id){
							self.showHelp(self.list()[0].helps[0]);
						}
					}else{
						alert(data.res.errMsg||'获取出错');
					}
				}
			});
		}

		// 显示文章
		this.showHelp = function(help){
			this.current(help);
			$('body').scrollTop($('.wrap').offset().top);
		}.bind(this);
	}

	var vm = new ViewModel();
	ko.applyBindings(vm);
	vm.getInfos();
})(jQuery,window);