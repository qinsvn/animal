package com.label.service.web.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.label.common.model.base.AdminUser;
import com.label.common.model.base.AdminUserExample;
import com.label.dao.auto.AdminUserMapper;
import com.label.service.web.AdminUserService;

@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {
	
//	private static Logger _log = LoggerFactory.getLogger(AdminUserServiceImpl.class);

    @Autowired
    AdminUserMapper adminUserCustomMapper;

	@Override
	public AdminUser selectByAccount(String account) {
		AdminUserExample example = new AdminUserExample();
		example.createCriteria().andFdAccountEqualTo(account);
		List<AdminUser> list = adminUserCustomMapper.selectByExample(example);
		
		if(list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}

	@Override
	public AdminUser selectById(int id) {
		return adminUserCustomMapper.selectByPrimaryKey(id);
	}

}
