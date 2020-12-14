package com.label.web.controller.web.permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.model.base.Permission;
import com.label.service.web.PermissionService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("web/permission")
public class PermissionController {

	Logger _log = LoggerFactory.getLogger(PermissionController.class);
	
	@Autowired
	private PermissionService permissionService;
	
	 @ApiOperation(value = "树状列表")
//	 @RequiresPermissions("label:permission:read")
	 @RequestMapping(value = "/treeList", method = RequestMethod.POST)
	 @ResponseBody
	 public UpmsResult list(Permission info) {
        try{
        	return permissionService.treeList(info);
        }catch(Exception e){
        	_log.error("jolley >> treeList 查询失败:", e);
        	return new UpmsResult(UpmsResultConstant.FAILED,"获取列表失败");
        }
    }
	
}