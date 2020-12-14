package com.label.service.web.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.entity.PageBean;
import com.label.common.model.base.AdminUser;
import com.label.common.model.base.AdminUserExample;
import com.label.common.model.base.RoleUserRef;
import com.label.common.model.base.RoleUserRefExample;
import com.label.common.model.custom.upms.UpmsUserInfo;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilMD5;
import com.label.dao.auto.RoleUserRefMapper;
import com.label.dao.custom.AdminUserCustomMapper;
import com.label.service.web.UserService;
import com.label.util.UserUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("adminService")
@Transactional
public class UserServiceImpl implements UserService {

	Logger _log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private AdminUserCustomMapper adminUserCustomMapper;
	
	@Autowired
	private RoleUserRefMapper roleUserRefMapper;
	
	@Override
	public UpmsResult listUser(UpmsUserInfo user, PageBean<JSONObject> pageBean) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败 ");
		try{
			JSONObject info = pageBean.getCondition();
			PageHelper.startPage(pageBean.getPage(), pageBean.getRows());
			Map<String, Object> data = new HashMap<>();
			if(user!=null && user.getCompanyId() > 0){
				info.put("fdConpanyId", user.getCompanyId());
			}
			List<Map<String,Object>> rows = adminUserCustomMapper.selectByWhere(info);
			PageInfo<Map<String,Object>>pageInfo = new PageInfo<Map<String,Object>>(rows);
			data.put("rows", pageInfo.getList());
			data.put("count", pageInfo.getSize());
			data.put("total", pageInfo.getTotal());
			result = new UpmsResult(UpmsResultConstant.SUCCESS, data);
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> AdminServiceImpl list error info:" + UtilJson.Obj2Str(pageBean), e);
		}
		return result;
	}

	@Override
	public UpmsResult createUser(UpmsUserInfo user, AdminUser info) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "创建失败 ");
		try{
			String password =UtilMD5.MD5(info.getFdAccount() + info.getFdPassword()).toLowerCase();
			info.setFdPassword(password);
			info.setFdCreateUser(user.getId());
			info.setFdCreateTime(new Date());
			if (user.getCompanyId() > 0) {//平台创建就不加
				info.setFdConpanyId(user.getCompanyId());
			}
			int returnId = adminUserCustomMapper.insertAdminUserReturnId(info);
			if(!StringUtils.isEmpty(info.getFdRoles())){
				//有勾选角色
				String [] roles = info.getFdRoles().split(",");
				for (String id : roles) {
					//创建关系数据
					RoleUserRef roleInfo = new RoleUserRef();
					roleInfo.setFdUserId(info.getId());
					roleInfo.setFdRoleId(Integer.parseInt(id));
					roleInfo.setFdCompanyId(user.getCompanyId());
					roleUserRefMapper.insert(roleInfo);
				}
			}
			if(returnId>0){
				UserUtil.initUsersInfo();
				result = new UpmsResult(UpmsResultConstant.SUCCESS, "创建成功 ");
			}else{
				_log.info("jolley >> UserServiceImpl 添加失败，info："+UtilJson.Obj2Str(info));
			}
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> UserServiceImpl create error info:" + UtilJson.Obj2Str(info), e);
		}
		return result;
	}

	@Override
	public UpmsResult infoUser(Integer id) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败");
		try{
			AdminUser record = adminUserCustomMapper.selectByPrimaryKey(id);
			record.setFdPassword(null);
			result = new UpmsResult(UpmsResultConstant.SUCCESS, record);				
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> UserServiceImpl info error id:" + id, e);
		}
		return result;
	}

	@Override
	public UpmsResult updateUser(UpmsUserInfo user ,AdminUser info) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "修改失败");
		try{
			AdminUser recode = adminUserCustomMapper.selectByPrimaryKey(info.getId());
			if(recode==null){
				result = new UpmsResult(UpmsResultConstant.FAILED, "数据不存在");
			}else{
				if(!StringUtils.isEmpty(info.getFdRoles())){
					//有勾选角色
					RoleUserRefExample example = new RoleUserRefExample();
					example.createCriteria().andFdUserIdEqualTo(recode.getId());
					roleUserRefMapper.deleteByExample(example);
					String [] roles = info.getFdRoles().split(",");
					for (String id : roles) {
						//创建关系数据
						RoleUserRef roleInfo = new RoleUserRef();
						roleInfo.setFdUserId(recode.getId());
						roleInfo.setFdRoleId(Integer.parseInt(id));
						roleInfo.setFdCompanyId(info.getFdConpanyId());
						roleUserRefMapper.insert(roleInfo);
					}
				}
				if(StringUtils.isNotBlank(info.getFdPassword())){
					String password =UtilMD5.MD5(info.getFdAccount() + info.getFdPassword()).toLowerCase();
					info.setFdPassword(password);
				}else{
					info.setFdPassword(null);
				}
				int count = adminUserCustomMapper.updateByPrimaryKeySelective(info);
				if(count>0){
					UserUtil.initUsersInfo();
					result = new UpmsResult(UpmsResultConstant.SUCCESS, "修改成功");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> UserServiceImpl update error info:"+UtilJson.Obj2Str(info),e);
		}
		return result;
	}

	@Override
	public UpmsResult deleteUser(List<Integer> ids) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "删除失败");
		try{
			AdminUserExample example = new AdminUserExample();
			example.createCriteria().andFdCreateUserIsNotNull().andIdIn(ids);
			int count = adminUserCustomMapper.deleteByExample(example);
			if(ids.size()!=count){
				result = new UpmsResult(UpmsResultConstant.SUCCESS, "成功删除"+count+"条数据，"+(ids.size()-count)+"条数据为原始数据，不可删除！");
			}else{
				UserUtil.initUsersInfo();
				result = new UpmsResult(UpmsResultConstant.SUCCESS, "删除成功");
			}
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> UserServiceImpl delete error ids:"+ids,e);
		}
		return result;
	}

	@Override
	public UpmsResult infoByAccount(String fdAccount) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败");
		try{
			AdminUserExample example = new AdminUserExample();
			example.createCriteria().andFdAccountEqualTo(fdAccount);
			List<AdminUser> record = adminUserCustomMapper.selectByExample(example);
			if(record!=null&&record.size()>0){
				result = new UpmsResult(UpmsResultConstant.SUCCESS, false);								
			}else{
				result = new UpmsResult(UpmsResultConstant.SUCCESS, true);
			}
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> UserServiceImpl info error fdAccount:"+fdAccount, e);
		}
		return result;
	}

	@Override
	public UpmsResult bind(Integer userId, String roleIds) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "绑定失败");
		try{
			AdminUser user = adminUserCustomMapper.selectByPrimaryKey(userId);
			if(user!=null){
				//先删除关系表数据
				RoleUserRefExample example = new RoleUserRefExample();
				example.createCriteria().andFdUserIdEqualTo(userId);
				roleUserRefMapper.deleteByExample(example);
				
				String [] roles = roleIds.split(",");
				for (String id : roles) {
					//创建关系数据
					RoleUserRef info = new RoleUserRef();
					info.setFdUserId(user.getId());
					info.setFdCompanyId(user.getFdConpanyId());
					info.setFdRoleId(Integer.parseInt(id));
					roleUserRefMapper.insert(info);
				}
				//更新user数据
				user.setFdRoles(roleIds);
				adminUserCustomMapper.updateByPrimaryKeySelective(user);
				result = new UpmsResult(UpmsResultConstant.SUCCESS, "绑定成功");
			}else{
				result = new UpmsResult(UpmsResultConstant.FAILED, "获取不到帐号信息");
			}
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> UserServiceImpl bind error userid:"+userId+";roleIds:"+roleIds, e);
		}
		return result;
	}

	@Override
	public UpmsResult updatePassword(UpmsUserInfo user, String oldPassword, String newPassword) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "绑定失败");
		try{
			String password =UtilMD5.MD5(user.getAccount() + oldPassword).toLowerCase();
			String updatePassword = UtilMD5.MD5(user.getAccount() + newPassword).toLowerCase();
			AdminUserExample example = new AdminUserExample();
			example.createCriteria().andFdAccountEqualTo(user.getAccount()).andFdPasswordEqualTo(password);
			List<AdminUser> list = adminUserCustomMapper.selectByExample(example);
			if(list!=null&&list.size()>0){
				AdminUser adminUser = list.get(0);
				adminUser.setFdPassword(updatePassword);
				int count =adminUserCustomMapper.updateByPrimaryKeySelective(adminUser);
				if(count>0){
					result = new UpmsResult(UpmsResultConstant.SUCCESS, "修改成功");
				}else{
					result = new UpmsResult(UpmsResultConstant.FAILED, "系统错误");
				}
			}else{
				result = new UpmsResult(UpmsResultConstant.FAILED, "密码错误");
			}
		}catch(Exception e){
			e.printStackTrace();
			_log.info("jolley >> 修改密码失败，user:"+UtilJson.Obj2Str(user)+";oldPassword:"+oldPassword+";newPassword:"+newPassword,e);
		}
		return result;
	}

	
}
