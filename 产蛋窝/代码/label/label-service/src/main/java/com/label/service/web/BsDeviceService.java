package com.label.service.web;

import java.util.List;
import java.util.Map;

import com.label.common.constant.UpmsResult;
import com.label.common.entity.PageBean;
import com.label.common.model.base.BsDevice;

/**
 * 设备service
 * @author jolley
 *
 */
public interface BsDeviceService {
	
	/**
	 * 添加设备
	 */
	UpmsResult createBsDevice(BsDevice  BsDevice);
	
	/**
	 * 更新设备
	 * @param result
	 * @param company
	 */
	UpmsResult updateBsDevice(BsDevice  BsDevice);
	
	/**
	 * 获取设备编号  或者 id
	 * @param result
	 * @param map
	 */
	UpmsResult getBsDevice(Map<String, Object> map);
	
	
	/**
	 * 按条件获取列表
	 * @param result
	 * @param map
	 */
	UpmsResult listBsDevice(PageBean<Map<String, Object> > pageBean);
	
	
	/**
	 * 批量删除
	 * @param map
	 * @return
	 */
	UpmsResult deleteBsDevices(List<Integer> list);
	
}
