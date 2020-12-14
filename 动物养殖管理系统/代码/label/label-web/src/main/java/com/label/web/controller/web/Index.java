package com.label.web.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.label.common.base.BaseController;
import com.label.common.model.custom.upms.UpmsUserInfo;

import io.swagger.annotations.ApiOperation;

/**
 * pc端首页管理
 * @author jolley
 */
@Controller
@RequestMapping("/web")
public class Index extends BaseController {

//    private static Logger _log = LoggerFactory.getLogger(Index.class);

    @ApiOperation(value = "首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String infoPage(ModelMap modelMap) {
    	UpmsUserInfo user = getUpmsUserInfo();
    	modelMap.put("account", user.getAccount());
    	modelMap.put("organId", user.getCompanyId());
    	modelMap.put("nickname", user.getNickname());
    	modelMap.put("roleType", user.getRoleType());
    	modelMap.put("id", user.getId());
    	modelMap.put("name", user.getFdName());
    	modelMap.put("phone", user.getPhone());
    	modelMap.put("email", user.getEmail());
    	modelMap.put("idCard", user.getIdCard());
    	modelMap.put("roles", user.getFdRoles());
    	return "/web/index.jsp";
    }
    
}
