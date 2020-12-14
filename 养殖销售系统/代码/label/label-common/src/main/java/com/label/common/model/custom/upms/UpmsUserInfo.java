package com.label.common.model.custom.upms;

/**
 * 登录用户信息
 * @author jolley
 */
public class UpmsUserInfo { 

	private int id; // 用户id
	
	private int Type; // 用户类型，1联盟用户2机构用户
	
	private int roleType; // 角色类型，0普通管理员(默认)，1超级管理员，11公众号粉丝，12企业号成员
	
	private String account; // 账号
	
	private String nickname; // 昵称
	
	private int companyId; // 所属机构id
	
	//帐号管理后加入的字段
	private String fdName;//用户名
	
	private String phone;
	
	private String email;
	
	private String idCard;
	
	private String fdRoles;
	
	private int fdCreateUser;
	//帐号管理后加入的字段
	
	public String getFdRoles() {
		return fdRoles;
	}
	
	public void setFdRoles(String fdRoles) {
		this.fdRoles = fdRoles;
	}
	
	public String getIdCard() {
		return idCard;
	}
	
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getFdName() {
		return fdName;
	}
	
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	
	
	public int getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getFdCreateUser() {
		return fdCreateUser;
	}

	public void setFdCreateUser(int fdCreateUser) {
		this.fdCreateUser = fdCreateUser;
	}
	
}
