package com.ts.animal.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 检疫记录表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-07 17:30:25
 */
public class ImmuneRecordDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//编号 rfid
	private String num;
	//批次
	private String batchNum;
	//种类
	private String typeName;
	//品种
	private String varietyName;
	//投入品编号
	private Integer inputsId;
	//日龄
	private String immuneAge;
	//检疫药物
	private String immuneDrug;
	//检疫结果
	private String immuneResult;
	//所属部门
	private Integer deptId;
	//其他信息
	private String otherInfo;
	//检疫人员
	private Integer createUser;
	//检疫时间
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
	 * 设置：编号 rfid
	 */
	public void setNum(String num) {
		this.num = num;
	}
	/**
	 * 获取：编号 rfid
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
	 * 设置：投入品编号
	 */
	public void setInputsId(Integer inputsId) {
		this.inputsId = inputsId;
	}
	/**
	 * 获取：投入品编号
	 */
	public Integer getInputsId() {
		return inputsId;
	}
	/**
	 * 设置：日龄
	 */
	public void setImmuneAge(String immuneAge) {
		this.immuneAge = immuneAge;
	}
	/**
	 * 获取：日龄
	 */
	public String getImmuneAge() {
		return immuneAge;
	}
	/**
	 * 设置：检疫药物
	 */
	public void setImmuneDrug(String immuneDrug) {
		this.immuneDrug = immuneDrug;
	}
	/**
	 * 获取：检疫药物
	 */
	public String getImmuneDrug() {
		return immuneDrug;
	}
	/**
	 * 设置：检疫结果
	 */
	public void setImmuneResult(String immuneResult) {
		this.immuneResult = immuneResult;
	}
	/**
	 * 获取：检疫结果
	 */
	public String getImmuneResult() {
		return immuneResult;
	}
	/**
	 * 设置：所属部门
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：所属部门
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
	 * 设置：检疫人员
	 */
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：检疫人员
	 */
	public Integer getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：检疫时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：检疫时间
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
