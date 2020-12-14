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

import com.ts.animal.domain.SupplierDO;
import com.ts.animal.service.SupplierService;
import com.ts.common.service.ExportExcelSevice;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;
import com.ts.common.utils.Result;
import com.ts.common.utils.ShiroUtils;
import com.ts.common.utils.StringUtils;
import com.ts.common.utils.UtilExportExcelPlus;
import com.ts.system.util.SystemUtil;

/**
 * 供应商表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:05:30
 */
 
@Controller
@RequestMapping("/animal/supplier")
public class SupplierController {
	@Autowired
	private SupplierService supplierService;
	
	@GetMapping()
	@RequiresPermissions("animal:supplier:supplier")
	String Supplier(){
	    return "animal/supplier/supplier";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("animal:supplier:supplier")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SupplierDO> supplierList = supplierService.list(query);
		int total = supplierService.count(query);
		PageUtils pageUtils = new PageUtils(supplierList, total);
		//导出 start
		if (StringUtils.isNotEmpty(UtilExportExcelPlus.getFileId(params))) {
			UtilExportExcelPlus.exportExcels(params, supplierList, new ExportExcelSevice() {
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
	public List<SupplierDO> dictList(@RequestParam Map<String, Object> params){
		ShiroUtils.setDeptId(Long.valueOf(params.get("cDeptId").toString()));
		//查询列表数据
		List<SupplierDO> spaceList = supplierService.list(params);
		return spaceList;
	}
	
	
	@GetMapping("/add")
	@RequiresPermissions("animal:supplier:add")
	String add(){
	    return "animal/supplier/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("animal:supplier:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		SupplierDO supplier = supplierService.get(id);
		model.addAttribute("supplier", supplier);
	    return "animal/supplier/edit";
	}
	
	/**
	 * 保存
	 */
	@Log("添加供应商信息")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("animal:supplier:add")
	public Result save( SupplierDO supplier){
		if(supplierService.save(null,supplier)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@Log("修改供应商信息")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("animal:supplier:edit")
	public Result update( SupplierDO supplier){
		supplierService.update(null,supplier);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@Log("删除供应商信息")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("animal:supplier:remove")
	public Result remove( Integer id){
		if(supplierService.remove(id)>0){
		return Result.ok();
		}
		return Result.error();
	}
	
	/**
	 * 删除
	 */
	@Log("批量删除供应商信息")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("animal:supplier:batchRemove")
	public Result remove(@RequestParam("ids[]") Integer[] ids){
		supplierService.batchRemove(ids);
		return Result.ok();
	}
	
}
