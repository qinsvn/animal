var itemPage = -1;
var idParam =[];
var allRole=[];
$(function(){	
	itemPage=10;
	initPage();
	getListByPage(1,$("#search").val());
	
	/**
	 * 搜索按钮
	 */
	$("#searchBtn").click(function(){
		getListByPage(1,$("#search").val());
	});
	
	$("#expNestDataButt").click(function() {
		//提示是否导出用户数据
		doDown();
	})
	
});


/**
 * 分页获取数据
 * @param page 起始页码
 * @param name 产品名称 
 */
function getListByPage(page,name){
	$(".loadrtkuno").css("display", "block")
	var rfidNum = $("#search_irfid_num").val();
	var workShopName = $("#search_workshop_name").val();
	var params={
			page:page,
			rows:itemPage,
			condition:{ }
	}
	if(rfidNum&&rfidNum!=''){
		params.condition.rfidNum=rfidNum;
	}
	if(workShopName&&workShopName!=''){
		params.condition.workShopName=workShopName;
	}
	$(".loadrtkuno").css("display", "block")
	ajaxRequest("../nestData/list20",params,function(data){
		if(data.code==0){
			$("#umantable").html();//清空原有数据
			var itemCount = data.data.total;
			updatePage(itemCount);
			selectPage(page);
			var tableHtml= "";
			var rs = data.data.rows;
			if(rs.length>0){
			
				var lastStyle = '';
				var lastRfidNum='';
				var bcStyle;
				
				for(var i = 0; i<rs.length; i++){
					var user = rs[i];
					if(i!=0){
						if(lastRfidNum==user.rfidNum){
							bcStyle = lastStyle;
						}else{
							if(lastStyle==''){
								lastStyle =' style="background-color: #d4ecec;" ';
							}else{
								lastStyle = '';
							}
						}
					}
					bcStyle = lastStyle;
					lastRfidNum = user.rfidNum;
					
					tableHtml += '<tr '+bcStyle+' >';
					if(user.workshopName){
						tableHtml += '<td>' + user.workshopName + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(user.rfidNum){
						tableHtml += '<td>' + user.rfidNum + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(user.inOrder){
						tableHtml += '<td>第' + user.inOrder + '次</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(user.dateRange){
						tableHtml += '<td>'+user.dateRange+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.endWeight){
						tableHtml += '<td>'+user.endWeight+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.incBodyWeight){
						tableHtml += '<td>'+user.incBodyWeight+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.eatMtWeight){
						tableHtml += '<td>'+user.eatMtWeight+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.mtBodyRatio){
						tableHtml += '<td>'+user.mtBodyRatio+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					tableHtml += '<td><a href="javascript:void(0)" class="colork" onclick="doDownInRfid('+user.rfidNum+')">导出</a>&nbsp;&nbsp;';
				}
			}
			setTimeout(function() {
				$(".loadrtkuno").css("display", "none")// 隐藏加载动画
				$("#umantable").html(tableHtml)
			}, 500)
		}else{
			set_message("auto-close-dialogBox","提示",data.message);
		}
	});
	
}

/**
 * 导出excel
 */
function doDown() {
	var rfidNum = $("#search_irfid_num").val();
	var workShopName = $("#search_workshop_name").val();
	var params={	};
	if(rfidNum&&rfidNum!=''){
		params.rfidNum=rfidNum;
	}
	if(workShopName&&workShopName!=''){
		params.workShopName=workShopName;
	}
	ajaxRequest('../nestData/export20', params, function(data) {
		if(data.code == 0) {
			window.location.href = "../../common/export/getFile?fileId=" + data.data;
		} else {
			set_message("auto-close-dialogBox", "提示", data.data);

		}
	});
}

/**
 * 导出excel
 */
function doDownInRfid(rfidNum) {
	var params={	};
	if(rfidNum&&rfidNum!=''){
		params.rfidNum=rfidNum;
	}
	ajaxRequest('../nestData/export20', params, function(data) {
		if(data.code == 0) {
			window.location.href = "../../common/export/getFile?fileId=" + data.data;
		} else {
			set_message("auto-close-dialogBox", "提示", data.data);

		}
	});
}