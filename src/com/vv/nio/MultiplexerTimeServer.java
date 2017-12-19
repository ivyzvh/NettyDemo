package com.vv.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {

    private Selector selector;
    private ServerSocketChannel servChannel;
    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try{
            selector = Selector.open();
            servChannel = ServerSocketChannel.open();
            servChannel.configureBlocking(false);
            servChannel.socket().bind(new InetSocketAddress(port),1024);
            //servChannel.register(selector, SelectionKey.OP_CONNECT); // ����
            servChannel.register(selector, SelectionKey.OP_ACCEPT);
            this.stop = false;
            System.out.println("[Time Server] start in port : " + port);
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void stop() {
        this.stop = true;
    }

    public void run() {
    while(!stop) {
        try {
            //wait until 1000 millisecond
            selector.select(1000);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();
            SelectionKey key = null;
            while(it.hasNext()) {
                key = it.next();
                try{
                    handleInput(key);
                }catch(Exception e) {

                }
                if(key != null) {
                    key.cancel();
                    if(key.channel() != null) {
                        key.channel().close();
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    //��·�������ر��Ժ�����ע���������Channel��Pipe����Դ�����Զ�ȥע�Ტ�رգ����Բ���Ҫ�ظ��ͷ���Դ
    if(selector != null) {
        try{
            selector.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    }

    //�����¼���Ӧ
    private void handleInput(SelectionKey key) throws Exception {
        if(key.isValid()) {
            if(key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            }
            if(key.isReadable()) {
                SocketChannel sc = (SocketChannel)key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0) {
                	readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("[Time Server] received message: " + body);
                    String response = "success get message at:" 
                    				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    				  .format(new Date(System.currentTimeMillis()));
                    doWrite(sc, response);
                } else {
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    //�ڳɹ����ܿͻ��˵�������Ϣ�Ժ�ͬʱ���ͻ��˷���һ���ɹ�����Ϣ
    private void doWrite(SocketChannel sc, String response) throws Exception {
        if(response != null && response.length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer  = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            sc.write(writeBuffer);
        }
    }
}