package com.ts.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ts.common.config.TsConfig;
import com.ts.common.constant.CommonConstant;
import com.ts.common.redis.RedisConstant;
import com.ts.common.redis.RedisUtil;
import com.ts.common.service.ExportExcelSevice;

public class UtilExportExcelPlus {

	public static void exportExcels(Map<String, Object> params, List<?> dataset, ExportExcelSevice deVal) {

		String fileId = (String) params.get(CommonConstant.EXPORT_ID_FLAG);
		if (StringUtils.isEmpty(fileId)) {
			return;
		}
		
		String exportSplit = (String) params.get(CommonConstant.EXPORT_SPLIT_FLAG);
		if (StringUtils.isEmpty(exportSplit)) {
			return;
		}
		
		String fileName = (String) params.get(CommonConstant.EXPORT_FILENAME_FLAG);
		if (StringUtils.isEmpty(fileName)) {
			fileName = "导出数据";
		}
		
		String headers = (String) params.get(CommonConstant.EXPORT_HEADER_FLAG);
		String[] headArr=null;
		if (StringUtils.isEmpty(headers)) {
			return;
		}else{
			headArr = headers.split(exportSplit);
		}
		
		String keys = (String) params.get(CommonConstant.EXPORT_KEYS_FLAG);
		String[] keyArr=null;
		if (StringUtils.isEmpty(keys)) {
			return;
		}else{
			keyArr = keys.split(exportSplit);
		}
		
		String sys_save_path =TsConfig.getProperty("ts.downPath");
		String savePath = sys_save_path + UtilExportFileName.getName();
		//测试地址
		File fileDic = new File(savePath);
		if (!fileDic.exists()) {
			// 无此文件夹时创建
			fileDic.mkdirs();
			//设置权限
			fileDic.getParentFile().setReadable(true);
			fileDic.getParentFile().setExecutable(true);
			fileDic.setReadable(true);
			fileDic.setExecutable(true);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String now = df.format(new Date());
		// 文件保存的名字
	     fileName = fileName+now+".xls";
		// 保存的文件路径
		String filePath = savePath + fileName;
		// 保存文件
		OutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			UtilExportExcel.exportExcels("数据列表", headArr, keyArr, dataset, out,"yyyy-MM-dd HH:mm:ss" , deVal);
			RedisUtil.set(RedisConstant.DOWNLOAD_DATA_KEY+fileId, filePath,10);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	public static String getFileId(Map<String, Object> map){
		return (String)map.get(CommonConstant.EXPORT_ID_FLAG);
	}
}
