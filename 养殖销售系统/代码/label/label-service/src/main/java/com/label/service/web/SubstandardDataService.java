package com.label.service.web;

import java.util.List;
import java.util.Map;

import com.label.common.constant.UpmsResult;
import com.label.common.entity.CollectionData;

/**
 * 设备service
 * @author jolley
 *
 */
public interface SubstandardDataService extends CommonService {
	public UpmsResult selectSubstandardDataSum(Map<String, Object> map);
	
	public UpmsResult approval(List<Integer> list);
	
	public UpmsResult exportSubstandardData(Map<String, Object> params);
	
	public UpmsResult addSubstandardData(CollectionData data) throws Exception;
}
