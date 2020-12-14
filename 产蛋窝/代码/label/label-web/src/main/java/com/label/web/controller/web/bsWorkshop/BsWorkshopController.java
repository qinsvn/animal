package com.label.web.controller.web.bsWorkshop;

import java.util.List;
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
import com.label.common.model.base.BsWorkshop;
import com.label.service.web.BsWorkshopService;

import io.swagger.annotations.ApiOperation;

/**
 * 厂房管理
 * @author Allen
 *
 */
@Controller
@RequestMapping("/web/workshop")
public class BsWorkshopController extends BaseController{

	Logger _log = LoggerFactory.getLogger(BsWorkshopController.class);
	
	@Autowired
	private BsWorkshopService bsWorkshopService;
	
	
	/**
	 * 添加厂房
	 * @return
	 */
	@ApiOperation(value = "添加厂房信息")
	@RequiresPermissions("label:bsWorkshop:create")
	@RequestMapping(value = "/create")
	@ResponseBody
	public Object createBsWorkshop(@RequestBody BsWorkshop bsWorkshop) {
		UpmsResult result = bsWorkshopService.createBsWorkshop(bsWorkshop);
		return result;
	}
	
	/**
	 * 删除厂房
	 * @return
	 */
	@ApiOperation(value = "删除厂房信息")
	@RequiresPermissions("label:bsWorkshop:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delBsWorkshop(@RequestBody List<Integer> list) {
		UpmsResult result = bsWorkshopService.deleteBsWorkshops(list);
		return result;
	}
	
	/**
	 * 更新厂房信息
	 * @return
	 */
	@ApiOperation(value = "更新厂房信息")
	@RequiresPermissions("label:bsWorkshop:update")
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object updateBsWorkshop(@RequestBody BsWorkshop bsWorkshop) {
		UpmsResult result =bsWorkshopService.updateBsWorkshop(bsWorkshop);
		return result;
	}
	
	/**
	 * 根据id获取厂房信息
	 * @return
	 */
	@ApiOperation(value = "根据id获取厂房信息")
	@RequiresPermissions("label:bsWorkshop:read")
	@RequestMapping(value = "/info")
	@ResponseBody
	public Object getBsWorkshop(@RequestBody Map<String, Object> map) {
		UpmsResult result = bsWorkshopService.getBsWorkshop(map);
		return result;
	}
	
	/**
	 * 获取厂房列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取厂房列表")
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public Object listBsWorkshop(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = bsWorkshopService.listBsWorkshop(pageBean);
		return result;
	}
	
	/**
	 * 厂房管理页面
	 * @return
	 */
	@ApiOperation(value = "厂房管理页面")
	@RequiresPermissions("label:bsWorkshop:read")
	@RequestMapping(value = "/pagebsWorkshop",method = RequestMethod.GET)
	public String pagelist() {
		return "/web/bsWorkshop/p_list_workshop.jsp";
	}
	
	
//	/**
//	 * 获取所有厂房 id和名称
//	 * @param map
//	 * @return
//	 */
//	@ApiOperation(value = "获取厂房信息")
//	@RequestMapping(value = "info")
//	@ResponseBody
//	public UpmsResult info(@RequestBody Map<String, Object> map) {
//		UpmsResult result = bsWorkshopService.getBsWorkshop(map);
//		return result;
//	}
	
}
