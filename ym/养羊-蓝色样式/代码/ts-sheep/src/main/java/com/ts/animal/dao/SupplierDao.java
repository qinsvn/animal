package com.ts.animal.dao;

import com.ts.animal.domain.SupplierDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 供应商表
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:05:30
 */
@Mapper
public interface SupplierDao {

	SupplierDO get(Integer id);
	
	List<SupplierDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SupplierDO supplier);
	
	int update(SupplierDO supplier);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
