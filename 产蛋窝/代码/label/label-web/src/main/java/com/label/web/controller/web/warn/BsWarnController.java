package com.label.web.controller.web.warn;

import java.util.Date;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.label.common.base.BaseController;
import com.label.common.constant.UpmsResult;
import com.label.common.entity.PageBean;
import com.label.common.model.base.BsWarnCus;
import com.label.service.web.BsWarnService;
import com.label.task.warn.WarnTask;

import io.swagger.annotations.ApiOperation;

/**
 * 告警信息管理
 * @author jolley
 *
 */
@Controller
@RequestMapping("/web/warn")
public class BsWarnController extends BaseController{

	Logger _log = LoggerFactory.getLogger(BsWarnController.class);
	
	@Autowired
	private BsWarnService BsWarnService;
	
	
	/**
	 * 获取告警信息列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取告警信息列表")
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public Object listBsWarn(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = BsWarnService.listBsWarn(pageBean);
		return result;
	}
	
	/**
	 * 告警信息管理页面
	 * @return
	 */
	@ApiOperation(value = "告警信息管理页面")
	@RequiresPermissions("label:warn:read")
	@RequestMapping(value = "/p_list",method = RequestMethod.GET)
	public String pagelist() {
		return "/web/warn/p_list_warn.jsp";
	}
	

	/**
	 * 获取告警信息列表
	 * @param map
	 * @return
	 */
//	@ApiOperation(value = "获取告警信息")
//	@RequestMapping(value = "/getWarnInfo",method = RequestMethod.GET)
//	@ResponseBody
//	public Object getWarnInfo() {
//		try {
//			return null;
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return null;
//	}
}
