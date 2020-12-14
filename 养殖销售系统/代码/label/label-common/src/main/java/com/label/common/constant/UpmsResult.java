package com.label.common.constant;

import net.sf.json.JSONObject;

/**
 * upms系统常量枚举类
 * @author jolley
 */
public class UpmsResult extends BaseResult { 

    public UpmsResult(UpmsResultConstant upmsResultConstant, Object data) {
        super(upmsResultConstant.getCode(), upmsResultConstant.getMessage(), data);
    }
    
    /**
     * 成功
     * @param data
     * @return
     */
    public static Object SUCCESS(Object data) {
    	return  new BaseResult(UpmsResultConstant.SUCCESS.getCode(), UpmsResultConstant.SUCCESS.getMessage(), data);
	}
    
    /**
     * 列表成功
     * @param list
     * @param total
     * @return
     */
    public static Object LISTSUCCESS(Object list, long total) {
    	JSONObject object = new JSONObject();
    	object.put("list", list);
    	object.put("total", total);
    	return  new BaseResult(UpmsResultConstant.SUCCESS.getCode(), UpmsResultConstant.SUCCESS.getMessage(), object);
    }
    
    /**
     * 失败
     * @param data
     * @return
     */
    public static Object FAILED(Object data) {
    	return  new BaseResult(UpmsResultConstant.FAILED.getCode(), UpmsResultConstant.FAILED.getMessage(), data);
    }

}
