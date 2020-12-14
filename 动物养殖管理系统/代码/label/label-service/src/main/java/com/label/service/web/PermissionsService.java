package com.label.service.web;

import java.util.List;

import com.label.common.model.base.Permission;

public interface PermissionsService {

	List<Permission> listPermission(Integer roleType);

}
