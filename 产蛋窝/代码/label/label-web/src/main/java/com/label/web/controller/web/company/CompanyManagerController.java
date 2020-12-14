package com.label.web.controller.web.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.label.common.base.BaseController;
import com.label.common.constant.RedisConstant;
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.entity.PageBean;
import com.label.common.model.base.Company;
import com.label.common.util.UtilExcel;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.redis.UtilRedis;
import com.label.service.web.CompanyService;

import io.swagger.annotations.ApiOperation;

/**
 * 商家管理
 * @author Allen
 *
 */
@Controller
@RequestMapping("/web/company")
public class CompanyManagerController extends BaseController{

	Logger _log = LoggerFactory.getLogger(CompanyManagerController.class);
	
	@Autowired
	private CompanyService companyService;
	
	
	/**
	 * 添加商家
	 * @return
	 */
	@ApiOperation(value = "添加商家信息")
	@RequiresPermissions("label:company:create")
	@RequestMapping(value = "/create")
	@ResponseBody
	public Object createCompany(@RequestBody Map<String, Object> map) {
		Map<String, Object> add = companyService.createCompany(map);
		if (add.get("success") != null && Integer.valueOf(add.get("success").toString()) == 0) {
			return new UpmsResult(UpmsResultConstant.SUCCESS, add);
		}
		return new UpmsResult(UpmsResultConstant.FAILED, add);
	}
	
	/**
	 * 删除商家
	 * @return
	 */
	@ApiOperation(value = "删除商家信息")
	@RequiresPermissions("label:company:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delCompany(@RequestBody Map<String, Object> map) {
		int del = companyService.deleteCompanys(map);
		if (del > 0) {
			return new UpmsResult(UpmsResultConstant.SUCCESS, del);
		}
		return new UpmsResult(UpmsResultConstant.FAILED, "数据删除失败！");
	}
	
	/**
	 * 更新商家信息
	 * @return
	 */
	@ApiOperation(value = "更新商家信息")
	@RequiresPermissions("label:company:update")
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object updateCompany(@RequestBody Map<String, Object> map) {
		Map<String, Object> update = null;
		try {
			update = companyService.updateCompany(map);
			if (update.get("success") != null && Integer.valueOf(update.get("success").toString()) == 0) {
				return new UpmsResult(UpmsResultConstant.SUCCESS, update);
			}
		} catch (NumberFormatException e) {
			return new UpmsResult(UpmsResultConstant.FAILED, update);
		}
		return new UpmsResult(UpmsResultConstant.FAILED, update);
	}
	
	/**
	 * 根据id获取商家信息
	 * @return
	 */
	@ApiOperation(value = "根据id获取商家信息")
	@RequiresPermissions("label:company:read")
	@RequestMapping(value = "/info")
	@ResponseBody
	public Object getCompany(@RequestBody Map<String, Object> map) {
		Map<String, Object> data = companyService.getCompanyById(map);
		if (data.get("data") != null) {
			return new UpmsResult(UpmsResultConstant.SUCCESS, data);
		}
		return new UpmsResult(UpmsResultConstant.FAILED, "获取数据失败！");
	}
	
	/**
	 * 获取商家列表
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取商家列表")
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public Object listCompany(@RequestBody PageBean<Map<String, Object>> pageBean) {
		int companyId = super.getUpmsUserInfo().getCompanyId();
		if (companyId > 0) {
			pageBean.getCondition().put("companyId", companyId);
		}
		Map<String, Object> datas = companyService.listCompany(pageBean);
		if (datas != null) {
			return new UpmsResult(UpmsResultConstant.SUCCESS, datas);
		}
		return new UpmsResult(UpmsResultConstant.FAILED, "获取数据失败");
	}
	
	/**
	 * 导入
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "批量导入商家信息")
	@RequiresPermissions("label:company:create")
	@RequestMapping(value = "/inserts",method = RequestMethod.POST)
	@ResponseBody
	public Object insertConpanys(@RequestBody Map<String, Object> map) {
		try {
			//获取参数
			String fileKey = map.get("filekey").toString();
			//String url = map.get("url").toString();
			String importFilePath = UtilRedis.get(RedisConstant.LABEL_TEMP_ + fileKey);
			String sys_save_path = UtilPropertiesFile.getInstance().get("sys_save_path");
			
			List<Map<String, Object>> readExcel = UtilExcel.readExcel(sys_save_path + importFilePath);
			List<Map<String, Object>> importData = new ArrayList<>();
			if (readExcel.size() > 0) {
				for(int i = 0; i < readExcel.size(); i++){
					Map<String, Object> sheet = readExcel.get(i);
					String[][] data = (String[][]) sheet.get("data");
					for(int j = 1; j < data.length; j++){//从第一行开始，默认第0行为标题
						String fdCode = data[j][0];//获取每行的第一列
						String fdName = data[j][1];
						String fdAddress = data[j][2];
						String fdDomain = data[j][3];
						String fdEmail = data[j][4];
						String fdPhone = data[j][5];
						String fdFax = data[j][6];
						
						Map<String, Object> row = new HashMap<>();
						row.put("fdCode", fdCode);
						row.put("fdName", fdName);
						row.put("fdAddress", fdAddress);
						row.put("fdDomain", fdDomain);
						row.put("fdEmail", fdEmail);
						row.put("fdPhone", fdPhone);
						row.put("fdFax", fdFax);
						
						importData.add(row);
					}
				}
			}
			map.put("list", importData);
			Map<String, Object> insetCompanys = companyService.insetCompanys(map);
			if (insetCompanys.get("rows") != null && Integer.valueOf(insetCompanys.get("rows").toString()) >= 0) {
				return new UpmsResult(UpmsResultConstant.SUCCESS, insetCompanys);
			}
			map.put("msg", "导入数据失败");
			_log.info("jolley >> {}",readExcel);
		}catch (Exception e) {
			_log.error("jolley >> insertConpanysErr{}",e);
		}
		
		return new UpmsResult(UpmsResultConstant.FAILED, map);
	}
	
	/**
	 * 导出Excel
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "批量导出商家信息EXCEL")
	@RequiresPermissions("label:company:read")
	@RequestMapping(value = "/listexport",method = RequestMethod.POST)
	@ResponseBody
	public Object exportExcel(@RequestBody PageBean<Map<String, Object>> pageBean) {
		@SuppressWarnings("unchecked")
		Map<String, Object> exportExcel = (Map<String, Object>) companyService.exportExcel(pageBean);
		if (exportExcel.containsKey("fileId")) {
			return new UpmsResult(UpmsResultConstant.SUCCESS, exportExcel);
		}
		return new UpmsResult(UpmsResultConstant.FAILED, "导出数据失败");
	}
	
	/**
	 * 商家管理页面
	 * @return
	 */
	@ApiOperation(value = "商家管理页面")
	@RequiresPermissions("label:company:read")
	@RequestMapping(value = "/pagecompany",method = RequestMethod.GET)
	public String pagelist() {
		return "/web/company/p_list_company.jsp";
	}
	
	
	/**
	 * 批量删除商家
	 * @return
	 */
	@ApiOperation(value = "批量删除商家")
	@RequiresPermissions("label:company:delete")
	@RequestMapping(value = "/deleteCompanys", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteCompanys(@RequestBody Map<String, Object> map) {
		try {
			companyService.deleteCompanys(map);
		} catch (Exception e) {
			return new UpmsResult(UpmsResultConstant.FAILED, "删除商家信息失败");
		}
		return new UpmsResult(UpmsResultConstant.SUCCESS, "删除商家信息成功");
	}
	
	/**
	 * 获取所有商家 id和名称
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取商家信息列表")
	@RequestMapping(value = "listAllCompanys")
	@ResponseBody
	public Object listAllCompanys(@RequestBody Map<String, Object> map) {
		List<Map<String, Object>> listAllCompanys = companyService.listAllCompanys(map);
		if (listAllCompanys == null) {
			return new UpmsResult(UpmsResultConstant.FAILED, "获取列表失败！");
		}
		return new UpmsResult(UpmsResultConstant.SUCCESS, listAllCompanys);
	}
	
	/**
	 * 获取最后一条数据的商户编号
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "获取最后一条数据的商户编号")
	@RequiresPermissions("label:company:read")
	@RequestMapping(value = "lastCompanys")
	@ResponseBody
	public Object lastCompanys() {
		List<Company> lastCompanys = companyService.lastCompanys();
		if (lastCompanys == null) {
			return new UpmsResult(UpmsResultConstant.FAILED, "获取列表失败！");
		}
		return new UpmsResult(UpmsResultConstant.SUCCESS, lastCompanys);
	}
}
