package com.ts.animal.dao;

import com.ts.animal.domain.SubstandardDataDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 耗损数据表
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:04:36
 */
@Mapper
public interface SubstandardDataDao {

	SubstandardDataDO get(Integer id);
	
	List<SubstandardDataDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SubstandardDataDO substandardData);
	
	int update(SubstandardDataDO substandardData);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
