package com.vv.http2;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Netty 4 �����У�����֤��
 */
public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {

    private static Log log = LogFactory.getLog(HttpServerInboundHandler.class);

    private HttpRequest request;

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
            String uri = request.getUri();
            System.out.println("Uri::" + uri);
        }

        if (msg instanceof HttpContent) {
        	// ��ӡ�ͻ��˷��͵���Ϣ
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            System.out.println("�ͻ��˷��͵���Ϣ::" + buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();

            // ��Ӧ��Ϣ[Ӧ����Ϣ]
            // String res = "I am OK";
            String res = "<html><head><title>Netty Http Demo</title></head><body>This is a Netty HTTP Demo.<a href=\"#\">�й�</a></body></html>";
            
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
            //response.headers().set(CONTENT_TYPE, "text/plain"); // ���ı�
            response.headers().set(CONTENT_TYPE, "text/html; charset=utf-8");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            if (HttpHeaders.isKeepAlive(request)) {
                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
            }
            context.write(response);
            context.flush();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage());
        ctx.close();
    }

}