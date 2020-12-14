package com.label.service.web;

import java.util.Map;

import com.label.common.constant.UpmsResult;
import com.label.common.entity.PageBean;

/**
 * 设备service
 * @author jolley
 *
 */
public interface OrderService extends CommonService {


	public  UpmsResult  orderSum(PageBean<Map<String, Object>> pageBean);

	UpmsResult exportOrder(Map<String, Object> params);

	UpmsResult exportOrderSum(Map<String, Object> params);
}
