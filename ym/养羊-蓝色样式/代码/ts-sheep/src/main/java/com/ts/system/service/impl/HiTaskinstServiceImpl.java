package com.ts.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.ts.system.dao.HiTaskinstDao;
import com.ts.system.domain.HiTaskinstDO;
import com.ts.system.service.HiTaskinstService;



@Service
public class HiTaskinstServiceImpl implements HiTaskinstService {
	@Autowired
	private HiTaskinstDao hiTaskinstDao;
	
	@Override
	public HiTaskinstDO get(String id){
		return hiTaskinstDao.get(id);
	}
	
	@Override
	public List<HiTaskinstDO> list(Map<String, Object> map){
		return hiTaskinstDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return hiTaskinstDao.count(map);
	}
	
	@Override
	public int save(HiTaskinstDO hiTaskinst){
		return hiTaskinstDao.save(hiTaskinst);
	}
	
	@Override
	public int update(HiTaskinstDO hiTaskinst){
		return hiTaskinstDao.update(hiTaskinst);
	}
	
	@Override
	public int remove(String id){
		return hiTaskinstDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return hiTaskinstDao.batchRemove(ids);
	}
	
}
