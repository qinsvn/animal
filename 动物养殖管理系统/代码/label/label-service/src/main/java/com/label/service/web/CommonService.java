package com.label.service.web;

import java.util.List;
import java.util.Map;

import com.label.common.constant.UpmsResult;
import com.label.common.entity.CollectionData;
import com.label.common.entity.PageBean;
import com.label.common.entity.ResultApi;

/**
 * 设备service
 * @author jolley
 *
 */
public interface CommonService  {

	/**
	 * 按条件获取列表
	 * @param result
	 * @param map
	 */
	public UpmsResult list(PageBean<Map<String, Object> > pageBean);	
	/**
	 * 批量删除
	 * @param map
	 * @return
	 */
	public UpmsResult deletes(List<Integer> list);
	
	/**
	 * 条件查询
	 * @param map
	 * @return
	 */
	public  List<Map<String, Object> > select(Map<String, Object> map);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public  int delete(int id);
	

	/**
	 * 按条件获取列表
	 * @param result
	 * @param map
	 */
	public UpmsResult listCommon(PageBean<Map<String, Object> > pageBean,ActionService service);
	
	/**
	 * 通用查询，不带分页
	 * @param info
	 * @param service
	 * @return
	 */
	public UpmsResult listCommon(Map<String, Object> info, ActionService service);
	
	/**
	 * 采集数据
	 * @param data
	 * @return
	 */
	public void collectionData(ResultApi resultApi,CollectionData data) throws Exception;
	
//	public UpmsResult upate(T t);
}
