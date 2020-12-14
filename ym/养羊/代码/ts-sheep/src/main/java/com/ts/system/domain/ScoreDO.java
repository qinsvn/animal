package com.ts.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 评分模板
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2019-09-20 10:05:04
 */
public class ScoreDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//企业id
	private Integer cid;
	//
	private String title;
	//第一选项内容
	private String radio1;
	//第二选项内容
	private String radio2;
	//第三选项内容
	private String radio3;
	//第四选项内容
	private String radio4;
	//第五选项内容
	private String radio5;
	//能否填写评价内容
	private Boolean isremark;
	//是否自动发送评分
	private Boolean autosend;

	/**
	 * 设置：企业id
	 */
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	/**
	 * 获取：企业id
	 */
	public Integer getCid() {
		return cid;
	}
	/**
	 * 设置：
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：第一选项内容
	 */
	public void setRadio1(String radio1) {
		this.radio1 = radio1;
	}
	/**
	 * 获取：第一选项内容
	 */
	public String getRadio1() {
		return radio1;
	}
	/**
	 * 设置：第二选项内容
	 */
	public void setRadio2(String radio2) {
		this.radio2 = radio2;
	}
	/**
	 * 获取：第二选项内容
	 */
	public String getRadio2() {
		return radio2;
	}
	/**
	 * 设置：第三选项内容
	 */
	public void setRadio3(String radio3) {
		this.radio3 = radio3;
	}
	/**
	 * 获取：第三选项内容
	 */
	public String getRadio3() {
		return radio3;
	}
	/**
	 * 设置：第四选项内容
	 */
	public void setRadio4(String radio4) {
		this.radio4 = radio4;
	}
	/**
	 * 获取：第四选项内容
	 */
	public String getRadio4() {
		return radio4;
	}
	/**
	 * 设置：第五选项内容
	 */
	public void setRadio5(String radio5) {
		this.radio5 = radio5;
	}
	/**
	 * 获取：第五选项内容
	 */
	public String getRadio5() {
		return radio5;
	}
	/**
	 * 设置：能否填写评价内容
	 */
	public void setIsremark(Boolean isremark) {
		this.isremark = isremark;
	}
	/**
	 * 获取：能否填写评价内容
	 */
	public Boolean getIsremark() {
		return isremark;
	}
	/**
	 * 设置：是否自动发送评分
	 */
	public void setAutosend(Boolean autosend) {
		this.autosend = autosend;
	}
	/**
	 * 获取：是否自动发送评分
	 */
	public Boolean getAutosend() {
		return autosend;
	}
}
