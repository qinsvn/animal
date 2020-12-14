package com.i.util;
import java.util.LinkedList;
import java.util.Queue;



public class TextLogUtil {
	
	private static Queue<String> queue = new LinkedList<String>();

	public  static void setInCurrent(String log) {
		queue.offer(DateUtils.getCurrentTime()+"   "+log);
    }
	
	public static String get(){
	  return 	queue.poll();
	}
	
}
