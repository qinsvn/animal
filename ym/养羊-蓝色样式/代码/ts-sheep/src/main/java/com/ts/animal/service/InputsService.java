package com.ts.animal.service;

import com.ts.animal.domain.InputsDO;

import java.util.List;
import java.util.Map;

import com.ts.common.utils.Result;

/**
 * 投入品表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:01:15
 */
public interface InputsService {
	
	InputsDO get(Integer id);
	
	List<InputsDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Result result,InputsDO inputs);
	
	 int update(Result result,InputsDO inputs);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	List<Map<String, Object>> exList(Map<String, Object> map);

	int exCount(Map<String, Object> map);

	public int update(InputsDO inputs);
}
