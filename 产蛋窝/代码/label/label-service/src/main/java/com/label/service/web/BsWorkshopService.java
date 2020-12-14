package com.label.service.web;

import java.util.List;
import java.util.Map;

import com.label.common.constant.UpmsResult;
import com.label.common.entity.PageBean;
import com.label.common.model.base.BsWorkshop;

/**
 * 厂房service
 * @author jolley
 *
 */
public interface BsWorkshopService {
	
	/**
	 * 添加厂房
	 */
	UpmsResult createBsWorkshop(BsWorkshop  bsWorkshop);
	
	/**
	 * 更新厂房
	 * @param result
	 * @param company
	 */
	UpmsResult updateBsWorkshop(BsWorkshop  bsWorkshop);
	
	/**
	 * 获取厂房编号  或者 id
	 * @param result
	 * @param map
	 */
	UpmsResult getBsWorkshop(Map<String, Object> map);
	
	
	/**
	 * 按条件获取列表
	 * @param result
	 * @param map
	 */
	UpmsResult listBsWorkshop(PageBean<Map<String, Object> > pageBean);
	
	
	/**
	 * 批量删除
	 * @param map
	 * @return
	 */
	UpmsResult deleteBsWorkshops(List<Integer> list);
	
}
