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
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.entity.PageBean;
import com.label.common.model.base.BsDevice;
import com.label.common.util.UserUtil;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilString;
import com.label.dao.auto.BsDeviceMapper;
import com.label.dao.custom.BsDeviceCustomMapper;
import com.label.service.web.BsDeviceService;
import com.label.task.device.DeviceData;

/**
 * @author jolley
 *
 */
@Service
@Transactional
public class BsDeviceServiceImpl implements BsDeviceService {

	private Logger _log = LoggerFactory.getLogger(BsDeviceServiceImpl.class);
	
	 @Autowired
	BsDeviceMapper BsDeviceMapper;

	@Autowired
	    BsDeviceCustomMapper BsDeviceCustomMapper;
	    
	@Override
	public UpmsResult createBsDevice(BsDevice BsDevice) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "创建失败 ");
		
		//后台增加判断
		if (BsDevice==null) {
			return result;
		}
		if (UtilString.isEmpty(BsDevice.getDeviceNum())) {
			result = new UpmsResult(UpmsResultConstant.FAILED, "设备编号不能为空");	
			return result;
		}
		if (UtilString.isEmpty(BsDevice.getWorkshopId())||-1==BsDevice.getWorkshopId()) {
			result = new UpmsResult(UpmsResultConstant.FAILED, "所厂房不能为空");	
			return result;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceNum", BsDevice.getDeviceNum());
		List<Map<String,Object>>list =BsDeviceCustomMapper.select(map);
		if(list!=null&&list.size()>0){
			result = new UpmsResult(UpmsResultConstant.FAILED, "编号已经存在");	
			return result;
		}
		
		BsDevice.setUpdateTime(new Date());
		int count = BsDeviceMapper.insertSelective(BsDevice);
		if(count>0){
			result = new UpmsResult(UpmsResultConstant.SUCCESS, "创建成功 ");
			DeviceData.initData();
		}else{
			_log.info("jolley >> BsDeviceServiceImpl 添加失败，info："+UtilJson.Obj2Str(BsDevice));
		}
		return result;
	}

	@Override
	public UpmsResult updateBsDevice(BsDevice BsDevice) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "修改失败 ");
		//后台增加判断
		if (BsDevice==null) {
			return result;
		}
		if (UtilString.isEmpty(BsDevice.getDeviceNum())) {
			result = new UpmsResult(UpmsResultConstant.FAILED, "设备编号不能为空");	
			return result;
		}

		if (UtilString.isEmpty(BsDevice.getWorkshopId())) {
			result = new UpmsResult(UpmsResultConstant.FAILED, "所属厂房不能为空");	
			return result;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("notId", BsDevice.getId());
		map.put("deviceNum", BsDevice.getDeviceNum());
		List<Map<String,Object>>list =BsDeviceCustomMapper.select(map);
		if(list!=null&&list.size()>0){
			result = new UpmsResult(UpmsResultConstant.FAILED, "编号已经存在");	
			return result;
		}
		
		BsDevice.setUpdateTime(new Date());
		int count = BsDeviceMapper.updateByPrimaryKeySelective(BsDevice);
		if(count>0){
			result = new UpmsResult(UpmsResultConstant.SUCCESS, "修改成功 ");
			DeviceData.initData();
		}else{
			_log.info("jolley >> BsDeviceServiceImpl 修改失败，info："+UtilJson.Obj2Str(BsDevice));
		}
		return result;
	}

	@Override
	public UpmsResult getBsDevice(Map<String, Object> map) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败");
		try{
			List<Map<String,Object>>list =BsDeviceCustomMapper.select(map);
			if(list!=null&&list.size()>0){
				result = new UpmsResult(UpmsResultConstant.SUCCESS, list.get(0));				
			}else{
				result = new UpmsResult(UpmsResultConstant.FAILED, "数据不存在");
			}
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> getBsDevice info error map:" + UtilJson.Obj2Str(map), e);
		}
		return result;
	}

	@Override
	public UpmsResult listBsDevice(PageBean<Map<String, Object> > pageBean) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败 ");
		try{
			Map<String, Object>  info = pageBean.getCondition();
			if(!UserUtil.isAdmin()){
				info.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
			}
			PageHelper.startPage(pageBean.getPage(), pageBean.getRows());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object> > rows = BsDeviceCustomMapper.select(info);
			PageInfo<Map<String, Object> > pageInfo = new PageInfo<Map<String, Object> >(rows);
			data.put("rows", pageInfo.getList());
			data.put("count", pageInfo.getSize());
			data.put("total", pageInfo.getTotal());
			result = new UpmsResult(UpmsResultConstant.SUCCESS, data);
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> listBsDevice list error info:" + UtilJson.Obj2Str(pageBean), e);
		}
		return result;
	}

	@Override
	public UpmsResult deleteBsDevices(List<Integer> list) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "删除成功 ");
		for (Integer id:list) {
			BsDeviceMapper.deleteByPrimaryKey(id);
		}
		DeviceData.initData();
		return result;
	}

}
