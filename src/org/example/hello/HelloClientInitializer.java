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
         * ����ط��� ����ͷ���˶�Ӧ�ϡ������޷���������ͱ���
         * ����ͱ��� �ҽ�������һ��Ϊ�����ϸ�Ľ��⡣�ٴ���ʱ������ϸ������
         */
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter())); // ���ڷָ�����֡������
        pipeline.addLast("decoder", new StringDecoder()); // �ַ���������
        pipeline.addLast("encoder", new StringEncoder()); // �ַ���������
        
        // �ͻ��˵��߼�
        pipeline.addLast("handler", new HelloClientHandler()); // �Զ��崦����
    }
}