package com.label.server.netmsg.mina;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.label.common.constant.RedisConstant;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.UtilString;
import com.label.common.util.redis.UtilRedis;

public class MsgIoHandler extends IoHandlerAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(MsgIoHandler.class);
	
	Map<IoSession, MINAClientHandler> clientMap = new HashMap<IoSession, MINAClientHandler>();
	Map<String, IoSession>  clientStrMap = new HashMap<String, IoSession> ();
   private static String startStr = UtilPropertiesFile.getInstance().get("sys_g780_client_startStr"); 
   
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		MINAClientHandler ch = new MINAClientHandler(session);
		clientMap.put(session, ch);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		String clientId = clientMap.get(session).getClientId();
		if (UtilString.isNotEmpty(clientId)) {
			clientStrMap.remove(clientId);
		}
		clientMap.remove(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String mp  =  message.toString();
		logger.info("jolley>> MsgIoHandler 接收数据: " + mp);
		MINAClientHandler ch = clientMap.get(session);
		if (UtilString.isNotEmpty(mp)) {
			if (UtilString.isEmpty(ch.getClientId())) {
				int index = mp.indexOf(startStr);
				if (index>0) {
					//客户端id  g780的emi码
					String clientId = mp.substring(0, index);
					clientStrMap.put(clientId, session);
					ch.setClientId(clientId);
					
				}
			}
			UtilRedis.lpush(RedisConstant.LABEL_G780_REC_DATA, mp);
			UtilRedis.lpush(RedisConstant.LABEL_CMD_LOG_DATA, "接收数据："+mp);
		}
	}

	 public void sendMsg( String fullMsg) throws Exception  {
		 String clientId,  message;
		if (UtilString.isNotEmpty(fullMsg)) {
			int index = fullMsg.indexOf(startStr);
			if (index>0) {
				 clientId = fullMsg.substring(0, index);
				 message = fullMsg.substring(index, fullMsg.length());
				IoSession session =  clientStrMap.get(clientId);
				if (session!=null) {
					MINAClientHandler ch = clientMap.get(session);
					if (ch!=null) {
						try {
							ch.sentData(message);
							UtilRedis.lpush(RedisConstant.LABEL_CMD_LOG_DATA, "发送成功");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							UtilRedis.lpush(RedisConstant.LABEL_CMD_LOG_DATA, "发送失败："+e.getMessage());
							e.printStackTrace();
						}
					}else{
						UtilRedis.lpush(RedisConstant.LABEL_CMD_LOG_DATA, "发送失败：没有指定客户端 ["+clientId+"]");
					}
				}else{
					UtilRedis.lpush(RedisConstant.LABEL_CMD_LOG_DATA, "发送失败：没有指定客户端 ["+clientId+"]");
				}
			}else{
				UtilRedis.lpush(RedisConstant.LABEL_CMD_LOG_DATA, "发送数据：["+fullMsg+"] "+"格式错误");
			}
		}
 }
	
	public static void main(String[] args) {
		
	}
}
