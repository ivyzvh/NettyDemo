package com.vv.helloworld;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty 4
 */
public class HelloServer {
    
    /**
     * ����˼����Ķ˿ڵ�ַ
     */
    private static final int portNumber = 7878;
    
    public static void main(String[] args) throws InterruptedException {
    	// ���������߳��飬һ��ר�����������¼��������ܿͻ��˵����ӣ�����һ�����������ͨ�ŵĶ�д
    	// �������������߳�,Worker�߳�ΪBoss�̷߳���,
    	// EventLoopGroup������3.x�汾�е��߳�,���ڹ���Channel���ӵ�
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // ���ڴ���������˽��տͻ�������
        EventLoopGroup workerGroup = new NioEventLoopGroup();  // ��������ͨ�ţ���д��  
        try {
            ServerBootstrap serverBoot = new ServerBootstrap(); // ���������࣬���ڷ�����ͨ����һϵ������
            serverBoot.group(bossGroup, workerGroup); // �������߳���
            serverBoot.channel(NioServerSocketChannel.class); // ָ��NIO��ģʽ
            serverBoot.childHandler(new HelloServerInitializer()); // ���þ�������ݴ���ʽ,HelloServerInitializer������ʵ�ʴ������ݵ���
         
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