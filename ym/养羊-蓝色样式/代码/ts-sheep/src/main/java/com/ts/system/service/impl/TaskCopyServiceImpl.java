package com.ts.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.ts.system.dao.TaskCopyDao;
import com.ts.system.domain.TaskCopyDO;
import com.ts.system.service.TaskCopyService;



@Service
public class TaskCopyServiceImpl implements TaskCopyService {
	@Autowired
	private TaskCopyDao taskCopyDao;
	
	@Override
	public TaskCopyDO get(Long id){
		return taskCopyDao.get(id);
	}
	
	@Override
	public List<TaskCopyDO> list(Map<String, Object> map){
		return taskCopyDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return taskCopyDao.count(map);
	}
	
	@Override
	public int save(TaskCopyDO taskCopy){
		return taskCopyDao.save(taskCopy);
	}
	
	@Override
	public int update(TaskCopyDO taskCopy){
		return taskCopyDao.update(taskCopy);
	}
	
	@Override
	public int remove(Long id){
		return taskCopyDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return taskCopyDao.batchRemove(ids);
	}
	
}
