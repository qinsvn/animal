package com.ts.system.dao;

import com.ts.system.domain.HiTaskinstDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2019-11-14 10:59:45
 */
@Mapper
public interface HiTaskinstDao {

	HiTaskinstDO get(String id);
	
	List<HiTaskinstDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(HiTaskinstDO hiTaskinst);
	
	int update(HiTaskinstDO hiTaskinst);
	
	int remove(String ID_);
	
	int batchRemove(String[] ids);
}
