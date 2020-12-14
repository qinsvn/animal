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

import com.label.common.base.ExportExcelSevice;
import com.label.common.constant.RedisConstant;
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.entity.CollectionData;
import com.label.common.entity.ResultApi;
import com.label.common.model.base.AdminUser;
import com.label.common.model.base.ChickenData;
import com.label.common.model.base.ChickenDataExample;
import com.label.common.model.base.StockData;
import com.label.common.model.base.StockDataExample;
import com.label.common.model.base.SubstandardData;
import com.label.common.util.ModelUtil;
import com.label.common.util.UtilExportExcel;
import com.label.common.util.UtilExportFileName;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.redis.UtilRedis;
import com.label.dao.auto.ChickenDataMapper;
import com.label.dao.auto.StockDataMapper;
import com.label.dao.auto.SubstandardDataMapper;
import com.label.service.web.ActionService;
import com.label.service.web.SubstandardDataService;
import com.label.util.UserUtil;

/**
 * @author admin
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SubstandardDataServiceImpl extends CommonServiceImpl implements SubstandardDataService {

	private Logger _log = LoggerFactory.getLogger(getClass());
	@Autowired
	SubstandardDataMapper substandardDataMapper;
	@Autowired
	ChickenDataMapper chickenDataMapper;
	@Autowired
	StockDataMapper stockDataMapper;

	@Override
	public List<Map<String, Object>> select(Map<String, Object> map) {
		// TODO Auto-generated method stub
		_log.info("查询次品列表，参数map：{}", UtilJson.Obj2Str(map));
		return breedDataCustomMapper.selectSubstandardDatas(map);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		_log.info("substandardDataMapper删除次品id：{}", id);
		return substandardDataMapper.deleteByPrimaryKey(id);
	}

	@Override
	public UpmsResult selectSubstandardDataSum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return listCommon(map, new ActionService() {
			@Override
			public List<Map<String, Object>> select(Map<String, Object> map) {
				// TODO Auto-generated method stub
				return breedDataCustomMapper.selectSubstandardDataSum(map);
			}
		});
	}

	@Override
	public void collectionData(ResultApi resultApi, CollectionData data) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String substandardType="0";
		if (data.getWorkModel().equals(ModelUtil.collection_ck_substandard)) {
			map.put("dataType", "0");
			substandardType="0";
		} else {
			map.put("dataType", "1");
			substandardType="2";
		}
		map.put("dataCode", data.getCode());
		List<Map<String, Object>> stocks = breedDataCustomMapper.selectStockDatas(map);
		List<Map<String, Object> > substandardDatas = breedDataCustomMapper.selectSubstandardDatas(map);
		if (substandardDatas.size()==0) {
			if (stocks.size() > 0) {// 有指定库存，可以加入次品
				AdminUser user = UserUtil.getUserInfoByAccount(data.getUserName());
				Map<String, Object> stock = stocks.get(0);
				SubstandardData substandardData = new SubstandardData();
				substandardData.setDataId((Integer) stock.get("dataId"));
				substandardData.setDataType((Integer) stock.get("dataType"));
				substandardData.setCreateUserId(user.getId());
				substandardData.setOrgId(user.getFdConpanyId());
				substandardData.setStatus(0);
				substandardData.setRemark1(substandardType);
				Date now = new Date();
				substandardData.setCreateTime(now);
				int ret = substandardDataMapper.insertSelective(substandardData);
				if (ret == 0) {
					resultApi.set(ResultApi.FAILCODE, "次品数据错误！");
					throw new Exception("次品数据错误！");
				} else {
//					if(
//						data.getWorkModel().equals(ModelUtil.collection_ck_substandard)){
//						ChickenData ck = new ChickenData();
//						ck.setStatus(3);//出库
//						ChickenDataExample cke = new ChickenDataExample();
//						cke.createCriteria().andRfidNumEqualTo(data.getCode());
//						chickenDataMapper.updateByExampleSelective(ck , cke );
//					}else{
//					}
					resultApi.set(ResultApi.SUCCESSCODE, ResultApi.SUCCESSMSG);
				}
			} else {
				resultApi.set(ResultApi.FAILCODE, "没有库存！");
			}
		}else{
			resultApi.set(ResultApi.FAILCODE, "不能重复添加次品！");
		}
	}


	@Override
	public UpmsResult addSubstandardData(CollectionData data) throws Exception {
		UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "加入次品成功！ ");
		Map<String, Object> map = new HashMap<String, Object>();
		
		String substandardType="1";
		if (data.getWorkModel().equals(ModelUtil.collection_ck_substandard)) {
			map.put("dataType", "0");
			substandardType = "1";
		} else {
			map.put("dataType", "1");
			substandardType = "2";
		}
		map.put("dataCode", data.getCode());
		
		List<Map<String, Object> > substandardDatas = breedDataCustomMapper.selectSubstandardDatas(map);
		if (substandardDatas.size()==0) {
			List<Map<String, Object>> stocks = breedDataCustomMapper.selectStockDatas(map);
			// dataType
			if (stocks.size() > 0) {// 有指定库存，可以加入次品
				Map<String, Object> stock = stocks.get(0);
				SubstandardData substandardData = new SubstandardData();
				substandardData.setDataId((Integer) stock.get("dataId"));
				substandardData.setDataType((Integer) stock.get("dataType"));
				substandardData.setCreateUserId(UserUtil.getUpmsUserInfo().getId());
				substandardData.setOrgId(UserUtil.getUpmsUserInfo().getCompanyId());
				substandardData.setStatus(0);
				substandardData.setRemark1(substandardType);
				Date now = new Date();
				substandardData.setCreateTime(now);
				int ret = substandardDataMapper.insertSelective(substandardData);
				if (ret == 0) {
					result = new UpmsResult(UpmsResultConstant.FAILED, "次品数据错误！ ");
				}else{
//					if(data.getWorkModel().equals(ModelUtil.collection_ck_substandard)){
//						ChickenData ck = new ChickenData();
//						ck.setStatus(3);//出库
//						ChickenDataExample cke = new ChickenDataExample();
//						cke.createCriteria().andRfidNumEqualTo(data.getCode());
//						chickenDataMapper.updateByExampleSelective(ck , cke );
//					}else{
//					}
				}
			} else {
				result = new UpmsResult(UpmsResultConstant.FAILED, "没有库存数据！ ");
			}
		}else{
			result = new UpmsResult(UpmsResultConstant.FAILED, "不能重复添加次品！ ");
		}
		
		return result;
	}
	
	
	@Override
	public UpmsResult approval(List<Integer> list) {
			for (Integer id : list) {
				//修改次品状态
				
				SubstandardData substandardData = substandardDataMapper.selectByPrimaryKey(id);
				substandardData.setApprovalUserId(UserUtil.getUpmsUserInfo().getId());
				substandardData.setApprovalOrgId(UserUtil.getUpmsUserInfo().getCompanyId());
				substandardData.setApprovalTime(new Date());
				substandardData.setStatus(1);
				substandardDataMapper.updateByPrimaryKeySelective(substandardData);
				//减少库存
				StockData stockData = new StockData();
				stockData.setStatus(3);
				StockDataExample example = new StockDataExample();
				example.createCriteria().andDataIdEqualTo(substandardData.getDataId()).andDataTypeEqualTo(substandardData.getDataType());
				stockDataMapper.updateByExampleSelective(stockData, example );
				
				//更新状态
				ChickenData ck = new ChickenData();
				ck.setStatus(3);//出库
				ck.setId(substandardData.getDataId());
				chickenDataMapper.updateByPrimaryKeySelective(ck);
				
		}
		return new UpmsResult(UpmsResultConstant.SUCCESS, null);
	}


@Override
public UpmsResult exportSubstandardData(Map<String, Object> params) {
	UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "导出成功 ");
	try {
		if(!UserUtil.isAdmin()){
			params.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
		}
		//获取数据库中的次品
		List<Map<String, Object> > datas = breedDataCustomMapper.selectSubstandardDatas(params);
		// 表格属性列名数组
		String[] strHeader = {"编号","种类","录入人","录入时间","审核人","审核时间","养殖户","状态"};
		// 表格属性列名key数组
		String[] keys = {"dataCode","dataTypeDes","createUser","createTime","approvalUser","approvalTime","orgName","status"};
			
		// 标题
		String title = "substandardData";
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
		String fileName = "次品数据"+now+".xls";
		// 保存的文件路径
		String filePath = savePath + fileName;
		_log.info("jolley >> exportExcel:{}", filePath);
		// 保存文件
		OutputStream out = new FileOutputStream(filePath);
		
		UtilExportExcel.exportExcels(title, strHeader, keys, datas, out, "yyyy-MM-dd HH:mm:ss",new ExportExcelSevice(){			
			public String changeVal(String val, int index) {
				if (index==7) {
					if (val.equals("1")) {
						return "已审核";
					}else{
						return "未审核";
					}
				}
				return val;
			}
		});
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
