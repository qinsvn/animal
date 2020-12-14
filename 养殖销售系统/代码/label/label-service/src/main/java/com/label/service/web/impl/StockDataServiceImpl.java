/**
 * 
 */
package com.label.service.web.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.label.common.constant.RedisConstant;
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.util.UtilExportExcel;
import com.label.common.util.UtilExportFileName;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.redis.UtilRedis;
import com.label.dao.auto.StockDataMapper;
import com.label.service.web.StockDataService;
import com.label.util.UserUtil;

/**
 * @author admin
 *
 */
@Service
@Transactional
public class StockDataServiceImpl extends CommonServiceImpl implements StockDataService {

	private Logger _log = LoggerFactory.getLogger(getClass());
	@Autowired
	StockDataMapper stockDataMapper;
	
	@Override
	public List<Map<String, Object>> select(Map<String, Object> map) {
		// TODO Auto-generated method stub
		_log.info("查询库存列表，参数map：{}",UtilJson.Obj2Str(map));
		return breedDataCustomMapper.selectStockDatas(map);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		_log.info("stockDataMapper 删除库存id：{}",id);
		return stockDataMapper.deleteByPrimaryKey(id);
	}


@Override
public UpmsResult exportStockData(Map<String, Object> params) {
	UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "导出成功 ");
	try {
		if(!UserUtil.isAdmin()){
			params.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
		}
		//获取数据库中的次品
		List<Map<String, Object> > datas = breedDataCustomMapper.selectStockDatas(params);
		// 表格属性列名数组
		String[] strHeader = {"编号","种类","操作人","入库时间","养殖户"};
		// 表格属性列名key数组
		String[] keys = {"dataCode","dataTypeDes","createUser","createTime","orgName"};
			
		// 标题
		String title = "stockData";
		// 保存的文件夹路径
		String sys_save_path = UtilPropertiesFile.getInstance().get("sys_save_path");
		String sys_exportpath = UtilPropertiesFile.getInstance().get("sys_exportpath");
		String savePath = sys_save_path + sys_exportpath + UtilExportFileName.getName();
		//测试地址
		//savePath = "C:\\actionTool\\export" + UtilExportFileName.getName();
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
		String fileName = "库存数据"+now+".xls";
		// 保存的文件路径
		String filePath = savePath + fileName;
		_log.info("jolley >> exportExcel:{}", filePath);
		// 保存文件
		OutputStream out = new FileOutputStream(filePath);
		
		UtilExportExcel.exportExcels(title, strHeader, keys, datas, out, "yyyy-MM-dd HH:mm:ss",null);
		out.close();
		String fileId = UUID.randomUUID().toString();
		UtilRedis.hset(RedisConstant.LABEL_EXPORT_EXCEL_PATH, fileId, filePath);
		
		result.setData(fileId);
		return result;
		
	} catch (Exception e) {
		_log.error("jolley --> exportExcelErr:{}", e);
	}
	
	return null;
}

}
