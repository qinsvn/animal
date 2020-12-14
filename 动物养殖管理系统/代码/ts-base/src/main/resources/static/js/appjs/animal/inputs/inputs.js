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
								},
																{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="javascript:void(0)" mce_href="javascript:void(0)" title="编辑" onclick="edit(\''
												+ row.id
												+ '\')">编辑</a> ';
										var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="javascript:void(0)" title="删除"  mce_href="#" onclick="remove(\''
												+ row.id
												+ '\')">删除</a> ';
										var f = '<a class="btn btn-success btn-sm" href="javascript:void(0)" title="备用"  mce_href="#" onclick="resetPwd(\''
												+ row.id
												+ '\')"><i class="fa fa-key"></i></a> ';
										return e + d ;
									}
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
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	});
}

/**
 * 检疫
 */
function immune() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要检疫的投入品");
		return;
	}
	var g=true;
	$.each(rows, function(i, row) {
		if( row['status']!='0'){
			layer.msg("只能检疫饲养中的投入品");
			g = false;
			return;
		}
	});
	if(!g){
		return;
	}
	
	layer.open({
		type : 2,
		title : '对'+rows.length+'件投入品进行检疫',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '460px' ],
		content :  top.ctx + "/animal/immuneRecord/add" // iframe的url
	});
	
}

/**
 * 检查
 */
function check() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要检查的投入品");
		return;
	}
	var g=true;
	$.each(rows, function(i, row) {
		if( row['status']!='0'){
			layer.msg("只能检查饲养中的投入品");
			g = false;
			return;
		}
	});
	if(!g){
		return;
	}
	layer.open({
		type : 2,
		title : '对'+rows.length+'件投入品进行检查',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '460px' ],
		content :  top.ctx + "/animal/checkRecord/add" // iframe的url
	});
}

/**
 * 售卖
 */
function sale() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要售卖的投入品");
		return;
	}
	var g=true;
	$.each(rows, function(i, row) {
		if( row['status']!='0'){
			layer.msg("只能售卖饲养中的投入品");
			g = false;
			return;
		}
	});
	if(!g){
		return;
	}

	layer.confirm("确认要售卖选中的'" + rows.length + "'件产品吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url :  top.ctx + "/animal/saleRecord/sale",
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {

	});

}


/**
 * 加入次品
 */
function bad() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要列入耗损的投入品");
		return;
	}
	var g=true;
	$.each(rows, function(i, row) {
		if( row['status']!='0'){
			layer.msg("只能检查饲养中的投入品");
			g = false;
			return;
		}
	});
	if(!g){
		return;
	}
	layer.open({
		type : 2,
		title : '将'+rows.length+'件投入品进行加入耗损',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '360px' ],
		content :  top.ctx + "/animal/substandardData/add" // iframe的url
	});
}


function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {

	});
}


function nconception() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择录为未受孕的投入品数据");
		return;
	}
	layer.confirm("确认要将'" + rows.length + "'条数据录为未受孕吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			$.ajax({
				cache : true,
				type : "POST",
				url : top.ctx + "/animal/inputs/update",
				data : {id:row['id'],remark1:'0'},
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