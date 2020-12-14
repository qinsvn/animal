$(function(){
	Info();
    $("#submit").click(function(){
    	submit();
    });
});
 
function Info(){
	ajaxGetFunctionAsytrue("../nestData/getBaseSysParam",null,function(data){
		if(data.code==0){
				if(data.data){
					$('#layMinDuration').val(data.data.layMinDuration);
					$('#layMaxDuration').val(data.data.layMaxDuration);
					$('#layMinWeight').val(data.data.layMinWeight);
					$('#layMinNest').val(data.data.layMinNest);
				}
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	});
}

function submit(){
	
	var r = /^\+?[1-9][0-9]*$/; 
	if(!r.test($('#layMinDuration').val())){
		alert('请输入整数！');
		return;
	}
	if(!r.test($('#layMaxDuration').val())){
		alert('请输入整数！');
		return;
		
	}
	if(!r.test($('#layMinWeight').val())){
		alert('请输入整数！');
		return;
		
	}
	if(!r.test($('#layMinNest').val())){
		alert('请输入整数！');
		return;
		
	}
	
	var params = {
			layMinDuration:$('#layMinDuration').val(),
			layMaxDuration:$('#layMaxDuration').val(),
			layMinWeight:$('#layMinWeight').val(),
			layMinNest:$('#layMinNest').val()
		};
	
	ajaxPostFunctionAsytrue("../nestData/saveBaseSysParam",params,function(data){
		if(data.code==0){
			set_message("auto-close-dialogBox","提示","保存成功！");
		}else{
			set_message("auto-close-dialogBox","提示",data.data);
		}
	});
}
