package com.label.service.web;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.label.common.constant.UpmsResult;
import com.label.common.entity.PageBean;
import com.label.common.model.base.AdminUser;
import com.label.common.model.custom.upms.UpmsUserInfo;

public interface UserService {

	/**
	 * 分页获取帐号数据
	 * @param user 关键字查询fdName
	 * @param pageBean 分页
	 * @return
	 */
	UpmsResult listUser(UpmsUserInfo user, PageBean<JSONObject> pageBean);

	/**
	 * 创建
	 * @param user 登录用户数据，用于填充创建者id
	 * @param info 
	 * @return
	 */
	UpmsResult createUser(UpmsUserInfo user, AdminUser info);

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	UpmsResult infoUser(Integer id);

	/**
	 * 更新用户数据
	 * @param info
	 * @return
	 */
	UpmsResult updateUser(UpmsUserInfo user,AdminUser info);

	/**
	 * 删除数据
	 * @param list id集合
	 * @return
	 */
	UpmsResult deleteUser(List<Integer> list);

	/**
	 * 通过帐号查询数据。用于注册时的帐号重复校验
	 * @param fdAccount
	 * @return
	 */
	UpmsResult infoByAccount(String fdAccount);

	/**
	 * 绑定角色
	 * @param userId 帐号id
	 * @param roleIds 逗号隔开角色id字符串
	 * @return
	 */
	UpmsResult bind(Integer userId, String roleIds);

	/**
	 * 更新密码
	 * @param user 帐号
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @return
	 */
	UpmsResult updatePassword(UpmsUserInfo user, String oldPassword, String newPassword);

}
