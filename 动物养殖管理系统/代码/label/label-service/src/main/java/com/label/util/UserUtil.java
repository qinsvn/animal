package com.label.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.label.common.constant.UpmsSessionKey;
import com.label.common.model.base.AdminUser;
import com.label.common.model.base.AdminUserExample;
import com.label.common.model.base.Company;
import com.label.common.model.base.CompanyExample;
import com.label.common.model.custom.upms.UpmsUserInfo;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilSpringContext;
import com.label.dao.auto.AdminUserMapper;
import com.label.dao.auto.CompanyMapper;
import com.label.dao.custom.AdminUserCustomMapper;

public class UserUtil { 

	private static Map<String, AdminUser> allUsersByAccount = new HashMap<>();
	private static Map<Integer, AdminUser> allUsersById = new HashMap<>();
	private static Map<Integer, Company> allCompanys = new HashMap<>();

	/**
	 * 登录用户信息
	 * 
	 * @return
	 */
	public static UpmsUserInfo getUpmsUserInfo() {
		Session session = SecurityUtils.getSubject().getSession();
		UpmsUserInfo upmsUserInfo = UtilJson.str2Bean(String.valueOf(session
				.getAttribute(UpmsSessionKey.UPMS_USER_INFO)),
				UpmsUserInfo.class);
		return upmsUserInfo;
	}

	/**
	 * @return
	 */
	public static boolean isAdmin() {
		Session session = SecurityUtils.getSubject().getSession();
		UpmsUserInfo upmsUserInfo = UtilJson.str2Bean(String.valueOf(session
				.getAttribute(UpmsSessionKey.UPMS_USER_INFO)),
				UpmsUserInfo.class);
		if (upmsUserInfo.getId() == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 所有未锁定的用户信息
	 */
	public static void initUsersInfo() {
		AdminUserMapper adminUserMapper = (AdminUserMapper) UtilSpringContext
				.getBean("adminUserMapper");
		AdminUserExample example = new AdminUserExample();
		example.createCriteria().andFdLockedEqualTo((byte) 0);
		List<AdminUser> adminusers = adminUserMapper.selectByExample(example);
		for (AdminUser user : adminusers) {
			allUsersByAccount.put(user.getFdAccount(), user);
			allUsersById.put(user.getId(), user);
		}
	}

	/**
	 * 所有未锁定的用户信息
	 */
	public static void initDeptsInfo() {
		CompanyMapper companyMapper = (CompanyMapper) UtilSpringContext .getBean("companyMapper");
		CompanyExample example = new CompanyExample();
		example.createCriteria().andFdStatusEqualTo((byte) 0);
		List<Company> companys = companyMapper.selectByExample(example);
		for (Company company : companys) {
			allCompanys.put(company.getId(), company);
		}
	}

	public static AdminUser getUserInfoByAccount(String account) {
		return allUsersByAccount.get(account);
	}
	public static AdminUser getUserInfoById(Integer id) {
		return allUsersByAccount.get(id);
	}
	public static Company getCompany(Integer id) {
		return allCompanys.get(id);
	}
	
	public static boolean checkUserEnabelByAccount(String account){
		 AdminUser user = allUsersByAccount.get(account);
		 if (user==null) {
			return false;
		}
		 Company company =  allCompanys.get(user.getFdConpanyId());
		 if (company==null) {
				return false;
			}
		 return true;
	}
}
