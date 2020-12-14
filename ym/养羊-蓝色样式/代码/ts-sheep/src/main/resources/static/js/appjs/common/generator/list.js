var prefix = top.ctx + "/common/generator"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
		.bootstrapTable({
			method: 'get', // 服务器数据的请求方式 get or post
			url: prefix + "/list", // 服务器数据的加载地址
			showRefresh: true,
			showToggle: true,
			showColumns: true,
			iconSize: 'outline',
			toolbar: '#exampleToolbar',
			striped: true, // 设置为true会有隔行变色效果
			dataType: "json", // 服务器返回的数据类型
			pagination: true, // 设置为true会在底部显示分页条
			// queryParamsType : "limit",
			// //设置为limit则会发送符合RESTFull格式的参数
			singleSelect: false, // 设置为true将禁止多选
			// contentType : "application/x-www-form-urlencoded",
			// //发送到服务器的数据编码类型
			pageSize: 10, // 如果设置了分页，每页数据条数
			pageNumber: 1, // 如果设置了分布，首页页码
			search: true, // 是否显示搜索框
			showColumns: true, // 是否显示内容下拉框（选择显示的列）
			sidePagination: "client", // 设置在哪里进行分页，可选值为"client" 或者
			// "server"
			// queryParams : queryParams,
			// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
			// queryParamsType = 'limit' ,返回参数必须包含
			// limit, offset, search, sort, order 否则, 需要包含:
			// pageSize, pageNumber, searchText, sortName,
			// sortOrder.
			// 返回false将会终止请求
			columns: [{
					checkbox: true
				},
				{
					field: 'tableName', // 列字段名
					title: '表名称' // 列标题
				},
				{
					field: 'engine',
					title: 'engine'
				},
				{
					field: 'tableComment',
					title: '表描述'
				},
				{
					field: 'createTime',
					title: '创建时间'
				},
				{
					title: '操作',
					field: 'id',
					align: 'center',
					formatter: function(value, row, index) {
						var e = '<a class="btn btn-primary btn-sm" href="javascript:void(0)" mce_href="#" title="生成代码" onclick="code(\'' +
							row.tableName +
							'\')">生成代码</a> '; //<i class="fa fa-code"></i>
						return e;
					}
				}
			]
		});
}

function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

var tableColumns;
var makeCodeLayer;

function code(tableName) {
	$('#tableName').val(tableName);
	tableColumns = [];
	//获取表信息
	$.get(prefix + "/table/" + tableName, function(res) {
		tableColumns = res.columns;
		$('#model').val(res.model);
		$('#className').val(res.className);
		var condition_html = "";
		var result_html = "";
		$.each(res.columns, function(i, item) {
			condition_html += '<div class="checkbox-inline editCheckbox com-flex">';
			condition_html += '<input name="conditionCheck_' + i + '" id="conditionCheck_' + i + '" type="checkbox" value="1">';
			condition_html += '<input name="conditionVal_' + i + '" class="form-control" type="text" value="' + item.columnComment + '" autocomplete="off">';
			condition_html += '<label for="conditionCheck_' + i + '" title="' + item.columnName + '">' + item.columnName + '</label>';
			condition_html += '</div>';
			result_html += '<div class="checkbox-inline editCheckbox com-flex">';
			result_html += '<input name="resultCheck_' + i + '" id="resultCheck_' + i + '" type="checkbox" value="1" checked>';
			result_html += '<input name="resultVal_' + i + '" class="form-control" type="text" value="' + item.columnComment + '" autocomplete="off">';
			result_html += '<label for="resultCheck_' + i + '" title="' + item.columnName + '">' + item.columnName + '</label>';
			result_html += '</div>';
		});
		$('#conditionColumnsBox').html(condition_html);
		$('#resultColumnsBox').html(result_html);
		makeCodeLayer = layer.open({
			type: 1,
			title: '生成代码',
			//maxmin : true,
			shadeClose: false,
			area: ['1000px', '560px'],
			content: $('#makeCodeLayer')
		});
	});

	//	$.get(prefix + "/table/" + tableName,function(data){
	//		var params = {
	//				"conditionColumns":data.columns,
	//				"resultColumns": data.columns,
	//				"className": data.className,
	//				"model": data.model,
	//				"tableName": tableName
	//			};
	//		$.ajax({
	//		    method: "POST",
	//		    url: prefix + "/code",
	//		    contentType: 'application/json',
	//		    data:JSON.stringify(params),
	//		    success: function( data ) {
	//		    	location.href = prefix + "/file/" + data.data;
	//		   }
	//		});
	//	});

}
//保存生成代码
$('#saveMakeCode').on('click', function() {
	var arr = $('#codeForm').serializeArray();
	var formData = {};
	$.each(arr, function(i, item) {
		formData[item.name] = item.value;
	});
	if(!formData.model) {
		layer.msg("未填写模块名称");
		return false;
	} else if(!formData.className) {
		layer.msg("未填写实体名称");
		return false;
	}
	var params = {};
	params['className'] = formData['className'];
	params['model'] = formData['model'];
	params['tableName'] = formData['tableName'];
	params['conditionColumns'] = [];
	params['resultColumns'] = [];
	$.each(tableColumns, function(j, jtem) {
		var _jtem = JSON.stringify(jtem);
		if(formData['conditionCheck_' + j]) {
			var obj = JSON.parse(_jtem);
			obj['columnComment'] = formData['conditionVal_' + j];
			params['conditionColumns'].push(obj)
		}
		if(formData['resultCheck_' + j]) {
			var _obj = JSON.parse(_jtem);
			_obj['columnComment'] = formData['resultVal_' + j];
			params['resultColumns'].push(_obj)
		}
	});
	makeCodefn(params);
	return false;
})

function makeCodefn(params) {
	$.ajax({
		cache: true,
		type: "POST",
		url: prefix + "/code",
		data: JSON.stringify(params),
		async: false,
		contentType: 'application/json;charset=UTF-8',
		error: function(request) {
			layer.alert("Connection error");
		},
		success: function(res) {
			if(res.code == 0) {
				layer.close(makeCodeLayer);
				layer.msg("操作成功");
				location.href = prefix + "/file/" + res.data;
			} else {
				layer.alert(res.msg)
			}
		}
	});
}

function batchCode() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if(rows.length == 0) {
		layer.msg("请选择要生成代码的表");
		return;
	}
	var tables = new Array();
	// 遍历所有选择的行数据，取每条数据对应的ID
	$.each(rows, function(i, row) {
		tables[i] = row['tableName'];
	});
	$.get(prefix + "/batchCode?tables=" + JSON.stringify(tables).replace('[', '%5B').replace(']', '%5D'), function(data) {
		location.href = prefix + "/file/" + data.data;
	});

}

function edit() {
	console.log('打开配置页面');
	layer.open({
		type: 2,
		title: '增加',
		//maxmin : true,
		shadeClose: false,
		area: ['800px', '520px'],
		content: prefix + '/edit'
	});
}