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
import com.label.common.entity.CollectionData;
import com.label.common.entity.ResultApi;
import com.label.common.model.base.AdminUser;
import com.label.common.model.base.ChickenBaseinput;
import com.label.common.model.base.ChickenData;
import com.label.common.model.base.ChickenDataExample;
import com.label.common.model.base.StockData;
import com.label.common.model.base.StockDataExample;
import com.label.common.util.UtilExportExcel;
import com.label.common.util.UtilExportFileName;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.redis.UtilRedis;
import com.label.dao.auto.ChickenDataMapper;
import com.label.dao.auto.StockDataMapper;
import com.label.service.web.ChickenService;
import com.label.util.BaseInputdataUtil;
import com.label.util.UserUtil;

/**
 * @author admin
 *
 */
@Service
@Transactional(rollbackFor=Exception.class) 
public  class ChickenServiceImpl extends CommonServiceImpl implements ChickenService {

	private Logger _log = LoggerFactory.getLogger(getClass());
	@Autowired
	ChickenDataMapper chickenDataMapper;
	@Autowired
	StockDataMapper stockDataMapper;
	@Override
	public List<Map<String, Object>> select(Map<String, Object> map) {
		// TODO Auto-generated method stub
		_log.info("查询禽类列表，参数map：{}",UtilJson.Obj2Str(map));
		return breedDataCustomMapper.selectChikens(map);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		_log.info("chickenDataMapper删除禽类id：{}",id);
		ChickenData chickenData = new ChickenData();
		chickenData.setId(id);
		chickenData.setStatus(1);
		chickenDataMapper.updateByPrimaryKeySelective(chickenData );
		
		StockData stockData = new StockData();
		stockData.setStatus(1);//减少库存
		StockDataExample example = new StockDataExample();
		example.createCriteria().andDataTypeEqualTo(0).andDataIdEqualTo((Integer)chickenData.getId());
		stockDataMapper.updateByExampleSelective(stockData, example);
		
		return 1;
	}

	@Override
	public void collectionData(ResultApi resultApi,CollectionData data) throws Exception
	{
				ChickenDataExample chickenDataExample = new ChickenDataExample();
				chickenDataExample.createCriteria().andStatusNotEqualTo(1).andRfidNumEqualTo(data.getCode());//
				if (chickenDataMapper.selectByExample(chickenDataExample).size()==0) {//未存在才入库
					AdminUser  user = UserUtil.getUserInfoByAccount(data.getUserName()); 
					ChickenBaseinput chickenBaseinput = BaseInputdataUtil.getChickenBaseinputByUserId(user.getId());
					ChickenData chickenData = new ChickenData();
					chickenData.setChikenType(chickenBaseinput.getChickenType());
					chickenData.setCreateUserId(user.getId());
					chickenData.setOrgId(user.getFdConpanyId()); 
					chickenData.setPupplier(chickenBaseinput.getSupplier());
					chickenData.setRfidNum(data.getCode());
					chickenData.setWeight(chickenBaseinput.getWeight());
					Date now = new Date();
					chickenData.setCreateTime(now);
					chickenData.setStatus(0);
					int ret = chickenDataMapper.insertSelective(chickenData);
					//库存
					if (ret>0) {
						StockData record = new StockData();
						record.setDataId(chickenData.getId());
						record.setDataType(0);
						record.setStatus(0);
						ret = stockDataMapper.insert(record );
						if (ret==0) {
							 resultApi.set(ResultApi.FAILCODE, "入库错误！");
							throw new Exception("禽类入库错误！");
						}else{
							resultApi.set(ResultApi.SUCCESSCODE, ResultApi.SUCCESSMSG);
						}
					}else{
						resultApi.set(ResultApi.FAILCODE, "采集错误！");
						throw new Exception("禽类采集错误！");
					}
				}else{
					resultApi.set(ResultApi.FAILCODE, "重复采集！");
				}
	}
	

	/**
	 * 修改
	 * @param result
	 * @param map
	 */
	@Override
	public UpmsResult update(ChickenData chickenData){
		int ret = chickenDataMapper.updateByPrimaryKeySelective(chickenData);
		if(ret>0){
			return new UpmsResult(UpmsResultConstant.SUCCESS, chickenData);
		}
		return   new UpmsResult(UpmsResultConstant.FAILED, "更新失败 ");
	}
	

@Override
public UpmsResult exportChickenData(Map<String, Object> params) {
	UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "导出成功 ");
	try {

		if(!UserUtil.isAdmin()){
			params.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
		}
		//获取数据库中的蛋类
		List<Map<String, Object> > datas = breedDataCustomMapper.selectChikens(params);
		// 表格属性列名数组
		String[] strHeader = {"脚号","品种","体重","供应商","采集日期","采集人","所属养殖户"};
		// 表格属性列名key数组
		String[] keys = {"rfidNum","chikenType","weight","pupplier","createTime","createUser","orgName"};
			
		// 标题
		String title = "chickenData";
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
		String fileName = "禽类录入数据"+now+".xls";
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
@Override
public UpmsResult getChickenInfoByRFID(String rfidNum){
	UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "查询成功 ");
	 Map<String, Object> ck = breedDataCustomMapper.getChickenInfoByRFID(rfidNum);
	 if (ck!=null) {
		 result.setData(ck);
	}else{
		 result = new UpmsResult(UpmsResultConstant.FAILED, "暂无数据 ");
	}
	return result;
}
}
