package com.ts.animal.controller;

import java.util.List;
import java.util.Map;

import com.ts.common.annotation.Log;
import com.ts.common.utils.*;
import com.ts.system.util.SystemUtil;
import com.ts.uhf.UhfReader;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ts.animal.domain.AccessRecordDO;
import com.ts.animal.service.AccessRecordService;
import com.ts.common.service.ExportExcelSevice;

/**
 * 进出记录表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-06-06 15:45:53
 */
 
@Controller
@RequestMapping("/animal/accessRecord")
public class AccessRecordController {
	@Autowired
	private AccessRecordService accessRecordService;
	
	@GetMapping()
	@RequiresPermissions("animal:accessRecord:accessRecord")
	String AccessRecord(){
	    return "animal/accessRecord/accessRecord";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("animal:accessRecord:accessRecord")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AccessRecordDO> accessRecordList = accessRecordService.list(query);
		int total = accessRecordService.count(query);
		PageUtils pageUtils = new PageUtils(accessRecordList, total);
		
			//导出 start
		if (StringUtils.isNotEmpty(UtilExportExcelPlus.getFileId(params))) {
			UtilExportExcelPlus.exportExcels(params, accessRecordList, new ExportExcelSevice() {
				@Override
				public Object changeVal(String key, Object val, int index) {
					// TODO Auto-generated method stub
					if (key.equals("direction")) {
						return SystemUtil.getDicts().get("access_direction").get(val).getName();
					}
					if (key.equals("varietyName")) {
						return SystemUtil.getDicts().get("animal_varieties").get(val).getName();
					}
					if (key.equals("occurrenceTime")) {
						return DateUtils.stampToDate(val.toString(),DateUtils.DATE_TIME_PATTERN);
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
	@RequiresPermissions("animal:accessRecord:add")
	String add(){
	    return "animal/accessRecord/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("animal:accessRecord:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		AccessRecordDO accessRecord = accessRecordService.get(id);
		model.addAttribute("accessRecord", accessRecord);
	    return "animal/accessRecord/edit";
	}
	
	/**
	 * 保存
	 */
	@Log("添加进出栏记录")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("animal:accessRecord:add")
	public Result save( AccessRecordDO accessRecord){
		Result result = Result.ok();
		accessRecordService.save(result,accessRecord);
		return result;
	}
	/**
	 * 修改
	 */
	@Log("修改进出栏记录")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("animal:accessRecord:edit")
	public Result update( AccessRecordDO accessRecord){
		Result result = Result.ok();
		accessRecordService.update(result,accessRecord);
		return result;
	}
	
	/**
	 * 删除
	 */
	@Log("删除进出栏记录")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("animal:accessRecord:remove")
	public Result remove( Integer id){
		Result result = Result.ok();
		accessRecordService.remove(result,id);
		return result;
	}
	
	/**
	 * 删除
	 */
	@Log("批量删除进出栏记录")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("animal:accessRecord:batchRemove")
	public Result remove(@RequestParam("ids[]") Integer[] ids){
		Result result = Result.ok();
		accessRecordService.batchRemove(result,ids);
		return result;
	}

	@GetMapping("/put/{num}")
	@ResponseBody
	Result put(@PathVariable("num") String num){
		Result result = Result.ok();
		UhfReader.put(num);
		return result;
	}

}
