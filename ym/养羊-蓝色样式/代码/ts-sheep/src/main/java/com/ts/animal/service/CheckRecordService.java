package com.ts.animal.service;

import java.util.List;
import java.util.Map;

import com.ts.animal.domain.CheckRecordDO;
import com.ts.common.utils.Result;

/**
 * 检查记录表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 21:54:38
 */
public interface CheckRecordService {
	
	CheckRecordDO get(Integer id);
	
	List<CheckRecordDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Result result,CheckRecordDO checkRecord);
	
	 int saveByNum(Result result,CheckRecordDO checkRecord);
	
	 int update(Result result,CheckRecordDO checkRecord);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
