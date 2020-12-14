package com.label.service.web.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.label.common.constant.RedisConstant;
import com.label.common.model.base.Upload;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.UtilString;
import com.label.common.util.redis.UtilRedis;
import com.label.dao.auto.UploadMapper;
import com.label.service.web.UploadService;

@Service
@Transactional
public class UploadServiceImpl implements UploadService {
	
	private static Logger _log = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Autowired
    UploadMapper uploadMapper;

	@Override
	public Object create(MultipartFile file, int type) {
		try {
			// 保存文件
			String sys_save_path = UtilPropertiesFile.getInstance().get("sys_save_path");
			String sys_uploadpath = UtilPropertiesFile.getInstance().get("sys_uploadpath");
			String randomDir1 = UtilString.randomStr(2, true, true, false);
			String randomDir2 = UtilString.randomStr(2, true, true, false);
			String uploadPath = sys_uploadpath + "/" + randomDir1 + "/" + randomDir2;
			
			String originFilename = file.getOriginalFilename();//文件名
			int idx = originFilename.lastIndexOf(".");
	        String ext = originFilename.substring(idx + 1);
	        // 保存文件的名称如：XX.png
	        String saveFilename = String.format("%s.%s",UUID.randomUUID().toString().replace("-", ""), ext);
			
			File fileDir = new File(sys_save_path + uploadPath);
	        if(!fileDir.exists()) {
	        	fileDir.mkdirs();
	        }
			
			File tempFile = new File(sys_save_path + uploadPath + "/" + saveFilename);
		    file.transferTo(tempFile);
			
		    // 保存数据库
		    Upload upload = new Upload();
		    upload.setFdType((byte) type);
			upload.setFdCtime(new Date());
			upload.setFdSize((int) file.getSize());
			upload.setFdName(originFilename);
			upload.setFdPath(uploadPath + "/" + saveFilename);
			
			uploadMapper.insertSelective(upload);
			
			// 上传文件后生临时凭证
			String tempKey = UtilString.randomStr(16, true, true, true);
			UtilRedis.setex(RedisConstant.LABEL_TEMP_ + tempKey, 5 * 60, uploadPath + "/" + saveFilename);
			
			String sys_scheme = UtilPropertiesFile.getInstance().get("sys_scheme");
			String sys_nethost = UtilPropertiesFile.getInstance().get("sys_nethost");
			String url = sys_scheme + "://" + sys_nethost + uploadPath + "/" + saveFilename;
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("url", url);
			map.put("local", uploadPath + "/" + saveFilename);
			map.put("filekey", tempKey);
			return map;
		} catch(Exception e) {
			_log.error("jolley>> 上传文件，保存文件失败", e);
		}
		
		return null;
	}

}
