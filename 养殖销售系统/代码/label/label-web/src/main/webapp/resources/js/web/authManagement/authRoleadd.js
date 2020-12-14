var paramsArry = []; // 保存选中的tree节点的id
var flag_kuno=true;
$(function(){
	$("#saveBtn").click(function(){
		saveBtn();
	})
	/*$(window).bind('beforeunload',function(){ 
		return confirm("你是否放弃此次编辑？"); 
	}); */
})

window.onload = function(){
	var params={
			
	}
	ajaxPostFunctionAsyfalse("../permission/treeList", params, function(data){
		if(data.code==0){
			//填充获取到的值
			if(data.data){
				loadDptList(data.data.menu);
			}
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	});
	
}

/**
 * 加载授权列表tree
 */
function loadDptList(treeList){
	var treeNode = treeList;
	var setting = { // 此处根据自己需要进行配置
			check: {
				enable: true,
				chkboxType: {"Y":"ps", "N":"ps"}  // 勾选 checkbox 对于父子节点的关联关系
			},
			view : {
				selectedMulti : false,
				showLine:false,
				dblClickExpand: false
			},
			data : {
				simpleData : {
					enable : false,
					idKey: "id",
					pIdKey: "fdPid",
					rootPId: 0
				},
				key:{
					name : "fdName",
					children: "childMenus",
					isParent: "fdPid"
				}
			},
			callback : {
//				onClick : onDesignTreeClick,  // node 树节点点击事件
				onCheck : onCheck // 多选框 点击事件
			}
		};
	
	$.fn.zTree.init($("#dptList"), setting, treeNode);
//	onExpandNode();
}

/**
 *   tree 节点展开折叠控制
 */
function onExpandNode(){
	var treeObj = $.fn.zTree.getZTreeObj("dptList");
	var childNodes = treeObj.getCheckedNodes(false);
	for(var i= 0; i<childNodes.length; i++){
		var isParent = childNodes[i].isParent;
		if(isParent){
			// false 折叠 ，true展开
			treeObj.expandNode(childNodes[i],false,false);
		}
	}
}


/**
 * 
 * @param event
 * @param treeId
 * @param treeNode
 */
function onCheck(event,treeId,treeNode){
	var treeArry = [];
	var treeObj = $.fn.zTree.getZTreeObj("dptList"),
	nodes = treeObj.getCheckedNodes(true);
	for(var i=0; i<nodes.length;i++){
		treeArry.push(nodes[i].id);
		paramsArry = treeArry;
	}
}

/**
 * 取消
 */
function closeBtn(){
	window.location.href = "p_list";
}

function getValue(){
	var params ={
			roleName : $("#rolename").val(),
			roleState : $("#targetRole").find("li.active a").data("val"),
			roleDesc : $("#roledesc").val()
	}
	return params;
}

/**
 * 保存
 */
function saveBtn(){
	var params = getValue();
	var roleMissions = paramsArry;
	roleMissions = roleMissions.join(",");
	if(!checkCZ('rolename','角色名称',20)){
		return;
	}
	if(!checkSymbol('roledesc','角色描述',200)){
		return;
	}
	
	var paramet = {
			fdName:params.roleName,
			fdDesp:params.roleDesc,
			fdPermissions : roleMissions
	}
	$(".loadrtkuno").css("display","block")//加载动画
	if(flag_kuno){
		flag_kuno=false;
		ajaxPostFunctionAsytrue("create",paramet,loadCallBack);
	}
	
}

/**
 * 回调函数
 * @param data
 */
function loadCallBack(data){
	if(data != undefined) {
		if(data.code == 0){
			window.location.href = "p_list";
			$(window).unbind('beforeunload');  
		}else{
			set_message("auto-close-dialogBox","提示",data.message);
		}
		flag_kuno=true;
	}else {
		set_message("auto-close-dialogBox","提示","添加失败");
		flag_kuno=true;
	}
	
}

