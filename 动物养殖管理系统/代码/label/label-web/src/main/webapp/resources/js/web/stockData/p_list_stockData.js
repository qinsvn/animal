var itemPage = -1;
var idParam =[];
var allRole=[];

var sumAll=0;
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
			set_message("auto-close-dialogBox","提示","请选择蛋类");
		}
	});

	/**
	 * 加入次品按钮事件
	 */
//	$("#substandard").click(function(){
//		idParam.splice(0,idParam.length);
//		$('input[name="usman"]:checked').each(function(){
//			idParam.push(parseInt($(this).val()));
//		});
//		if(idParam.length>0){
//			//提示框
//			defind_text("auto-close-dialogBox","提示","是否加入次品？",doSubstandard, __cancel);
//		}else{
//			set_message("auto-close-dialogBox","提示","请选择次品");
//		}
//	});
	
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
	$(".loadrtkuno").css("display", "block");
	ajaxRequest("../stockData/list",params,function(data){
		if(data.code==0){
			$("#umantable").html();//清空原有数据
			var itemCount = data.data.total;
			updatePage(itemCount);
			selectPage(page);
			var tableHtml= "";
			var rs = data.data.rows;
			 sumAll=0;
			 sumChicken=0;
			 sumAgg=0;
			if(rs.length>0){
				for(var i = 0; i<rs.length; i++){
					var stockDataData = rs[i];
					tableHtml += '<tr><td><label class="checkbox m-n i-checks"><input type="checkbox" name="usman" value="'
						+ stockDataData.id + '"><i></i></label> </td>';
					if(stockDataData.dataCode){
						tableHtml += '<td>' + stockDataData.dataCode + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(stockDataData.dataTypeDes){
						tableHtml += '<td>' + stockDataData.dataTypeDes+ '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(stockDataData.createUser){
						tableHtml += '<td>'+stockDataData.createUser+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(stockDataData.createTime){
						tableHtml += '<td>' + formatDate(stockDataData.createTime)  + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(stockDataData.orgName){
						tableHtml += '<td>'+stockDataData.orgName+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(typeof(c_stock_substandard) != "undefined"&&c_stock_substandard=="0"){
						var param = "'"+stockDataData.dataCode+"$#$"+(stockDataData.dataType==0?1006:1005)+"'";
						tableHtml += '<td><a href="javascript:void(0)" style="'+c_stock_substandard+'" class="colork" onclick="doSubstandard('+param+')">加入次品</a>&nbsp;&nbsp;';
					}else{
						tableHtml += '<td>不可操作</td>';
					}
				}
			}
			
			//获取汇总
			sumAll = data.data.total;
			params.condition.dataTypeDes='蛋类';
			ajaxRequest("../stockData/list",params,function(data1){
				sumAgg =  data1.data.total;
				sumChicken=sumAll -data1.data.total;
				$('#sumInfo').html("汇总信息-库存量为："+sumAll+",其中鸡库存量为："+sumChicken+"，蛋卖库存为："+sumAgg);
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
	ajaxRequest('../stockData/delete', idParam, function(data) {
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
 * 加入次品方法
 */
function doSubstandard(val){
	defind_text("auto-close-dialogBox","提示","是否加入次品？",function(){
		var param = {};
		param.code= val.split("$#$")[0];
		param.workModel= val.split("$#$")[1];
		ajaxRequest('../substandard/addSubstandardData', param, function(data) {
			if (data.code == 0) {
				set_message("auto-close-dialogBox","提示","加入成功，等待审核！");
				$("#selectAll").attr('checked',false);
				getListByPage(1,$("#search").val());
			}else{
				set_message("auto-close-dialogBox","提示",data.data);
			}
		});
	}, __cancel);
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
	
	ajaxRequest('../stockData/export', params, function(data) {
		if(data.code == 0) {
			window.location.href = "../../common/export/getFile?fileId=" + data.data;
		} else {
			set_message("auto-close-dialogBox", "提示", data.data);

		}
	});
}

