package com.ts.common.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ts.common.dao.GeneratorMapper;
import com.ts.common.service.GeneratorService;
import com.ts.common.utils.GenUtils;


@Service
public class GeneratorServiceImpl implements GeneratorService {
	@Autowired
	GeneratorMapper generatorMapper;

	@Override
	public List<Map<String, Object>> list() {
		List<Map<String, Object>> list = generatorMapper.list();
		return list;
	}

	@Override
	public byte[] generatorCode(Map<String, Object> datas) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		String tableName = (String)datas.get("tableName");
		String className = (String)datas.get("className");
		String model = (String)datas.get("model");
		@SuppressWarnings("unchecked")
		List<Map<String, String>>  conditionColumns = (List<Map<String, String>> )datas.get("conditionColumns");
		@SuppressWarnings("unchecked")
		List<Map<String, String>>  resultColumns = (List<Map<String, String>> )datas.get("resultColumns");
		//查询表信息
		Map<String, String> table = generatorMapper.get(tableName);
		//查询列信息
		List<Map<String, String>> allColumns = generatorMapper.listColumns(tableName);
		//生成代码
		GenUtils.generatorCode(table, className,model,conditionColumns,resultColumns,allColumns, zip);
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
	
	@Override
	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = generatorMapper.get(tableName);
			//查询列信息
			List<Map<String, String>> columns = generatorMapper.listColumns(tableName);
			//生成代码
			GenUtils.generatorCode(table,null,null,null,null, columns, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	@Override
	public Map<String, Object> getTableInfo(String tableName) {
		Map<String, Object> ret = new HashMap<String, Object> ();
		//配置信息
        Configuration config = GenUtils.getConfig();
		List<Map<String, String>> columns = generatorMapper.listColumns(tableName);//查询表信息
		Map<String, String> table = generatorMapper.get(tableName);
		String className = GenUtils.tableToJava(table.get("tableName"), config.getString("tablePrefix"), config.getString("autoRemovePre"));
		ret.put("className", className);
		ret.put("model", config.getString("model"));
		ret.put("columns", columns);
		return ret;
	}

}
