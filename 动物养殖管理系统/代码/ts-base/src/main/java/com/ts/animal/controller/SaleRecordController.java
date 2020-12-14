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

import com.ts.animal.domain.SaleRecordDO;
import com.ts.animal.service.SaleRecordService;
import com.ts.common.service.ExportExcelSevice;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;
import com.ts.common.utils.Result;
import com.ts.common.utils.StringUtils;
import com.ts.common.utils.UtilExportExcelPlus;
import com.ts.system.util.SystemUtil;

/**
 * 销售记录表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:02:22
 */
 
@Controller
@RequestMapping("/animal/saleRecord")
public class SaleRecordController {
	@Autowired
	private SaleRecordService saleRecordService;
	
	@GetMapping()
	@RequiresPermissions("animal:saleRecord:saleRecord")
	String SaleRecord(){
	    return "animal/saleRecord/saleRecord";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("animal:saleRecord:saleRecord")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Map<String, Object>> saleRecordList = saleRecordService.exList(query);
		int total = saleRecordService.count(query);
		PageUtils pageUtils = new PageUtils(saleRecordList, total);
		//导出 start
		if (StringUtils.isNotEmpty(UtilExportExcelPlus.getFileId(params))) {
			UtilExportExcelPlus.exportExcels(params, saleRecordList, new ExportExcelSevice() {
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
	@RequiresPermissions("animal:saleRecord:add")
	String add(){
	    return "animal/saleRecord/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("animal:saleRecord:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		SaleRecordDO saleRecord = saleRecordService.get(id);
		model.addAttribute("saleRecord", saleRecord);
	    return "animal/saleRecord/edit";
	}
	
	/**
	 * 保存 售卖出库
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("animal:saleRecord:add")
	public Result save( SaleRecordDO saleRecord){
		if(saleRecordService.save(null,saleRecord)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("animal:saleRecord:edit")
	public Result update( SaleRecordDO saleRecord){
		saleRecordService.update(null,saleRecord);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("animal:saleRecord:remove")
	public Result remove( Integer id){
		if(saleRecordService.remove(id)>0){
		return Result.ok();
		}
		return Result.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("animal:saleRecord:batchRemove")
	public Result remove(@RequestParam("ids[]") Integer[] ids){
		saleRecordService.batchRemove(ids);
		return Result.ok();
	}
	
}
