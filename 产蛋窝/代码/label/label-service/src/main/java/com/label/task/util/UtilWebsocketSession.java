package com.label.task.util;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import com.label.common.model.custom.upms.UpmsUserInfo;

public class UtilWebsocketSession { 
	public static Map< Session,UpmsUserInfo> sessionMap = new HashMap< Session,UpmsUserInfo>();
}
