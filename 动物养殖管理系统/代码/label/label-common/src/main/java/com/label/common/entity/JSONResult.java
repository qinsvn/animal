package com.label.common.entity;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * JSONResult : Response JSONResult for RESTful,封装返回JSON格式的数据
 *
 * @author StarZou
 * @since 2014年5月26日 上午10:51:46
 */

public class JSONResult extends Result {

	private static final long serialVersionUID = 7880907731807860636L;

	private Map<String, Object> mapData = new HashMap<String, Object>();

	public JSONResult() {
		super();
	}

	/**
	 * 数据
	 */
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void addData(String key, Object value) {
		mapData.put(key, value);
		setData(mapData);
	}

	/**
	 * 返回数据和消息
	 *
	 * @param data
	 * @param message
	 */
	public JSONResult(Object data, String message) {
		this.data = data;
		super.setMessage(message);
	}

	/**
	 * 成功返回数据
	 *
	 * @param data
	 */
	public JSONResult(Object data) {
		this.data = data;
	}

	public String toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", this.getMessage());
		jsonObject.put("code", this.getCode());
		jsonObject.put("success", this.isSuccess());
		jsonObject.put("data", this.getData());
		String str = jsonObject.toString();
		str = str.replaceAll(":null", ":\"\"");
		return str;
	}
}