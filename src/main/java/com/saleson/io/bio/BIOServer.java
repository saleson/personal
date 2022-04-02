package com.saleson.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author saleson
 * @date 2022-03-02 00:34
 */
public class BIOServer {

    public static void main(String[] args) {
        try {
            openServer(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void openServer(int port) throws IOException {
//        创建ServerSocket
        ServerSocket serverSocket = new ServerSocket();
//        绑定端口
        serverSocket.bind(new InetSocketAddress(port));
        System.out.println("BIOServer启动...");
        while (true) {
//            堵塞，等待客户端连接
            Socket socket = serverSocket.accept();
            System.out.println("连接成功...");
//            输入流
//            BufferedReader是缓冲输入流，InputStreamReader是转换输入流，InputStream是字节输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String str = null;
            while ((str = br.readLine()) != null) {
                if ("quit".equals(str)) {
                    break;
                }
                System.out.println(str);
            }
//            关闭流
            br.close();
//            关闭socket
            socket.close();
            System.out.println("连接结束...");
        }

//        System.out.println("BIOServer关闭...");

    }

}
