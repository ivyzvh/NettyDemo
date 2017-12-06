package org.example.hello;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloServer {
    
    /**
     * 服务端监听的端口地址
     */
    private static final int portNumber = 7878;
    
    public static void main(String[] args) throws InterruptedException {
    	// 定义两个工作线程,Worker线程为Boss线程服务,
    	// EventLoopGroup类似于3.x版本中的线程,用于管理Channel连接的
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBoot = new ServerBootstrap();
            serverBoot.group(bossGroup, workerGroup);
            serverBoot.channel(NioServerSocketChannel.class);
            serverBoot.childHandler(new HelloServerInitializer()); // 需要自定义

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