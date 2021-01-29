package com.xyren.socket.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author renxiaoya
 * @date 2021-01-29
 **/
public class SimpleServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(2000);

        System.out.println("服务器已启动，等待客户端连接。" + server.getInetAddress() + " IP : " + server.getLocalPort());

        // 等待客户端连接
        for (; ; ) {
            Socket client = server.accept();
            ClientHandler clientHandler = new ClientHandler(client);
            clientHandler.start();
        }

    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private boolean flag = true;

        private ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("新客户端连接: " + socket.getInetAddress() + " P: " + socket.getPort());
            try {
                // 得到打印流，用于数据输出。服务器回送数据使用
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                // 得到输入流，用于接收数据
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                do {
                    // 客户端拿到一条数据
                    String str = socketInput.readLine();
                    if ("bye".equalsIgnoreCase(str)) {
                        socketOutput.println("bye");
                        flag = false;
                    } else {
                        System.out.println(str);
                        socketOutput.println("收到：" + str.length());
                    }
                } while (flag);

                socketOutput.close();
                socketInput.close();


            } catch (Exception e) {
                System.out.println("连接异常断开");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("客户端已退出。" + socket.getInetAddress() + " P: " + socket.getPort());
        }
    }
}
