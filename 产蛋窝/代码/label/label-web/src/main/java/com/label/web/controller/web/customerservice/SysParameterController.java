package com.label.web.controller.web.customerservice;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.model.base.SysParameter;
import com.label.service.web.SysParameterService;

/**
 * 系统参数管理
 * @author chenjolley
 * 
 */
@Controller
@RequestMapping("web/sysparameter")
public class SysParameterController {
	
	public Logger _log = LoggerFactory.getLogger(SysParameterController.class); // 日志对象
	
	@Resource
	private SysParameterService sysParameterService;
	
	/**
	 * 修改
	 * @param result
	 * @param info
	 * @return
	 */
	@RequestMapping("/alter")
	//@RequiresPermissions(value = "label:sysparameter:update")
	public @ResponseBody UpmsResult alter(String data,String appid) {
		try {
			if(StringUtils.isEmpty(data)) {
				return new UpmsResult(UpmsResultConstant.FAILED, "缺少请求参数！");
			}
			else {
				sysParameterService.alter(1, data);
				return new UpmsResult(UpmsResultConstant.SUCCESS, "保存成功！");
			}
		} catch(Exception e) {
			_log.error("jolley >> alterERR:{}",e);
		}
		
		return new UpmsResult(UpmsResultConstant.FAILED, "保存失败！");
	}
	
	/**
	 * 列表信息
	 * @param result
	 * @param syskeys
	 * @return
	 */
	@RequestMapping("list")
	//@RequiresPermissions(value = "label:sysparameter:read")
	public @ResponseBody UpmsResult list(String category,String appid, String syskeys) {
		try {
			List<SysParameter> list = sysParameterService.list(1, category,appid, syskeys);
			return new UpmsResult(UpmsResultConstant.SUCCESS, list);
		} catch (Exception e) {
			_log.error("jolley >> listERR:{}",e);
		}
		
		return new UpmsResult(UpmsResultConstant.FAILED, "获取数据失败！");
	}

}
