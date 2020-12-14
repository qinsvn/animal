package com.label.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 进程指令管理
 * @author jolley
 */
public class UtilProcessCommand {
	
	/**
	 * 执行简单指令
	 * @param command
	 * @return
	 */
	public static String exec(String command) {
		String result = null;
		
		try {
			String[] cmds = {"/bin/sh", "-c", command};
			Process pro = Runtime.getRuntime().exec(cmds);
			pro.waitFor();
			InputStream in = pro.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			result = read.readLine();
			String line = null;
			while((line = read.readLine()) != null) {
	            result += "\n" + line;
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
