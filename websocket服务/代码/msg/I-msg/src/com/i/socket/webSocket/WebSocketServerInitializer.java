package com.i.socket.webSocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class WebSocketServerInitializer   extends ChannelInitializer<SocketChannel> {

	public  WebSocketServerHandler webSocketServerHandler;
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		webSocketServerHandler = new WebSocketServerHandler();
		ch.pipeline().addLast(  
                new HttpResponseEncoder(),  
                new HttpRequestDecoder(),  
                new HttpObjectAggregator(65536),  
                new WebSocketServerProtocolHandler("/websocket"),  
                webSocketServerHandler
                );
		
	}

}
