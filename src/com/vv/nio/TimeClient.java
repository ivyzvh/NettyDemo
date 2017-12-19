package com.vv.nio;

public class TimeClient {
	public static void main(String[] args) {
		int port = 7878;

		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				// ����Ĭ��ֵ
			}
		}

		new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();
	}
}
