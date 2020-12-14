package com.label.common.util;

import java.util.Random;

/**
 * 数字处理类
 * @author jolley
 */
public class UtilDigit {
	
	/**
	 * 对象转整型
	 * @param valueStr
	 * @param defaultInt
	 * @return
	 */
	public static int obj2Int(Object obj, int defaultInt) {
		if(obj != null) {
			return str2Int(String.valueOf(obj), defaultInt);
		}
		
		return defaultInt;
	}

	/**
	 * 字符串转整型
	 * @param valueStr
	 * @param defaultInt
	 * @return
	 */
	public static int str2Int(String valueStr, int defaultInt) {
		int value = defaultInt;
		
		if(valueStr != null && valueStr.matches("^\\d+$")) {
			try {
				value = Integer.valueOf(valueStr);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return value;
	}
	
	/**
	 * 对象转长整型
	 * @param valueStr
	 * @param defaultInt
	 * @return
	 */
	public static long obj2Long(Object obj, long defaultInt) {
		if(obj != null) {
			return str2Long(String.valueOf(obj), defaultInt);
		}
		
		return defaultInt;
	}
	
	/**
	 * 字符串转长整型
	 * @param valueStr
	 * @param defaultLong
	 * @return
	 */
	public static long str2Long(String valueStr, long defaultLong) {
		long value = defaultLong;
		
		if(valueStr != null && valueStr.matches("^\\d+$")) {
			try {
				value = Long.valueOf(valueStr);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return value;
	}
	
	/**
	 * 字符串转浮点型
	 * @param valueStr
	 * @param defaultFloat
	 * @return
	 */
	public static float str2Float(String valueStr, long defaultFloat) {
		float value = defaultFloat;
		
		if(valueStr != null) {
			try {
				value = Float.valueOf(valueStr);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return value;
	}
	
	/**
	 * 字符串转双精型
	 * @param valueStr
	 * @param defaultFloat
	 * @return
	 */
	public static double str2Double(String valueStr, double defaultFloat) {
		double value = defaultFloat;
		
		if(valueStr != null) {
			try {
				value = Double.valueOf(valueStr);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return value;
	}
	
	/**
	 * 获取两个数之间的随机数，支持负数
	 * @param limit1 随机数的最大或最小值
	 * @param limit2 随机数的最大或最小值
	 * @return 如果大小一样返回0
	 */
	public static int random(int limit1, int limit2) {
		int min = limit1;
		int max = limit2;
		
		// 如果大小一样返回0
		if(min == max) {
			return min;
		}
		// 检查大小
		if(min > max) {
			min = max - min;
			max = max - min;
			min = max + min;
		}
		// 检查负数
		int negativeBase = 0;
		if(min < 0) {
			negativeBase = -min;
			min += negativeBase;
			max += negativeBase;
		}
		
		Random random = new Random();
		return random.nextInt(max + 1) % (max-min + 1) + min - negativeBase;
	}
	
	/**
	 * 整型转中文阿拉伯数字，仅支持小于一百的数
	 * @param value
	 * @return
	 */
	public static String int2ChineseStr(int value) {
		String chineseStr = "";
		if(value < 0) {
			chineseStr = "负";
			value = -value;
		}
		
		String[] str = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
		
		if(value < 10) {
			chineseStr += str[value];
		}
		else if(value < 20) {
			chineseStr += str[10] + (value % 10 > 0 ? str[value % 10] : "");
		}
		else if(value < 100) {
			chineseStr += str[value / 10] + str[10] + (value % 10 > 0 ? str[value % 10] : "");
		}
		
		return chineseStr;
	}
	
	public static void main(String []args) {
		int i = UtilDigit.random(-2,-1);
		
		System.out.println("jolley>> " + i);
	}
}
