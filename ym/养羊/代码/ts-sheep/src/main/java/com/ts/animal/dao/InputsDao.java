package com.ts.animal.dao;

import com.ts.animal.domain.InputsDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 投入品表
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:01:15
 */
@Mapper
public interface InputsDao {

	InputsDO get(Integer id);
	
	List<InputsDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(InputsDO inputs);
	
	int update(InputsDO inputs);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
