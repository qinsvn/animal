var prefix = top.ctx + "/animal/substandardData"
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
								
																batchNum : $('#con_batchNum').val(), 
																typeName : $('#con_typeName').val(), 
																varietyName : $('#con_varietyName').val(), 
																num : $('#con_num').val(), 
																substandardReason : $('#con_substandardReason').val(), 
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
									field : 'approvalId', 
									title : '审核人'  ,
									formatter : function(value, row, index) {
										return top.sys.userName(value);
									}
								},
																{
									field : 'approvalTime', 
									title : '审核时间' 
								},
																{
									field : 'substandardReason', 
									title : '耗损原因'  ,
									formatter : function(value, row, index) {
										return top.sys.dictName('substandard_reason',value);
									}
								},
																{
									field : 'deptId', 
									title : '所属养殖户' ,
									formatter : function(value, row, index) {
										return top.sys.deptName(value);
									}
								},
																{
									field : 'otherInfo', 
									title : '其他信息' 
								},
																{
									field : 'createUser', 
									title : '上报人员'  ,
										formatter : function(value, row, index) {
											return top.sys.userName(value);
										}
								},
																{
									field : 'createTime', 
									title : '上报时间' 
								}]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
//重置
function reset() {
		$("#con_batchNum").val('');
		$("#con_num").val('');
		$("#con_typeName").val('');
		$("#con_varietyName").val('');
		$("#con_substandardReason").val('');
		reLoad();
	}


function exportData() {
	var exportFileId = Math.random();
	var params = {
							batchNum : $('#con_batchNum').val(), 
							typeName : $('#con_typeName').val(), 
							varietyName : $('#con_varietyName').val(), 
							num : $('#con_num').val(), 
							substandardReason : $('#con_substandardReason').val(), 
							status : $('#con_status').val(), 
							
							//以下是 导出配置
							exportFileId :exportFileId,
							exportFileName :"耗损数据",
							exportSplit :"##",
							exportHeader :['投入品编号' ,'批次' ,'种类'  , '品种'  ,'审核人'  ,'审核时间' ,'耗损原因'  , '所属养殖户' ,'其他信息', '上报人员'  , '上报时间'  ].join("##"),
							exportkeys :['num', 'batchNum', 'typeName',  'varietyName',  'approvalId',  'approvalTime',  'substandardReason', 'deptId', 'otherInfo',  'createUser', 'createTime'].join("##")
						};
	
	 $.post(prefix + "/list", params,function(data){
		 location.href = top.ctx + "/common/sysFile/file/" + exportFileId;
	 });
}