package com.label.service.web.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.model.base.Permission;
import com.label.common.model.custom.permission.PermissionMenu;
import com.label.common.util.UtilJson;
import com.label.dao.custom.PermissionCustomerMapper;
import com.label.service.web.PermissionService;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	Logger _log = LoggerFactory.getLogger(PermissionServiceImpl.class);
	
	@Autowired
	private PermissionCustomerMapper permissionCustomerMapper;

	@Override
	public UpmsResult treeList(Permission info) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "查询失败");
		try{
			//定义树菜单list
			List<PermissionMenu> menuList = new ArrayList<PermissionMenu>();
			//查询所有菜单信息
			List<PermissionMenu> rootMenu = permissionCustomerMapper.selectByList(info);
			//对数据进行遍历，先把所有父id 为0 的数据取出，得到id列表，再遍历列表
			for(int i = 0; i<rootMenu.size(); i++){
				if(rootMenu.get(i).getFdPid()==0){//一级菜单pid==0
					menuList.add(rootMenu.get(i));
				}
			}
			//查询每一个一级菜单的子菜单
			for(PermissionMenu menu : menuList){
				menu.setChildMenus(getChildsList(menu.getId(),rootMenu));
			}
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("menu", menuList);
			result = new UpmsResult(UpmsResultConstant.SUCCESS, resultMap);
			System.out.println(UtilJson.Obj2Str(resultMap));;
		}catch(Exception e){
			e.printStackTrace();
			_log.error("jolley >> PermissionServiceImpl treeList error info:" , e);
		}
		return result;
	}

	/**
	 * （递归）查询每一个菜单id的所有子菜单
	 * @param id 需要查询的菜单
	 * @param rootMenu 所有数据集合
	 * @return 
	 */
	private List<PermissionMenu> getChildsList(Integer id, List<PermissionMenu> rootMenu) {

		List<PermissionMenu> childslist = new ArrayList<>();
		for(PermissionMenu menu : rootMenu){
			if(id == menu.getFdPid()){
				childslist.add(menu);
			}
		}
		for(PermissionMenu menu: childslist){
			menu.setChildMenus(getChildsList(menu.getId(),rootMenu));
		}
		if(childslist.size()==0){
			return null;
		}
		return childslist;
	}

	@Override
	public List<String> listInIds(List<Integer> ids) {
		return permissionCustomerMapper.listInIds(ids);
	}

}
