package com.ts.animal.dao;

import com.ts.animal.domain.AccessRecordDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 进出记录表
 * @author bobby
 * @email bobby@126.com
 * @date 2020-06-06 15:45:53
 */
@Mapper
public interface AccessRecordDao {

	AccessRecordDO get(Integer id);
	
	List<AccessRecordDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(AccessRecordDO accessRecord);
	
	int update(AccessRecordDO accessRecord);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
