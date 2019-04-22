(function($){
	$.ajaxCheck = function(data) {
		if(data.result) return true;
		else {
			alert(data.msg);
			return false;
		}
	};
	$.fn.mysorttable = function(opts){
		var _isSort = false;
		var sortEle = $(".listTable tbody");
		var _that = this;
		var setting = $.extend({
			begin:"beginOrder",
			save:"saveOrder"
		},opts||{});
		sortEle.sortable({
			axis:"y",
			helper:function(e, ele){
				var _original = ele.children();
				var _helper = ele.clone();
				
				_helper.children().each(function (index){
					$(this).width(_original.eq(index).width());
				});
				_helper.css("background", "#bbf");
				return _helper;
			},
			update:function(e, ui){
				setOrder();
			}
		});
		sortEle.sortable("disable");
	
		$("#"+setting.begin).click(beginOrder);
		
		$("#"+setting.save).click(saveOrder);
		
		function beginOrder(){
			if(!_isSort){
				$(_that).find("thead tr").append("<td>序号</td>");
				setOrder();
				$(_that).find("tfoot tr").append("<td>拖动排序</td>");
				sortEle.sortable("enable");
				_isSort = true;
			}else{
				alert("已经处于排序状态");
			}
		}
		
		function saveOrder(){
			if(_isSort){
				var idArg = sortEle.sortable("serialize",{key:"ids"});
				$.post("updateSort?"+idArg, function(data){
					if($.ajaxCheck(data)){
						if(data.result){
							$(_that).find("tr").each(function(){
								$(this).children().last().remove();
							});
							sortEle.sortable("disable");
							_isSort = false;
						}
					}
				});
			}else{
				alert("还不是排序状态");
			}
		}
		
		function setOrder(){
			$(_that).find("tbody tr").each(function(index){
				if(_isSort){
					$(this).find("td:last").html((index+1));
				}else{
					$(this).append("<td>"+(index+1)+"</td>");
				}
			});
		}
	}
	$.fn.mytree = function(opts){
		var setting = $.extend({
				data:{
					simpleData:{
						enable:true,
						idKey: "id",
						pIdKey: "pid",
						rootPId: -1
					}
				},
				view:{
					dblClickExpand: false,
					selectedMulti: false
				},
				async: {
				enable: true,
				url: opts?(opts.url || "treeAll"): "treeAll",
				},
				mine:{
					listChild:1,
					SrcElement:"#cc"
				},
				callback:{
					onAsyncSuccess:function(){
						if(opts.mine.expandAll){
							t.expandAll(true)
						}
					}
				}
			}, opts || {});
			var _mine = setting.mine;
			var t = $.fn.zTree.init($(this), setting);
			t.getCheckParentNodes = getCheckParentNodes;
			t.getCheckChildNodes = getCheckChildNodes;
			if(_mine.listChild){
				t.setting.callback.onClick = listChild;
			}
			function listChild(event, treeId, treeNode){
				$(_mine.SrcElement).attr("src", "channels/"+treeNode.id);
			}
			function getCheckParentNodes(treeNode,checked){
				var ps = new Array();
				var pn;
				while((pn=treeNode.getParentNode())){
					if(pn.checked == checked){
						ps.push(pn);
					}
					treeNode = pn;
				}
				return ps;
			}
			function getCheckChildNodes(treeNode, checked, cs){
				var ccs;
				if((ccs = treeNode.children)){
					for(var i=0; i<ccs.length; i++){
						var c = ccs[i];
						if(c.checked == checked){
							cs.push(c);
						}
						getCheckChildNodes(c, checked, cs);
					}
				}
			}
			return t;
	};
	$.fn.myaccordion = function(opts){
		var settings = $.extend({
			selectedClz:"navSelected",
			titleTagName:"h3"
		},opts||{});
		
		var titleNode = $(this).find("ul>"+settings.titleTagName);
		var selectedNode = $(this).find("ul."+settings.selectedClz+">"+settings.titleTagName);
		titleNode.css("cursor","pointer");
		titleNode.nextAll().css("display","none");
		selectedNode.nextAll().css("display", "block");
		
		titleNode.click(function(){
			var checked = $(this).parent().hasClass(settings.selectedClz);
			if(checked){
				$(this).parent().removeClass(settings.selectedClz);
				$(this).nextAll().slideUp();
			}else{
				$(this).parent().addClass(settings.selectedClz);
				$(this).nextAll().slideDown();
			}
		});
	};
	
	$.fn.trColorChange = function(opts){
			var settings = $.extend({
				overClz:"trMouseover",
				evenClz:"trEvenColor"
			},opts||{});
			$(this).find("tbody tr:even").addClass(settings.evenClz);
			$(this).find("tbody tr").on("mouseenter mouseleave",function(){
				$(this).toggleClass(settings.overClz);
			});
	};
	$.fn.confirmOperator = function(opts){
		var settings = $.extend({
			msg:"该操作不可逆, 确定进行该操作吗？",
			evenName:"click"
		},opts||{});
		$(this).on(settings.evenName,function(event){
			if(!confirm(settings.msg)){
				event.preventDefault();
					  
			}
		});
	};
})(jQuery);


