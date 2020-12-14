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
	})
	
});

/**
 * 分页获取数据
 * @param page 起始页码
 * @param name 产品名称 
 */
function getListByPage(page,name){
	$(".loadrtkuno").css("display", "block")
	var rfidNum = $("#search_rfid_num").val();
	var name = $("#search_name").val();
	var startExceed = $("#search_startExceed").val();
	var params={
			page:page,
			rows:itemPage,
			condition:{ }
	}
	if(rfidNum&&rfidNum!=''){
		params.condition.rfidNum=rfidNum;
	}
	if(name&&name!=''){
		params.condition.name=name;
	}
	if(startExceed&&startExceed!=''){
		params.condition.startExceed=startExceed*60;
	}
	$(".loadrtkuno").css("display", "block")
	ajaxRequest("../warn/list",params,function(data){
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
					tableHtml += '<tr><td><label class="checkbox m-n i-checks"><input type="checkbox" name="usman" value="'
						+ user.id + '"><i></i></label> </td>';
					if(user.rfidNum){
						tableHtml += '<td>' + user.rfidNum + '</td>';						
					}else{
						tableHtml += '<td></td>';
					}
					if(user.name){
						tableHtml += '<td>'+user.name+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.exceedTime){
						if(user.exceedTime>60){
							var hour = parseInt(user.exceedTime/60);
							var mini = user.exceedTime%60;
							if(mini!=0){
								tableHtml += '<td>'+hour+'小时 '+mini+'分钟</td>';
							}else{
								tableHtml += '<td>'+hour+'小时 </td>';
							}
						}else{
							tableHtml += '<td>'+user.exceedTime+'分钟</td>';
						}
					}else{
						tableHtml += '<td></td>';
					}
					if(user.header){
						tableHtml += '<td>'+user.header+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.warnTime){
						tableHtml += '<td>'+formatDate(user.warnTime)+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
					if(user.warnInfo){
						tableHtml += '<td title="'+user.warnInfo+'">'+user.warnInfo+'</td>';
					}else{
						tableHtml += '<td></td>';
					}
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
