/**
 * 
 */
package com.ts.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @author bobby@126.com
 * @Time 2017年9月6日
 * @description
 * 
 */
@Service
public interface GeneratorService {
	List<Map<String, Object>> list();
	
	public byte[] generatorCode(Map<String, Object> datas);

	byte[] generatorCode(String[] tableNames);
	
	Map<String, Object> getTableInfo(String tableName);
	
}
