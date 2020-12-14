var pageCount;
var pageIndex=1;
var itemCount;
var itemPage;
var	searchsign=1;
var vaguevalue;
$(document).ready(function(e) {
	
    $(document).on('click','.pages a',function(e){
    	e.preventDefault();
		var target=$(e.target);
		var tarvalue=target.text();
		pageIndex=parseInt(pageIndex);
		if(tarvalue=="上一页")
		{
			if(pageIndex>1)
			{
				if(searchsign==1){
					getListByPage(pageIndex-1);
				}
				else if(searchsign==2)
				{
					vagueSearch(vaguevalue,pageIndex-1)
				}
			}
		}
		else if(tarvalue=="下一页")
		{
			if(pageIndex<pageCount)
			{	
				if(searchsign==1){
					getListByPage(pageIndex+1);
				}
				else if(searchsign==2)
				{
					vagueSearch(vaguevalue,pageIndex+1)
				}
			}
		}
		else
		{
			if(searchsign==1){
				getListByPage(tarvalue);
			}
			else if(searchsign==2)
			{
				vagueSearch(vaguevalue,tarvalue)
			}
			
		}
		return false;
	});
	$(document).on('click','.pages .btnGo',function(e){
			var pagesign=$('.pages .txtPage').val();
			if(pagesign!=''&&pagesign>=1&&pagesign<=pageCount)
			{
				if(searchsign==1){
					getListByPage(parseInt(pagesign));
				}
				else if(searchsign==2)
				{
					vagueSearch(vaguevalue,parseInt(pagesign));
				}
				
			}
		});
	$(document).on('keypress','.pages .txtPage',function(event){
		var kc = "";
		if(window.event){
			kc = event.keyCode;
		}else if(event.which){  // Firefox 下input失效问题
			kc = event.which;
		}
		if(kc>47&&kc<58)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	});
	
	/**
	 * 解决firefox下 回退键失效问题
	 */
	$(document).on("keydown",".pages .txtPage",function(event){
		var kc = event.keyCode;
		if(kc == 8){
			$(".pages .txtPage").val("");
		}
	})
});

function initPage()
{
	$('.pages').append("<a id=pageprevious href=\"#\">上一页</a>");
	for(var i=1;i<=5;i++)
	{
		$('.pages').append("<a id=bpage"+i+" href=\"#\">"+i+"</a>");
	}
	$('.pages').append("<a id=pagenext href=\"#\">下一页</a>");
//	$('.pages').append("<div class=\"goDiv\"> 到 第<input class=\"txtPage\"  type=\"text\" value=\"1\"/> 页 <input class=\"btnGo\" type=\"button\" value=\"go\" /></div>");
	$('.pages').append("<div class='goDiv'> 到 第<input class='txtPage'  type='text' value='1'/> 页 <input class='btnGo' type='button' value='go' /></div>");
	getPageSign();
}

function updatePage(_itemCount)
{
	itemCount=_itemCount;
	pageCount=Math.ceil(itemCount/itemPage);
	$('#itemCount').text(itemCount);
	$('#itemPage').text(itemPage);
	if(pageCount<=1)
	{
		$('.pages').css('display','none');
	}
	else
	{
		$('.pages').css('display','-webkit-box');
		$('.pages').css('display','-moz-box');
		$('.pages').css('display','-ms-flexbox');
		$('.pages').css('display','-webkit-flex');
		$('.pages').css('display','flex');
	}
}

function selectPage(pageindex)
{
	var pagesign= $('#pages').attr('data-page');
	setPageSign(pageindex,pagesign);
	pageIndex=pageindex;
	sessionStorage.s
	$('.txtPage').val(pageIndex);
	$('.pages a').removeClass('on');
	if(pageCount<=5)
	{
		var i=1
		for(i=1;i<=pageCount;i++)
		{
			$('#bpage'+i).css('display','');
			$('#bpage'+i).text(i);
		}
		for(;i<=5;i++)
		{
			$('#bpage'+i).css('display','none');
		}
		$('#bpage'+pageIndex).addClass('on');
	}
	else
	{
		if(pageIndex<4)
		{
			for(var i=1;i<=5;i++)
			{
				$('#bpage'+i).css('display','');
				$('#bpage'+i).text(i);
			}
			$('#bpage'+pageIndex).addClass('on');
		}
		else
		{
			if(pageCount-pageIndex>1)
			{
				for(var i=1;i<=5;i++)
				{
					$('#bpage'+i).css('display','');
					$('#bpage'+i).text(pageIndex-3+i);
				}
				$('#bpage3').addClass('on');
			}
			else
			{
				for(var i=0;i<5;i++)
				{
					var bpageindex=5-i;
					var pagevalue=pageCount-i;
					$('#bpage'+bpageindex).css('display','');
					$('#bpage'+bpageindex).text(pagevalue);
					if(pagevalue==pageIndex)
					{
						$('#bpage'+bpageindex).addClass('on');
					}
				}
			}
		}
	}
	
}


function setPageSign(pageindex,pagesign)
{
	var pagesigns=sessionStorage.getItem('yy-page-sign');
	if(!pagesigns)
	{
		pagesigns={};
	}
	else
	{
		pagesigns=JSON.parse(pagesigns);
	}
	pagesigns[pagesign]=pageindex;
	sessionStorage.setItem('yy-page-sign',JSON.stringify(pagesigns));
}

function getPageSign()
{
	var pagesign= $('#pages').attr('data-page');
	try{
		var  pagesigns=sessionStorage.getItem('yy-page-sign');
		pagesigns=JSON.parse(pagesigns);
		pageIndex=pagesigns[pagesign];
		pageIndex=pageIndex?pageIndex:1;
	}
	catch(e)
	{
		pageIndex=1;
	}
	
}