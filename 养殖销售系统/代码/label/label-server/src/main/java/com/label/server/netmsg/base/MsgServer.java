//package com.label.server.netmsg.base;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.mina.core.session.IoSession;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.label.server.netmsg.utils.StringUtils;
//
//public class MsgServer {
//
//	private static final Logger log = LoggerFactory.getLogger(MsgServer.class);
//
//	private Set<ClientHandler> clients;
//	Map<String, IoSession>  clientStrMap = new HashMap<String, IoSession> ();
//
//	public MsgServer() {
//		clients = new HashSet<ClientHandler>();
//	}
//
//	public void addClient(ClientHandler ch) {
//		log.info(StringUtils.join("client", ch.getClientId(), "connected", "."));
//		clients.add(ch);
//		try {
//		} catch (Exception e) {
//			log.info(StringUtils.join("send welcom message to", ch.getClientId(), "error."));
//			e.printStackTrace();
//		}
//	}
//
//	public void removeClient(ClientHandler ch) {
//		clients.remove(ch);
//	}
//
//
//	protected String usernames() {
//		int size = clients.size();
//		int count = 0;
//		StringBuffer usernames = new StringBuffer();
//		for (ClientHandler sch : clients) {
//			count++;
//			usernames.append(sch.getClientId());
//			if (count < size) {
//				usernames.append(",");
//			}
//		}
//		return usernames.toString();
//	}
//
//	public void shutdown() {
//		beforeShutdown();
//		for (ClientHandler sch : clients) {
//			try {
//				sch.disconnectClient();
//				sch.disconnect();
//				removeClient(sch);
//				log.info(StringUtils.join("client", sch.getClientId(), "#", sch.getClientId(), "disconnecting..."));
//			} catch (Exception e) {
//				log.info(StringUtils.join("client", sch.getClientId(), "#", sch.getClientId(), "disconnect error."));
//				e.printStackTrace();
//			}
//		}
//		afterShutdown();
//	}
//
//	protected void beforeShutdown() {
//
//	}
//
//	protected void afterShutdown() {
//
//	}
//}