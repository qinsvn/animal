$(function() {
	$('#vcode_img').on('click', function() {
		$(this).attr("src", "/base/getVerify?" + Math.random())
	})
})