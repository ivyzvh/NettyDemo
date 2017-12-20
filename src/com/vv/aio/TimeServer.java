package com.vv.aio;

import java.io.IOException;

public class TimeServer {

/**
* @param args
* @throws IOException
*/
public static void main(String[] args) throws IOException {
int port = 7878;
if (args != null && args.length > 0) {
try {
port = Integer.valueOf(args[0]);
} catch (NumberFormatException e) {
// ����Ĭ��ֵ
}
}
AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();
}
}