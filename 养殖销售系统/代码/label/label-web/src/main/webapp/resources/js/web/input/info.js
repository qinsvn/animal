$(function(){
	Info();
    $("#submit").click(function(){
    	submit();
    });
    $("#submit2").click(function(){
    	submit2();
    });
    $("#submit3").click(function(){
    	submit3();
    });
});
 
function Info(){
	ajaxGetFunctionAsytrue("../input/getBaseInputdata",null,function(data){
		if(data.code==0){
				if(data.data.chickenBaseinputs.length>0){
					$('#weight').val(data.data.chickenBaseinputs[0].weight);
					$('#chickenType').val(data.data.chickenBaseinputs[0].chickenType);
					$('#supplier').val(data.data.chickenBaseinputs[0].supplier);
					$('#otherInfo').val(data.data.chickenBaseinputs[0].otherInfo);
					
					var html="";
					for(var i=0;i<data.data.chickenBaseinputs.length;i++){
						var baseData = data.data.chickenBaseinputs[i];
						html+='<tr><td>'+baseData.supplier+'</td>';
						var supplierParam = "'"+baseData.supplier+"'";
						html += '<td><a href="javascript:void(0)" style="color: #0044cc;" onclick="selectSupplierHis('+supplierParam+')" >选定</a></td></tr>';
					}
					$("#supplierHisBody").empty();
					$("#supplierHisBody").append(html);
				}
				if(data.data.immuneBaseinputs.length>0){
					$('#age').val(data.data.immuneBaseinputs[0].age);
					$('#i_weight').val(data.data.immuneBaseinputs[0].weight);
					$('#immuneType').val(data.data.immuneBaseinputs[0].immuneType);
					$('#result').val(data.data.immuneBaseinputs[0].result);
					
					
					var html="";
					for(var i=0;i<data.data.immuneBaseinputs.length;i++){
						var baseData = data.data.immuneBaseinputs[i];
						html+='<tr><td>'+baseData.immuneType+'</td>';
						var immuneTypeParam = "'"+baseData.immuneType+"'";
						html += '<td><a href="javascript:void(0)" style="color: #0044cc;" onclick="selectImmuneTypeHis('+immuneTypeParam+')" >选定</a></td></tr>';
					}
					$("#immuneTypeHisBody").empty();
					$("#immuneTypeHisBody").append(html);
					
					
				}
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	});
}

function submit(){
	var params ={name:$('#name').val()};
	ajaxPostFunctionAsytrue("../input/saveChickenType",params,function(data){
		if(data.code==0){
			set_message("auto-close-dialogBox","提示","保存成功！");
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	});
}

function submit2(){
	var params = {
		weight:$('#weight').val(),
	   chickenType:$('#chickenType').val(),
	   supplier:$('#supplier').val(),
	   otherInfo:$('#otherInfo').val()
	};
	ajaxPostFunctionAsytrue("../input/saveChickenBaseinput",params,function(data){
		if(data.code==0){
			set_message("auto-close-dialogBox","提示","保存成功！");
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	});
}

function submit3(){
	var params = {
			age : $('#age').val(),
		weight:$('#i_weight').val(),
		immuneType:$('#immuneType').val(),
		result:$('#result').val()
	};
	ajaxPostFunctionAsytrue("../input/saveImmuneData",params,function(data){
		if(data.code==0){
			set_message("auto-close-dialogBox","提示","保存成功！");
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	});
}
function selectSupplierHis(val){
	$('#supplier').val(val);
	$('#supplierHis').modal('hide');
}

function selectImmuneTypeHis(val){
	$('#immuneType').val(val);
	$('#immuneTypeHis').modal('hide');
}


//function createWindowImmuneTypeHis(e){
//	$('input[name="role"]').prop("checked",false);
//	var userId = $(e).data("id");
//	var fdRoles = $(e).data("role")+"";
//	$("#userid").val(userId);
//	if(fdRoles){
//		var fdRoleList = fdRoles.split(",");
//		for(var i=0;i<fdRoleList.length;i++){
//			$('input[name="role"]').each(function(){
//				if(fdRoleList[i]==$(this).val()){
//					//勾选
//					$(this).prop("checked",true);
//					
//				}
//			})
//		}
//	}
//	$('#modal-form').modal('show');
//}