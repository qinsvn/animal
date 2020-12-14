var prefix = top.ctx + "/animal/inputs"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								
																num : $('#con_num').val(), 
																batchNum : $('#con_batchNum').val(), 
																typeName : $('#con_typeName').val(), 
																varietyName : $('#con_varietyName').val(), 
																status : $('#con_status').val(), 
																remark1 : '0', 
																
								offset:params.offset
					           // name:$('#searchName').val(),
					           // username:$('#searchName').val()
							};
						},
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						columns : [
								{
									checkbox : true
								},
																{
									field : 'num', 
									title : '投入品编号' 
								},
																{
									field : 'batchNum', 
									title : '批次' 
								},
																{
									field : 'typeName', 
									title : '种类'  ,
									formatter : function(value, row, index) {
										return top.sys.dictName('animal_type',value);
									}
								},
																{
									field : 'varietyName', 
									title : '品种'  ,
									formatter : function(value, row, index) {
										return top.sys.dictName('animal_varieties',value);
									}
								},
																{
									field : 'initWeight', 
									title : '最初体重' 
								},
																{
									field : 'curWeight', 
									title : '目前体重' 
								},
								{
									field : 'spaceName', 
									title : '入住场地' 
								},
																{
									field : 'deptId', 
									title : '所属养殖户' ,
									formatter : function(value, row, index) {
										return top.sys.deptName(value);
									}
								},
								{
									field : 'supplierName', 
									title : '供应商' 
								},
																{
									field : 'status', 
									title : '状态'  ,
									formatter : function(value, row, index) {
										return top.sys.dictName('inputs_status',value);
									}
								},
																{
									field : 'createUser', 
									title : '录入人员' ,
									formatter : function(value, row, index) {
										return top.sys.userName(value);
									}
								},
																{
									field : 'createTime', 
									title : '入栏时间' 
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
//重置
function reset() {
		$("#con_id").val('');
		$("#con_num").val('');
		$("#con_batchNum").val('');
		$("#con_typeName").val('');
		$("#con_varietyName").val('');
		$("#con_initWeight").val('');
		$("#con_curWeight").val('');
		$("#con_deptId").val('');
		$("#con_otherInfo").val('');
		$("#con_status").val('');
		$("#con_createUser").val('');
		$("#con_createTime").val('');
		reLoad();
	}


function conception() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择录为已受孕的投入品数据");
		return;
	}
	layer.confirm("确认要将'" + rows.length + "'条数据录为已受孕吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			$.ajax({
				cache : true,
				type : "POST",
				url : top.ctx + "/animal/inputs/update",
				data : {id:row['id'],remark1:'1'},
				async : false,
				error : function(request) {
					parent.layer.alert("Connection error");
				},
				success : function(data) {
				}
			});
			
		});
		layer.msg('操作成功');
		reLoad();
	}, function() {

	});
	
}

function getRows(){
	return $('#exampleTable').bootstrapTable('getSelections'); 
}