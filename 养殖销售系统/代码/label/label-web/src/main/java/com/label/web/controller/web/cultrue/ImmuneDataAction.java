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
import com.label.common.model.base.ImmuneData;
import com.label.service.web.ImmuneDataService;

@Controller
@RequestMapping("web/immuneData")
public class ImmuneDataAction {
	
	@Resource
	private ImmuneDataService immuneDataService;
	

	/**
	 * 跳转到疫苗列表页面
	 * @return
	 */
	@ApiOperation(value = "疫苗列表页面")
//	//	@RequiresPermissions("label:cmd:action")
	@RequestMapping(value = "/listPage",method = RequestMethod.GET)
	public String toBasePage() {
		return "/web/immuneData/p_list_immuneData.jsp";
	}
	
	/**
	 * 获取疫苗列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取疫苗列表")
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public Object listImmuneDatas(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = immuneDataService.list(pageBean);
		return result;
	}
	
	
	/**
	 * 删除疫苗
	 * @return
	 */
	@ApiOperation(value = "删除疫苗")
//	//	@RequiresPermissions("label:device:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delImmuneData(@RequestBody List<Integer> list) {
		UpmsResult result = immuneDataService.deletes(list);
		return result;
	}
	

	/**
	 * 修改疫苗信息
	 * @return
	 */
	@ApiOperation(value = "修改信息")
//	//	@RequiresPermissions("label:device:delete")
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object updateCastrationData(@RequestBody ImmuneData immuneData ) {
		UpmsResult result = immuneDataService.update(immuneData) ;
		return result;
	}
	

	/**
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "导出疫苗信息")
	@RequestMapping(value = "/export",method = RequestMethod.POST)
	@ResponseBody
	public Object export(@RequestBody Map<String, Object> params) {
		UpmsResult result = immuneDataService.exportImmuneData(params);
		return result;
	}
}



