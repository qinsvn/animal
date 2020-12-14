package com.label.service.web;

import java.util.List;

import com.label.common.model.base.Role;
import com.label.common.model.base.RoleExample;
import com.label.common.model.base.RoleUserRef;
import com.label.common.model.base.RoleUserRefExample;

public interface RoleService {
	
	long count(RoleExample example);
	
	List<Role> list(int pageNum, int pageSize, RoleExample example);
	
	List<RoleUserRef> selectRoleUserRef(RoleUserRefExample example);
	
	Role info(int id);
	
	int create(Role role);
	
	int createRoleUserRef(RoleUserRef roleUserRef);
	
	int delete(RoleExample example);
	
	int deleteRoleUserRef(RoleUserRefExample example);
	
	int alter(Role record, RoleExample example);
	
}
