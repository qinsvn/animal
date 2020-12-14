var itemPage = -1;
var idParam =[];
var allRole=[];
$(function(){	
	getWorkShops();
	itemPage=10;
	initPage();
	getListByPage(1,$("#search").val());
	
	/**
	 * 搜索按钮
	 */
	$("#searchBtn").click(function(){
		getListByPage(1,$("#search").val());
	})
	
	/**
	 * 新增厂房 打开对话框
	 */
	$("#addDeviceButt").click(function(){
		createWindow();
	});
	
	/**
	 * 新增厂房 保存数据
	 */
	$("#add_create").click(function(){
		var params={
				deviceNum:$('#add_device_num').val(),
				workshopId:$('#add_workshopId').val(),
				deviceName:$('#add_device_name').val()
		};
		ajaxRequest('../device/create', params, function(data) {
			if (data.code == 0) {
				$("#selectAll").attr('checked',false);
				getListByPage(1,$("#search").val());
				$('#create_win').modal('hide');
			}else{
				set_message("auto-close-dialogBox","提示",data.data);
			}
		})
	});

	//取消按钮
	$("#add_cancel").click(function(){
		$('#create_win').modal('hide');
	});
	
	/**
	 * 编辑厂房 保存数据
	 */
	$("#edit_create").click(function(){
		var params={
				id:$('#edit_id').val(),
				deviceNum:$('#edit_device_num').val(),
				workshopId:$('#edit_workshopId').val(),
				deviceName:$('#edit_device_name').val()
		};
		ajaxRequest('../device/update', params, function(data) {
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
	$("#deleteDeviceButt").click(function(){
		idParam.splice(0,idParam.length);
		$('input[name="usman"]:checked').each(function(){
			idParam.push(parseInt($(this).val()));
		})
		if(idParam.length>0){
			//提示框
			defind_text("auto-close-dialogBox","提示","是否删除？",doDelete, __cancel);
		}else{
			set_message("auto-close-dialogBox","提示","请选择设备");
		}
	});
	
});

/**
 * 打开新增对话框
 */
function createWindow(){
	$('#add_device_num').val("");
	$('#add_workshopId').val("-1");
	$('#add_device_name').val("");
	$('#create_win').modal('show');
}

/**
 * 打开编辑对话框
 */
function editWindow(id){
	var params = {id:id};
	ajaxRequest("../device/info",params,function(data){
		if(data.code==0){
			$('#edit_device_num').val(data.data.deviceNum);
			$('#edit_workshopId').val(data.data.workshopId);
			$('#edit_device_name').val(data.data.deviceName);
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
	var deviceNum = $("#search_device_num").val();
	var workShopName = $("#search_work_shop_name").val();
	var params={
			page:page,
			rows:itemPage,
			condition:{ }
	}
	if(deviceNum&&deviceNum!=''){
		params.condition.deviceNum=deviceNum
	}
	if(workShopName&&workShopName!=''){
		params.condition.workShopName=workShopName
	}
	$(".loadrtkuno").css("display", "block")
	ajaxRequest("../device/list",params,function(data){
		if(data.code==0){
			$("#umantable").html();//清空原有数据
			var itemCount = data.data.total;
			updatePage(itemCount);
			selectPage(page);
			var tableHtml= "";
			var rs = data.data.rows;
			if(rs.length>0){
				for(var i = 0; i<rs.length; i++){
					var user = rs[i];
					tableHtml += '<tr><td><label class="checkbox m-n i-checks"><input type="checkbox" name="usman" value="'
						+ user.id + '"><i></i></label> </td>';
					if(user.deviceNum){
						tableHtml += '<td>' + user.deviceNum + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(user.workShopName){
						tableHtml += '<td>'+user.workShopName+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.dptName){
						tableHtml += '<td>' + user.dptName + '</td>';						
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
	ajaxRequest('../device/delete', idParam, function(data) {
		if (data.code == 0) {
			set_message("auto-close-dialogBox","提示","删除成功！");
			$("#selectAll").attr('checked',false);
			getListByPage(1,$("#search").val());
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	})
}

/**
 * 获取厂房
 */
function getWorkShops(){
	var params = {
			page:1,
			rows:9999,
			condition:{ }
	}
	ajaxRequest("../workshop/list",params,function(data){
		if(data.code==0){
			var rows = data.data.rows;
			var html='';
				html+='<option value="-1" >请选择厂房</option>'	
			for(var i=0;i<rows.length;i++){
				//生成数据append到ul中
				var li = rows[i];
				html += '<option value="'+li.id+'" >'+li.name+'</option>';
			}
				$("#add_workshopId").empty().append(html);
				$("#edit_workshopId").empty().append(html);
		}else{
			set_message("auto-close-dialogBox","提示","厂房加载失败！");
		}
	})
}
