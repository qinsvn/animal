package com.label.server.netmsg.mina;

import org.apache.mina.core.session.IoSession;

import com.label.server.netmsg.base.ClientHandler;

public class MINAClientHandler extends ClientHandler {
	private final IoSession session;

	public MINAClientHandler(IoSession session) {
		this.session = session;
	}

	@Override
	protected void disconnect() throws Exception {
		session.close(true);
	}

	@Override
	protected void sentData(String mp) throws Exception {
		session.write(mp);
	}

}
