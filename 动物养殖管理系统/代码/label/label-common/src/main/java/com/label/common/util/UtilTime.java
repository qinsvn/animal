package com.label.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 * @author jolley
 */
public class UtilTime {

	/** 时间戳转对应格式的字符串，时间戳单位秒或毫秒，默认格式 yyyy-MM-dd HH:mm:ss */
	public static String longToStr(long timestamp, String timeFormat) {
		String timeStr = null;

		// 如果时间戳为毫秒，则转为秒
		if (String.valueOf(timestamp).length() == 13) {
			timestamp = timestamp / 1000;
		}

		if (timestamp > 0) {
			if (timeFormat == null || timeFormat.length() == 0) {
				timeFormat = "yyyy-MM-dd HH:mm:ss";
			}

			SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
			timeStr = sdf.format(new Date(timestamp * 1000l));
		}

		return timeStr;
	}

	/** 将字符串日期转换为毫秒级时间戳类型 */
	public static Long str2Long(String date, String pattern) {
		long timeStamp = 0l;

		if (date == null || date.length() == 0) {
			return timeStamp;
		}

		if (pattern == null || pattern.length() == 0) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			timeStamp = sdf.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeStamp;
	}

	/** 获取当前星期 */
	public static String getWeek() {
		String[] weekDaysCode = { "日", "一", "二", "三", "四", "五", "六" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysCode[intWeek];
	}

	/**
	 * 是否在时间段内
	 * @param time 需要判断的时间 格式 HH:mm
	 * @param times 时间段 格式 HH:mm-HH:mm
	 * @return
	 */
	public static boolean isInMiddle(String time, String times) {
		boolean isInMiddle = false;

		if (time != null && time.replace(" ", "").matches("^\\d{2}:\\d{2}$") && times != null
				&& times.replace(" ", "").matches("^\\d{2}:\\d{2}-\\d{2}:\\d{2}$")) {
			String time1 = times.replace(" ", "").substring(0, 5);
			String time2 = times.replace(" ", "").substring(6, 11);

			DateFormat df = new SimpleDateFormat("HH:mm");
			try {
				Date d0 = df.parse(time.replace(" ", ""));
				Date d1 = df.parse(time1);
				Date d2 = df.parse(time2);

				// 单天时间段内
				if (d1.getTime() < d2.getTime() && d0.getTime() >= d1.getTime() && d0.getTime() <= d2.getTime()) {
					isInMiddle = true;
				}
				// 隔天时间段内
				if (d1.getTime() > d2.getTime() && (d0.getTime() >= d1.getTime() || d0.getTime() <= d2.getTime())) {
					isInMiddle = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return isInMiddle;
	}

	/**
	 * 判断两个时间差是否在day内，已自然天为单位
	 * @param beforDate 之前时间
	 * @param nowDate 当前时间
	 * @param day 相差天数
	 * @return
	 */
	public static boolean isInclude(Date beforeDate, Date nowDate, int day) {
		boolean flag = false;
		long now = nowDate.getTime();
		long before = beforeDate.getTime();
		if (now - before <= day * 24 * 60 * 60) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 获取日期相差天数
	 * @param date1 日期1
	 * @param date2 日期2
	 * @param format 日期格式
	 * @return date1减去date2的天数
	 */
	public static int compareDay(String date1, String date2, String format) {
		int ret = 0;

		try {
			Date d1 = new SimpleDateFormat(format, Locale.CHINA).parse(date1);
			Date d2 = new SimpleDateFormat(format, Locale.CHINA).parse(date2);

			// 日期转为 yyyy-MM-dd 00:00:00
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
			Date dateStart = new SimpleDateFormat("yyyyMMdd", Locale.CHINA).parse(sdf.format(d1));
			Date dateEnd = new SimpleDateFormat("yyyyMMdd", Locale.CHINA).parse(sdf.format(d2));

			ret = (int) ((dateStart.getTime() - dateEnd.getTime()) / (24 * 60 * 60 * 1000));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * 时间字符串转秒，如00:01:22转82
	 * @param time
	 * @return
	 */
	public static int timestr2int(String time) {
		int sec = 0;
		if (time != null && time.matches("^\\d+(:\\d{1,2}){0,2}$")) {
			String[] hhmmss = time.split(":");
			for (int i = 0; i < hhmmss.length; i++) {
				if (i == 0) {
					sec += UtilString.getInt(hhmmss[hhmmss.length - i - 1]);
				} else if (i == 1) {
					sec += UtilString.getInt(hhmmss[hhmmss.length - i - 1]) * 60;
				} else if (i == 2) {
					sec += UtilString.getInt(hhmmss[hhmmss.length - i - 1]) * 60 * 60;
				}
			}
		}

		return sec;
	}

	/**
	 * 秒转时间字符串，如82转00:01:22
	 * @param sec
	 * @return
	 */
	public static String int2timestr(int sec) {
		String timestr = null;

		// 秒
		if (sec >= 0) {
			timestr = String.format("%02d", sec % 60);
		}
		// 分
		if (sec >= 60) {
			timestr = String.format("%02d", sec / 60 % 60) + ":" + timestr;
		} else {
			timestr = "00:" + timestr;
		}
		// 时
		if (sec >= 60 * 60) {
			timestr = String.format("%02d", sec / (60 * 60)) + ":" + timestr;
		} else {
			timestr = "00:" + timestr;
		}

		return timestr;
	}
}
