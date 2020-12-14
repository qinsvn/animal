package com.ts.system.dao;

import com.ts.system.domain.TaskCopyDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-01-13 10:45:59
 */
@Mapper
public interface TaskCopyDao {

	TaskCopyDO get(Long id);
	
	List<TaskCopyDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(TaskCopyDO taskCopy);
	
	int update(TaskCopyDO taskCopy);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
