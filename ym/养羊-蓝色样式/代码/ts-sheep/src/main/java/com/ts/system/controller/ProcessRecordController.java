package com.ts.system.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ts.system.domain.ProcessRecordDO;
import com.ts.system.service.ProcessRecordService;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;
import com.ts.common.utils.Result;

/**
 * 工单流转记录
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-01-13 17:18:39
 */
 
@Controller
@RequestMapping("/system/processRecord")
public class ProcessRecordController {
	@Autowired
	private ProcessRecordService processRecordService;
	
	@GetMapping()
	@RequiresPermissions("system:processRecord:processRecord")
	String ProcessRecord(){
	    return "system/processRecord/processRecord";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("system:processRecord:processRecord")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ProcessRecordDO> processRecordList = processRecordService.list(query);
		int total = processRecordService.count(query);
		PageUtils pageUtils = new PageUtils(processRecordList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:processRecord:add")
	String add(){
	    return "system/processRecord/add";
	}

	@GetMapping("/edit/{prId}")
	@RequiresPermissions("system:processRecord:edit")
	String edit(@PathVariable("prId") Integer prId,Model model){
		ProcessRecordDO processRecord = processRecordService.get(prId);
		model.addAttribute("processRecord", processRecord);
	    return "system/processRecord/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:processRecord:add")
	public Result save( ProcessRecordDO processRecord){
		if(processRecordService.save(processRecord)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:processRecord:edit")
	public Result update( ProcessRecordDO processRecord){
		processRecordService.update(processRecord);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:processRecord:remove")
	public Result remove( Integer prId){
		if(processRecordService.remove(prId)>0){
		return Result.ok();
		}
		return Result.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:processRecord:batchRemove")
	public Result remove(@RequestParam("ids[]") Integer[] prIds){
		processRecordService.batchRemove(prIds);
		return Result.ok();
	}
	
}
