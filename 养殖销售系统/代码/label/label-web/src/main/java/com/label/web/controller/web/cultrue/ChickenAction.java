package com.label.web.controller.web.cultrue;

import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.label.common.constant.UpmsResult;
import com.label.common.entity.PageBean;
import com.label.common.model.base.ChickenData;
import com.label.service.web.ChickenService;

@Controller
@RequestMapping("web/chicken")
public class ChickenAction {
	
	@Resource
	private ChickenService chickenService;
	

	/**
	 * 跳转到列表页面
	 * @return
	 */
	@ApiOperation(value = "基础信息页面")
//	//	@RequiresPermissions("label:cmd:action")
	@RequestMapping(value = "/listPage",method = RequestMethod.GET)
	public String toBasePage() {
		return "/web/chicken/p_list_chicken.jsp";
	}
	
	/**
	 * 获取设备列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取禽类列表")
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public Object listChickens(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = chickenService.list(pageBean);
		return result;
	}
	
	
	/**
	 * 删除禽类
	 * @return
	 */
	@ApiOperation(value = "删除删除禽类")
//	//	@RequiresPermissions("label:device:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delChicken(@RequestBody List<Integer> list) {
		UpmsResult result = chickenService.deletes(list);
		return result;
	}
	

	/**
	 * 修改禽类信息
	 * @return
	 */
	@ApiOperation(value = "修改信息")
//	//	@RequiresPermissions("label:device:delete")
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object updateCastrationData(@RequestBody ChickenData chickenData ) {
		UpmsResult result = chickenService.update(chickenData);
		return result;
	}
	

	/**
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "导出禽类信息")
	@RequestMapping(value = "/export",method = RequestMethod.POST)
	@ResponseBody
	public Object export(@RequestBody Map<String, Object> params) {
		UpmsResult result = chickenService.exportChickenData(params);
		return result;
	}


	/**
	 * 跳转到列表页面
	 * @return
	 */
	@ApiOperation(value = "禽类信息页面")
	@RequestMapping(value = "/info",method = RequestMethod.GET)
	public String info() {
		return "/web/chicken/info.jsp";
	}
	
	/**
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取禽类信息")
	@RequestMapping(value = "/ckInfo",method = RequestMethod.GET)
	@ResponseBody
	public Object getChickenInfoByRFID(String rfidNum){
		UpmsResult result = chickenService.getChickenInfoByRFID(rfidNum);
		return result;
	}
}



