package com.label.common.entity;

/**
 * JSONResult : Response JSONResult for RESTful,封装返回JSON格式的数据
 *
 * @author StarZou
 * @since 2014年5月26日 上午10:51:46
 */

public class JSONResults<T> extends Result {

	private static final long serialVersionUID = 7880907731807860636L;

	public JSONResults() {
		super();
	}
	
	/**
	 * 数据
	 */
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 返回数据和消息
	 *
	 * @param data
	 * @param message
	 */
	public JSONResults(T data, String message) {
		this.data = data;
		super.setMessage(message);
	}

	/**
	 * 成功返回数据
	 *
	 * @param data
	 */
	public JSONResults(T data) {
		this.data = data;
	}
}