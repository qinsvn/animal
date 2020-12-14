package com.label.common.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * request工具类
 * @author jolley
 */
public class UtilRequest {

	/**
	 * 获取语法的url
	 * @param request
	 * @param besideKeys 移除请求中指定的参数
	 * @return
	 */
	public static String getUrl(HttpServletRequest request, String []besideKeys) {
		return getUrl(request, -1, besideKeys);
	}
	/**
	 * 获取语法的url
	 * @param request
	 * @param port
	 * @param besideKeys 移除请求中指定的参数
	 * @return
	 */
    public static String getUrl(HttpServletRequest request, int port, String []besideKeys) {
    	String params = "";
    	if(besideKeys == null || besideKeys.length <= 0) {
    		params = request.getQueryString();
    	} else {
	    	@SuppressWarnings("unchecked")
			Map<String, String[]> map = request.getParameterMap();
			for(Map.Entry<String, String[]> entry : map.entrySet()) {
				if(!UtilString.isContain(besideKeys, entry.getKey(), false)) {
					if (params.equals("")) {
	                    params = entry.getKey() + "=" + entry.getValue()[0];
	                } else {
	                    params += "&" + entry.getKey() + "=" + entry.getValue()[0];
	                }
				}
			}
    	}
		
        String url = null;
        if(port > 0) {
        	if(port == 80) {
        		url = String.format("%s://%s%s", request.getScheme(), request.getServerName(), request.getRequestURI());
        	} else {
        		url = String.format("%s://%s:%s%s", request.getScheme(), request.getServerName(), port, request.getRequestURI());
        	}
        } else {
        	url = request.getRequestURL().toString();
        }
		if (!StringUtils.isBlank(params)) {
			url = url + "?" + params;
        }
        
        return url.toString();
    }

	/**
	 * 获取ip工具类，除了getRemoteAddr，其他ip均可伪造
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("Cdn-Src-Ip");    // 网宿cdn的真实ip
		if (ip == null || ip.length() == 0 || " unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");   // 蓝讯cdn的真实ip
		}
		if (ip == null || ip.length() == 0 || " unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");  // 获取代理ip
		}
		if (ip == null || ip.length() == 0 || " unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP"); // 获取代理ip
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP"); // 获取代理ip
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr(); // 获取真实ip
		}
		
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if(ip != null) {
			ip = ip.split(",")[0].trim();
		}
		
		return ip;
	}
}
