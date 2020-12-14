/**
 * Created by Kuno on 2018/1/15.
 */
var birthdayflage=true;
var dtpicker;
$(function(){
	
  /*  var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true
    });*/
   /* $(".shade").bind("click",function(){
        $(".shadeshenji").slideUp()
        $(".shade").slideUp();
    })*/
    //时间选择器初始化
	$("#birthday").bind("click",function(){
		var nowdata="";
		if($("#birthday").val()!=""){
			nowdata=$("#birthday").val()
		}
		dtpicker= new mui.DtPicker({
	        type: "date",//设置日历初始视图模式
	        beginDate: 1880,//设置开始日期
	        endDate:new Date(),//设置结束日期
	        value:nowdata,
	        labels: ['年', '月', '日'],//设置默认标签区域提示语
	        customData: {
	        }//时间/日期别名
	    })
	    dtpicker.show(function(e) {
	        if(e==undefined){
	            mui.toast("请重新选择");
	            return
	        }
	        $("#birthday").val(e.value)
	    })
	});
    
    getinfo();
    getJf();
    getUserInfo();
 
})
function getinfo(){
    ajaxPostFunctionAsytrue('../mpuser/wealth',{},function(data){
        if(data.code == '0'){
            $(".moneyNum").html(Number(parseFloat(data.data.balance?data.data.balance:0)).toFixed(2));//可提现金额
            //add by jolley 20180412 信积分异步 注释
//            $("#xjf").html(Number(parseFloat(data.data.count?data.data.count:0)).toFixed(2));//信积分
            $("#ljjl").html(Number(parseFloat(data.data.allReward?data.data.allReward:0)).toFixed(2));//累计奖励（元）
            $("#dyjl").html(Number(parseFloat(data.data.monthReward?data.data.monthReward:0)).toFixed(2));//当月奖励（元）
            /* $("#dyjs").html(Number(parseFloat(data.data.balance?data.data.balance:0)).toFixed(2));//当月缴税
            $("#bktx").html(Number(parseFloat(data.data.balance?data.data.balance:0)).toFixed(2));//暂不可提现 */
            
        }
    })
}
//获取用户信息：头像 号码等
function getUserInfo(){
    ajaxPostFunctionAsytrue("./info",{},function(data){
        if(data.code==0){
            //用户头像
        	var customer = data.data;
        	if(customer){
        		if(customer.nickname){
        			$(".usertel").html(customer.nickname);
        		}
        		if(customer.headImgUrl){
        			$(".contentImg").attr("src",customer.headImgUrl);        			
        		}
        		
        		//用户账号类型：个人or机构
        		var usertype="个人";
        		if(customer.fdLeve){
        			if(customer.fdLeve>0){
        				usertype="机构";
        			}else{
        				usertype="个人";
        				$("#update").remobeClass("hide");
        			}
        		}else{
        			$("#update").removeClass("hide");
        			usertype="个人";
        		}
        		$(".jigoutype").html(usertype);
        		
        	}
        	window.sessionStorage.setItem("key", "111");
    		$("#loadingwarp").hide();
        	/*setTimeout(function(){
        		
        	},500)*/
        }

    })
}

function getJf(){
 //add by jolley 20180412 信积分异步
 ajaxPostFunctionAsytrue('../mpuser/postCount',{},function(data){
 	  $("#xjf").html(data.data?data.data:0);//信积分
 });
}


function cencleshade(){
    $(".shadeshenji").slideUp()
    $(".shade").slideUp();
}
//升级为机构
function  jg_saved(){
    var jgcode=$("#jgyqm").val();
    if(jgcode==""){
        mui.toast("请输入机构邀请码");
        return false
    }
    var birthday=$("#birthday").val();
    if(birthday==""){
        mui.toast("请输入生日");
        return false
    }
    if(!$("#checkbox").prop("checked")){
        mui.toast("请阅读并同意服务款项");
        return false
    }
    var params={
        code:jgcode,
        birth:birthday
    }
    //code birth openid
    ajaxPostFunctionAsytrue("./activation",params,function(data){
        if(data.code==0){
            mui.toast("升级成功")
            //重新请求下页面
            window.location.reload();
        }else{
        	mui.toast(data.data);
        }
    })

}
//显示机构邀请码文本
function showshade(){
    if($(".jigoutype").html()=="个人"){
    	
        $(".shadeshenji").slideDown();
        $(".shade").slideDown();
    }
}
//跳转至二维码
function gotoeqcode(){
    window.location.href="./p_info_qrcode"
}
function gotouserInfo(){
	 window.location.href="./p_info_edit"
}
function gotoFans(){
	window.location.href="./p_info_fans"
}
function gotoOrder(){
//	window.location.href="../common/to/order"
		window.location.href="../common/to/_mp_order_orderInfo"
}
function gotoRule(){
	window.location.href="../common/to/_mp_rule_ruleInfo"
}
function gotoWealth(){
	window.location.href="./p_info_wealth"
}
function noContentAlert(){
	mui.toast("敬请期待")
}