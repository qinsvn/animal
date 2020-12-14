var prefix = top.ctx + "/animal/inputs"
$(function () {
    laydate.render({
        elem: '#con_startBirthday' //指定元素
    });
    laydate.render({
        elem: '#con_endBirthday' //指定元素
    });
    load();
});

function load() {
    // $('#exampleTable').on('post-body.bs.table', function (e,data){
    //     alert(JSON.stringify(data));
    // });
    $('#exampleTable').bootstrapTable(
        {
            method: 'get', // 服务器数据的请求方式 get or post
            url: prefix + "/list", // 服务器数据的加载地址
            //	showRefresh : true,
            //	showToggle : true,
            //	showColumns : true,
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
            //search : true, // 是否显示搜索框
            showColumns: false, // 是否显示内容下拉框（选择显示的列）
            sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
            queryParams: function (params) {
                return {
                    //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                    limit: params.limit,

                    num: $('#con_num').val(),
                    batchNum: $('#con_batchNum').val(),
                    typeName: $('#con_typeName').val(),
                    varietyName: $('#con_varietyName').val(),
                    status: $('#con_status').val(),
                    parentNum: $('#con_parentNum').val(),
                    pparentNum: $('#con_pparentNum').val(),
                    startBirthday: $('#con_startBirthday').val(),
                    endBirthday: $('#con_endBirthday').val(),

                    offset: params.offset
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
            onLoadSuccess: function (data) {
                setTimeout(uploadPhone(),1000);
            },
            columns: [
                {
                    checkbox: true
                },
                {
                    field: 'num',
                    title: '编号',
                    formatter: function (value, row, index) {
                        var e = '<a href="javascript:void(0)" title="编号" onclick="edit(\''
                            + row.id
                            + '\')">' + value + '</a> ';
                        return e;
                    }
                },
                {
                    field: 'batchNum',
                    title: '批次'
                },
                {
                    field: 'remark2',//使用了备用字段remark2存储头像图片位置
                    title: '头像/照片',
                    formatter: function (value, row, index) {
                        if (value == null || value == '') {
                            return '暂无   ' + '<a href="javascript:void(0)" class="uploadButt" data-id="'+row.id+'">立即上传</a>';
                        } else {
                            return '<a href="javascript:void(0)" onclick="showPhone(\'' + value + '\')">查看照片</a>' + '&nbsp;&nbsp;<a href="javascript:void(0)" class="uploadButt" data-id="'+row.id+'">重新上传</a>';
                        }
                    }
                },
                {
                    field: 'typeName',
                    title: '种类',
                    formatter: function (value, row, index) {
                        return top.sys.dictName('animal_type', value);
                    }
                },
                {
                    field: 'varietyName',
                    title: '品种',
                    formatter: function (value, row, index) {
                        return top.sys.dictName('animal_varieties', value);
                    }
                },
                {
                    field: 'birthday',
                    title: '出生日期'
                },
                {
                    field: 'initWeight',
                    title: '最初体重'
                },
                {
                    field: 'curWeight',
                    title: '目前体重'
                },
                {
                    field: 'spaceName',
                    title: '入住场地'
                },
                {
                    field: 'deptId',
                    title: '所属养殖户',
                    formatter: function (value, row, index) {
                        return top.sys.deptName(value);
                    }
                },
                {
                    field: 'supplierName',
                    title: '供应商'
                },
                {
                    field: 'status',
                    title: '状态',
                    formatter: function (value, row, index) {
                        return top.sys.dictName('inputs_status', value);
                    }
                },
                {
                    field: 'parentId',
                    title: '上代',
                    formatter: function (value, row, index) {
                        if (value != null && value != -1) {
                            return '<a href="javascript:void(0)" title="点击查看详细信息" onclick="editInTitle(' + value + ',\'上代信息\')">查看</a>';
                        } else {
                            return '无记录';
                        }

                    }
                },
                {
                    field: 'pparentId',
                    title: '上上代',
                    formatter: function (value, row, index) {
                        if (value != null && value != -1) {
                            return '<a href="javascript:void(0)" title="点击查看详细信息" onclick="editInTitle(' + value + ',\'上上代信息\')">查看</a>';
                        } else {
                            return '无记录';
                        }

                    }
                },
                {
                    field: 'createUser',
                    title: '录入人员',
                    formatter: function (value, row, index) {
                        return top.sys.userName(value);
                    }
                },
                {
                    field: 'createTime',
                    title: '入栏时间'
                },
                {
                    title: '操作',
                    field: 'id',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="javascript:void(0)" mce_href="javascript:void(0)" title="编辑" onclick="edit(\''
                            + row.id
                            + '\')">编辑</a> ';
                        var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="javascript:void(0)" title="删除"  mce_href="#" onclick="remove(\''
                            + row.id
                            + '\')">删除</a> ';
                        var f = '<a class="btn btn-success btn-sm" href="javascript:void(0)" title="备用"  mce_href="#" onclick="resetPwd(\''
                            + row.id
                            + '\')"><i class="fa fa-key"></i></a> ';
                        return e + d;
                    }
                }]
        });
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function uploadPhone() {
    layui.use('upload', function () {
        var upload = layui.upload;
        //执行实例
        var uploadInst = upload.render({
            elem: '.uploadButt', //绑定元素
            url: top.ctx + '/common/sysFile/upload', //上传接口
            size: 1000,
            accept: 'file',
            done: function (r) {
                if (r.code == 0) {
                    var id =  $(this.item).data('id')
                    $.ajax({
                        cache: true,
                        type: "POST",
                        url: top.ctx + "/animal/inputs/updatePhone",
                        data: {id: id, remark2: r.fileName},//
                        async: false,
                        error: function (request) {
                            layer.alert("Connection error");
                        },
                        success: function (data) {
                            if (data.code == 0) {
                                reLoad();
                                layer.msg("上传成功");
                            } else {
                                layer.alert(data.msg)
                            }
                        }
                    });
                }
            },
            error: function (r) {
                layer.msg(r.msg);
            }
        });
    });
}

function showPhone(url) {
    //相册的json数据
    var json = {
        "start": 0, //初始显示的图片序号，默认0
        "data": [   //相册包含的图片，数组格式
            {
                "alt": "照片",
                "src": top.ctx + url
                // "src": top.ctx + "/files/6d160145-c044-4c1d-ae47-aa73ecd17396.jpg",
            }
        ]
    }
    //显示相册图片
    layer.photos({
        photos: json
        , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
    });
}

//重置
function reset() {
    $("#con_id").val('');
    $("#con_num").val('');
    $("#con_parentNum").val('');
    $("#con_pparentNum").val('');
    $("#con_startBirthday").val('');
    $("#con_endBirthday").val('');
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
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add' // iframe的url
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}


function editInTitle(id, title) {
    var option = {
        type: 2,
        title: title,
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id,// iframe的url
        success: function (v, i) {
            var iframeName = $('.layui-layer-content').find('iframe').attr('name');
            $(window.frames[iframeName].document).find('button[type="submit"]').css('display', 'none');
        }
    };
    layer.open(option);
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
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
    var g = true;
    $.each(rows, function (i, row) {
        if (row['status'] != '0') {
            layer.msg("只能检疫饲养中的投入品");
            g = false;
            return;
        }
    });
    if (!g) {
        return;
    }

    layer.open({
        type: 2,
        title: '对' + rows.length + '件投入品进行检疫',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '460px'],
        content: top.ctx + "/animal/immuneRecord/add" // iframe的url
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
    var g = true;
    $.each(rows, function (i, row) {
        if (row['status'] != '0') {
            layer.msg("只能检查饲养中的投入品");
            g = false;
            return;
        }
    });
    if (!g) {
        return;
    }
    layer.open({
        type: 2,
        title: '对' + rows.length + '件投入品进行检查',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '460px'],
        content: top.ctx + "/animal/checkRecord/add" // iframe的url
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
    var g = true;
    $.each(rows, function (i, row) {
        if (row['status'] != '0') {
            layer.msg("只能售卖饲养中的投入品");
            g = false;
            return;
        }
    });
    if (!g) {
        return;
    }

    layer.confirm("确认要售卖选中的'" + rows.length + "'件产品吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: top.ctx + "/animal/saleRecord/sale",
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

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
    var g = true;
    $.each(rows, function (i, row) {
        if (row['status'] != '0') {
            layer.msg("只能检查饲养中的投入品");
            g = false;
            return;
        }
    });
    if (!g) {
        return;
    }
    layer.open({
        type: 2,
        title: '将' + rows.length + '件投入品进行加入耗损',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '360px'],
        content: top.ctx + "/animal/substandardData/add" // iframe的url
    });
}


function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}


function nconception() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择录为未受孕的投入品数据");
        return;
    }
    layer.confirm("确认要将'" + rows.length + "'条数据录为未受孕吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            $.ajax({
                cache: true,
                type: "POST",
                url: top.ctx + "/animal/inputs/update",
                data: {id: row['id'], remark1: '0'},
                async: false,
                error: function (request) {
                    parent.layer.alert("Connection error");
                },
                success: function (data) {
                }
            });

        });
        layer.msg('操作成功');
        reLoad();
    }, function () {

    });

}

function getRows() {
    return $('#exampleTable').bootstrapTable('getSelections');
}