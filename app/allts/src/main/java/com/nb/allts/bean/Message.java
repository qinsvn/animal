package com.nb.allts.bean;

import com.nb.allts.constant.MgsConstant;

public class Message {
    private String id;
    private String code;
    private String type;
    private String msg;
    private Object data;

    public Message() {
        this.code = MgsConstant.code_success;
        this.type = MgsConstant.type_1;
        this.msg = "成功数据";
    }

    public Message(String code, String type, String msg) {
        this.code = code;
        this.type = type;
        this.msg = msg;
    }

    public Message(String id, String code, String type, String msg, Object data) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.msg = msg;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
