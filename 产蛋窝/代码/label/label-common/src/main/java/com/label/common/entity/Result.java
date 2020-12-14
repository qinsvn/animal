package com.label.common.entity;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * Result : 响应的结果对象
 *
 * @author StarZou
 * @since 2014-09-27 16:28
 */
public class Result implements Serializable {
	private static final long serialVersionUID = 6288374846131788743L;

	/**
	 * 信息
	 */
	private String message;

	/**
	 * 状态码
	 */
	private String code;

	/**
	 * 成功true 失败false
	 */
	private boolean success = false;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		if (StringUtils.isEmpty(message)) {
			return ResponseCode.getMessage(code);
		}
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Result() {

	}
}
