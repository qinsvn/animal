/**
 * 
 */
package com.label.task.warn;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.label.common.base.BaseThread;
import com.label.common.constant.RedisConstant;
import com.label.common.entity.WebsockeBean;
import com.label.common.model.base.BsWarnCus;
import com.label.common.model.custom.upms.UpmsUserInfo;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilString;
import com.label.common.util.redis.UtilRedis;
import com.label.task.util.UtilWebsocketSession;

/**
 * @author admin
 *
 */
public class WarnTask implements BaseThread {

	private static Logger _log = LoggerFactory.getLogger(WarnTask.class);
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
					while(UtilString.isNotEmpty(message = UtilRedis.rpop(RedisConstant.LABEL_OVERTIME_WARN_DATA))) {
						try {
								for(Session session : UtilWebsocketSession.sessionMap.keySet()){
								synchronized (session) {
									if (session.isOpen()) {
										_log.info("jolley>>" + session.getId() + "--告警信息为：" + message);
										UpmsUserInfo user = UtilWebsocketSession.sessionMap.get(session);
										BsWarnCus warn = UtilJson.str2Bean(message, BsWarnCus.class);
										 if(user.getCompanyId()==0||warn.getDptId().equals(String.valueOf(user.getCompanyId()))){
											 WebsockeBean result = new WebsockeBean();
											 result.setType(WebsockeBean.WARN_TYPE);
											 result.setMessage(warn.getWarnInfo());
											 session.getBasicRemote().sendText(UtilJson.Obj2Str(result));
										 }
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
