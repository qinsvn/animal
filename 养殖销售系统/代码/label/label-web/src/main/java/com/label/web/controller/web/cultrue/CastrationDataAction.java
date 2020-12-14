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
import com.label.common.model.base.CastrationData;
import com.label.service.web.CastrationDataService;

@Controller
@RequestMapping("web/castrationData")
public class CastrationDataAction {
	
	@Resource
	private CastrationDataService castrationDataService;
	

	/**
	 * 跳转到阉割信息列表页面
	 * @return
	 */
	@ApiOperation(value = "阉割信息列表页面")
//	//	@RequiresPermissions("label:cmd:action")
	@RequestMapping(value = "/listPage",method = RequestMethod.GET)
	public String toBasePage() {
		return "/web/castrationData/p_list_castrationData.jsp";
	}
	
	/**
	 * 获取阉割信息列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取阉割信息列表")
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public Object listCastrationDatas(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = castrationDataService.list(pageBean);
		return result;
	}
	
	
	/**
	 * 删除阉割信息
	 * @return
	 */
	@ApiOperation(value = "删除阉割信息")
//	//	@RequiresPermissions("label:device:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delCastrationData(@RequestBody List<Integer> list) {
		UpmsResult result = castrationDataService.deletes(list);
		return result;
	}
	

	
	/**
	 * 修改阉割信息
	 * @return
	 */
	@ApiOperation(value = "修改信息")
//	//	@RequiresPermissions("label:device:delete")
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object updateCastrationData(@RequestBody CastrationData castrationData ) {
		UpmsResult result = castrationDataService.update(castrationData);
		return result;
	}
	

	/**
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "导出阉割信息")
	@RequestMapping(value = "/export",method = RequestMethod.POST)
	@ResponseBody
	public Object export(@RequestBody Map<String, Object> params) {
		UpmsResult result = castrationDataService.exportCastrationData(params);
		return result;
	}
}



