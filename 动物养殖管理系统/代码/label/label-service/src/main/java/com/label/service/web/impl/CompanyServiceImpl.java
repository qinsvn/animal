package com.label.service.web.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.label.common.constant.RedisConstant;
import com.label.common.entity.PageBean;
import com.label.common.model.base.Company;
import com.label.common.model.base.CompanyExample;
import com.label.common.util.UtilCloneObj;
import com.label.common.util.UtilExportExcel;
import com.label.common.util.UtilExportFileName;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilMap;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.UtilString;
import com.label.common.util.redis.UtilRedis;
import com.label.dao.auto.AdminUserMapper;
import com.label.dao.auto.CompanyMapper;
import com.label.dao.custom.AdminUserCustomMapper;
import com.label.dao.custom.CompanyCustomMapper;
import com.label.service.web.AdminUserService;
import com.label.service.web.CompanyService;
import com.label.util.UserUtil;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	private Logger _log = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	@Autowired
	private CompanyCustomMapper companyCustomMapper;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Transactional
	@Override
	public Map<String, Object> createCompany(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
	
		try {
				Company company = UtilJson.str2Bean(UtilJson.Obj2Str(map.get("company")), Company.class);
				int inNum = 0;
				
				//生成编号
				List<Company> lastlist= companyCustomMapper.lastCompanys();
				if(lastlist.size()>0){
					company.setFdCode(UtilString.processingnumber(lastlist.get(0).getFdCode().toString()));
				}else{
					company.setFdCode("G000001");
				}
				
				//判断是否有此账号
				/*AdminUser user = UtilJson.str2Bean(UtilJson.Obj2Str(map.get("user")), AdminUser.class);
				AdminUser have = adminUserService.selectByAccount(user.getFdAccount());
				if (have != null){
					result.put("success", 1);
					result.put("msg", "已存在管理员账号，请修改");
					_log.error("jolley >> createCompany Account has already existed");
					return result;
				}*/
				//添加商家
				Date now = new Date();
				company.setFdCreateTime(now);
				company.setFdUpdateTime(now);
				CompanyExample example = new CompanyExample();
				example.createCriteria().andFdNameEqualTo(company.getFdName());
				List<Company> list = companyCustomMapper.selectByExample(example);
				if(list.size()>0){
					result.put("message", "商家名重复");
				}else{
					inNum = companyCustomMapper.insetCompanyReturnId(company);
					result.put("rows", inNum);
					result.put("success", "0");
				}
				UserUtil.initDeptsInfo();
				_log.info("jolley >> addCompany:{}",inNum);
				return result;
		} catch (Exception e) {
			result.put("success", 1);
			_log.error("jolley >> addCompanyERR:{}",e);
			throw new RuntimeException(e);
		}
		//return result;
	}

	@Override
	public Map<String, Object> delCompany(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		int delNum = 0;
		try {
			Integer id = Integer.valueOf(map.get("cid").toString());
			delNum = companyCustomMapper.deleteByPrimaryKey(id);
			UserUtil.initDeptsInfo();
			_log.info("jolley >> delCompany:{},{}",id,delNum);
		} catch (Exception e) {
			_log.error("jolley >> ");
		}
		result.put("rows", delNum);
		return result;
	}

	@Transactional
	@Override
	public Map<String, Object> updateCompany(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		result.put("success", 0);
		int upNum = 0;
		try {
			//更新商家
			Company company = UtilJson.str2Bean(UtilJson.Obj2Str(map.get("company")), Company.class);
			Date now = new Date();
			company.setFdUpdateTime(now);
			CompanyExample example = new CompanyExample();
			example.createCriteria().andIdNotEqualTo(company.getId()).andFdNameEqualTo(company.getFdName());
			List<Company> list = companyCustomMapper.selectByExample(example);
			if(list==null||list.size()==0){
				upNum = companyCustomMapper.updateByPrimaryKeySelective(company);
				if (upNum != 1) {
					result.put("success", 1);
					result.put("msg", "商家信息更新失败！");
					return result;
				}				
			}else{
				result.put("success", 1);
				result.put("msg", "商家名重复！");
				return result;
			}
			UserUtil.initDeptsInfo();
			_log.info("jolley >> updateCompany:{}",upNum);
		} catch (Exception e) {
			result.put("success", 1);
			result.put("msg", "商家用户更新失败！");
			_log.error("jolley >> updateCompanyERR:{}",e);
			throw new RuntimeException(e);
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getCompanyById(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		try {
			Integer id = Integer.valueOf(map.get("cid").toString());
			Company company = companyCustomMapper.selectByPrimaryKey(id);
			if (company != null) {
				result.put("data", company);
			}
//			List<Map<String, Object>> listUser = adminUserCustomMapper.listUserByCompanyId(map);
//			if (listUser != null && listUser.size() > 0) {
//				Map<String, Object> user = listUser.get(0);
//				result.put("user", user);
//			}else{//没有找到预设为空值
//				Map<String, Object> user = new HashMap<>();
//				user.put("id", "");
//				user.put("fdAccount", "");
//				user.put("fdPassword", "");
//				result.put("user", user);
//			}
			_log.info("jolley >> getCompanyById:{}",company);
		} catch (Exception e) {
			_log.error("jolley >> getCompanyByIdERR:{}",e);
		}
		return result;
	}

	@Override
	public Company info(int id) {
		return companyMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public Map<String, Object> listCompany(PageBean<Map<String, Object>> pageBean) {
		Map<String, Object> result = new HashMap<>();
		try {
			PageHelper.startPage(pageBean.getPage(),pageBean.getRows());
			List<Company> listCompanys = companyCustomMapper.listCompanys(pageBean.getCondition());
			PageInfo<Company> pageInfo = new PageInfo<>(listCompanys);
			if (listCompanys != null) {
				result.put("rows", pageInfo.getList());
				result.put("total", pageInfo.getTotal());
			}
			_log.info("jolley >> listCompany:{}",listCompanys);
		} catch (Exception e) {
			_log.error("jolley >> listCompanyERR:{}",e);
		}
		return result;
	}

	@Override
	public Map<String, Object> insetCompanys(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		int inNum = 0;
		try {
			List<Map<String, Object>> errList = new ArrayList<>();
			checkData(map,errList);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String,Object>>)map.get("list");
			if (list.size() > 0) {
				inNum = companyCustomMapper.insetCompanys(map);
			}
			result.put("rows", inNum);
			result.put("errRows", errList.size());
			UserUtil.initDeptsInfo();
			_log.info("jolley >> insetCompanys:{},{}",inNum,JSONUtils.toJSONString(map));
		} catch (Exception e) {
			_log.error("jolley >> insetCompanysERR:{}",e);
		}
		return result;
	}
	
	/**
	 * 对比转换数据
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	private void checkData(Map<String, Object> map,List<Map<String, Object>> errList) throws ClassNotFoundException, IOException {
		//获取参数信息
		List<Map<String, Object>> list = (List<Map<String, Object>>)map.get("list");
		List<Map<String, Object>> insertList = UtilCloneObj.deepCopy(list);
		Map<String, Object> paramet = new HashMap<>();
		//获取数据库中的商家信息
		List<Company> coms = companyCustomMapper.listCompanys(paramet);
		if (list != null && list.size() > 0) {
			//遍历参数中的数据
			for(int i = 0; i < list.size(); i++){
				Map<String, Object> pra = list.get(i);
				//没有商家码记录错误
				if (StringUtils.isBlank(pra.get("fdCode").toString())) {
					errList.add(pra);
					insertList.remove(pra);
					continue;
				}
				if (pra.get("fdCode").toString().length() != 4) {
					errList.add(pra);
					insertList.remove(pra);
					continue;
				}
				//商家代码格式错误记录错误
				if (!ValidateCompanyCode(pra.get("fdCode").toString())) {
					errList.add(pra);
					insertList.remove(pra);
					continue;
				}
				//名字为空记录错误
				if (pra.get("fdName").toString().equals("")) {
					errList.add(pra);
					insertList.remove(pra);
					continue;
				}
				//电话,邮箱,传真至少有一个不为空
				if (pra.get("fdPhone").toString().equals("") && pra.get("fdFax").toString().equals("")
						&& pra.get("fdEmail").toString().equals("")) {
					//相同插入错误列表
					errList.add(pra);
					insertList.remove(pra);
					continue;
				}
				
				//遍历数据库中的数据
				boolean entity = false;
				for(int j = 0; j < coms.size(); j++){
					Company com = coms.get(j);
					//数据库中存在商家代码 或者 商家名相等 就记录错误
					if (com.getFdCode().equals(pra.get("fdCode").toString()) || com.getFdName().equals(pra.get("fdName").toString())) {
						entity = true;//数据库中存在的标记
						//相同插入错误列表
						errList.add(pra);
						insertList.remove(pra);
						break;
					}
				}
				
				if (!entity) {
					Company map2Java = UtilMap.map2Java(new Company(), pra);
					coms.add(map2Java);
				}
			}
			//更新参数map
			map.put("list", insertList);
		}
	}

	/**
	 * 正则校验商家代码
	 * @param code
	 * @return
	 */
	public boolean ValidateCompanyCode(String code) {
		final String strReGex = "^[0-9a-zA-Z]*$";
		//final String strReGex = "^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*)$";
		Pattern pattern = Pattern.compile(strReGex);
		Matcher matcher = pattern.matcher(code);
		return matcher.find();
	}
	
	@Override
	public Object exportExcel(PageBean<Map<String, Object>> pageBean) {
		try {
			//获取数据库中的商家信息
			List<Company> coms = companyCustomMapper.listCompanys(pageBean.getCondition());
			// 表格属性列名数组
			String[] strHeader = {"商家编号","商家名称","商家类型","领域","电子邮箱","电话","传真"};
			// 表格属性列名key数组
			String[] keys = {"fdCode","fdName","fdType","fdDomain","fdEmail","fdPhone","fdFax"};
			// 标题
			String title = "OutBountResultList";
			// 保存的文件夹路径
			String sys_save_path = UtilPropertiesFile.getInstance().get("sys_save_path");
			String sys_exportpath = UtilPropertiesFile.getInstance().get("sys_exportpath");
			String savePath = sys_save_path + sys_exportpath + UtilExportFileName.getName();
			//测试地址
			//savePath = "C:\\actionTool\\export" + UtilExportFileName.getName();
			File fileDic = new File(savePath);
			if (!fileDic.exists()) {
				// 无此文件夹时创建
				fileDic.mkdirs();
				
				//设置权限
				fileDic.getParentFile().setReadable(true);
				fileDic.getParentFile().setExecutable(true);
				fileDic.setReadable(true);
				fileDic.setExecutable(true);
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String now = df.format(new Date());
			// 文件保存的名字
			String fileName = "商家信息"+now+".xls";
			// 保存的文件路径
			String filePath = savePath + fileName;
			_log.info("jolley >> exportExcel:{}", filePath);
			// 保存文件
			OutputStream out = new FileOutputStream(filePath);
			
			UtilExportExcel.exportExcels(title, strHeader, keys, coms, out, "yyyy-MM-dd HH:mm:ss",null);
			out.close();
			String fileId = UUID.randomUUID().toString();
			UtilRedis.hset(RedisConstant.LABEL_EXPORT_EXCEL_PATH, fileId, filePath);
			
			Map<String, Object> result = new HashMap<>();
			result.put("fileId", fileId);
			return result;
			
		} catch (Exception e) {
			_log.error("jolley --> exportExcelErr:{}", e);
		}
		
		return null;
	}

	@Transactional
	@Override
	public int deleteCompanys(Map<String, Object> map) {
		try {
			companyCustomMapper.deleteCompanys(map);
//			adminUserCustomMapper.updateCompanyUser(map);
			UserUtil.initDeptsInfo();
			_log.info("jolley >> deleteCompanys:{}");
		} catch (Exception e) {
			_log.error("jolley >> deleteCompanysERR:{}",e);
			throw new RuntimeException(e);
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> listAllCompanys(Map<String, Object> map) {
		List<Map<String, Object>> listAllCompanys = companyCustomMapper.listAllCompanys(map);
		return listAllCompanys;
	}
	
	@Override
	public List<Company> lastCompanys() {
		List<Company> lastCompanys = companyCustomMapper.lastCompanys();
		return lastCompanys;
	}	
}
