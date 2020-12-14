package com.label.dao.custom;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.label.common.model.base.AdminUser;
import com.label.dao.auto.AdminUserMapper;



/**
 * 自定义userMapper
 * @author Allen
 *
 */
public interface AdminUserCustomMapper extends AdminUserMapper{
	
	/**
	 * 商家id查找用户
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> listUserByCompanyId(Map<String, Object> map);
	
	/**
	 * 将商家用户降级为个人用户 并锁定
	 * @param map
	 * @return
	 */
	int updateCompanyUser(Map<String, Object> map);
	
	/**
	 * 插入管理员用户并返回ID
	 * @param adminUser
	 * @return
	 */
	int insertAdminUserReturnId(AdminUser adminUser);

	/**
	 * 条件查询数据
	 * @param info
	 * @return
	 */
	List<Map<String,Object>> selectByWhere(JSONObject info);
}
