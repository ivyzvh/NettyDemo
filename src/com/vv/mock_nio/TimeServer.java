package com.vv.mock_nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * α�첽
 */
public class TimeServer {

	public static void main(String[] args) throws IOException {
		int port = 7878;
		ServerSocket server = null;
		try {
			server = new ServerSocket(7878);
			System.out.println("[Server] The time server is start in port: " + port);
			Socket socket = null;
			TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50, 10000);
			while (true) {
				System.out.println("[Server] Before Accept");
				socket = server.accept();
				System.out.println("[Server] Behind Accept");
				singleExecutor.execute(new TimeServerHandler(socket));
			}
		} finally {
			if (server != null) {
				System.out.println("[Server] The time server close.");
				server.close();
				server = null;
			}
		}
	}
}
 