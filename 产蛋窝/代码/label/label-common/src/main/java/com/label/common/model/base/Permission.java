package com.label.common.model.base;

import java.util.Date;

public class Permission {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_permission.id
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_permission.fdPid
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	private Integer fdPid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_permission.fdName
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	private String fdName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_permission.fdType
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	private Byte fdType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_permission.fdValue
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	private String fdValue;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_permission.fdUri
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	private String fdUri;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_permission.fdStatus
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	private Byte fdStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_permission.fdCreateTime
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	private Date fdCreateTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_permission.fdOrders
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	private Integer fdOrders;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_permission.open
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	private String open;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_permission.id
	 * @return  the value of tb_permission.id
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_permission.id
	 * @param id  the value for tb_permission.id
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_permission.fdPid
	 * @return  the value of tb_permission.fdPid
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public Integer getFdPid() {
		return fdPid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_permission.fdPid
	 * @param fdPid  the value for tb_permission.fdPid
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public void setFdPid(Integer fdPid) {
		this.fdPid = fdPid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_permission.fdName
	 * @return  the value of tb_permission.fdName
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_permission.fdName
	 * @param fdName  the value for tb_permission.fdName
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName == null ? null : fdName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_permission.fdType
	 * @return  the value of tb_permission.fdType
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public Byte getFdType() {
		return fdType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_permission.fdType
	 * @param fdType  the value for tb_permission.fdType
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public void setFdType(Byte fdType) {
		this.fdType = fdType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_permission.fdValue
	 * @return  the value of tb_permission.fdValue
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public String getFdValue() {
		return fdValue;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_permission.fdValue
	 * @param fdValue  the value for tb_permission.fdValue
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public void setFdValue(String fdValue) {
		this.fdValue = fdValue == null ? null : fdValue.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_permission.fdUri
	 * @return  the value of tb_permission.fdUri
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public String getFdUri() {
		return fdUri;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_permission.fdUri
	 * @param fdUri  the value for tb_permission.fdUri
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public void setFdUri(String fdUri) {
		this.fdUri = fdUri == null ? null : fdUri.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_permission.fdStatus
	 * @return  the value of tb_permission.fdStatus
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public Byte getFdStatus() {
		return fdStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_permission.fdStatus
	 * @param fdStatus  the value for tb_permission.fdStatus
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public void setFdStatus(Byte fdStatus) {
		this.fdStatus = fdStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_permission.fdCreateTime
	 * @return  the value of tb_permission.fdCreateTime
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_permission.fdCreateTime
	 * @param fdCreateTime  the value for tb_permission.fdCreateTime
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_permission.fdOrders
	 * @return  the value of tb_permission.fdOrders
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public Integer getFdOrders() {
		return fdOrders;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_permission.fdOrders
	 * @param fdOrders  the value for tb_permission.fdOrders
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public void setFdOrders(Integer fdOrders) {
		this.fdOrders = fdOrders;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_permission.open
	 * @return  the value of tb_permission.open
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public String getOpen() {
		return open;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_permission.open
	 * @param open  the value for tb_permission.open
	 * @mbg.generated  Fri Mar 09 14:49:50 CST 2018
	 */
	public void setOpen(String open) {
		this.open = open == null ? null : open.trim();
	}
}