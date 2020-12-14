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
import com.label.service.web.OrderService;

@Controller
@RequestMapping("web/order")
public class OrderAction {
	
	@Resource
	private OrderService orderService;
	

	/**
	 * 跳转到订单列表页面
	 * @return
	 */
	@ApiOperation(value = "订单列表页面")
//	//	@RequiresPermissions("label:cmd:action")
	@RequestMapping(value = "/listPage",method = RequestMethod.GET)
	public String toBasePage() {
		return "/web/order/p_list_order.jsp";
	}
	
	/**
	 * 获取订单列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取订单列表")
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public Object listOrders(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = orderService.list(pageBean);
		return result;
	}
	

	/**
	 * 跳转到订单列表页面
	 * @return
	 */
	@ApiOperation(value = "订单汇总页面")
//	//	@RequiresPermissions("label:cmd:action")
	@RequestMapping(value = "/listSumPage",method = RequestMethod.GET)
	public String toOrderSumPage() {
		return "/web/order/p_list_sumOrder.jsp";
	}
	
	/**
	 * 获取订单汇总
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取汇总列表")
	@RequestMapping(value = "/listSum",method = RequestMethod.POST)
	@ResponseBody
	public Object listSum(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = orderService.orderSum(pageBean);
		return result;
	}
	
	
	/**
	 * 删除订单
	 * @return
	 */
	@ApiOperation(value = "删除订单")
//	//	@RequiresPermissions("label:device:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delOrder(@RequestBody List<Integer> list) {
		UpmsResult result = orderService.deletes(list);
		return result;
	}
	
	

	/**
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "导出明细")
	@RequestMapping(value = "/export",method = RequestMethod.POST)
	@ResponseBody
	public Object export(@RequestBody Map<String, Object> params) {
		UpmsResult result = orderService.exportOrder(params);
		return result;
	}
	

	/**
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "导出汇总")
	@RequestMapping(value = "/exportSum",method = RequestMethod.POST)
	@ResponseBody
	public Object exportSum(@RequestBody Map<String, Object> params) {
		UpmsResult result = orderService.exportOrderSum(params);
		return result;
	}
}



