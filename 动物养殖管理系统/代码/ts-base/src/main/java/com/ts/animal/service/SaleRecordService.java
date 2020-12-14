package com.ts.animal.service;

import com.ts.animal.domain.SaleRecordDO;

import java.util.List;
import java.util.Map;
import com.ts.common.utils.Result;

/**
 * 销售记录表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:02:22
 */
public interface SaleRecordService {
	
	SaleRecordDO get(Integer id);
	
	List<SaleRecordDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Result result,SaleRecordDO saleRecord);
	
	 int saveByNum(Result result,SaleRecordDO saleRecord);
	 
	 int update(Result result,SaleRecordDO saleRecord);
	
	int remove(Integer id);

	int batchRemove(Integer[] ids);
	
	List<Map<String, Object>> exList(Map<String, Object> map);

}
