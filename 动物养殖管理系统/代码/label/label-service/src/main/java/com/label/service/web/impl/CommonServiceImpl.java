/**
 * 
 */
package com.label.service.web.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.entity.CollectionData;
import com.label.common.entity.PageBean;
import com.label.common.entity.ResultApi;
import com.label.common.util.UtilJson;
import com.label.dao.custom.BreedDataCustomMapper;
import com.label.service.web.ActionService;
import com.label.service.web.CommonService;
import com.label.util.UserUtil;

/**
 * @author admin
 *
 */
public abstract class CommonServiceImpl  implements CommonService{

	private Logger _log = LoggerFactory.getLogger(getClass());
	@Autowired
	BreedDataCustomMapper breedDataCustomMapper;
	
	@Override
	public UpmsResult list(PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败 ");
		try{
			Map<String, Object>  info = pageBean.getCondition();
			if(!UserUtil.isAdmin()){
				info.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
			}
			PageHelper.startPage(pageBean.getPage(), pageBean.getRows());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object> > rows = select(info);
			PageInfo<Map<String, Object> > pageInfo = new PageInfo<Map<String, Object> >(rows);
			data.put("rows", pageInfo.getList());
			data.put("count", pageInfo.getSize());
			data.put("total", pageInfo.getTotal());
			result = new UpmsResult(UpmsResultConstant.SUCCESS, data);
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> listChicken list error info:" + UtilJson.Obj2Str(pageBean), e);
		}
		return result;
	}

	public abstract List<Map<String, Object> > select(Map<String, Object> map);
	
	@Override
	public UpmsResult deletes(List<Integer> list) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "删除成功 ");
		for (Integer id:list) {
			delete(id);
		}
		return result;
	}
	
	public abstract int delete(int id);
	
	@Override
	public UpmsResult listCommon(PageBean<Map<String, Object> > pageBean,ActionService service){
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败 ");
		try{
			Map<String, Object>  info = pageBean.getCondition();
			if(!UserUtil.isAdmin()){
				info.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
			}
			PageHelper.startPage(pageBean.getPage(), pageBean.getRows());
			List<Map<String, Object> > rows = service.select(info);
			PageInfo<Map<String, Object> > pageInfo = new PageInfo<Map<String, Object> >(rows);
			info.put("rows", pageInfo.getList());
			info.put("count", pageInfo.getSize());
			info.put("total", pageInfo.getTotal());
			result = new UpmsResult(UpmsResultConstant.SUCCESS, info);
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> listChicken list error info:" + UtilJson.Obj2Str(pageBean), e);
		}
		return result;
	}
	
	@Override
	public UpmsResult listCommon(Map<String, Object> info,ActionService service){
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败 ");
		try{
			if(!UserUtil.isAdmin()){
				info.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
			}
			List<Map<String, Object> > ret = service.select(info);
			result = new UpmsResult(UpmsResultConstant.SUCCESS, ret);
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> listChicken list error info:" + UtilJson.Obj2Str(result), e);
		}
		return result;
	}
	public void collectionData(ResultApi resultApi,CollectionData data) throws Exception
	{
		
	}
}
