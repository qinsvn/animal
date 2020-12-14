var flag_kuno=true;
$(function(){
	
	$("#signin").bind("click",function(){
		var aNumber=$("#aNumber").val();
		var apassword=$("#apassword").val();
		if(aNumber==""){
			 set_message("auto-close-dialogBox","提示","请输入账号!");
             return false;
		}
		if(apassword==""){
			 set_message("auto-close-dialogBox","提示","请输入密码!");
            return false;
		}
		$(".loadershade").css("display","block");
		var params={
			'account':aNumber,
			'password':hex_md5(aNumber+apassword),
			'backurl':getUrlParam('backurl')
		}
		if(flag_kuno){
			flag_kuno=false;
			ajaxPostFunctionAsytrue("../sso/login",params,function(data){
				if(data.code=="0"){
					setTimeout(function(){
						window.location.href=data.data;
						flag_kuno=true;
					},800)					
				}else{
					$(".loadershade").css("display","none");
					 set_message("auto-close-dialogBox","提示",data.data);
					 flag_kuno=true;
				}
			})
			//window.location.href="index.html"
		}
		
	})
	
	$('#apassword').keyup(function(event){  
	    if(event.keyCode ==13){  
	      $('#signin').trigger("click");
	    }  
	}); 
})