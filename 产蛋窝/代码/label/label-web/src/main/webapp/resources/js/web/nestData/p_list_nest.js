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
	
    activeTime('search_startInTime', 'search_endInTime');
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
	var startInTime = $("#search_startInTime").val();
	var endInTime = $("#search_endInTime").val();

	var startNestDuration = $("#startNestDuration").val();
	var endNestDuration = $("#endNestDuration").val();
	var startNestDurationSum = $("#startNestDurationSum").val();
	var endNestDurationSum = $("#endNestDurationSum").val();
	
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
	if(startInTime&&startInTime!=''){
		params.condition.startInTime=startInTime;
	}
	if(endInTime&&endInTime!=''){
		params.condition.endInTime=endInTime;
	}

	var r = /^\+?[1-9][0-9]*$/; 
	if(startNestDuration&&startNestDuration!=''){
		if(!r.test(startNestDuration)){
			alert('抱窝时长必须为整数！！');
			return;
			
		}
		params.condition.startNestDuration=startNestDuration*60;
	}
	if(endNestDuration&&endNestDuration!=''){
		if(!r.test(endNestDuration)){
			alert('抱窝时长必须为整数！！');
			return;
		}
		params.condition.endNestDuration=endNestDuration*60;
	}
	if(startNestDurationSum&&startNestDurationSum!=''){
		if(!r.test(startNestDurationSum)){
			alert('抱窝时长必须为整数！！');
			return;
		}
		params.condition.startNestDurationSum=startNestDurationSum*60;
	}
	if(endNestDurationSum&&endNestDurationSum!=''){
		if(!r.test(endNestDurationSum)){
			alert('抱窝时长必须为整数！！');
			return;
		}
		params.condition.endNestDurationSum=endNestDurationSum*60;
	}
	
	$(".loadrtkuno").css("display", "block")
	ajaxRequest("../nestData/listBsNest",params,function(data){
		if(data.code==0){
			$("#umantable").html();//清空原有数据
			var itemCount = data.data.total;
			updatePage(itemCount);
			selectPage(page);
			var tableHtml= "";
			var rs = data.data.rows;
			if(rs.length>0){
				for(var i = 0; i<rs.length; i++){
					var user = rs[i];
					tableHtml += '<tr  >';
					if(user.rfidNum){
						tableHtml += '<td>' + user.rfidNum + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}

					if(user.outTime){
						tableHtml += '<td>'+formatDate(user.outTime)+'</td>';
					}else{
						tableHtml += '<td></td>';
					}

					if(user.outBodyWeight){
						tableHtml += '<td>'+user.outBodyWeight+'</td>';
					}else{
						tableHtml += '<td></td>';
					}

					if(user.nestDuration){
						if(user.nestDuration>60){
							var hour = parseInt(user.nestDuration/60);
							var mini = user.nestDuration%60;
							if(mini!=0){
								tableHtml += '<td>'+hour+'小时 '+mini+'分钟</td>';
							}else{
								tableHtml += '<td>'+hour+'小时 </td>';
							}
						}else{
							tableHtml += '<td>'+user.nestDuration+'分钟</td>';
						}
					}else{
						tableHtml += '<td></td>';
					}

					if(user.nestDurationSum){
						if(user.nestDurationSum>60){
							var hour = parseInt(user.nestDurationSum/60);
							var mini = user.nestDurationSum%60;
							if(mini!=0){
								tableHtml += '<td>'+hour+'小时 '+mini+'分钟</td>';
							}else{
								tableHtml += '<td>'+hour+'小时 </td>';
							}
						}else{
							tableHtml += '<td>'+user.nestDurationSum+'分钟</td>';
						}
					}else{
						tableHtml += '<td></td>';
					}

					if(user.workshopName){
						tableHtml += '<td>' + user.workshopName + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					tableHtml += '</tr>';
				}
			}
			setTimeout(function() {
				$(".loadrtkuno").css("display", "none")// 隐藏加载动画
				$("#umantable").html(tableHtml)
			}, 500);
		}else{
			set_message("auto-close-dialogBox","提示",data.message);
		}
	});
	
}

function activeTime(start, end) {
	var myDate = new Date();
	myDate = myDate.toLocaleDateString();

	var sDate = laydate.render({
		elem: "#" + start,
		type: "datetime",
		format: "yyyy-MM-dd HH:mm:ss",
		istime: false,
		btns: ['confirm'],
		done: function(value, date) {
			if(value != "") {
				eDate.config.min.year = date.year;
				eDate.config.min.month = date.month - 1;
				eDate.config.min.date = date.date;
				eDate.config.min.hours = date.hours;
				eDate.config.min.minutes = date.minutes;
				eDate.config.min.seconds = date.seconds;
			} else {
				eDate.config.min.year = "";
				eDate.config.min.month = "";
				eDate.config.min.date = "";
				eDate.config.min.hours = "";
				eDate.config.min.minutes = "";
				eDate.config.min.seconds = "";
			}
		}
	})

	var eDate = laydate.render({
		elem: "#" + end,
		type: "datetime",
		format: "yyyy-MM-dd HH:mm:ss",
		istime: false,
		btns: ['confirm'],
		done: function(value, date) {
			if(value != "") {
//				sDate.config.max.year = date.year;
//				sDate.config.max.month = date.month - 1;
//				sDate.config.max.date = date.date;
				sDate.config.min.hours = date.hours;
				sDate.config.min.minutes = date.minutes;
				sDate.config.min.seconds = date.seconds;
			} else {
				sDate.config.max.year = "";
				sDate.config.max.month = "";
				sDate.config.max.date = "";
				sDate.config.min.hours = "";
				sDate.config.min.minutes = "";
				sDate.config.min.seconds = "";
			}
		}
	})
}
