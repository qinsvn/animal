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
			set_message("auto-close-dialogBox","提示","请选择禽类");
		}
	});
	
	/**
	 * 编辑 保存数据
	 */
	$("#edit_create").click(function(){
		var params={
				id:$('#edit_id').val(),
				chikenType:$('#edit_chikenType').val(),
				weight:$('#edit_weight').val(),
				pupplier:$('#edit_pupplier').val()
		};
		ajaxRequest('../chicken/update', params, function(data) {
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
	var pupplier = $("#pupplier").val();
	var startCreateTime = $("#startCreateTime").val();
	var endCreateTime = $("#endCreateTime").val();
	var status = $("#status").val();
	var unImmu = $("#unImmu").val();
	
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
	if(pupplier&&pupplier!=''){
		params.condition.pupplier=pupplier;
	}
	if(startCreateTime&&startCreateTime!=''){
		params.condition.startCreateTime=startCreateTime;
	}
	if(endCreateTime&&endCreateTime!=''){
		params.condition.endCreateTime=endCreateTime;
	}
	if(status&&status!=''&&status!='-1'){
		params.condition.status=status;
	}
	if(unImmu&&unImmu!=''){
		params.condition.unImmu=unImmu;
	}
	$(".loadrtkuno").css("display", "block");
	ajaxRequest("../chicken/list",params,function(data){
		if(data.code==0){
			$("#umantable").html();//清空原有数据
			var itemCount = data.data.total;
			updatePage(itemCount);
			selectPage(page);
			var tableHtml= "";
			var rs = data.data.rows;
			if(rs.length>0){
				for(var i = 0; i<rs.length; i++){
					var chickenData = rs[i];
					tableHtml += '<tr><td><label class="checkbox m-n i-checks"><input type="checkbox" name="usman" value="'
						+ chickenData.id + '"><i></i></label> </td>';
					if(chickenData.rfidNum){
						tableHtml += '<td>' + chickenData.rfidNum + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(chickenData.chikenType){
						tableHtml += '<td>' + chickenData.chikenType + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(chickenData.weight){
						tableHtml += '<td>' + chickenData.weight + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(chickenData.pupplier){
						tableHtml += '<td>' + chickenData.pupplier + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(chickenData.createTime){
						tableHtml += '<td>' + formatDate(chickenData.createTime) + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(chickenData.createUser){
						tableHtml += '<td>' + chickenData.createUser + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(chickenData.orgName){
						tableHtml += '<td>'+chickenData.orgName+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(chickenData.status=='0'){
						tableHtml += '<td>饲养中</td>';
					} else if(chickenData.status=='2'){
						tableHtml += '<td>已出库</td>';
					}else if(chickenData.status=='3'){
						tableHtml += '<td>次品</td>';
					}else{
						tableHtml += '<td></td>';
					}
					var param = "'"+chickenData.id+"$#$"+chickenData.rfidNum+"$#$"+chickenData.chikenType+"$#$"+chickenData.weight+"$#$"+chickenData.pupplier+"'";
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
	ajaxRequest('../chicken/delete', idParam, function(data) {
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
	var array = params.split("$#$");
	$('#edit_id').val(array[0]);
	$('#edit_rfidNum').val(array[1]);
	$('#edit_chikenType').val(array[2]);
	$('#edit_weight').val(array[3]);
	$('#edit_pupplier').val(array[4]);
	$('#edit_win').modal('show');
}



/**
 * 导出excel
 */
function doDown() {
	var rfidNum = $("#rfidNum").val();
	var chikenType = $("#chikenType").val();
	var pupplier = $("#pupplier").val();
	var startCreateTime = $("#startCreateTime").val();
	var endCreateTime = $("#endCreateTime").val();
	var params={ };
	if(rfidNum&&rfidNum!=''){
		params.rfidNum=rfidNum;
	}
	if(chikenType&&chikenType!=''){
		params.chikenType=chikenType;
	}
	if(pupplier&&pupplier!=''){
		params.pupplier=pupplier;
	}
	if(startCreateTime&&startCreateTime!=''){
		params.startCreateTime=startCreateTime;
	}
	if(endCreateTime&&endCreateTime!=''){
		params.endCreateTime=endCreateTime;
	}
	
	ajaxRequest('../chicken/export', params, function(data) {
		if(data.code == 0) {
			window.location.href = "../../common/export/getFile?fileId=" + data.data;
		} else {
			set_message("auto-close-dialogBox", "提示", data.data);

		}
	});
}