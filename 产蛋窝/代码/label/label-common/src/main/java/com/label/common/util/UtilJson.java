package com.label.common.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.sf.json.xml.XMLSerializer;

/**
 * Json工具类，参考 http://www.cnblogs.com/a757956132/p/5555834.html
 * @author jolley
 */
public class UtilJson {
	
	private final static Logger _log = LoggerFactory.getLogger(UtilJson.class);
	/**
	 * 对象转json字符串
	 * @param obj
	 * @return
	 */
	public static String Obj2Str(Object obj) {
		try {
			return JSON.toJSONString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 字符串转json
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject str2Json(String jsonStr) {
		if(jsonStr != null) {
			try {
				return JSON.parseObject(jsonStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 字符串转jsonarry
	 * @param jsonStr
	 * @return
	 */
	public static JSONArray str2JSONArry(String jsonStr) {
		try {
			return JSONArray.parseArray(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * xml格式转json
	 * @param xml
	 * @return
	 */
	public static JSONObject xml2Json(String xml) {
		if (xml != null) {
			try {
				XMLSerializer xmlSerializer = new XMLSerializer();
				String jsonStr = xmlSerializer.read(xml).toString();
				
				return str2Json(jsonStr);
			} catch (Exception e) {
				_log.info("jolley>> try again xml2json, xml: " + xml);
				// e.printStackTrace();
				
				// 尝试去掉无效字符再转
				try {
					XMLSerializer xmlSerializer = new XMLSerializer();
					String jsonStr = xmlSerializer.read(UtilString.filterInvalidChar(xml)).toString();

					return str2Json(jsonStr);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}

		return null;
	}
	
	/**
	 * 字符串转bean类
	 * @param jsonStr
	 * @param cls
	 * @return
	 */
	public static <T> T str2Bean(String jsonStr, Class<T> cls) {
		T obj = null;
		
		if(jsonStr != null) {
			try {
				obj = JSON.parseObject(jsonStr, cls);
			} catch (Exception e) {
				e.printStackTrace();
				_log.info("jolley>> jsonStr: {}", jsonStr);
			}
		}
		
		return obj;
	}
	
	/**
	 * 字符串转bean类
	 * @param jsonStr
	 * @param cls
	 * @return
	 */
	public static <T> List<T> str2ListBean(String jsonArrStr, Class<T> cls) {
		List<T> obj = null;
		try {
			obj = JSONArray.parseArray(jsonArrStr, cls);
		} catch (Exception e) {
			e.printStackTrace();
			_log.info("jolley>> jsonArrStr: {}", jsonArrStr);
		}
		
		return obj;
	}
	
	public static void main(String[] args) {
		String s = "{\"Id\":2,\"name\":\"fds\"}";
		JSONObject jsonObj = str2Json(s);
		
		System.out.println(jsonObj.get("Id"));
	}
}
