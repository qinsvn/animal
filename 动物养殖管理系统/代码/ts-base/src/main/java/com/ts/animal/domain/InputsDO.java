package com.ts.animal.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 投入品表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-10 15:59:08
 */
public class InputsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//编号
	private String num;
	//批次
	private String batchNum;
	//种类
	private String typeName;
	//品种
	private String varietyName;
	//最初体重
	private String initWeight;
	//目前体重
	private String curWeight;
	//
	private Integer deptId;
	//场地
	private Integer spaceId;
	//供应商id
	private Integer supplierId;
	//其他信息
	private String otherInfo;
	//状态 0 饲养中，1已出库, 2次品
	private String status;
	//录入人员
	private Integer createUser;
	//入栏时间
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
	 * 设置：编号
	 */
	public void setNum(String num) {
		this.num = num;
	}
	/**
	 * 获取：编号
	 */
	public String getNum() {
		return num;
	}
	/**
	 * 设置：批次
	 */
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	/**
	 * 获取：批次
	 */
	public String getBatchNum() {
		return batchNum;
	}
	/**
	 * 设置：种类
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取：种类
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置：品种
	 */
	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	}
	/**
	 * 获取：品种
	 */
	public String getVarietyName() {
		return varietyName;
	}
	/**
	 * 设置：最初体重
	 */
	public void setInitWeight(String initWeight) {
		this.initWeight = initWeight;
	}
	/**
	 * 获取：最初体重
	 */
	public String getInitWeight() {
		return initWeight;
	}
	/**
	 * 设置：目前体重
	 */
	public void setCurWeight(String curWeight) {
		this.curWeight = curWeight;
	}
	/**
	 * 获取：目前体重
	 */
	public String getCurWeight() {
		return curWeight;
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
	 * 设置：场地
	 */
	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}
	/**
	 * 获取：场地
	 */
	public Integer getSpaceId() {
		return spaceId;
	}
	/**
	 * 设置：供应商id
	 */
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	/**
	 * 获取：供应商id
	 */
	public Integer getSupplierId() {
		return supplierId;
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
	 * 设置：状态 0 饲养中，1已出库, 2次品
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态 0 饲养中，1已出库, 2次品
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：录入人员
	 */
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：录入人员
	 */
	public Integer getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：入栏时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：入栏时间
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
