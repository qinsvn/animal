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

import com.ts.animal.domain.CheckRecordDO;
import com.ts.animal.service.CheckRecordService;
import com.ts.common.service.ExportExcelSevice;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;
import com.ts.common.utils.Result;
import com.ts.common.utils.StringUtils;
import com.ts.common.utils.UtilExportExcel;
import com.ts.common.utils.UtilExportExcelPlus;
import com.ts.system.util.SystemUtil;

/**
 * 检查记录表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 21:54:38
 */
 
@Controller
@RequestMapping("/animal/checkRecord")
public class CheckRecordController {
	@Autowired
	private CheckRecordService checkRecordService;
	
	@GetMapping()
	@RequiresPermissions("animal:checkRecord:checkRecord")
	String CheckRecord(){
	    return "animal/checkRecord/checkRecord";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("animal:checkRecord:checkRecord")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CheckRecordDO> checkRecordList = checkRecordService.list(query);
		int total = checkRecordService.count(query);
		PageUtils pageUtils = new PageUtils(checkRecordList, total);
		
		//导出 start
		if (StringUtils.isNotEmpty(UtilExportExcelPlus.getFileId(params))) {
			UtilExportExcelPlus.exportExcels(params, checkRecordList, new ExportExcelSevice() {
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
	@RequiresPermissions("animal:checkRecord:add")
	String add(){
	    return "animal/checkRecord/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("animal:checkRecord:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		CheckRecordDO checkRecord = checkRecordService.get(id);
		model.addAttribute("checkRecord", checkRecord);
	    return "animal/checkRecord/edit";
	}
	
	/**
	 * 保存
	 */
	@Log("添加检查记录")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("animal:checkRecord:add")
	public Result save( CheckRecordDO checkRecord){
		if(checkRecordService.save(null,checkRecord)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@Log("修改检查记录")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("animal:checkRecord:edit")
	public Result update( CheckRecordDO checkRecord){
		checkRecordService.update(null,checkRecord);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@Log("删除检查记录")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("animal:checkRecord:remove")
	public Result remove( Integer id){
		if(checkRecordService.remove(id)>0){
		return Result.ok();
		}
		return Result.error();
	}
	
	/**
	 * 删除
	 */
	@Log("批量删除检查记录")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("animal:checkRecord:batchRemove")
	public Result remove(@RequestParam("ids[]") Integer[] ids){
		checkRecordService.batchRemove(ids);
		return Result.ok();
	}
	
}
