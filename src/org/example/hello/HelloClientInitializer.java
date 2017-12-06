package org.example.hello;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class HelloClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        /** 
         * 这个地方的 必须和服务端对应上。否则无法正常解码和编码
         * 解码和编码 我将会在下一张为大家详细的讲解。再次暂时不做详细的描述
         */
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter())); // 基于分隔符的帧解码器
        pipeline.addLast("decoder", new StringDecoder()); // 字符串解码器
        pipeline.addLast("encoder", new StringEncoder()); // 字符串编码器
        
        // 客户端的逻辑
        pipeline.addLast("handler", new HelloClientHandler()); // 自定义处理器
    }
}