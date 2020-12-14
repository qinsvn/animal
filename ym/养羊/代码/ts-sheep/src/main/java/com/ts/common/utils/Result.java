package com.ts.common.utils;

import java.util.HashMap;
import java.util.Map;

public class Result extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	public static final int SECCESS =0;
	public static final int FAIL =-1;
	
	public Result() {
		put("code", SECCESS);
		put("msg", "操作成功");
	}

	public static Result error() {
		return error(FAIL, "操作失败");
	}

	public static Result error(String msg) {
		return error(FAIL, msg);
	}

	public static Result error(int code, String msg) {
		Result r = new Result();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}
	
	public static Result error(String msg,Object data) {
		Result r = new Result();
		r.put("code", FAIL);
		r.put("msg", msg);
		r.put("data", data);
		return r;
	}
	
	public static Result error(int code, String msg,Object data) {
		Result r = new Result();
		r.put("code", code);
		r.put("msg", msg);
		r.put("data", data);
		return r;
	}

	public static Result ok(String msg) {
		Result r = new Result();
		r.put("msg", msg);
		return r;
	}
	public static Result ok(String msg,Object data) {
		Result r = new Result();
		r.put("msg", msg);
		r.put("data", data);
		return r;
	}

	public static Result ok(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}

	public static Result ok() {
		return new Result();
	}

	public  Result setCode(int code){
		super.put("code", code);
		return this;
	}
	public  Result setMsg(String msg){
		super.put("msg", msg);
		return this;
	}

	public  Result setData(Object data){
		super.put("data", data);
		return this;
	}

	public  Result set(String msg,Object data){
		super.put("msg", msg);
		super.put("data", data);
		return this;
	}
	public  Result set(int code,String msg,Object data){
		super.put("code", code);
		super.put("msg", msg);
		super.put("data", data);
		return this;
	}
	
	@Override
	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
