package com.label.dao.custom;

import java.util.List;
import java.util.Map;

import com.label.common.model.base.Company;
import com.label.dao.auto.CompanyMapper;

public interface CompanyCustomMapper extends CompanyMapper {

	/**
	 * 插入返回id
	 * @param company
	 * @return
	 */
	int insetCompanyReturnId(Company company);
	
	/**
	 * 批量插入
	 * @param map
	 * @return
	 */
	int insetCompanys(Map<String, Object> map);
	
	/**
	 * 获取列表
	 * @param map
	 * @return
	 */
	List<Company> listCompanys(Map<String, Object> map);
	
	/**
	 * 批量删除
	 * @param map
	 * @return
	 */
	int deleteCompanys(Map<String, Object> map);
	
	/**
	 * 获取商家id和名称
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> listAllCompanys(Map<String, Object> map);
	
	/**
	 * 添加默认角色
	 * @param map
	 * @return
	 */
	int insertDefulRoleUser(Map<String, Object> map);
	
	/**
	 * 添加角色信息
	 * @param map
	 * @return
	 */
	int insertRole(Map<String, Object> map);
	
	/**
	 * 获取最后一条数据的商户编号
	 * @param map
	 * @return
	 */
	List<Company> lastCompanys();
}
