package com.ts.animal.dao;

import com.ts.animal.domain.CustomerDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 客户表
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 21:57:31
 */
@Mapper
public interface CustomerDao {

	CustomerDO get(Integer id);
	
	List<CustomerDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(CustomerDO customer);
	
	int update(CustomerDO customer);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
