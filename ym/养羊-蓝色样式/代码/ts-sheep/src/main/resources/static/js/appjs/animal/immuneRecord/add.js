$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
//	debugger;
	var rows =parent.getRows(); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	var remark1='';
	$.each(rows, function(i, row) {
		remark1 =remark1+ ','+row['id'];
	});
	$.ajax({
		cache : true,
		type : "POST",
		url : top.ctx + "/animal/immuneRecord/save",
		data : $('#signupForm').serialize()+"&remark1="+remark1,// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			immuneAge : {
				required : true
			},
			immuneDrug : {
				required : true
			},
			immuneResult : {
				required : true
			}
		},
		messages : {
			immuneAge : {
				required : icon + "请填写检疫日龄"
			},
			immuneDrug : {
			required : icon + "请填写检疫药物"
		},
		immuneResult : {
			required : icon + "请填写检疫结果"
		}
		}
	})
}