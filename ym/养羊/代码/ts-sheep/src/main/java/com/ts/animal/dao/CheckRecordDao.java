package com.ts.animal.dao;

import com.ts.animal.domain.CheckRecordDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 检查记录表
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 21:54:38
 */
@Mapper
public interface CheckRecordDao {

	CheckRecordDO get(Integer id);
	
	List<CheckRecordDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(CheckRecordDO checkRecord);
	
	int update(CheckRecordDO checkRecord);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
