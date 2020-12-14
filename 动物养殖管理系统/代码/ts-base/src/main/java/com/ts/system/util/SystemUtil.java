package com.ts.system.util;

import java.util.Map;

import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.ts.common.config.TsConfig;
import com.ts.common.domain.DictDO;
import com.ts.system.domain.DeptDO;
import com.ts.system.domain.UserDO;

public class SystemUtil {
	 private static Map<Long, UserDO> users;
	 private static Map<Long, DeptDO> depts;
	 private static ThymeleafViewResolver viewResolver;
	 private static Map<Object,Map<Object, DictDO>> dicts;
	 
	 /**
	  * 系统所有用户
	  * @return
	  */
	public  static Map<Long, UserDO> getUsers() {
		return users;
	}
	public  static void setUsers(Map<Long, UserDO> users) {
		SystemUtil.users = users;
		 Map<String, Object> vars = Maps.newHashMap();
         Object obj = JSON.toJSON(users);
         vars.put("sysUsers", obj);
         viewResolver.setStaticVariables(vars);
	}
	/**
	 * 系统所有部门
	 * @return
	 */
	public  static Map<Long, DeptDO> getDepts() {
		return depts;
	}
	public  static void setDepts(Map<Long, DeptDO> depts) {
		 SystemUtil.depts = depts;
		 Map<String, Object> vars = Maps.newHashMap();
		  Object obj = JSON.toJSON(depts);
         vars.put("sysDepts", obj);
         viewResolver.setStaticVariables(vars);
	}
	public static ThymeleafViewResolver getViewResolver() {
		return viewResolver;
	}
	public static void setViewResolver(ThymeleafViewResolver viewResolver) {
		//设置全局变量
		viewResolver.addStaticVariable("maxOrg", Long.valueOf(TsConfig.getProperty("ts.manage.maxOrg")));
		SystemUtil.viewResolver = viewResolver;
	}
	//系统所有静态数据字典
	public static Map<Object, Map<Object, DictDO>> getDicts() {
		return dicts;
	}
	public static void setDicts(Map<Object, Map<Object, DictDO>> dicts) {
		SystemUtil.dicts = dicts;
		 Map<String, Object> vars = Maps.newHashMap();
		  Object obj = JSON.toJSON(dicts);
        vars.put("sysDicts", dicts);
        viewResolver.setStaticVariables(vars);
	}
	
}
