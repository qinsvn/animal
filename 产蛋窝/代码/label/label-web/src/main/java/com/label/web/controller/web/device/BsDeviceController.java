package com.label.web.controller.web.device;

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
import com.label.common.model.base.BsDevice;
import com.label.service.web.BsDeviceService;

import io.swagger.annotations.ApiOperation;

/**
 * 设备管理
 * @author jolley
 *
 */
@Controller
@RequestMapping("/web/device")
public class BsDeviceController extends BaseController{

	Logger _log = LoggerFactory.getLogger(BsDeviceController.class);
	
	@Autowired
	private BsDeviceService bsDeviceService;
	
	
	/**
	 * 添加设备
	 * @return
	 */
	@ApiOperation(value = "添加设备信息")
	@RequiresPermissions("label:device:create")
	@RequestMapping(value = "/create")
	@ResponseBody
	public Object createBsDevice(@RequestBody BsDevice device) {
		UpmsResult result = bsDeviceService.createBsDevice(device);
		return result;
	}
	
	/**
	 * 删除设备
	 * @return
	 */
	@ApiOperation(value = "删除设备信息")
	@RequiresPermissions("label:device:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delBsDevice(@RequestBody List<Integer> list) {
		UpmsResult result = bsDeviceService.deleteBsDevices(list);
		return result;
	}
	
	/**
	 * 更新设备信息
	 * @return
	 */
	@ApiOperation(value = "更新设备信息")
	@RequiresPermissions("label:device:update")
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object updateBsDevice(@RequestBody BsDevice device) {
		UpmsResult result =bsDeviceService.updateBsDevice(device);
		return result;
	}
	
	/**
	 * 根据id获取设备信息
	 * @return
	 */
	@ApiOperation(value = "根据id获取设备信息")
	@RequiresPermissions("label:device:read")
	@RequestMapping(value = "/info")
	@ResponseBody
	public Object getBsDevice(@RequestBody Map<String, Object> map) {
		UpmsResult result = bsDeviceService.getBsDevice(map);
		return result;
	}
	
	/**
	 * 获取设备列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取设备列表")
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public Object listBsDevice(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = bsDeviceService.listBsDevice(pageBean);
		return result;
	}
	
	/**
	 * 设备管理页面
	 * @return
	 */
	@ApiOperation(value = "设备管理页面")
	@RequiresPermissions("label:device:read")
	@RequestMapping(value = "/pagedevice",method = RequestMethod.GET)
	public String pagelist() {
		return "/web/device/p_list_device.jsp";
	}
	
}
