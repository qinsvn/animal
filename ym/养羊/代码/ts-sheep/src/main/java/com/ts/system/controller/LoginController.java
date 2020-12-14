package com.ts.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ts.common.annotation.Log;
import com.ts.common.config.TsConfig;
import com.ts.common.constant.CommonConstant;
import com.ts.common.controller.BaseController;
import com.ts.common.domain.FileDO;
import com.ts.common.domain.Tree;
import com.ts.common.service.FileService;
import com.ts.common.utils.MD5Utils;
import com.ts.common.utils.Result;
import com.ts.common.utils.RandomValidateCodeUtil;
import com.ts.common.utils.ShiroUtils;
import com.ts.common.utils.StringUtils;
import com.ts.system.domain.DeptDO;
import com.ts.system.domain.MenuDO;
import com.ts.system.domain.UserDO;
import com.ts.system.service.DeptService;
import com.ts.system.service.MenuService;
import com.ts.system.service.UserService;
import com.ts.system.util.SystemUtil;

@Controller
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MenuService menuService;
    @Autowired
    FileService fileService;
	@Autowired
	UserService userService;
	@Autowired
	private DeptService sysDeptService;

    @GetMapping({"/", ""})
    String welcome(Model model) {

        return "redirect:/login";
    }

    //@Log("请求访问主页")
    @GetMapping({"/index"})
    String index(Model model,HttpServletRequest request) {
    	String from = request.getParameter(CommonConstant.LOGIN_FROM);//pc  mobile
    	if (Objects.equals(CommonConstant.LOGIN_FROM_MOBILE, from)) {
    		FileDO fileDO = fileService.get(getUser().getPicId());
    		if (fileDO != null && fileDO.getUrl() != null) {
    			if (fileService.isExist(fileDO.getUrl())) {
    				model.addAttribute("picUrl", fileDO.getUrl());
    			} else {
    				model.addAttribute("picUrl", "/img/photo_s.jpg");
    			}
    		} else {
    			model.addAttribute("picUrl", "/img/photo_s.jpg");
    		}
    		model.addAttribute("user", getUser());
    		return "wechat/index";
		}else{
    		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
    		model.addAttribute("menus", menus);
    		model.addAttribute("name", getUser().getName());
    		FileDO fileDO = fileService.get(getUser().getPicId());
    		if (fileDO != null && fileDO.getUrl() != null) {
    			if (fileService.isExist(fileDO.getUrl())) {
    				model.addAttribute("picUrl", fileDO.getUrl());
    			} else {
    				model.addAttribute("picUrl", "/img/photo_s.jpg");
    			}
    		} else {
    			model.addAttribute("picUrl", "/img/photo_s.jpg");
    		}
    		model.addAttribute("username", getUser().getUsername());
    		return "index_v1";
		}
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("username", TsConfig.getUsername());
        model.addAttribute("password", TsConfig.getPassword());
        return "login";
    }

    @Log("登录系统")
    @PostMapping("/login")
    @ResponseBody
    Result ajaxLogin(String username, String password,String verify,HttpServletRequest request) {

        try {
            //从session中获取随机数
            String random = (String) request.getSession().getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
            if (StringUtils.isBlank(verify)) {
                return Result.error("请输入验证码");
            }
            if (random.equals(verify)) {
            } else {
                return Result.error("请输入正确的验证码");
            }
        } catch (Exception e) {
            logger.error("验证码校验失败", e);
            return Result.error("验证码校验失败");
        }
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return Result.ok();
        } catch (AuthenticationException e) {
            return Result.error("用户或密码错误");
        }
    }

    @GetMapping("/logout")
    String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }
    
    @GetMapping("/mobile/logout")
    String mobileLogout() {
        ShiroUtils.logout();
        return "redirect:/templates/wechat/login.html";
    }

    @GetMapping("/main")
    String main() {
        return "main";
    }

    /**
     * 生成验证码
     */
    @GetMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            logger.error("获取验证码失败>>>> ", e);
        }
    }


    /**
     * 系统信息
     */
    @GetMapping(value = "/sysInfo")
    @ResponseBody
    public Map<String, Object> sysInfo(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> ret = new HashMap<>();
        try {
    		ret.put("users", SystemUtil.getUsers());
    		ret.put("depts", SystemUtil.getDepts());
    		ret.put("dicts", SystemUtil.getDicts());
        } catch (Exception e) {
        }
        return ret;
    }
}
