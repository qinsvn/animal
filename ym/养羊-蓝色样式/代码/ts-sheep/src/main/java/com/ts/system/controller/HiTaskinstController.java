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

import com.ts.system.domain.HiTaskinstDO;
import com.ts.system.service.HiTaskinstService;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;
import com.ts.common.utils.Result;

/**
 * 
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2019-11-14 10:59:45
 */
 
@Controller
@RequestMapping("/system/hiTaskinst")
public class HiTaskinstController {
	@Autowired
	private HiTaskinstService hiTaskinstService;
	
	@GetMapping()
	@RequiresPermissions("system:hiTaskinst:hiTaskinst")
	String HiTaskinst(){
	    return "system/hiTaskinst/hiTaskinst";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("system:hiTaskinst:hiTaskinst")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<HiTaskinstDO> hiTaskinstList = hiTaskinstService.list(query);
		int total = hiTaskinstService.count(query);
		PageUtils pageUtils = new PageUtils(hiTaskinstList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:hiTaskinst:add")
	String add(){
	    return "system/hiTaskinst/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:hiTaskinst:edit")
	String edit(@PathVariable("id") String id,Model model){
		HiTaskinstDO hiTaskinst = hiTaskinstService.get(id);
		model.addAttribute("hiTaskinst", hiTaskinst);
	    return "system/hiTaskinst/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:hiTaskinst:add")
	public Result save( HiTaskinstDO hiTaskinst){
		if(hiTaskinstService.save(hiTaskinst)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:hiTaskinst:edit")
	public Result update( HiTaskinstDO hiTaskinst){
		hiTaskinstService.update(hiTaskinst);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:hiTaskinst:remove")
	public Result remove( String id){
		if(hiTaskinstService.remove(id)>0){
		return Result.ok();
		}
		return Result.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:hiTaskinst:batchRemove")
	public Result remove(@RequestParam("ids[]") String[] ids){
		hiTaskinstService.batchRemove(ids);
		return Result.ok();
	}
	
}
