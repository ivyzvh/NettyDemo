package com.vv.mock_nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRunnable implements Runnable{
	private String host;
	private int port;
	private int no;
	
	public ClientRunnable(String host, int port, int no) {
		this.host = host;
		this.port = port;
		this.no = no;
	}
	
	@Override
	public void run() {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			socket = new Socket(host, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("QUERY TIME ORDER " + no);
			System.out.println("[Client "+no+"] Send order 2 server succeed.");
			String resp = in.readLine();
			System.out.println("[Client "+no+"] Current time is : " + resp);
		} catch (Exception e) {
			//
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


