package com.ts.animal.service;

import com.ts.animal.domain.ImmuneRecordDO;

import java.util.List;
import java.util.Map;

import com.ts.common.utils.Result;
/**
 * 检疫记录表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 21:59:00
 */
public interface ImmuneRecordService {
	
	ImmuneRecordDO get(Integer id);
	
	List<ImmuneRecordDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Result result,ImmuneRecordDO immuneRecord);
	
	 int saveByNum(Result result,ImmuneRecordDO immuneRecord);
	 
	 int update(Result result,ImmuneRecordDO immuneRecord);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
