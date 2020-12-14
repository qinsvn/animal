var MuiPage = (function(){
	function init(){
			   //活动对象初始化
			  // initActivityObj();
			   mui.init({
			    	pullRefresh: [{
			    	    container: '#pullrefresh',
			    	    down: {
			    	        style:'circle',
			    	        callback: pulldownRefresh1
			    	    },
			    	    up: {
			    	        auto:true,
			    	        contentrefresh: '正在加载...',
			    	        callback: pullupRefresh1
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
			   mui('#pullrefresh').on('tap','ul li .newpadding',function(){
				   console.log($(this).attr('dataid'));
				   window.location.href = $('#basePath').val()+"/mp/mpActivity/info?id=" + $(this).attr('dataid');
			   });
		   }
	return {
		init:function(){
			init();
		}
	}
})();