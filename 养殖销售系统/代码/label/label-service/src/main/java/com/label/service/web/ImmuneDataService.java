package com.label.service.web;

import java.util.Map;

import com.label.common.constant.UpmsResult;
import com.label.common.model.base.ImmuneData;

/**
 * 设备service
 * @author jolley
 *
 */
public interface ImmuneDataService extends CommonService {
	public UpmsResult update(ImmuneData immuneData);
	
	public UpmsResult exportImmuneData(Map<String, Object> params) ;
}
