package com.vv.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

/**
 * Netty 4 可运行（已验证）
 */
public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            System.out.println("[服务器应答消息][CONTENT_TYPE]::" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
        }
        
        if(msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            System.out.println("[服务器应答消息][消息内容]::" + buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();
        }
    }
}