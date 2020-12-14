var sys;//top.sys.userName(1);
var sub;
$(function(){
	 sys = sysInfo();
	 //默认打开控制台页面
	 function sysInfo() {
	 	var users;
	 	var depts;
	 	var dicts;
	 	$.ajax({
	 		cache : true,
	 		type : "GET",
	 		url : top.ctx + "/sysInfo",
	 		async : false,
	 		error : function(request) {
	 			parent.layer.alert("Connection error");
	 		},
	 		success : function(data) {
	 			users = data.users;
	 			depts =data.depts;
	 			dicts =data.dicts;
	 		}
	 	});
	 	return{
	 		userName:function(id){
	 			try{
	 				return users[id].name;
	 			}catch(e){
	 				return '';
	 			}
	 		},userAttr:function(id,field){
	 			try{
	 				return users[id].name;
	 			}catch(e){
	 				return '';
	 			}
	 			return users[id][field];
	 		},user:function(id){
	 			try{
	 				return users[id];
	 			}catch(e){
	 				return '';
	 			}
	 		
	 		},
	 		deptName:function(id){
	 			try{
	 				return depts[id].name;
	 			}catch(e){
	 				return '';
	 			}
	 			
	 		},deptAttr:function(id,field){
	 			try{
	 				return depts[id][field];
	 			}catch(e){
	 				return '';
	 			}
	 		
	 		},dept:function(id){
	 			try{
	 				return depts[id];
	 			}catch(e){
	 				return '';
	 			}
	 			
	 		},dictName:function(type,value){
	 			try{
	 				return dicts[type][value].name;
	 			}catch(e){
	 				return '';
	 			}
	 			
	 		}
	 	}
	 }
	 

	 sub = subInfo();
	 function subInfo() {
	 	return{
	 		resetDict:function(id, targetId) {//目标option value  包含源 value
	 			var val = $('#' + id).val();
	 			var $select = $('#' + targetId);
	 			checkSelect(targetId);
	 			$('#' + targetId ).empty();
	 			$($('#copy_' + targetId + ' option')).each(function(index, element) {
	 				var elevar = $(this).val();
	 				if (elevar.indexOf(val) > -1 ||val=='' || index == 0) {
	 					$select.append($(this).prop("outerHTML"));
	 				} 
	 			});
	 		},
	 		resetDict0:function(id, targetId) {//目标option value 等于 源 value
	 			var val = $('#' + id).val();
	 			var $select = $('#' + targetId);
	 			checkSelect(targetId);
	 			$('#' + targetId ).empty();
	 			$($('#copy_' + targetId + ' option')).each(function(index, element) {
	 				var supervalue = $(this).attr('supervalue');
	 				if (val==supervalue ||val=='' || index == 0) {
	 					$select.append($(this).prop("outerHTML"));
	 				} 
	 			});
	 		},
	 		resetDictByDept:function(id, targetId) {//源 value (deptId) 是目标option value 的上级 
	 			var val = $('#' + id).val();
	 			val =','+ top.sys.deptAttr(val,'childrens')+',';
	 			var $select = $('#' + targetId);
	 			checkSelect(targetId);
	 			$('#' + targetId ).empty();
	 			$($('#copy_' + targetId + ' option')).each(function(index, element) {
	 				var supervalue = $(this).attr('supervalue');
	 				supervalue = ','+ supervalue+','
	 				if (val.indexOf(supervalue)>-1 ||val=='' || index == 0) {
	 					$select.append($(this).prop("outerHTML"));
	 				} 
	 			});
	 		}
	 	}
	 }
	 
	 function checkSelect(targetId){
		 if ( $("#copy_"+targetId).length == 0 ) {
			 var optionHtml = $("#"+targetId).html();
			 var copyHtml = '<select id="copy_'+targetId+'" style="display:none" >'+optionHtml+'</select>';
			 $("#"+targetId).after(copyHtml);
		 }
	 }

	 $("#workMoel").on("change", function(){
		 selectModel();
	 });
	 
	 $("#clearLog").on("click", function(){
		$('#log').html('');
	 });
	 
//	 if($('#actionButt').is(':checked')){
	 
	 $("#actionButt").on("click", function(){
		 //batchNum
		 if($('#actionButt').is(':checked')){
			 $('#batchNum').attr("disabled",true);
			 $('#num').attr("disabled",true);
			 $('#workMoel').attr("disabled",true);
			 $('#typeName').attr("disabled",true);
			 $('#varietyName').attr("disabled",true);
			 $('#supplierId').attr("disabled",true);
			 $('#spaceId').attr("disabled",true);
			 $('#initWeight').attr("disabled",true);
			 $('#immuneAge').attr("disabled",true);
			 $('#immuneDrug').attr("disabled",true);
			 $('#immuneResult').attr("disabled",true);
			 $('#checkWeight').attr("disabled",true);
			 $('#checkResult').attr("disabled",true);
			 $('#substandardReason').attr("disabled",true);
			 $('#customerId').attr("disabled",true);
			 $('#otherInfo').attr("disabled",true);
			 
		 }else{
			 $('#batchNum').attr("disabled",false);
			 $('#num').attr("disabled",false);
			 $('#workMoel').attr("disabled",false);
			 $('#typeName').attr("disabled",false);
			 $('#varietyName').attr("disabled",false);
			 $('#supplierId').attr("disabled",false);
			 $('#spaceId').attr("disabled",false);
			 $('#initWeight').attr("disabled",false);
			 $('#immuneAge').attr("disabled",false);
			 $('#immuneDrug').attr("disabled",false);
			 $('#immuneResult').attr("disabled",false);
			 $('#checkWeight').attr("disabled",false);
			 $('#checkResult').attr("disabled",false);
			 $('#substandardReason').attr("disabled",false);
			 $('#customerId').attr("disabled",false);
			 $('#otherInfo').attr("disabled",false);
		 }
	 });
	 
	 selectModel();
	 
	 $('#logout').on('click',function(){
		  $.confirm({
			  title: '提示',
			  text: '确认登出客户端吗',
			  onOK: function () {
				  top.location.href='mobile/logout';
			  },
			  onCancel: function () {
			  }
			});
	 });
});

function selectModel(){
	$('#batchNum').parent().parent().css('display','none');
	$('#num').parent().parent().css('display','none');
	$('#typeName').parent().parent().css('display','none');
	$('#varietyName').parent().parent().css('display','none');
	$('#supplierId').parent().parent().css('display','none');
	$('#spaceId').parent().parent().css('display','none');
	$('#initWeight').parent().parent().css('display','none');
	$('#immuneAge').parent().parent().css('display','none');
	$('#immuneDrug').parent().parent().css('display','none');
	$('#immuneResult').parent().parent().css('display','none');
	$('#checkWeight').parent().parent().css('display','none');
	$('#checkResult').parent().parent().css('display','none');
	$('#substandardReason').parent().parent().css('display','none');
	$('#customerId').parent().parent().css('display','none');
	$('#otherInfo').parent().parent().css('display','none');
	var workMoel = $('#workMoel').val();
		//	alert(workMoel);
	if(workMoel=='1000'){//获取动物信息
		$('#num').parent().parent().css('display','');
	}else if(workMoel=='1001'){//入库采集
		$('#batchNum').parent().parent().css('display','');
		$('#num').parent().parent().css('display','');
		$('#typeName').parent().parent().css('display','');
		$('#varietyName').parent().parent().css('display','');
		$('#supplierId').parent().parent().css('display','');
		$('#spaceId').parent().parent().css('display','');
		$('#initWeight').parent().parent().css('display','');
		$('#otherInfo').parent().parent().css('display','');
	}else if(workMoel=='1002'){//检疫采集
		$('#num').parent().parent().css('display','');
		$('#immuneAge').parent().parent().css('display','');
		$('#immuneDrug').parent().parent().css('display','');
		$('#immuneResult').parent().parent().css('display','');
		$('#otherInfo').parent().parent().css('display','');
	}else if(workMoel=='1003'){//检验采集
		$('#num').parent().parent().css('display','');
		$('#checkWeight').parent().parent().css('display','');
		$('#checkResult').parent().parent().css('display','');
		$('#otherInfo').parent().parent().css('display','');
	}else if(workMoel=='1004'){//耗损采集
		$('#num').parent().parent().css('display','');
		$('#substandardReason').parent().parent().css('display','');
		$('#otherInfo').parent().parent().css('display','');
	}else if(workMoel=='1005'){//出库采集
		$('#num').parent().parent().css('display','');
		$('#customerId').parent().parent().css('display','');
		$('#otherInfo').parent().parent().css('display','');
	}
}

//提交数据到服务 或者跳转到详情页面
function putData(fn){
	 if(!$('#actionButt').is(':checked')){
		 $('#log').prepend(now()+"  提示：启用后才可采集数据\n");
		 if(typeof fn === "function"){
			 fn({code:-1});//失败
		 }
		 return;
	 }
	
	 //提交数据
	 var workMoel =  $('#workMoel').val();
			 
	 if(workMoel=='1000'){
		 if($('#num').val()==''){
			 $('#log').prepend(now()+"  未采集到任何数据\n");
			 if(typeof fn === "function"){
				 fn({code:-1});//失败
			 }
			 return;
		 }else{
			 self.location.href=top.ctx +"/animal/inputs/info/"+$('#num').val();
		 }
	 }else{
		 var params={};
		 params.batchNum= $('#batchNum').val();
		 params.num= $('#num').val();
		 params.typeName= $('#typeName').val();
		 params.varietyName= $('#varietyName').val();
		 params.supplierId= $('#supplierId').val();
		 params.spaceId= $('#spaceId').val();
		 params.initWeight= $('#initWeight').val();
		 params.immuneAge= $('#immuneAge').val();
		 params.immuneDrug= $('#immuneDrug').val();
		 params.immuneResult= $('#immuneResult').val();
		 params.checkWeight= $('#checkWeight').val();
		 params.checkResult= $('#checkResult').val();
		 params.substandardReason= $('#substandardReason').val();
		 params.customerId= $('#customerId').val();
		 params.otherInfo= $('#otherInfo').val();
		 $.post(top.ctx +"/animal/api/work/"+workMoel, params,function(data){
			 $('#log').prepend(now()+"   "+data.msg+"\n");
			 if(typeof fn === "function"){
				 fn(data);
			 }
		 });
	 }
}

function now(){
	//判断是否在前面加0
	function getNow(s) {
	return s < 10 ? '0' + s: s;
	}
	var myDate = new Date();             

	var year=myDate.getFullYear();        //获取当前年
	var month=myDate.getMonth()+1;   //获取当前月
	var date=myDate.getDate();            //获取当前日

	var h=myDate.getHours();              //获取当前小时数(0-23)
	var m=myDate.getMinutes();          //获取当前分钟数(0-59)
	var s=myDate.getSeconds();

	var nowTime=year+'-'+getNow(month)+"-"+getNow(date)+" "+getNow(h)+':'+getNow(m)+":"+getNow(s);
	return nowTime;
}