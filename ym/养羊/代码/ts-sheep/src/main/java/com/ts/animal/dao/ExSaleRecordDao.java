package com.ts.animal.dao;

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
public interface ExSaleRecordDao {
	List<Map<String,Object>> list(Map<String,Object> map);
	
}
