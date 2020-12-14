package com.ts.system.service;

import com.ts.system.domain.TaskCopyDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-01-13 10:45:59
 */
public interface TaskCopyService {
	
	TaskCopyDO get(Long id);
	
	List<TaskCopyDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TaskCopyDO taskCopy);
	
	int update(TaskCopyDO taskCopy);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
