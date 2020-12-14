var producthavedata=false;
var productgetlistdata='';
var productpageindex=1;
var count0 = 0;
var hasData=false;
$(function(){
	mui("#productsUl").on("tap","li",function(e){
       var _id = $(this).attr("dataid");
    });
	mui.init({
    	pullRefresh: [{
    	    container: '#productrefresh',
    	    up: {
    	        auto:true,
    	        contentrefresh: '正在加载...',
    	        callback: pullupRefresh0
    	    }
    	}]
    });
	getAllCount();
})
function getAllCount(){
	ajaxPostFunctionAsyfalse("../mpuser/allCount",{},function(data){
		if(data.code==0){
			$("#fansnum_k").html(data.data.all);
			$("#fansorder_k").html(data.data.sumOrder);
			$("#fansreward_k").html(data.data.sumAmonut);			
		}
	})
}
function pullupRefresh0() {
    setTimeout(function() {
        mui('#productrefresh').pullRefresh().endPullupToRefresh(hasData); //参数为true代表没有更多数据了。
   	var cells = document.body.querySelectorAll('.getdatali0');
    	if(cells.length==0){
    		 getlist0(productpageindex);
    	}else{
    		productpageindex++;
    		getlist0(productpageindex);
    		
    	}
       	if(producthavedata){
       		$(".mui-table-view0").append(productgetlistdata);
       	}else{
       		/* mui.toast("没有更多内容了哦") */
       	}
        
    }, 1500);
}

function getlist0(pageNum){
	producthavedata=false;
//	var nowurl=window.location.href.split("label-web")[0]+"label-web";
	var params={
		pagenum:pageNum,
		pagesize:10
	}
	
	ajaxPostFunctionAsyfalse("../mpuser/listFans",params,function(data){
		if(data.code==0){
			var rows=data.data.rows;
			var liHtml='';
			if(rows.length>0){
				for(var i=0;i<rows.length;i++){
					liHtml += '<li class="am-g am-list-item-desced am-list-item-thumbed getdatali0">'+
					'<div class="am-u-sm-3"><div class="am-list-item-text">'+rows[i].nickname+'</div></div>'+
					'<div class="am-u-sm-3"><div class="am-list-item-text">'+(rows[i].fdCreateTime?fomatter(rows[i].fdCreateTime):"")+'</div></div>'+
					'<div class="am-u-sm-3"><div class="am-list-item-text"></div>'+(rows[i].orders?rows[i].orders:0)+'</div>'+
					'<div class="am-u-sm-3"><div class="am-list-item-text"></div>'+(rows[i].amonunt?rows[i].amonunt:0)+'</div>'+
					'</li>';
				}
				producthavedata=true;
				productgetlistdata=liHtml;
			}else{
				liHtml += '<li class="am-g am-list-item-desced am-list-item-thumbed getdatali0 nodata" style="tetx-align:center"><span>暂无更多数据</span></li>';
				hasData=true;
				productpageindex--;
				if($(".nodata").length<=0){
					productgetlistdata=liHtml;
				}else{
					productgetlistdata='';
				}
		
			}
		
		}else{
			mui.toast(data.data)
		}
		
	})
}

function fomatter(str){
	var datas=new Date(str);
	var year=datas.getFullYear();
	var month=datas.getMonth()+1;
	var days=datas.getDate();
	var sttr=year+"年"+month+"月"+days+"日";
	return sttr;
}