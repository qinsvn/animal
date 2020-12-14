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
import com.label.service.web.AggService;

@Controller
@RequestMapping("web/agg")
public class AggAction {
	
	@Resource
	private AggService aggService;
	

	/**
	 * 跳转到蛋类列表页面
	 * @return
	 */
	@ApiOperation(value = "蛋类列表页面")
//	//	@RequiresPermissions("label:cmd:action")
	@RequestMapping(value = "/listPage",method = RequestMethod.GET)
	public String toBasePage() {
		return "/web/agg/p_list_agg.jsp";
	}
	
	/**
	 * 获取蛋类列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取蛋类列表")
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public Object listAggs(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = aggService.list(pageBean);
		return result;
	}
	
	
	/**
	 * 删除蛋类
	 * @return
	 */
	@ApiOperation(value = "删除删除禽类")
//	//	@RequiresPermissions("label:device:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delAgg(@RequestBody List<Integer> list) {
		UpmsResult result = aggService.deletes(list);
		return result;
	}
}



