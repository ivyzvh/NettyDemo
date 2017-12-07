package org.example.hello;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;

public class TomcatClient {
    
    public static String host = "127.0.0.1"; // 变量
    public static int port = 7878; // 变量

    /**
     * @param args
     * @throws InterruptedException 
     * @throws IOException 
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                     .channel(NioSocketChannel.class)
                     .handler(new HelloClientInitializer());  // 变量

            // 连接服务端
            Channel channel = bootstrap.connect("127.0.0.1", 7878).sync().channel();  
            
            channel.writeAndFlush("99999\r\n");
            channel.writeAndFlush("0000\r\n");
            
            channel.closeFuture().sync();  
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }
}