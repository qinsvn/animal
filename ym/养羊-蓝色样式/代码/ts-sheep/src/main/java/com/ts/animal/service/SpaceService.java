package com.ts.animal.service;

import com.ts.animal.domain.SpaceDO;

import java.util.List;
import java.util.Map;
import com.ts.common.utils.Result;

/**
 * 场地表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:03:31
 */
public interface SpaceService {
	
	SpaceDO get(Integer id);
	
	List<SpaceDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Result result,SpaceDO space);
	
	 int update(Result result,SpaceDO space);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
