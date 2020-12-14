var itemPage = -1;
var idParam =[];
var allRole=[];

var sumAll=0;
var sumApproval=0;
var sumChicken=0;
var sumAgg=0

$(function(){	
	activeTime('startCreateTime', 'endCreateTime');
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
	 * 删除按钮事件
	 */
	$("#deleteDeviceButt").click(function(){
		idParam.splice(0,idParam.length);
		$('input[name="usman"]:checked').each(function(){
			idParam.push(parseInt($(this).val()));
		});
		if(idParam.length>0){
			//提示框
			defind_text("auto-close-dialogBox","提示","是否删除？",doDelete, __cancel);
		}else{
			set_message("auto-close-dialogBox","提示","请选择次品");
		}
	});
	
	/**
	 * 删除按钮事件
	 */
	$("#apDeviceButt").click(function(){
		idParam.splice(0,idParam.length);
		$('input[name="usman"]:checked').each(function(){
			idParam.push(parseInt($(this).val()));
		});
		if(idParam.length>0){
			//提示框
			defind_text("auto-close-dialogBox","提示","确定审批通过？",doApproval, __cancel);
		}else{
			set_message("auto-close-dialogBox","提示","请选择次品进行审批！");
		}
	});
	
	$("#export").click(function() {
		doDown();
	});
});

/**
 * 分页获取数据
 * @param page 起始页码
 * @param name 产品名称 
 */
function getListByPage(page,name){
	$(".loadrtkuno").css("display", "block");
	var dataCode = $("#dataCode").val();
	var dataTypeDes = $("#dataTypeDes").val();
	var startCreateTime = $("#startCreateTime").val();
	var endCreateTime = $("#endCreateTime").val();
	var type = $('#type').val();
	var substandardType = $('#substandardType').val();
	var params={
			page:page,
			rows:itemPage,
			condition:{ }
	};
	if(dataCode&&dataCode!=''){
		params.condition.dataCode=dataCode;
	}

	if(dataTypeDes&&dataTypeDes!=''){
		params.condition.dataTypeDes=dataTypeDes;
	}
	if(startCreateTime&&startCreateTime!=''){
		params.condition.startCreateTime=startCreateTime;
	}
	if(endCreateTime&&endCreateTime!=''){
		params.condition.endCreateTime=endCreateTime;
	}
	
	if(type&&type!=''){
		params.condition.type=type;
	}
	if(substandardType&&substandardType!=''&&substandardType!='-1'){
		params.condition.substandardType=substandardType;
	}
	
	$(".loadrtkuno").css("display", "block");
	ajaxRequest("../substandard/list",params,function(data){
		if(data.code==0){
			$("#umantable").html();//清空原有数据
			var itemCount = data.data.total;
			updatePage(itemCount);
			selectPage(page);
			var tableHtml= "";
			var rs = data.data.rows;
			 
			if(rs.length>0){
				for(var i = 0; i<rs.length; i++){
					var substandardData = rs[i];
					tableHtml += '<tr><td><label class="checkbox m-n i-checks"><input type="checkbox" name="usman" value="'
						+ substandardData.id + '"><i></i></label> </td>';
					if(substandardData.dataCode){
						tableHtml += '<td>' + substandardData.dataCode + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(substandardData.dataTypeDes){
						tableHtml += '<td>' + substandardData.dataTypeDes+ '</td>';						
					}else{
						tableHtml += '<td></td>';
					}

					if(substandardData._type){
						tableHtml += '<td>' + substandardData._type+ '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					
					if(substandardData.createUser){
						tableHtml += '<td>'+substandardData.createUser+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(substandardData.createTime){
						tableHtml += '<td>' + formatDate(substandardData.createTime)  + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}

					if(substandardData.approvalUser){
						tableHtml += '<td>'+substandardData.approvalUser+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(substandardData.approvalTime){
						tableHtml += '<td>' + formatDate(substandardData.approvalTime)  + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					
					if(substandardData.orgName){
						tableHtml += '<td>'+substandardData.orgName+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(substandardData.remark1=='0'){
						tableHtml += '<td>死亡</td>';
					}
					else if(substandardData.remark1=='1'){
						tableHtml += '<td>遗失</td>';
					}
					else if(substandardData.remark1=='2'){
						tableHtml += '<td>损坏</td>';
					}else{
						tableHtml += '<td></td>';
					}					
					if(substandardData.status==0){
						tableHtml += '<td style="color: #f90707; ">未审核</td>';
					}else	if(substandardData.status==1){
						
						tableHtml += '<td style="color: #4cb6cb;font-weight: bold; ">已审核</td>';
					}else{
						tableHtml += '<td></td>';
					}
				}
			}
			
			//获取汇总
			 sumAll=0;
			 sumApproval=0;
			 sumChicken=0;
			 sumAgg=0;
			ajaxRequest("../substandard/listSum",params.condition,function(data1){
				if (data1.data!=null&&data1.data.length>0) {
					 sumAll=data1.data[0].allsum;
					 sumApproval=data1.data[0].sumApproval;
					 sumChicken=data1.data[0].sumChicken;
					 sumAgg=data1.data[0].sumAgg;
				}
				
				$('#sumInfo').html("汇总信息-上报次品总共为："+sumAll+"件。其中，已审核次品总共为："+(sumApproval==null?0:sumApproval)+"件。已审核部分，禽类次品量为："+(sumChicken==null?0:sumChicken)+"，蛋类次品量为："+(sumAgg==null?0:sumAgg));
			});
			
			
			setTimeout(function() {
				$(".loadrtkuno").css("display", "none");// 隐藏加载动画
				$("#umantable").html(tableHtml);
			}, 500);
		}else{
			set_message("auto-close-dialogBox","提示",data.message);
		}
	});
	
}

/**
 * 删除方法
 */
function doDelete(){
	ajaxRequest('../substandard/delete', idParam, function(data) {
		if (data.code == 0) {
			set_message("auto-close-dialogBox","提示","删除成功！");
			$("#selectAll").attr('checked',false);
			getListByPage(1,$("#search").val());
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	});
}


/**
 * 审批方法
 */
function doApproval(){
	ajaxRequest('../substandard/approval', idParam, function(data) {
		if (data.code == 0) {
			set_message("auto-close-dialogBox","提示","审批成功！");
			$("#selectAll").attr('checked',false);
			getListByPage(1,$("#search").val());
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	});
}


/**
 * 导出excel
 */
function doDown() {
	var dataCode = $("#dataCode").val();
	var dataTypeDes = $("#dataTypeDes").val();
	var startCreateTime = $("#startCreateTime").val();
	var endCreateTime = $("#endCreateTime").val();
	var params={ };
	if(dataCode&&dataCode!=''){
		params.dataCode=dataCode;
	}

	if(dataTypeDes&&dataTypeDes!=''){
		params.dataTypeDes=dataTypeDes;
	}
	if(startCreateTime&&startCreateTime!=''){
		params.startCreateTime=startCreateTime;
	}
	if(endCreateTime&&endCreateTime!=''){
		params.endCreateTime=endCreateTime;
	}
	
	ajaxRequest('../substandard/export', params, function(data) {
		if(data.code == 0) {
			window.location.href = "../../common/export/getFile?fileId=" + data.data;
		} else {
			set_message("auto-close-dialogBox", "提示", data.data);

		}
	});
}

