var idsArry = []; // 保存接口返回的已选择checked 值
var paramsArry = []; // 保存提交数据的选中checked 值 
var fliterArry = []; // 保存处理过的tree id 
var flag_kuno=true;
$(function(){
	$("#saveBtn").click(function(){
		saveBtn();
	});
	/*$(window).bind('beforeunload',function(){ 
		return confirm("你是否放弃此次编辑？"); 
	}); */
	init();
});

 function init(){
	var params={
			
	};
	ajaxPostFunctionAsyfalse("../permission/treeList", params, function(data){
		if(data.code==0){
			//填充获取到的值
			if(data.data){
				loadDptList(data.data.menu);
				setAlterHtml();
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
	addStr();
}

/**
 * 转变id位数再做比较
 */
function addStr(){
	var treeObj = $.fn.zTree.getZTreeObj("dptList");
	var chk = treeObj.getCheckedNodes(false);
	for(var i=0; i<chk.length;i++){
		var chkId = chk[i].id;
		if(String(chkId).length == 1){
			chkId = '0'+chkId;
			fliterArry.push(chkId);
		}else{
			fliterArry.push(String(chkId));
		}
	}
}

function onCheck(event,treeId,treeNode){
	var treeArry = []; // 保存tree选中checked 值 
	var treeObj = $.fn.zTree.getZTreeObj("dptList");
	var nodes = treeObj.getCheckedNodes(true);
	for(var i=0; i<nodes.length;i++){
		var fdId = nodes[i].id;
		treeArry.push(fdId);
		paramsArry = treeArry;
	}
	
}

/**
 * set 编辑页面
 */
function setAlterHtml(){
	var getUrl = GetRequest();
	var _id = getUrl.id;
	var params = {
			id : _id
	}
	ajaxPostFunctionAsytrue("info",params,function(data){ // save  alter
		if(data.code == "0"){
			if(data.data){
				setListObj(data.data);
			}
		}else{
			set_message("auto-close-dialogBox","提示",data.message);
		}
	});
}

/**
 * 赋值html
 * @param data
 */
function setListObj(data){
	var msg = data;
	$("#rolename").val(msg.fdName);
	$("#roledesc").val(msg.fdDesp);
	onCheckTree(data.fdPermissions);
}

/**
 * 设置tree选中
 */
function onCheckTree(obj){
	var idsCopy = [];
	var ids = obj;
	ids = ids.split(",");
	for(var i=0;i<ids.length;i++){
		var idsIndex = ids[i];
		if(idsIndex.length == 1){
			idsIndex = '0'+idsIndex;
			idsCopy.push(idsIndex);
		}else{
			idsCopy.push(idsIndex);
		}
	}
	idsArry = idsCopy;
	var treeObj = $.fn.zTree.getZTreeObj("dptList");
	var chk = treeObj.getCheckedNodes(false);
	for(var i=0;i<fliterArry.length;i++){
		var index = filter(fliterArry[i]);
		if(index != -1){
			if(chk[i].id == Number(index)){
				chk[i].checked = true;
				treeObj.updateNode(chk[i]); // 重点在这里， 没有更新节点，会出现只有鼠标滑过节点的时候才选中
				// 给选中的父节点展开，其余的关闭
				if(chk[i].isParent){
					treeObj.expandNode(chk[i],true,false);
				}
			}
		}
	}
}

/**
 * 找到对应的id 勾选checked
 * @param num checked 的id
 * @returns
 */
function filter(num){
	var index = -1;
	for(var i=0;i<idsArry.length;i++){
		if(idsArry[i].indexOf( num)){
			index = index;
		}else{
			index = num;
		}
	}
	return index;
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
	onCheck(null,null,null);
	var params = getValue();
	var getUrl = GetRequest();
	var fdId = getUrl.id;
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
			id : fdId,
			fdPermissions : roleMissions
	}
	$(".loadrtkuno").css("display","block")//加载动画
	if(flag_kuno){
		flag_kuno=false;
		ajaxPostFunctionAsytrue("alter",paramet,loadCallBack);
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

