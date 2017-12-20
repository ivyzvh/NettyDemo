package com.vv.aio;

public class TimeClient {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 7878;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				// ����Ĭ��ֵ
			}
		}
		new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();
	}
}
