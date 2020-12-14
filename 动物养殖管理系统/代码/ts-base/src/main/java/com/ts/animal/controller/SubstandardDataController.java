package com.ts.animal.controller;

import java.util.List;
import java.util.Map;

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

import com.ts.animal.domain.SubstandardDataDO;
import com.ts.animal.service.SubstandardDataService;
import com.ts.common.service.ExportExcelSevice;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;
import com.ts.common.utils.Result;
import com.ts.common.utils.StringUtils;
import com.ts.common.utils.UtilExportExcelPlus;
import com.ts.system.util.SystemUtil;

/**
 * 耗损数据表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:04:36
 */
 
@Controller
@RequestMapping("/animal/substandardData")
public class SubstandardDataController {
	@Autowired
	private SubstandardDataService substandardDataService;
	
	@GetMapping()
	@RequiresPermissions("animal:substandardData:substandardData")
	String SubstandardData(){
	    return "animal/substandardData/substandardData";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("animal:substandardData:substandardData")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SubstandardDataDO> substandardDataList = substandardDataService.list(query);
		int total = substandardDataService.count(query);
		PageUtils pageUtils = new PageUtils(substandardDataList, total);
		//导出 start
		if (StringUtils.isNotEmpty(UtilExportExcelPlus.getFileId(params))) {
			UtilExportExcelPlus.exportExcels(params, substandardDataList, new ExportExcelSevice() {
				@Override
				public Object changeVal(String key, Object val, int index) {
					// TODO Auto-generated method stub
					if (key.equals("typeName")) {
						return SystemUtil.getDicts().get("animal_type").get(val).getName();
					}
					if (key.equals("varietyName")) {
						return SystemUtil.getDicts().get("animal_varieties").get(val).getName();
					}
					if (key.equals("substandardReason")) {
						return SystemUtil.getDicts().get("substandard_reason").get(val).getName();
					}
					if (key.equals("deptId")) {
						Long valLong = Long.valueOf(val.toString());
						return SystemUtil.getDepts().get(valLong).getName();
					}
					if (key.equals("createUser")) {
						Long valLong = Long.valueOf(val.toString());
						return SystemUtil.getUsers().get(valLong).getName();
					}
					if (key.equals("approvalId")) {
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
	

	/**
	 * 审核次品
	 */
	@ResponseBody
	@PostMapping("/approval")
	@RequiresPermissions("animal:substandardData:approval")
	public Result approval( @RequestParam("ids[]") Integer[] ids){
		if(substandardDataService.approval(ids)>0){
			return Result.ok();
		}
		return Result.error();
	}
	
	
	@GetMapping("/add")
	@RequiresPermissions("animal:substandardData:add")
	String add(){
	    return "animal/substandardData/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("animal:substandardData:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		SubstandardDataDO substandardData = substandardDataService.get(id);
		model.addAttribute("substandardData", substandardData);
	    return "animal/substandardData/edit";
	}
	
	
	/**
	 * 加入耗损
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("animal:substandardData:add")
	public Result save( SubstandardDataDO substandardData){
		if(substandardDataService.save(null,substandardData)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("animal:substandardData:edit")
	public Result update( SubstandardDataDO substandardData){
		substandardDataService.update(null,substandardData);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("animal:substandardData:remove")
	public Result remove( Integer id){
		if(substandardDataService.remove(id)>0){
		return Result.ok();
		}
		return Result.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("animal:substandardData:batchRemove")
	public Result remove(@RequestParam("ids[]") Integer[] ids){
		substandardDataService.batchRemove(ids);
		return Result.ok();
	}
	
}
