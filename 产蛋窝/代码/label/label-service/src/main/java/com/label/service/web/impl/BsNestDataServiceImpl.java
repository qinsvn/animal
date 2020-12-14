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

import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.label.common.constant.RedisConstant;
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.entity.PageBean;
import com.label.common.model.base.BsNestData;
import com.label.common.model.base.BsNestDataExample;
import com.label.common.model.base.BsNestparam;
import com.label.common.model.base.Company;
import com.label.common.util.UserUtil;
import com.label.common.util.UtilDate;
import com.label.common.util.UtilDouble;
import com.label.common.util.UtilExportExcel;
import com.label.common.util.UtilExportFileName;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.UtilString;
import com.label.common.util.redis.UtilRedis;
import com.label.dao.auto.BsNestData20Mapper;
import com.label.dao.auto.BsNestDataMapper;
import com.label.dao.auto.BsNestparamMapper;
import com.label.dao.custom.BsNestDataCustomMapper;
import com.label.service.web.BsNestDataService;
import com.label.task.device.DeviceData;
import com.label.task.util.UtilSysParam;

/**
 * @author Administrator
 *
 */
@Service
@Transactional
public class BsNestDataServiceImpl implements BsNestDataService {
	
	private Logger _log = LoggerFactory.getLogger(BsNestDataServiceImpl.class);

	//解析G780的参数
   private static int SYS_G780_ANALYSISDATA_LENGTH = Integer.parseInt(UtilPropertiesFile.getInstance().get("sys_g780_analysisdata_length")); 
   private static int SYS_G780_ANALYSISDATA_RFID_START = Integer.parseInt(UtilPropertiesFile.getInstance().get("sys_g780_analysisdata_rfid_start")); 
   private static int SYS_G780_ANALYSISDATA_RFID_END = Integer.parseInt(UtilPropertiesFile.getInstance().get("sys_g780_analysisdata_rfid_end")); 
   private static int SYS_G780_ANALYSISDATA_WEIGHT_START = Integer.parseInt(UtilPropertiesFile.getInstance().get("sys_g780_analysisdata_weight_start")); 
   private static int SYS_G780_ANALYSISDATA_WEIGHT_END = Integer.parseInt(UtilPropertiesFile.getInstance().get("sys_g780_analysisdata_weight_end")); 
   private static int SYS_G780_ANALYSISDATA_DATE_START = Integer.parseInt(UtilPropertiesFile.getInstance().get("sys_g780_analysisdata_date_start")); 
   private static int SYS_G780_ANALYSISDATA_DATE_END = Integer.parseInt(UtilPropertiesFile.getInstance().get("sys_g780_analysisdata_date_end")); 
   private static String SYS_G780_CLIENT_STARTSTR = UtilPropertiesFile.getInstance().get("sys_g780_client_startStr"); 
   private static int SYS_G780_SOME_DAY_AGO = Integer.parseInt(UtilPropertiesFile.getInstance().get("sys_g780_some_day_ago")); 
   
   private static int SYS_G780_USE_SYSTEM_DATE = Integer.parseInt(UtilPropertiesFile.getInstance().get("sys_g780_use_system_date")); 
   
   
	 @Autowired
	 BsNestDataMapper bsNestDataMapper;

	@Autowired
	BsNestData20Mapper bsNestData20Mapper;

	@Autowired
	BsNestDataCustomMapper bsNestDataCustomMapper;
	
	@Autowired
	BsNestparamMapper bsNestparamMapper;
		
	/* (non-Javadoc)
	 * @see com.label.service.web.BsNestDataService#createBsNestData(com.label.common.model.base.BsNestData)
	 */
	@Override
	public UpmsResult createBsNestData(BsNestData bsNestData) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "插入失败");
		try{
			int ret =bsNestDataMapper.insertSelective(bsNestData);
			if(ret>0){
				result = new UpmsResult(UpmsResultConstant.SUCCESS, bsNestData);				
			}else{
				result = new UpmsResult(UpmsResultConstant.FAILED, "数据不存在");
			}
			//更新一下  最后更新时间
			BsNestData lastTimeBsNestData = new BsNestData();
			lastTimeBsNestData.setLastUpdateTime(new Date());
			BsNestDataExample bsNestDataExample = new BsNestDataExample();
			bsNestDataExample.createCriteria().andRfidNumEqualTo(bsNestData.getRfidNum());
			bsNestDataMapper.updateByExampleSelective(lastTimeBsNestData, bsNestDataExample);
			//更新一下  最后更新时间
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public UpmsResult updateBsNestData(BsNestData bsNestData) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "更新失败");
		try{
			int ret =bsNestDataMapper.updateByPrimaryKeySelective(bsNestData);
			if(ret>0){
				result = new UpmsResult(UpmsResultConstant.SUCCESS, bsNestData);				
			}else{
				result = new UpmsResult(UpmsResultConstant.FAILED, "数据不存在");
			}
			//更新一下  最后更新时间
			BsNestData lastTimeBsNestData = new BsNestData();
			lastTimeBsNestData.setLastUpdateTime(new Date());
			BsNestDataExample bsNestDataExample = new BsNestDataExample();
			bsNestDataExample.createCriteria().andRfidNumEqualTo(bsNestData.getRfidNum());
			bsNestDataMapper.updateByExampleSelective(lastTimeBsNestData, bsNestDataExample);
			//更新一下  最后更新时间
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see com.label.service.web.BsNestDataService#updateBsNestData(com.label.common.model.base.BsNestData)
	 */
	@Override
	public UpmsResult updateBsNestData(Map<String, Object> bsNestData) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "修改失败");
		
		String in = (String)bsNestData.get("inBodyWeight");
		String out = (String)bsNestData.get("outBodyWeight");
        String eatMtWeight = (String)bsNestData.get("eatMtWeight");
        
		double inD ;//进窝体重
		double outD;//出窝体重
		double incD ;//增加体总
		double eatMtWeightD ;//吃料重
//		BsNestData newData =  bsNestDataMapper.selectByPrimaryKey((Integer)bsNestData.get("id"));
		BsNestData newData =   new BsNestData();
		newData.setId( Integer.parseInt(bsNestData.get("id").toString()));
		if ( UtilString.isNotEmpty(in) ) {
			if ( !UtilString.verification(in, "[0-9]*(\\.?)[0-9]*")) {
				result = new UpmsResult(UpmsResultConstant.FAILED, "进窝体重必须为数字格式");
				return result;
			}
			 inD = Double.valueOf(in.toString());
		}else{
//			inD = newData.getInBodyWeight();
			inD =0;
		}
		
		if ( UtilString.isNotEmpty(out)){
			if ( !UtilString.verification(out, "[0-9]*(\\.?)[0-9]*")) {
				result = new UpmsResult(UpmsResultConstant.FAILED, "出窝体重必须为数字格式");
				return result;
			}
			 outD = Double.valueOf(out.toString());
		}else{
			outD = 0;
		}
		
 
		if ( UtilString.isNotEmpty(eatMtWeight)){
			if ( !UtilString.verification(eatMtWeight, "[0-9]*(\\.?)[0-9]*")) {
				result = new UpmsResult(UpmsResultConstant.FAILED, "出窝体重必须为数字格式");
				return result;
			}
			eatMtWeightD = Double.valueOf(eatMtWeight.toString());
		}else{
			eatMtWeightD = 0;
		}
		
		
		newData.setInBodyWeight(inD);
		newData.setOutBodyWeight(outD);
		newData.setEatMtWeight(eatMtWeightD);
		 incD =UtilDouble.sub( outD , inD); 
		 newData.setIncBodyWeight((incD<=0)?0:incD);
		 //计算料肉比
		if(outD!=0&&inD!=0&&eatMtWeightD!=0){
			
			String ratio  = String.format("%.2f", (incD*100/eatMtWeightD))+"%";
			newData.setMtBodyRatio(ratio);
		}else{
			newData.setMtBodyRatio("");
		}
		int ret = bsNestDataMapper.updateByPrimaryKeySelective(newData);
		if (ret>0) {
			 result = new UpmsResult(UpmsResultConstant.SUCCESS, "修改成功");
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.label.service.web.BsNestDataService#getBsNestData(java.util.Map)
	 */
	@Override
	public UpmsResult getBsNestData(Map<String, Object> map) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败");
		try{
			List<Map<String,Object>>list =bsNestDataCustomMapper.select(map);
			if(list!=null&&list.size()>0){
				result = new UpmsResult(UpmsResultConstant.SUCCESS, list.get(0));				
			}else{
				result = new UpmsResult(UpmsResultConstant.FAILED, "数据不存在");
			}
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> getBsNestData info error map:" + UtilJson.Obj2Str(map), e);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.label.service.web.BsNestDataService#listBsNestData(com.label.common.entity.PageBean)
	 */
	@Override
	public UpmsResult listBsNestData(PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败 ");
		try{
			Map<String, Object>  info = pageBean.getCondition();
			if(!UserUtil.isAdmin()){
				info.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
			}
			PageHelper.startPage(pageBean.getPage(), pageBean.getRows());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object> > rows = bsNestDataCustomMapper.select(info);
			PageInfo<Map<String, Object> > pageInfo = new PageInfo<Map<String, Object> >(rows);
			data.put("rows", pageInfo.getList());
			data.put("count", pageInfo.getSize());
			data.put("total", pageInfo.getTotal());
			result = new UpmsResult(UpmsResultConstant.SUCCESS, data);
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> listBsNestData list error info:" + UtilJson.Obj2Str(pageBean), e);
		}
		return result;
	}

	@Override
	public List<Map<String, Object> >  listBsNestData(Map<String, Object> map) {
		try{
			List<Map<String, Object> > rows = bsNestDataCustomMapper.select(map);
			return rows;
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> listBsNestData20 list error map:" + UtilJson.Obj2Str(map), e);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.label.service.web.BsNestDataService#deleteBsNestDatas(java.util.List)
	 */
	@Override
	public UpmsResult deleteBsNestDatas(List<Integer> list) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "删除成功 ");
		for (Integer id : list) {
			BsNestData bsnestData = bsNestDataMapper.selectByPrimaryKey(id);
			bsNestDataMapper.deleteByPrimaryKey(id); 
			Map<String, Object> map = new HashMap<>();
			map.put("rfidNum", bsnestData.getRfidNum());

			List<Map<String, Object>> dbList = bsNestDataCustomMapper.select(map);
			for (int i = 0; i < dbList.size(); i++) {
				BsNestData nest = new BsNestData();
				nest.setId(Integer.valueOf(dbList.get(i).get("id").toString()));
				nest.setInOrder(i + 1);
				bsNestDataMapper.updateByPrimaryKeySelective(nest);
			}
		}
		return result;
	}


	@Override
	public Integer maxOrder(String rfidNum) {
		Integer maxOrder = bsNestDataCustomMapper.maxOrder(rfidNum);
		if(maxOrder==null||maxOrder==0){
			return 1;
		}
		return maxOrder+1;
	}

	
	/* (non-Javadoc)
	 * @see com.label.service.web.BsNestDataService#listBsNestData20(com.label.common.entity.PageBean)
	 */
	@Override
	public UpmsResult listBsNestData20(PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败 ");
		try{
			Map<String, Object>  info = pageBean.getCondition();
			if(!UserUtil.isAdmin()){
				info.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
			}
			PageHelper.startPage(pageBean.getPage(), pageBean.getRows());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object> > rows = bsNestDataCustomMapper.select20(info);
			PageInfo<Map<String, Object> > pageInfo = new PageInfo<Map<String, Object> >(rows);
			data.put("rows", pageInfo.getList());
			data.put("count", pageInfo.getSize());
			data.put("total", pageInfo.getTotal());
			result = new UpmsResult(UpmsResultConstant.SUCCESS, data);
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> listBsNestData20 list error info:" + UtilJson.Obj2Str(pageBean), e);
		}
		return result;
	}

	@Override
	public List<Map<String, Object> > listBsNestData20(Map<String, Object> map) {
		try{
			List<Map<String, Object> > rows = bsNestDataCustomMapper.select20(map);
			return rows;
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> listBsNestData20 list error map:" + UtilJson.Obj2Str(map), e);
		}
		return null;
	}

public static void main(String[] args) {
	String ss = "3";
	String cc = "56";
	double value = Double.valueOf(ss.toString());
	double value1 = Double.valueOf(cc.toString());
	
	System.out.println((value*100/value1));
	System.out.println(String.format("%.2f", (value*100/value1)));
}

@Override
public UpmsResult export(Map<String, Object> params) {
	UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "导出成功 ");
	try {
		//获取数据库中的商家信息
		List<Map<String, Object> > datas = bsNestDataCustomMapper.select(params);
		// 表格属性列名数组
		String[] strHeader = {"厂房","脚号","吃料(克)","进窝次序","进窝时间","出窝时间","进窝体重（克）","出窝体重（克）","增重（克）","肉料比"};
		// 表格属性列名key数组
		String[] keys = {"workshopName","rfidNum","eatMtWeight","inOderEx","inTimeEx","outTimeEx","inBodyWeight","outBodyWeight","incBodyWeight","mtBodyRatio"};
			
		// 标题
		String title = "nestData";
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
		String fileName = "产蛋窝数据"+now+".xls";
		// 保存的文件路径
		String filePath = savePath + fileName;
		_log.info("jolley >> exportExcel:{}", filePath);
		// 保存文件
		OutputStream out = new FileOutputStream(filePath);
		
		UtilExportExcel.exportExcels(title, strHeader, keys, datas, out, "yyyy-MM-dd HH:mm:ss");
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
	public UpmsResult export20(Map<String, Object> params) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "导出成功 ");
		try {
			//获取数据库中的商家信息
			List<Map<String, Object> > datas = bsNestDataCustomMapper.select20(params);
			// 表格属性列名数组
			String[] strHeader = {"厂房","脚号","统计次序","日期范围","体重(克)","增重(克)","吃料(克)","肉料比"};
			// 表格属性列名key数组
			String[] keys = {"workshopName","rfidNum","inOderEx","dateRange","endWeight","incBodyWeight","eatMtWeight","mtBodyRatio"};
				
			// 标题
			String title = "nestData";
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
			String fileName = "20日统计数据"+now+".xls";
			// 保存的文件路径
			String filePath = savePath + fileName;
			_log.info("jolley >> exportExcel:{}", filePath);
			// 保存文件
			OutputStream out = new FileOutputStream(filePath);
			
			UtilExportExcel.exportExcels(title, strHeader, keys, datas, out, "yyyy-MM-dd HH:mm:ss");
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
	public BsNestData analysisG780Data(String data){
		BsNestData bsNestData = null;
		try {
					if (UtilString.isEmpty(data)) {
						UtilRedis.lpush(RedisConstant.LABEL_CMD_LOG_DATA, "接收数据为空");
						return null;
					}
					int index = data.indexOf(SYS_G780_CLIENT_STARTSTR);
					//设备编号  IMEI码
					String deviceNum = data.substring(0, index);
					//实际的数据
				    data = data.substring(index, data.length());
				    
				    _log.info("jolley >> analysisG780Data 解析数据长度  data.length：{}, data：{}", data.length(),data);
					if (data.length()<SYS_G780_ANALYSISDATA_LENGTH) {
						UtilRedis.lpush(RedisConstant.LABEL_CMD_LOG_DATA, "接收数据："+data+"  长度有误");
						return null;
					}
					//解析数据
					String rfidnumStr = data.substring(SYS_G780_ANALYSISDATA_RFID_START, SYS_G780_ANALYSISDATA_RFID_END);
					String weightStr =  data.substring(SYS_G780_ANALYSISDATA_WEIGHT_START, SYS_G780_ANALYSISDATA_WEIGHT_END);
				
					Date date =null;//0使用服务器时间，1使用设备传过来的时间
					if(0 ==SYS_G780_USE_SYSTEM_DATE){
						date = new Date();
					}else{
						String dateSr = data.substring( SYS_G780_ANALYSISDATA_DATE_START, SYS_G780_ANALYSISDATA_DATE_END);
						date = UtilDate.convertDateTimeByFormate(dateSr,"yyyyMMddHHmmss");
					}
					//转换数据
					Double bodyWeight = Double.valueOf(weightStr)/10;
					_log.info("jolley >> analysisG780Data 解析数据-原始  irfid：{}, 体重：{}", rfidnumStr,weightStr);
					BsNestDataExample nestDataExample = new BsNestDataExample();
					Date someDayAgo = UtilDate.getDateBefore(date, SYS_G780_SOME_DAY_AGO);//30内的数据
					nestDataExample.createCriteria().andRfidNumEqualTo(rfidnumStr).andRemark1EqualTo("0").andInTimeGreaterThanOrEqualTo(someDayAgo);
					//取出30天以后进窝&&还没有出窝的数据
					List<BsNestData> list =  bsNestDataMapper.selectByExample(nestDataExample );
					_log.info("jolley >> analysisG780Data- 是否存在进窝数据（未出窝）："+UtilJson.Obj2Str(list));
					
					if (list.size()>0) {
						//取一条数据即可
						bsNestData = list.get(0);
						bsNestData.setOutBodyWeight(bodyWeight);
						bsNestData.setOutTime(date);
						//增重数据
//						Double incBodyWeight = (double) (Math.round((bodyWeight - bsNestData.getInBodyWeight())*100)/100)  ;
						
						Double incBodyWeight =UtilDouble.sub(bodyWeight,bsNestData.getInBodyWeight());
//						incBodyWeight = (incBodyWeight<=0)?0:incBodyWeight;
						bsNestData.setIncBodyWeight(incBodyWeight);

						Double  in = bsNestData.getInBodyWeight();
						Double  out = bsNestData.getOutBodyWeight();
						Double  eat = bsNestData.getEatMtWeight();
						
						//计算料肉比数据
						if(in!=null&&out!=null&&eat!=null&in!=0&&out!=0&&eat!=0){
							String ratio  = String.format("%.2f", (incBodyWeight*100/bsNestData.getEatMtWeight()))+"%";
							bsNestData.setMtBodyRatio(ratio);
						}else{
							bsNestData.setMtBodyRatio("");
						}
						bsNestData.setOutStatus(0);//正常出窝
						
						//判断是否产蛋
						Date inTime = bsNestData.getInTime();
						int durationTime = (int)(date.getTime() - inTime.getTime())/1000/60;//进窝出窝多少分钟 
						if((incBodyWeight*-1)>=UtilSysParam.getBsNestparam().getLayMinWeight()
								&&durationTime>=UtilSysParam.getBsNestparam().getLayMinDuration()
								&&durationTime<=UtilSysParam.getBsNestparam().getLayMaxDuration()){
							bsNestData.setOutStatus(1);  
						}
						//判断是否抱窝
						if(durationTime>UtilSysParam.getBsNestparam().getLayMinNest())
						{    
							bsNestData.setOutStatus(2);
							bsNestData.setNestDuration(durationTime);
							//计算抱窝累计时长
							Integer nestDurationSum =  bsNestDataCustomMapper.getNestDurations(rfidnumStr);
							if(nestDurationSum==null)
							{
								nestDurationSum = 0;
							}
							bsNestData.setNestDurationSum(nestDurationSum+durationTime);
						}
						
						//出窝标志
					}else{
						bsNestData = new BsNestData();
						bsNestData.setOutStatus(4);
						bsNestData.setRfidNum(rfidnumStr);
						bsNestData.setInTime(date);
						bsNestData.setInBodyWeight(bodyWeight);
						bsNestData.setInOrder(maxOrder(rfidnumStr));
						//进窝标志
					}
					//从缓存中获取厂房id
					String workshopIdStr = DeviceData.getData(deviceNum, "workshopId");
					if (StringUtil.isNotEmpty(workshopIdStr)) {
						bsNestData.setWorkshopId(Integer.parseInt(workshopIdStr));
					}
					_log.info("jolley >> analysisG780Data- 解析出-产蛋窝数据："+UtilJson.Obj2Str(bsNestData));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return bsNestData;
	}

	@Override
	public UpmsResult getBaseSysParam() {
		UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "成功 ");
		BsNestparam bsNestparam = UtilSysParam.getBsNestparam();
		if (bsNestparam==null) {
			bsNestparam = bsNestparamMapper.selectByPrimaryKey(1);
		}
		result.setData(bsNestparam);
		return result;
	}

	@Override
	public UpmsResult saveBaseSysParam(BsNestparam nestparam) {
		// TODO Auto-generated method stub
		
		nestparam.setId(1);
		bsNestparamMapper.updateByPrimaryKeySelective(nestparam);
		BsNestparam newBsNestparam = bsNestparamMapper.selectByPrimaryKey(1);
		UtilSysParam.setBsNestparam(newBsNestparam);
		UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "成功 ");
		result.setData(newBsNestparam);
		return result;
	}

	
	
	@Override
	public UpmsResult listBsNest(PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败 ");
		try{
			Map<String, Object>  info = pageBean.getCondition();
			if(!UserUtil.isAdmin()){
				info.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
			}
			PageHelper.startPage(pageBean.getPage(), pageBean.getRows());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object> > rows = bsNestDataCustomMapper.selectNestDatas(info);
			PageInfo<Map<String, Object> > pageInfo = new PageInfo<Map<String, Object> >(rows);
			data.put("rows", pageInfo.getList());
			data.put("count", pageInfo.getSize());
			data.put("total", pageInfo.getTotal());
			result = new UpmsResult(UpmsResultConstant.SUCCESS, data);
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> listBsNest list error info:" + UtilJson.Obj2Str(pageBean), e);
		}
		return result;
	}
}
