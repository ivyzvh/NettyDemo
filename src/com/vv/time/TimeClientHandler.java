package com.vv.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

/**
 * Netty 5
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
	private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());

	private final ByteBuf firstMessage;

	public TimeClientHandler() {
		System.out.println(this.getClass().getSimpleName() + ".TimeClientHandler()");
		byte[] req = "QUERY TIME ORDER".getBytes();
		firstMessage = Unpooled.buffer(req.length);
		firstMessage.writeBytes(req);
	}

	//@Override
	public void channelActive(ChannelHandlerContext ctx) throws UnsupportedEncodingException {
		System.out.println(this.getClass().getSimpleName() + ".channelActive()");
		ctx.writeAndFlush(firstMessage);
	}

	//@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(this.getClass().getSimpleName() + ".channelRead()");
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("时间服务器返回信息 : " + body);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.out.println(this.getClass().getSimpleName() + ".exceptionCaught()");
		logger.warning("Unexcepted exception from downstram : " + cause.getMessage());
		ctx.close();
	}

}
