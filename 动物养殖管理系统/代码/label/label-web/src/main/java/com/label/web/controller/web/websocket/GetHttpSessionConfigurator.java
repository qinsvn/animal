package com.label.web.controller.web.websocket;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import com.label.common.model.custom.upms.UpmsUserInfo;
import com.label.util.UserUtil;
 
public class GetHttpSessionConfigurator extends Configurator { 
 
	@Override 
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		UpmsUserInfo userInfo = UserUtil.getUpmsUserInfo(); 
		sec.getUserProperties().put(HttpSession.class.getName(), userInfo); 
	} 
 
}