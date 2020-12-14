package com.ts.system.service;

import com.ts.system.domain.HiTaskinstDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2019-11-14 10:59:45
 */
public interface HiTaskinstService {
	
	HiTaskinstDO get(String id);
	
	List<HiTaskinstDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(HiTaskinstDO hiTaskinst);
	
	int update(HiTaskinstDO hiTaskinst);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
