var sub;//top.sys.userName(1);
$(function(){
	 sub = subInfo();
	 function subInfo() {
	 	return{
	 		resetDict:function(id, targetId) {//目标option value  包含源 value
	 			var val = $('#' + id).val();
	 			var $select = $('#' + targetId);
	 			checkSelect(targetId);
	 			$('#' + targetId ).empty();
	 			$($('#copy_' + targetId + ' option')).each(function(index, element) {
	 				var elevar = $(this).val();
	 				if (elevar.indexOf(val) > -1 ||val=='' || index == 0) {
	 					$select.append($(this).prop("outerHTML"));
	 				} 
	 			});
	 		},
	 		resetDict0:function(id, targetId) {//目标option value 等于 源 value
	 			var val = $('#' + id).val();
	 			var $select = $('#' + targetId);
	 			checkSelect(targetId);
	 			$('#' + targetId ).empty();
	 			$($('#copy_' + targetId + ' option')).each(function(index, element) {
	 				var supervalue = $(this).attr('supervalue');
	 				if (val==supervalue ||val=='' || index == 0) {
	 					$select.append($(this).prop("outerHTML"));
	 				} 
	 			});
	 		},
	 		resetDictByDept:function(id, targetId) {//源 value (deptId) 是目标option value 的上级 
	 			var val = $('#' + id).val();
	 			val =','+ top.sys.deptAttr(val,'childrens')+',';
	 			var $select = $('#' + targetId);
	 			checkSelect(targetId);
	 			$('#' + targetId ).empty();
	 			$($('#copy_' + targetId + ' option')).each(function(index, element) {
	 				var supervalue = $(this).attr('supervalue');
	 				supervalue = ','+ supervalue+','
	 				if (val.indexOf(supervalue)>-1 ||val=='' || index == 0) {
	 					$select.append($(this).prop("outerHTML"));
	 				} 
	 			});
	 		}
	 	}
	 }
	 function checkSelect(targetId){
		 if ( $("#copy_"+targetId).length == 0 ) {
			 var optionHtml = $("#"+targetId).html();
			 var copyHtml = '<select id="copy_'+targetId+'" style="display:none" >'+optionHtml+'</select>';
			 $("#"+targetId).after(copyHtml);
		 }
	 }
});
