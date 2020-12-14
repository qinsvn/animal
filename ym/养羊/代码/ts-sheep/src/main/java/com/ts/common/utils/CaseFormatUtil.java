package com.ts.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;

public class CaseFormatUtil {

	public static List<Map<String, Object>> lowerUnderscore(List<Map<String, Object>> list ){
		List<Map<String, Object>> ret  = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			Map<String, Object> retMap = Maps.newHashMap();
			for(Entry<String, Object> entry : map.entrySet()){
			    String mapKey = entry.getKey();
			    Object mapValue = entry.getValue();
			    retMap.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,mapKey), mapValue);
			}
			ret.add(retMap);
		}
		 return ret;
	}
}
