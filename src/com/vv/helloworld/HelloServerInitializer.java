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

        // ��("\n")Ϊ��β�ָ�� ������������һ8192�����֡���ȣ�������������ָ�����
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        // �ַ�������ͱ���
        pipeline.addLast("decoder", new StringDecoder()); // �ַ���������
        pipeline.addLast("encoder", new StringEncoder()); // �ַ���������

        // �Լ����߼�Handler
        pipeline.addLast("handler", new HelloServerHandler()); // ���봦���߼�
    }
}