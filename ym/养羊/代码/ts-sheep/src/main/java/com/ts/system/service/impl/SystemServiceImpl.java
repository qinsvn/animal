package com.ts.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.ts.common.service.DictService;
import com.ts.system.base.BaseInterface;
import com.ts.system.service.DeptService;
import com.ts.system.service.UserService;
import com.ts.system.util.SystemUtil;

@Service
public class SystemServiceImpl implements BaseInterface{

	@Autowired
	private UserService userService;
	@Autowired
	private DeptService sysDeptService;
	@Autowired   
	private ThymeleafViewResolver thymeleafViewResolver; 

	@Autowired
	private DictService dictService;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		SystemUtil.setViewResolver(thymeleafViewResolver);
		SystemUtil.setUsers(userService.users());
		SystemUtil.setDepts(sysDeptService.depts());
	 	SystemUtil.setDicts(dictService.dicts());
	}
}
