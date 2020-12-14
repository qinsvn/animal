var zNodes;
var zTree = $.fn.zTree.getZTreeObj("treeDemo");
var setting ={
		async:{
			enable:true,
			url:'',
			autoParam:['id','name=n','level=lv'],
			otherParam:{'otherParam':'ZTreeAsyncTest'},
			dataFilter:filter,
			dataType:'json'
		},
		view:{
			showLine:false,
			selectedMulti:false, /*设置是否允许同时选中多个节点。*/
//			addHoverDom:addHoverDom,
//			removeHoverDom:removeHoverDom
		},
		edit:{
			enable:false
		},
		data:{
			simpleData:{
				enable:true,
			}
		},
		callback:{
			beforeDrag:beforeDrag,  /*设置节点拖拽*/
			beforeAsync: zTreeBeforeAsync,
			onAsyncSuccess: zTreeOnAsyncSuccess,
			onClick: onClick
		}
};

zNodes =[
         
         ];


function zTreeBeforeAsync(treeId, treeNode) {
    return (treeNode.id !== 1);
};
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
    alert(msg);
};

function filter(treeId, parentNode, childNodes){
	if(!childNodes){
		return null;
	}
	for(var i=0,l=childNodes.length;i<l;i++){
		childNodes[i].name =childNodes[i].name.replace(/\.n/g,'.');
	}
	return childNodes;
}

function onClick(e,treeId, treeNode){
	zTree.expandNode(treeNode);
	 alert(treeNode.tId + ", " + treeNode.name);
}

var newCount =1;
function addHoverDom(treeId,treeNode){
	var sObj =$('#' +treeNode.tId + '_span');
	if(treeNode.editNameFlag || $('#addBtn_' + treeNode.id).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.id 
	+ "' title='添加节点' onfocus='this.blur();'></span";
	sObj.append(addStr);
	var btn =$('#addBtn_' +treeNode.id);
	if(btn) btn.bind('click',function(){
		zTree.addNodes(treeNode,{id:(100 +newCount),pId:treeNode.id,name:'new node'
			+(newCount++)});
	});
}
function removeHoverDom(treeId,treeNode){
	$('#addBtn_' + treeNode.id).unbind().remove();
}

function beforeDrag(treeId,treeNodes){
	return false;
}
$(function(){
	getNodesData();
});

function getNodesData(){
	var param = {
			pid:null
	}
	ajaxRequestPost("../../web/mpGroup/treeList", param,function(data){
		if(data.code===0){
			zNodes = eval(data.data);
			$.fn.zTree.init($('#treeDemo'), setting, zNodes);
		}else{
			alert(data);
		}
	})
}




