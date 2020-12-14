package com.ts.system.config;

import java.util.Objects;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ts.common.constant.CommonConstant;

/**
 * 重写authc过滤器
 * @author jolley
 */
public class TsAuthenticationFilter extends FormAuthenticationFilter {

    private final static Logger _log = LoggerFactory.getLogger(TsAuthenticationFilter.class);


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    	HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
    	if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                	_log.trace("Login submission detected.  Attempting to execute login.");
                return executeLogin(request, response);
            } else {
            	_log.trace("Login page view.");
                //allow them to see the login page ;)
                return true;
            }
        } else {
        	_log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
        	String from = httpServletRequest.getParameter(CommonConstant.LOGIN_FROM);
        	String contextPath = httpServletRequest.getContextPath();
        	if(Objects.equals(CommonConstant.LOGIN_FROM_MOBILE, from)) {
    	        WebUtils.toHttp(response).sendRedirect(contextPath + "/templates/wechat/login.html");
        	}else{
        		saveRequestAndRedirectToLogin(request, response);
        	}
            return false;
        }
    
    }

}
