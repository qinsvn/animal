/**
 * 	限制图片宽高
 * @param w  图片的宽度
 * @param h	 图片的高度
 * @param file	图片的信息
 */
function checkImgWH(w,h,file){
	var image = new Image();
	var _file = file.value;
	image.src = _file;
	var height = image.height;
	var width = image.width;
	var fileSize = image.filesize;
	if(height != h && width != w){
		set_message("auto-close-dislogBox","提示",'请上传指定尺寸图片');
		return false;
	}
	return true;
	
}