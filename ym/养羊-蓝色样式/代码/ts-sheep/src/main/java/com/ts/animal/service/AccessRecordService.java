package com.ts.animal.service;

import com.ts.animal.domain.AccessRecordDO;
import com.ts.common.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * 进出记录表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-06-06 15:45:53
 */
public interface AccessRecordService {
	
	AccessRecordDO get(Integer id);
	
	List<AccessRecordDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Result result,AccessRecordDO accessRecord);

	public void saveBySystem(String num);
	
	int update(Result result,AccessRecordDO accessRecord);
	
	int remove(Result result,Integer id);
	
	int batchRemove(Result result,Integer[] ids);
}
