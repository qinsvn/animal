package com.ts.animal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExInputsDao {

	List<Map<String,Object>> list(Map<String,Object> map);

	int count(Map<String, Object> map);
}
