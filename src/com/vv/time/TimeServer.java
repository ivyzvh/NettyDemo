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

public class TimeServer {
	
	public void bind(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // ���ڴ���������˽��տͻ�������
		EventLoopGroup workerGroup = new NioEventLoopGroup(); // ��������ͨ�ţ���д��
		try {
			ServerBootstrap serverBoot = new ServerBootstrap(); // ���������࣬���ڷ�����ͨ����һϵ������
			serverBoot.group(bossGroup, workerGroup) // �������߳���
			          .channel(NioServerSocketChannel.class) // ָ��NIO��ģʽ
			          .option(ChannelOption.SO_BACKLOG, 1024)
			          .childHandler(new TimeServerHandler());
			          
			// �������󶨶˿ڼ���
			ChannelFuture future = serverBoot.bind(port).sync();
			// �����������رռ���(Ϊ�˷�ֹ�߳�ֹͣ)��
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
			arg0.pipeline().addLast(new HelloClientHandler());
		}
	}
	
	public static void main(String[] args) throws Exception {
		new TimeServer().bind(7879);
	}
}
