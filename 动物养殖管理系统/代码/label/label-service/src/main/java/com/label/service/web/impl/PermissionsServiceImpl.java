package com.label.service.web.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.label.common.model.base.Permission;
import com.label.common.model.base.PermissionExample;
import com.label.dao.auto.PermissionMapper;
import com.label.service.web.PermissionsService;
@Service("permissionsService")
@Transactional
public class PermissionsServiceImpl implements PermissionsService {

	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	public List<Permission> listPermission(Integer roleType) {
		try{
		}catch(Exception e){
			
		}
		return null;
	}
	
}
