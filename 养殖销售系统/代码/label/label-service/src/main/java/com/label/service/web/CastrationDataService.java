package com.label.service.web;

import java.util.Map;

import com.label.common.constant.UpmsResult;
import com.label.common.model.base.CastrationData;

/**
 * 设备service
 * @author jolley
 *
 */
public interface CastrationDataService extends CommonService {
	/**
	 * 修改
	 * @param result
	 * @param map
	 */
	public UpmsResult update(CastrationData castrationData );	
	

	public UpmsResult exportCastrationData(Map<String, Object> params);
}
