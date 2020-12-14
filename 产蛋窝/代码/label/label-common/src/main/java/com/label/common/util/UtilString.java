package com.label.common.util;


import java.io.InputStream;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 * String 工具类
 * @author jolley
 */
public class UtilString extends StringUtils{

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
	/**
	 * 判断字符串是否为空的正则表达式，空白字符对应的unicode编码
	 */
	private static final String EMPTY_REGEX = "[\\s\\u00a0\\u2007\\u202f\\u0009-\\u000d\\u001c-\\u001f]+";

    /**
	 * 读取输入流
	 * @param in
	 * @return
	 */
	public static String getString (InputStream in) {
		String data = null;
		try {
		    StringBuffer out = new StringBuffer();
		    byte[] b = new byte[4096];
		    for(int n; (n = in.read(b)) != -1;) {
		        out.append(new String(b, 0, n));
		    }
		    data = out.toString();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * 数组是否包含字符串
	 * @param data
	 * @param str
	 * @param ignoreCase
	 * @return
	 */
	public static boolean isContain(String []data, String str, boolean ignoreCase) {
		boolean ret = false;
		
		if(data != null && str != null) {
			for(String item : data) {
				if(str.equals(item) || (ignoreCase && str.equalsIgnoreCase(item))) {
					ret = true;
					break;
				}
			}
		}
		
		return ret;
	}
	
	/**
	 * 获取随机数据字母
	 * @param count 随机数据长度
	 * @param digit 是否包含数字
	 * @param lowerLetter 是否包含小写字母
	 * @param caseLetter 是否包含大写字母
	 * @return
	 */
	public static String randomStr(int count, boolean digit, boolean lowerLetter, boolean caseLetter) {
		String ret = "";
		
		int randomMax = (digit ? 10 : 0) + (lowerLetter ? 26 : 0) + (caseLetter ? 26 : 0);
		if(randomMax > 0) {
			Random random = new Random();
			for(int i = 0; i < count; i++) {
				int r = random.nextInt(randomMax);
				if(digit && r < 10) {
					ret += r;
				}
				else if(lowerLetter && (r < ((digit ? 10 : 0) + 26))) {
					ret += (char) (r - (digit ? 10 : 0) + 'a');
				}
				else if(caseLetter) {
					ret += (char) (r - (digit ? 10 : 0) - (lowerLetter ? 26 : 0) + 'A');
				}
			}
		}
		
		return ret;
	}
	
	/**
	 * 过虑无效的字符
	 * @param data
	 * @return
	 */
	public static String filterInvalidChar(String data) {
		StringBuilder sb = new StringBuilder();
		char[] chs = data.toCharArray();
		for(char ch : chs) {
			if((ch >= 0x00 && ch <= 0x08)
					|| (ch >= 0x0b && ch <= 0x0c)
					|| (ch >= 0x0e && ch <= 0x1f)) {
				
			}
			else {
				sb.append(ch);
			}
		}
		
		return sb.toString();
	}

	/**
	 * UTF-8编码
	 * @param s
	 * @return
	 */
	public static String toUTF8(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			}
			else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception e) {
					e.printStackTrace();
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) {
						k += 256;  
					}
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
    
    /**
     * 下划线转驼峰
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);

        str = sb.toString();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);

        return str;
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    /**
     * 驼峰转下划线,效率比上面高
     * @param str
     * @return
     */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 首字母转小写
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuffer()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * object转String
     * @param object
     * @return
     */
    public static String getString(Object object) {
        return getString(object, "");
    }

    public static String getString(Object object, String defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return object.toString();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转Integer
     * @param object
     * @return
     */
    public static int getInt(Object object) {
        return getInt(object, -1);
    }

    public static int getInt(Object object, Integer defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转Boolean
     * @param object
     * @return
     */
    public static boolean getBoolean(Object object) {
        return getBoolean(object, false);
    }

    public static boolean getBoolean(Object object, Boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    /**
     * 数组转字符串
     * @param arr
     * @param delimit 分隔符，默认逗号
     * @return
     */
    public static String arr2Str(Object []arr, String delimit) {
    	if(delimit == null) {
    		delimit = ",";
    	}
    	
    	if(arr != null) {
    		if(arr.length > 0) {
    			StringBuffer strBuffer = new StringBuffer();
    			for(Object obj : arr) {
    				if(!StringUtils.isEmpty(obj)) {
    					if(strBuffer.length() > 0) {
    						strBuffer.append(delimit);
    					}
    					strBuffer.append(obj);
    				}
    			}
    			
        		return strBuffer.toString();
    		}
    	}
    	
    	return null;
    }

    /**
     * 按规则（正则表达式）校验字符串
     * @param target 被校验字符串
     * @param rule 校验规则
     * @return
     */
    public static boolean verification(String target,String rule) {  
        boolean flag = false;  
        try {
        	if(!StringUtils.isEmpty(target)&&!StringUtils.isEmpty(rule)){
        		Pattern regex = Pattern.compile(rule);  
        		Matcher matcher = regex.matcher(target);  
        		flag = matcher.matches();          		
        	}
        } catch (Exception e) {  
            flag = false;  
        }  
        return flag;  
    }  
    
    /**
     * 处理编号
     * @param str 参数
     * @return
     */
    public static String processingnumber(String str) { 
    	
    	String newnum="";
        int num =Integer.parseInt(str.substring(str.length()-6))+1;
        switch ((num+"").length()) {
		case 1:
			newnum="G00000"+num;
			break;
		case 2:
			newnum="G0000"+num;
			break;
		case 3:
			newnum="G000"+num;
			break;
		case 4:
			newnum="G00"+num;
			break;
		case 5:
			newnum="G0"+num;
			break;
		case 6:
			newnum="G"+num;
			break;
		default:
			break;
		}
        return newnum;  
    }  
    
    /**
	 * 多个字符串顺序拼接
	 * @return
	 */
	public static String addString(Object... strs) {
		if (strs != null && strs.length > 0) {
			StringBuffer buffer = new StringBuffer();
			for (Object string : strs) {
				buffer.append(string);
			}
			return buffer.toString();
		}
		return "";
	}
	
	
	/**
	 * 验证字符串是否为空
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isEmpty(String input) {
		return input == null || input.equals("") || input.matches(EMPTY_REGEX);
	}
	
	public static boolean isNotEmpty(String input){
		return !isEmpty(input);
	}
	
	/**
	 * 补0和截取长度
	 * @param start 开头
	 * @param id 需要处理的字符串
	 * @param length 去除开头后的长度
	 * @return
	 */
	public static String getCode(String start, String id,Integer length) {
		String code = "";
		if(!StringUtils.isEmpty(id)){
			if(id.length()>length){
				//id超长
				//id = id.substring(id.length()-length,id.length());
			}else  if(id.length()<length){
				for(int i=0;i<length-id.length();i++){
					start += "0";
				}
			}
		}else{
			return "";
		}
		code = start+id;
		return code;
	}
	
    public static void main(String[] args){
//    	System.out.println(processingnumber("G145688"));
//    	System.out.println(isDecimal("1"));
//    	System.out.println(isDecimal("1.1"));
//    	System.out.println(isDecimal("1.23"));
//    	System.out.println(isDecimal("s1"));
//    	System.out.println(isDecimal("11231.235"));
    }
    
    //浮点型判断
	public static boolean isDecimal(String str) {
		if (str == null || "".equals(str))
			return false;
		java.util.regex.Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
		return pattern.matcher(str).matches();
	}

    //整型判断
	public static boolean isInteger(String str) {
		if (str == null)
			return false;
		Pattern pattern = Pattern.compile("[0-9]+");
		return pattern.matcher(str).matches();
	}

}
