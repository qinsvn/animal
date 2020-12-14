package com.ts.animal.dao;

import com.ts.animal.domain.ImmuneRecordDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 检疫记录表
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 21:59:00
 */
@Mapper
public interface ImmuneRecordDao {

	ImmuneRecordDO get(Integer id);
	
	List<ImmuneRecordDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ImmuneRecordDO immuneRecord);
	
	int update(ImmuneRecordDO immuneRecord);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
