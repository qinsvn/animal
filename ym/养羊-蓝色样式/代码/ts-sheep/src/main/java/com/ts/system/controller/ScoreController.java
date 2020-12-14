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

import com.ts.system.domain.ScoreDO;
import com.ts.system.service.ScoreService;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;
import com.ts.common.utils.Result;

/**
 * 评分模板
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2019-09-20 10:05:04
 */
 
@Controller
@RequestMapping("/system/score")
public class ScoreController {
	@Autowired
	private ScoreService scoreService;
	
	@GetMapping()
	@RequiresPermissions("system:score:score")
	String Score(){
	    return "system/score/score";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("system:score:score")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ScoreDO> scoreList = scoreService.list(query);
		int total = scoreService.count(query);
		PageUtils pageUtils = new PageUtils(scoreList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:score:add")
	String add(){
	    return "system/score/add";
	}

	@GetMapping("/edit/{cid}")
	@RequiresPermissions("system:score:edit")
	String edit(@PathVariable("cid") Integer cid,Model model){
		ScoreDO score = scoreService.get(cid);
		model.addAttribute("score", score);
	    return "system/score/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:score:add")
	public Result save( ScoreDO score){
		if(scoreService.save(score)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:score:edit")
	public Result update( ScoreDO score){
		scoreService.update(score);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:score:remove")
	public Result remove( Integer cid){
		if(scoreService.remove(cid)>0){
		return Result.ok();
		}
		return Result.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:score:batchRemove")
	public Result remove(@RequestParam("ids[]") Integer[] cids){
		scoreService.batchRemove(cids);
		return Result.ok();
	}
	
}
