package com.ts.system.service;

import com.ts.system.domain.ProcessRecordDO;

import java.util.List;
import java.util.Map;

/**
 * 工单流转记录
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-01-13 17:18:39
 */
public interface ProcessRecordService {
	
	ProcessRecordDO get(Integer prId);
	
	List<ProcessRecordDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProcessRecordDO processRecord);
	
	int update(ProcessRecordDO processRecord);
	
	int remove(Integer prId);
	
	int batchRemove(Integer[] prIds);
}
