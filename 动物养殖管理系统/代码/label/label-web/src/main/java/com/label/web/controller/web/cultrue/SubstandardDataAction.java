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
import com.label.common.entity.CollectionData;
import com.label.common.entity.PageBean;
import com.label.service.web.SubstandardDataService;

@Controller
@RequestMapping("web/substandard")
public class SubstandardDataAction {
	
	@Resource
	private SubstandardDataService substandardDataService;
	

	/**
	 * 跳转到次品列表页面
	 * @return
	 */
	@ApiOperation(value = "次品列表页面")
//	//	@RequiresPermissions("label:cmd:action")
	@RequestMapping(value = "/listPage",method = RequestMethod.GET)
	public String toBasePage() {
		return "/web/substandard/p_list_substandard.jsp";
	}
	
	/**
	 * 获取次品列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取次品列表")
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public Object listSubstandards(@RequestBody PageBean<Map<String, Object>> pageBean) {
		UpmsResult result = substandardDataService.list(pageBean);
		return result;
	}
	
	

	/**
	 * 获取次品列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取次品汇总")
	@RequestMapping(value = "/listSum",method = RequestMethod.POST)
	@ResponseBody
	public Object listSubstandards(@RequestBody Map<String, Object> info) {
		UpmsResult result = substandardDataService.selectSubstandardDataSum(info);
		return result;
	}
	
	/**
	 * 删除次品
	 * @return
	 */
	@ApiOperation(value = "删除次品")
//	//	@RequiresPermissions("label:device:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delSubstandard(@RequestBody List<Integer> list) {
		UpmsResult result = substandardDataService.deletes(list);
		return result;
	}
	
	/**
	 * 审核次品
	 * @return
	 */
	@ApiOperation(value = "审核次品")
//	//	@RequiresPermissions("label:device:delete")
	@RequestMapping(value = "/approval")
	@ResponseBody
	public Object approval(@RequestBody List<Integer> list) {
		UpmsResult result = substandardDataService.approval(list);
		return result;
	}
	

	/**
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "导出审核信息")
	@RequestMapping(value = "/export",method = RequestMethod.POST)
	@ResponseBody
	public Object export(@RequestBody Map<String, Object> params) {
		UpmsResult result = substandardDataService.exportSubstandardData(params);
		return result;
	}
	
	
	/**
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "加入次品")
	@RequestMapping(value = "/addSubstandardData",method = RequestMethod.POST)
	@ResponseBody
	public Object addSubstandardData(@RequestBody CollectionData data) {
		UpmsResult result = null;
		try {
			result = substandardDataService.addSubstandardData(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}



