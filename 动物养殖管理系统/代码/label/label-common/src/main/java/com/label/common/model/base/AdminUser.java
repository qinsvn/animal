package com.label.common.model.base;

import java.util.Date;

public class AdminUser {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_admin_user.id
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_admin_user.fdAccount
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	private String fdAccount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_admin_user.fdPassword
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	private String fdPassword;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_admin_user.fdName
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	private String fdName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_admin_user.fdEmail
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	private String fdEmail;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_admin_user.fdPhone
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	private String fdPhone;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_admin_user.fdIdcard
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	private String fdIdcard;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_admin_user.fdLocked
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	private Byte fdLocked;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_admin_user.fdConpanyId
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	private Integer fdConpanyId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_admin_user.fdType
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	private Byte fdType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_admin_user.fdCreateUser
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	private Integer fdCreateUser;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_admin_user.fdCreateTime
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	private Date fdCreateTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_admin_user.fdRoles
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	private String fdRoles;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_admin_user.id
	 * @return  the value of tb_admin_user.id
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_admin_user.id
	 * @param id  the value for tb_admin_user.id
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_admin_user.fdAccount
	 * @return  the value of tb_admin_user.fdAccount
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public String getFdAccount() {
		return fdAccount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_admin_user.fdAccount
	 * @param fdAccount  the value for tb_admin_user.fdAccount
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public void setFdAccount(String fdAccount) {
		this.fdAccount = fdAccount == null ? null : fdAccount.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_admin_user.fdPassword
	 * @return  the value of tb_admin_user.fdPassword
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public String getFdPassword() {
		return fdPassword;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_admin_user.fdPassword
	 * @param fdPassword  the value for tb_admin_user.fdPassword
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public void setFdPassword(String fdPassword) {
		this.fdPassword = fdPassword == null ? null : fdPassword.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_admin_user.fdName
	 * @return  the value of tb_admin_user.fdName
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_admin_user.fdName
	 * @param fdName  the value for tb_admin_user.fdName
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName == null ? null : fdName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_admin_user.fdEmail
	 * @return  the value of tb_admin_user.fdEmail
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public String getFdEmail() {
		return fdEmail;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_admin_user.fdEmail
	 * @param fdEmail  the value for tb_admin_user.fdEmail
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public void setFdEmail(String fdEmail) {
		this.fdEmail = fdEmail == null ? null : fdEmail.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_admin_user.fdPhone
	 * @return  the value of tb_admin_user.fdPhone
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public String getFdPhone() {
		return fdPhone;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_admin_user.fdPhone
	 * @param fdPhone  the value for tb_admin_user.fdPhone
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public void setFdPhone(String fdPhone) {
		this.fdPhone = fdPhone == null ? null : fdPhone.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_admin_user.fdIdcard
	 * @return  the value of tb_admin_user.fdIdcard
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public String getFdIdcard() {
		return fdIdcard;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_admin_user.fdIdcard
	 * @param fdIdcard  the value for tb_admin_user.fdIdcard
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public void setFdIdcard(String fdIdcard) {
		this.fdIdcard = fdIdcard == null ? null : fdIdcard.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_admin_user.fdLocked
	 * @return  the value of tb_admin_user.fdLocked
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public Byte getFdLocked() {
		return fdLocked;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_admin_user.fdLocked
	 * @param fdLocked  the value for tb_admin_user.fdLocked
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public void setFdLocked(Byte fdLocked) {
		this.fdLocked = fdLocked;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_admin_user.fdConpanyId
	 * @return  the value of tb_admin_user.fdConpanyId
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public Integer getFdConpanyId() {
		return fdConpanyId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_admin_user.fdConpanyId
	 * @param fdConpanyId  the value for tb_admin_user.fdConpanyId
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public void setFdConpanyId(Integer fdConpanyId) {
		this.fdConpanyId = fdConpanyId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_admin_user.fdType
	 * @return  the value of tb_admin_user.fdType
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public Byte getFdType() {
		return fdType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_admin_user.fdType
	 * @param fdType  the value for tb_admin_user.fdType
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public void setFdType(Byte fdType) {
		this.fdType = fdType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_admin_user.fdCreateUser
	 * @return  the value of tb_admin_user.fdCreateUser
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public Integer getFdCreateUser() {
		return fdCreateUser;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_admin_user.fdCreateUser
	 * @param fdCreateUser  the value for tb_admin_user.fdCreateUser
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public void setFdCreateUser(Integer fdCreateUser) {
		this.fdCreateUser = fdCreateUser;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_admin_user.fdCreateTime
	 * @return  the value of tb_admin_user.fdCreateTime
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_admin_user.fdCreateTime
	 * @param fdCreateTime  the value for tb_admin_user.fdCreateTime
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_admin_user.fdRoles
	 * @return  the value of tb_admin_user.fdRoles
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public String getFdRoles() {
		return fdRoles;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_admin_user.fdRoles
	 * @param fdRoles  the value for tb_admin_user.fdRoles
	 * @mbg.generated  Wed Mar 07 09:29:43 CST 2018
	 */
	public void setFdRoles(String fdRoles) {
		this.fdRoles = fdRoles == null ? null : fdRoles.trim();
	}
}