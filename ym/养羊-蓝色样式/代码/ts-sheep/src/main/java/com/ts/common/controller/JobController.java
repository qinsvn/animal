package com.ts.common.controller;

import com.ts.common.constant.CommonConstant;
import com.ts.common.domain.TaskDO;
import com.ts.common.service.JobService;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;
import com.ts.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2017-09-26 20:53:48
 */
@Controller
@RequestMapping("/common/job")
public class JobController extends BaseController{
	@Autowired
	private JobService taskScheduleJobService;

	@GetMapping()
	String taskScheduleJob() {
		return "common/job/job";
	}

	@ResponseBody
	@RequestMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<TaskDO> taskScheduleJobList = taskScheduleJobService.list(query);
		int total = taskScheduleJobService.count(query);
		PageUtils pageUtils = new PageUtils(taskScheduleJobList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	String add() {
		return "common/job/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Long id, Model model) {
		TaskDO job = taskScheduleJobService.get(id);
		model.addAttribute("job", job);
		return "common/job/edit";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public Result info(@PathVariable("id") Long id) {
		TaskDO taskScheduleJob = taskScheduleJobService.get(id);
		return Result.ok().put("taskScheduleJob", taskScheduleJob);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public Result save(TaskDO taskScheduleJob) {
		if (CommonConstant.DEMO_ACCOUNT.equals(getUsername())) {
			return Result.error(Result.FAIL, "演示系统不允许修改,完整体验请部署程序");
		}
		if (taskScheduleJobService.save(taskScheduleJob) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@PostMapping("/update")
	public Result update(TaskDO taskScheduleJob) {
		if (CommonConstant.DEMO_ACCOUNT.equals(getUsername())) {
			return Result.error(Result.FAIL, "演示系统不允许修改,完整体验请部署程序");
		}
		taskScheduleJobService.update(taskScheduleJob);
		return Result.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	public Result remove(Long id) {
		if (CommonConstant.DEMO_ACCOUNT.equals(getUsername())) {
			return Result.error(Result.FAIL, "演示系统不允许修改,完整体验请部署程序");
		}
		if (taskScheduleJobService.remove(id) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	public Result remove(@RequestParam("ids[]") Long[] ids) {
		if (CommonConstant.DEMO_ACCOUNT.equals(getUsername())) {
			return Result.error(Result.FAIL, "演示系统不允许修改,完整体验请部署程序");
		}
		taskScheduleJobService.batchRemove(ids);

		return Result.ok();
	}

	@PostMapping(value = "/changeJobStatus")
	@ResponseBody
	public Result changeJobStatus(Long id,String cmd ) {
		if (CommonConstant.DEMO_ACCOUNT.equals(getUsername())) {
			return Result.error(Result.FAIL, "演示系统不允许修改,完整体验请部署程序");
		}
		String label = "停止";
		if ("start".equals(cmd)) {
			label = "启动";
		} else {
			label = "停止";
		}
		try {
			taskScheduleJobService.changeStatus(id, cmd);
			return Result.ok("任务" + label + "成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.ok("任务" + label + "失败");
	}

}
