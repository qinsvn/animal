package com.label.service.web;

import com.label.common.model.base.AdminUser;

public interface AdminUserService {
	
	AdminUser selectByAccount(String account);
	
	AdminUser selectById(int id);
}
