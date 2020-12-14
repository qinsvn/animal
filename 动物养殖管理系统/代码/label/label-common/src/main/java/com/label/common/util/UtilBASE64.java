package com.label.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * BASE64工具类
 * @author jolley
 */
@SuppressWarnings("restriction")
public class UtilBASE64 {
	
	/**
	 * BASE64解密
	 * @param key
	 * @return
	 */
	public static byte[] decryptBASE64(String key) {
		try {
			return (new BASE64Decoder()).decodeBuffer(key);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * BASE64加密
	 * @param key
	 * @return
	 */
	public static String encryptBASE64(byte[] key) {
		try {
			return (new BASE64Encoder()).encodeBuffer(key);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
