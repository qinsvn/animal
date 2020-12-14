package com.label.service.web;

import java.util.List;
import java.util.Map;

import com.label.common.constant.UpmsResult;
import com.label.common.entity.PageBean;
import com.label.common.model.base.BsNestData;
import com.label.common.model.base.BsNestparam;

/**
 * 产蛋窝service
 * @author jolley
 *
 */
public interface BsNestDataService {
	
	/**
	 * 添加产蛋窝数据
	 */
	UpmsResult createBsNestData(BsNestData  BsNestData);
	
	/**
	 * 更新产蛋窝数据
	 * @param result
	 * @param company
	 */
	UpmsResult updateBsNestData(Map<String, Object>  BsNestData);
	
	/**
	 * 获取产蛋窝  编号  或者 id
	 * @param result
	 * @param map
	 */
	UpmsResult getBsNestData(Map<String, Object> map);
	

	/**
	 * 按条件获取取产蛋窝列表
	 * @param result
	 * @param map
	 */
	UpmsResult listBsNestData(PageBean<Map<String, Object> > pageBean);

	/**
	 * 按条件获取取产蛋窝列表 不分页
	 * @param result
	 * @param map
	 */
	List<Map<String, Object>> listBsNestData(Map<String, Object> map);
	
	
	/**
	 * 批量删除
	 * @param map
	 * @return
	 */
	UpmsResult deleteBsNestDatas(List<Integer> list);
	
	/**
	 * 获取最大排序
	 * @param rfidNum
	 * @return
	 */
	Integer maxOrder(String rfidNum);

	/**
	 * 按条件获取产蛋窝20日龄数据列表
	 * @param result
	 * @param map
	 */
	UpmsResult listBsNestData20(PageBean<Map<String, Object> > pageBean);

	/**
	 * 按条件获取产蛋窝20日龄数据列表 不分页
	 * @param result
	 * @param map
	 */
	List<Map<String, Object>> listBsNestData20(Map<String, Object>  map);

	/**
	 * 导出产蛋窝数据
	 * @param params
	 * @return
	 */
	UpmsResult export(Map<String, Object> params);

	/**
	 * 导出产蛋窝20日数据
	 * @param params
	 * @return
	 */
	UpmsResult export20(Map<String, Object> params);

	/**
	 * 解析G780传过来的数据
	 * @param data
	 * @return
	 */
	BsNestData analysisG780Data(String data);

	UpmsResult updateBsNestData(BsNestData BsNestData);
	
	/**
	 * 获取参数
	 * @return
	 */
	UpmsResult getBaseSysParam();
	
	/**
	 * 保存参数
	 * @param nestparam
	 * @return
	 */
	UpmsResult saveBaseSysParam(BsNestparam nestparam);
	
	public UpmsResult listBsNest(PageBean<Map<String, Object>> pageBean);
}
