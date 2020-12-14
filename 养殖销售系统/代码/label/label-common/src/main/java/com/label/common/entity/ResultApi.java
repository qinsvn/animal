/**
 * 
 */
package com.label.common.entity;

/**
 * @author Administrator
 *
 */
public class ResultApi {

	public static String SUCCESSCODE ="0";
	public static String FAILCODE ="-1";

	public static String SUCCESSMSG ="请求成功";
	public static String FAILMSG ="请求失败";
	
	private String code=FAILCODE;
	private String msg=FAILMSG;
	
	public ResultApi(String code,String msg){
		this.code=code;
		this.msg=msg;
	}
	
	public void set(String code,String msg){
		this.code=code;
		this.msg=msg;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
