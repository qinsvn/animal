/**
 * @company 广州天迅网络科技有限公司 copyright
 * @author jolley
 * @date 2017年11月23日
 * @describe  xxx
 *
 */
package com.label.web.controller.common;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.label.common.constant.RedisConstant;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.redis.UtilRedis;
import com.label.service.common.CommonFileService;


@Controller
@RequestMapping("/common/export")
public class CommonFileController { 

	private static Logger log = LoggerFactory.getLogger(CommonFileController.class);
	
	@Resource(name = "commonFileServiceImpl")
	private CommonFileService commonFileService;
	
	/**
	 * 取得的文件
	  * @Description: 
	  * @return void   
	  * @throws
	 */
	@RequestMapping(value ="/getFile")
	@ResponseBody
    public void getFile(HttpServletResponse rsp,String fileId){
		OutputStream out = null;
		try {
			String path = UtilRedis.hget(RedisConstant.LABEL_EXPORT_EXCEL_PATH, fileId);
			String[] split = path.split("/");
			String fn = split[split.length-1];
			fn = new String(fn.getBytes("GB2312"), "ISO_8859_1");
			rsp.setHeader("content-disposition", "attachment;filename=" + fn);
			out = rsp.getOutputStream();
			commonFileService.getFileFromDisk(out,fileId);
		} catch (IOException e) {
			log.error("getFile IOException:{}",e);
		}

    }
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "{type}/{path}")    
    public ResponseEntity<byte[]> download(HttpServletRequest req,@PathVariable String type,@PathVariable String path,String fileName) throws IOException {    
    	//相对根路径
    	String sysSavePath =null;
    	if (!StringUtils.isEmpty(type)) {
			if (type.equalsIgnoreCase("relative")) {//相对路径 在 label-web底下
				sysSavePath = req.getServletContext().getRealPath("/");
			}else if (type.equalsIgnoreCase("absolutely")) {//相对路径 在 sys_save_path底下
				 sysSavePath = UtilPropertiesFile.getInstance().get("sys_save_path").concat("/");
			}else{
				log.error("jolley>>路径类型有问题type:{}",type);
				return null;
			}
		}else{
			log.error("jolley>>路径类型为空type:{}",type);
		}
    	if(StringUtils.isEmpty(path)){
    		log.error("jolley>>文件路径有问题path:{}",path);
    		return null;
    	}
    	path = path.replace("_", ".");
    	if (StringUtils.isEmpty(fileName)) {
    		String[] array = path.split("-");
    		fileName =array[array.length-1];
		}else{
			//js 两个encodeURI嵌套
			fileName = URLDecoder.decode(URLDecoder.decode(fileName));
		}
    	path = path.replace("-", "/");
    	
    	
    	String filePath = sysSavePath.concat(path);
        File file=new File(filePath);  
        if (file.exists()) {
			if (file.isFile()) {
					HttpHeaders headers = new HttpHeaders();    
			        fileName=new String(fileName.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
			        headers.setContentDispositionFormData("attachment", fileName);   
			        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
			        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),  headers, HttpStatus.CREATED);    
			}else{
				log.error("jolley>>文件不存在filePath:{}",filePath);
			}
		}else{
			log.error("jolley>>文件不存在filePath:{}",filePath);
		}
        return null;
    }    
}
