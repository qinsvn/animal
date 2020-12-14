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

import com.ts.system.domain.TaskCopyDO;
import com.ts.system.service.TaskCopyService;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;
import com.ts.common.utils.Result;

/**
 * 
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-01-13 10:45:59
 */
 
@Controller
@RequestMapping("/system/taskCopy")
public class TaskCopyController {
	@Autowired
	private TaskCopyService taskCopyService;
	
	@GetMapping()
	@RequiresPermissions("system:taskCopy:taskCopy")
	String TaskCopy(){
	    return "system/taskCopy/taskCopy";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("system:taskCopy:taskCopy")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TaskCopyDO> taskCopyList = taskCopyService.list(query);
		int total = taskCopyService.count(query);
		PageUtils pageUtils = new PageUtils(taskCopyList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:taskCopy:add")
	String add(){
	    return "system/taskCopy/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:taskCopy:edit")
	String edit(@PathVariable("id") Long id,Model model){
		TaskCopyDO taskCopy = taskCopyService.get(id);
		model.addAttribute("taskCopy", taskCopy);
	    return "system/taskCopy/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:taskCopy:add")
	public Result save( TaskCopyDO taskCopy){
		if(taskCopyService.save(taskCopy)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:taskCopy:edit")
	public Result update( TaskCopyDO taskCopy){
		taskCopyService.update(taskCopy);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:taskCopy:remove")
	public Result remove( Long id){
		if(taskCopyService.remove(id)>0){
		return Result.ok();
		}
		return Result.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:taskCopy:batchRemove")
	public Result remove(@RequestParam("ids[]") Long[] ids){
		taskCopyService.batchRemove(ids);
		return Result.ok();
	}
	
}
