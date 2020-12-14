var productId;
var wxconfig;//用户储存微信配置信息
var fximg;//分享时显示的图标
var fxtitle; // 分享标题
var fxdesc; // 分享描述
var fxlink=window.location.href.split("#")[0] // 分享链接
$(function(){
	param = GetRequest();
	if (param && param.id != undefined) {
		productId = param.id;
		info();
	}
	$("#setTab").setTab();
	 $("#denxahade").bind("click",function(){
     	$("#denxahade").slideUp();
     })
     invit();
})
function info(){
	var params={
			id:productId
	}
	ajaxPostFunctionAsyfalse("../mpProduct/info", params, function(data){
		if(data.code==0){
			setValue(data.data);
		}else if(data.code==-1){
			mui.toast("抱歉,您非机构成员，无法查看此产品详情");
			setTimeout(function(){
				window.location.href="../mpuser/p_info"
			},800)
		}else{
			mui.toast(data.data);
		}
	})
}

function setValue(product){
	$("#fdTitle").html(product.fdTitle);
	var wid=$(".imgbox").width();
	var hei=Math.ceil(wid*0.75);
	$("#fdImgUrl").height(hei);
	$("#fdImgUrl").attr("src",product.fdImgUrl);
	$("#fdDetails").html(product.fdDetails);
	$("#fdFeatures").attr("src",product.fdFeatures);
	$("#fdExample").attr("src",product.fdExample);
	$("#fdNotice").attr("src",product.fdNotice);
	$("#fdUrl").attr("href",pdurl(product.fdUrl));
	if(product.fdName){
		$(".shadeTitle").html(product.fdName);
	}else{
		$(".shadeTitle").hide();
	}
}

function pdurl(_str){
	if(_str.indexOf("http")<0){
		return 'http://'+_str;
	}else{
		return _str
	}
}
function invit(){
	
	setWxconfig();
    if(wxconfig==null||wxconfig==undefined){
        mui.toast("网络延迟请刷新页面")//获取微信配置信息异常
        return;
    }else{
        var __shareData = {
            title: $("#fdTitle").html(), // 分享标题
            desc: $("#fdDetails").html(), // 分享描述
            link: window.location.href, // 分享链接
            imgUrl:$("#fdImgUrl").attr("src"), //分享时显示的小图标
            success: function() {
            }
        };
        function initShare() {
            //分享到聊天
            wx.onMenuShareAppMessage({
                title: __shareData.title, // 分享标题
                desc: __shareData.desc, // 分享描述
                link: window.location.href, // 分享链接
                imgUrl:$("#fdImgUrl").attr("src"), //分享时显示的小图标
                success: __shareData.success
            });
            //分享朋友圈
            wx.onMenuShareTimeline({
                title: __shareData.title, // 分享标题
                desc: __shareData.desc, // 分享描述
                link:window.location.href, // 分享链接
                imgUrl:$("#fdImgUrl").attr("src"), //分享时显示的小图标
                success: __shareData.success
            });
        }
        wx.ready(function() {
            initShare();
        });
       
    }
}
function onpensild(){
	  $("#denxahade").slideDown();
}
//设置微信配置
function setWxconfig(){
	wxconfig = null;
	ajaxPostFunctionAsyfalse("../mp/infoJssdkConf",{url:fxlink},function(data){
		if(data.code==0){
    		wxconfig = data.data.wxconfig;
    		if(wxconfig!=null){
	    		 wx.config({
	                 debug: false,
	                 appId: wxconfig.appId,
	                 timestamp:wxconfig.timestamp,
	                 nonceStr:wxconfig.nonceStr,
	                 signature:wxconfig.signature,
	                 jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage']
	             });
    		}
		}
	});
}