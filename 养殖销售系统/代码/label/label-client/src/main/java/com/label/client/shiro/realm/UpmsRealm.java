package com.label.client.shiro.realm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.label.common.constant.UpmsSessionKey;
import com.label.common.model.base.AdminUser;
import com.label.common.model.base.Role;
import com.label.common.model.base.RoleExample;
import com.label.common.model.base.RoleUserRef;
import com.label.common.model.base.RoleUserRefExample;
import com.label.common.model.custom.upms.UpmsUserInfo;
import com.label.common.model.custom.upms.UpmsUsernamePasswordToken;
import com.label.common.util.UtilDigit;
import com.label.common.util.UtilJson;
import com.label.service.web.AdminUserService;
import com.label.service.web.PermissionService;
import com.label.service.web.RoleService;

/**
 * 用户认证和授权
 * @author jolley
 */
public class UpmsRealm extends AuthorizingRealm {

//	private static Logger _log = LoggerFactory.getLogger(UpmsRealm.class);
	
	@Autowired
    private AdminUserService adminUserService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
//	@Autowired
//    private MpUserService mpUserService;

    /**
     * 授权：验证权限时调用
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    	
    	Session session = SecurityUtils.getSubject().getSession();
    	UpmsUserInfo upmsUserInfo = UtilJson.str2Bean(String.valueOf(session.getAttribute(UpmsSessionKey.UPMS_USER_INFO)), UpmsUserInfo.class);
    	
    	if(upmsUserInfo.getRoleType() == 0) { // 普通管理员
    		int companyId = upmsUserInfo.getCompanyId();
    		
    		RoleUserRefExample example = new RoleUserRefExample();
    		example.createCriteria().andFdUserIdEqualTo(upmsUserInfo.getId());
    		List<RoleUserRef> roleUserRefList = roleService.selectRoleUserRef(example);
    		if(roleUserRefList != null && roleUserRefList.size() > 0) {
    			// 获取管理员所属角色列表
    			List<Integer> roleidList = new LinkedList<Integer>();
    			for(RoleUserRef item : roleUserRefList) {
    				roleidList.add(item.getFdRoleId());
    			}
    			RoleExample exampleRole = new RoleExample();
    			exampleRole.createCriteria().andIdIn(roleidList);
    			List<Role> roleList = roleService.list(0, 9999, exampleRole);
    			
    			if(roleList != null && roleList.size() > 0) {
    				// 获取管理员具有的权限列表
    				List<Integer> permissionidList = new LinkedList<Integer>();
        			for(Role item : roleList) {
        				if(!StringUtils.isEmpty(item.getFdPermissions())) {
        					for(String permissionidStr : item.getFdPermissions().split(",")) {
        						int permissionid = UtilDigit.str2Int(permissionidStr, 0);
        						if(permissionid > 0) {
        							permissionidList.add(permissionid);
        						}
        					}
        				}
        			}
        			//通过权限id获取权限
        			List<String> permissionList = permissionService.listInIds(permissionidList);
            		Set<String> permissions = new HashSet<>();
            		for(String permission:permissionList){
            			if(!StringUtils.isEmpty(permission)){
            				permissions.add(permission);
            			}
            		}
                    simpleAuthorizationInfo.setStringPermissions(permissions);
    			}
    		}
    	} else if(upmsUserInfo.getRoleType() == 1) { // 超级管理员
    		List<String> permissionList = permissionService.listInIds(null);//传入null时查询全部
    		Set<String> permissions = new HashSet<>();
    		for(String permission:permissionList){
    			if(!StringUtils.isEmpty(permission)){
    				permissions.add(permission);
    			}
    		}
    		simpleAuthorizationInfo.setStringPermissions(permissions);
    	} else if(upmsUserInfo.getRoleType() == 11) { // 公众号粉丝
//    		_log.info("jolley>> 设置公众号粉丝权限");
    	} else if(upmsUserInfo.getRoleType() == 12) { // 企业号成员
//    		_log.info("jolley>> 设置企业号成员权限");
    	}
//    	else if(upmsUserInfo.getRoleType()==10){//本段测试用
//    		List<String> permissionList = permissionService.listInIds(null);//传入null时查询全部
//    		Set<String> permissions = new HashSet<>();
//    		for(String permission:permissionList){
//    			if(!StringUtils.isEmpty(permission)){
//    				permissions.add(permission);
//    			}
//    		}
//            simpleAuthorizationInfo.setStringPermissions(permissions);
//    	}
        
        return simpleAuthorizationInfo;
    }

    /**
     * 认证：登录时调用
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    	UpmsUsernamePasswordToken upmsUsernamePasswordToken = (UpmsUsernamePasswordToken) authenticationToken;
    	int roleType = upmsUsernamePasswordToken.getRoleType();
    	String username = upmsUsernamePasswordToken.getUsername();
    	String password = new String(upmsUsernamePasswordToken.getPassword());
    	
    	UpmsUserInfo upmsUserInfo = new UpmsUserInfo();
    	upmsUserInfo.setRoleType(roleType);
    	
    	if(roleType == 11) { // 微信公众号登录
    		// 检查用户是否被锁定
    		
    	} else if(roleType == 12) { // 微信企业号登录
    		// 检查用户是否被锁定
    		
    	} else { // 默认管理员登录
            // 查询管理员信息
            AdminUser adminUser = adminUserService.selectByAccount(username);
	        if (null == adminUser) {
	            throw new UnknownAccountException();
	        }
        	if(adminUser.getFdLocked() == 1) {
        		throw new LockedAccountException();
        	}
        	if(!adminUser.getFdPassword().equals(password)) {
        		throw new IncorrectCredentialsException();
        	}
        	if(adminUser.getFdConpanyId()!=null){
        		upmsUserInfo.setCompanyId(adminUser.getFdConpanyId());        		
        	}
        	upmsUserInfo.setRoleType(0);
        	upmsUserInfo.setNickname(adminUser.getFdAccount());
        	
        	upmsUserInfo.setId(adminUser.getId());
        	upmsUserInfo.setType(adminUser.getFdType());
        	//帐号管理后加入的数据
        	upmsUserInfo.setFdName(adminUser.getFdName());
        	upmsUserInfo.setFdRoles(adminUser.getFdRoles());
        	upmsUserInfo.setPhone(adminUser.getFdPhone());
        	upmsUserInfo.setEmail(adminUser.getFdEmail());
        	upmsUserInfo.setIdCard(adminUser.getFdIdcard());
        	upmsUserInfo.setFdCreateUser(adminUser.getFdCreateUser());
    	}
    	upmsUserInfo.setAccount(username);
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(UpmsSessionKey.UPMS_USER_INFO, UtilJson.Obj2Str(upmsUserInfo));

        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
