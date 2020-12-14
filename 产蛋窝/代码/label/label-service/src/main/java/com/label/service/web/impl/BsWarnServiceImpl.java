/**
 * 
 */
package com.label.service.web.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.label.common.constant.RedisConstant;
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.entity.PageBean;
import com.label.common.model.base.BsNestData;
import com.label.common.model.base.BsWarnCus;
import com.label.common.util.UserUtil;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.UtilString;
import com.label.common.util.redis.UtilRedis;
import com.label.dao.auto.BsWarnMapper;
import com.label.dao.custom.BsWarnCustomMapper;
import com.label.service.web.BsWarnService;
import com.label.task.device.DeviceData;
import com.label.task.util.UtilSysParam;

/**
 * @author jolley
 *
 */
@Service
@Transactional
public class BsWarnServiceImpl implements BsWarnService {

	private Logger _log = LoggerFactory.getLogger(BsWarnServiceImpl.class);
	//解析G780的参数
   private static String SYS_G780_CLIENT_STARTSTR = UtilPropertiesFile.getInstance().get("sys_g780_client_startStr"); 
	 @Autowired
	BsWarnMapper BsWarnMapper;

	@Autowired
    BsWarnCustomMapper BsWarnCustomMapper;
	    
	@Override
	public void checkBsWarn(String fromData,BsNestData bsNestData) {
		int index = fromData.indexOf(SYS_G780_CLIENT_STARTSTR);
		//设备编号  IMEI码
		String deviceNum = fromData.substring(0, index);
		
		long outTime = bsNestData.getOutTime().getTime();  
		long inTime = bsNestData.getInTime().getTime();  
		int mini = (int) ((outTime - inTime) / (1000 * 60 ));
		String beyonText = "";
		int hour = mini/60;
		int minis = mini%60;
		if (minis==0) {
			beyonText = hour+"小时";
		}else{
			beyonText = hour+"小时"+minis+"分";
		}
		
		if (mini>4* 60) {
			BsWarnCus warn = new BsWarnCus();
			//从缓存中获取厂房id
			String cId = DeviceData.getData(deviceNum, "dptId");
			warn.setDptId(cId);
			warn.setWarnTime(new Date());
			warn.setRfidNum(bsNestData.getRfidNum());
			warn.setExceedTime((double) mini);
			warn.setWsId(bsNestData.getWorkshopId());
			warn.setWarnInfo("告警：标签号【"+bsNestData.getRfidNum()+"】出窝超时"+beyonText+"，请留意！");
			UtilRedis.lpush(RedisConstant.LABEL_OVERTIME_WARN_DATA, UtilJson.Obj2Str(warn));
			int count = BsWarnMapper.insertSelective(warn);
			if(count>0){
				_log.info("jolley >> checkBsWarn  添加超时告警：{}",UtilJson.Obj2Str(warn));
			}else{
				_log.info("jolley >> checkBsWarn  添加失败：{}",UtilJson.Obj2Str(warn));
			}
		}else{
			_log.info("jolley >> checkBsWarn 标签号【{}】 未超时",bsNestData.getRfidNum());
		}
	
		
		//判断是否产蛋
		if(bsNestData.getOutStatus()==1){
			BsWarnCus warn = new BsWarnCus();
			//从缓存中获取厂房id
			String cId = DeviceData.getData(deviceNum, "dptId");
			warn.setDptId(cId);
			warn.setWarnTime(new Date());
			warn.setRfidNum(bsNestData.getRfidNum());
			warn.setExceedTime((double) mini);
			warn.setWsId(bsNestData.getWorkshopId());
			warn.setWarnInfo("告警：标签号【"+bsNestData.getRfidNum()+"】出窝体重减轻"+bsNestData.getIncBodyWeight()*-1+" 克，系统判定为已产蛋，请留意！");
			UtilRedis.lpush(RedisConstant.LABEL_OVERTIME_WARN_DATA, UtilJson.Obj2Str(warn));
			int count = BsWarnMapper.insertSelective(warn);
			if(count>0){
				_log.info("jolley >> checkBsWarn  添加已产蛋告警：{}",UtilJson.Obj2Str(warn));
			}else{
				_log.info("jolley >> checkBsWarn  添加失败：{}",UtilJson.Obj2Str(warn));
			}
		}
		//判断是否抱窝
		if(bsNestData.getOutStatus()==2)
		{
			BsWarnCus warn = new BsWarnCus();
			//从缓存中获取厂房id
			String cId = DeviceData.getData(deviceNum, "dptId");
			warn.setDptId(cId);
			warn.setWarnTime(new Date());
			warn.setRfidNum(bsNestData.getRfidNum());
			warn.setExceedTime((double) mini);
			warn.setWsId(bsNestData.getWorkshopId());
			warn.setWarnInfo("告警：标签号【"+bsNestData.getRfidNum()+"】出窝超过【鉴定抱窝最小时长:"+UtilSysParam.getBsNestparam().getLayMinNest()+"分钟】，系统判定为已抱窝，请留意！");
			UtilRedis.lpush(RedisConstant.LABEL_OVERTIME_WARN_DATA, UtilJson.Obj2Str(warn));
			int count = BsWarnMapper.insertSelective(warn);
			if(count>0){
				_log.info("jolley >> checkBsWarn  添加已抱窝告警：{}",UtilJson.Obj2Str(warn));
			}else{
				_log.info("jolley >> checkBsWarn  添加失败：{}",UtilJson.Obj2Str(warn));
			}
		}
		
		
	}

	@Override
	public UpmsResult listBsWarn(PageBean<Map<String, Object> > pageBean) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败 ");
		try{
			Map<String, Object>  info = pageBean.getCondition();
			if (!UtilString.verification(""+info.get("startExceed"), "^\\d+$")) {
				info.put("startExceed", null);
			}
			if(!UserUtil.isAdmin()){
				info.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
			}
			PageHelper.startPage(pageBean.getPage(), pageBean.getRows());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object> > rows = BsWarnCustomMapper.select(info);
			PageInfo<Map<String, Object> > pageInfo = new PageInfo<Map<String, Object> >(rows);
			data.put("rows", pageInfo.getList());
			data.put("count", pageInfo.getSize());
			data.put("total", pageInfo.getTotal());
			result = new UpmsResult(UpmsResultConstant.SUCCESS, data);
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> listBsWarn list error info:" + UtilJson.Obj2Str(pageBean), e);
		}
		return result;
	}

//	private 
}
