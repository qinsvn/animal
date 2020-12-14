package com.label.common.model.custom.upms;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 添加用户自定义属性
 * @author jolley
 */
public class UpmsUsernamePasswordToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;
	
	private int roleType = 0; // 角色类型，0管理员(默认)，11公众号粉丝，12企业号成员
	
	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public UpmsUsernamePasswordToken(final String username, final String password) {
		super(username, password, false, null);
	}

	public UpmsUsernamePasswordToken(final String username, final String password, int roleType) {
		super(username, password, false, null);
		
		this.roleType = roleType;
	}
	
	public UpmsUsernamePasswordToken(final String username, final String password, final boolean rememberMe, final String host, int roleType) {
		super(username, password, rememberMe, host);
		
		this.roleType = roleType;
	}
	
}
