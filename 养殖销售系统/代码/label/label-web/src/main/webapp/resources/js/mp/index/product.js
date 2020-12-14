var producthavedata=false;
var productgetlistdata='';
var productpageindex=1;
var count0 = 0;
var productPageNum=0;
var swiper1;
$(function(){
	mui('body').on('tap','a',function(){document.location.href=this.href;});
	mui("#productsUl").on("tap","li .newpadding",function(e){
       var _id = $(this).attr("dataid");
		info(_id);
    });
	
	swiper1 = new Swiper('.swiper1', {
	    pagination: '.swiper-product-pagination',
        paginationClickable: true,
        spaceBetween: 10,
		  autoplayDisableOnInteraction : false,
		 autoplay :3000,
	    speed:300,
		observer:true,
	    observeParents:true
	    
	
	});
	/*getCarousel();*/
	
	//获取list轮播图
})

function getCarousel(){
	//请求成功
	var params = {
			
	}
	ajaxPostFunctionAsyfalse("../mpProduct/carousellist",params,function(data){
		if(data.code==0){
			if(data.data){
				var divHtmls="";
				var listObj = data.data.fdContent;
				listObj = JSON.parse(listObj);
				var imgwith=$(".swiper-product-container").width();
				var imgHeight=Math.ceil(imgwith/2);
				for(var i=0; i<listObj.length; i++){
					var obj = listObj[i];
					divHtmls+='<div class="swiper-slide"><a href="'+obj.url+'">';
					if(obj.img){
						divHtmls+='<img src="'+obj.img+'" class="topWarpImg"  style="height:'+imgHeight+'px">';
					}else{
						divHtmls+='<img src="../images/mp/index/swiperdemo.png" class="topWarpImg"  style="height:'+imgHeight+'px">';
					}
					divHtmls+='</a></div>';
				}
				$("#productCarousel").empty();
				$("#productCarousel").append(divHtmls);
			
			}
		}
	})
}
function pullupRefresh0() {
    setTimeout(function() {
        mui('#productrefresh').pullRefresh().endPullupToRefresh(); //参数为true代表没有更多数据了。
   	var cells = document.body.querySelectorAll('.getdatali0');
    	if(cells.length==0){
    		 getlist0(productpageindex);
    	}else{
    		productpageindex++;
    		getlist0(productpageindex);
    	}
       	if(producthavedata){
       		$(".mui-table-view0").empty();
       		$(".mui-table-view0").append(productgetlistdata);
       	}else{
       		/* mui.toast("没有更多内容了哦") */
       	}
        
    }, 1500);
}

/**
 * 下拉刷新具体业务实现
 */
function pulldownRefresh0() {
    setTimeout(function() {
        mui('#productrefresh').pullRefresh().endPulldownToRefresh();
        getlist_shux();
    }, 1500);
}

function getlist0(pageNum){
	producthavedata=false;
//	var nowurl=window.location.href.split("label-web")[0]+"label-web";
	var params={
		pageNum:1,
		pageSize:pageNum*10
	}
	productPageNum=pageNum;
	ajaxPostFunctionAsyfalse($('#basePath').val()+"/mp/mpProduct/list",params,function(data){
		if(data.code==0){
			var rows=data.data.list;
			var liHtml='';
			if(rows.length>0){
				//计数器，循环2次或i==rows.length时添加一个li
				var count=0;
				for(var i=0;i<rows.length;i++){
					
					if(i % 2==0){//单数
						count+=1;
						liHtml += '<li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left  getdatali0 am-padding-top-0 am-padding-bottom-0">';
						liHtml+='<div class="am-u-sm-6 am-list-thumb borright1 newpadding"  dataid="'+rows[i].id+'">';
						liHtml+='<h3 class="am-list-item-hd mb10">';
						liHtml+='<div class="listTitle fdkunoTitle">'+rows[i].fdTitle+'</div>';
						liHtml += '</h3>';
						liHtml+='<div class="am-u-sm-7 am-list-main infotoplistdiv am-padding-0">';
						liHtml+='<div class="listTitle fskuno2">'+rows[i].fdDetails+'</div>';
						liHtml += '</div>';
						liHtml+='<div class="am-u-sm-5 am-list-main am-text-center am-padding-0" style="width: 56px">';
						liHtml+=' <img src="'+rows[i].fdImgUrl+'" style="width: 100%; height: 42px; " alt="产品头图">';
						liHtml+='</div></div>';
					}else{//偶数
						count+=1;
						
						liHtml+='<div class="am-u-sm-6 am-list-thumb newpadding"  dataid="'+rows[i].id+'">';
						liHtml+='<h3 class="am-list-item-hd mb10">';
						liHtml+='<div class="listTitle fdkunoTitle">'+rows[i].fdTitle+'</div>';
						liHtml += '</h3>';
						liHtml+='<div class="am-u-sm-7 am-list-main infotoplistdiv am-padding-0">';
						liHtml+='<div class="listTitle fskuno2">'+rows[i].fdDetails+'</div>';
						liHtml += '</div>';
						liHtml+='<div class="am-u-sm-5 am-list-main am-text-center am-padding-0" style="width: 56px">';
						liHtml+=' <img src="'+rows[i].fdImgUrl+'" style="width: 100%; height: 42px; " alt="产品头图">';
						liHtml+='</div></div>';
					}
					if(count%2==0||i==rows.length-1){
						liHtml+="</li>";
					}
				}
				if(data.data.total<pageNum*10){
					liHtml+='<div class="mui-pull-bottom-pocket mui-block mui-visibility"><div class="mui-pull"><div class="mui-pull-loading mui-icon mui-spinner mui-hidden"></div><div class="mui-pull-caption mui-pull-caption-nomore">没有更多数据了</div></div></div>';
				}
				
				producthavedata=true;
				productgetlistdata=liHtml;
			}else{
				productpageindex--;
				productPageNum--;
			}
		
		}else{
			mui.toast(data.data)
		}
		
	})
}
function info(id){
	
	 window.location.href = $('#basePath').val()+"/mp/mpProduct/p_info?id="+id;
}
function getlist_shux(){
	producthavedata=false;
//	var nowurl=window.location.href.split("label-web")[0]+"label-web";
	var params={
		pageNum:1,
		pageSize:(productPageNum*10)
	}
	ajaxPostFunctionAsyfalse($('#basePath').val()+"/mp/mpProduct/list",params,function(data){
		if(data.code==0){
			var rows=data.data.list;
			var liHtml='';
			var hasdataid=[];
			var tenattr=[];
			$(".mui-table-view0").find("li").each(function(){
				hasdataid.push(parseInt($(this).attr("dataid")));
			})
			tenattr=hasdataid.slice(0,10);
			$(".mui-table-view0").html("")
			if(rows.length>0){
				var count=0;
				for(var i=0;i<rows.length;i++){
					
					if(i % 2==0){//单数
						count+=1;
						liHtml += '<li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left  getdatali0 am-padding-top-0 am-padding-bottom-0">';
						liHtml+='<div class="am-u-sm-6 am-list-thumb borright1 newpadding"  dataid="'+rows[i].id+'">';
						liHtml+='<h3 class="am-list-item-hd mb10">';
						liHtml+='<div class="listTitle fdkunoTitle">'+rows[i].fdTitle+'</div>';
						liHtml += '</h3>';
						liHtml+='<div class="am-u-sm-7 am-list-main infotoplistdiv am-padding-0">';
						liHtml+='<div class="listTitle fskuno2">'+rows[i].fdDetails+'</div>';
						liHtml += '</div>';
						liHtml+='<div class="am-u-sm-5 am-list-main am-text-center am-padding-0" style="max-width: 66px">';
						liHtml+=' <img src="'+rows[i].fdImgUrl+'" style="width: 100%;max-height: 66px; " alt="产品头图">';
						liHtml+='</div></div>';
					}else{//偶数
						count+=1;
						
						liHtml+='<div class="am-u-sm-6 am-list-thumb newpadding"  dataid="'+rows[i].id+'">';
						liHtml+='<h3 class="am-list-item-hd mb10">';
						liHtml+='<div class="listTitle fdkunoTitle">'+rows[i].fdTitle+'</div>';
						liHtml += '</h3>';
						liHtml+='<div class="am-u-sm-7 am-list-main infotoplistdiv am-padding-0">';
						liHtml+='<div class="listTitle fskuno2">'+rows[i].fdDetails+'</div>';
						liHtml += '</div>';
						liHtml+='<div class="am-u-sm-5 am-list-main am-text-center am-padding-0" style="max-width: 66px">';
						liHtml+=' <img src="'+rows[i].fdImgUrl+'" style="width: 100%;max-height: 66px; " alt="产品头图">';
						liHtml+='</div></div>';
					}
					if(count%2==0||i==rows.length-1){
						liHtml+="</li>";
					}
				}
				$(".mui-table-view0").empty();
			
				$(".mui-table-view0").append(liHtml);
			}else{
				mui.toast("没有最新产品")
			}
		}else{
			mui.toast(data.data)
		}
		
	})
}