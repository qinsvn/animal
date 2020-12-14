/**
 * 删除的id数组
 */
var flag_kuno=true;
var delIds = [];
$(function(){
	itemPage=10;
    initPage();
	//加载数据
    pageIndex = 1;
	loadData(pageIndex);
	//取消
	$("#cancel").click(function(){
		hideModal();
	})
	//删除
	$("#del_role").click(function(){
		delIds = [];
		$(".role_sel:checkbox:checked").each(function(){
			delIds.push(parseInt($(this).val()));
		})
		if(delIds.length == 0){
			set_message("auto-close-dialogBox","提示","请选择需要删除的角色");
			return;
		}
		defind_text("auto-close-dialogBox","提示","确定删除选中的角色吗？",deleteRole,'');
		
	})
	//查询
	$("#seach_btn").click(function(){
		loadData(1);
	});
})

/**
 * 加载数据方法
 */
function loadData(pageNo){
	var paramet = {
			pagenum:pageNo,
			pagesize:itemPage,
			keyword:$('#seach_name').val()
	}
	$(".loadrtkuno").css("display","block")//加载动画
	ajaxPostFunctionAsytrue("list",paramet,loadCallBack);
}

/**
 * 回调函数
 * @param data
 */
function loadCallBack(data){
	if(data.code == 0){
		
		itemCount = parseInt(data.data.total);
        updatePage(data.data.total);//更新总条数
        selectPage(pageIndex);//当前总条数
		
		var rows = data.data.rows;
		var blog = '<thead><tr>'
                    +'<th style="width:20px;"><label class="checkbox m-n i-checks"><input type="checkbox"><i></i></label></th>'
                    +'<th class="th-sortable hidden" data-toggle="class">id</th>'
                    +'<th>角色名称</th>'
                    +'<th>角色描述</th>'
                    +'<th>创建时间</th>'
                    +'<th width="160">操作</th>'
                    +'</tr></thead>';
		for(var i = 0; i < rows.length; i++){
			var obj = rows[i];
			blog += '<tr>';
				  if(obj.id !=1){
					  blog +='<td><label class="checkbox m-n i-checks"><input type="checkbox" class="role_sel" name="usman" value="'+obj.id+'"><i></i></label>';
				  }else{
					  blog +='<td> ';
				  }
				  blog +='</td>'
                    +'<td class="hidden cid">'+ obj.id +'</td>'
                    +'<td>'+ (obj.fdName != null ? obj.fdName : "") +'</td>'
                    +'<td>'+ (obj.fdDesp != null ? obj.fdDesp : "") +'</td>'
                    +'<td>'+ (obj.fdTime != null ? getTimeFormat(obj.fdTime,"yyyy-MM-dd HH:mm:ss") : "") +'</td>'
                    +'<td>';
                    if(obj.id !=1){
                    	blog +='<a href="p_alter?id='+obj.id+'" class="colork" data-toggle="modal">编辑</a>';
                    }else{
                    	blog +='不可编辑';
                    }
                    blog +='</td>'
                    +'</tr>';
		}
		$("#datas").html("");
		$("#datas").append(blog);
		
		setTimeout(function (){
            $(".loadrtkuno").css("display","none")//隐藏加载动画
        },500)
	}else{
		set_message("auto-close-dialogBox","提示","无相关数据");
	}
}



/**
 * 翻页回调
 * @param pageNo
 */
function getListByPage(pageNo){
	loadData(pageNo);
	pageIndex = pageNo;
}

function hideModal(){
	$('#modal-form').modal('hide');
}

/**
 * 删除机构
 * @param ids
 */
function deleteRole(){
	try {
		var parameter = {
				ids : delIds.toString()
		}
		
		$(".loadrtkuno").css("display", "block")//加载动画
		ajaxPostFunctionAsytrue("delete", parameter,
				function(data) {
					if (data.code == 0) {
						loadData(1);
						pageIndex = 1;
						set_message("auto-close-dialogBox", "提示", "删除成功");
					} else {
						set_message("auto-close-dialogBox", "提示", data.message);
					}
					$(".loadrtkuno").css("display", "none")//加载动画
				});
	} catch (e) {
		set_message("auto-close-dialogBox", "提示", "删除失败");
	}
}


function addUser(){
	window.location.href = "p_info";
}