var flag_kuno=true;
/**
 *	监控字符串输入
 * @param id
 */
function textLimitByElementId_ZH(id,textLength){
	var pattern = /^[\u4e00-\u9fa5]*$/;
	$("#"+id).blur(function(){
		if(!pattern.test($(this).val())){
			$(this).val('');
			$.messager.alert("提示","任务名称不能为空且必须为中文");
		}
	});
	$("#"+id).bind('input propertychange',function(){
		if($(this).val().length > textLength){
			//$.messager.alert("提示","任务名称长度不能大于"+textLength);
			$(this).val($(this).val().substring(0,textLength));
		}
	});
}

/**
 * 说明监控
 * @param id
 * @param textLength
 */
function descTextLimitByElementId(id,textLength){
	$("#"+id).bind('input propertychange',function(){
		if($(this).val().length > textLength){
			//$.messager.alert("提示","任务说明长度不能大于"+textLength);
			$(this).val($(this).val().substring(0,textLength));
		}
	});
}

/**
 * 机构代码校验
 * @param id
 * @param textLength
 */
function companyCodeCheckById(id,textLength){
	$("#"+id).bind('input propertychange',function(){
		companyCodeInputCheck(id,textLength);
	});
}
function companyCodeCheck(id,textLength){
	var val = $("#"+id).val();
	if(val.length == 0){
		set_message("auto-close-dialogBox","提示","机构代码不能为空");
		return false;
	}
	var pattern = /^[0-9a-zA-Z]+$/;
	if(!pattern.test(val)){
		$("#"+id).val('');
		set_message("auto-close-dialogBox","提示","机构代码不合法，机构代码只能输入数字或者大小写字母");
		return false;
	}
	if(val.length > textLength){
		set_message("auto-close-dialogBox","提示","机构代码长度不能大于 " + textLength);//提示框 
		$("#"+id).val(val.substring(0,textLength));
		return false;
	}
	return true;
}
function companyCodeInputCheck(id,textLength){
	var val = $("#"+id).val();
//	var pattern = /^[0-9a-zA-Z]+$/;
	var pattern = /^[A-Z0-9]+$/;
	if(val.length == 0){
		return false
	}
	if(!pattern.test(val)){
		$("#"+id).val('');
		set_message("auto-close-dialogBox","提示","机构编号不合法，机构编号只能输入大写字母或者数字");
		return false;
	}
	if(val.length > textLength){
		set_message("auto-close-dialogBox","提示","机构编号长度不能大于 " + textLength);//提示框 
		$("#"+id).val(val.substring(0,textLength));
		return false;
	}
	return true;
}
/**
 * 通用长度校验
 * @param id
 * @param tips
 * @param textLength
 */
function checkLengthById(id,tips,textLength){
	$("#"+id).bind('input propertychange',function(){
		checkInputLength(id,tips,textLength);
	});
}
/**
 * 验证长度不允许为空
 * @param id
 * @param tips
 * @param textLength
 * @returns {Boolean}
 */
function checkLength(id,tips,textLength){
	var val = $("#"+id).val();
	if($.trim(val).length == 0){
		set_message("auto-close-dialogBox","提示",tips + "不能为空");
		return false
	}
	if(val.length > textLength){
		set_message("auto-close-dialogBox","提示",tips + "长度不能大于" + textLength);
		//$("#"+id).val($("#"+id).val().substring(0,textLength));
		return false;
	}
	return true;
}
function checkInputLength(id,tips,textLength){
	var val = $("#"+id).val();
	if(val.length == 0){
		return false
	}
	if(val.length > textLength){
		set_message("auto-close-dialogBox","提示",tips + "长度不能大于" + textLength);
		$("#"+id).val($("#"+id).val().substring(0,textLength));
		return false;
	}
	return true;
}
/**
 * 验证长度允许为空
 * @param id
 * @param tips
 * @param textLength
 * @returns {Boolean}
 */
function checkLengthIssuBlack(id,tips,textLength){
	var val = $("#"+id).val();
	if(val.length > textLength){
		set_message("auto-close-dialogBox","提示",tips + "长度不能大于" + textLength);
		//$("#"+id).val($("#"+id).val().substring(0,textLength));
		return false;
	}
	return true;
}

/**
 * 管理员密码校验
 * @param id
 * @param textLength
 */
function managerPasswordCheckById(id,textLength){
	$("#"+id).blur(function(){
		managerPasswordInputCheck(id,textLength);
	});
}
function managerPasswordCheck(id,textLength){
	var val = $("#"+id).val();
	if(val.length == 0){
		set_message("auto-close-dialogBox","提示","管理员密码不能为空 ");//提示框 
		return false;
	}
	var pattern = /^[0-9a-zA-Z]+$/;
	if(!pattern.test(val)){
		$("#"+id).val('');
		set_message("auto-close-dialogBox","提示","管理员密码不合法，管理员密码只能输入数字或者大小写字母");
		return false;
	}
	return true;
}
function managerPasswordInputCheck(id,textLength){
	var val = $("#"+id).val();
	if(val.length == 0){
		return false;
	}
	var pattern = /^[0-9a-zA-Z]+$/;
	if(!pattern.test(val)){
		$("#"+id).val('');
		$('#'+id).focus();
		set_message("auto-close-dialogBox","提示","管理员密码不合法，管理员密码只能输入数字或者大小写字母");
		return false;
	}
	if(val.length > textLength || val.length < 8){
		set_message("auto-close-dialogBox","提示","管理员密码长度必须在8 ~" +textLength + "之间");
		$('#'+id).focus();
		return false;
	}
	return true;
}
function managerPwdLengthCheck(id,minL,maxL){
	var val = $("#"+id).val();
	if(val == 0){
		return false;
	}
	var pattern = /^[0-9a-zA-Z]{8,20}$/;
	if(!pattern.test(val)){
		$("#"+id).val('');
		set_message("auto-close-dialogBox","提示","管理员密码不合法，管理员密码只能输入数字或者大小写字母");
		return false;
	}
	if(val.length > maxL || val.length < minL){
		set_message("auto-close-dialogBox","提示","管理员密码长度必须在 " + minL + " ~ " + maxL + " 位之间");//提示框 
		return false;
	}
	return true;
}
/**
 * pwd 长度检查
 * @param id
 * @param minL
 * @param maxL
 */
function checkPwdLength(id,minL,maxL){
	var val = $("#"+id).val();
	if(val.length > maxL || val.length < minL){
		set_message("auto-close-dialogBox","提示","管理员密码长度必须在 " + minL + " ~ " + maxL + " 位之间");//提示框 
		return false;
	}
	return true;
}

/**
 * 手机电话校验
 * @param id
 * @param textLength
 */
function checkPhoneById(id){
	var pattern = "";
	$("#"+id).blur(function(){
		checkPhone(id);
	});
}
function checkPhone(id){
	var val = $('#'+id).val();
//	if(val == ''){
//		return true;
//	}
	var pattern = '';
	if(val.indexOf("-") > 0){//电话号码
		pattern = /^(\d{3,4}-)?\d{7,8}$/;
	}else{//手机号码
		pattern = /^1[34578]\d{9}$/;
	}
	if(!pattern.test(val)){
		set_message("auto-close-dialogBox","提示","号码不合法，请修改！例：电话号码 xxx-xxxxxxx, 手机号11位手机号");
		$('#'+id).val('');
		$("#"+id).focus();
		return false;
	}
	return true;
}

/**
 * 邮箱校验
 * @param id
 */
function checkEmailById(id){
	$("#"+id).blur(function(){
		checkEmail(id);
	});
}
function checkEmail(id){
	var val = $('#'+id).val();
//	if(val == ''){
//		return true;
//	}
	var pattern =  /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	if(!pattern.test(val)){
		set_message("auto-close-dialogBox","提示","邮箱不合法，请修改！例：xxx@xxx.xxx");
		$('#'+id).focus();
		return false;
	}
	return true;
}

/**
 * 传真
 * @param id
 */
function checkFaxById(id){
	$("#"+id).blur(function(){
		checkFax(id);
	});
}
function checkFax(id){
	var val = $('#'+id).val();
//	if(val == ''){
//		return true;
//	}
	var pattern = /^(\d{3,4}-)?\d{7,8}$/;
	if(!pattern.test(val)){
		set_message("auto-close-dialogBox","提示","传真不合法，请修改！例：xxx-xxxxxxx");
		$('#'+id).focus();
		return false;
	}
	return true;
}

/**
 * 校验URL
 * @param id
 * @param tips
 * @returns {Boolean}
 */
function checkUrl(id,tips){
	var val = $('#'+id).val();
	if(val == ''){
		set_message("auto-close-dialogBox","提示",tips + "不能为空！！！");
		return false;
	}
	var pattern=/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)/;
	if(!pattern.test(val)){
		set_message("auto-close-dialogBox","提示",tips + "不合法，请修改！！！");
		return false;
	}
	if(val.length>255){
		set_message("auto-close-dialogBox","提示",tips + "链接长度超过限制");
		return false;
	}
	return true
}

/**
 * 判断是否为空
 * @param id
 * @param tips
 * @returns {Boolean}
 */
function isBlank(id,tips){
	var val = $('#'+id).val();
	if(val == ''){
		set_message("auto-close-dialogBox","提示",tips + "不能为空！！！");
		return false;
	}
	return true;
}


/**
 * 管理员账号校验
 * @param id
 * @param textLength
 */
function managerAccountCheckById(id,textLength){
	$("#"+id).blur(function(){
		managerAccountInputCheck(id,textLength);
	});
}
function managerAccountInputCheck(id,textLength){
	var val = $("#"+id).val();
	var pattern = /^[a-zA-Z0-9]*$/;
	if(val.length == 0){
		return false
	}
	if(!pattern.test(val)){
		$("#"+id).val('');
		$("#"+id).focus();
		set_message("auto-close-dialogBox","提示","管理员账号不合法，管理员账号只能输入汉字拼音全拼加数字");
		return false;
	}
	if(val.length < textLength){
		set_message("auto-close-dialogBox","提示","管理员账号长度不能小于 " + textLength);//提示框 
		$("#"+id).val(val.substring(0,textLength));
		$("#"+id).focus();
		return false;
	}
	return true;
}

/**
 * 银行卡校验
 * @param bankno 银行卡号
 */
function luhnCheck(bankno){
	var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhn进行比较）

	var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
	var newArr=new Array();
	for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
		newArr.push(first15Num.substr(i,1));
	}
	var arrJiShu=new Array();  //奇数位*2的积 <9
	var arrJiShu2=new Array(); //奇数位*2的积 >9

	var arrOuShu=new Array();  //偶数位数组
	for(var j=0;j<newArr.length;j++){
		if((j+1)%2==1){//奇数位
			if(parseInt(newArr[j])*2<9)
				arrJiShu.push(parseInt(newArr[j])*2);
			else
				arrJiShu2.push(parseInt(newArr[j])*2);
		}
		else //偶数位
			arrOuShu.push(newArr[j]);
	}

	var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
	var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
	for(var h=0;h<arrJiShu2.length;h++){
		jishu_child1.push(parseInt(arrJiShu2[h])%10);
		jishu_child2.push(parseInt(arrJiShu2[h])/10);
	}

	var sumJiShu=0; //奇数位*2 < 9 的数组之和
	var sumOuShu=0; //偶数位数组之和
	var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
	var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
	var sumTotal=0;
	for(var m=0;m<arrJiShu.length;m++){
		sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
	}

	for(var n=0;n<arrOuShu.length;n++){
		sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
	}

	for(var p=0;p<jishu_child1.length;p++){
		sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
		sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
	}
	//计算总和
	sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);

	//计算luhn值
	var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;
	var luhn= 10-k;

	if(lastNum==luhn){
		console.log("验证通过");
		return true;
	}else{
		set_message("auto-close-dialogBox","提示","银行卡号不正确 ");
		return false;
	}
}
//检查银行卡号
function CheckBankNo(bankno) {
	var bankno = bankno.replace(/\s/g,'');
	if(bankno == "") {
		set_message("auto-close-dialogBox","提示","请填写银行卡号 ");
		return false;
	}
	if(bankno.length < 16 || bankno.length > 19) {
		set_message("auto-close-dialogBox","提示","银行卡号长度必须在16到19之间 ");
		return false;
	}
	var num = /^\d*$/;//全数字
	if(!num.exec(bankno)) {
		set_message("auto-close-dialogBox","提示","银行卡号必须全为数字 ");
		return false;
	}
	//开头6位
	var strBin = "10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
	if(strBin.indexOf(bankno.substring(0, 2)) == -1) {
		set_message("auto-close-dialogBox","提示","银行卡号开头6位不符合规范 ");
		return false;
	}
	//Luhn校验
	if(!luhnCheck(bankno)){
		return false;
	}
	return true;
}

/**
 *  空格校验，中英文数字-_(用户名)校验
 * @param id
 * @param tips
 */
function trimCheckId(id,tips){
	$("#"+id).blur(function(){
		trimCheck(id,tips);
	})
}
function trimCheck(id,tips){
	var str = $("#"+id).val();
	var regex = /^[A-Za-z0-9_\-\u4e00-\u9fa5]+$/;
	if(!regex.test(str)){
		set_message("auto-close-dialogBox",'提示',tips + '可能包含<>/特殊符号或空格，请修改重试');
		$('#'+id).focus();
		return false;
	}
	return true;
}

/**
 *  身份证校验
 * @param id
 */
function isCardNo(card) { 
	 var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
	 return pattern.test(card); 
} 

/**
 * （中英文）账号类型校验 长度校验
 * @param id
 * @param tips
 * @param textLength
 * @returns {Boolean}
 */
function checkCZ(id,tips,textLength){
	var val = $("#"+id).val();
	val = $.trim(val);
	if(val.length == 0){
		set_message("auto-close-dialogBox","提示",tips + "不能为空");
		return false
	}
	if(val.length > textLength){
		set_message("auto-close-dialogBox","提示",tips + "长度不能大于" + textLength);
		//$("#"+id).val($("#"+id).val().substring(0,textLength));
		return false;
	}
	var regex = /^[a-zA-Z\·\u4E00-\u9FA5]+$/;
	if(!regex.test(val)){
		set_message("auto-close-dialogBox","提示",tips + "只能输入中英文，请修改重试");
		return false;
	}
	return true;
}

/**
 * （中英文+数字）类型校验 长度校验
 * @param id
 * @param tips
 * @param textLength
 * @returns {Boolean}
 */
function checkCZN(id,tips,textLength){
	var val = $("#"+id).val();
	val = $.trim(val);
	if(val.length == 0){
		set_message("auto-close-dialogBox","提示",tips + "不能为空");
		return false
	}
	if(val.length > textLength){
		set_message("auto-close-dialogBox","提示",tips + "长度不能大于" + textLength);
		//$("#"+id).val($("#"+id).val().substring(0,textLength));
		return false;
	}
	var regex = /^[a-zA-Z0-9\u4E00-\u9FA5]+$/;
	if(!regex.test(val)){
		set_message("auto-close-dialogBox","提示",tips + "只能输入中英文与数字，请修改重试");
		return false;
	}
	return true;
}

/**
 * （英文+数字）类型校验 长度校验
 * @param id
 * @param tips
 * @param textLength
 * @returns {Boolean}
 */
function checkZN(id,tips,textLength){
	var val = $("#"+id).val();
	val = $.trim(val);
	if(val.length == 0){
		set_message("auto-close-dialogBox","提示",tips + "不能为空");
		return false
	}
	if(val.length > textLength){
		set_message("auto-close-dialogBox","提示",tips + "长度不能大于" + textLength);
		//$("#"+id).val($("#"+id).val().substring(0,textLength));
		return false;
	}
	var regex = /^[a-zA-Z0-9]+$/;
	if(!regex.test(val)){
		set_message("auto-close-dialogBox","提示",tips + "只能输入英文与数字，请修改重试");
		return false;
	}
	return true;
}

/**
 * 字符串空格校验
 * @param id    校验的对象id
 * @param tips  校验对象的名称
 */
function checkTrim(id,tips){
	var val = $("#" +id).val();
	if(val.inddexOf(" ") != "-1"){
		set_message("auto-close-dialogBox","提示",tips +"不能存在空格");
		return false;
	}
	return true;
}

/**
 * 通用简介校验，支持中英文标点符号输入
 * @param id   校验对象id
 * @param tips 校验对象的名称
 */
function checkSymbol(id,tips,textLength){
	var val = $("#"+id).val();
	if(val.length == 0){
		set_message("auto-close-dialogBox","提示",tips + "不能为空");
		return false
	}
	if(val.length > textLength){
		set_message("auto-close-dialogBox","提示",tips + "长度不能大于" + textLength);
		//$("#"+id).val($("#"+id).val().substring(0,textLength));
		return false;
	}
	var regex = /^[a-zA-Z0-9\u4e00-\u9fa5，。！、……《》（）【】：；“‘”’？￥,\.\?!:;<>'"\(\)]+$/;
	if(!regex.test(val)){
		set_message("auto-close-dialogBox","提示",tips + "输入不合法，请修改重试");
		return false;
	}
	return true; 
}

