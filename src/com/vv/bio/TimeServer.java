package com.vv.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {

	public static void main(String[] args) throws IOException {
		int port = 7878;
		ServerSocket server = null;
		try {
			server = new ServerSocket(7878);
			System.out.println("[Server] The time server is start in port: " + port);

			Socket socket = null;
			while (true) {
				System.out.println("[Server] Before Accept");
				socket = server.accept();
				System.out.println("[Server] Behind Accept");
				new Thread(new TimeServerHandler(socket)).start();
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
 