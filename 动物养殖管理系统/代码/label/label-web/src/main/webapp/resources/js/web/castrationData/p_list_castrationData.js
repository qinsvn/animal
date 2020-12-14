var itemPage = -1;
var idParam =[];
var allRole=[];
$(function(){
	activeTime('startCreateTime', 'endCreateTime');
	itemPage=10;
	initPage();
	getListByPage(1,$("#search").val());
	
	/**
	 * 搜索按钮
	 */
	$("#searchBtn").click(function(){
		getListByPage(1,$("#search").val());
	});
	
	/**
	 * 删除按钮事件
	 */
	$("#deleteDeviceButt").click(function(){
		idParam.splice(0,idParam.length);
		$('input[name="usman"]:checked').each(function(){
			idParam.push(parseInt($(this).val()));
		});
		if(idParam.length>0){
			//提示框
			defind_text("auto-close-dialogBox","提示","是否删除？",doDelete, __cancel);
		}else{
			set_message("auto-close-dialogBox","提示","请选择蛋类");
		}
	});
	

	/**
	 * 编辑 保存数据
	 */
	$("#edit_create").click(function(){
		var params={
				id:$('#edit_id').val(),
				result:$('#edit_result').val()
		};
		ajaxRequest('../castrationData/update', params, function(data) {
			if (data.code == 0) {
				$("#selectAll").attr('checked',false);
				getListByPage(1,$("#search").val());
				$('#edit_win').modal('hide');
			}else{
				set_message("auto-close-dialogBox","提示",data.data);
			}
		});
	});
	//取消按钮
	$("#edit_cancel").click(function(){
		$('#edit_win').modal('hide');
	});

	$("#export").click(function() {
		doDown();
	});
});

/**
 * 分页获取数据
 * @param page 起始页码
 * @param name 产品名称 
 */
function getListByPage(page,name){
	$(".loadrtkuno").css("display", "block");
	var rfidNum = $("#rfidNum").val();
	var chikenType = $("#chikenType").val();
	var startCreateTime = $("#startCreateTime").val();
	var endCreateTime = $("#endCreateTime").val();
	var params={
			page:page,
			rows:itemPage,
			condition:{ }
	};
	if(rfidNum&&rfidNum!=''){
		params.condition.rfidNum=rfidNum;
	}

	if(chikenType&&chikenType!=''){
		params.condition.chikenType=chikenType;
	}
	if(startCreateTime&&startCreateTime!=''){
		params.condition.startCreateTime=startCreateTime;
	}
	if(endCreateTime&&endCreateTime!=''){
		params.condition.endCreateTime=endCreateTime;
	}
	$(".loadrtkuno").css("display", "block");
	ajaxRequest("../castrationData/list",params,function(data){
		if(data.code==0){
			$("#umantable").html();//清空原有数据
			var itemCount = data.data.total;
			updatePage(itemCount);
			selectPage(page);
			var tableHtml= "";
			var rs = data.data.rows;
			if(rs.length>0){
				for(var i = 0; i<rs.length; i++){
					var castrationDataData = rs[i];
					tableHtml += '<tr><td><label class="checkbox m-n i-checks"><input type="checkbox" name="usman" value="'
						+ castrationDataData.id + '"><i></i></label> </td>';
					if(castrationDataData.rfidNum){
						tableHtml += '<td>' + castrationDataData.rfidNum + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(castrationDataData.chikenType){
						tableHtml += '<td>' + castrationDataData.chikenType+ '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(castrationDataData.createTime){
						tableHtml += '<td>' + formatDate(castrationDataData.createTime)  + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(castrationDataData.createUser){
						tableHtml += '<td>'+castrationDataData.createUser+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(castrationDataData.orgName){
						tableHtml += '<td>'+castrationDataData.orgName+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(castrationDataData.result){
						tableHtml += '<td>'+castrationDataData.result+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
//					tableHtml += '<td><a href="javascript:void(0)" class="colork" onclick="editWindow('+castrationDataData.id+')">编辑</a>&nbsp;&nbsp;';
					var param = "'"+castrationDataData.id+"$#$"+castrationDataData.rfidNum+"$#$"+castrationDataData.chikenType+"$#$"+castrationDataData.result+"'";
					tableHtml += '<td><a href="javascript:void(0)" class="colork" onclick="editWindow('+param+')">编辑</a>&nbsp;&nbsp;';
				}
			}
			setTimeout(function() {
				$(".loadrtkuno").css("display", "none");// 隐藏加载动画
				$("#umantable").html(tableHtml);
			}, 500);
		}else{
			set_message("auto-close-dialogBox","提示",data.message);
		}
	});
	
}

/**
 * 删除方法
 */
function doDelete(){
	ajaxRequest('../castrationData/delete', idParam, function(data) {
		if (data.code == 0) {
			set_message("auto-close-dialogBox","提示","删除成功！");
			$("#selectAll").attr('checked',false);
			getListByPage(1,$("#search").val());
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	});
}


/**
 * 打开编辑对话框
 */
function editWindow(params){
//	var param = "'"+castrationDataData.id+"$#$"+castrationDataData.rfidNum+"$#$"+castrationDataData.chikenType+"$#$"+castrationDataData.result+"'";
	var array = params.split("$#$");
	$('#edit_id').val(array[0]);
	$('#edit_rfidNum').val(array[1]);
	$('#edit_chikenType').val(array[2]);
	$('#edit_result').val(array[3]);
	$('#edit_win').modal('show');
}

/**
 * 导出excel
 */
function doDown() {
	var rfidNum = $("#rfidNum").val();
	var chikenType = $("#chikenType").val();
	var startCreateTime = $("#startCreateTime").val();
	var endCreateTime = $("#endCreateTime").val();
	var params={ };
	if(rfidNum&&rfidNum!=''){
		params.rfidNum=rfidNum;
	}

	if(chikenType&&chikenType!=''){
		params.chikenType=chikenType;
	}
	if(startCreateTime&&startCreateTime!=''){
		params.startCreateTime=startCreateTime;
	}
	if(endCreateTime&&endCreateTime!=''){
		params.endCreateTime=endCreateTime;
	}
	
	ajaxRequest('../castrationData/export', params, function(data) {
		if(data.code == 0) {
			window.location.href = "../../common/export/getFile?fileId=" + data.data;
		} else {
			set_message("auto-close-dialogBox", "提示", data.data);

		}
	});
}