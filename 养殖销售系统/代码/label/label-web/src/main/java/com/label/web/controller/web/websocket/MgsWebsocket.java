package com.label.web.controller.web.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.label.common.entity.WebsockeBean;
import com.label.common.model.custom.upms.UpmsUserInfo;
import com.label.common.util.UtilJson;
import com.label.util.UtilWebsocketSession;

@Controller 
@ServerEndpoint(value="/web/socket", configurator = GetHttpSessionConfigurator.class)
public class MgsWebsocket {  

	private Logger log = LoggerFactory.getLogger(MgsWebsocket.class);
	
	//连接超时
		public static final long MAX_TIME_OUT = 8 * 60 * 1000;
		
		private Session session; 
	      
		@OnOpen
		public void onOpen(Session session, EndpointConfig config) {
			this.session = session;
			UpmsUserInfo user= (UpmsUserInfo) config.getUserProperties().get(HttpSession.class.getName());
			session.setMaxIdleTimeout(MAX_TIME_OUT);
			UtilWebsocketSession.sessionMap.put(session,user);
		}
		
		@OnMessage
		public void onMessage(Session session, String message) {
			try {
				WebsockeBean json = UtilJson.str2Bean(message,WebsockeBean.class);
				//cmd
				if ("0".equals(json.getType())) {
//					UtilRedis.lpush(RedisConstant.LABEL_CMD_LOG_DATA, "发送数据："+json.getMessage());
//					MsgIoHandler mh = MINAMsgServerStarter.getMsgIoHandler();
//					if (mh!=null) {
//						mh.sendMsg(json.getMessage());
//					}
				}
				
				//warn
				if ("1".equals(json.getType())) {
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@OnClose
		public void onClose() {
			try {
				log.info("jolley>> onClose：关闭连接命令的websocket");
				UtilWebsocketSession.sessionMap.remove(session);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}
