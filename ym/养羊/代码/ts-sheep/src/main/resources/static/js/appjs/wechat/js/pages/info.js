$(function() {
	 $("#goBack").on("click", function(){
		 self.location.href=top.ctx +"/index?from=mobile";
	 });
	 initSound();
});