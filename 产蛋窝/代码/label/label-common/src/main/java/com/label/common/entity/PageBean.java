package com.label.common.entity;

/**
 * 分页及查询条件bean
 * 
 * @author peach 2016-01-31
 */
public class PageBean<T> {

	private int page;// 第几页
	private int rows;// 获取几条
	private String sStartTime = null;// 开始时间
	private String sEndTime = null;// 结束时间
	private String sFuzzy = "";// 模糊搜索

	private T condition;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getsStartTime() {
		return sStartTime;
	}

	public void setsStartTime(String sStartTime) {
		this.sStartTime = sStartTime;
	}

	public String getsEndTime() {
		return sEndTime;
	}

	public void setsEndTime(String sEndTime) {
		this.sEndTime = sEndTime;
	}

	public String getsFuzzy() {
		return sFuzzy;
	}

	public void setsFuzzy(String sFuzzy) {
		this.sFuzzy = sFuzzy;
	}

	public T getCondition() {
		return condition;
	}

	public void setCondition(T condition) {
		this.condition = condition;
	}

}
