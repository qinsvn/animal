package com.i.socket.webSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

public class WebSocketServerHandler  extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private static final ChannelGroup users= new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	/*
     * 被启用的时候触发 (在建立连接的时候)
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	System.out.println(ctx.channel().remoteAddress()+ "连接!");
    	users.add(ctx.channel());
        sendMsg("当前在线:"+users.size());
        super.channelActive(ctx);
    }

	//接收消息
	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
			TextWebSocketFrame msg) throws Exception {
		System.out.println(msg.text());
		sendMsg(msg.text());
		
	}
	
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		users.remove(ctx.channel());
		sendMsg("当前在线:"+users.size());
		super.handlerRemoved(ctx);
	}

	public void sendMsg(String msg){
		TextWebSocketFrame tmsg=new TextWebSocketFrame(msg);
		users.writeAndFlush(tmsg);
	}
}
