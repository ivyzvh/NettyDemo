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
     * 服务端监听的端口地址
     */
    private static final int portNumber = 7878;
    
    public static void main(String[] args) throws InterruptedException {
    	// 创建两个线程组，一个专门用于网络事件处理（接受客户端的连接），另一个则进行网络通信的读写
    	// 定义两个工作线程,Worker线程为Boss线程服务,
    	// EventLoopGroup类似于3.x版本中的线程,用于管理Channel连接的
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // 用于处理服务器端接收客户端连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();  // 进行网络通信（读写）  
        try {
            ServerBootstrap serverBoot = new ServerBootstrap(); // 辅助工具类，用于服务器通道的一系列配置
            serverBoot.group(bossGroup, workerGroup); // 绑定两个线程组
            serverBoot.channel(NioServerSocketChannel.class); // 指定NIO的模式
            serverBoot.childHandler(new HelloServerInitializer()); // 配置具体的数据处理方式,HelloServerInitializer：用于实际处理数据的类
         
            // 服务器绑定端口监听
            ChannelFuture future = serverBoot.bind(portNumber).sync();
            // 监听服务器关闭监听(为了防止线程停止)。
            future.channel().closeFuture().sync();

            // 可以简写为
            /* serverBoot.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}