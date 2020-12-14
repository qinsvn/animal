package com.label.service.web;

import java.util.List;

import com.label.common.constant.UpmsResult;
import com.label.common.model.base.Permission;

public interface PermissionService {

	UpmsResult treeList(Permission info);

	List<String> listInIds(List<Integer> permissionidList);

}
