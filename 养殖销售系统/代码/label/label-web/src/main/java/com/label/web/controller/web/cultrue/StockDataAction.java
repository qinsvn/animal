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
import com.label.service.web.StockDataService;

@Controller
@RequestMapping("web/stockData")
public class StockDataAction {
	
	@Resource
	private StockDataService stockDataService;
	

	/**
	 * 跳转到库存列表页面
	 * @return
	 */
	@ApiOperation(value = "库存列表页面")
//	//	@RequiresPermissions("label:cmd:action")
	@RequestMapping(value = "/listPage",method = RequestMethod.GET)
	public String toBasePage() {
		return "/web/stockData/p_list_stockData.jsp";
	}
	
	/**
	 * 获取库存列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取库存列表")
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public Object listStockDatas(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = stockDataService.list(pageBean);
		return result;
	}
	
	
	/**
	 * 删除库存
	 * @return
	 */
	@ApiOperation(value = "删除库存")
//	//	@RequiresPermissions("label:device:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delStockData(@RequestBody List<Integer> list) {
		UpmsResult result = stockDataService.deletes(list);
		return result;
	}
	

	/**
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "导出库存信息")
	@RequestMapping(value = "/export",method = RequestMethod.POST)
	@ResponseBody
	public Object export(@RequestBody Map<String, Object> params) {
		UpmsResult result = stockDataService.exportStockData(params);
		return result;
	}
}



