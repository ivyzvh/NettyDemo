package com.vv.privateProtocolStack;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;
import java.util.Map;

/**
 * Created by carl.yu on 2016/12/19.
 */
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {

    MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() throws IOException {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf sendBuf) throws Exception {
        if (null == msg || null == msg.getHeader()) {
            throw new Exception("The encode message is null");
        }
        //---д��crcCode---
        sendBuf.writeInt((msg.getHeader().getCrcCode()));
        //---д��length---
        sendBuf.writeInt((msg.getHeader().getLength()));
        //---д��sessionId---
        sendBuf.writeLong((msg.getHeader().getSessionID()));
        //---д��type---
        sendBuf.writeByte((msg.getHeader().getType()));
        //---д��priority---
        sendBuf.writeByte((msg.getHeader().getPriority()));
        //---д�븽����С---
        sendBuf.writeInt((msg.getHeader().getAttachment().size()));

        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment()
                .entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes("UTF-8");
            sendBuf.writeInt(keyArray.length);
            sendBuf.writeBytes(keyArray);
            value = param.getValue();
            // marshallingEncoder.encode(value, sendBuf);
        }
        // for gc
        key = null;
        keyArray = null;
        value = null;

        if (msg.getBody() != null) {
            marshallingEncoder.encode(msg.getBody(), sendBuf);
        } else
            sendBuf.writeInt(0);
        // ֮ǰд��crcCode 4bytes����ȥcrcCode��length 8bytes��Ϊ����֮����ֽ�
        sendBuf.setInt(4, sendBuf.readableBytes() - 8);
    }
}