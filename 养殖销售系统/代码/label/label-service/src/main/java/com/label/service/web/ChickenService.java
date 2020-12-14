package com.label.service.web;

import java.util.Map;

import com.label.common.constant.UpmsResult;
import com.label.common.model.base.ChickenData;


/**
 * 设备service
 * @author jolley
 *
 */
public interface ChickenService extends CommonService {

	/**
	 * 修改
	 * @param result
	 * @param map
	 */
	public UpmsResult update(ChickenData chickenData);	
	
	public UpmsResult exportChickenData(Map<String, Object> params);
	
	
	public UpmsResult getChickenInfoByRFID(String rfidNum);
}
