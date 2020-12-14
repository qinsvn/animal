package com.i.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date d = new Date();
		String str = sdf.format(d);
		return str;
	}
}
