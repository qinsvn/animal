var userInfo;
$(function(){
	
	userInfo = $.parseJSON(localStorage.getItem("loginInfo"));
	
	$("#passwordCancel").click(function(){
		$("#modal-form").click();
	})
	
//	$("#oldpassword").blur(function(){
//		var password = $("#oldpassword").val();
//		if(password){
//			if(password.length<8||password.length>20){
//				set_message("auto-close-dialogBox","提示","密码长度为8到20位");
//				$("#oldpassword").focus();
//			}
//		}else{
//			set_message("auto-close-dialogBox","提示","请输入原始密码");
//			$("#oldpassword").focus();
//		}
//	})
	
//	$("#newpassword").blur(function(){
//		var newPassword=$("#newpassword").val();
//		if(newPassword){
//			if(newPassword.length<8||newPassword.length>20){
//				set_message("auto-close-dialogBox","提示","密码长度为8到20位");
//				$("#newpassword").focus();
//			}
//		}else{
//			set_message("auto-close-dialogBox","提示","请输入新密码");
//			$("#newpassword").focus();
//		}
//	})
//	
//	$("#checkePassword").blur(function(){
//		var newPassword=$("#newpassword").val();
//		var check=$("#checkePassword").val();
//		if(newPassword!=check){
//			set_message("auto-close-dialogBox","提示","2次输入的密码不一致");
//			return;
//		}
//	})
	
	$("#passwordConfirm").click(function(){
		var password = $("#oldpassword").val();
		var newPassword=$("#newpassword").val();
		var check=$("#checkePassword").val();
		if(password){
			if(password.length<8||password.length>20){
				set_message("auto-close-dialogBox","提示","密码长度为8到20位");
				return;
			}
		}else{
			set_message("auto-close-dialogBox","提示","请输入原始密码");
			return;
		}
		if(newPassword){
			if(newPassword.length<8||newPassword.length>20){
				set_message("auto-close-dialogBox","提示","密码长度为8到20位");
				return;
			}else {
				if(newPassword!=check){
					set_message("auto-close-dialogBox","提示","2次输入的密码不一致");
					return;
				}
			}
		}else{
			set_message("auto-close-dialogBox","提示","请输入新密码");
			return;
		}
		var params={
				oldPassword:password,
				newPassword:newPassword
		}
		ajaxPostFunctionAsytrue("./user/updatePwd", params, function(data){
			if(data.code==0){
				set_message("auto-close-dialogBox","提示",data.data);
				$("#modal-form").click();
			}else{
				set_message("auto-close-dialogBox","提示",data.data);
			}
		})
	});
})