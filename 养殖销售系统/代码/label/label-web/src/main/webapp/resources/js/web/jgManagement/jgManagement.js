var flag_kuno=true;
/**
 * Created by Kuno on 2018/1/11.
 */
var itemPage=0;//ÿҳ����
$(function(){
    itemPage=10;
    initPage();
    getListByPage();
})
window.onload = function() {
    getListByPage(1);
}
/*
 * ��ȡ�б� pageindex �� ҳ��
 */
function getListByPage(pageindex){
    $(".loadrtkuno").css("display","block")
    var contentvaguec= $("#contentvague").val();//��������
    var params={
        "iStart":pageindex,
        "iCount":itemPage,
        "sTitleName":contentvaguec
    };

    $(".loadrtkuno").css("display","block")//���ض���
    ajaxPostFunctionAsytrue('../jsp/action',params,function(data) {
        eval('var json=' + data);
        if (json.sCode == '00') {
            $("#umantable").html("")
            var itemCount = json.sResult.iCount;//������
            itemCount = parseInt(itemCount);
            updatePage(itemCount);//����������
            selectPage(pageindex);//��ǰ������
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
                    tableHtml+='<td><a href="#" class="colork">��ѯ</a><a href="#" class="colork ">�༭</a><a href="#" class="colork">�̳�</a><a href="#" class="colork">����</a></td></tr>';
                }
            }
            setTimeout(function (){
                $(".loadrtkuno").css("display","none")//���ؼ��ض���
                $("#umantable").html(tableHtml)
            },500)
        }
    })
}
function addUser(){
    window.location.href="userManagementadd.html"
}