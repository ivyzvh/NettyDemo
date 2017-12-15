package com.vv.privateProtocolStack;
import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;
import java.io.StreamCorruptedException;

/**
 * @author Lilinfeng
 * @version 1.0
 * @date 2014��3��14��
 */
public class MarshallingDecoder {

    private final Unmarshaller unmarshaller;

    /**
     * Creates a new decoder whose maximum object size is {@code 1048576} bytes.
     * If the size of the received object is greater than {@code 1048576} bytes,
     * a {@link StreamCorruptedException} will be raised.
     *
     * @throws IOException
     */
    public MarshallingDecoder() throws IOException {
        unmarshaller = MarshallingCodecFactory.buildUnMarshalling();
    }

    protected Object decode(ByteBuf in) throws Exception {
        //1. ��ȡ��һ��4bytes��������õ���object�����byte����
        int objectSize = in.readInt();
        ByteBuf buf = in.slice(in.readerIndex(), objectSize);
        //2 . ʹ��bytebuf�Ĵ�����
        ByteInput input = new ChannelBufferByteInput(buf);
        try {
            //3. ��ʼ����
            unmarshaller.start(input);
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();
            //4. ����֮�����ö�ȡ��λ��
            in.readerIndex(in.readerIndex() + objectSize);
            return obj;
        } finally {
            unmarshaller.close();
        }
    }
}