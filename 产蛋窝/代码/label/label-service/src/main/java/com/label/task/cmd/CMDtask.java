/**
 * 
 */
package com.label.task.cmd;

import java.util.Date;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.label.common.base.BaseThread;
import com.label.common.constant.RedisConstant;
import com.label.common.entity.WebsockeBean;
import com.label.common.util.UtilDate;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilString;
import com.label.common.util.redis.UtilRedis;
import com.label.task.util.UtilWebsocketSession;

/**
 * @author admin
 *
 */
public class CMDtask implements BaseThread {
 
	private static Logger _log = LoggerFactory.getLogger(CMDtask.class); 
//	public static Set< Session> sessionSet= new HashSet<Session>();
	/* (non-Javadoc)
	 * @see com.label.common.base.BaseThread#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		new MessageThread().start();
	}
	private class MessageThread extends Thread {
		public void run() {
			while(true) {
				try {
					String message = null;
					while(UtilString.isNotEmpty(message = UtilRedis.rpop(RedisConstant.LABEL_CMD_LOG_DATA))) {
						try {
							for(Session session : UtilWebsocketSession.sessionMap.keySet()){
								synchronized (session) {
									if (session.isOpen()) {
										_log.info("jolley>>" + session.getId() + "--信息为：" + message);
										StringBuilder msg = new StringBuilder("【").append(UtilDate.formatDate("HH:mm:ss", new Date())).append("】   ").append(message);
										WebsockeBean result = new WebsockeBean();
										result.setType(WebsockeBean.CMD_TYPE);
										result.setMessage(msg.toString());
										session.getBasicRemote().sendText(UtilJson.Obj2Str(result));
									}
								}
							}
						} catch (Exception e) {
							_log.error("send message exception", e);
						}
					}
					Thread.sleep(1000l);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
