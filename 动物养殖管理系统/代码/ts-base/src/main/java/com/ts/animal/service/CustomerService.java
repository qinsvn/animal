package com.ts.animal.service;

import java.util.List;
import java.util.Map;

import com.ts.animal.domain.CustomerDO;
import com.ts.common.utils.Result;

/**
 * 客户表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 21:57:31
 */
public interface CustomerService {
	
	CustomerDO get(Integer id);
	
	List<CustomerDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Result result,CustomerDO customer);
	
	 int update(Result result,CustomerDO customer);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
