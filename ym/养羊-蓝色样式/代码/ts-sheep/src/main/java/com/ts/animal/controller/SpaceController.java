package com.ts.animal.controller;

import java.util.List;
import java.util.Map;

import com.ts.common.annotation.Log;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ts.animal.domain.SpaceDO;
import com.ts.animal.service.SpaceService;
import com.ts.common.service.ExportExcelSevice;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;
import com.ts.common.utils.Result;
import com.ts.common.utils.ShiroUtils;
import com.ts.common.utils.StringUtils;
import com.ts.common.utils.UtilExportExcelPlus;
import com.ts.system.util.SystemUtil;

/**
 * 场地表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:03:31
 */
 
@Controller
@RequestMapping("/animal/space")
public class SpaceController {
	@Autowired
	private SpaceService spaceService;
	
	@GetMapping()
	@RequiresPermissions("animal:space:space")
	String Space(){
	    return "animal/space/space";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("animal:space:space")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SpaceDO> spaceList = spaceService.list(query);
		int total = spaceService.count(query);
		PageUtils pageUtils = new PageUtils(spaceList, total);
		//导出 start
		if (StringUtils.isNotEmpty(UtilExportExcelPlus.getFileId(params))) {
			UtilExportExcelPlus.exportExcels(params, spaceList, new ExportExcelSevice() {
				@Override
				public Object changeVal(String key, Object val, int index) {
					// TODO Auto-generated method stub
					if (key.equals("typeName")) {
						return SystemUtil.getDicts().get("animal_type").get(val).getName();
					}
					if (key.equals("varietyName")) {
						return SystemUtil.getDicts().get("animal_varieties").get(val).getName();
					}
					if (key.equals("deptId")) {
						Long valLong = Long.valueOf(val.toString());
						return SystemUtil.getDepts().get(valLong).getName();
					}
					if (key.equals("createUser")) {
						Long valLong = Long.valueOf(val.toString());
						return SystemUtil.getUsers().get(valLong).getName();
					}
					return val;
				}
			});
			return null;
		}
		//end 导出结束
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/dict/list")
	public List<SpaceDO> dictList(@RequestParam Map<String, Object> params){
		ShiroUtils.setDeptId(Long.valueOf(params.get("cDeptId").toString()));
		//查询列表数据
		List<SpaceDO> spaceList = spaceService.list(params);
		return spaceList;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("animal:space:add")
	String add(){
	    return "animal/space/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("animal:space:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		SpaceDO space = spaceService.get(id);
		model.addAttribute("space", space);
	    return "animal/space/edit";
	}
	
	/**
	 * 保存
	 */
	@Log("添加场地信息")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("animal:space:add")
	public Result save( SpaceDO space){
		if(spaceService.save(null,space)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@Log("修改场地信息")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("animal:space:edit")
	public Result update( SpaceDO space){
		spaceService.update(null,space);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@Log("删除场地信息")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("animal:space:remove")
	public Result remove( Integer id){
		if(spaceService.remove(id)>0){
		return Result.ok();
		}
		return Result.error();
	}
	
	/**
	 * 删除
	 */
	@Log("批量删除场地信息")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("animal:space:batchRemove")
	public Result remove(@RequestParam("ids[]") Integer[] ids){
		spaceService.batchRemove(ids);
		return Result.ok();
	}
	
}
