/* easyui 框架 */
//在右边center区域打开菜单，新增tab
function Open(title, href, icon){
    var tt = $('#tabs'), 
    	flag = !!href,
    content = flag ? '<iframe frameborder="0" src="'+href+'" style="width:100%;height:100%;"></iframe>' : '<div style="margin:10px;"><h2>Coming soon...！</h2></div>';
    if( !flag ){
    	return false;
    }else{
    	if (tt.tabs('exists', title)){  
            tt.tabs('select', title);
            var tab = tt.tabs('getSelected');
            tt.tabs('update',{
                tab: tab,
                options: {
                    title: title,
                    content: content
                }
            })
        } else {  
            tt.tabs('add', {  
                title : title,
                closable : true,
                content : content,
                fit : true,
                iconCls: icon || ''
            });
        }
    }
    
}

/*var treeData = [
    {
        text : "后台管理",
        
        children : GL_tree,
          //[
           // {text : "权限管理", state : "closed", children: [
           //     {text : "人员管理", attributes : {url : "ad/admin!search.action"} },
                //{text : "角色管理", attributes : {url : "ad/permissionGroup!list.action"} }
           // ]},
            //{text : "机柜类别", attributes : {url : "ad/type!list.action?typeId=0"} },
           // {text : "机楼管理", attributes : {url : "ad/room!search.action"} }, 
           // {text : "机柜管理", attributes : {url : "ad/cabinet!list.action"} },
           // {text : "设备管理", attributes : {url : "ad/equipment!search.action"} }, 
           // {text : "工单添加", attributes : {url : "ad/task!toAdd.action"} }, 
           // {text : "工单查询", attributes : {url : "ad/task!search.action"} }, 
          //  {text : "客户管理", attributes : {url : "ad/client!search.action"} },  
           // {text : "控制台", attributes : {url : "ad/console!list.action"} },      
           // {text : "退出", attributes : {url : "adminLogin!loginOut.action"} }
         // ],
    }
];*/
    //实例化树形菜单
    /*$("#tree").tree({
        data : treeData,
        animate : true,
        lines : true,
        onClick : function (node) {
            if (node.text == "退出") window.location.href = node.attributes.url;
            if (node.attributes) {
                Open(node.text, node.attributes.url);
            }
        }
    });*/


	var isAllowSimple = true;
	;+function(window,$){
		var isAllowSimple = window.isAllowSimple;
		$.each(GL_tree, function(i,o){
			if( o.hide=='true' ) return true;// 由后台权限确定是否隐藏，默认显示
			
			var children = o.children, clen = children && children.length;
			if(!isAllowSimple){
				if( !children || !clen){
					children = [o];
					clen = children.length;
				}
			}
			if( o.text=='退出' || !children || !clen ){
				var li = $('<li>'),
					defcln = o.text=='退出' ? 'icon-exit':'icon-default',
					ul = $('<ul class="Menu">'),
		        	flag = o.attributes && o.attributes.url;
		        $('<a>',{
		            text : o.text,
		            href : flag ? o.attributes.url : 'javascript:void(0)'
		        }).append('<div class="panel-icon '+(o.iconCls||defcln)+'"></div>').appendTo(li);
		        
		        li.appendTo(ul);
		        ul.appendTo('#Menu');
			}else{
				var ul = '<ul class="Menu">',
					iconCls = o.iconCls||'icon-default'; 
				$.each(children, function(i,o){
					if( o && o.hide=='true' ){
						return true;// 由后台权限确定是否隐藏，默认显示
					}else{
						if(o){
							ul+='<li><a href="'+o.attributes.url+'"><div class="panel-icon '+(o.iconCls||iconCls)+'"></div>'+o.text+'</a></li>'
						}
					}
				});
				ul+='</ul>';
				$('#Menu').append('<div title="'+o.text+'" iconCls="'+(o.iconCls||'icon-default')+'">'+ul+'</div>');
			}
			
		});
	}(this,jQuery)





	/*function makeLink(o){
		var li = $('<li>'),
			flag = !!o.attributes.url;
        $('<a>',{
            text : o.text,
            href : o.attributes.url || "javascript:void(0)"
        }).appendTo(li);
        
        if( !flag ){
        	
        }
	}
	$("#Menu")
    .each(function (){
        $.each(GL_tree, function (i, o){
        	if( o.hide=='true' ) return true;// 由后台权限确定是否隐藏，默认显示
            var li = $('<li>'),
            	flag = o.attributes && o.attributes.url;
            $('<a>',{
                text : o.text,
                href : flag ? o.attributes.url : 'javascript:void(0)' 
            }).appendTo(li);
            
            
            var children = o.children, clen = children && children.length;
            var ul = $('<ul>');
            if(clen && typeof clen != 'undefined'){
            	$.each(children, function (i2, o2){
	            	if( o2 && o2.hide=='true' ){
	            		clen--;
	            		return true;// 由后台权限确定是否隐藏，默认显示
	            	}else{
	            		var childrenLi = $('<li>');
	            		if( o2 ){
	            			$('<a>',{
	            				text : o2.text,
	            				href : o2.attributes.url
	            			}).appendTo(childrenLi);
	            		}
	            		childrenLi.appendTo(ul);
	            	}
	            });
            	
            }
            if(clen){
            	$(li).find('a').addClass('spread').attr('title','展开').parent().prepend('<img src="/images/TreeImages/plus.gif" style="vertical-align:middle"> ');
	            ul.appendTo(li);
        	}else{
        		ul = null;
        	}
            if( typeof clen != 'undefined' && clen==0 ){return true;}// children 全为 hide 的时候
        	
            li.appendTo( "#Menu" );
        });
    });*/
	
    $(".Menu a").click(function (){
        var _$ = $(this),flag,cln =  _$.find('div').attr('class').replace('panel-icon ','');
        if( _$.text()==="退出" ) return true;
        if( _$.attr('href') ){
        	if( _$.text().indexOf("订单管理")!=-1 ){
        		Open( "订单管理", _$.attr('href'), cln );
        	}else if( _$.text().indexOf("总平台账户管理")!=-1 ){
        		Open( "总平台账户管理", _$.attr('href'), cln );
        	}else{
        		Open( _$.text(), _$.attr('href'), cln );
        	}
        }else{
        	Open( _$.text(), undefined, cln);
        }
        $(".Menu a").removeClass('selected');
        _$.addClass('selected');
        
        return false;
    });
	//初始化进入默认页面
	$(function(){
		$('#Menu a').each(function(){
			var $this = $(this),
				sText = $this.text(),
				$parent = $this.parent().parent().parent();
			if(sText.indexOf("订单管理")>-1||sText.indexOf("总平台账户管理")>-1){
				$this.click();
				if( $parent.is('.accordion-body') ){
					$('#Menu').accordion('select',$parent.prev().find('.panel-title').html());
				}else{
					$('#Menu .accordion-header-selected').click();// 收起selected，代替 unselect 方法
				}
			}
		});
	});
    
    
    $("#tabs").tabs({
    	// 绑定tabs的右键菜单
        onContextMenu : function (e, title) {
            e.preventDefault();
            $('#tabsMenu').menu('show', {
                left : e.pageX,
                top : e.pageY
            }).data("tabTitle", title);
        },
        // 绑定tabs的选中事件
        onSelect: function(title, index){
        	var $menu = $('#Menu'), $west = $('.west');
        	$menu.find('.selected').removeClass('selected');
        	$menu.find('a').each(function(i,o){
        		var $o = $(o), $ul = $o.parent().parent();
        		if($o.text()==title){
        			$o.addClass('selected');
        			$west.scrollTop( $o.offset().top );
        			if( !$ul.is('.active')&&!$ul.is('#Menu') ){
        				$ul.addClass('active');
        			}
        		}
        	});
        }
    });
    
    //实例化menu的onClick事件
    $("#tabsMenu").menu({
        onClick : function (item) {
            CloseTab(this, item.name);
        }
    });
    
    //几个关闭事件的实现
    function CloseTab(menu, type) {
        var curTabTitle = $(menu).data("tabTitle");
        var tabs = $("#tabs");
        
        var allTabs = tabs.tabs("tabs");
        var closeTabsTitle = [];
        
        $.each(allTabs, function () {
            var opt = $(this).panel("options");
            if (opt.closable && opt.title != curTabTitle && type === "Other") {
                closeTabsTitle.push(opt.title);
            } else if (opt.closable && type === "All") {
                closeTabsTitle.push(opt.title);
            } else if (opt.closable && type === "close" && opt.title == curTabTitle){
                closeTabsTitle.push(opt.title);
            }
        });

        var closeTabsLen = closeTabsTitle.length;
        if (closeTabsLen)
        for (var i = 0; i < closeTabsLen; i+=1) {
            tabs.tabs("close", closeTabsTitle[i]);
        }
    }
