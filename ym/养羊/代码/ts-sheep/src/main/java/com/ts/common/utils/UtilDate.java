/**
 * @company 广州天迅网络科技有限公司 copyright
 * @author jolley
 * @date 2017年11月23日
 * @describe  xxx
 *
 */
package com.ts.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description:时间工具类
 * @version 1.0
 * @since 1.0
 * @author jolley
 */
public class UtilDate {
	
	public static SimpleDateFormat chineseFormat = new SimpleDateFormat("yyyy年MM月dd日");
	public static SimpleDateFormat chineseFullFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	private static String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	
	/**
	 * 把yyyy-MM-dd类型的时间转成java.util.Date类型
	 * @param adateStrteStr 日期字符串
	 * @return 返回转换成功的Date类型的日期
	 */
	public static Date convertDate(String adateStrteStr) {
		Date date = null;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			date=sdf.parse(adateStrteStr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 把yyyy-MM-dd HH:mm:ss类型的时间转成java.util.Date类型
	 * @param dateTime 日期字符串
	 * @return 返回转换成功的Date类型的日期
	 */
	public static Date convertDateTime(String dateTime) {
		Date date = null;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			sdf.setLenient(false);
			date=sdf.parse(dateTime);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}
	
	/**
	 * @param dateTime 日期字符串
	 * @param formate   格式
	 * @return 返回转换成功的Date类型的日期
	 */
	public static Date convertDateTimeByFormate(String dateTime,String formate) {
		Date date = null;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat(formate);
			sdf.setLenient(false);
			date=sdf.parse(dateTime);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}
	

    /**
     * 根据格式返回日期
     *
     * @param format
     * @return
     */
    public static String getDate(String format) {
        sdf.applyPattern(format);
        return sdf.format(new Date());
    }

    /**
     * 根据格式,日期 返回日期
     *
     * @param format
     * @return
     */
    public static String formatDate(String format,Date date) {
        sdf.applyPattern(format);
        return sdf.format(date);
    }

    /**
     * 获取今天是星期几
     * @return
     */
    public static String getWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 输入时期是否今天
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        if (date == null) {
            return false;
        }
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());
        Calendar inputDate = Calendar.getInstance();
        inputDate.setTime(date);

        if (nowCalendar.get(Calendar.YEAR) == inputDate.get(Calendar.YEAR)
                && nowCalendar.get(Calendar.MONTH) == inputDate.get(Calendar.MONTH)
                && nowCalendar.get(Calendar.DAY_OF_MONTH) == inputDate.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }
    
    /**
     * 获得当前时间
     * @return
     */
    public static Timestamp getCurrentTime(){
    	return new Timestamp(new Date().getTime());
    }
    
    
    /**
     * 两个日期相差的天数（非严格意义上的天数，只考虑到天，不考虑时分秒等）
     * @param bfDate
     * @param afDate
     * @return
     */
    public static int getIntervalDays(Date bfDate, Date afDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bfDate);      
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(afDate);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);

        return day2 - day1;

     }
    
    /**
     * 几天前
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {  
        Calendar now = Calendar.getInstance();  
        now.setTime(d);  
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);  
        return now.getTime();  
    }    
    
    /**
     * 几天后
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {  
        Calendar now = Calendar.getInstance();  
        now.setTime(d);  
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);  
        return now.getTime();  
    }

    public static String timeStamp2Date(String seconds,String format) {  
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }   
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds+"000")));  
    }  
    public static void main(String[] args) {
    	 String date = timeStamp2Date("1572510054", "yyyy-MM-dd HH:mm:ss");  
         System.out.println("date="+date);//运行输出:date=2016-08-04 10:34:42
	}
}
