package com.label.service.web;

import java.util.List;
import java.util.Map;

import com.label.common.entity.PageBean;
import com.label.common.model.base.Company;

/**
 * 商家service
 * @author Allen
 *
 */
public interface CompanyService {
	
	/**
	 * 添加商家
	 */
	Map<String, Object> createCompany(Map<String, Object> map);
	
	/**
	 * 删除商家
	 * @param result
	 * @param id
	 */
	Map<String, Object> delCompany(Map<String, Object> map);
	
	/**
	 * 更新商家
	 * @param result
	 * @param company
	 */
	Map<String, Object> updateCompany(Map<String, Object> map);
	
	/**
	 * 获取商家
	 * @param result
	 * @param map
	 */
	Map<String, Object> getCompanyById(Map<String, Object> map);
	
	/**
	 * 通过id获取信息
	 */
	Company info(int id);
	
	/**
	 * 按条件获取列表
	 * @param result
	 * @param map
	 */
	Map<String, Object> listCompany(PageBean<Map<String, Object>> pageBean);
	
	/**
	 * 批量插入
	 * @param result
	 * @param map
	 */
	Map<String, Object> insetCompanys(Map<String, Object> map);
	
	/**
	 * 导出数据
	 * @param pageBean
	 * @return
	 */
	Object exportExcel(PageBean<Map<String, Object>> pageBean);
	
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
	 * 获取最后一条数据的商户编号
	 * @param map
	 * @return
	 */
	List<Company> lastCompanys();
}
