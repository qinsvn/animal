package com.ts.animal.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 供应商表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:05:30
 */
public class SupplierDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//供应商名称
	private String name;
	//供应商地址
	private String address;
	//供应商负责人
	private String headMan;
	//联系电话
	private String phone;
	//邮箱
	private String email;
	//供应商领域
	private String field;
	//
	private Integer deptId;
	//其他信息
	private String otherInfo;
	//创建人
	private Integer createUser;
	//创建时间
	private Date createTime;
	//修改人
	private Integer updateUser;
	//修改时间
	private Date updateTime;
	//
	private String remark1;
	//
	private String remark2;
	//
	private String remark3;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：供应商名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：供应商名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：供应商地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：供应商地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：供应商负责人
	 */
	public void setHeadMan(String headMan) {
		this.headMan = headMan;
	}
	/**
	 * 获取：供应商负责人
	 */
	public String getHeadMan() {
		return headMan;
	}
	/**
	 * 设置：联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：供应商领域
	 */
	public void setField(String field) {
		this.field = field;
	}
	/**
	 * 获取：供应商领域
	 */
	public String getField() {
		return field;
	}
	/**
	 * 设置：
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：
	 */
	public Integer getDeptId() {
		return deptId;
	}
	/**
	 * 设置：其他信息
	 */
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	/**
	 * 获取：其他信息
	 */
	public String getOtherInfo() {
		return otherInfo;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：创建人
	 */
	public Integer getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：修改人
	 */
	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * 获取：修改人
	 */
	public Integer getUpdateUser() {
		return updateUser;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	/**
	 * 获取：
	 */
	public String getRemark1() {
		return remark1;
	}
	/**
	 * 设置：
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	/**
	 * 获取：
	 */
	public String getRemark2() {
		return remark2;
	}
	/**
	 * 设置：
	 */
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	/**
	 * 获取：
	 */
	public String getRemark3() {
		return remark3;
	}
}
