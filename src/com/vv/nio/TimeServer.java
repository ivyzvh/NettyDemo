package com.vv.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * NIO
 */
public class TimeServer {

	public static void main(String[] args) throws IOException {
		int port = 7878;

		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
		new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
	}
}
 