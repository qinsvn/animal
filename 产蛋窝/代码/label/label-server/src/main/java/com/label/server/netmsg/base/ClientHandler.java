package com.label.server.netmsg.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ClientHandler {

	private static final Logger log = LoggerFactory.getLogger(ClientHandler.class);

	private String clientId;

	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * 接收消息处理
	 * @param mp
	 * @throws Exception
	 */
	public void dispatch(String mp) throws Exception {
		log.info("rec client msg:{}",mp); 
		
	}

	protected void disconnectClient() throws Exception {
	}

	protected abstract void sentData(String mp) throws Exception;

	protected abstract void disconnect() throws Exception;

}