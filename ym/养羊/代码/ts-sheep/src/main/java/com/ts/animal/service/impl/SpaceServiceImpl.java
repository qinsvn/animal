package com.ts.animal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ts.animal.dao.SpaceDao;
import com.ts.animal.domain.SpaceDO;
import com.ts.animal.service.SpaceService;
import com.ts.common.utils.DataPerUitl;
import com.ts.common.utils.Result;
import com.ts.common.utils.ShiroUtils;


@Transactional
@Service
public class SpaceServiceImpl implements SpaceService {
	@Autowired
	private SpaceDao spaceDao;
	
	@Override
	public SpaceDO get(Integer id){
		return spaceDao.get(id);
	}
	
	@Override
	public List<SpaceDO> list(Map<String, Object> map){
		map.putAll(DataPerUitl.deptPer());
		return spaceDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		map.putAll(DataPerUitl.deptPer());
		return spaceDao.count(map);
	}
	
	@Override
	public int save(Result result,SpaceDO space){
		if (space.getDeptId()==null) {
			space.setDeptId((int)ShiroUtils.getUser().getDeptId().longValue());
		}
		space.setCreateUser((int)ShiroUtils.getUserId().longValue());
		space.setCreateTime(new Date());
		return spaceDao.save(space);
	}
	
	@Override
	public int update(Result result,SpaceDO space){
		if (space.getDeptId()==null) {
			space.setDeptId((int)ShiroUtils.getUser().getDeptId().longValue());
		}
		space.setUpdateUser((int)ShiroUtils.getUserId().longValue());
		space.setUpdateTime(new Date());
		return spaceDao.update(space);
	}
	
	@Override
	public int remove(Integer id){
		return spaceDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return spaceDao.batchRemove(ids);
	}
	
}
