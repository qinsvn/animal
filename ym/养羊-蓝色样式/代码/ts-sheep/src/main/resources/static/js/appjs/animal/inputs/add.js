$().ready(function() {
	validateRule();
	var spaceId = $('#spaceId').val();
	var varietyName = $('#varietyName').val();
	var supplierId = $('#supplierId').val();
	
	sub.resetDict('typeName','varietyName');
	sub.resetDictByDept('deptId','spaceId');
	sub.resetDictByDept('deptId','supplierId')
	
	$('#varietyName').val(varietyName);
	$('#spaceId').val(spaceId);
	$('#supplierId').val(supplierId);
	// debugger;
	laydate.render({
		elem: '#birthday' //指定元素
	});
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : top.ctx + "/animal/inputs/save",
		data : $('#signupForm').serialize(),// 你的formid
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
			num : {
				required : true
			},
			batchNum : {
				required : true
			},
			typeName : {
				required : true
			},
			varietyName : {
				required : true
			}
		},
		messages : {
			num : {
				required : icon + "请输入编号"
			},
			batchNum : {
				required : icon + "请输入批次"
			},
			typeName : {
				required : icon + "请选择动物种类"
			},
			varietyName : {
				required : icon + "请选择动物品种"
			}
		}
	})
}

function getSpace(){
	
}