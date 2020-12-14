package com.ts.animal.dao;

import com.ts.animal.domain.SpaceDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 场地表
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:03:31
 */
@Mapper
public interface SpaceDao {

	SpaceDO get(Integer id);
	
	List<SpaceDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SpaceDO space);
	
	int update(SpaceDO space);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
