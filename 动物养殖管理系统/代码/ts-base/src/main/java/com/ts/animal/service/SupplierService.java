package com.ts.animal.service;

import com.ts.animal.domain.SupplierDO;

import java.util.List;
import java.util.Map;
import com.ts.common.utils.Result;

/**
 * 供应商表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:05:30
 */
public interface SupplierService {
	
	SupplierDO get(Integer id);
	
	List<SupplierDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Result result,SupplierDO supplier);
	
	 int update(Result result,SupplierDO supplier);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
