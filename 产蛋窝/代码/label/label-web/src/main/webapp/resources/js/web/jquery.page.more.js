function pageparam()
{
	this.pageCount=0;
	this.pageIndex=1;
	this.itemCount=0;
	this.searchsign=1;
	this.vaguevalue="";
	this.pagefunction;
	this.vaguefunction;
}
$(document).ready(function(e) {
    $(document).on('click','.pages a',function(e){
    	e.preventDefault();
		var target=$(e.target);
		var tarparentid=target.attr('pdiv');
		var pp=pparamlist[tarparentid];
		if(pp)
		{
			var tarvalue=target.text();
			var pindex=parseInt(pp.pageIndex);
			if(tarvalue=="上一页")
			{
				if(pindex>1)
				{
					if(pp.searchsign==1){
						pp.pagefunction(pindex-1);
						/*getListByPage(pindex-1);*/
					}
					else if(pp.searchsign==2)
					{
						pp.vaguefunction(pp.vaguevalue,pindex-1);
					}
				}
			}
			else if(tarvalue=="下一页")
			{
				if(pindex<pp.pageCount)
				{	
					if(pp.searchsign==1){
						pp.pagefunction(pindex+1);
					}
					else if(pp.searchsign==2)
					{
						pp.vaguefunction(pp.vaguevalue,pindex+1);
					}
				}
			}
			else
			{
				if(pp.searchsign==1){
					pp.pagefunction(tarvalue);
				}
				else if(searchsign==2)
				{
					pp.vaguefunction(pp.vaguevalue,tarvalue);
				}
			}
		}
		return false;
	});
	$(document).on('click','.pages .btnGo',function(e){
		var tarparentid=$(e.currentTarget).attr('pdiv');
		var pagesign=$('#'+tarparentid+' .txtPage').val();
		var pp=pparamlist[tarparentid];
		if(pp)
		{
			if(pagesign!=''&&pagesign>=1&&pagesign<=pp.pageCount)
			{
				if(pp.searchsign==1){
					pp.pagefunction(parseInt(pagesign));
				}
				else if(searchsign==2)
				{
					pp.vaguefunction(pp.vaguevalue,parseInt(pagesign));
				}
			}
		}
		
	});
	$(document).on('keypress','.pages .txtPage',function(e){
		var kc=e.keyCode;
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

function initPage(tarid,_pagefunction,_vaguefunction)
{
	var pparams=new pageparam();
	pparams.pagefunction=_pagefunction;
	pparams.vaguefunction=_vaguefunction;
	pparamlist[tarid]=pparams;
	$('#'+tarid+" .pages").append("<a class=pageprevious pdiv=\""+tarid+"\" href=\"#\">上一页</a>");
	for(var i=1;i<=5;i++)
	{
		$('#'+tarid+" .pages").append("<a class=bpage"+i+" pdiv=\""+tarid+"\" href=\"#\">"+i+"</a>");
	}
	$('#'+tarid+" .pages").append("<a class=pagenext pdiv=\""+tarid+"\" href=\"#\">下一页</a>");
	$('#'+tarid+" .pages").append("<div class=\"goDiv\"> 到 第<input class=\"txtPage\"  type=\"text\" value=\"1\"/> 页 <input pdiv=\""+tarid+"\" class=\"btnGo\" type=\"button\" value=\"go\" /></div>");
}

function updatePage(tarid,_itemCount,_pageCount)
{
	_itemCount=parseInt(_itemCount);
	var pp=pparamlist[tarid];
	if(pp)
	{
		pp.itemCount=_itemCount;
		pp.pageCount=Math.ceil(pp.itemCount/(_pageCount > 0 ? _pageCount : itemPage));
		$('#'+tarid+' .itemPage').text((_pageCount > 0 ? _pageCount : itemPage));
		$('#'+tarid+' .itemCount').text(pp.itemCount);
		if(pp.pageCount<=1)
		{
			$('#'+tarid+' .pages').css('display','none');
		}
		else
		{
			$('#'+tarid+' .pages').css('display','-webkit-box');
			$('#'+tarid+' .pages').css('display','-moz-box');
			$('#'+tarid+' .pages').css('display','-ms-flexbox');
			$('#'+tarid+' .pages').css('display','-webkit-flex');
			$('#'+tarid+' .pages').css('display','flex');
		}
		pparamlist[tarid]=pp;
	}
}

function selectPage(tarid,pageindex)
{
	var pp=pparamlist[tarid];
	if(pp)
	{
		pp.pageIndex=pageindex;
		$('#'+tarid+' .txtPage').val(pp.pageIndex);
		$('#'+tarid+' .pages a').removeClass('on');
		if(pp.pageCount<=5)
		{
			var i=1
			for(i=1;i<=pp.pageCount;i++)
			{
				$('#'+tarid+' .bpage'+i).css('display','');
				$('#'+tarid+' .bpage'+i).text(i);
			}
			for(;i<=5;i++)
			{
				$('#'+tarid+' .bpage'+i).css('display','none');
			}
			$('#'+tarid+' .bpage'+pp.pageIndex).addClass('on');
		}
		else
		{
			if(pp.pageIndex<4)
			{
				for(var i=1;i<=5;i++)
				{
					$('#'+tarid+' .bpage'+i).css('display','');
					$('#'+tarid+' .bpage'+i).text(i);
				}
				$('#'+tarid+' .bpage'+pp.pageIndex).addClass('on');
			}
			else
			{
				if(pp.pageCount-pp.pageIndex>1)
				{
					for(var i=1;i<=5;i++)
					{
						$('#'+tarid+' .bpage'+i).css('display','');
						$('#'+tarid+' .bpage'+i).text(pp.pageIndex-3+i);
					}
					$('#'+tarid+' .bpage3').addClass('on');
				}
				else
				{
					for(var i=0;i<5;i++)
					{
						var bpageindex=5-i;
						var pagevalue=pp.pageCount-i;
						$('#'+tarid+' .bpage'+bpageindex).css('display','');
						$('#'+tarid+' .bpage'+bpageindex).text(pagevalue);
						if(pagevalue==pp.pageIndex)
						{
							$('#'+tarid+' .bpage'+bpageindex).addClass('on');
						}
					}
				}
			}
		}
		pparamlist[tarid]=pp;
	}
}
