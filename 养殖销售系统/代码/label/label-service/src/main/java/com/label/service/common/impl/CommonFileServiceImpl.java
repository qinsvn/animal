/**
 * @company 广州天迅网络科技有限公司 copyright
 * @author jolley
 * @date 2017年11月23日
 * @describe  xxx
 *
 */
package com.label.service.common.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.label.common.constant.RedisConstant;
import com.label.common.util.redis.UtilRedis;
import com.label.service.common.CommonFileService;

@Service("commonFileServiceImpl")
public class CommonFileServiceImpl implements  CommonFileService{
	private static Logger log = LoggerFactory.getLogger(CommonFileServiceImpl.class);
	
	/**
	 * 从硬盘获取文件
	 * @param fileId 文件id
	 */
	@Override
	public void getFileFromDisk(OutputStream out,String fileId) {
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(UtilRedis.hget(RedisConstant.LABEL_EXPORT_EXCEL_PATH,fileId));
			int c;
	        while((c=fin.read())!=-1)
	        {
	        	out.write(c);
	        }
		} catch (FileNotFoundException e) {
			log.error("getFileFromDisk FileNotFoundException:",e.getMessage());
		} catch (IOException e) {
			log.error("getFileFromDisk IOException:",e.getMessage());
		}finally{
			if(fin!=null){
				UtilRedis.del(RedisConstant.LABEL_EXPORT_EXCEL_PATH);
				try {
					fin.close();
				} catch (IOException e) {
					log.error("getFileFromDisk IOException:",e.getMessage());
				}
			}	
		}
	}
}

