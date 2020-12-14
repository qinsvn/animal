package com.ts.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.ts.system.dao.ScoreDao;
import com.ts.system.domain.ScoreDO;
import com.ts.system.service.ScoreService;



@Service
public class ScoreServiceImpl implements ScoreService {
	@Autowired
	private ScoreDao scoreDao;
	
	@Override
	public ScoreDO get(Integer cid){
		return scoreDao.get(cid);
	}
	
	@Override
	public List<ScoreDO> list(Map<String, Object> map){
		return scoreDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return scoreDao.count(map);
	}
	
	@Override
	public int save(ScoreDO score){
		return scoreDao.save(score);
	}
	
	@Override
	public int update(ScoreDO score){
		return scoreDao.update(score);
	}
	
	@Override
	public int remove(Integer cid){
		return scoreDao.remove(cid);
	}
	
	@Override
	public int batchRemove(Integer[] cids){
		return scoreDao.batchRemove(cids);
	}
	
}
