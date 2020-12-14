var itemPage = -1;
var idParam =[];
var allRole=[];
$(function(){	
	itemPage=10;
	initPage();
	getAllRole();
	getListByPage(1,$("#search").val());
	/**
	 * 搜索按钮
	 */
	$("#searchBtn").click(function(){
		getListByPage(1,$("#search").val());
	})
	/**
	 * 删除按钮事件
	 */
	$("#deleteUser").click(function(){
		idParam.splice(0,idParam.length);
		$('input[name="usman"]:checked').each(function(){
			idParam.push(parseInt($(this).val()));
		})
		if(idParam.length>0){
			//提示框
			defind_text("auto-close-dialogBox","提示","是否删除？",doDelete, __cancel);
		}else{
			set_message("auto-close-dialogBox","提示","请选择用户");
		}
	});
	
	$("#roles").click(function(){
		var role ="";
		$('input[name="role"]:checked').each(function(){
			role+=$(this).val()+",";
		})
		if(role){
			role=role.substring(0,role.length-1);
			var userid = $("#userid").val();
			if(userid){
				doBind(userid,role);
			}else{
				set_message("auto-close-dialogBox","提示","获取用户失败");
			}
		}else{
			set_message("auto-close-dialogBox","提示","请选择角色");
		}
		
		//遍历数据，生成fdRoles，更新fdRoles 保存关系到角色关系表
	})
	
	$("#cancel").click(function(){
		hideModel();
	})
	
	
	$("#carousel").click(function(){
		window.location.href = "../carousel/p_info?type=1"
	})
})

function doBind(userid,roleid){
	var params = {
			userId:userid,
			ids:roleid,
			
	}
	ajaxPostFunctionAsytrue('../user/bind', params, function(data) {
		if (data.code == 0) {
			set_message("auto-close-dialogBox","提示","绑定成功！");
			$("#cancel").click();
			getListByPage(1,$("#search").val());
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	})
}

/**
 * 分配角色弹窗
 */
function createWindow(e){
	$('input[name="role"]').prop("checked",false);
	var userId = $(e).data("id");
	var fdRoles = $(e).data("role")+"";
	$("#userid").val(userId);
	if(fdRoles){
		var fdRoleList = fdRoles.split(",");
		for(var i=0;i<fdRoleList.length;i++){
			$('input[name="role"]').each(function(){
				if(fdRoleList[i]==$(this).val()){
					//勾选
					$(this).prop("checked",true);
					
				}
			})
		}
	}
	$('#modal-form').modal('show');
}

/**
 * 隐藏弹窗
 */
function hideModel(){
	$('#modal-form').modal('hide');
}

/**
 * 分页获取数据
 * @param page 起始页码
 * @param name 产品名称 
 */
function getListByPage(page,name){
	
	$(".loadrtkuno").css("display", "block")
	var fdPhone = $("#fdPhone").val();
	var fdLocked = $("#fdLocked").val();
	var companyName = $("#companyName").val();
	var params={
			page:page,
			rows:itemPage,
			condition:{
				fdName:name,
			}
	}
	if(fdPhone&&fdPhone!=''){
		params.condition.fdPhone=fdPhone
	}
	if(fdLocked&&fdLocked!=''){
		params.condition.fdLocked=fdLocked
	}
	if(companyName&&companyName!=''){
		params.condition.companyName=companyName
	}
	$(".loadrtkuno").css("display", "block")
	ajaxRequest("../user/list",params,function(data){
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
					if(user.fdAccount){
						tableHtml += '<td>' + user.fdAccount + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(user.fdEmail){
						tableHtml += '<td>'+user.fdEmail+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.fdName){
						tableHtml += '<td>' + user.fdName + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(user.fdPhone){
						tableHtml += '<td>'+user.fdPhone+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.fdIdcard){
						tableHtml += '<td>'+user.fdIdcard+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.companyName){
						tableHtml += '<td>'+user.companyName+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					
					//待修改
					if(user.fdRoles){
						var roles = user.fdRoles.split(",");
						var roleNames="";
						for(var r=0;r<roles.length;r++){
							var roleId = roles[r];
							for(var j=0;j<allRole.length;j++){
								var role = allRole[j];
								if(role.id==roleId){
									roleNames+=role.fdName+",";
									continue;
								}
							}
						}
						if(roleNames){
							roleNames=roleNames.substring(0,roleNames.length-1);
							tableHtml += '<td>'+roleNames+'</td>';
						}else{
							tableHtml += '<td></td>';
						}
					}else{
						tableHtml += '<td></td>';
					}
					//待修改
					
					tableHtml += '<td>'+getTimeFormat(user.fdCreateTime, "yyyy-MM-dd HH:mm:ss")+'</td>';
					var lock="未锁定";
					if(user.fdLocked){
						if(user.fdLocked==1){
							lock="锁定";
						}else if(user.fdLocked==0){
							lock="未锁定";
						}
					}
					tableHtml += '<td>'+lock+'</td>';
					tableHtml += '<td><a href="p_info?id='+ user.id	+ '" class="colork">编辑</a>&nbsp;&nbsp;';
					tableHtml += '<a href="javascript:void(0)" class="colork" onclick="createWindow(this)" data-id="'+user.id+'" data-role = "'+user.fdRoles+'">分配角色</a></td></tr>';
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
	var params = {
			ids:idParam
	}
	ajaxRequest('../user/delete', params, function(data) {
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
 * 启用
 */
//function doUpUser(){
//	for(var i=0;i<idParam.length;i++){
//		var start = new Date($("#start"+idParam[i]).html());
//		var end = new Date($("#end"+idParam[i]).html());
//		var now = new Date();
//		if(start>now){//开始时间在现在之后
//			if(end>now){//结束时间在现在之后
//				//可启用，需要把开始时间变更为当前
//			}else{//结束时间在现在之前
//				set_message("auto-close-dialogBox","提示","时间设置不正确，启用失败");
//				return;
//				//不可启用，结束时间大于开始时间
//			}
//		}else{//开始时间在现在之前
//			if(end>now){//结束时间在现在之后
//				//可以启用
//				
//			}else{//结束时间在现在之前
//				if(end>start){
//					//超时自动停用
//				}else{
//					//结束时间大于开始
//				}
//				set_message("auto-close-dialogBox","提示","时间设置不正确，启用失败");
//				return;
//			}
//		}
//	}
//	var params = {
//			ids:idParam
//	}
//	ajaxRequest('../user/upUser', params, function(data) {
//		if (data.code == 0) {
//			$("#selectAll").attr('checked',false);
//			set_message("auto-close-dialogBox","提示","启用成功！");
//			getListByPage(1,$("#search").val());
//		}else{
//			set_message("auto-close-dialogBox","提示",data.data);
//		}
//	})
//}

/**
 * 停用
 */
//function doDownUser(){
//	var params = {
//			ids:idParam
//	}
//	ajaxRequest('../user/downUser', params, function(data) {
//		if (data.code == 0) {
//			$("#selectAll").attr('checked',false);
//			set_message("auto-close-dialogBox","提示","停用成功！");
//			getListByPage(1,$("#search").val());
//		}else{
//			set_message("auto-close-dialogBox","提示",data.data);
//		}
//	})
//}

/**
 * 加载角色列表
 */
function getAllRole(){
	var params={
			pagenum:1,
			pagesize:9999
	}
	ajaxPostFunctionAsyfalse("../role/list",params,function(data){
		if(data.code==0){
			if(data.data.rows){
				allRole = data.data.rows;
				var html="";
				for(var i=0;i<data.data.rows.length;i++){
					var role = data.data.rows[i];
					html+='<tr><td><label class="checkbox m-n i-checks"><input type="checkbox" name="role" class="role_sel" value="'+role.id+'" ><i></i></label>';
					html+='<td>'+role.fdName+'</td>';
					html+='<td>'+role.fdDesp+'</td></tr>';
				}
				$("#rolesBody").empty();
				$("#rolesBody").append(html);
			}
		}else{
			set_message("auto-close-dialogBox","提示","角色加载失败！");
		}
	})
}
/**
 * 跳转到添加页面
 */
function addUser(){
	window.location.href = "p_info"
}


