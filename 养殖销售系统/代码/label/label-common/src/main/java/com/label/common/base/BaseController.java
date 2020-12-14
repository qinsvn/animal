package com.label.common.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.label.common.constant.UpmsSessionKey;
import com.label.common.model.custom.label.UserInfo;
import com.label.common.model.custom.upms.UpmsUserInfo;
import com.label.common.util.UtilJson;

/**
 * 控制器基类
 * @author jolley
 *
 */
public abstract class BaseController {

	private final static Logger _log = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 统一异常处理
	 * @param request
	 * @param response
	 * @param exception
	 */
	@ExceptionHandler
	public String exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		_log.error("统一异常处理：", exception);
		request.setAttribute("ex", exception);
		if (null != request.getHeader("X-Requested-With") && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
			request.setAttribute("requestHeader", "ajax");
		}
		// shiro没有权限异常
		if (exception instanceof UnauthorizedException) {
			return "/403.jsp";
		}
		// shiro会话已过期异常
		if (exception instanceof InvalidSessionException) {
			return "/error.jsp";
		}
		return "/error.jsp";
	}
	
	/**
	 * 登录用户信息
	 * @return
	 */
	public UpmsUserInfo getUpmsUserInfo() {
		Session session = SecurityUtils.getSubject().getSession();
    	UpmsUserInfo upmsUserInfo = UtilJson.str2Bean(String.valueOf(session.getAttribute(UpmsSessionKey.UPMS_USER_INFO)), UpmsUserInfo.class);
    	return upmsUserInfo;
	}
	
	/**
	 * 联盟用户信息
	 * @return
	 */
	public UserInfo getCiticUserInfo() {
		Session session = SecurityUtils.getSubject().getSession();
		UserInfo userInfo = UtilJson.str2Bean(String.valueOf(session.getAttribute(UpmsSessionKey.UPMS_LABEL_INFO)), UserInfo.class);
    	return userInfo;
	}

	/**
	 * 返回jsp视图
	 * @param path
	 * @return
	 */
	public static String jsp(String path) {
		return path.concat(".jsp");
	}

}
