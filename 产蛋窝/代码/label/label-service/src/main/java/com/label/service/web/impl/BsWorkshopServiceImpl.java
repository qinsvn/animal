/**
 * 
 */
package com.label.service.web.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.entity.PageBean;
import com.label.common.model.base.BsWorkshop;
import com.label.common.util.UserUtil;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilString;
import com.label.dao.auto.BsWorkshopMapper;
import com.label.dao.custom.BsWorkshopCustomMapper;
import com.label.service.web.BsWorkshopService;

/**
 * @author jolley
 *
 */
@Service
@Transactional
public class BsWorkshopServiceImpl implements BsWorkshopService {

	private Logger _log = LoggerFactory.getLogger(BsWorkshopServiceImpl.class);
	
	 @Autowired
	BsWorkshopMapper bsWorkshopMapper;

	@Autowired
	    BsWorkshopCustomMapper bsWorkshopCustomMapper;
	    
	@Override
	public UpmsResult createBsWorkshop(BsWorkshop bsWorkshop) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "创建失败 ");
		
		//后台增加判断
		if (bsWorkshop==null) {
			return result;
		}
		if (UtilString.isEmpty(bsWorkshop.getName())) {
			result = new UpmsResult(UpmsResultConstant.FAILED, "厂房名称不能为空");	
			return result;
		}
		if (UtilString.isEmpty(bsWorkshop.getNum())) {
			result = new UpmsResult(UpmsResultConstant.FAILED, "厂房编号不能为空");	
			return result;
		}
		if (!UserUtil.isAdmin()) {
			bsWorkshop.setDptId(UserUtil.getUpmsUserInfo().getCompanyId());
		}
		if (UtilString.isEmpty(bsWorkshop.getDptId())||-1==bsWorkshop.getDptId()) {
			result = new UpmsResult(UpmsResultConstant.FAILED, "所属机构不能为空");	
			return result;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", bsWorkshop.getNum());
		List<Map<String,Object>>list =bsWorkshopCustomMapper.select(map);
		if(list!=null&&list.size()>0){
			result = new UpmsResult(UpmsResultConstant.FAILED, "编号已经存在");	
			return result;
		}
		
		bsWorkshop.setUpdateTime(new Date());
		int count = bsWorkshopMapper.insertSelective(bsWorkshop);
		if(count>0){
			result = new UpmsResult(UpmsResultConstant.SUCCESS, "创建成功 ");
		}else{
			_log.info("jolley >> BsWorkshopServiceImpl 添加失败，info："+UtilJson.Obj2Str(bsWorkshop));
		}
		return result;
	}

	@Override
	public UpmsResult updateBsWorkshop(BsWorkshop bsWorkshop) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "修改失败 ");
		//后台增加判断
		if (bsWorkshop==null) {
			return result;
		}
		if (UtilString.isEmpty(bsWorkshop.getName())) {
			result = new UpmsResult(UpmsResultConstant.FAILED, "厂房名称不能为空");	
			return result;
		}
		if (UtilString.isEmpty(bsWorkshop.getNum())) {
			result = new UpmsResult(UpmsResultConstant.FAILED, "厂房编号不能为空");	
			return result;
		}
		if (!UserUtil.isAdmin()) {
			bsWorkshop.setDptId(UserUtil.getUpmsUserInfo().getCompanyId());
		}
		if (UtilString.isEmpty(bsWorkshop.getDptId())) {
			result = new UpmsResult(UpmsResultConstant.FAILED, "所属机构不能为空");	
			return result;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("notId", bsWorkshop.getId());
		map.put("num", bsWorkshop.getNum());
		List<Map<String,Object>>list =bsWorkshopCustomMapper.select(map);
		if(list!=null&&list.size()>0){
			result = new UpmsResult(UpmsResultConstant.FAILED, "编号已经存在");	
			return result;
		}
		
		bsWorkshop.setUpdateTime(new Date());
		int count = bsWorkshopMapper.updateByPrimaryKeySelective(bsWorkshop);
		if(count>0){
			result = new UpmsResult(UpmsResultConstant.SUCCESS, "修改成功 ");
		}else{
			_log.info("jolley >> BsWorkshopServiceImpl 修改失败，info："+UtilJson.Obj2Str(bsWorkshop));
		}
		return result;
	}

	@Override
	public UpmsResult getBsWorkshop(Map<String, Object> map) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败");
		try{
			List<Map<String,Object>>list =bsWorkshopCustomMapper.select(map);
			if(list!=null&&list.size()>0){
				result = new UpmsResult(UpmsResultConstant.SUCCESS, list.get(0));				
			}else{
				result = new UpmsResult(UpmsResultConstant.FAILED, "数据不存在");
			}
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> getBsWorkshop info error map:" + UtilJson.Obj2Str(map), e);
		}
		return result;
	}

	@Override
	public UpmsResult listBsWorkshop(PageBean<Map<String, Object> > pageBean) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败 ");
		try{
			Map<String, Object>  info = pageBean.getCondition();
			if(!UserUtil.isAdmin()){
				info.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
			}
			PageHelper.startPage(pageBean.getPage(), pageBean.getRows());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object> > rows = bsWorkshopCustomMapper.select(info);
			PageInfo<Map<String, Object> > pageInfo = new PageInfo<Map<String, Object> >(rows);
			data.put("rows", pageInfo.getList());
			data.put("count", pageInfo.getSize());
			data.put("total", pageInfo.getTotal());
			result = new UpmsResult(UpmsResultConstant.SUCCESS, data);
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> listBsWorkshop list error info:" + UtilJson.Obj2Str(pageBean), e);
		}
		return result;
	}

	@Override
	public UpmsResult deleteBsWorkshops(List<Integer> list) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "删除成功 ");
		for (Integer id:list) {
			bsWorkshopMapper.deleteByPrimaryKey(id);
		}
		return result;
	}

}
