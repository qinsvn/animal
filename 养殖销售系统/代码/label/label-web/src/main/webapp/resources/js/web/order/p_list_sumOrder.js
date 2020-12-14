var itemPage = -1;
var idParam =[];
var allRole=[];

var sumAll=0;
var sumChicken=0;
var sumAgg=0

$(function(){	
	activeTime('startCreateTime', 'endCreateTime');
	itemPage=1000;
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
			set_message("auto-close-dialogBox","提示","请选择订单明细");
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
	var year = $("#year").val();
	var params={
			page:page,
			rows:itemPage,
			condition:{ }
	};
	if(year&&year!='-1'){
		params.condition.year=year;
	}

	$(".loadrtkuno").css("display", "block");
	ajaxRequest("../order/listSum",params,function(data){
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
					var orderData = rs[i];
					tableHtml += '<tr><td><label class="checkbox m-n i-checks"><input type="checkbox" name="usman" value="'
						+ orderData.id + '"><i></i></label> </td>';
					if(orderData.monthly){
						tableHtml += '<td>' + orderData.monthly + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(orderData.allsum){
						tableHtml += '<td>' + orderData.allsum + '</td>';						
					}else{
						tableHtml += '<td>0</td>';
					}
					if(orderData.checken){
						tableHtml += '<td>' + orderData.checken+ '</td>';						
					}else{
						tableHtml += '<td>0</td>';
					}
					if(orderData.agg){
						tableHtml += '<td>' + orderData.agg+ '</td>';						
					}else{
						tableHtml += '<td>0</td>';
					}
					
					 sumAll=sumAll+orderData.allsum;
					 sumChicken=sumChicken+orderData.checken;
					 sumAgg=sumAgg+orderData.agg;
					
				}
			}
			setTimeout(function() {
				$(".loadrtkuno").css("display", "none");// 隐藏加载动画
				$("#umantable").html(tableHtml);
			}, 500);
			
			$('#sumInfo').html("汇总信息-总卖出量为："+sumAll+",其中鸡卖出量为："+sumChicken+"，蛋卖出量为："+sumAgg);
		}else{
			set_message("auto-close-dialogBox","提示",data.message);
		}
	});
	
}

/**
 * 删除方法
 */
function doDelete(){
	ajaxRequest('../order/delete', idParam, function(data) {
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
 * 导出excel
 */
function doDown() {
	var year = $("#year").val();
	var params={	};
	if(year&&year!=''){
		year.year=year;
	}
	ajaxRequest('../order/exportSum', params, function(data) {
		if(data.code == 0) {
			window.location.href = "../../common/export/getFile?fileId=" + data.data;
		} else {
			set_message("auto-close-dialogBox", "提示", data.data);

		}
	});
}
