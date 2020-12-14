/**
 * @company 广州天迅网络科技有限公司 copyright
 * @author jolley
 * @date 2017年11月23日
 * @describe  xxx
 *
 */
package com.label.service.common;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.label.common.constant.ImportData;
import com.label.common.constant.SheetData;

public interface CommonImportService { 

	/**
	 * 读取文件 放入封装的工作簿实体类
	 * @param in
	 * @param fileExt
	 * @return
	 * @throws Exception
	 */
	public ImportData readExcel(InputStream in,String fileExt) throws Exception;
	
	/**
	 * 校验excel
	 * @param data
	 * @param type
	 * @return
	 */
	public ImportData checkImportExcel(ImportData data,String type,HttpServletRequest request,Map<String, Object> dataMap);
	
	/**
	 * 批量导入数据
	 * @param sd
	 * @param type
	 * @return
	 */
	public int importData(int sheetNo,SheetData sd,String type,HttpServletRequest request,Map<String, Object> dataMap);
	
	/**
	 * 将校验不通过的记录写入excel文件
	 * @param data
	 * @param errorPath
	 * @return
	 */
	public String writeErrorExcel(int sheetNo,SheetData data,String errorPath,String fileName);
	
}
