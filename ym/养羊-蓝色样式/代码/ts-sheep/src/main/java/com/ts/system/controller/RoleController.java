package com.ts.system.controller;

import com.ts.common.annotation.Log;
import com.ts.common.constant.CommonConstant;
import com.ts.common.controller.BaseController;
import com.ts.common.utils.Result;
import com.ts.system.domain.RoleDO;
import com.ts.system.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sys/role")
@Controller
public class RoleController extends BaseController {
	String prefix = "system/role";
	@Autowired
	RoleService roleService;

	@RequiresPermissions("sys:role:role")
	@GetMapping()
	String role() {
		return prefix + "/role";
	}

	@RequiresPermissions("sys:role:role")
	@RequestMapping("/list")
	@ResponseBody()
	List<RoleDO> list() {
		List<RoleDO> roles = roleService.list();
		return roles;
	}

	@Log("添加角色")
	@RequiresPermissions("sys:role:add")
	@GetMapping("/add")
	String add() {
		return prefix + "/add";
	}

	@Log("编辑角色")
	@RequiresPermissions("sys:role:edit")
	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Long id, Model model) {
		RoleDO roleDO = roleService.get(id);
		model.addAttribute("role", roleDO);
		return prefix + "/edit";
	}

	@Log("保存角色")
	@RequiresPermissions("sys:role:add")
	@PostMapping("/save")
	@ResponseBody()
	Result save(RoleDO role) {
		if (CommonConstant.DEMO_ACCOUNT.equals(getUsername())) {
			return Result.error(Result.FAIL, "演示系统不允许修改,完整体验请部署程序");
		}
		if (roleService.save(role) > 0) {
			return Result.ok();
		} else {
			return Result.error(Result.FAIL, "保存失败");
		}
	}

	@Log("更新角色")
	@RequiresPermissions("sys:role:edit")
	@PostMapping("/update")
	@ResponseBody()
	Result update(RoleDO role) {
		if (CommonConstant.DEMO_ACCOUNT.equals(getUsername())) {
			return Result.error(Result.FAIL, "演示系统不允许修改,完整体验请部署程序");
		}
		if (roleService.update(role) > 0) {
			return Result.ok();
		} else {
			return Result.error(Result.FAIL, "保存失败");
		}
	}

	@Log("删除角色")
	@RequiresPermissions("sys:role:remove")
	@PostMapping("/remove")
	@ResponseBody()
	Result save(Long id) {
		if (CommonConstant.DEMO_ACCOUNT.equals(getUsername())) {
			return Result.error(Result.FAIL, "演示系统不允许修改,完整体验请部署程序");
		}
		if (roleService.remove(id) > 0) {
			return Result.ok();
		} else {
			return Result.error(Result.FAIL, "删除失败");
		}
	}
	
	@RequiresPermissions("sys:role:batchRemove")
	@Log("批量删除角色")
	@PostMapping("/batchRemove")
	@ResponseBody
	Result batchRemove(@RequestParam("ids[]") Long[] ids) {
		if (CommonConstant.DEMO_ACCOUNT.equals(getUsername())) {
			return Result.error(Result.FAIL, "演示系统不允许修改,完整体验请部署程序");
		}
		int r = roleService.batchremove(ids);
		if (r > 0) {
			return Result.ok();
		}
		return Result.error();
	}
}
