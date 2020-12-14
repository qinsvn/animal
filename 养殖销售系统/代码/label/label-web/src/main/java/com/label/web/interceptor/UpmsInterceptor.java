package com.label.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.label.common.constant.UpmsSessionKey;
import com.label.common.model.custom.upms.UpmsUserInfo;
import com.label.common.util.UtilJson;

/**
 * 登录信息拦截器
 * @author jolley
 */
public class UpmsInterceptor extends HandlerInterceptorAdapter {

    private static Logger _log = LoggerFactory.getLogger(UpmsInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String uri = request.getRequestURI();
    	_log.info("jolley>> request uri: " + uri);
    	
        // 登录信息
    	Session session = SecurityUtils.getSubject().getSession();
    	UpmsUserInfo upmsUserInfo = UtilJson.str2Bean(String.valueOf(session.getAttribute(UpmsSessionKey.UPMS_USER_INFO)), UpmsUserInfo.class);
        request.setAttribute("upmsUserInfo", upmsUserInfo);
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

}
