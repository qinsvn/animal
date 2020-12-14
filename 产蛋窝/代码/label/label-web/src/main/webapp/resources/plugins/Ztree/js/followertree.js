var className = "dark";
var groupid="";
var selectUserid=[];
var load=false;
var showData;
var requestSrc;
var pparamlist=new Array();
var itemPage;
var isDisposal_val=[];
$(function() {
	itemPage=10;
	initPage('pages',getListByPage,null);
	initPage('pages_2',getListByPage,null);
	showData = "welTable_showData";
	requestSrc = "../../web/mpuser/list";
	$('#fensi-addBbtn').hide();	
	getListByPage(1);
	var setting = {
		async : {
			enable : true,
			url : '',
			type : 'post',
			autoParam : [ 'id=pid', 'name=name', 'level=lv' ],
			otherParam : {
				'otherParam' : 'ZTreeAsyncTest'
			},
			dataFilter : filter
		},
		view : {
			showLine : false,
			selectedMulti : false,
			addHoverDom : addHoverDom,
			removeHoverDom : removeHoverDom
		},
		edit : {
			enable : true,
			removeTitle : '删除',
			renameTitle : '重命名',
			showRemoveBtn : showRemoveBtn
		},
		data : {
			keep : {
				// parent:false,
				leaf : false
			// 锁定叶子节点isParent：false不能增加子节点
			},
			simpleData : {
				enable : false,
				idKey : "id",
				pIdKey : "pid",
				rootPId : 0
			}
		},
		callback : {
			beforeDrag : beforeDrag, /* 设置节点拖拽 */
			beforeRemove : beforeRemove, // 用于捕获节点被删除之前的事件回调函数，并且根据返回值确定是否允许删除操作
			beforeRename : beforeRename, // 用于捕获节点编辑名称结束（Input 失去焦点 或 按下
											// Enter
											// 键）之后，更新节点名称数据之前的事件回调函数，并且根据返回值确定是否允许更改名称的操作
			onClick : onDesignTreeClick, // 点击节点时，显示节点详细信息
			onRename : onRename
		},
	};
	var params = {
		pid : null
	};
	ajaxRequestPost("../../web/mpGroup/treeList", params, function(data) {
		if (data.code === 0) {
			$.fn.zTree.init($("#followtree"), setting, eval(data.data));
		}
	});
	$("#selsectAll").click(function() {
		var chkitem=$(".chkitem");
		var select_data=$("#selsectAll").attr("select-data");
		if(select_data==1){
			for(var i = 0;i<chkitem.length;i++){
				if(chkitem[i].type == "checkbox") chkitem[i].checked = true;
				}
			$("#selsectAll").attr("select-data","2");
		}else{
			for(var i = 0;i<chkitem.length;i++){
				if(chkitem[i].type == "checkbox") chkitem[i].checked = false;
				}
			$("#selsectAll").attr("select-data","1");
		}
		
	});
	
//	//全选
//	$("#selsectAll").click(function() {
//		$(".chkitem").prop('checked',this.checked);
//	});
//	$('.chkitem').click(function(){
//		var chkitems =$('.chkitem');
//		$('#selsectAll').prop('checked',chkitems.length == chkitems.filter(':checked').length ?true:false );
//	});
	
	$("#submitfollower").click(function(){
		olddata();
		selectUserid=[];
	});
	
	//关键字查询
	$("#btn_search").click(function(){
		 search(1);
	});
	
	$("#followName").focus(function(){
		$("#followName_2").val("");		
	});
	$("#followName_2").focus(function(){
		$("#followName").val("");		
	});
})

function onRename(e, treeId, treeNode, isCancel) {
	var params = {
		id : treeNode.id,
		name : treeNode.name
	}
	ajaxRequestPost("../../web/mpGroup/alter", params, function(data) {
		if (data.code != 0) {
			window.wxc.xcConfirm(data.msg, window.wxc.xcConfirm.typeEnum.error);
		}
	});
}
function beforeRename(treeId, treeNode, newName, isCancel) {
	if (newName.length == 0) {
		window.wxc.xcConfirm("组名不能为空", window.wxc.xcConfirm.typeEnum.info);
		return false;
	}
	return true;
}
function showRemoveBtn(treeId, treeNode) {
	return treeNode.level != 0;
}

function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	var zTree = $.fn.zTree.getZTreeObj("followtree");
	zTree.selectNode(treeNode);
	var isDel = confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
	var isDeled = false;
	if (isDel) {
		var params = {
			id : treeNode.id
		};
		ajaxRequestPost("../../web/mpGroup/delete", params, function(data) {
			if (data.code === 0) {
				zTree.removeNode(treeNode.id);
				isDeled == true;
				return isDeled;
			} else {
				isDeled == false;
				return isDeled;
			}
		});
	} else {
		return false;
	}
}

function filter(treeId, parentNode, childNodes) {
	if (!childNodes) {
		return null;
	}
	for (var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

function beforeDrag(treeId, treeNodes) {
	return false;
}
var newCount = 1;
function addHoverDom(treeId, treeNode) {
	selectUserid=[];
	var sObj = $('#' + treeNode.tId + '_span');
	if (treeNode.editNameFlag || $('#addBtn_' + treeNode.id).length > 0)
		return;
	if (treeNode.level == 2)
		return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.id
			+ "' title='添加节点' onfocus='this.blur();'></span";
	sObj.append(addStr);
	var btn = $('#addBtn_' + treeNode.id);
	if (btn)
		btn.bind('click', function() {
			// newCount++;
			var zTree = $.fn.zTree.getZTreeObj('followtree');
			// nodes = zTree.getSelectedNodes();
			// treeNode = nodes;
			var newName = '新分类' + newCount++;
			var params = {
				pid : treeNode.id,
				pids : treeNode.pids + ',' + treeNode.id,
				name : newName
			};
			ajaxRequestPost("../../web/mpGroup/add", params, function(data) {
				if (data.code === 0) {
					zTree.addNodes(treeNode, {
						id : data.data.id,
						type : 'current',
						pid : treeNode.id,
						pids : (treeNode.pids + ',' + treeNode.id),
						name : newName
					});
					return false;
				} else {
					window.wxc.xcConfirm(data.msg, window.wxc.xcConfirm.typeEnum.error);
				}
			});
		});
}

function removeHoverDom(treeId, treeNode) {
	$('#addBtn_' + treeNode.id).unbind().remove();
}

function onDesignTreeClick(event, treeId, treeNode) {
	//换组合时应该把所有的粉丝去钩
	$(".chkitem").attr("checked",false);
	selectUserid=[];
	groupid = null;
	if (treeNode.level==0) {
		showData = "welTable_showData";
		requestSrc = "../../web/mpuser/list";
		getListByPage(1);
		$('#fensi-addBbtn').hide();	
	}else{
		groupid = treeNode.id;
		showData = "welTable_showData";
		requestSrc="../../web/mpuser/listByGroup";
		getListByPage(1);
		$('#fensi-addBbtn').show();	
	}
}

function addBtn(){
	//获取当前点击的节点id
	var zTree = $.fn.zTree.getZTreeObj("followtree"), nodes = zTree.getSelectedNodes();
	treeNode = nodes[0];
	if (treeNode.level==0) {
		selectUserid=[];
		load=false;
	}else{
		load = true;
		groupid=treeNode.id;
		showData="welTable_selectData";
		requestSrc = "../../web/mpuser/list";
		getListByPage(1);
		$('.fenmodel-part').show();		
	}
}
function getListByPage(pageNum) {
	getSelectItem();
	var params = {
		groupid : groupid,
		pageSize : itemPage,
		pageNum : pageNum,
	}
	var backParam = {}
	backParam.pageNum = pageNum;
	backParam.showData = showData;
	
	ajaxRequestPost(requestSrc, params, listBack, backParam);
}

function search(pageNum) {
	var remarkname = $("#followName").val();
	var nickname = $("#followName_2").val();
	var params = {
		pageSize : itemPage,
		pageNum : pageNum
	}
	if (remarkname != undefined && remarkname.length > 0) {
		params.remarkname = remarkname;
	}
	if (nickname != undefined && nickname.length > 0) {
		params.nickname = nickname;
	}
	var backParam={
		pageNum:pageNum,
		showData:"welTable_showData"
	}
	ajaxRequestPost("../../web/mpuser/search", params, listBack, backParam);
}

function listBack(data, backParam) {
	if (data.code === 0) {
		var list = data.data.list;
		if(load){
			var select = data.data.select;
			if(select!=undefined&&select!=[]){
				//原来选中的加入数组
				for(var j=0;j<select.length;j++){
					var num = parseInt(select[j].mpuserid);
					if(selectUserid.indexOf(num)==-1){
						selectUserid.push(parseInt(num));							
					}
				}
			}
			load=false;
		}
		var count = data.data.count;
		var itemCount = data.data.total;
		itemCount = parseInt(itemCount);
		updatePage('pages',itemCount);
		updatePage('pages_2',itemCount);
		selectPage('pages',backParam.pageNum);
		selectPage('pages_2',backParam.pageNum);
		var tableHtml = "";
		for (var i = 0; i < list.length; i++) {
			if (i == (list.length - 1)) {
				tableHtml += "<tr class=\"lastline\">";
			} else {
				tableHtml += "<tr class=\"line\">";
			}
			if(backParam.showData=="welTable_selectData"){
				tableHtml += "<td><input type='checkbox' class='chkitem' value = '"+list[i].id+"' ";
				//如果返回的数据select不为空，取出id遍历和list[i].id对比，符合则勾选
				//select数组和selectUserid数组结合去重
				for(var k=0;k<selectUserid.length;k++){
					if(list[i].id==selectUserid[k]){
						tableHtml+=" checked = 'checked' ";						
					}
				}
				tableHtml+="/></td>";
			}
			tableHtml += "<td>" + (i + 1) + "</td>";
			tableHtml += "<td><img src='" + list[i].headimgurl + "' height='30' maxWidth='100%'/></td>";
			var nickname = list[i].nickname==undefined?"-":list[i].nickname
			tableHtml += "<td>" + nickname + "</td>";
			var remarkname=list[i].remarkname==undefined?"-":list[i].remarkname;
			tableHtml += "<td>" + remarkname + "</td>";
			var phone1=list[i].phone1==undefined?"-":list[i].phone1;
			tableHtml += "<td>" + phone1 + "</td>";
			tableHtml += "<td>"
					+ getTimeFormat(new Date(list[i].subscribetime),
							'yyyy-MM-dd HH:mm:ss') + "</td>";
			if(backParam.showData!="welTable_selectData"){
				tableHtml += "<td><a href='followerInfo.html?id=" + list[i].id
				+ "' class='operInfo'>查看 </a></td>"
			}
			
		}
		$('#'+backParam.showData+' .line').remove();
		$('#'+backParam.showData+' .lastline').remove();
		$('#'+backParam.showData).append(tableHtml);
	} else {
		window.wxc.xcConfirm(data.msg, window.wxc.xcConfirm.typeEnum.error);
	}

}

/*
 * 获取选中项
 */
function getSelectItem() {
	$(".chkitem").each(function() {
		if (true == $(this).prop("checked")) {
			var id = parseInt($(this).attr("value"));
			if(selectUserid.indexOf(id)==-1){
				selectUserid.push(id);
			}
		}else{
			var id = parseInt($(this).attr("value"));
			if(selectUserid.indexOf(id)!=-1){
				selectUserid.splice(selectUserid.indexOf(id),1);
			}
			//把未选中的userid从数组中去除
		}
	});
	return selectUserid;
}

//關閉model
$('.closeFensi').click(function(){
	selectUserid=[];
	olddata();
	
//	$('.fenmodel-part').hide();
//	selectUserid=[];
//	load =false;
	//取消第二个分页插件id
	//显示第一个分页插件id
});

//重置model選框
function resetModel(){
	selectUserid=[];
	$('#welTable_selectData input[type=checkbox]').attr('checked',false);
}

//存储已选择的数据
function olddata(){
	getSelectItem();
	var mpuserids=selectUserid.join(",");
	var zTree = $.fn.zTree.getZTreeObj("followtree"), nodes = zTree.getSelectedNodes();
	groupid = nodes[0].id;
	showData = "welTable_showData";
	var params = {
			groupid : groupid,
			mpuserids : mpuserids
	}
	ajaxRequestPost("../../web/mpGroup/addToGroup",params,function(data){
		if(data.code===0){
			requestSrc="../../web/mpuser/listByGroup";
			getListByPage(1);
		}else{
			window.wxc.xcConfirm(data.msg, window.wxc.xcConfirm.typeEnum.error);
		}
		$('.fenmodel-part').hide();
	});
	selectUserid=[];
	
}
//function addHoverDom(treeId, treeNode) {
//	var dataId="";
//	var sObj = $('#' + treeNode.tId + '_span');
//	if (treeNode.editNameFlag || $('#addBtn_' + treeNode.id).length > 0)
//		return;
//	if (treeNode.level == 2)
//		return;
//	var addStr = "<span class='button add' id='addBtn_" + treeNode.id
//			+ "' title='添加节点' onfocus='this.blur();'></span";
//	sObj.append(addStr);
//	var btn = $('#addBtn_' + treeNode.id);
//	if (btn)
//		btn.bind('click', function() {
//			// newCount++;
//			var zTree = $.fn.zTree.getZTreeObj('followtree');
//			// nodes = zTree.getSelectedNodes();
//			// treeNode = nodes;
//			var newName = '新分类' + newCount++;
//			var params = {
//				pid : treeNode.id,
//				pids : treeNode.pids + ',' + treeNode.id,
//				name : newName
//			};
//			ajaxFunction("../../web/mpGroup/add", params, function(data) {
//				if (data.code === 0) {
//					zTree.addNodes(treeNode, {
//						id : data.data.id,
//						type : 'current',
//						pid : treeNode.id,
//						pids : (treeNode.pids + ',' + treeNode.id),
//						name : newName
//					});
//					dataId=data.data.id;
//				} else {
//					window.wxc.xcConfirm(data.msg, window.wxc.xcConfirm.typeEnum.error);
//				}
//			});
//			console.log(treeNode.children[0].id);
//			if(dataId!=null&&treeNode.children.length==1&&treeNode.children[0].id==dataId){
//				var param={
//						id:treeNode.id,
//						newId:dataId
//				}
//				console.log(param);
//				ajaxRequestPost("../../web/mpGroup/alterGroupRef", param, function(result) {
//					if (result.code === 0) {
//						console.log(result.msg);
//					} else {
//						window.wxc.xcConfirm(result.msg, window.wxc.xcConfirm.typeEnum.error);
//					}
//				});				
//			}
//		});
//}

