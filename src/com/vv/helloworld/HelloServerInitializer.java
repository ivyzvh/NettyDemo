package com.vv.helloworld;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Netty 4
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        // 以("\n")为结尾分割的 解码器（参数一8192：最大帧长度，参数二：定义分隔符）
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        // 字符串解码和编码
        pipeline.addLast("decoder", new StringDecoder()); // 字符串解码器
        pipeline.addLast("encoder", new StringEncoder()); // 字符串编码器

        // 自己的逻辑Handler
        pipeline.addLast("handler", new HelloServerHandler()); // 代码处理逻辑
    }
}