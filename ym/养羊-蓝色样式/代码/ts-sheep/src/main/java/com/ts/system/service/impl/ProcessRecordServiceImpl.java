package com.ts.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.ts.system.dao.ProcessRecordDao;
import com.ts.system.domain.ProcessRecordDO;
import com.ts.system.service.ProcessRecordService;



@Service
public class ProcessRecordServiceImpl implements ProcessRecordService {
	@Autowired
	private ProcessRecordDao processRecordDao;
	
	@Override
	public ProcessRecordDO get(Integer prId){
		return processRecordDao.get(prId);
	}
	
	@Override
	public List<ProcessRecordDO> list(Map<String, Object> map){
		return processRecordDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return processRecordDao.count(map);
	}
	
	@Override
	public int save(ProcessRecordDO processRecord){
		return processRecordDao.save(processRecord);
	}
	
	@Override
	public int update(ProcessRecordDO processRecord){
		return processRecordDao.update(processRecord);
	}
	
	@Override
	public int remove(Integer prId){
		return processRecordDao.remove(prId);
	}
	
	@Override
	public int batchRemove(Integer[] prIds){
		return processRecordDao.batchRemove(prIds);
	}
	
}
