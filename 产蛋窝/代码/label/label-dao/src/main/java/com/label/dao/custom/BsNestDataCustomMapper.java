package com.label.dao.custom;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BsNestDataCustomMapper {

	List<Map<String, Object>> select(Map<String, Object> map);
	
	Integer maxOrder(@Param("rfidNum") String rfidNum);

	List<Map<String, Object>> select20(Map<String, Object> map);
	
	Map<String, Object> select20Static(Map<String, Object> map);

	Integer maxOrder20(@Param("rfidNum") String rfidNum);
	
	Integer getNestDurations(@Param("rfidNum") String rfidNum);
	
	List<Map<String, Object>> selectNestDatas(Map<String, Object> map);
}
