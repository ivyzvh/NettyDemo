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
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBoot = new ServerBootstrap();
            serverBoot.group(bossGroup, workerGroup);
            serverBoot.channel(NioServerSocketChannel.class);
            serverBoot.childHandler(new HelloServerInitializer()); // 关键

            // 服务器绑定端口监听
            ChannelFuture future = serverBoot.bind(portNumber).sync();
            // 监听服务器关闭监听
            future.channel().closeFuture().sync();

            // 可以简写为
            /* serverBoot.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}