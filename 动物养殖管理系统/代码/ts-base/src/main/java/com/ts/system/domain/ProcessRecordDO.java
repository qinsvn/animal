package com.ts.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 工单流转记录
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-01-13 17:18:39
 */
public class ProcessRecordDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Integer prId;
	//处理意见
	private String prContent;
	//处理结果：申请类（1同意，2不同意，）； 报障类（3指派，0表示本人处理，4：委派）
	private Integer prResult;
	//工单id
	private Integer pjId;
	//部门
	private Integer prDptId;
	//处理时间
	private Date prHandleTime;
	//处理人
	private Integer prHandleUser;
	//处理时长
	private Long prHandleLongtime;
	//工单附件
	private Integer pfId;
	//暂存派转人id
	private String prBackup1;
	//暂存流程实例id
	private String prBackup2;
	//
	private String prBackup3;

	/**
	 * 设置：主键
	 */
	public void setPrId(Integer prId) {
		this.prId = prId;
	}
	/**
	 * 获取：主键
	 */
	public Integer getPrId() {
		return prId;
	}
	/**
	 * 设置：处理意见
	 */
	public void setPrContent(String prContent) {
		this.prContent = prContent;
	}
	/**
	 * 获取：处理意见
	 */
	public String getPrContent() {
		return prContent;
	}
	/**
	 * 设置：处理结果：申请类（1同意，2不同意，）； 报障类（3指派，0表示本人处理，4：委派）
	 */
	public void setPrResult(Integer prResult) {
		this.prResult = prResult;
	}
	/**
	 * 获取：处理结果：申请类（1同意，2不同意，）； 报障类（3指派，0表示本人处理，4：委派）
	 */
	public Integer getPrResult() {
		return prResult;
	}
	/**
	 * 设置：工单id
	 */
	public void setPjId(Integer pjId) {
		this.pjId = pjId;
	}
	/**
	 * 获取：工单id
	 */
	public Integer getPjId() {
		return pjId;
	}
	/**
	 * 设置：部门
	 */
	public void setPrDptId(Integer prDptId) {
		this.prDptId = prDptId;
	}
	/**
	 * 获取：部门
	 */
	public Integer getPrDptId() {
		return prDptId;
	}
	/**
	 * 设置：处理时间
	 */
	public void setPrHandleTime(Date prHandleTime) {
		this.prHandleTime = prHandleTime;
	}
	/**
	 * 获取：处理时间
	 */
	public Date getPrHandleTime() {
		return prHandleTime;
	}
	/**
	 * 设置：处理人
	 */
	public void setPrHandleUser(Integer prHandleUser) {
		this.prHandleUser = prHandleUser;
	}
	/**
	 * 获取：处理人
	 */
	public Integer getPrHandleUser() {
		return prHandleUser;
	}
	/**
	 * 设置：处理时长
	 */
	public void setPrHandleLongtime(Long prHandleLongtime) {
		this.prHandleLongtime = prHandleLongtime;
	}
	/**
	 * 获取：处理时长
	 */
	public Long getPrHandleLongtime() {
		return prHandleLongtime;
	}
	/**
	 * 设置：工单附件
	 */
	public void setPfId(Integer pfId) {
		this.pfId = pfId;
	}
	/**
	 * 获取：工单附件
	 */
	public Integer getPfId() {
		return pfId;
	}
	/**
	 * 设置：暂存派转人id
	 */
	public void setPrBackup1(String prBackup1) {
		this.prBackup1 = prBackup1;
	}
	/**
	 * 获取：暂存派转人id
	 */
	public String getPrBackup1() {
		return prBackup1;
	}
	/**
	 * 设置：暂存流程实例id
	 */
	public void setPrBackup2(String prBackup2) {
		this.prBackup2 = prBackup2;
	}
	/**
	 * 获取：暂存流程实例id
	 */
	public String getPrBackup2() {
		return prBackup2;
	}
	/**
	 * 设置：
	 */
	public void setPrBackup3(String prBackup3) {
		this.prBackup3 = prBackup3;
	}
	/**
	 * 获取：
	 */
	public String getPrBackup3() {
		return prBackup3;
	}
}
