var ajaxbg;
var ajaxbginterval;
var ajaxTime=0;
var itemPage=0;
var myconfirm={};
var basePath="";
$(document).ready(function(){
	/*
	 * 设置每页显示的数据量
	 */
	basePath=window.location.href.split("label-web")[0];
	basePath+='label-web';
	itemPage = 10; 
	/*
	 * 设置提示框同意样式
	 * */
	myconfirm={
			title:['提示',"text-align:center;padding: 0;background: #9d887a;color: #fff;"],
			btnAlign: 'c',
			closeBtn: 0,
			resize :false
	};
	/*
	 * 时间格式化
	 */
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1, // month
			"d+" : this.getDate(), // day
			"h+" : this.getHours(), // hour
			"m+" : this.getMinutes(), // minute
			"s+" : this.getSeconds(), // second
			"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
			"S" : this.getMilliseconds()
					// millisecond
		}
		if (/(y+)/.test(format))
			format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			for ( var k in o)
				if (new RegExp("(" + k + ")").test(format))
						format = format.replace(RegExp.$1,RegExp.$1.length == 1 ? o[k]: ("00" + o[k]).substr(("" + o[k]).length));
			return format;
	}
	Date.getDayOfMonth = function (y, Mm) {  
	    if (typeof y == 'undefined') { y = (new Date()).getFullYear(); }  
	    if (typeof Mm == 'undefined') { Mm = (new Date()).getMonth(); }  
	    var Feb = (y % 4 != 0) ? 28 : (y % 100 != 0) ? 29 : (y % 400 == 0) ? 29 : 28;  
	    var aM = new Array(31, Feb, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);  
	    return  aM[Mm];  
	};  
	Date.getDateOfPreMonth = function (dt) {   
	    if (typeof dt == 'undefined') { dt = (new Date()); }  
	    var y = (dt.getMonth() == 0) ? (dt.getFullYear() - 1) : dt.getFullYear();  
	    var m = (dt.getMonth() == 0) ? 11 : dt.getMonth() - 1;  
	    var preM = Date.getDayOfMonth(y, m);  
	    var d = (preM < dt.getDate()) ? preM : dt.getDate();  
	    return new Date(y, m, d);  
	};  
	Date.getDateOfNextMonth = function (dt) {  
	    if (typeof dt == 'undefined') { dt = (new Date()); }  
	    var y = (dt.getMonth() == 11) ? (dt.getFullYear() + 1) : dt.getFullYear();  
	    var m = (dt.getMonth() == 11) ? 0 : dt.getMonth() + 1;  
	    var preM = Date.getDayOfMonth(y, m);  
	    var d = (preM < dt.getDate()) ? preM : dt.getDate();  
	    return new Date(y, m, d);  
	};  
	/*
	 * 统一加载条内容加载到页面中
	 */
//	var bgHtml="<div id=\"ld-background\" class=\"ld-background\" style=\"display: none; \"></div> <div id=\"ld-progressBar\" class=\"ld-progressBar\" style=\"display: none; \"><img src=\"../images/progressCircle.gif\" style=\"width:24px;height:24px;\"></div>";
//	$('body').append(bgHtml);
//	ajaxbg=$("#ld-background,#ld-progressBar");
//	ajaxbg.hide();
	$(window).resize(function () { 
		resizeContent();
	});
	
})

/*
 * ajax调用时显示加载条
 */
$(document).ajaxStart(function(){
	//ajaxbg.show(); 
});

/*
 * ajax调用结束隐藏加载条
 */
$(document).ajaxStop(function(){
	//ajaxbg.hide();
	
});


/*
 * 基础ajax调用方法 
 */
/*function ajaxFunction(aurl,aparams,acallback)
{
	if(aparams==null||(!aparams))
	{
		aparams={};
	}
	aparams.uni=localStorage["yy_as"];
	$.ajax({
		url: aurl,
		dataType : "json",
		contentType: "application/json",
		data : aparams,
		type : "post",
		success:function(data)
		{
			var type=typeof data;
			if(type=="string"){
				eval('var json='+data);
			}
			else if(type=="object"){
				var json=data;
			}
			if(json.sCode=="A103")
			{
				
			}
			acallback(json);
		},
		failure:function(data){
			alert(data);
		}
	});
}*/


/**
 * 基础ajaxGet调用方法
 * @param aurl 地址
 * @param adata 请求参数
 * @param acallback 回调方法
 * @param adataType 请求数据类型，默认json
 * @param acontentType 请求数据格式类型，默认application/x-www-form-urlencoded，可设置为application/json
 * @param uni 请求唯一标识
 */
function ajaxGetFunctionAsyflase(aurl,adata,acallback,adataType,acontentType,uni)
{
	if(adata==null||(!adata))
	{
		adata={};
	}
	if(adataType==null||(!adataType))
	{
		adataType='json';
	}
	if(acontentType==null||(!acontentType))
	{
		acontentType='application/x-www-form-urlencoded';
	}
	
	$.ajax({
		url: aurl,
		dataType : adataType,
		contentType: acontentType,
		data : adata,
		async: false,
		type : "get",
		success:function(data)
		{
			var type=typeof data;
			if(type=="string"){
				eval('var json='+data);
			}
			else if(type=="object"){
				var json=data;
			}
			
			if(acallback!=null&&acallback){
				if(uni==null||(!uni)){
					acallback(json);
				}
				else {
					acallback(uni,json);
				}
			}
		},
		failure:function(data){
			alert(data);
		}
	});
}
/**
 * 基础ajaxPost调用方法
 * @param aurl 地址
 * @param adata 请求参数
 * @param acallback 回调方法
 * @param adataType 请求数据类型，默认json
 * @param acontentType 请求数据格式类型，默认application/x-www-form-urlencoded，可设置为application/json
 * @param uni 请求唯一标识
 */
function ajaxPostFunctionAsyfalse(aurl,adata,acallback,adataType,acontentType,uni)
{
	if(adata==null||(!adata))
	{
		adata={};
	}
	if(adataType==null||(!adataType))
	{
		adataType='json';
	}
	if(acontentType==null||(!acontentType))
	{
		acontentType='application/x-www-form-urlencoded';
	}
	
	$.ajax({
		url: aurl,
		dataType : adataType,
		contentType: acontentType,
		data : adata,
		async: false,
		type : "post",
		success:function(data)
		{
			var type=typeof data;
			if(type=="string"){
				eval('var json='+data);
			}
			else if(type=="object"){
				var json=data;
			}
			
			if(acallback!=null&&acallback){
				if(uni==null||(!uni)){
					acallback(json);
				}
				else {
					acallback(uni,json);
				}
			}
		},
		failure:function(data){
			alert(data);
		}
	});
}
/**
 * 基础ajaxPost调用方法
 * @param aurl 地址
 * @param adata 请求参数
 * @param acallback 回调方法
 * @param adataType 请求数据类型，默认json
 * @param acontentType 请求数据格式类型，默认application/x-www-form-urlencoded，可设置为application/json
 * @param uni 请求唯一标识
 */
function ajaxPostFunctionAsytrue(aurl,adata,acallback,adataType,acontentType,uni)
{
	if(adata==null||(!adata))
	{
		adata={};
	}
	if(adataType==null||(!adataType))
	{
		adataType='json';
	}
	if(acontentType==null||(!acontentType))
	{
		acontentType='application/x-www-form-urlencoded';
	}
	
	$.ajax({
		url: aurl,
		dataType : adataType,
		contentType: acontentType,
		data : adata,
		type : "post",
		success:function(data)
		{
			var type=typeof data;
			if(type=="string"){
				eval('var json='+data);
			}
			else if(type=="object"){
				var json=data;
			}
			
			if(acallback!=null&&acallback){
				if(uni==null||(!uni)){
					acallback(json);
				}
				else {
					acallback(uni,json);
				}
			}
		},
		failure:function(data){
			alert(data);
		}
	});
}
/**
 * 基础ajaxGet调用方法
 * @param aurl 地址
 * @param adata 请求参数
 * @param acallback 回调方法
 * @param adataType 请求数据类型，默认json
 * @param acontentType 请求数据格式类型，默认application/x-www-form-urlencoded，可设置为application/json
 * @param uni 请求唯一标识
 */
function ajaxGetFunctionAsytrue(aurl,adata,acallback,adataType,acontentType,uni)
{
	if(adata==null||(!adata))
	{
		adata={};
	}
	if(adataType==null||(!adataType))
	{
		adataType='json';
	}
	if(acontentType==null||(!acontentType))
	{
		acontentType='application/x-www-form-urlencoded';
	}
	
	$.ajax({
		url: aurl,
		dataType : adataType,
		contentType: acontentType,
		data : adata,
		type : "get",
		success:function(data)
		{
			var type=typeof data;
			if(type=="string"){
				eval('var json='+data);
			}
			else if(type=="object"){
				var json=data;
			}
			
			if(acallback!=null&&acallback){
				if(uni==null||(!uni)){
					acallback(json);
				}
				else {
					acallback(uni,json);
				}
			}
		},
		failure:function(data){
			alert(data);
		}
	});
}

//主框高度
function resizeContent(){
	var itemHeight = $('#autoHeightContent').find(".my-left-side").innerHeight();
	$('#autoHeightContent').css('height',itemHeight+"px");
}
//获取地址栏参数
function getUrlParam(name)
{
var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
var r = window.location.search.substr(1).match(reg);  //匹配目标参数
if (r!=null) return unescape(r[2]); return null; //返回参数值
} 

function GetRequest() {
	 var url=window.location.href;
	 var requeststr=url.split('?')[1];
	 var theRequest=new Object();
	 if(requeststr!=undefined)
	 {
		 var requestparams=requeststr.split('&');
		 for(var i=0;i<requestparams.length;i++)
		 {
			 var key=requestparams[i].split("=")[0];
			 var kvalue=requestparams[i].split("=")[1];
			 kvalue=decodeURI(kvalue);
			 theRequest[key]=decodeURI(kvalue);
		 }
	 }
	 return theRequest;
}

function getTimeFormat(time, format){
    var t = new Date(time);
    var tf = function(i){return (i < 10 ? '0' : '') + i};
    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
        switch(a){
            case 'yyyy':
                return tf(t.getFullYear());
                break;
            case 'MM':
                return tf(t.getMonth() + 1);
                break;
            case 'mm':
                return tf(t.getMinutes());
                break;
            case 'dd':
                return tf(t.getDate());
                break;
            case 'HH':
                return tf(t.getHours());
                break;
            case 'ss':
                return tf(t.getSeconds());
                break;
        }
    })
}


