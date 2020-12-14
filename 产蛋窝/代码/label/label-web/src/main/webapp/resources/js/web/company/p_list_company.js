var flag_kuno=true;
/**
 * 删除的id数组
 */
var delIds = [];
$(function(){
	itemPage=10;
    initPage();
 
	//加载数据
    pageIndex = 1;
	loadData(pageIndex);
	//取消
	$("#cancel").click(function(){
		defind_text("auto-close-dialogBox","提示","确定放弃本次编辑吗？",hideModal,__cancel);
		
	})
	//编辑保存
	$("#save").click(function(){
		save($("#basePath").val()+"/web/company/update");
	})
	//执行创建
	$("#create").click(function(){
		save($("#basePath").val()+"/web/company/create");
	})
	//创建窗口
	$('#create_company').click(function(){
		$("#headTitleEdit").html("新增机构");
		$(".span_pwd").removeClass("hidden");
		createWindow();
	})
	//编辑密码
	$('#manager_pwd').focus(function(){
		$("#manager_pwd").removeAttr("type");
	})
	
	//删除
	$("#del_company").click(function(){
		delIds = [];
		$(".company_sel:checkbox:checked").each(function(){
			delIds.push(parseInt($(this).val()));
		})
		if(delIds.length == 0){
			set_message("auto-close-dialogBox","提示","请选择需要删除的机构");
			return;
		}
		defind_text("auto-close-dialogBox","提示","确定删除选中的机构吗？",deleteCompanys,'');
		
	})
	//导出
	$("#export_company").click(function(){
		exportExcel();
	})
	//查询
	$("#seach_btn").click(function(){
		loadData(1);
	});
	// 上传文件
	$("#filestyle-0").change(function(e) {
		var files = e.target.files;
		if(files==-1||files.length==0){
			set_message("auto-close-dialogBox","提示","请选择文件！");
		}else{
			uploadFile(files)
		}
	})
		//add by jolley 解除用户关系
//    $('#relieveRelat').on('click',function(){
//    	var comIds = '';
//    	$('#datas .checkbox').find('input[type=checkbox]:checked').each(function(){
//    		comIds =comIds+',' +$(this).attr('value');
//    	});
//    	if(comIds!=''){
//    		defind_text("auto-close-dialogBox","提示","确认要解除机构下的用户吗？",function(){
//				comIds = comIds.substring(1);
//	    		ajaxPostFunctionAsytrue($('#basePath').val()+'/web/customer/relieveRelatByComIds',{comIds:comIds},function(data) {
//	    			if(data.code=='0'){
//	    				set_message("auto-close-dialogBox","提示",'成功解除'+data.data+'个用户！');
//	    				loadData(1);
//	    			}else{
//	    				set_message("auto-close-dialogBox","错误提示",data.message);
//	    			}
//	    		});
//			}, __cancel);
//    	}else{
//    		set_message("auto-close-dialogBox","提示",'请选择一个机构!');
//    		return ;
//    	}
//    });
    //end by jolley 
	
	//绑定校验
//	companyCodeCheckById("edit_code",4);//机构代码
//	managerAccountCheckById("manager_account",8);//管理员账号
//	managerPasswordCheckById("manager_pwd",20);//管理员密码
//	checkLengthById("edit_name","机构名称",20);//机构名称
//	checkLengthById("edit_domain","行业",200);//行业
//	checkPhoneById("edit_phone");//电话
//	checkEmailById("edit_email");//邮箱
//	checkFaxById("edit_fax");//传真
//	checkLengthById("edit_address","地址",200);//地址
})

/**
 * 加载数据方法
 */
function loadData(pageNo){
	var aurl = $("#basePath").val() + "/web/company/list";
	var paramet = {
			page:pageNo,
			rows:itemPage,
			condition:{
				fdName:$('#seach_name').val(),
				fdType:$('#seach_type').val()
			}
	}
	$(".loadrtkuno").css("display","block")//加载动画
	ajaxPostFunctionAsytrue(aurl,JSON.stringify(paramet),loadCallBack,null,"application/json",null);
}

/**
 * 回调函数
 * @param data
 */
function loadCallBack(data){
	if(data.code == 0){
		
		itemCount = parseInt(data.data.total);
        updatePage(data.data.total);//更新总条数
        selectPage(pageIndex);//当前总条数
		
		var rows = data.data.rows;
		var blog = '<thead><tr>'
                    +'<th style="width:20px;"><label class="checkbox m-n i-checks"><input type="checkbox"><i></i></label></th>'
                    +'<th class="th-sortable hidden" data-toggle="class">id</th>'
                    +'<th>机构编号</th>'
                    +'<th>机构名称</th>'
                    +'<th>机构类型</th>'
                    +'<th>领域</th>'
                    +'<th>电话</th>'
                    +'<th>状态</th>'
                    +'<th width="160">操作</th>'
                    +'</tr></thead>';
		for(var i = 0; i < rows.length; i++){
			var obj = rows[i];
			if(obj.fdStatus == 0){//启用状态不给选择删除
				blog += '<tr>'
                    +'<td><label class="checkbox m-n i-checks"><input type="checkbox" disabled="disabled" class="company_sel" name="usman" value="'+obj.id+'"><i></i></label>'
                    +'</td>'
                    +'<td class="hidden cid">'+ obj.id +'</td>'
                    +'<td>'+ obj.fdCode +'</td>'
                    +'<td>'+ obj.fdName +'</td>'
                    +'<td data-fdType="'+obj.fdType+'">'+ formatterfdType(obj.fdType) +'</td>'
                    +'<td>'+ obj.fdDomain +'</td>'
                    +'<td>'+ obj.fdPhone +'</td>'
                    +'<td data-fdStatus="'+obj.fdStatus+'">'+ formatterStatus(obj.fdStatus) +'</td>'
                    +'<td>'
                    +'<a class="colork" data-toggle="modal" onclick="edit(this)" >编辑</a>'
                    +'</td>'
                    +'</tr>'
                    continue;
			}
			blog += '<tr>'
                    +'<td><label class="checkbox m-n i-checks"><input type="checkbox" class="company_sel" name="usman" value="'+obj.id+'"><i></i></label>'
                    +'</td>'
                    +'<td class="hidden cid">'+ obj.id +'</td>'
                    +'<td>'+ obj.fdCode +'</td>'
                    +'<td>'+ obj.fdName +'</td>'
                    +'<td data-fdType="'+obj.fdType+'">'+ formatterfdType(obj.fdType) +'</td>'
                    +'<td>'+ obj.fdDomain +'</td>'
                    +'<td>'+ obj.fdPhone +'</td>'
                    +'<td data-fdStatus="'+obj.fdStatus+'">'+ formatterStatus(obj.fdStatus) +'</td>'
                    +'<td>'
                    +'<a class="colork" data-toggle="modal" onclick="edit(this)" >编辑</a>'
                    +'</td>'
                    +'</tr>'
		}
		$("#datas").html("");
		$("#datas").append(blog);
		
		setTimeout(function (){
            $(".loadrtkuno").css("display","none")//隐藏加载动画
        },500)
	}else{
		set_message("auto-close-dialogBox","提示","无相关数据");
	}
}

/**
 * 翻页回调
 * @param pageNo
 */
function getListByPage(pageNo){
	loadData(pageNo);
	pageIndex = pageNo;
}
/**
 * 打开编辑页面
 * @param e
 */
function edit(e){
	$('.fd_btn').removeClass("hidden");
	$('#create').addClass("hidden");
	var cid = $(e).parent().parent().find("td:nth-child(2)").html();
	loadCompanyById(cid);
	
	$('#pwd_box').removeClass("hidden");
	$("#pwd_check").prop("checked", false);
	$(".span_pwd").addClass("hidden");
	$('#modal-formedit').modal('show');
	
	
}

function hideModal(){
	$('#modal-formedit').modal('hide');
}
/**
 * 获取编辑页面数据
 * @param cid
 */
function loadCompanyById(cid){
	var paramet = {
			cid : cid
	}
	var aurl = $("#basePath").val() + "/web/company/info";
	$(".loadrtkuno").css("display","block")//加载动画
	ajaxPostFunctionAsytrue(aurl,JSON.stringify(paramet),function(data){
		if(data.code == 0){
			//设置机构信息
			var obj = data.data.data;
			$('#edit_cid').val(obj.id);
			$('#edit_name').val(obj.fdName);
			$('#typeselect').val(obj.fdType);
			$('#edit_domain').val(obj.fdDomain);
			$('#edit_phone').val(obj.fdPhone);
			$('#edit_email').val(obj.fdEmail);
			$('#edit_fax').val(obj.fdFax);
			$('#comboselect').val(obj.fdStatus);
			
			//设置用户信息
//			var user = data.data.user;
//			$('#uid').val(user.id);
//			$('#manager_account').val(user.fdAccount);
//			$('#manager_pwd').val("");
//			if(user.fdAccount === ""){
//				$('#manager_pwd').val("");
//			}
//			$('#manager_pwd').attr("type","password");
//			$('#pwd_check').attr("checked",false); 
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
		$("#headTitleEdit").html("编辑机构");
		$(".loadrtkuno").css("display","none")//加载动画
	},null,"application/json",null);
}

/**
 * 保存编辑
 * @param aurl
 * @param type create edit
 */
function save(aurl){
	try {
		var name = $('#edit_name').val();
		var type=$("#typeselect").val();
		var phone = $('#edit_phone').val();
		var email = $('#edit_email').val();
		var fax = $('#edit_fax').val();
//		var account = $('#manager_account').val();
//		var pwd = $('#manager_pwd').val();
		var domain = $("#edit_domain").val();
		var status=$("#comboselect").val();

//		if(account.indexOf(" ") != -1){
//			set_message("auto-close-dialogBox", "提示", "管理员账号不能存在空格！");
//			return;
//		}
//		var pattern = /^[0-9a-zA-Z]+$/;
//		if(!pattern.test(account)){
//			set_message("auto-close-dialogBox", "提示", "管理员账号不合法，请修改！例：zhangsan01");
//			return
//		}
//		if ($.trim(account).length < 8) {
//			set_message("auto-close-dialogBox", "提示", "管理员账号不少于 8 位！");
//			return;
//		}
//		if ($("#pwd_check").is(':checked')) {
//			var pattern = /^[0-9a-zA-Z]+$/;
//			if(!pattern.test(pwd)){
//				set_message("auto-close-dialogBox", "提示", "管理员密码不合法，管理员密码只能输入数字或者大小写字母！");
//				return
//			}
//			if($.trim(pwd).length <8 || $.trim(pwd).length >20){
//				set_message("auto-close-dialogBox", "提示", "管理员密码长度必须在8~20位之间！");
//				return
//			}
//		}
		if(!checkCZN("edit_name","机构名称",20)){
			return;
		}
		if($.trim(domain) != ""){
			var domainreg = /^[a-zA-Z_0-9\-\u4E00-\u9FA5]+$/;
			if(!domainreg.test($.trim(domain))){
				set_message("auto-close-dialogBox",'提示',"领域只能输入中英文与数字");
				return;
			}
		}
		if($.trim(phone) == ""){
//			set_message("auto-close-dialogBox", "提示", "电话不能为空");
//			return;
		}else{
			var pattern = '';
			if(phone.indexOf("-") > 0){//电话号码
				pattern = /^(\d{3,4}-)?\d{7,8}$/;
			}else{//手机号码
				pattern = /^1[34578]\d{9}$/;
			}
			if(!pattern.test(phone)){
				set_message("auto-close-dialogBox","提示","号码不合法，请修改！例：电话号码 xxx-xxxxxxx, 手机号11位手机号");
				return;
			}
		}
		if($.trim(email) !="" || email != ""){
			var pattern =  /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
			if(!pattern.test(email)){
				set_message("auto-close-dialogBox","提示","邮箱不合法，请修改！例：xxx@xxx.xxx");
				return;
			}
		}
		if($.trim(fax) !="" || fax != ""){
			var pattern = /^(\d{3,4}-)?\d{7,8}$/;
			if(!pattern.test(fax)){
				set_message("auto-close-dialogBox","提示","传真不合法，请修改！例：xxx-xxxxxxx");
				return;
			}
		}

		
		//密码没勾选就清空
		if (!$("#pwd_check").is(':checked')) {
			pwd = '';
		}
		var paramet = {
			company : {
				id : $.trim($('#edit_cid').val()),
				fdType : type,
				fdName : name,
				fdDomain : $.trim($('#edit_domain').val()),
				fdPhone : $.trim(phone),
				fdEmail : $.trim(email),
				fdFax : $.trim(fax),
				fdStatus : status
			},
//			user : {
//				id : $('#uid').val(),
//				fdAccount : account,
//				fdPassword : pwd
//			}
		}

		$(".loadrtkuno").css("display", "block")//加载动画
		if(flag_kuno){
			flag_kuno=false;
			ajaxPostFunctionAsytrue(aurl, JSON.stringify(paramet), function(data) {
				if (data.code == -110){
					set_message("auto-close-dialogBox", "提示", data.message);
				}else{
					if (data.code == 0) {
						hideModal();
						loadData(1);
						pageIndex = 1;
						flag_kuno=true;
						
					} else {
						set_message("auto-close-dialogBox", "提示", data.data.message);
						flag_kuno=true;
					}
				}
					
				
			}, null, "application/json", null);
		}
		
	} catch (e) {
		set_message("auto-close-dialogBox", "提示", "失败");
	}
}
/**
 * 创建机构
 * @param e
 */
function createWindow(){
	
	$('#manager_account').val("111111111111");
	$('#manager_pwd').val("");
	$('#edit_cid').val("");
	$('#edit_name').val("");
	$('#edit_domain').val("");
	$('#edit_phone').val("");
	$('#edit_email').val("");
	$('#edit_fax').val("");
	$('.fd_btn').removeClass("hidden");
	$('#save').addClass("hidden");
	$('#pwd_box').addClass("hidden");
	$('#pwd_check').prop("checked", true);
	$('#uid').val("")
	$('#modal-formedit').modal('show');
}
/**
 * 机构类型转换
 * @param satatus
 */
function formatterfdType(type){
	if (type == 1){
		return "集团机构";
	}
	if (type == 2){
		return "内部";
	}
	return "";
}
/**
 * 状态转换
 * @param satatus
 */
function formatterStatus(satatus){
	if (satatus == 0){
		return "启用";
	}
	if (satatus == 1){
		return "冻结";
	}
	return "";
}
/**
 * 删除机构
 * @param ids
 */
function deleteCompanys(){
	try {
		var parameter = {
			list : delIds
		}

		var aurl = $("#basePath").val() + "/web/company/deleteCompanys";
		$(".loadrtkuno").css("display", "block")//加载动画
		ajaxPostFunctionAsytrue(aurl, JSON.stringify(parameter),
				function(data) {
					if (data.code == 0) {
						loadData(1);
						pageIndex = 1;
						set_message("auto-close-dialogBox", "提示", "删除成功");
					} else {
						set_message("auto-close-dialogBox", "提示", data.data);
					}
					$(".loadrtkuno").css("display", "none")//加载动画
				}, null, "application/json", null);
	} catch (e) {
		set_message("auto-close-dialogBox", "提示", "删除失败");
	}
}
/**
 * 导出excel
 */
function exportExcel(pageNo){
	var aurl = $("#basePath").val() + "/web/company/listexport"
	var paramet = {
			page:pageNo,
			rows:itemPage,
			conditiong:{
				fdName:$('#seach_name').val()
			}
	}
	$(".loadrtkuno").css("display","block")//加载动画
	ajaxPostFunctionAsytrue(aurl,JSON.stringify(paramet),function(data){
		if(data.code == 0){
			window.location.href = $("#basePath").val() + "/common/export/getFile?fileId="+data.data.fileId;
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
		$(".loadrtkuno").css("display","none")//加载动画
	},null,"application/json",null);
}

/**
 * 文件上传
 * @param files
 */
function uploadFile(files){
	var file = files[0];
	var xhr = new XMLHttpRequest();
	if(xhr.upload){
		xhr.onreadystatechange = function(e) {
			if (xhr.readyState == 4) {
				if (xhr.status == 200) {
					onSuccess(file, xhr.responseText);
				}else{
					onFailure(file, xhr.responseText);
				}
			}
		};
		// 开始上传
		xhr.open("POST",$("#basePath").val() + "/common/upload/create",true);
		//模拟创建表单提交
		var fdata=new FormData();
		fdata.append('file',file);
		fdata.append('type',0);
		xhr.send(fdata);
	}
}
/**
 * 文件上传成功后操作
 * @param file——上传文件
 * @param response——后台回调信息
 */
function onSuccess(file,response)
{	
	try
	{
		eval('var json='+response);
		if(json.code==0)
		{
			var data = json.data;
			var params={
					filekey : data.filekey,
					url : ""
			}
			//获取url 和 机构id调用接口
			ajaxPostFunctionAsytrue($("#basePath").val() + '/web/company/inserts',JSON.stringify(params),function(data){
				if(data.code==0){
					set_message("auto-close-dialogBox","提示","机构信息批量导入，成功 " + data.data.rows + " 行，失败 " + data.data.errRows + " 行");
					loadData(1);
				}else{
					set_message("auto-close-dialogBox","提示","机构导入失败！");
				}
			},null,"application/json",null)
		} 
	}
	catch(e)
	{
		set_message("auto-close-dialogBox","提示","文件上传失败");
	}
}
/**
 * 文件上传失败回调
 * @param file
 * @param response
 */
function onFailure(file, response)
{
	set_message("auto-close-dialogBox","提示","文件上传失败");
}
