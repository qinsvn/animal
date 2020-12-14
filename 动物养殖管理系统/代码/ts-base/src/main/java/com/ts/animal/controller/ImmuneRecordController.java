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

import com.ts.animal.domain.ImmuneRecordDO;
import com.ts.animal.service.ImmuneRecordService;
import com.ts.common.service.ExportExcelSevice;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;
import com.ts.common.utils.Result;
import com.ts.common.utils.StringUtils;
import com.ts.common.utils.UtilExportExcel;
import com.ts.common.utils.UtilExportExcelPlus;
import com.ts.system.util.SystemUtil;

/**
 * 检疫记录表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 21:59:00
 */
 
@Controller
@RequestMapping("/animal/immuneRecord")
public class ImmuneRecordController {
	@Autowired
	private ImmuneRecordService immuneRecordService;
	
	@GetMapping()
	@RequiresPermissions("animal:immuneRecord:immuneRecord")
	String ImmuneRecord(){
	    return "animal/immuneRecord/immuneRecord";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("animal:immuneRecord:immuneRecord")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ImmuneRecordDO> immuneRecordList = immuneRecordService.list(query);
		int total = immuneRecordService.count(query);
		PageUtils pageUtils = new PageUtils(immuneRecordList, total);
		
		//导出 start
		if (StringUtils.isNotEmpty(UtilExportExcelPlus.getFileId(params))) {
			UtilExportExcelPlus.exportExcels(params, immuneRecordList, new ExportExcelSevice() {
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
	
	@GetMapping("/add")
	@RequiresPermissions("animal:immuneRecord:add")
	String add(){
	    return "animal/immuneRecord/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("animal:immuneRecord:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ImmuneRecordDO immuneRecord = immuneRecordService.get(id);
		model.addAttribute("immuneRecord", immuneRecord);
	    return "animal/immuneRecord/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("animal:immuneRecord:add")
	public Result save( ImmuneRecordDO immuneRecord){
		if(immuneRecordService.save(null,immuneRecord)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("animal:immuneRecord:edit")
	public Result update( ImmuneRecordDO immuneRecord){
		immuneRecordService.update(null,immuneRecord);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("animal:immuneRecord:remove")
	public Result remove( Integer id){
		if(immuneRecordService.remove(id)>0){
		return Result.ok();
		}
		return Result.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("animal:immuneRecord:batchRemove")
	public Result remove(@RequestParam("ids[]") Integer[] ids){
		immuneRecordService.batchRemove(ids);
		return Result.ok();
	}
	
}
