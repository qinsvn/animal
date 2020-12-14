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

public class WebsockeBean extends Result {

	private static final long serialVersionUID = 7880907731807860636L;

	public static String WARN_TIP="-1";//心跳
	public static String CMD_TYPE="0";
	public static String WARN_TYPE="1";
	
	public String type; //0 cmd 1告警
	
	public WebsockeBean() {
		super();
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	public String toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", this.getMessage());
		jsonObject.put("code", this.getCode());
		jsonObject.put("success", this.isSuccess());
		jsonObject.put("type", this.getType());
		String str = jsonObject.toString();
		str = str.replaceAll(":null", ":\"\"");
		return str;
	}
}