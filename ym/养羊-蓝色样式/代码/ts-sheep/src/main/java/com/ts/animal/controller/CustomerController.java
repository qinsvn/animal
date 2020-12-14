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

import com.ts.animal.domain.CustomerDO;
import com.ts.animal.service.CustomerService;
import com.ts.common.service.ExportExcelSevice;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;
import com.ts.common.utils.Result;
import com.ts.common.utils.ShiroUtils;
import com.ts.common.utils.StringUtils;
import com.ts.common.utils.UtilExportExcelPlus;
import com.ts.system.util.SystemUtil;

/**
 * 客户表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 21:57:31
 */
 
@Controller
@RequestMapping("/animal/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping()
	@RequiresPermissions("animal:customer:customer")
	String Customer(){
	    return "animal/customer/customer";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("animal:customer:customer")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CustomerDO> customerList = customerService.list(query);
		int total = customerService.count(query);
		PageUtils pageUtils = new PageUtils(customerList, total);
		//导出 start
				if (StringUtils.isNotEmpty(UtilExportExcelPlus.getFileId(params))) {
					UtilExportExcelPlus.exportExcels(params, customerList, new ExportExcelSevice() {
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
	public List<CustomerDO> dictList(@RequestParam Map<String, Object> params){
		ShiroUtils.setDeptId(Long.valueOf(params.get("cDeptId").toString()));
		//查询列表数据
		List<CustomerDO> customerList = customerService.list(params);
		return customerList;
	}
	
	
	@GetMapping("/add")
	@RequiresPermissions("animal:customer:add")
	String add(){
	    return "animal/customer/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("animal:customer:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		CustomerDO customer = customerService.get(id);
		model.addAttribute("customer", customer);
	    return "animal/customer/edit";
	}
	
	/**
	 * 保存
	 */
	@Log("添加客户")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("animal:customer:add")
	public Result save( CustomerDO customer){
		if(customerService.save(null,customer)>0){
			return Result.ok();
		}
		return Result.error();
	}
	/**
	 * 修改
	 */
	@Log("修改客户信息")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("animal:customer:edit")
	public Result update( CustomerDO customer){
		customerService.update(null,customer);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@Log("删除客户信息")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("animal:customer:remove")
	public Result remove( Integer id){
		if(customerService.remove(id)>0){
		return Result.ok();
		}
		return Result.error();
	}
	
	/**
	 * 删除
	 */
	@Log("批量删除客户信息")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("animal:customer:batchRemove")
	public Result remove(@RequestParam("ids[]") Integer[] ids){
		customerService.batchRemove(ids);
		return Result.ok();
	}
	
}
