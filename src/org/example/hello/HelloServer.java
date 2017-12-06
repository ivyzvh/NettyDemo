package org.example.hello;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloServer {
    
    /**
     * ����˼����Ķ˿ڵ�ַ
     */
    private static final int portNumber = 7878;
    
    public static void main(String[] args) throws InterruptedException {
    	// �������������߳�,Worker�߳�ΪBoss�̷߳���,
    	// EventLoopGroup������3.x�汾�е��߳�,���ڹ���Channel���ӵ�
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBoot = new ServerBootstrap();
            serverBoot.group(bossGroup, workerGroup);
            serverBoot.channel(NioServerSocketChannel.class);
            serverBoot.childHandler(new HelloServerInitializer()); // ��Ҫ�Զ���

            // �������󶨶˿ڼ���
            ChannelFuture future = serverBoot.bind(portNumber).sync();
            // �����������رռ���(Ϊ�˷�ֹ�߳�ֹͣ)��
            future.channel().closeFuture().sync();

            // ���Լ�дΪ
            /* serverBoot.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}