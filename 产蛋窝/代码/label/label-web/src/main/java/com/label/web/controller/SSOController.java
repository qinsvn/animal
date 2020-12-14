package com.label.web.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.label.common.base.BaseController;
import com.label.common.constant.RedisConstant;
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.model.custom.upms.UpmsUsernamePasswordToken;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.redis.UtilRedis;

import io.swagger.annotations.ApiOperation;

/**
 * 登录管理
 * @author jolley
 */
@Controller
@RequestMapping("/sso")
public class SSOController extends BaseController {

    private final static Logger _log = LoggerFactory.getLogger(SSOController.class);

//    @Autowired
//    UpmsSessionDao upmsSessionDao;

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        
        // 判断是否已登录，如果已登录，则回跳
        String code = UtilRedis.get(RedisConstant.LABEL_UPMS_SERVER_SESSION_ID_ + sessionId);
        // code校验值
        if (StringUtils.isNotBlank(code)) {
        	String sso_web_index = UtilPropertiesFile.getInstance().get("sso_web_index");
        	return "redirect:" + sso_web_index;
        }
        
        return "/sso/login.jsp";
    }

    @ApiOperation(value = "重新登录")
    @RequestMapping(value = "/relogin", method = RequestMethod.GET)
    public String relogin(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        
        // 判断是否已登录，如果已登录，则回跳
        String code = UtilRedis.get(RedisConstant.LABEL_UPMS_SERVER_SESSION_ID_ + sessionId);
        // code校验值
        if (StringUtils.isNotBlank(code)) {
        	String sso_web_index = UtilPropertiesFile.getInstance().get("sso_web_index");
        	return "redirect:" + sso_web_index;
        }
        
        return "/sso/relogin.jsp";
    }
    
    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
    	_log.debug("jolley>> account: {}, password: {}", account, password);
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return new UpmsResult(UpmsResultConstant.INVALID_ACCORPWD, "账号或密码错！");
        }
        
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        // 判断是否已登录，如果已登录，则回跳，防止重复登录
        String code = UtilRedis.get(RedisConstant.LABEL_UPMS_SERVER_SESSION_ID_ + sessionId);
        // code校验值
        if (StringUtils.isBlank(code)) {
            // 使用shiro认证
            UpmsUsernamePasswordToken usernamePasswordToken = new UpmsUsernamePasswordToken(account, password);
            try {
                usernamePasswordToken.setRememberMe(false);
                subject.login(usernamePasswordToken);
            } catch (UnknownAccountException e) {
            	_log.info("jolley>> 账号错, account: {}", account);
                return new UpmsResult(UpmsResultConstant.INVALID_ACCORPWD, "账号或密码错！");
            } catch (IncorrectCredentialsException e) {
            	_log.info("jolley>> 密码错, account: {}, password, {}", account, password);
                return new UpmsResult(UpmsResultConstant.INVALID_ACCORPWD, "账号或密码错！");
            } catch (LockedAccountException e) {
                return new UpmsResult(UpmsResultConstant.INVALID_ACCOUNT, "帐号已锁定！");
            }
            
            // 更新session状态
//            upmsSessionDao.updateStatus(sessionId, UpmsSession.OnlineStatus.on_line);
            // 默认验证帐号密码正确，创建code
            code = UUID.randomUUID().toString();
            // 全局会话的code
            UtilRedis.setex(RedisConstant.LABEL_UPMS_SERVER_SESSION_ID_ + sessionId, (int) subject.getSession().getTimeout() / 1000, code);
        }
        
        // 跳进登录主页
        String backurl = request.getParameter("backurl");
        if(StringUtils.isEmpty(backurl)) {
	        String contextPath = request.getContextPath();
	    	String sso_web_index = UtilPropertiesFile.getInstance().get("sso_web_index");
	        return new UpmsResult(UpmsResultConstant.SUCCESS, contextPath + sso_web_index);
        } else {
        	return new UpmsResult(UpmsResultConstant.SUCCESS, backurl);
        }
    }

    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        // shiro退出登录
        SecurityUtils.getSubject().logout();
        // 跳转登录页面
    	return "/sso/login.jsp";
    }

}