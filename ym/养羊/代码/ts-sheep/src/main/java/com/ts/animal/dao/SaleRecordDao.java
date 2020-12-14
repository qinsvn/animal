package com.ts.animal.dao;

import com.ts.animal.domain.SaleRecordDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 销售记录表
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:02:22
 */
@Mapper
public interface SaleRecordDao {

	SaleRecordDO get(Integer id);
	
	List<SaleRecordDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SaleRecordDO saleRecord);
	
	int update(SaleRecordDO saleRecord);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
