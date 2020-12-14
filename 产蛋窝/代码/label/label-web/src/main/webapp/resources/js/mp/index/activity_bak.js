var Activity = (function(){
    	var pageSize =10//默认每页10条记录 
    	var activityObjInit ={code: 0, message: "success",data:{pageNum: 0, pageSize:pageSize,pages: 0,total: 0,list:[]}};
	   //活动对象
	   var activityObj;
	   //初始化活动对象
	   function initActivityObj(){
		   $('#pullrefresh').find('ul').html('');
		   activityObj = jQuery.extend(true,{}, activityObjInit);
	   }
	   
	   function init(){
		   //活动对象初始化
		   initActivityObj();
		   mui.init({
		    	pullRefresh: [{
		    	    container: '#pullrefresh',
		    	    down: {
		    	        style:'circle',
		    	        callback: function(){
			            	setTimeout(function() {
				             	initActivityObj();
				     			queryActivitys(activityObj.data.pageNum+1,activityObj.data.pageSize);
				     			mui('#pullrefresh').pullRefresh().endPulldownToRefresh();
			            	},700);
			            }
		    	    },
		    	    up: {
		    	        auto:true,
		    	        contentrefresh: '正在加载...',
		    	        callback: function(){
			            	setTimeout(function() {
			            		queryActivitys(activityObj.data.pageNum+1,activityObj.data.pageSize);
			            		mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
			            	},700);
			            }
		    	    }
		    	},{
		    	    container: '#productrefresh',
		    	    down: {
		    	        style:'circle',
		    	        callback: pulldownRefresh0
		    	    },
		    	    up: {
		    	        auto:true,
		    	        contentrefresh: '正在加载...',
		    	        callback: pullupRefresh0
		    	    }
		    	}]
		    });
		   mui('#pullrefresh').on('tap','ul li',function(){
			   window.location.href = $('#basePath').val()+"/mp/mpActivity/info?id="+$(this).attr('activityId');
		   });
		 
	   }
	   //活动列表
	   function queryActivitys(pageNum,pageSize,callback){
		   if($('#pullrefresh').find('ul').find('.mui-pull-caption-nomore').length>0){
				return;
			}
	  	    ajaxPostFunctionAsytrue($('#basePath').val()+"/mp/mpActivity/list",{pageNum:pageNum,pageSize:pageSize},function(data){
			    var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
				var liHtml = ' <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left" activityId="[activityId]"> '
									+' <a href="javascript:void(0)"> '
									+' <div class="am-u-sm-3 am-list-thumb" style="padding-right: 0;max-width: 94px !important;"> '
									+' <img src="[fdImgUrl]" style="width: 100%;max-height: 60px; " alt="活动头图" />'
									+' </div> '
									+' <div class="am-u-sm-9 am-list-main"> '
									+' <h3 class="am-list-item-hd"> '
									+' <div class="listTitle">[fdTitle]</div> '
									+' </h3> '
									+' <div class="listTitle fskuno">[fdDetails]</div> '
									+' <div class="am-list-item-text listtime">[fdCreateTime]</div> '
									+' </div> '
									+' </a> '
									+' </li> ';
				//if									
				if(data.code=='0'&&data.data.list.length>0){
					var tempList = data.data.list.concat(activityObj.data.list);
					$.extend(true,activityObj,data);
					activityObj.data.list = tempList;
					$.each(data.data.list,function(i,val){
					  var content = liHtml.replace(reg, function (node, key) {
			                return {
			                	activityId:val.id,
			                	fdImgUrl: ((val.fdImgUrl=='')?$('#basePath').val()+"/resources/images/mp/index/activity.png":(val.fdImgUrl)),
			                	fdTitle: (val.fdTitle==''?'':val.fdTitle),
			                	fdDetails: (val.fdDetails==''?'':val.fdDetails),
			                	fdCreateTime:(val.fdCreateTime==''?'':val.fdCreateTime)
			                }[key];
			            });
					  $('#pullrefresh').find('ul').append(content); 
					});		
					
					if(activityObj.data.pageNum == activityObj.data.pages){
						$('#pullrefresh').find('ul').append('<div class="mui-pull-bottom-pocket mui-block mui-visibility"><div class="mui-pull"><div class="mui-pull-loading mui-icon mui-spinner mui-hidden"></div><div class="mui-pull-caption mui-pull-caption-nomore">没有更多数据了</div></div></div>'); 
					}
					if($.isFunction(callback)){
						callback();
					}
				}//end if		
	   	});
	   }
	   
    	return {
    		init:function(){
    			init();
    		},
    		query:function(){
    			queryActivitys(activityObj.data.pageNum+1,activityObj.data.pageSize);
    		},
    		refresh:function(){
    			initActivityObj();
    			queryActivitys(activityObj.data.pageNum+1,activityObj.data.pageSize);
    		}
    	}
    })();
