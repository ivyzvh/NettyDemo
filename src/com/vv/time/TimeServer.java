package com.vv.time;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.example.hello.HelloClientHandler;

/**
 * Netty 5
 */
public class TimeServer {
	
	public void bind(int port) throws Exception {
		System.out.println("[时间服务器]启动...");
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // 用于处理服务器端接收客户端连接
		EventLoopGroup workerGroup = new NioEventLoopGroup(); // 进行网络通信（读写）
		try {
			ServerBootstrap serverBoot = new ServerBootstrap(); // 辅助工具类，用于服务器通道的一系列配置
			serverBoot.group(bossGroup, workerGroup) // 绑定两个线程组
			          .channel(NioServerSocketChannel.class) // 指定NIO的模式
			          .option(ChannelOption.SO_BACKLOG, 1024)
			          .childHandler(new ChildChannelHandler());
			
			// 服务器绑定端口监听
			ChannelFuture future = serverBoot.bind(port).sync();
			// 监听服务器关闭监听(为了防止线程停止)。
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
			System.out.println("[时间服务器]" + this.getClass().getSimpleName() + ".initChannel()");
			arg0.pipeline().addLast(new TimeServerHandler());
		}
	}
	
	public static void main(String[] args) throws Exception {
		new TimeServer().bind(7879);
	}
}
