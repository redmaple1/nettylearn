package com.xyren.socket.simple;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author renxiaoya
 * @date 2021-01-29
 **/
public class SimpleClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        // 设置超时时间
        socket.setSoTimeout(3000);

        // 连接服务器
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 2000), 3000);

        System.out.println("已发起服务器连接，开始后续流程~");
        System.out.println("客户端信息 : " + socket.getLocalAddress() + " P : " + socket.getLocalPort());
        System.out.println("服务器信息 : " + socket.getInetAddress() + " P : " + socket.getPort());

        try {
            // 发送数据
            todo(socket);
        } catch (Exception e) {
            System.out.println("socket异常");
        }

        socket.close();
        System.out.println("客户端已退出");
    }

    private static void todo(Socket client) throws IOException {
        // 构建键盘输入流
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        // 得到socket输出流，并转换为打印流
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);

        // 得到socket输入流，并转换为BufferedReader
        InputStream inputStream = client.getInputStream();
        BufferedReader socketInputBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        boolean flag = true;
        do {
            // 从键盘读取一行
            String str = input.readLine();
            // 发送到服务器
            socketPrintStream.println(str);

            String echo = socketInputBufferedReader.readLine();
            if ("bye".equalsIgnoreCase(echo)) {
                flag = false;
            } else {
                System.out.println(echo);
            }
        } while (flag);

    }
}
