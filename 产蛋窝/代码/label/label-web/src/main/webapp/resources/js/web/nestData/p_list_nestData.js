var itemPage = -1;
var idParam =[];
var allRole=[];
$(function(){	
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
	 * 编辑产蛋窝数据 保存数据
	 */
	$("#edit_create").click(function(){
		var params={
				id:$('#edit_id').val(),
				inBodyWeight:$('#edit_inBodyWeight').val(),
				outBodyWeight:$('#edit_outBodyWeight').val(),
				eatMtWeight:$('#edit_eatMtWeight').val()
		};
		ajaxRequest('../nestData/update', params, function(data) {
			if (data.code == 0) {
				$("#selectAll").attr('checked',false);
				getListByPage(1,$("#search").val());
				$('#edit_win').modal('hide');
			}else{
				set_message("auto-close-dialogBox","提示",data.data);
			}
		})
	});
//取消按钮
    $("#edit_cancel").click(function(){
		$('#edit_win').modal('hide');
	});
    
	/**
	 * 删除按钮事件
	 */
	$("#deleteNestDataButt").click(function(){
		idParam.splice(0,idParam.length);
		$('input[name="usman"]:checked').each(function(){
			idParam.push(parseInt($(this).val()));
		})
		if(idParam.length>0){
			//提示框
			defind_text("auto-close-dialogBox","提示","是否删除？",doDelete, __cancel);
		}else{
			set_message("auto-close-dialogBox","提示","请选择数据");
		}
	});
	
	$("#expNestDataButt").click(function() {
		//提示是否导出用户数据
		doDown();
	})
	
    activeTime('search_startInTime', 'search_endInTime');
});


/**
 * 打开编辑对话框
 */
function editWindow(id){
	$('#edit_rfidNum').val('');
	$('#edit_inOrder').val('');
	$('#edit_inBodyWeight').val('');
	$('#edit_outBodyWeight').val('');
	$('#edit_eatMtWeight').val('');
	$('#edit_id').val('');
	var params = {id:id};
	ajaxRequest("../nestData/info",params,function(data){
		if(data.code==0){
			$('#edit_rfidNum').val(data.data.rfidNum);
			$('#edit_inOrder').val('第'+data.data.inOrder+'次');
			$('#edit_inBodyWeight').val(data.data.inBodyWeight);
			$('#edit_outBodyWeight').val(data.data.outBodyWeight);
			$('#edit_eatMtWeight').val(data.data.eatMtWeight);
			$('#edit_id').val(data.data.id);
			$('#edit_win').modal('show');
		}
	});
}

/**
 * 分页获取数据
 * @param page 起始页码
 * @param name 产品名称 
 */
function getListByPage(page,name){
	$(".loadrtkuno").css("display", "block")
	var rfidNum = $("#search_irfid_num").val();
	var workShopName = $("#search_workshop_name").val();
	var startInTime = $("#search_startInTime").val();
	var endInTime = $("#search_endInTime").val();
	var outStatus = $("#search_outStatus").val();
	var params={
			page:page,
			rows:itemPage,
			condition:{ }
	}
	if(rfidNum&&rfidNum!=''){
		params.condition.rfidNum=rfidNum;
	}
	if(workShopName&&workShopName!=''){
		params.condition.workShopName=workShopName;
	}
	if(startInTime&&startInTime!=''){
		params.condition.startInTime=startInTime;
	}
	if(endInTime&&endInTime!=''){
		params.condition.endInTime=endInTime;
	}
	if(outStatus&&outStatus!=''){
		params.condition.outStatus=outStatus;
	}
	$(".loadrtkuno").css("display", "block")
	ajaxRequest("../nestData/list",params,function(data){
		if(data.code==0){
			$("#umantable").html();//清空原有数据
			var itemCount = data.data.total;
			updatePage(itemCount);
			selectPage(page);
			var tableHtml= "";
			var rs = data.data.rows;
			if(rs.length>0){
			
				var lastStyle = '';
				var lastRfidNum='';
				var bcStyle;
				
				for(var i = 0; i<rs.length; i++){
					var user = rs[i];
					if(i!=0){
						if(lastRfidNum==user.rfidNum){
							bcStyle = lastStyle;
						}else{
							if(lastStyle==''){
								lastStyle =' style="background-color: #d4ecec;" ';
							}else{
								lastStyle = '';
							}
						}
					}
					bcStyle = lastStyle;
					lastRfidNum = user.rfidNum;
					
					tableHtml += '<tr '+bcStyle+' ><td><label class="checkbox m-n i-checks"><input type="checkbox" name="usman" value="'
						+ user.id + '"><i></i></label> </td>';
					if(user.workshopName){
						tableHtml += '<td>' + user.workshopName + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(user.rfidNum){
						tableHtml += '<td>' + user.rfidNum + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}

					if(user.outStatus==2){
						tableHtml += '<td style="color: #ea7777">已抱窝</td>';
					}else if(user.outStatus==1) {
						tableHtml += '<td style="color: #f70606">已产蛋</td>';
					}else if(user.outStatus==0){
						tableHtml += '<td>正常出窝</td>';
					}else{
						tableHtml += '<td>还未出窝</td>';
					}
					if(user.eatMtWeight){
						tableHtml += '<td>'+user.eatMtWeight+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.inOrder){
						tableHtml += '<td>第' + user.inOrder + '次</td>';						
					}else{
						tableHtml += '<td></td>';
					}

					if(user.inTime){
						tableHtml += '<td>'+formatDate(user.inTime)+'</td>';
					}else{
						tableHtml += '<td></td>';
					}

					if(user.outTime){
						tableHtml += '<td>'+formatDate(user.outTime)+'</td>';
					}else{
						tableHtml += '<td></td>';
					}

					if(user.inBodyWeight){
						tableHtml += '<td>'+user.inBodyWeight+'</td>';
					}else{
						tableHtml += '<td></td>';
					}

					if(user.outBodyWeight){
						tableHtml += '<td>'+user.outBodyWeight+'</td>';
					}else{
						tableHtml += '<td></td>';
					}

					if(user.incBodyWeight){
						tableHtml += '<td>'+user.incBodyWeight+'</td>';
					}else{
						tableHtml += '<td></td>';
					}

					if(user.mtBodyRatio){
						tableHtml += '<td>'+user.mtBodyRatio+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					tableHtml += '<td><a href="javascript:void(0)" class="colork" onclick="editWindow('+user.id+')">编辑</a>&nbsp;&nbsp;';
				}
			}
			setTimeout(function() {
				$(".loadrtkuno").css("display", "none")// 隐藏加载动画
				$("#umantable").html(tableHtml)
			}, 500)
		}else{
			set_message("auto-close-dialogBox","提示",data.message);
		}
	});
	
}

/**
 * 删除方法
 */
function doDelete(){
	ajaxRequest('../nestData/delete', idParam, function(data) {
		if (data.code == 0) {
			set_message("auto-close-dialogBox","提示","删除成功！");
			$("#selectAll").attr('checked',false);
			getListByPage(1,$("#search").val());
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	})
}


function activeTime(start, end) {
	var myDate = new Date();
	myDate = myDate.toLocaleDateString();

	var sDate = laydate.render({
		elem: "#" + start,
		type: "datetime",
		format: "yyyy-MM-dd HH:mm:ss",
		istime: false,
		btns: ['confirm'],
		done: function(value, date) {
			if(value != "") {
				eDate.config.min.year = date.year;
				eDate.config.min.month = date.month - 1;
				eDate.config.min.date = date.date;
				eDate.config.min.hours = date.hours;
				eDate.config.min.minutes = date.minutes;
				eDate.config.min.seconds = date.seconds;
			} else {
				eDate.config.min.year = "";
				eDate.config.min.month = "";
				eDate.config.min.date = "";
				eDate.config.min.hours = "";
				eDate.config.min.minutes = "";
				eDate.config.min.seconds = "";
			}
		}
	})

	var eDate = laydate.render({
		elem: "#" + end,
		type: "datetime",
		format: "yyyy-MM-dd HH:mm:ss",
		istime: false,
		btns: ['confirm'],
		done: function(value, date) {
			if(value != "") {
//				sDate.config.max.year = date.year;
//				sDate.config.max.month = date.month - 1;
//				sDate.config.max.date = date.date;
				sDate.config.min.hours = date.hours;
				sDate.config.min.minutes = date.minutes;
				sDate.config.min.seconds = date.seconds;
			} else {
				sDate.config.max.year = "";
				sDate.config.max.month = "";
				sDate.config.max.date = "";
				sDate.config.min.hours = "";
				sDate.config.min.minutes = "";
				sDate.config.min.seconds = "";
			}
		}
	})
}

/**
 * 导出excel
 */
function doDown() {
	var rfidNum = $("#search_irfid_num").val();
	var workShopName = $("#search_workshop_name").val();
	var startInTime = $("#search_startInTime").val();
	var endInTime = $("#search_endInTime").val();
	var params={	};
	if(rfidNum&&rfidNum!=''){
		params.rfidNum=rfidNum;
	}
	if(workShopName&&workShopName!=''){
		params.workShopName=workShopName;
	}
	if(startInTime&&startInTime!=''){
		params.startInTime=startInTime;
	}
	if(endInTime&&endInTime!=''){
		params.endInTime=endInTime;
	}

	ajaxRequest('../nestData/export', params, function(data) {
		if(data.code == 0) {
			window.location.href = "../../common/export/getFile?fileId=" + data.data;
		} else {
			set_message("auto-close-dialogBox", "提示", data.data);

		}
	});
}