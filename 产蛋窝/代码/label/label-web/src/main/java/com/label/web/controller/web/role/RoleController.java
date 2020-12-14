package com.label.web.controller.web.role;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.label.common.base.BaseController;
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.model.base.AdminUser;
import com.label.common.model.base.Role;
import com.label.common.model.base.RoleExample;
import com.label.common.model.base.RoleUserRef;
import com.label.common.model.base.RoleUserRefExample;
import com.label.common.util.UtilDigit;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilString;
import com.label.service.web.AdminUserService;
import com.label.service.web.RoleService;

import io.swagger.annotations.ApiOperation;

/**
 * 角色管理
 * @author xiaoc
 */
@Controller
@RequestMapping("web/role")
public class RoleController extends BaseController{

	private static Logger _log = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AdminUserService adminUserService;
	
    @ApiOperation(value = "列表页面")
    @RequiresPermissions("label:role:read")
    @RequestMapping(value = "/p_list", method = RequestMethod.GET)
    public String p_list(HttpServletRequest request) {
        return "/web/role/list.jsp";
    }
	
    @ApiOperation(value = "新增页面")
    @RequiresPermissions("label:role:read")
    @RequestMapping(value = "/p_info", method = RequestMethod.GET)
    public String p_info(HttpServletRequest request) {
        return "/web/role/info.jsp";
    }
	
    @ApiOperation(value = "编辑页面")
    @RequiresPermissions("label:role:read")
    @RequestMapping(value = "/p_alter", method = RequestMethod.GET)
    public String p_alter(HttpServletRequest request) {
        return "/web/role/alter.jsp";
    }

	@ApiOperation(value = "查询角色信息")
	@RequiresPermissions("label:role:read")
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public UpmsResult info(int id) {
		if(id <= 0) {
			_log.info("xiaoc>> 请求参数错，id: {}", id);
			return new UpmsResult(UpmsResultConstant.FAILED, "请求参数错");
		}
		
		Role role = roleService.info(id);
		if(role.getFdCompanyId() == null) {
			role.setFdCompanyId(0);
		}
		/*if(super.getUpmsUserInfo().getCompanyId() != role.getFdCompanyId()) {
			_log.info("xiaoc>> 角色所属商家不存在，当前所属商家id: {}，role: {}", super.getUpmsUserInfo().getCompanyId(), UtilJson.Obj2Str(role));
			return new UpmsResult(UpmsResultConstant.FAILED, "数据不存在");
		}*/
		
		return new UpmsResult(UpmsResultConstant.SUCCESS, role);
	}

	@ApiOperation(value = "角色列表信息")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public UpmsResult list(
    		@RequestParam(required = false, defaultValue = "1", value = "pagenum") int pagenum,
            @RequestParam(required = false, defaultValue = "10", value = "pagesize") int pagesize,
            @RequestParam(required = false, defaultValue = "", value = "keyword") String keyword) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		RoleExample example = new RoleExample();
		example.setOrderByClause("fdTime desc");
		
		//是否商家用户
		if (super.getUpmsUserInfo().getCompanyId() > 0) {
			example.createCriteria().andFdIsCompanyRoleEqualTo((byte) 1);
		}
		
		if(!StringUtils.isEmpty(keyword)) {
			example.createCriteria().andFdNameLike("%" + keyword + "%");
		}
		long count = roleService.count(example);
		result.put("total", count);
		
		if(count > (pagenum - 1) * pagesize) {
			List<Role> data = roleService.list(pagenum, pagesize, example);
			result.put("rows", data);
			result.put("size", data.size());
		} else {
			result.put("rows", new LinkedList<Role>());
			result.put("size", 0);
		}
		
		return new UpmsResult(UpmsResultConstant.SUCCESS, result);
	}
	@ApiOperation(value = "角色列表信息")
	
	@RequestMapping(value = "/companyRolelist", method = RequestMethod.POST)
	@ResponseBody
	public UpmsResult companyRolelist(
			@RequestParam(required = false, defaultValue = "1", value = "pagenum") int pagenum,
			@RequestParam(required = false, defaultValue = "10", value = "pagesize") int pagesize,
			@RequestParam(required = false, defaultValue = "", value = "keyword") String keyword) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		RoleExample example = new RoleExample();
		example.setOrderByClause("fdTime desc");
		
		//是否商家用户
		if (super.getUpmsUserInfo().getCompanyId() > 0) {
			example.createCriteria().andFdIsCompanyRoleEqualTo((byte) 1);
		}
		
		if(!StringUtils.isEmpty(keyword)) {
			example.getOredCriteria().get(0).andFdNameLike("%" + keyword + "%");
		}
		long count = roleService.count(example);
		result.put("total", count);
		
		if(count > (pagenum - 1) * pagesize) {
			List<Role> data = roleService.list(pagenum, pagesize, example);
			result.put("rows", data);
			result.put("size", data.size());
		} else {
			result.put("rows", new LinkedList<Role>());
			result.put("size", 0);
		}
		
		return new UpmsResult(UpmsResultConstant.SUCCESS, result);
	}
		

	@ApiOperation(value = "新增角色")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@RequiresPermissions("label:role:create")
	@ResponseBody
	public UpmsResult create(Role role) {
		// 检查参数
		if(StringUtils.isEmpty(role.getFdName()) || (!StringUtils.isEmpty(role.getFdPermissions()) && !role.getFdPermissions().matches("^(\\d{1,11})(,\\d{1,11})*$"))) {
			_log.info("xiaoc>>  缺少请求参数, role: {}", UtilJson.Obj2Str(role));
			return new UpmsResult(UpmsResultConstant.FAILED, "缺少请求参数");
		}
		
		role.setFdCompanyId(super.getUpmsUserInfo().getCompanyId());
		role.setFdTime(new Date());
		role.setFdCanDel((byte) 1); // 能删除
		role.setId(null);
		
		
		int userid = super.getUpmsUserInfo().getId();
		int companyId = super.getUpmsUserInfo().getCompanyId();
		
		// 检查当前管理员所属角色
		if(!StringUtils.isEmpty(role.getFdPermissions())) {
			RoleUserRefExample exampleRoleUserRef = new RoleUserRefExample();
			exampleRoleUserRef.createCriteria().andFdUserIdEqualTo(userid).andFdCompanyIdEqualTo(companyId);
			List<RoleUserRef> roleUserRefs = roleService.selectRoleUserRef(exampleRoleUserRef);
			if(roleUserRefs == null || roleUserRefs.size() <= 0) {
				_log.info("xiaoc>> 新增权限不足，未找到所属角色id，userid: {}, companyId: {}", userid, companyId);
				return new UpmsResult(UpmsResultConstant.FAILED, "权限不足，未找到您的权限信息");
			}
			List<Integer> listRoleid = new LinkedList<Integer>();
			for(RoleUserRef item : roleUserRefs) {
				listRoleid.add(item.getFdRoleId());
			}
			
			// 查找所属角色信息
			RoleExample exampleRole = new RoleExample();
			exampleRole.createCriteria().andFdCompanyIdEqualTo(companyId).andIdIn(listRoleid);
			List<Role> roles = roleService.list(1, 9999, exampleRole);
			if(roles == null || roles.size() <= 0) {
				_log.info("xiaoc>> 新增权限不足，未找到所属角色信息，listRoleid: {}, companyId: {}", UtilJson.Obj2Str(listRoleid), companyId);
				return new UpmsResult(UpmsResultConstant.FAILED, "权限不足，未找到您的权限信息");
			}
			
			// 查找当前管理员权限信息
			String permissions = "";
			for(Role item : roles) {
				permissions += item.getFdPermissions() + ",";
			}
			String []permissionArr = permissions.split(",");
			
			for(String per : role.getFdPermissions().split(",")) {
				if(!UtilString.isContain(permissionArr, per, false)) {
					_log.info("xiaoc>> 新增权限不足，存在无权限分配的权限信息，分配的权限 per: {}, 具备的权限 permissions: {}", per, permissions);
					return new UpmsResult(UpmsResultConstant.FAILED, "权限不足，存在无权限分配的权限信息");
				}
			}
		}
		
		// 创建角色
		roleService.create(role);
		
		return new UpmsResult(UpmsResultConstant.SUCCESS, null);
	}

	@ApiOperation(value = "删除角色信息")
	@RequiresPermissions("label:role:delete")
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public UpmsResult delete(String ids) {
		if(StringUtils.isEmpty(ids) || !ids.matches("^(\\d{1,11})(,\\d{1,11})*$")) {
			_log.info("xiaoc>> 请求参数错，ids: {}", ids);
			return new UpmsResult(UpmsResultConstant.FAILED, "请求参数错");
		}
		
		List<Integer> idList = new LinkedList<Integer>();
		for(String idStr : ids.split(",")) {
			if(UtilDigit.str2Int(idStr, 0)==1){
				return new UpmsResult(UpmsResultConstant.FAILED, "系统管理员不允许删除！");
			}
			idList.add(UtilDigit.str2Int(idStr, 0));
		}
		
		RoleExample exampleRole = new RoleExample();
		exampleRole.createCriteria().andFdCompanyIdEqualTo(super.getUpmsUserInfo().getCompanyId()).andFdCanDelEqualTo((byte) 1).andIdIn(idList);
		int flag = roleService.delete(exampleRole);
		if(flag>0){
			return new UpmsResult(UpmsResultConstant.SUCCESS, "删除成功！");
		}else{
			return new UpmsResult(UpmsResultConstant.FAILED, "删除失败！");
		}
		
	}

	@ApiOperation("修改角色信息")
	@RequiresPermissions("label:role:update")
	@ResponseBody
	@RequestMapping(value = "/alter", method = RequestMethod.POST)
	public UpmsResult alter(Role role) {
		if(role.getId() == null || role.getId() <= 0) {
			_log.info("xiaoc>> 请求参数错，id: {}", role.getId());
			return new UpmsResult(UpmsResultConstant.FAILED, "请求参数错");
		}
		
		int companyId = super.getUpmsUserInfo().getCompanyId();
		// 获取欲修改的角色信息
		RoleExample exampleRoleSel = new RoleExample();
		exampleRoleSel.createCriteria().andFdCompanyIdEqualTo(companyId).andIdEqualTo(role.getId());
		List<Role> roleList = roleService.list(1, 1, exampleRoleSel);
		if(roleList == null || roleList.size() <= 0) {
			_log.info("xiaoc>> 角色信息未找到， companyId: {}, id: {}", companyId, role.getId());
			return new UpmsResult(UpmsResultConstant.FAILED, "角色信息未找到");
		}
		
		Role record = new Role();
		record.setFdName(role.getFdName());
		record.setFdDesp(role.getFdDesp());
		record.setFdIsCompanyRole(role.getFdIsCompanyRole());
		if(roleList.get(0).getFdCanDel() != null && roleList.get(0).getFdCanDel() == 1) { // 1能删除，也可修改权限
			if(!StringUtils.isEmpty(role.getFdPermissions()) && !role.getFdPermissions().equals(roleList.get(0).getFdPermissions())) {
				String []permissionArrOld = roleList.get(0).getFdPermissions().split(",");
				
				// 检查当前管理员所属角色
				int userid = super.getUpmsUserInfo().getId();
				RoleUserRefExample exampleRoleUserRef = new RoleUserRefExample();
				exampleRoleUserRef.createCriteria().andFdUserIdEqualTo(userid).andFdCompanyIdEqualTo(companyId);
				List<RoleUserRef> roleUserRefs = roleService.selectRoleUserRef(exampleRoleUserRef);
				if(roleUserRefs == null || roleUserRefs.size() <= 0) {
					_log.info("xiaoc>> 修改权限不足，未找到所属角色id，userid: {}, companyId: {}", userid, companyId);
					return new UpmsResult(UpmsResultConstant.FAILED, "权限不足，未找到您的权限信息");
				}
				List<Integer> listRoleid = new LinkedList<Integer>();
				for(RoleUserRef item : roleUserRefs) {
					listRoleid.add(item.getFdRoleId());
				}
				
				// 查找所属角色信息
				RoleExample exampleRole = new RoleExample();
				exampleRole.createCriteria().andFdCompanyIdEqualTo(companyId).andIdIn(listRoleid);
				List<Role> roles = roleService.list(1, 9999, exampleRole);
				if(roles == null || roles.size() <= 0) {
					_log.info("xiaoc>> 修改权限不足，未找到所属角色信息，listRoleid: {}, companyId: {}", UtilJson.Obj2Str(listRoleid), companyId);
					return new UpmsResult(UpmsResultConstant.FAILED, "权限不足，未找到您的权限信息");
				}
				
				// 查找当前管理员权限信息
				String permissions = "";
				for(Role item : roles) {
					permissions += item.getFdPermissions() + ",";
				}
				String []permissionArr = permissions.split(",");
				
				for(String per : role.getFdPermissions().split(",")) {
					// 修改后的权限，即不是自己具备的也不是原有的
					if(!UtilString.isContain(permissionArr, per, false) && !UtilString.isContain(permissionArrOld, per, false)) {
						_log.info("xiaoc>> 修改权限不足，存在无权限分配的权限信息，分配的权限 per: {}, 具备的权限 permissions: {}", per, permissions);
						return new UpmsResult(UpmsResultConstant.FAILED, "权限不足，存在无权限分配的权限信息");
					}
				}
				
				record.setFdPermissions(role.getFdPermissions());
			}
		} else {
			_log.info("xiaoc>> 当前角色不可修改权限信息, roleid", role.getId());
		}
		
		RoleExample exampleRoleAlter = new RoleExample();
		exampleRoleAlter.createCriteria().andFdCompanyIdEqualTo(companyId).andIdEqualTo(role.getId());
		if (StringUtils.isEmpty(role.getFdPermissions())) {
			record.setFdPermissions("");
		}
		roleService.alter(record, exampleRoleAlter);
		
		return new UpmsResult(UpmsResultConstant.SUCCESS, null);
	}

	@ApiOperation("修改管理员所属角色信息")
	@RequiresPermissions("label:role:update")
	@ResponseBody
	@RequestMapping(value = "/alterroleuser", method = RequestMethod.POST)
	public UpmsResult alterRoleUser(
    		@RequestParam(required = false, defaultValue = "0", value = "userid") int userid,
            @RequestParam(required = false, defaultValue = "", value = "roleids") String roleids) {
		if(userid <= 0 || (!StringUtils.isEmpty(roleids) && !roleids.matches("^(\\d{1,11})(,\\d{1,11})*$"))) {
			_log.info("xiaoc>> 请求参数有错， userid: {}, roleids: {}", userid, roleids);
			return new UpmsResult(UpmsResultConstant.FAILED, "请求参数有错");
		}
		
		int companyId = super.getUpmsUserInfo().getCompanyId();
		AdminUser adminUser = adminUserService.selectById(userid);
		if(adminUser == null || adminUser.getId() <= 0 || adminUser.getFdConpanyId() != companyId) {
			_log.info("xiaoc>> 用户不存在， userid: {}, companyId: {}, adminUser: {}", userid, companyId, UtilJson.Obj2Str(adminUser));
			return new UpmsResult(UpmsResultConstant.FAILED, "用户不存在");
		}
		
		// 系统默认管理员不可修改所属角色信息
		if(super.getUpmsUserInfo().getFdCreateUser() <= 0) {
			_log.info("xiaoc>> 请该用户不可修改， userid: {}, fdCreateUser: {}", userid, super.getUpmsUserInfo().getFdCreateUser());
			return new UpmsResult(UpmsResultConstant.FAILED, "该用户不可修改");
		}
		
		// 删除角色信息
		RoleUserRefExample exampleRoleUserRef = new RoleUserRefExample();
		exampleRoleUserRef.createCriteria().andFdUserIdEqualTo(userid).andFdCompanyIdEqualTo(companyId);
		roleService.deleteRoleUserRef(exampleRoleUserRef);
		
		// 重新添加所属角色信息
		if(!StringUtils.isEmpty(roleids)) {
			RoleUserRef roleUserRef = new RoleUserRef();
			roleUserRef.setFdCompanyId(companyId);
			roleUserRef.setFdUserId(userid);
			for(String roleidStr : roleids.split(",")) {
				int roleid = UtilDigit.str2Int(roleidStr, 0);
				if(roleid > 0) {
					roleUserRef.setFdRoleId(roleid);
					roleService.createRoleUserRef(roleUserRef);
				} else {
					_log.info("xiaoc>> 添加所属角色信息，忽略roleid: {}", roleid);
				}
			}
		}
		
		return new UpmsResult(UpmsResultConstant.SUCCESS, null);
	}
	
}
