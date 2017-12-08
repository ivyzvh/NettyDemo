package com.vv.time2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Netty 5
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(this.getClass().getSimpleName() + ".channelRead()");
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("[时间服务器收到信息]" + body);
		
		//String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? 
		//		new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
		
		String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
								new Date(System.currentTimeMillis())
							 );
	    
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.write(resp);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println(this.getClass().getSimpleName() + ".channelReadComplete()");
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.out.println(this.getClass().getSimpleName() + ".exceptionCaught()");
		ctx.close();
	}
	
}
