package com.label.web.controller.web.nest;

import io.swagger.annotations.ApiOperation;

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
import com.label.common.constant.UpmsResultConstant;
import com.label.common.entity.PageBean;
import com.label.common.model.base.BsNestparam;
import com.label.service.web.BsNestDataService;

/**
 * 产蛋窝数据管理
 * @author jolley
 *
 */
@Controller
@RequestMapping("/web/nestData")
public class BsNestDataController extends BaseController{

	Logger _log = LoggerFactory.getLogger(BsNestDataController.class);
	
	@Autowired
	private BsNestDataService bsNestDataService;
	
	
	/**
	 * 删除产蛋窝数据
	 * @return
	 */
	@ApiOperation(value = "删除产蛋窝数据信息")
	@RequiresPermissions("label:nestData:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delBsNestData(@RequestBody List<Integer> list) {
		UpmsResult result = bsNestDataService.deleteBsNestDatas(list);
		return result;
	}
	
	/**
	 * 更新产蛋窝数据信息
	 * @return
	 */
	@ApiOperation(value = "更新产蛋窝数据信息")
	@RequiresPermissions("label:nestData:update")
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object updateBsNestData(@RequestBody Map<String, Object> nestData) {
		UpmsResult result =bsNestDataService.updateBsNestData(nestData);
		return result;
	}
	
	/**
	 * 根据id获取产蛋窝数据信息
	 * @return
	 */
	@ApiOperation(value = "根据id获取产蛋窝数据信息")
	@RequiresPermissions("label:nestData:read")
	@RequestMapping(value = "/info")
	@ResponseBody
	public Object getBsNestData(@RequestBody Map<String, Object> map) {
		UpmsResult result = bsNestDataService.getBsNestData(map);
		return result;
	}
	
	/**
	 * 获取产蛋窝数据列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取产蛋窝数据列表")
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public Object listBsNestData(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = bsNestDataService.listBsNestData(pageBean);
		return result;
	}
	
	/**
	 * 获取产蛋窝数据列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取产蛋窝数据列表")
	@RequestMapping(value = "/export",method = RequestMethod.POST)
	@ResponseBody
	public Object export(@RequestBody Map<String, Object> params) {
		UpmsResult result = bsNestDataService.export(params);
		return result;
	}
	
	
	/**
	 * 产蛋窝数据管理页面
	 * @return
	 */
	@ApiOperation(value = "产蛋窝数据管理页面")
	@RequiresPermissions("label:nestData:read")
	@RequestMapping(value = "/pagenestData",method = RequestMethod.GET)
	public String pagelist() {
		return "/web/nestData/p_list_nestData.jsp";
	}
	

	/**
	 * 获取产蛋窝20日数据列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取产蛋窝数据列表")
	@RequestMapping(value = "/list20",method = RequestMethod.POST)
	@ResponseBody
	public Object listBsNestData20(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = bsNestDataService.listBsNestData20(pageBean);
		return result;
	}

	/**
	 * 产蛋窝20日管理页面
	 * @return
	 */
	@ApiOperation(value = "产蛋窝数据管理页面")
	@RequiresPermissions("label:nestData20:read")
	@RequestMapping(value = "/pagenestData20",method = RequestMethod.GET)
	public String pagelist20() {
		return "/web/nestData/p_list_nestData20.jsp";
	}
	

	/**
	 * 获取产蛋窝数据列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取产蛋窝数据列表")
	@RequestMapping(value = "/export20",method = RequestMethod.POST)
	@ResponseBody
	public Object export20(@RequestBody Map<String, Object> params) {
		UpmsResult result = bsNestDataService.export20(params);
		return result;
	}
	
	/**
	 * 跳转到参数设置页面
	 * @return
	 */
	@ApiOperation(value = "参数设置页面")
	@RequestMapping(value = "/baseSysParam",method = RequestMethod.GET)
	public String toBasePage() {
		return "/web/nestData/baseSysParam.jsp";
	}
	
	
	/**
	 * 获取基础信息
	 * @return
	 */
	@ApiOperation(value = "获取基础信息")
	@RequestMapping(value = "/getBaseSysParam",method = RequestMethod.GET)
	@ResponseBody
	public UpmsResult getBaseSysParam() {
		return bsNestDataService.getBaseSysParam();
	}
	
	/**
	 *  保存参数
	 * @param result
	 * @param syskeys
	 * @return
	 */
	@RequestMapping("saveBaseSysParam")
	public @ResponseBody UpmsResult saveBaseSysParam(BsNestparam nestparam) {
		try {
			return bsNestDataService.saveBaseSysParam(nestparam);
		} catch (Exception e) {
			_log.error("jolley >> listERR:{}",e);
		}
		return new UpmsResult(UpmsResultConstant.FAILED, "获取数据失败！");
	}
	

	/**
	 * 抱窝列表页面
	 * @return
	 */
	@ApiOperation(value = "抱窝列表页面")
	@RequestMapping(value = "/nestList",method = RequestMethod.GET)
	public String nestList() {
		return "/web/nestData/p_list_nest.jsp";
	}
	

	/**
	 * 获取抱窝列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "抱窝列表")
	@RequestMapping(value = "/listBsNest",method = RequestMethod.POST)
	@ResponseBody
	public Object listBsNest(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = bsNestDataService.listBsNest(pageBean);
		return result;
	}
}
