package com.label.web.controller.web.cmd;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiOperation;

@Controller
public class CmdAction {
	
	/**
	 * 命令执行页面
	 * @return
	 */
	@ApiOperation(value = "命令执行页面")
	@RequiresPermissions("label:cmd:action")
	@RequestMapping(value = "/action",method = RequestMethod.GET)
	public String pagelist() {
		return "/web/cmd/action.jsp";
	}
	

}
