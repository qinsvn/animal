package com.label.service.web;

import java.util.Map;

import com.label.common.constant.UpmsResult;
import com.label.common.entity.PageBean;
import com.label.common.model.base.BsNestData;

/**
 *告警信息service
 * @author jolley
 *
 */
public interface BsWarnService {
	
	/**
	 * 添加告警信息
	 */
	void checkBsWarn(String fromData,BsNestData bsNestData) ;
	
	
	/**
	 * 按条件获取列表
	 * @param result
	 * @param map
	 */
	UpmsResult listBsWarn(PageBean<Map<String, Object> > pageBean);
	
	
	
}
