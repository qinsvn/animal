/**
 * 
 */
package com.label.service.web.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.label.common.entity.PageBean;
import com.label.common.entity.ResultApi;
import com.label.common.model.base.AdminUser;
import com.label.common.model.base.ChickenData;
import com.label.common.model.base.ChickenDataExample;
import com.label.common.model.base.OrderData;
import com.label.common.model.base.StockData;
import com.label.common.model.base.StockDataExample;
import com.label.common.util.ModelUtil;
import com.label.common.util.UtilDate;
import com.label.common.util.UtilExportExcel;
import com.label.common.util.UtilExportFileName;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.redis.UtilRedis;
import com.label.dao.auto.ChickenDataMapper;
import com.label.dao.auto.OrderDataMapper;
import com.label.dao.auto.StockDataMapper;
import com.label.service.web.ActionService;
import com.label.service.web.OrderService;
import com.label.util.UserUtil;

/**
 * @author admin
 *
 */
@Service
@Transactional(rollbackFor=Exception.class) 
public class OrderServiceImpl extends CommonServiceImpl implements OrderService {

	private Logger _log = LoggerFactory.getLogger(getClass());
	@Autowired
	OrderDataMapper orderDataMapper;
	@Autowired
	StockDataMapper stockDataMapper;

	@Autowired
	ChickenDataMapper chickenDataMapper;
	
	@Override
	public List<Map<String, Object>> select(Map<String, Object> map) {
		// TODO Auto-generated method stub
		_log.info("查询蛋类列表，参数map：{}",UtilJson.Obj2Str(map));
		return breedDataCustomMapper.selectOrderDeail(map);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		_log.info("orderDataMapperr删除订单id：{}",id);
		return orderDataMapper.deleteByPrimaryKey(id);
	}

	@Override
	public UpmsResult orderSum(PageBean<Map<String, Object>> pageBean) {
		// TODO Auto-generated method stub
		return listCommon(pageBean, new ActionService() {
			@Override
			public List<Map<String, Object>> select(Map<String, Object> map) {
				// TODO Auto-generated method stub
				return breedDataCustomMapper.selectOrderSum(map);
			}
		});
	}


@Override
public UpmsResult exportOrder(Map<String, Object> params) {
	UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "导出成功 ");
	try {
		if(!UserUtil.isAdmin()){
			params.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
		}
		//获取数据库中的商家信息
		List<Map<String, Object> > datas = breedDataCustomMapper.selectOrderDeail(params);
		// 表格属性列名数组
		String[] strHeader = {"编号","种类","销售人员","售卖时间","养殖户"};
		// 表格属性列名key数组
		String[] keys = {"dataCode","dataTypeDes","createUser","createTime","orgName"};
			
		// 标题
		String title = "order";
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
		String fileName = "订单明细数据"+now+".xls";
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
public UpmsResult exportOrderSum(Map<String, Object> params) {
	UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "导出成功 ");
	try {

		if(!UserUtil.isAdmin()){
			params.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
		}
		//获取数据库中的商家信息
		List<Map<String, Object> > datas = breedDataCustomMapper.selectOrderSum(params);
		// 表格属性列名数组
		String[] strHeader = {"月份","总卖出量","鸡卖出量","蛋卖出量"};
		// 表格属性列名key数组
		String[] keys = {"monthly","allsum","checken","agg"};
			
		// 标题
		String title = "order";
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
		String fileName = "订单汇总数据"+now+".xls";
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
public void collectionData(ResultApi resultApi,CollectionData data) throws Exception
{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dataCode", data.getCode());
		if(data.getWorkModel().equals(ModelUtil.collection_ck_order)){
			map.put("dataType", "0");
		}else{
			map.put("dataType", "1");
		}
		List<Map<String, Object>>  stocks = breedDataCustomMapper.selectStockDatas(map);
		//dataType
		if (stocks.size()>0){//有指定库存，可以卖出生成订单
			AdminUser  user = UserUtil.getUserInfoByAccount(data.getUserName());
			Map<String, Object> stock = stocks.get(0);
			OrderData order = new OrderData();
			order.setCreateUserId(user.getId());
			order.setDataId((Integer)stock.get("dataId"));
			order.setDataType((Integer)stock.get("dataType"));
			order.setOrgId(user.getFdConpanyId());
			Date now = new Date();
			order.setCreateTime(now);
			order.setRemark1(UtilDate.formatDate("yyyyMM", now));
			order.setRemark2(UtilDate.formatDate("yyyy", now));
			int ret = orderDataMapper.insertSelective(order);
			if (ret==0) {
				resultApi.set(ResultApi.FAILCODE, "卖出数据错误！");
				throw new Exception("卖出数据错误！");
			}else{
				StockData stockData = new StockData();
				stockData.setStatus(3);
				StockDataExample example = new StockDataExample();
				if(data.getWorkModel().equals(ModelUtil.collection_ck_order)){
					example.createCriteria().andDataTypeEqualTo(0).andDataIdEqualTo((Integer)stock.get("dataId"));
				}else{
					example.createCriteria().andDataTypeEqualTo(1).andDataIdEqualTo((Integer)stock.get("dataId"));
				} 
				ret=stockDataMapper.updateByExampleSelective(stockData, example);
				if (ret==0) {
					resultApi.set(ResultApi.FAILCODE, "减少库存错误！");
					throw new Exception("减少库存错误！");
				}else{
					if(data.getWorkModel().equals(ModelUtil.collection_ck_order)){
						ChickenData ck = new ChickenData();
						ck.setStatus(2);//出库
						ChickenDataExample cke = new ChickenDataExample();
						cke.createCriteria().andRfidNumEqualTo(data.getCode());
						chickenDataMapper.updateByExampleSelective(ck , cke );
					}else{
//						map.put("dataType", "1");
					}
					resultApi.set(ResultApi.SUCCESSCODE, ResultApi.SUCCESSMSG);
				}
			}
		}else{
			resultApi.set(ResultApi.FAILCODE, "没有库存！");
		}
}

}
