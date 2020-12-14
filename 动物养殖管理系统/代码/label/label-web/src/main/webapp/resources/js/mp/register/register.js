var flag=true;
var djsnum=60;
var myinter;
$(function() {
	heightreset();
	Is_getImg = 0;
	$("input").focus(function() {
		$(this).css("font-weight", "normal");
	});
})
$(window).resize(function() {
	heightreset()
});
//页面顶部图片高度
function heightreset(){
	var bodyHeight = document.documentElement.clientHeight;
	var testHeight = $(".test").outerHeight(true);
	$(".header").height(bodyHeight-testHeight)
}
//注册按钮事件
function saved(){
	var phone=$("#phone").val();
	var vercode=$("#yanzhengma").val();
	if(phone==""||vercode==""){
		mui.toast("还有内容没填写");
		return
	}
	if($("#checkbox").prop("checked")==false){
		mui.toast("请详细阅读并同意条款");
		return
	}
	var params={
		"phone":phone,
		"vercode":vercode
	}
	ajaxPostFunctionAsytrue('../../action',params,function(data){
		if(data.code == '0'){
			mui.toast("注册成功");
			setTimeout(function(){
				window.location.href="index.html"
			})
		}
	})

}
function getyzma(){
	if(flag){
		var _phone=$("#phone").val();
		if(_phone==""){
			mui.toast("请输入手机号码");
			return
		}
		var params={
			"phone":_phone
		}
		ajaxPostFunctionAsytrue('../../action',params,function(data){
			if(data.code == '0'){
				flag=false;
				mui.toast("验证码已发送");
				myinter=setInterval(daoj, 1000);
			}
		})
	}
}
function daoj(){
	if(djsnum>0){
		$("#yzma").html(djsnum+"s");
		djsnum--;
		$("#yzma").removeAttr("onclick");
		flag=false;
	}else{
		$("#yzma").html("发送验证码");
		djsnum=60;
		$("#yzma").attr("onclick","getyzma()");
		clearInterval(myinter);
		flag=true;
	}

}