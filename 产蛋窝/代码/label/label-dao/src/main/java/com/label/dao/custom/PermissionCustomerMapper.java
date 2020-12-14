package com.label.dao.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.label.common.model.base.Permission;
import com.label.common.model.custom.permission.PermissionMenu;

public interface PermissionCustomerMapper {

	List<PermissionMenu> selectByList(Permission info);

	List<String> listInIds(@Param("ids")List<Integer> ids);

}
