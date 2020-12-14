var flag_kuno=true;
/**
 * Created by Kuno on 2018/1/11.
 * var loginInfo = localStorage.getItem("");//获取用户信息
 */
var infoId = "";
var param;
var userInfo;
var fdRoles;
var roles=[];
$(function(){
	/*$(window).bind('beforeunload',function(){ 
		return confirm("你是否放弃此次编辑？"); 
	}); */
//    $("#inner_content").slimScroll({
//        height:'100%',
//        width:'100%',
//    });
    $("#submit").click(function(){
    	doModify();
    })
    $("#update").click(function(){
    	doModify();
    })
    //取消
    $("#cancel").click(function(){
    	window.location.href="p_list";
    })
    
	
//	$("#fdPassword").blur(function(){
//		if($("#fdPassword").val()==""||$("#fdPassword").val().length<8||$("#fdPassword").val().length>20){
//			set_message("auto-close-dialogBox","提示","密码长度为8到20位");
//		}
//	})
    
  
	$("#checkedpwd").blur(function(){
		var fdPassword=$("#fdPassword").val();
		var checkedpwd=$("#checkedpwd").val();
		if(fdPassword!=checkedpwd){
			set_message("auto-close-dialogBox","提示","两次输入密码不一致,请重新确认");
		}
	})
	
	$("#selectRole").click(function(){
		$('input[name="role"]').prop("checked",false);//先取消全选
		//把原有的角色勾选上
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
	})
	$("#cancelRole").click(function(){
		hideModel();
	})
	
	$("#roles").click(function(){
		//获取勾选上的所有角色，
		var role = "";
		$('input[name="role"]:checked').each(function(){
			role += $(this).val()+",";
		})
		if(role){
			role = role.substring(0,role.length-1);
		}
		fdRoles = role;
		hideModel();
		var roleName = "";
		if(fdRoles){
			var r = fdRoles.split(",");
			for(var i=0;i<r.length;i++){
				for(var j=0;j<roles.length;j++){
					if(roles[j].id==r[i]){
						roleName+=roles[j].fdName+",";
						continue;
					}
				}
			}
			roleName=roleName.substring(0,roleName.length-1);
		}
		if(roleName==""){
			roleName="请选择角色";
		}
		$("#selectRole").html(roleName);
	})
	
	
})



window.onload=function(){
	param = GetRequest();
	userInfo = $.parseJSON(localStorage.getItem("loginInfo"));
	if(userInfo!=undefined&&userInfo!=null&&userInfo.organId==0){
		getOrgan();//联盟用户
		$("#organBox").removeClass("hidden");
	}
	if(param&&param.id!=undefined){
		infoId = param.id;
		//infoId有值表示为编辑，无值为新建
		info(infoId);
		$("#liTitle").html("编辑用户");
		$("#headTitle").html("编辑用户信息");
		$(".span_pwd").addClass("hidden");
		$("#update").show();
		$("#submit").hide();
	}else{
		$("#update").hide();
		$("#submit").show();
		$(".span_pwd").removeClass("hidden");
	}
	getAllRole();
}

/**
 * 隐藏弹窗
 */
function hideModel(){
	$('#modal-form').modal('hide');
}


/**
 * 获取机构
 */
function getOrgan(_id){
	var params = {
			page:1,
			rows:9999,
			condition:{}
	}
	ajaxRequest("../company/list",params,function(data){
		if(data.code==0){
			var rows = data.data.rows;
			var html='';
			if(_id&&_id==-1){
				html+='<li dataid="-1" class="active"><input type="radio" name="d-s-r" ><a href="#">请选择机构</a></li>'	
					$("#jgTitle").html("请选择机构");
			}else{
				html+='<li dataid="-1" class=""><input type="radio" name="d-s-r" ><a href="#">请选择机构</a></li>'	
					$("#jgTitle").html("请选择机构");
			}
			for(var i=0;i<rows.length;i++){
				//生成数据append到ul中
				var li = rows[i];
				if(li.id==parseInt(_id)){
					html += '<li dataid='+li.id+' class="fdOrganId_'+li.id+' active"><input type="radio" name="d-s-r"><a href="#">'+li.fdName+'</a></li>';
					$("#organBtn .dropdown-label").html(li.fdName);
				}else{
					html += '<li dataid='+li.id+' class="fdOrganId_'+li.id+'"><input type="radio" name="d-s-r"><a href="#">'+li.fdName+'</a></li>';
				}
				
			}
			if(userInfo!=undefined&&userInfo!=null&&userInfo.organId==0){
				$(".fdOrganId_"+info.fdOrganId).find('a').click();			
			}
			$("#organ").empty().append(html);
		}else{
			set_message("auto-close-dialogBox","提示","机构加载失败！");
		}
	},null,null,null,null,"false")
}
/**
 * 加载角色列表
 */
function getAllRole(){
	var params={
			pagenum:1,
			pagesize:9999
	}
	ajaxPostFunctionAsyfalse("../role/companyRolelist",params,function(data){
		if(data.code==0){
			if(data.data.rows){
				roles = data.data.rows;
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
 * 查询详情
 * @param id
 */
function info(id){
	var params = {
			id:id
	}
	ajaxPostFunctionAsytrue("../user/info", params, function(data){
		if(data.code==0){
			//填充获取到的值
			if(data.data){
				setValues(data.data);
				getOrgan(data.data.fdConpanyId);
//				if(userInfo!=undefined&&userInfo!=null&&userInfo.organId==0){
					$(".fdOrganId_"+data.data.fdConpanyId).find('a').click();
//				}
			}
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	});
}
function setValues(info){
	fdRoles=info.fdRoles;//用户角色
	$("#fdAccount").val(info.fdAccount);
//	$("#fdPassword").val(info.fdPassword);
//	$("#checkedpwd").val(info.fdPassword);
	$("#fdName").val(info.fdName);
	$("#fdEmail").val(info.fdEmail);
	$("#fdPhone").val(info.fdPhone);
	$("#fdIdcard").val(info.fdIdcard);
	//用户类型
	if(info.fdLocked){
		if(info.fdLocked==1){
			$("#fdLockedName").text("锁定");
			$("#isLock").addClass("active");
		}
	}
	//状态
	//角色
	var roleName = "";
	if(info.fdRoles){
		var r = fdRoles.split(",");
		for(var i=0;i<r.length;i++){
			for(var j=0;j<roles.length;j++){
				if(roles[j].id==r[i]){
					roleName+=roles[j].fdName+",";
					continue;
				}
			}
		}
		roleName=roleName.substring(0,roleName.length-1);
	}
	if(roleName==""){
		roleName="请选择角色";
	}
	$("#selectRole").html(roleName);
}
function getValues(){
	var params={
			//fdAge:$("#age").val(),
			fdAccount:$("#fdAccount").val(),
			fdPassword:$("#fdPassword").val(),//转换时间戳
			fdName:$("#fdName").val(),
			fdEmail:$("#fdEmail").val(),
			fdPhone:$("#fdPhone").val(),
			fdEmail:$("#fdEmail").val(),
			fdPhone:$("#fdPhone").val(),
			fdIdcard:$("#fdIdcard").val(),
			fdLocked:$("#fdLocked").find("li.active a").data("val"),
			fdRoles:fdRoles
	}
	//获取类型和状态
	
	if(infoId){
		params.id==infoId;
	}
	params.fdType=2;
	return params
}
/**
 * 添加
 */
function doModify(){
	var params = getValues();
	var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
	var v = $("#fdAccount").val();
	if(v==""||v.length<6||v.length>26){
		set_message("auto-close-dialogBox","提示","账号长度为6到26位");
		$("#fdAccount").focus();
		return;
	}else{
		if(!checkZN("fdAccount","账号",20)){
			return;
		}else{
			if(!infoId){
				var account={
						fdAccount:v
				}
				ajaxPostFunctionAsyfalse("../user/infoByAccount", account, function(data){
					if(data.code==0){
						//填充获取到的值
						if(!data.data){
							set_message("auto-close-dialogBox","提示","该账号已存在！");
							$("#fdAccount").focus();
							return;
						}else{
//							if(userInfo!=undefined&&userInfo!=null&&userInfo.organId==0){//联盟用户需要选择机构
								params.fdConpanyId=$("#organ").find(".active").attr("dataid");
								if(!params.fdConpanyId||params.fdConpanyId<0){
//									set_message("auto-close-dialogBox","提示","请选择机构信息");
//									return
									params.fdConpanyId=0;//超级账号
								}
//							}
							if($("#fdPassword").val()==""||$("#fdPassword").val().length<8||$("#fdPassword").val().length>20){
								set_message("auto-close-dialogBox","提示","密码长度为8到20位");
								$("#fdPassword").focus();
								return;
							}
							if($("#checkedpwd").val()==""){
								set_message("auto-close-dialogBox","提示","请确定密码");
								$("#checkedpwd").focus();
								return;
							}else{
								var fdPassword=$("#fdPassword").val();
								var checkedpwd=$("#checkedpwd").val();
								if(fdPassword!=""&&checkedpwd!=""){
									if(fdPassword!=checkedpwd){
										set_message("auto-close-dialogBox","提示","两次输入密码不一致,请重新确认");
										$("#checkedpwd").focus();
										return 
									}
								}
							}
							if(!checkCZ('fdName','真实姓名',30)){
								return;
							}
							if($.trim($("#fdEmail").val()) !="" ){
								var email= $.trim($("#fdEmail").val());
								var pattern =  /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
								if(!pattern.test(email)){
									set_message("auto-close-dialogBox","提示","邮箱不合法，请修改！例：xxx@xxx.xxx");
									return;
								}
							}
							
							if($("#fdPhone").val()==""){
//								set_message("auto-close-dialogBox","提示","请输入手机号码");
//								$("#fdPhone").focus();
//								return;
							}else{
								if(!$("#fdPhone").val().match(/^1(3|4|5|7|8)\d{9}$/)){
									set_message("auto-close-dialogBox","提示","手机号码格式不正确");
									$("#fdPhone").focus();
									return ;
								}
							}
							
							if($.trim($("#fdIdcard").val()) !=""){
								var reg = /(^\d{15}$)|^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X|x)$/;
								if(!reg.test($("#fdIdcard").val())){
									set_message("auto-close-dialogBox","提示","身份证格式不正确");
									$("#fdIdcard").focus();
									return 	
								}
							}
							if(infoId){//修改
								params.id=infoId;
								ajaxPostFunctionAsytrue("../user/update",params,function(data){
									if(data.code==0){
										set_message("auto-close-dialogBox","提示","修改成功！");
										$(window).unbind('beforeunload'); 
										window.location.href = "p_list"
									}else{
										set_message("auto-close-dialogBox","提示",data.data);
									}
								})
							}else{//添加
								ajaxPostFunctionAsytrue("../user/create",params,function(data){
									if(data.code==0){
										set_message("auto-close-dialogBox","提示","添加成功！");
										$(window).unbind('beforeunload'); 
										window.location.href = "p_list"
									}else{
										set_message("auto-close-dialogBox","提示",data.data);
									}
								})
							}
						}
					}else{
						set_message("auto-close-dialogBox","提示",data.data);
						$("#fdAccount").focus();
						return;
					}
				});
			}else{
//				if(userInfo!=undefined&&userInfo!=null&&userInfo.organId==0){//联盟用户需要选择机构
					params.fdConpanyId=$("#organ").find(".active").attr("dataid");
					if(!params.fdConpanyId||params.fdConpanyId<0){
						//set_message("auto-close-dialogBox","提示","请选择机构信息");
						//return
						params.fdConpanyId=0;//超级账号
					}
//				}
				if($("#fdName").val()==""){
					set_message("auto-close-dialogBox","提示","请输入真实姓名");
					$("#fdName").focus();
					return;
				}else{
					if(!checkCZ('fdName','真实姓名',30)){
						return;
					}
				}
				if($.trim($("#fdEmail").val()) !="" ){
					var email= $.trim($("#fdEmail").val());
					var pattern =  /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
					if(!pattern.test(email)){
						set_message("auto-close-dialogBox","提示","邮箱不合法，请修改！例：xxx@xxx.xxx");
						return;
					}
				}
				if($.trim($("#fdPhone").val())==""){
//					set_message("auto-close-dialogBox","提示","请输入手机号码");
//					$("#fdPhone").focus();
//					return;
				}else{
					if(!$("#fdPhone").val().match(/^1(3|4|5|7|8)\d{9}$/)){
						set_message("auto-close-dialogBox","提示","手机号码格式不正确");
						$("#fdPhone").focus();
						return ;
					}
				}
				if($.trim($("#fdIdcard").val()) !=""){
					var reg = /(^\d{15}$)|^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X|x)$/;
					if(!reg.test($("#fdIdcard").val())){
						set_message("auto-close-dialogBox","提示","身份证格式不正确");
						$("#fdIdcard").focus();
						return 	
					}
				}
				if($("#fdPassword").val()||$("#fdPassword").val()!=""){
					if($("#fdPassword").val().length<8||$("#fdPassword").val().length>20){
						set_message("auto-close-dialogBox","提示","密码长度为8到20位");
						$("#fdPassword").focus();
						return;
					}					
					if($("#checkedpwd").val()==""){
						set_message("auto-close-dialogBox","提示","请确定密码");
						$("#checkedpwd").focus();
						return;
					}else{
						var fdPassword=$("#fdPassword").val();
						var checkedpwd=$("#checkedpwd").val();
						if(fdPassword!=""&&checkedpwd!=""){
							if(fdPassword!=checkedpwd){
								set_message("auto-close-dialogBox","提示","两次输入密码不一致,请重新确认");
								$("#checkedpwd").focus();
								return 
							}
						}
					}
				}
				if(flag_kuno){
					flag_kuno=false;
					if(infoId){//修改
						params.id=infoId;
						ajaxPostFunctionAsytrue("../user/update",params,function(data){
							if(data.code==0){
								set_message("auto-close-dialogBox","提示","修改成功！");
								flag_kuno=true;
								window.location.href = "p_list"
							}else{
								set_message("auto-close-dialogBox","提示",data.data);
								flag_kuno=true;
							}
						})
					}else{//添加
						ajaxPostFunctionAsytrue("../user/create",params,function(data){
							if(data.code==0){
								set_message("auto-close-dialogBox","提示","添加成功！");
								flag_kuno=true;
								window.location.href = "p_list"
							}else{
								set_message("auto-close-dialogBox","提示",data.data);
								flag_kuno=true;
							}
						})
					}
				}
				
				
			}
			
		}
	}	
	
	
}
