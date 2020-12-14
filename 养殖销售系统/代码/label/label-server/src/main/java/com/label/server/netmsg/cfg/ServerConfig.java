package com.label.server.netmsg.cfg;

import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.UtilString;

public class ServerConfig {
	public static int SERVER_PORT =  3636;
	static{
		String  port = UtilPropertiesFile.getInstance().get("sys_g780_server_port"); 
		if (UtilString.isNotEmpty(port)) {
			SERVER_PORT = Integer.valueOf(port);
		}
	}
}
