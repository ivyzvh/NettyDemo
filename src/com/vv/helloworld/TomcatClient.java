package com.vv.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;

import com.vv.db.DB;
import com.vv.model.SystemAccount;

/**
 * Netty 4
 */
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
            
            List<SystemAccount> list = DB.getSystemAccountList();
            for (SystemAccount o : list) {
            	channel.writeAndFlush(JSONObject.fromObject(o)+"\r\n");
            }
            
            channel.writeAndFlush("99999\r\n");
            channel.writeAndFlush("0000\r\n");
            
            channel.closeFuture().sync();  
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }
}