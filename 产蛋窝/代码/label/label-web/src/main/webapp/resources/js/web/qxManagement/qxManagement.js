/**
 * Created by Kuno on 2018/1/11.
 */
var itemPage=0;//每页条数
$(function(){
    itemPage=10;
    initPage();
    getListByPage();
})
window.onload = function() {
    getListByPage(1);
}
/*
 * 获取列表 pageindex ： 页码
 */
function getListByPage(pageindex){
    $(".loadrtkuno").css("display","block")
    var contentvaguec= $("#contentvague").val();//搜索内容
    var params={
        "iStart":pageindex,
        "iCount":itemPage,
        "sTitleName":contentvaguec
    };

    $(".loadrtkuno").css("display","block")//加载动画
    ajaxPostFunctionAsytrue('../jsp/action',params,function(data) {
        eval('var json=' + data);
        if (json.sCode == '00') {
            $("#umantable").html("")
            var itemCount = json.sResult.iCount;//总条数
            itemCount = parseInt(itemCount);
            updatePage(itemCount);//更新总条数
            selectPage(pageindex);//当前总条数
            var tableHtml = '';
            var rs=json.sResult.Votes;
            if(rs.lenght>0){
                for (var i = 0; i < rs.length; i++) {
                    tableHtml+='<tr><td><label class="checkbox m-n i-checks"><input type="checkbox" name="usman" id="'+rs[i].id+'"><i></i></label> </td>';
                    tableHtml+='<td>'+rs[i].id+'</td>';
                    tableHtml+='<td>'+rs[i].name+'</td>';
                    tableHtml+='<td>'+rs[i].tel+'</td>';
                    tableHtml+='<td>'+rs[i].type+'</td>';
                    tableHtml+='<td>'+rs[i].company+'</td>';
                    tableHtml+='<td>'+rs[i].address+'</td>';
                    tableHtml+='<td>'+rs[i].status+'</td>';
                    tableHtml+='<td><a href="#" class="colork">查询</a><a href="#" class="colork ">编辑</a><a href="#" class="colork">继承</a><a href="#" class="colork">冻结</a></td></tr>';
                }
            }
            setTimeout(function (){
                $(".loadrtkuno").css("display","none")//隐藏加载动画
                $("#umantable").html(tableHtml)
            },500)
        }
    })
}
function addUser(){
    window.location.href="userManagementadd.html"
}