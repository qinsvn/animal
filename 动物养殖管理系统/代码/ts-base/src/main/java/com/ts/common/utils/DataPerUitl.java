package com.ts.common.utils;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Objects;
import com.ts.common.config.TsConfig;
/**
 * 数据权限工具类
 * @author admin
 *
 */
public class DataPerUitl {

	public static Map<String,Object> userPer(){
		Map<String,Object>  map = new HashMap<>();
		if (!Objects.equal(TsConfig.getProperty("ts.manage.maxOrg"), ShiroUtils.getUser().getDeptId().toString())) {
			map.put("curUserId", ShiroUtils.getUserId());
			map.put("keyForCurUserId", "user_id");
		}
		return map;
	} 
	public static Map<String,Object> userPer(String keyForCurUserId){
		Map<String,Object>  map = new HashMap<>();
		if (!Objects.equal(TsConfig.getProperty("ts.manage.maxOrg"), ShiroUtils.getUser().getDeptId().toString())) {
			map.put("curUserId", ShiroUtils.getUserId());
			map.put("keyForCurUserId", keyForCurUserId);
		}
		return map;
	}
	
	
	public static Map<String,Object> deptPer(){
		Map<String,Object>  map = new HashMap<>();
		if (!ShiroUtils.isMechanismOrg()) {
			map.put("curDeptId", ShiroUtils.getUser().getDeptId());
			map.put("keyForCurDeptId", "dept_id");
		}
		return map;
	}
	public static Map<String,Object> deptPer(String keyForCurDeptId){
		Map<String,Object>  map = new HashMap<>();
		if (!ShiroUtils.isMechanismOrg()) {
			map.put("curDeptId", ShiroUtils.getUser().getDeptId());
			map.put("keyForCurDeptId", keyForCurDeptId);
		}
		return map;
	}
	
	
}
