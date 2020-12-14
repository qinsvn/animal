var itemPage = -1;
var idParam =[];
var allRole=[];
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
	
});

/**
 * 分页获取数据
 * @param page 起始页码
 * @param name 产品名称 
 */
function getListByPage(page,name){
	$(".loadrtkuno").css("display", "block");
	var barCode = $("#barCode").val();
	var startCreateTime = $("#startCreateTime").val();
	var endCreateTime = $("#endCreateTime").val();
	var params={
			page:page,
			rows:itemPage,
			condition:{ }
	};
	if(barCode&&barCode!=''){
		params.condition.barCode=barCode;
	}
	if(startCreateTime&&startCreateTime!=''){
		params.condition.startCreateTime=startCreateTime;
	}
	if(endCreateTime&&endCreateTime!=''){
		params.condition.endCreateTime=endCreateTime;
	}
	$(".loadrtkuno").css("display", "block");
	ajaxRequest("../agg/list",params,function(data){
		if(data.code==0){
			$("#umantable").html();//清空原有数据
			var itemCount = data.data.total;
			updatePage(itemCount);
			selectPage(page);
			var tableHtml= "";
			var rs = data.data.rows;
			if(rs.length>0){
				for(var i = 0; i<rs.length; i++){
					var aggData = rs[i];
					tableHtml += '<tr><td><label class="checkbox m-n i-checks"><input type="checkbox" name="usman" value="'
						+ aggData.id + '"><i></i></label> </td>';
					if(aggData.barCode){
						tableHtml += '<td>' + aggData.barCode + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(aggData.createTime){
						tableHtml += '<td>' + formatDate(aggData.createTime) + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(aggData.createUser){
						tableHtml += '<td>' + aggData.createUser + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(aggData.orgName){
						tableHtml += '<td>'+aggData.orgName+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
				}
			}
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
	ajaxRequest('../agg/delete', idParam, function(data) {
		if (data.code == 0) {
			set_message("auto-close-dialogBox","提示","删除成功！");
			$("#selectAll").attr('checked',false);
			getListByPage(1,$("#search").val());
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	});
}
