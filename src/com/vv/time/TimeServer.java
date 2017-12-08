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
		System.out.println("[ʱ�������]����...");
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // ���ڴ���������˽��տͻ�������
		EventLoopGroup workerGroup = new NioEventLoopGroup(); // ��������ͨ�ţ���д��
		try {
			ServerBootstrap serverBoot = new ServerBootstrap(); // ���������࣬���ڷ�����ͨ����һϵ������
			serverBoot.group(bossGroup, workerGroup) // �������߳���
			          .channel(NioServerSocketChannel.class) // ָ��NIO��ģʽ
			          .option(ChannelOption.SO_BACKLOG, 1024)
			          .childHandler(new ChildChannelHandler());
			
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
			System.out.println("[ʱ�������]" + this.getClass().getSimpleName() + ".initChannel()");
			arg0.pipeline().addLast(new TimeServerHandler());
		}
	}
	
	public static void main(String[] args) throws Exception {
		new TimeServer().bind(7879);
	}
}
