package com.vv.mock_nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient {

	public static void main(String[] args) {
		int port = 7878;

		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;

		try {
			socket = new Socket("127.0.0.1", port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			//for (int i=0; i<1; i++) {
				/*out.println("QUERY TIME ORDER");
				System.out.println("[Client] Send order 2 server succeed.");
				String resp = in.readLine();
				System.out.println("[Client] Current time is : " + resp);*/
			//}
			
			/*
			 */
			// 多线程:模拟多线程环境
			for (int i = 0; i < 10; i++) {
				new Thread(new ClientRunnable("127.0.0.1", 7878, i)).start();
			}
		} catch (Exception e) {

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				in = null;
			}

			if (out != null) {
				out.close();
				out = null;
			}

			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				socket = null;
			}
		}

	}

}
