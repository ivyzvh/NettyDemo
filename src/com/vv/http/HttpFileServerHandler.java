package com.vv.http;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
		@Override 
		public void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) 
				throws Exception {
			if (!request.getDecoderResult().isSuccess()) {
				sendError(ctx, BAD_REQUEST);
				return;
			}
			if (request.getMethod() != GET) {
				sendError(ctx, METHOD_NOT_ALLOWED);
				return;
			}
			final String uri = request.getUri();
			final String path = sanitizeUri(uri);
			if (path == null) {
				sendError(ctx, FORBIDDEN);
				return;
			}
			
			File file = new File(path);
			if (file.isHidden() || !file.exists()) {
				sendError(ctx, NOT_FOUND);
				return;
			}
			
			//此处代码省略 
			
			if (!file.isFile()) {
				sendEror(ctx, FORBIDDEN);
				return;
			}
			RandomAccessFile randomAccessFile = null;
			try {
				randomAccessFile = new RandomAccessFile(file, "r"); // 以只读方式打开文件
			} catch (FileNotFoundException fnfe) {
				sendError(ctx, NOT_FOUND);
				return;
			}
			
			long fileLength = randomAccessFile.length();
			HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
			setContentLength(response, fileLength);
			setContentTypeHeader(response, file);
			if (isKeepAlive(request)) {
				response.headers.set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
			}
			ctx.write(response);
			ChannelFuture sendFileFuture;
			sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 8192)),
					ctx.newProgressivePromise());
			sendFileFuture.addListener(new ChannelProgressiveFutureListenter() {
				// 此处代码省略
				privte String sanitizeUri(String uri) {
					try {
						uri = URLDecoder.decode(uri, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						try {
							uri = URLDecoder.decode(uri, "ISO-8859-1");
						} catch (UnsupportedEncodingException e) {
							throw new Error();
						}
					}
					// 此处代码省略
					uri = uri.replace('/', File.separatorChar);
					if (uri.contains(File.separator + "."))
						|| uri.contains('.' + File.separator) 
						|| uri.startsWith(".")
						|| uri.endsWith(".")
						|| INSECURE_URI.matcher(uri).matches()) {
				          return null;
			        }
					return System.getProperty("user.dir") + File.separator + uri;
				}
				
				private static void sendListing(ChannelHandlerContext ctx, Filedir) {
					FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);
					response.headers().set(CONTENT_TYPE, "text/html;charset=UTF-8");
					StringBuilder buf = new StringBuilder();
					// 代码省略
					buf.append("<li>链接：<a href=\"../\">..</a></li>\r\n");
					for (File f : dir.listFiles()) {
						// 代码省略
						buf.append("<li>链接：<a href=\"");
						// 代码省略						
					}
					buf.append("</ul></body></html>\r\n");
			
					ByteBuf buffer = Unpooled.copiedBuffer(buf, CharsetUtil.UTF_8);
					response.content().writeBytes(buffer);
					buffer.release();
					ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
				}
				// 代码省略
					
					
					
				}
			});
			
			
		}
	}
	
	
	
	
}
