package com.i.socket.webSocket;

import com.i.util.TextLogUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WebsocketServer {
	private static final int PORT = 7878;
	protected static final int BIZTHREADSIZE = 1;
	
	private static EventLoopGroup bossGroup;
	private static EventLoopGroup workerGroup;
	private static Channel serverChannel;
	private static WebSocketServerInitializer webSocketServerInitializer;
	
	private static boolean isStart = false;
	
	public static void start(){
		if (isStart) {
			stop();
		}
		new  Thread(){
			public void run() {
				try {
					bossGroup=new NioEventLoopGroup(BIZTHREADSIZE);
					workerGroup=new NioEventLoopGroup(BIZTHREADSIZE);
					webSocketServerInitializer = new WebSocketServerInitializer();
					ServerBootstrap b=new ServerBootstrap();
					b.group(bossGroup, workerGroup);
					b.channel(NioServerSocketChannel.class);
					b.childHandler(webSocketServerInitializer);
					
					b.option(ChannelOption.SO_BACKLOG, 65536)           
					.childOption(ChannelOption.SO_KEEPALIVE, true)  
					.childOption(ChannelOption.TCP_NODELAY, true);
					// 服务器绑定端口监听
					ChannelFuture f = b.bind(PORT).sync();
					
					serverChannel = f.channel();
					isStart = true;
					// 监听服务器关闭监听
					serverChannel.closeFuture().sync();
				} catch (InterruptedException e) {
					System.out.println("端口被占用:"+PORT);
				}
				finally{
					bossGroup.shutdownGracefully();
					workerGroup.shutdownGracefully();
					bossGroup=null;
					workerGroup =null;
					serverChannel=null;
					webSocketServerInitializer=null;
					isStart = false;
				}
			};
		}.start();
	}
	
	public static void stop(){
		try {
			if (serverChannel != null) {
				serverChannel.close();
				serverChannel = null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		start();
	}
	
	public static void broadcastMsg(String msg){
		if (webSocketServerInitializer!=null) {
			if (webSocketServerInitializer.webSocketServerHandler!=null) {
				webSocketServerInitializer.webSocketServerHandler.sendMsg(msg);
			}else{
				TextLogUtil.setInCurrent("无连接客户端，不广播数据");
				System.out.println("没有websocket客户端，不广播数据");
			}
		}else{
			TextLogUtil.setInCurrent("socket服务未启动！");
			System.out.println("没有启动websocket服务");
		}
	}
	
	public static boolean isStart(){
		return isStart;
	}
}
