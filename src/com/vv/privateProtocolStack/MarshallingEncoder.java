package com.vv.privateProtocolStack;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;

import java.io.IOException;

import javax.xml.bind.Marshaller;

/**
 * @author Lilinfeng
 * @version 1.0
 * @date 2014��3��14��
 */
@Sharable
public class MarshallingEncoder {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    Marshaller marshaller;

    public MarshallingEncoder() throws IOException {
        marshaller = MarshallingCodecFactory.buildMarshalling();
    }

    // ʹ��marshall��Object���б��룬����д��bytebuf...
    protected void encode(Object msg, ByteBuf out) throws Exception {
        try {
            //1. ��ȡд��λ��
            int lengthPos = out.writerIndex();
            //2. ��д��4��bytes�����ڼ�¼Object�������󳤶�
            out.writeBytes(LENGTH_PLACEHOLDER);
            //3. ʹ�ô�����󣬷�ֹmarshallerд��֮��ر�byte buf
            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
            //4. ��ʼʹ��marshaller��bytebuf�б���
            marshaller.start(output);
            marshaller.writeObject(msg);
            //5. ��������
            marshaller.finish();
            //6. ���ö��󳤶�
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
        } finally {
            marshaller.close();
        }
    }
}