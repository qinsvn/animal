package com.label.common.model.custom.permission;

import java.util.List;

import com.label.common.model.base.Permission;

public class PermissionMenu extends Permission{

	private List<PermissionMenu> childMenus;
	
	public List<PermissionMenu> getChildMenus() {
		return childMenus;
	}
	
	public void setChildMenus(List<PermissionMenu> childMenus) {
		this.childMenus = childMenus;
	}
}
