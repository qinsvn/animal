package com.label.client.shiro.filter;

import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.UtilRequest;

/**
 * 重写authc过滤器
 * @author jolley
 */
public class UpmsAuthenticationFilter extends AuthenticationFilter {

    private final static Logger _log = LoggerFactory.getLogger(UpmsAuthenticationFilter.class);

//    @Autowired
//    UpmsSessionDao upmsSessionDao;
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//    	_log.info("jolley>> 拦截了");
    	
        Subject subject = getSubject(request, response);
        return subject.isAuthenticated();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    	HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
    	String uri = httpServletRequest.getRequestURI();
    	_log.info("jolley>> uri: {}", uri);
    	
    	// 过滤ajax
    	if (null != httpServletRequest.getHeader("X-Requested-With") && httpServletRequest.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
    		response.setCharacterEncoding("UTF-8");
    		response.setContentType("text/html;charset=UTF-8");
    		PrintWriter out = response.getWriter();
			out.print(UtilJson.Obj2Str(new UpmsResult(UpmsResultConstant.NOT_LOGGED_IN, "未登录")));
			out.close();
			out = null;
		} else {
	    	// 跳转pc端登录页面
			String uri2 = uri.replace("/", "").split(";")[0];
	    	if(uri2.endsWith("webindex") || uri2.endsWith("web") || uri2.endsWith("label-web") || StringUtils.isEmpty(uri2)) {
		    	String contextPath = httpServletRequest.getContextPath();
		        String ssoLogin = UtilPropertiesFile.getInstance().get("sso_login");
		        WebUtils.toHttp(response).sendRedirect(contextPath + ssoLogin);
	    	} else {
	    		String backurl = URLEncoder.encode(UtilRequest.getUrl(httpServletRequest, null), "utf-8");
	            
	    		String contextPath = httpServletRequest.getContextPath();
		        String ssoRelogin = UtilPropertiesFile.getInstance().get("sso_relogin");
		        WebUtils.toHttp(response).sendRedirect(contextPath + ssoRelogin + "?backurl=" + backurl);
	    	}
		}
    	
        return false;
    }

}
