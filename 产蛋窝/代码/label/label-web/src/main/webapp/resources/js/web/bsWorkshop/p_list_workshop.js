var itemPage = -1;
var idParam =[];
var allRole=[];
$(function(){	
	getDepts();
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
	$("#addWorkShopButt").click(function(){
		createWindow();
	});
	
	/**
	 * 新增厂房 保存数据
	 */
	$("#add_create").click(function(){
		var params={
				num:$('#add_num').val(),
				name:$('#add_name').val(),
				dptId:$('#add_dptId').val(),
				header:$('#add_header').val(),
				address:$('#add_address').val()
		};
		ajaxRequest('../workshop/create', params, function(data) {
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
				num:$('#edit_num').val(),
				name:$('#edit_name').val(),
				dptId:$('#edit_dptId').val(),
				header:$('#edit_header').val(),
				address:$('#add_address').val()
		};
		ajaxRequest('../workshop/update', params, function(data) {
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
	$("#deleteWorkShopButt").click(function(){
		idParam.splice(0,idParam.length);
		$('input[name="usman"]:checked').each(function(){
			idParam.push(parseInt($(this).val()));
		})
		if(idParam.length>0){
			//提示框
			defind_text("auto-close-dialogBox","提示","是否删除？",doDelete, __cancel);
		}else{
			set_message("auto-close-dialogBox","提示","请选择厂房");
		}
	});
	
});

/**
 * 打开新增对话框
 */
function createWindow(){
	$('#add_num').val("");
	$('#add_name').val("");
	$('#add_dptId').val("-1");
	$('#add_header').val("");
	$('#add_address').val("");
	$('#create_win').modal('show');
}

/**
 * 打开编辑对话框
 */
function editWindow(id){
	var params = {id:id};
	ajaxRequest("../workshop/info",params,function(data){
		if(data.code==0){
			$('#edit_num').val(data.data.num);
			$('#edit_name').val(data.data.name);
			$('#edit_dptId').val(data.data.dptId);
			$('#edit_header').val(data.data.header);
			$('#edit_address').val(data.data.address);
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
	var num = $("#search_num").val();
	var name = $("#search_name").val();
	var params={
			page:page,
			rows:itemPage,
			condition:{ }
	}
	if(num&&num!=''){
		params.condition.num=num
	}
	if(name&&name!=''){
		params.condition.name=name
	}
	$(".loadrtkuno").css("display", "block")
	ajaxRequest("../workshop/list",params,function(data){
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
					if(user.num){
						tableHtml += '<td>' + user.num + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(user.name){
						tableHtml += '<td>'+user.name+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.fdName){
						tableHtml += '<td>' + user.fdName + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(user.header){
						tableHtml += '<td>'+user.header+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.address){
						tableHtml += '<td>'+user.address+'</td>';
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
	ajaxRequest('../workshop/delete', idParam, function(data) {
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
 * 获取机构
 */
function getDepts(){
	var params = {
			page:1,
			rows:9999,
			condition:{ }
	}
	ajaxRequest("../company/list",params,function(data){
		if(data.code==0){
			var rows = data.data.rows;
			var html='';
				html+='<option value="-1" >请选择机构</option>'	
			for(var i=0;i<rows.length;i++){
				//生成数据append到ul中
				var li = rows[i];
				html += '<option value="'+li.id+'" >'+li.fdName+'</option>';
			}
				$("#add_dptId").empty().append(html);
				$("#edit_dptId").empty().append(html);
		}else{
			set_message("auto-close-dialogBox","提示","机构加载失败！");
		}
	})
}
