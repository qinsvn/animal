package com.label.common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.label.common.constant.UpmsSessionKey;
import com.label.common.model.custom.upms.UpmsUserInfo;
import com.label.common.util.UtilJson;

public class UserUtil {

	/**
	 * 登录用户信息
	 * @return
	 */
	public static UpmsUserInfo getUpmsUserInfo() {
		Session session = SecurityUtils.getSubject().getSession();
    	UpmsUserInfo upmsUserInfo = UtilJson.str2Bean(String.valueOf(session.getAttribute(UpmsSessionKey.UPMS_USER_INFO)), UpmsUserInfo.class);
    	return upmsUserInfo;
	}
	
	/**
	 * @return
	 */
	public static boolean isAdmin() {
		Session session = SecurityUtils.getSubject().getSession();
    	UpmsUserInfo upmsUserInfo = UtilJson.str2Bean(String.valueOf(session.getAttribute(UpmsSessionKey.UPMS_USER_INFO)), UpmsUserInfo.class);
    	if (upmsUserInfo.getId()==1) {
			return true;
		}else{
			return false;
		}
	}
}
