package com.label.web.controller.web.cultrue;

import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.model.base.ChickenBaseinput;
import com.label.common.model.base.ChickenType;
import com.label.common.model.base.ImmuneBaseinput;
import com.label.service.web.BaseInputService;

@Controller
@RequestMapping("web/input")
public class BaseInputAction {
	
	private Logger _log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private BaseInputService baseInputService;
	

	/**
	 * 跳转到基础信息页面
	 * @return
	 */
	@ApiOperation(value = "基础信息页面")
//	//	@RequiresPermissions("label:cmd:action")
	@RequestMapping(value = "/basePage",method = RequestMethod.GET)
	public String toBasePage() {
		return "/web/input/basePage.jsp";
	}
	
	
	/**
	 * 获取基础信息
	 * @return
	 */
	@ApiOperation(value = "获取基础信息")
//	//	@RequiresPermissions("label:cmd:action")
	@RequestMapping(value = "/getBaseInputdata",method = RequestMethod.GET)
	@ResponseBody
	public UpmsResult getBaseInputdata() {
		return baseInputService.getBaseInputdata();
	}

	/**
	 *  保存禽类品种
	 * @param result
	 * @param syskeys
	 * @return
	 */
	@RequestMapping("saveChickenType")
	////	@RequiresPermissions(value = "label:sysparameter:read")
	public @ResponseBody UpmsResult saveChickenType(ChickenType chickenType) {
		try {
			return baseInputService.saveChickenType(chickenType);
		} catch (Exception e) {
			_log.error("jolley >> listERR:{}",e);
		}
		return new UpmsResult(UpmsResultConstant.FAILED, "获取数据失败！");
	}
	
	/**
	 * 保存禽类录入基础信息
	 * @param result
	 * @param syskeys
	 * @return
	 */
	@RequestMapping("saveChickenBaseinput")
	////	@RequiresPermissions(value = "label:sysparameter:read")
	public @ResponseBody UpmsResult saveChickenBaseinput(ChickenBaseinput chickenBaseinput) {
		try {
			return baseInputService.saveChickenBaseinput(chickenBaseinput);
		} catch (Exception e) {
			_log.error("jolley >> listERR:{}",e);
		}
		
		return new UpmsResult(UpmsResultConstant.FAILED, "获取数据失败！");
	}
	
	/**
	 * 保存疫苗基础录入信息
	 * @param result
	 * @param syskeys
	 * @return
	 */
	@RequestMapping("saveImmuneData")
	////	@RequiresPermissions(value = "label:sysparameter:read")
	public @ResponseBody UpmsResult saveImmuneData(ImmuneBaseinput  immuneBaseinput) {
		try {
			return baseInputService.saveImmuneBaseinput(immuneBaseinput);
		} catch (Exception e) {
			_log.error("jolley >> listERR:{}",e);
		}
		
		return new UpmsResult(UpmsResultConstant.FAILED, "获取数据失败！");
	}


}
