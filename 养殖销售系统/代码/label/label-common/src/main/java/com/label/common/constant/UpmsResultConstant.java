package com.label.common.constant;

/**
 * upms系统接口结果常量枚举类
 * @author jolley
 */
public enum UpmsResultConstant {

    FAILED(-1, "failed"),
    SUCCESS(0, "success"),

    NOT_LOGGED_IN(10001, "Not Logged In"),
    INVALID_ACCORPWD(10101, "Account or Password error"),
	INVALID_ACCOUNT(10102, "Invalid account"),
	INVALID_PARAMETER(10103, "Invalid parameter");

    public int code;

    public String message;

    UpmsResultConstant(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
